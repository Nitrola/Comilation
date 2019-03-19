package yal.arbre.expressions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeFonction;
import yal.analyse.symbole.Symbole;
import yal.analyse.symbole.SymboleFonction;
import yal.exceptions.AnalyseSemantiqueException;

import java.util.ArrayList;

public class AppelFonction extends Expression{

    private String idf;
    private String type;
    private String label;
    private int nbParam;
    private ArrayList<Expression> realParam;

    public AppelFonction(String id, int noLigne){
        super(noLigne);
        idf = id;
        this.nbParam = 0;
        realParam = new ArrayList<>();
    }

    public AppelFonction(String id, int noLigne, ArrayList<Expression> parameters){
        super(noLigne);
        idf = id;
        this.nbParam = parameters.size();
        this.realParam = parameters;
    }


    @Override
    public String getType() {
        return type;
    }

    @Override
    public String operation() {
        return "Appel";
    }

    @Override
    public void verifier() {

        EntreeFonction e = new EntreeFonction(idf,nbParam);
        SymboleFonction s = (SymboleFonction) TDS.getInstance().identifier(e);

        if (s == null){
            throw new AnalyseSemantiqueException(getNoLigne(),"pas de déclaration de " + idf + "()");
        }
        label = s.getLabel();
        type = s.getType();

    }

    @Override
    public String toMIPS() {
        String mips =
                "#mise en place des paramètres \n" +
                "add $sp, $sp, -" + this.nbParam *4 + "\n";

        for(int i = 0; i < nbParam; i++){
            Expression param = realParam.get(i);
            mips += param.toMIPS() + " sw $v0, " + i*4 + "($sp)\n" ;
        }

        mips += "#Appel de fonction\n"+
                "#Allocation pour la valeur retournée\n"+
                "add $sp, $sp, -4\n\n"+
                "#Jump vers le label de la fonction " + idf + "\n"+
                "jal " + label + "\n\n"+
                "#Depile dans $v0\n" +
                "add $sp, $sp, 4\n"+
                "lw $v0, 0($sp)\n\n"+
                "#Dépiler les params\n"+
                "add $sp, $sp, " + nbParam *4 + "\n\n";


        return mips;
    }
}

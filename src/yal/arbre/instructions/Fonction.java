package yal.arbre.instructions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeFonction;
import yal.analyse.symbole.Symbole;
import yal.analyse.symbole.SymboleFonction;
import yal.arbre.BlocDInstructions;
import yal.exceptions.AnalyseSemantiqueException;

import java.util.ArrayList;

public class Fonction extends Instruction{

    private String idf;
    private String label;

    private int idRegion;
    private int nbParam;
    private int varMemory;

    private BlocDInstructions inst;

    public Fonction(BlocDInstructions b, String idf, int nbParameters, int Lignum){
        super(Lignum);
        this.idf = idf;
        this.inst = b;
        this.nbParam = nbParameters;
        this.idRegion = TDS.getInstance().getIdRegion();
        this.varMemory = TDS.getInstance().tailleTableVariable();
    }


    public boolean isReturn(){
        return inst.isReturn();
    }

    @Override
    public void verifier() {

        EntreeFonction e = new EntreeFonction(idf,nbParam);
        SymboleFonction s = (SymboleFonction) TDS.getInstance().identifier(e);

        if(s == null){
            throw  new AnalyseSemantiqueException(getNoLigne(),"pas de déclaration de la fonction " + idf + "()");
        }

        label = s.getLabel();
        TDS.getInstance().entreeBloc();
        inst.verifier();


        if (!isReturn()) {
            throw new AnalyseSemantiqueException(getNoLigne(), "retourne peut ne pas être atteint dans la fonction " + idf + "( avec " + nbParam + " parametres )");
        }

        TDS.getInstance().sortieBloc();

    }

    @Override
    public String toMIPS() {

        StringBuilder mips = new StringBuilder("#Fonction\n" + label + ":\n" +
                "#Empile l'adresse de retour\n" +
                "sw $ra, 0($sp)\n" +
                "add $sp, $sp, -4\n" + "\n" +
                "#Empilement chainage dynamique\n" +
                "sw $s7, 0($sp)\n" +
                "add $sp, $sp, -4\n" + "\n" +
                "#Empilement de l'id de la region\n" +
                "li $t8, " + idRegion + "\n" +
                "sw $t8, 0($sp)\n"+
                "add $sp, $sp, -4\n" + "\n" +
                "#Deplacement de la base\n" +
                "move $s7, $sp\n" + "\n" +
                "#Allocation des variables \n" +
                "add $sp, $sp , -" + this.varMemory + "\n" +
                "# initialisation de toutes les variables a 0\n") ;

                for(int dep = 0; dep < this.varMemory; dep += 4) {
                    mips.append("sw $zero, -");
                    mips.append(dep);
                    mips.append("($s7)\n");
                }

                mips.append("#Instruction dans la fonction\n");
                mips.append(inst.toMIPS());
                mips.append("\n");

                return mips.toString();
    }
}

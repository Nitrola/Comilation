package yal.arbre.instructions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeFonction;
import yal.analyse.symbole.SymboleFonction;
import yal.arbre.BlocDInstructions;
import yal.exceptions.AnalyseSemantiqueException;

public class Fonction extends Instruction{
    private String idf;
    private String label;

    private int idRegion;
    private int varMemory;

    private BlocDInstructions inst;

    public Fonction(BlocDInstructions b, String idf, int nbParameters,int Lignum){
        super(Lignum);
        this.idf = idf;
        this.inst = b;
        this.idRegion = TDS.getInstance().getIdRegion();
        this.varMemory = TDS.getInstance().memorySizeVar();

    }


    public boolean isReturn(){
        return inst.isReturn();
    }
    @Override
    public void verifier() {
        EntreeFonction e = new EntreeFonction(idf,0);
        SymboleFonction s = (SymboleFonction) TDS.getInstance().identifier(e);
        if(s == null){
            throw  new AnalyseSemantiqueException(getNoLigne(),"pas de déclaration de la fonction " + idf + "()");
        }
        label = s.getLabel();
        TDS.getInstance().entreeBloc();
        inst.verifier();
        TDS.getInstance().sortieBloc();

    }

    @Override
    public String toMIPS() {
        return "#Fonction\n" + label + ":\n" +
                "#Empile l'adresse de retour\n"+
                "sw $ra, 0($sp)\n" +
                "add $sp, $sp, -4\n" + "\n" +
                "#Empilement chainage dynamique\n" +
                "sw $s7, 0($sp)\n" +
                "add $sp, $sp, -4\n" + "\n"+
                "#Empilement de l'id de la region\n"+
                "li $t8" + idRegion + "\n"+
                "sw $t8, 0($sp)\n"+
                "add $sp, $sp, -4\n" + "\n" +
                "#Deplacement de la base\n"+
                "move $s7, $sp\n" + "\n"+
                "#Instruction dans la fonction\n"+
                inst.toMIPS() + "\n";
    }
}

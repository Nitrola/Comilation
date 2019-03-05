package yal.arbre;

import yal.analyse.TDS;

import java.util.ArrayList;

public class Main extends ArbreAbstrait {

    protected ArrayList<ArbreAbstrait> programme ;

    protected static String zoneData = ".data\n" +
            "finLigne:     .asciiz \"\\n\"\n" +
            "              .align 2\n" +
            "divZero:      .asciiz \"Erreur :\\n\\t division par zero\"\n" +
            "vrai:         .asciiz \"vrai\"\n" +
            "faux:         .asciiz \"faux\"\n\n" ;

    protected static String debutCode = ".text\n" +
            "main :\n" ;

    protected static String finCode = "end :\n" +
            "    li $v0, 10            # retour au système\n" +
            "    syscall\n\n" ;

    private int tailleTableVariables;

    private BlocDInstructions blocDInstructions;
    private BlocDInstructions blocDeFonctions; //instructions des fonctions a la fin du mips

    public Main(BlocDInstructions b, int n) {
        super(n);
        blocDInstructions = b;
        blocDeFonctions = new BlocDInstructions(n + 1);
    }

    public Main(BlocDInstructions f, BlocDInstructions b, int n) {
        super(n);
        blocDInstructions = b;
        blocDeFonctions = f;
    }

    @Override
    public void verifier() {

        TDS.getInstance().entreeBloc();
        tailleTableVariables = TDS.getInstance().tailleTableVariable();
        blocDInstructions.verifier();
        blocDeFonctions.verifier();
        TDS.getInstance().sortieBloc();

    }

    @Override
    public String toMIPS() {

        StringBuilder sb = new StringBuilder("") ;
        sb.append(zoneData) ;
        sb.append(debutCode) ;

        sb.append("#intialiser $s7 avec $sp\n");
        sb.append("move $s7, $sp\n");
        sb.append("\n");

        sb.append("#réserver la place pour ");
        sb.append(tailleTableVariables/4);
        sb.append(" variables\n");
        sb.append("addi $sp, $sp, ");
        sb.append(- tailleTableVariables);
        sb.append("\n");
        sb.append("\n");

        sb.append("# initialisation de toutes les variables a 0\n");

        for(int dep = 0; dep < tailleTableVariables; dep += 4) {
            sb.append("sw $zero, -");
            sb.append(dep);
            sb.append("($s7)\n");
        }

        sb.append("\n");

        sb.append(blocDInstructions.toMIPS());
        sb.append(finCode) ;

        sb.append(blocDeFonctions.toMIPS()); //les fonctions

        return sb.toString() ;

    }
}

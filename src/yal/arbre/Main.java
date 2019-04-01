package yal.arbre;

import yal.analyse.TDS;
import yal.arbre.instructions.Instruction;

import java.util.ArrayList;

public class Main extends ArbreAbstrait {

    protected ArrayList<ArbreAbstrait> programme ;

    protected static String zoneData = ".data\n" +
            "finLigne:     .asciiz \"\\n\"\n" +
            "              .align 2\n" +
            "divZero:      .asciiz \"Erreur :\\n\\t division par zero\"\n" +
            "vrai:         .asciiz \"vrai\"\n" +
            "faux:         .asciiz \"faux\"\n" +
            "longueurTabInvalide :\n" +
            "              .asciiz \"decl de tab avec longueur nulle ou negative\"\n" +
            "accesTabInvalide : \n" +
            "              .asciiz \"acces a un tableau avec indice invalide (pas dans les bornes)\"\n\n";

    protected static String debutCode = ".text\n" +
            "main :\n\n" ;

    protected static String finCode = "end :\n" +
            "    li $v0, 10            # retour au système\n" +
            "    syscall\n\n" +

            "    erreurLongueurTab :\n" +
            "    li $v0, 4\n" +
            "    la $a0, longueurTabInvalide\n" +
            "    syscall\n" +
            "    j end\n\n" +

            "    erreurAccesTab :\n" +
            "    li $v0, 4\n" +
            "    la $a0, accesTabInvalide\n" +
            "    syscall\n" +
            "    j end\n\n" ;

    private int tailleTableVariables;

    private BlocDInstructions blocDInstructions;
    private BlocDInstructions blocDeTableaux;
    private BlocDInstructions blocDeFonctions; //instructions des fonctions a la fin du mips

    public Main(BlocDInstructions b, int n) {
        super(n);
        blocDInstructions = b;
        blocDeFonctions = new BlocDInstructions(n + 1);
        blocDeTableaux = new BlocDInstructions(n + 1);
    }

    public Main(BlocDInstructions f, BlocDInstructions b, int n) {
        super(n);
        blocDInstructions = b;
        blocDeFonctions = new BlocDInstructions(n + 1);
        blocDeTableaux = new BlocDInstructions(n + 1);

        for(int i = 0; i < f.nbInst(); i++) {
            Instruction decl = f.get(i);

            if(decl.isDeclTab()) {
                blocDeTableaux.ajouter(decl);
            }else{
                blocDeFonctions.ajouter(decl);
            }
        }
    }

    @Override
    public void verifier() {

        TDS.getInstance().entreeBloc();
        tailleTableVariables = TDS.getInstance().tailleTableVariable();
        blocDeTableaux.verifier();
        blocDeFonctions.verifier();
        blocDInstructions.verifier();
        TDS.getInstance().sortieBloc();

    }

    @Override
    public String toMIPS() {

        StringBuilder sb = new StringBuilder("") ;
        sb.append(zoneData) ;
        sb.append(debutCode) ;

        sb.append("#numéro de région\n");
        sb.append("li $t8, 0\n");
        sb.append("sw $t8, 0($sp)\n");
        sb.append("addi $sp, $sp, -4\n");
        sb.append("\n");

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

        sb.append(blocDeTableaux.toMIPS()); //les tableaux
        sb.append(blocDInstructions.toMIPS()); //les instructions
        sb.append(finCode) ;
        sb.append(blocDeFonctions.toMIPS()); //les fonctions

        return sb.toString() ;

    }
}

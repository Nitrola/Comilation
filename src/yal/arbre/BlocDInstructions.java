package yal.arbre;

import yal.analyse.TDS;

import java.util.ArrayList;

/**
 * 21 novembre 2018
 *
 * @author brigitte wrobel-dautcourt
 */

public class BlocDInstructions extends ArbreAbstrait {
    
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
                                      "    syscall\n" ;

    private int tailleTableVariables;

    public BlocDInstructions(int n) {
        super(n) ;
        programme = new ArrayList<>() ;
    }
    
    public void ajouter(ArbreAbstrait a) {
        programme.add(a) ;
    }
    
    @Override
    public String toString() {
        return programme.toString() ;
    }

    @Override
    public void verifier() {

        tailleTableVariables = TDS.getInstance().tailleTableVariable();

        for (ArbreAbstrait a : programme) {
            a.verifier() ;
        }
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

        for (ArbreAbstrait a : programme) {
            sb.append(a.toMIPS()) ;
        }
        sb.append(finCode) ;
        return sb.toString() ;
    }

}

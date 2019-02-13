package yal.arbre.instructions;

import yal.arbre.expressions.Expression;

public class Ecrire extends Instruction {

    protected Expression exp ;
    private static int cmpt = 0;
    private int compteur;

    public Ecrire (Expression e, int n) {
        super(n) ;
        exp = e ;
        compteur = cmpt;
        cmpt ++;
    }

    @Override
    public void verifier() {
        exp.verifier();
    }

    @Override
    public String toMIPS() {

        String res = "";

        if (exp.getType().equals("booleen")){

            res =   "# affichage de l'expression (booleen)\n" +
                    exp.toMIPS() +
                    "beqz $v0, alors_affbool_" + compteur + "\n" +
                    "la $a0, vrai \n" +
                    "j fin_affbool_" + compteur + "\n" +
                    "alors_affbool_" + compteur + " :\n" +
                    "la $a0, faux \n" +
                    "fin_affbool_" + compteur + " :\n" +
                    "li $v0, 4\n" +
                    "syscall\n" +
                    "li $v0, 4      # retour à la ligne\n" +
                    "la $a0, finLigne\n" +
                    "syscall\n\n" ;


        }else{

            res =   "# affichage de l'expression (entier)\n" +
                    exp.toMIPS() +
                    "move $a0, $v0\n" +
                    "li $v0, 1\n" +
                    "syscall\n" +
                    "li $v0, 4      # retour à la ligne\n" +
                    "la $a0, finLigne\n" +
                    "syscall\n\n" ;

        }

        return res;



    }

}

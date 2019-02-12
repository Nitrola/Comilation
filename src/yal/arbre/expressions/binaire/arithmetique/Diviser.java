package yal.arbre.expressions.binaire.arithmetique;
import yal.arbre.expressions.Expression;


public class Diviser extends ArithmetiqueBinaire {

    private static int cmpt = 0;
    private int compteur;

    public Diviser(Expression gauche, Expression droite) {
        super(gauche, droite);
        compteur = cmpt;
        cmpt++;
    }

    @Override
    public String operateur() {
        return " / ";
    }

    @Override
    public String toMIPS() {
        StringBuilder div = new StringBuilder(150);

        div.append(super.toMIPS());

        div.append("# la division par 0\n");
        div.append("beqz $v0, alors_div_" + compteur + "\n");


        div.append("div $v0, $t8, $v0\n");
        div.append("j fin_div_" + compteur + "\n");
        div.append("alors_div_" + compteur + " :\n");
        div.append("#l'expression droite est egale a 0\n");
        div.append("li $v0, 4\n");
        div.append("la $a0, divZero\n");
        div.append("syscall\n");
        div.append("j end\n");
        div.append("fin_div_" + compteur + " :\n");

        return div.toString();
    }


    @Override
    public String operation() {
        return " Division ";
    }


}
package yal.arbre.expressions.binaire.logique;
import yal.arbre.expressions.Expression;

public class Ou extends Logique {

    public Ou (Expression gauche, Expression droite) {
        super(gauche, droite);
    }


    @Override
    public String toMIPS() {

        StringBuilder ou = new StringBuilder(100);
        ou.append(super.toMIPS());
        ou.append("or $v0, $t8, $v0\n");

        return ou.toString();
    }
    @Override
    public String operateur() {
        return " ou " ;
    }

    @Override
    public String operation() {
        return " Ou ";
    }

}
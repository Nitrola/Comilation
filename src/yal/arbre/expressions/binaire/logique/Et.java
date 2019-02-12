package yal.arbre.expressions.binaire.logique;

import yal.arbre.expressions.Expression;

public class Et extends Logique {

    public Et(Expression gauche, Expression droite) {
        super(gauche, droite);
    }

    @Override
    public String operateur() {
        return " et " ;
    }

    @Override
    public String operation() {
        return " Et Logique ";
    }

    @Override
    public String toMIPS() {
        StringBuilder et = new StringBuilder(100);

        et.append(super.toMIPS());
        et.append("and $v0, $t8, $v0\n");

        return et.toString();
    }

}
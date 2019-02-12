package yal.arbre.expressions.binaire.arithmetique;


import yal.arbre.expressions.Expression;

public class Plus extends ArithmetiqueBinaire {

    public Plus(Expression gauche, Expression droite) {
        super(gauche, droite);
    }

    @Override
    public String operateur() {
        return " + " ;
    }

    @Override
    public String toMIPS() {
        StringBuilder add = new StringBuilder(100);
        add.append(super.toMIPS());
        add.append("add $v0, $t8, $v0\n");

        return add.toString();
    }


    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public String operation() {
        return " Addition ";
    }

}
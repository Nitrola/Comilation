package yal.arbre.expressions.binaire.arithmetique;
import yal.arbre.expressions.Expression;

public class Multiplier extends ArithmetiqueBinaire {

    public Multiplier(Expression gauche, Expression droite) {
        super(gauche, droite);
    }

    @Override
    public String operateur() {
        return " * ";
    }

    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public String operation() {
        return " Multiplication ";
    }

    @Override
    public String toMIPS() {
        StringBuilder mult = new StringBuilder(100);

        mult.append(super.toMIPS());
        mult.append("mult $v0, $t8\n");

        mult.append("# Résultat $lo -> $v0\n");
        mult.append("mflo $v0\n");

        return mult.toString();
    }

}
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
    public String toMIPS() {
        StringBuilder mult = new StringBuilder(100);
        mult.append(super.toMIPS());
        mult.append("mult $v0, $t8\n");
        mult.append("#Res $lo -> $v0\n");
        mult.append("mflo $v0\n");

        return mult.toString();
    }

    @Override
    public String operation() {
        return " Multiplication ";
    }

}
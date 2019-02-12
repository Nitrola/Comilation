package yal.arbre.expressions.binaire.arithmetique;
import yal.arbre.expressions.Expression;

/**
 * @author Clément Bellanger, Pierre Génard, Valentin Thouvenin
 */
public class Moins extends yal.arbre.expressions.binaire.arithmetique.ArithmetiqueBinaire {

    public Moins(Expression gauche, Expression droite) {
        super(gauche, droite);
    }

    @Override
    public String operateur() {
        return " - ";
    }

    @Override
    public String toMIPS() {
        StringBuilder soustraction = new StringBuilder(100);
        soustraction.append(super.toMIPS());
        soustraction.append("sub $v0, $t8, $v0\n");

        return soustraction.toString();
    }

    @Override
    public String operation() {
        return " Soustraction ";
    }

}
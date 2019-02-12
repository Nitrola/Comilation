package yal.arbre.expressions.binaire.comparaison;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.binaire.Binaire;

public abstract class Comparaison extends Binaire {

    protected Comparaison(Expression gauche, Expression droite) {
        super(gauche, droite);
    }


    @Override
    public String toMIPS() {
        return super.toMIPS();
    }


    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public void verifier() {
        super.verifier();
    }

}
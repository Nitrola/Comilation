package yal.arbre.expressions.unaire;

import yal.arbre.expressions.Expression;

public abstract class Unaire extends Expression {

    protected Expression e;

    public Unaire(Expression exp){
        super(exp.getNoLigne());
        this.e = exp;
    }

    public abstract String operateur();

    @Override
    public void verifier() {
        this.e.verifier();
    }

    @Override
    public String toMIPS() {
        return "#" + operateur() + this.e + "\n" +
                "# Calcul de l'expression\n + " +
                this.e.toMIPS();
    }
}

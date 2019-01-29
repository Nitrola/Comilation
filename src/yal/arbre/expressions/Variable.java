package yal.arbre.expressions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeVariable;
import yal.analyse.symbole.Symbole;

public class Variable extends Expression {

    private String idf;
    private String type;
    private int dep;

    protected Variable(String nidf, int n) {
        super(n);
        this.idf = nidf;

    }


    public String getIdf() {
        return idf;
    }

    public String getType() {
        return type;
    }

    public int getDep() {
        return dep;
    }


    @Override
    public void verifier() {
        EntreeVariable e = new EntreeVariable(idf);
        Symbole s = TDS.getInstance().identifier(e);

    }

    @Override
    public String toMIPS() {

        return "lw $v0, " +
                dep + "($s7)";
    }
}

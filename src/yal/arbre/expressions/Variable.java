package yal.arbre.expressions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeVariable;
import yal.analyse.symbole.Symbole;
import yal.exceptions.AnalyseSemantiqueException;

public class Variable extends Expression {

    private String idf;
    private String type;
    private int dep;

    public Variable(String nidf, int n) {

        super(n);
        this.idf = nidf;

    }


    public String getIdf() {
        return idf;
    }

    public String getType() {
        return type;
    }

    @Override
    public String operation() {
        return null;
    }

    public int getDep() {
        return dep;
    }


    @Override
    public void verifier() {

        EntreeVariable e = new EntreeVariable(idf);
        Symbole s = TDS.getInstance().identifier(e);
        if(s == null){
            throw new AnalyseSemantiqueException(getNoLigne(), " " + idf +  "n'a pas été déclarée ");
        }
        type = s.getType();
        dep = s.getDep();


    }

    @Override
    public String toMIPS() {
        return "lw $v0, " +
                dep + "($s7)\n";
    }
}

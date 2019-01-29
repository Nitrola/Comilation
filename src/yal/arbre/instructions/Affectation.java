package yal.arbre.instructions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeVariable;
import yal.analyse.symbole.Symbole;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Affectation extends Instruction{

    private String idf;
    private Expression exp;
    private int dep;

    public Affectation(String idf, Expression exp){
        super(exp.getNoLigne());
        this.idf = idf;
        this.exp = exp;
    }

    @Override
    public void verifier(){

        EntreeVariable e = new EntreeVariable(idf);
        Symbole s = TDS.getInstance().identifier(e);

        if (s == null) {
            throw new AnalyseSemantiqueException(exp.getNoLigne(), "la variable  `" + idf + "`"+"n'est pas declaree");
        }

        this.dep = s.getDep();

        exp.verifier();
    }

    @Override
    public String toMIPS(){
        StringBuilder affect = new StringBuilder(50);
        affect.append(exp.toMIPS());
        affect.append("sw $v0, ");
        affect.append(dep);
        affect.append("($s7)\n");
        affect.append("\n");
        return affect.toString();
    }

}

package yal.arbre.instructions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeVariable;
import yal.analyse.symbole.Symbole;
import yal.exceptions.AnalyseSemantiqueException;

public class Lire extends Instruction{

    private String idf;
    private int dep;

    public Lire(String idf,int n){
        super(n);
        this.idf = idf;
    }

    @Override
    public void verifier() {

        EntreeVariable e = new EntreeVariable(idf);
        Symbole s = TDS.getInstance().identifier(e);

        if (s == null) {
            throw new AnalyseSemantiqueException(getNoLigne(), "la variable `" + idf + "`"+"n'est pas declaree");
        }

        this.dep = s.getDep();
    }

    @Override
    public String toMIPS() {
        StringBuilder lire = new StringBuilder();
        lire.append("# Lecture d'un entier\n");
        lire.append("li $v0, 5\n");
        lire.append("syscall\n");

        lire.append("#affecte a la variable, la valeur entiere lue\n");
        lire.append("sw $v0, ");
        lire.append(dep);
        lire.append("($s7)\n");
        lire.append("\n");

        return lire.toString();
    }
}

package yal.analyse.symbole;

import yal.analyse.TDS;

public class SymboleVariable  extends Symbole{

    public SymboleVariable(String ntype) {
        super(ntype);
        dep = - TDS.getInstance().tailleTableVariable();
        space = 4;
    }

    public boolean isVar() {
        return true;
    }
}

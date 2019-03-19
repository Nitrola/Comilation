package yal.analyse.symbole;

import yal.analyse.TDS;

public class SymboleParam extends Symbole {

    public SymboleParam(String ntype) {
        super(ntype);
        dep = 16 + TDS.getInstance().tailleTableParam();
        space = 4;
    }

    @Override
    public boolean isParam() {
        return true;
    }
}

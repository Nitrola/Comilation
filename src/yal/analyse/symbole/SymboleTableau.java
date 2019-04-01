package yal.analyse.symbole;

import yal.analyse.TDS;

public class SymboleTableau extends Symbole {

    public SymboleTableau() {
        super("tab");
        dep = -TDS.getInstance().tailleTableVariable();
        space = 4;
    }

    public boolean isVar() {
        return true;
    }
}

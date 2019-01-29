package yal.analyse.symbole;

import yal.analyse.TDS;

public abstract class Symbole {
    private String type;
    private int dep;

    public Symbole(String ntype){
        this.type = ntype;
        this.dep = - TDS.getInstance().tailleTableVariable();
    }

    public String getType() {
        return type;
    }

    public int getDep() {
        return dep;
    }
}

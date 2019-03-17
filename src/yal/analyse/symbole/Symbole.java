package yal.analyse.symbole;

import yal.analyse.TDS;

public abstract class Symbole {

    protected String type;
    protected int dep;
    protected int space;
    protected int idRegion;
    protected int boxingLevel;

    public Symbole(String ntype){
        this.type = ntype;
        this.dep = - TDS.getInstance().tailleTableVariable();
        this.idRegion = TDS.getInstance().getIdRegion();
        this.boxingLevel = TDS.getInstance().getIdBoxing();
    }

    public String getType() {
        return type;
    }

    public int getDep() {
        return dep;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public int getIdBoxing() {
        return boxingLevel;
    }

}

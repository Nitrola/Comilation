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
    }

    public String getType() {
        return type;
    }

    public int getDep() {
        return dep;
    }

    @Override
    public String toString() {
        return "Symbole{" +
                "type='" + type + '\'' +
                ", dep=" + dep +
                '}';
    }
}

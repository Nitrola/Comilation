package yal.analyse.symbole;


import java.util.ArrayList;

public class SymboleFonction extends Symbole {

    private String label;
    private static int cmpt;
    private int compteur;
    private ArrayList<String> paramTypes;

    public SymboleFonction(String ntype) {
        super(ntype);
        this.dep = 0;
        space = 0;
        compteur = cmpt;
        cmpt ++;
        label = makeLabel();
        this.paramTypes = new ArrayList<>();
    }
    public SymboleFonction(String ntype, ArrayList<String> types) {
        super(ntype);
        this.dep = 0;
        space = 0;
        compteur = cmpt;
        cmpt ++;
        label = makeLabel();
        this.paramTypes = types;
    }


    public String makeLabel(){
        return "fonction" + compteur;
    }

    public String getLabel() {
        return label;
    }

    public String getTypeRetour(){
        return getType();
    }

    public ArrayList<String> getParamTypes(){
        return paramTypes;
    }
}

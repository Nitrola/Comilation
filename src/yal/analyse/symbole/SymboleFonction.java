package yal.analyse.symbole;


public class SymboleFonction extends Symbole {

    private String label;
    private static int cmpt;
    private int compteur;
    public SymboleFonction(String ntype) {
        super(ntype);
        this.dep =0;
        space = 0;
        compteur = cmpt;
        cmpt ++;
        label = makeLabel();
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
}

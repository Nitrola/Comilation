package yal.analyse;

import yal.analyse.entree.Entree;
import yal.analyse.symbole.Symbole;
import yal.exceptions.AnalyseSemantiqueException;
import yal.exceptions.AnalyseSyntaxiqueException;

import java.util.HashMap;

public class TDS {

    private static TDS instance  = new TDS();
    private HashMap<Entree,Symbole> tab;

    private TDS(){
        tab = new HashMap<Entree, Symbole>();

    }

    public static TDS getInstance() {
        return instance;
    }

    public int tailleTableVariable(){
        return tab.size() *4;
    }

    public void ajouter(Entree e, Symbole s){
        if(tab.containsKey(e)){
            throw new AnalyseSyntaxiqueException("double déclaration");
        }
        tab.put(e,s);
    }
    public Symbole identifier(Entree e){
        return tab.get(e);
    }
}

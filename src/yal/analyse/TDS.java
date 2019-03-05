package yal.analyse;

import yal.analyse.entree.Entree;
import yal.analyse.symbole.Symbole;
import yal.exceptions.AnalyseSemantiqueException;
import yal.exceptions.AnalyseSyntaxiqueException;

import java.util.HashMap;

public class TDS {

    private static TDS instance  = new TDS();
    private HashMap<Entree,Symbole> tab;


    private int idRegion;
    private int idBoxing;

    private TDS(){
        tab = new HashMap<>();
        idRegion = -1;
        idBoxing =-1;
    }

    public static TDS getInstance() {
        return instance;
    }

    public int tailleTableVariable(){
        return tab.size() * 4;
    }

    public void ajouter(Entree e, Symbole s, int noLigne){

        if(tab.containsKey(e)){
            throw new AnalyseSemantiqueException(noLigne,"double déclaration");
        }
        tab.put(e,s);
    }
    public Symbole identifier(Entree e){

        return tab.get(e);
    }
    public void entreeBloc(){
        idRegion++;
        idBoxing++;
    }
    public void sortieBloc(){
        idBoxing--;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public int getIdBoxing() {
        return idBoxing;
    }
    public int nbVariable(){
        return tab.size();
    }
    public int memorySizeVar(){
        return tab.size() *4;
    }
}


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
    private boolean Syntaxique;

    private Bloc mainBloc;
    private Bloc currentBloc;

    private TDS(){
        mainBloc = null;
        currentBloc = null;
        idRegion = -1;
        idBoxing = -1;
        Syntaxique = true;
    }

    public void setup() {
        Syntaxique = false;
        idRegion = -1;
        idBoxing = -1;

    }

    public static TDS getInstance() {
        return instance;
    }

    public int tailleTableVariable(){
        return currentBloc.tailleTableVariable();
    }

    public int tailleTableParam() {
        return currentBloc.tailleTableParam();
    }

    public void ajouter(Entree e, Symbole s, int noLigne){

        currentBloc.ajouter(e, s, noLigne);
    }

    public Symbole identifier(Entree e){

        return currentBloc.identifier(e);
    }

    public void entreeBloc(){
        idRegion++;
        idBoxing++;

        if(Syntaxique) {
            if (idRegion == 0) {

                Bloc b = new Bloc(idRegion);
                mainBloc = b;
                currentBloc = b;

            } else {

                Bloc nb = new Bloc(idRegion, currentBloc);
                currentBloc.ajouterSuivant(nb);
                currentBloc = nb;

            }
        }else{
            if (idRegion == 0) {

                currentBloc = mainBloc;

            } else {

                Bloc bf = currentBloc.recupSuivant(idRegion);
                currentBloc = bf;

            }
        }
    }

    public void sortieBloc(){

        Bloc b = currentBloc.getBlocPrecedent();
        currentBloc = b;
        idBoxing--;
    }

    public int getIdRegion() {
        return currentBloc.getIdRegion();
    }

    public int idPrevious() {
        Bloc b = currentBloc.getBlocPrecedent();
        if(b != null) {
            return b.getIdRegion();
        }

        return -1;

    }

    public int varCount() {
        return currentBloc.varCount();
    }

    public int parCount() { return currentBloc.parCount();}

    public int getIdBoxing() {
        return idBoxing;
    }

    public int nbVariable(){
        return tab.size();
    }

}


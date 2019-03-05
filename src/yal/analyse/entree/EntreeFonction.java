package yal.analyse.entree;

public class EntreeFonction  extends Entree{
    private int nbParameters;


    public EntreeFonction(String nidf, int nb) {
        super(nidf);
        this.nbParameters = nb;
    }
    public int getNbParameters(){
        return  this.nbParameters;
    }

}

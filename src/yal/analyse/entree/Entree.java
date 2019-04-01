package yal.analyse.entree;

import java.util.Objects;

public abstract class Entree {

    private String idf;
    private int nbParam;

    public Entree(String nidf){
        this.idf = nidf;
        this.nbParam = 0;
    }

    public Entree(String nidf, int nbParam) {
        this.idf = nidf;
        this.nbParam = nbParam;
    }


    @Override
    public String toString() {
        return idf;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entree entree = (Entree) o;
        return nbParam == entree.nbParam &&
                Objects.equals(idf, entree.idf);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idf, nbParam);
    }
}

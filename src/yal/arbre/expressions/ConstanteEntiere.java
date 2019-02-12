package yal.arbre.expressions;

public class ConstanteEntiere extends Constante {
    
    public ConstanteEntiere(String texte, int n) {
        super(texte, n) ;
    }

    @Override
    public String constante() {
        return cste;
    }


    @Override
    public String getType() {
        return null;
    }

    @Override
    public String operation() {
        return null;
    }

    @Override
    public String toMIPS() {
        return super.toMIPS();
    }
}

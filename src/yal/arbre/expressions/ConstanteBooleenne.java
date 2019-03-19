package yal.arbre.expressions;

public class ConstanteBooleenne extends Constante {

    public ConstanteBooleenne(String texte, int n) {
        super(texte, n) ;
    }

    @Override
    public String operation() {
        return " Const Bool ";
    }

    @Override
    public String constante() {
        if (cste.equals("vrai")) {
            return "1";
        }
        else {
            return "0";
        }
    }

    @Override
    public String toMIPS() {
        return super.toMIPS();
    }

    @Override
    public String getType() {
        return "booleen";
    }

}
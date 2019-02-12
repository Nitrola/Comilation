package yal.arbre.expressions;

public abstract class Constante extends Expression {

    protected String cste ;
    
    protected Constante(String texte, int n) {
        super(n) ;
        cste = texte ;
    }
    
    @Override
    public void verifier() {
    }

    @Override
    public String toString() {
        return cste ;
    }

    public abstract String constante();

    @Override
    public String toMIPS() {
        StringBuilder constante = new StringBuilder(50);
        constante.append("#" + operation() + "\n");
        constante.append("# On met la constante dans $v0\n");
        constante.append("li $v0, " + constante() + "\n");
        return constante.toString();
    }

}

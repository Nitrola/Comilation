package yal.arbre.expressions.binaire;
import yal.arbre.expressions.Expression;

public abstract class Binaire extends Expression {

    protected Expression g;
    protected Expression d;


    protected Binaire(Expression gauche, Expression droite) {
        super(gauche.getNoLigne());
        this.g = gauche;
        this.d = droite;
    }

    public abstract String operateur();

    @Override
    public void verifier() {
        g.verifier();
        d.verifier();
    }

    @Override
    public String toMIPS() {

        return "#" + operation() + "\n" +
                "#partie gauche\n" +
                g.toMIPS() + "\n" +
                "#mise dans la pile de la partie gauche\n " +
                "sw $v0, 0($sp)\n" +
                "add $sp, $sp, -4\n" +
                "#partie droite \n" +
                d.toMIPS() + "\n" +
                "#depilage de la partie gauche \n" +
                "add $sp, $sp, 4\n" +
                "lw $t8, 0($sp)\n" +
                "#" + operation() + "de $v0 et $t8\n"
                ;
    }
}
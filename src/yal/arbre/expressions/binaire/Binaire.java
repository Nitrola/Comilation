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
        StringBuilder mips = new StringBuilder(100);

        mips.append("#" + operation() + "\n");
        mips.append("# calcul partie gauche\n");
        mips.append(g.toMIPS());
        mips.append("\n");
        mips.append("# empile partie gauche\n");
        mips.append("sw $v0, 0($sp)\n");
        mips.append("add $sp, $sp, -4\n");
        mips.append("\n");
        mips.append("# calcul partie droite\n");
        mips.append(d.toMIPS());
        mips.append("\n");
        mips.append("# depile partie gauche\n");
        mips.append("add $sp, $sp, 4\n");
        mips.append("lw $t8, 0($sp)\n");
        mips.append("\n");
        mips.append("#" + operation() + "entre $v0 et $t8\n");

        return mips.toString();
    }

    @Override
    public String toString()
    {
        return "("+g+operateur()+d+ ")" ;
    }

}
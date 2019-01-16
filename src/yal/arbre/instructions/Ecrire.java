package yal.arbre.instructions;

import yal.arbre.expressions.Expression;

public class Ecrire extends Instruction {

    protected Expression exp ;

    public Ecrire (Expression e, int n) {
        super(n) ;
        exp = e ;
    }

    @Override
    public void verifier() {
    }

    @Override
    public String toMIPS() {
        StringBuilder res = new StringBuilder("");
        res.append("li $v0, 1\n");
        res.append("li $a0" + exp.toMIPS() + "\n");
        res.append("syscall\n");
        return res.toString();
    }

}

package yal.arbre.instructions;

import yal.arbre.ArbreAbstrait;

public abstract class Instruction extends ArbreAbstrait {

    protected Instruction(int n) {
        super(n);
    }

    public boolean isReturn(){
        return false;
    }

}

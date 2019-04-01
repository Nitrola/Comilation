package yal.arbre.expressions;
import yal.arbre.ArbreAbstrait;

public abstract class Expression extends ArbreAbstrait {
    
    protected Expression(int n) {
        super(n) ;
    }

    public boolean isCste() {
        return false;
    }

    public abstract String getType();
    public abstract String operation();

}

package yal.arbre.expressions.unaire;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.binaire.logique.Logique;
import yal.exceptions.AnalyseSemantiqueException;

public class Non extends Unaire {


    public Non(Expression exp) {
        super(exp);
    }

    @Override
    public void verifier() {
        super.verifier();
        if(!this.e.getType().equals("entier")){
            throw new AnalyseSemantiqueException(getNoLigne(),"erreure:" + operateur() + this.e + "\n" + "l'opérande doit être un entier");
        }
    }

    @Override
    public String toMIPS() {
        return super.toMIPS() +
               "# XOR $v0 et 1 " +
                "xori $v0, $v0, 1\n";
    }

    @Override
    public String operateur() {
        return "non ";
    }

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public String operation() {
        return "Non";
    }
}

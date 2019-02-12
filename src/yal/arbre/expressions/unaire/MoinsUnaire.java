package yal.arbre.expressions.unaire;

import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class MoinsUnaire extends Unaire {

    public MoinsUnaire(Expression e){
        super(e);
    }

    @Override
    public String operateur() {
        return "- ";
    }

    @Override
    public String operation() {
        return "MoinsUnaire";
    }

    @Override
    public void verifier() {
        super.verifier();
        if(!this.e.getType().equals("entier")){
            throw new AnalyseSemantiqueException(getNoLigne(),"erreure:" + operateur() + this.e + "\n" + "l'opérande doit être un entier");
        }
    }
    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public String toMIPS() {
        return super.toMIPS() +
                "#" + operation() + "sub $v0, 0, $v0\n"+
                "neg $v0, $v0\n";
    }
}

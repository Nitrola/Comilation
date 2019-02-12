package yal.arbre.instructions;

import yal.arbre.BlocDInstructions;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Boucle extends  Instruction{
    private Expression e;
    private BlocDInstructions inst;

    public Boucle(Expression exp , BlocDInstructions b){
        super(exp.getNoLigne());
        this.e = exp;
        this.inst = b;
    }

    @Override
    public void verifier() {
        // vérification que la condition est un booléen
        if(!this.e.getType().equals("bool")){
            throw new AnalyseSemantiqueException(getNoLigne(),new String("erreur type:\t"+ e + "\n" + "une expression évaluée pour une boucle doit être booléenne" ));
        }
        inst.verifier();

    }

    @Override
    public String toMIPS() {
        String s = new String();
        s += "#Boucle\n tantque: \n";
        s += e.toMIPS();
        s += "beqz $v0,finTantque\n";
        s += " iteration\n";
        s += "j tantque\n";
        s += "finTantque\n";
        return s;
    }
}

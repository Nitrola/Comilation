package yal.arbre.instructions;

import yal.arbre.BlocDInstructions;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Boucle extends  Instruction{
    private Expression e;
    private BlocDInstructions inst;
    private static int compteur = 0;

    public Boucle(Expression exp , BlocDInstructions b){
        super(exp.getNoLigne());
        this.e = exp;
        this.inst = b;
        compteur++;
    }

    @Override
    public void verifier() {
        // vérification que la condition est un booléen
        if(!this.e.getType().equals("booleen")){
            throw new AnalyseSemantiqueException(getNoLigne(),new String("erreur type:\t"+ e + "\n" + "une expression évaluée pour une boucle doit être booléenne" ));
        }
        inst.verifier();

    }

    @Override
    public String toMIPS() {
        String s = new String();
        s += "#Boucle\n tantque_" + compteur + ": \n";
        s += e.toMIPS();
        s += "beqz $v0,finTantque_" + compteur + "\n";
        s += "iteration_" + compteur + " :\n";
        s += "j tantque_" + compteur + "\n";
        s += "finTantque_" + compteur + " :\n";
        return s;
    }
}

package yal.arbre.instructions;

import yal.arbre.BlocDInstructions;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Condition extends Instruction {

    private Expression e;
    private BlocDInstructions alors;
    private BlocDInstructions sinon;

    /***
     * Création d'une condition de type
     * SI EXP
     * @param exp
     */
    public Condition(Expression exp){
        super(exp.getNoLigne());
        this.e = exp;
        alors = new BlocDInstructions(noLigne +1);
        sinon = new BlocDInstructions(noLigne +1);
    }

    /**
     * Création d'une condition avec SI EXP
     * suivis d'un SINON ou d'un ALORS
     * typeBloc == 0 pour alors
     * typeBloc != 0 pour sinon
     * @param exp
     * @param b
     * @param typeBloc
     */
    public Condition(Expression exp, BlocDInstructions b, int typeBloc){
        super(exp.getNoLigne());
        this.e = exp;
        if(typeBloc == 0){
            alors = b;
            sinon = new BlocDInstructions(noLigne +1);
        }
        else{
            alors = new BlocDInstructions(noLigne +1);
            sinon = b;
        }
    }

    /**
     * Création d'une condition de type
     * SI  EXP ALORS SINON
     * @param exp
     * @param alors
     * @param sinon
     */
    public Condition(Expression exp, BlocDInstructions alors, BlocDInstructions sinon){
        super(exp.getNoLigne());
        this.e = exp;
        this.alors = alors;
        this.sinon = sinon;

    }
    @Override
    public void verifier() {
        // vérification que la condition est un booléen
        if(!this.e.getType().equals("bool")){
            throw new AnalyseSemantiqueException(getNoLigne(),
                    new String("erreur type:\t"+ e + "\n" + "une expression évaluée pour une boucle doit être booléenne"));
        }
        alors.verifier();
        sinon.verifier();

    }

    @Override
    public String toMIPS() {
        String s = new String();
        s += "#Condition\n si: \n";
        s += e.toMIPS();
        s += "beqz $v0,sinon\n";
        s += " alors\n";
        s += alors.toMIPS();
        s += "j fin\n";
        s += "fin\n";
        s += "sinon\n";
        s += sinon.toMIPS();
        s += "fin\n";
        return s;
    }
}
package yal.arbre.instructions;

import yal.arbre.BlocDInstructions;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Condition extends Instruction {

    private Expression e;
    private BlocDInstructions alors;
    private BlocDInstructions sinon;

    private boolean isSinon;

    private static int cmpt = 0;
    private int compteur;
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
        this.isSinon = false;
        this.compteur = cmpt;
        cmpt++;
    }

    /**
     * Création d'une condition avec SI EXP
     * suivis d'un SINON ou d'un ALORS
     * typeBloc == 0 pour alors
     * typeBloc != 0 pour sinon
     * @param exp
     * @param b" + compteur + "
     * @param typeBloc
     */
    public Condition(Expression exp, BlocDInstructions b, int typeBloc){
        super(exp.getNoLigne());
        this.e = exp;
        if(typeBloc == 0){
            alors = b;
            sinon = new BlocDInstructions(noLigne +1);
            isSinon = false;
        }
        else {
            alors = new BlocDInstructions(noLigne + 1);
            sinon = b;
            isSinon = true;
        }
        this.compteur = cmpt;
        cmpt++;
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
        this.isSinon = true;
        this.compteur = cmpt;
        cmpt++;
    }

    @Override
    public boolean isReturn() {
        boolean res = false;

        if(isSinon) {
            res = sinon.isReturn();
        }

        return res && alors.isReturn();
    }


    @Override
    public void verifier() {
        e.verifier();
        // vérification que la condition est un booléen
        if(!this.e.getType().equals("booleen")){
            throw new AnalyseSemantiqueException(getNoLigne(),
                    new String("erreur type:\t"+ e + "\n" + "une expression evaluee pour une boucle doit etre booleenne"));
        }

        alors.verifier();
        sinon.verifier();

    }

    @Override
    public String toMIPS() {
        String s = new String();
        s += "#Condition\n si_" + compteur + " :\n";
        s += e.toMIPS();
        s += "beqz $v0,sinon_" + compteur + "\n";
        s += " alors_" + compteur + " :\n";
        s += alors.toMIPS();
        s += "j fin_" + compteur + "\n";
        s += "sinon_" + compteur + " :\n";
        s += sinon.toMIPS();
        s += "fin_" + compteur + " :\n";
        return s;
    }
}

package yal.arbre.instructions;

import yal.analyse.TDS;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Retourner extends Instruction {

    private Expression exp;
    private int idRegion;

    public Retourner(Expression exp) {

        super(exp.getNoLigne());
        this.exp = exp;

    }

    @Override
    public boolean isReturn(){
        return true;
    }

    @Override
    public void verifier() {
        this.exp.verifier();
        if(!exp.getType().equals("entier")){
            throw new AnalyseSemantiqueException(getNoLigne(),"erreur type :\n return" + exp + "\n une fonction doit retourner un entier");
        }
        idRegion = TDS.getInstance().getIdRegion();
    }


    @Override
    public String toMIPS() {

        String mips = "#Retour de fonction\n" +
                      "#Met exp dans $v0\n" +
                      exp.toMIPS() + "\n";

        if(idRegion >0){
            mips = mips +
                    "#retire de la pile les variables\n" +
                    "move $sp, $s6\n" +
                    "lw $s6, 8($sp)\n" +
                    "#Depile l'id de la region & le chainage dynamique & l'adresse de retour \n" +
                    "add $sp, $sp, 12\n" +
                    "lw $ra, 0($sp)\n" + "\n" +
                    "#Sauvegarde la valeur calculé dans $v0\n" +
                    "sw $v0, 4($sp)\n"+"\n" +
                    "#Jump vers le bloc precedent\n" +
                    "jr $ra\n" +
                    "\n";
        }
        else{
            mips = mips + "#Jump à la fin du programme\n" + "j fin\n";
        }
        return mips;
    }
}

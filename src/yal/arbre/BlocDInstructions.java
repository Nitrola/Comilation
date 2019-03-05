package yal.arbre;

import yal.analyse.TDS;
import yal.arbre.instructions.Instruction;

import java.util.ArrayList;

/**
 * 21 novembre 2018
 *
 * @author brigitte wrobel-dautcourt
 */

public class BlocDInstructions extends ArbreAbstrait {
    
    protected ArrayList<Instruction> blocDInstructions ;


    public BlocDInstructions(int n) {
        super(n) ;
        blocDInstructions = new ArrayList<>() ;
    }
    
    public void ajouter(Instruction a) {
        blocDInstructions.add(a) ;
    }
    
    @Override
    public String toString() {
        return blocDInstructions.toString() ;
    }

    @Override
    public void verifier() {

        for (Instruction a : blocDInstructions) {
            a.verifier() ;
        }
    }
    
    @Override
    public String toMIPS() {

        StringBuilder sb = new StringBuilder("");

        for (Instruction a : blocDInstructions) {
            sb.append(a.toMIPS()) ;
            sb.append("\n");
        }

        return sb.toString() ;
    }

    public boolean isReturn(){
        boolean res = false;
        for(Instruction inst : blocDInstructions){
            res = inst.isReturn();
        }
        return res;
    }
}

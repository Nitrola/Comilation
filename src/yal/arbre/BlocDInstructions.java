package yal.arbre;

import yal.arbre.instructions.Instruction;

import java.util.ArrayList;
import java.util.Iterator;

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

    public void ajouterAvant(ArrayList<Instruction> a) {

        for(int i = a.size() - 1; i >= 0; i--) {
            blocDInstructions.add(0, a.get(i));
        }

    }

    public int nbInst() {
        return blocDInstructions.size();
    }

    public Instruction get(int i) {
        return blocDInstructions.get(i);
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
            if(res) {
                return true;
            }
        }

//        Iterator<Instruction> parcoursBloc = blocDInstructions.iterator();
//
//        while (!res && parcoursBloc.hasNext()) {
//            Instruction inst = parcoursBloc.next();
//
//            res = inst.isReturn();
//        }

        return false;
    }
}

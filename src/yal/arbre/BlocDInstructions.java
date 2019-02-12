package yal.arbre;

import yal.analyse.TDS;

import java.util.ArrayList;

/**
 * 21 novembre 2018
 *
 * @author brigitte wrobel-dautcourt
 */

public class BlocDInstructions extends ArbreAbstrait {
    
    protected ArrayList<ArbreAbstrait> blocDInstructions ;


    public BlocDInstructions(int n) {
        super(n) ;
        blocDInstructions = new ArrayList<>() ;
    }
    
    public void ajouter(ArbreAbstrait a) {
        blocDInstructions.add(a) ;
    }
    
    @Override
    public String toString() {
        return blocDInstructions.toString() ;
    }

    @Override
    public void verifier() {

        for (ArbreAbstrait a : blocDInstructions) {
            a.verifier() ;
        }
    }
    
    @Override
    public String toMIPS() {

        StringBuilder sb = new StringBuilder("");

        for (ArbreAbstrait a : blocDInstructions) {
            sb.append(a.toMIPS()) ;
            sb.append("\n");
        }

        return sb.toString() ;
    }

}

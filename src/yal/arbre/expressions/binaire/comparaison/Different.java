package yal.arbre.expressions.binaire.comparaison;

import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Different extends Comparaison {

    public Different(Expression gauche, Expression droite) {
        super(gauche, droite);
    }

    @Override
    public void verifier() throws AnalyseSemantiqueException {
        super.verifier();

        if (!g.getType().equals(d.getType())) {

            StringBuilder erreur = new StringBuilder(50);
            erreur.append("erreur de type :\t");
            erreur.append(g);
            erreur.append(operateur());
            erreur.append(d);
            erreur.append("\n\tles expressions a gauche et a droite doivent être de meme type");

            throw new AnalyseSemantiqueException(getNoLigne(), erreur.toString());
        }
    }

    @Override
    public String toMIPS() {

        StringBuilder diff = new StringBuilder(40);
        diff.append(super.toMIPS());
        diff.append("#différent -> on met 1 dans $v0, sinon 0\n");
        diff.append("sne $v0, $v0, $t8\n");

        return diff.toString();
    }


    @Override
    public String operateur() {
        return " != ";
    }

    @Override
    public String operation() {
        return " Différent ";
    }

}
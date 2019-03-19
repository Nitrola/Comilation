package yal.arbre.expressions.binaire.logique;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.binaire.Binaire;
import yal.exceptions.AnalyseSemantiqueException;

public abstract class Logique extends Binaire {

    protected Logique(Expression gauche, Expression droite) {
        super(gauche, droite) ;
    }

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public void verifier() {
        super.verifier();

        if (!g.getType().equals("booleen") || !d.getType().equals("booleen")) {
            StringBuilder erreur = new StringBuilder(40);

            erreur.append("erreur de type :\t");
            erreur.append(g);
            erreur.append(operateur());
            erreur.append(d);
            erreur.append("\n\t");
            erreur.append("les deux opérandes doivent être des booléens");

            throw new AnalyseSemantiqueException(getNoLigne(), erreur.toString());
        }
    }

    @Override
    public String toMIPS() {
        return super.toMIPS();
    }

}
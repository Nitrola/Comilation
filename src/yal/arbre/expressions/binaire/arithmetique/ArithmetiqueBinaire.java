package yal.arbre.expressions.binaire.arithmetique;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.binaire.Binaire;
import yal.exceptions.AnalyseSemantiqueException;

public abstract class ArithmetiqueBinaire extends Binaire {

    protected ArithmetiqueBinaire(Expression gauche, Expression droite) {
        super(gauche, droite);
    }

    @Override
    public void verifier() throws AnalyseSemantiqueException {
        super.verifier();


        if (!gauche.getType().equals("entier") || !droite.getType().equals("entier")) {

            StringBuilder erreur = new StringBuilder(50);
            erreur.append("erreur type:\t");
            erreur.append(gauche);
            erreur.append(operateur());
            erreur.append(droite);
            erreur.append("\n\t");

            erreur.append("les deux expressions doivent etre des entieres");

            throw new AnalyseSemantiqueException(getNoLigne(), erreur.toString());
        }
    }

    @Override
    public String toMIPS() {
        return super.toMIPS();
    }

}
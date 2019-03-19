package yal.arbre.expressions.binaire.comparaison;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Superieur extends Comparaison {

    public Superieur(Expression gauche, Expression droite) {
        super(gauche, droite);
    }


    @Override
    public void verifier() {
        super.verifier();

        if (!g.getType().equals("entier") || !d.getType().equals("entier")) {

            StringBuilder erreur = new StringBuilder(40);
            erreur.append("erreur de type :\t");
            erreur.append(g);
            erreur.append(operateur());
            erreur.append(d);
            erreur.append("\n\t");
            erreur.append("les deux expressions doivent être des entiers");

            throw new AnalyseSemantiqueException(getNoLigne(), erreur.toString());
        }
    }

    @Override
    public String toMIPS() {
        StringBuilder sup = new StringBuilder(100);

        sup.append(super.toMIPS());
        sup.append("#partie gauche est supérieure à la droite, on met 1 dans $v0, sinon 0\n");
        sup.append("sgt $v0, $t8, $v0\n");

        return sup.toString();
    }

    @Override
    public String operateur() {
        return " > ";
    }

    @Override
    public String operation() {
        return " Supérieur ";
    }

}
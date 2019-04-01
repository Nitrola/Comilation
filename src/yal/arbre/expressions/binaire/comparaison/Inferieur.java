package yal.arbre.expressions.binaire.comparaison;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Inferieur extends Comparaison {

    public Inferieur(Expression gauche, Expression droite) {
        super(gauche, droite);
    }

    @Override
    public void verifier() {
        super.verifier();

        if (!g.getType().equals(d.getType())) {

            StringBuilder erreur = new StringBuilder(40);
            erreur.append("erreur de type :\t");
            erreur.append(g);
            erreur.append(operateur());
            erreur.append(d);
            erreur.append("\n\t");
            erreur.append("les deux expressions doivent être de même type");

            throw new AnalyseSemantiqueException(getNoLigne(), erreur.toString());
        }
    }

    @Override
    public String toMIPS() {
        StringBuilder sb = new StringBuilder(100);

        sb.append(super.toMIPS());
        sb.append("#partie gauche est inférieure à la droite, on met 1 dans $v0, sinon 0\n");
        sb.append("slt $v0, $t8, $v0\n");

        return sb.toString();
    }


    @Override
    public String operateur() {
        return " < ";
    }

    @Override
    public String operation() {
        return " Inférieur ";
    }

}
package yal.arbre.expressions.binaire.comparaison;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class Egalite extends Comparaison {

    public Egalite(Expression gauche, Expression droite) {
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
            erreur.append("\n\tles expressions a gauche et a droite doivent être de même type");

            throw new AnalyseSemantiqueException(getNoLigne(), erreur.toString());
        }
    }

    @Override
    public String toMIPS() {
        StringBuilder egal = new StringBuilder(100);

        egal.append(super.toMIPS());
        egal.append("#egalité -> on met 1 dans $v0, sinon 0\n");
        egal.append("seq $v0, $v0, $t8\n");

        return egal.toString();
    }


    @Override
    public String operateur() {
        return " == ";
    }

    @Override
    public String operation() {
        return " Egalite ";
    }
}
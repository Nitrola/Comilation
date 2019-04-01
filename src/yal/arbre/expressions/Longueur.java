package yal.arbre.expressions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeVariable;
import yal.analyse.symbole.Symbole;
import yal.exceptions.AnalyseSemantiqueException;

public class Longueur extends Expression {

    private String idf;
    private int dep;
    private int idRegion;
    private static int cmpt= 0;
    private int compteur;

    public Longueur(String nidf, int n) {
        super(n);
        this.idf = nidf;
        this.compteur = cmpt;
        cmpt++;
    }

    @Override
    public String operation() {
        return " Longueur ";
    }

    @Override
    public String getType() {
        return "entier";
    }


    @Override
    public void verifier() {
        EntreeVariable e = new EntreeVariable(idf);
        Symbole s = TDS.getInstance().identifier(e);

        if(s == null){
            throw new AnalyseSemantiqueException(getNoLigne(), " " + idf +  "n'a pas été déclarée ");
        }

        if(!(s.getType().equals("tab"))) {
            throw new AnalyseSemantiqueException(getNoLigne(), "erreur :\t " + idf + ".longueur\n\t " + idf + " n'est pas un tableau");
        }

        dep = s.getDep();
        idRegion = s.getIdRegion();
    }

    @Override
    public String toMIPS() {

        String mips = "#positionnement de la longueur du tableau dans v0\n" +

                "#On recupere la base\n" +
                "move $t5, $s7\n" +

                "#on récupere le numéro de région du tableau\n" +
                "li $v1, " + idRegion + "\n" +

                "#début du tantque\n" +
                "tantquelongueur_" + compteur + " :\n" +

                "#on prend le numéro de région actuel\n" +
                "lw $v0, 4($t5)\n" +
                "sub $v0, $v0, $v1\n" +

                "#on va a la fin si les numéros correspondent\n" +
                "beqz $v0, fintantquelongueur_" + compteur + "\n" +

                "#on essaye avec le numéro de région précédent sinon\n" +
                "lw $t5, 8($t5) \n" +
                "j tantquelongueur_" + compteur + "\n" +

                "#sortie du tantque\n" +
                "fintantquelongueur_" + compteur + " :\n\n" +

                "#on charge l'adresse du tab dans $t4\n" +
                "lw $t4, " + dep + "($t5)\n" +

                "#chargement classique de la longueur dans v0\n" +
                "lw $v0, 0($t4)\n" ;

        return mips;

    }
}

package yal.arbre.expressions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeVariable;
import yal.analyse.symbole.Symbole;
import yal.exceptions.AnalyseSemantiqueException;

public class Variable extends Expression {

    private String idf;
    private String type;
    private int dep;
    private int idRegion;
    private static int cmpt= 0;
    private int compteur;

    public Variable(String nidf, int n) {

        super(n);
        this.idf = nidf;
        this.compteur = cmpt;
        cmpt++;

    }


    public String getIdf() {
        return idf;
    }

    public String getType() {
        return type;
    }

    @Override
    public String operation() {
        return " Variable ";
    }

    public int getDep() {
        return dep;
    }


    @Override
    public void verifier() {

        EntreeVariable e = new EntreeVariable(idf);
        Symbole s = TDS.getInstance().identifier(e);
        if(s == null){
            throw new AnalyseSemantiqueException(getNoLigne(), " " + idf +  "n'a pas été déclarée ");
        }
        type = s.getType();
        dep = s.getDep();
        idRegion = s.getIdRegion();


    }


    @Override
    public String toMIPS() {

    String mips = "#positionnement d'une variable dans v0\n" +

            "#On recupere la base\n" +
            "move $t5, $s7\n" +

            "#on récupere le numéro de région de la variable\n" +
            "li $v1, " + idRegion + "\n" +

            "#début du tantque\n" +
            "tantquevariable_" + compteur + " :\n" +

            "#on prend le numéro de région courant\n" +
            "lw $v0, 4($t5)\n" +
            "sub $v0, $v0, $v1\n" +

            "#on va a la fin si les numéros correspondent\n" +
            "beqz $v0, fintantquevariable_" + compteur + "\n" +

            "#on essaye avec le numéro de région précédent sinon\n" +
            "lw $t5, 8($t5) \n" +
            "j tantquevariable_" + compteur + "\n" +

            "#sortie du tantque\n" +
            "fintantquevariable_" + compteur + " :\n\n" +

            "#chargement classique dans $v0\n" +
            "lw $v0, " + dep + "($t5)\n";

        return mips;
    }
}
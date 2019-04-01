package yal.arbre.expressions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeVariable;
import yal.analyse.symbole.Symbole;
import yal.exceptions.AnalyseSemantiqueException;

public class VariableTableau extends Expression {

    private String idf;
    private Expression indice;
    private String type;
    private int dep;
    private int idRegion;
    private static int cmpt= 0;
    private int compteur;

    public VariableTableau(String nidf, Expression indice, int n) {

        super(n);
        this.idf = nidf;
        this.indice = indice;
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
        return " VariableTableau ";
    }


    public int getDep() {
        return dep;
    }


    @Override
    public void verifier() {
        indice.verifier();

        if(!(indice.getType().equals("entier"))) {
            throw new AnalyseSemantiqueException(getNoLigne(), "erreur type :\t indice " + idf + " \n\t" + indice + " n'est pas entier");
        }

        EntreeVariable e = new EntreeVariable(idf);
        Symbole s = TDS.getInstance().identifier(e);

        if(s == null){
            throw new AnalyseSemantiqueException(getNoLigne(), " " + idf +  "n'a pas été déclarée ");
        }

        if(!(s.getType().equals("tab"))) {
            throw new AnalyseSemantiqueException(getNoLigne(), "erreur :\t " + idf + ".longueur\n\t " + idf + " n'est pas un tableau");
        }

        type = s.getType();
        dep = s.getDep();
        idRegion = s.getIdRegion();


    }


    @Override
    public String toMIPS() {

        String mips = "#positionnement d'une variable de tableau dans v0\n" +

                "#On recupere la base\n" +
                "move $t5, $s7\n" +

                "#on récupere le numéro de région du tableau\n" +
                "li $v1, " + idRegion + "\n" +

                "#début du tantque\n" +
                "tantquevariabletableau_" + compteur + " :\n" +

                "#on prend le numéro de région courant\n" +
                "lw $v0, 4($t5)\n" +
                "sub $v0, $v0, $v1\n" +

                "#on va a la fin si les numéros correspondent\n" +
                "beqz $v0, fintantquevariabletableau_" + compteur + "\n" +

                "#on essaye avec le numéro de région précédent sinon\n" +
                "lw $t5, 8($t5) \n" +
                "j tantquevariabletableau_" + compteur + "\n" +

                "#sortie du tantque\n" +
                "fintantquevariabletableau_" + compteur + " :\n\n" +

                "#chargement de l'adresse du tableau\n" +
                "lw $t4, " + dep + "($t5)\n" +

                indice.toMIPS() +

                "#si acces a un indice plus petit que 0\n" +
                "bltz $v0, erreurAccesTab\n" +

                "#longueur du tab dans t5\n" +
                "lw $t5, 0($t4)\n" +

                "sub $t5, $t5, $v0\n" +

                "#si acces a un indice plus grand que la longueur du tab\n" +
                "blez $t5, erreurAccesTab\n" +

                "#$t2 va avoir le dep pour aller a l'indice voulu\n" +
                "li $t3, -4\n" +
                "mult $v0, $t3\n" +
                "mflo $t2\n" +

                "#on retire la place de la longueur à $t2\n" +
                "add $t2, $t2, -4\n" +

                "#ajout dep de $t2 a $t4\n" +
                "add $t4, $t4, $t2\n" +

                "#chargement case dans $v0\n" +
                "lw $v0, 0($t4)\n";

        return mips;
    }
}
package yal.arbre.instructions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeVariable;
import yal.analyse.symbole.Symbole;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class AffectTab extends Instruction {

    private String idf;
    private Expression exp;
    private Expression indice;
    private int dep;
    private int idRegion;
    private static int cmpt= 0;
    private int compteur;

    public AffectTab(String idf, Expression indice, Expression exp) {
        super(exp.getNoLigne());
        this.idf = idf;
        this.indice = indice;
        this.exp = exp;
        this.compteur = cmpt;
        cmpt++;

    }

    @Override
    public void verifier() {
        exp.verifier();
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

        idRegion = s.getIdRegion();
        dep = s.getDep();



    }

    @Override
    public String toMIPS() {

        String mips = "#Affectation tableau \n" +

                    exp.toMIPS() +

                    "#on empile la valeur qu'on veut mettre dans la variable\n" +
                    "sw $v0, 0($sp)\n" +
                    "add $sp, $sp, -4\n" +

                    "#On recupere la base\n" +
                    "move $t5, $s7\n" +

                    "#on récupere le numéro de région du tableau\n" +
                    "li $v1, " + idRegion + "\n" +

                    "#début tantque\n" +
                    "tantqueaffecttab_" + compteur + " :\n" +

                    "#on prend le numéro de région courant\n" +
                    "lw $v0, 4($t5) \n" +
                    "sub $v0, $v0, $v1\n" +

                    "#on va a la fin si les numéros correspondent\n" +
                    "beqz $v0, fintantqueaffecttab_" + compteur + "\n" +

                    "#on essaye avec le numéro de région précédent sinon\n" +
                    "lw $t5, 8($t5) \n" +
                    "j tantqueaffecttab_" + compteur + "\n" +

                    "#sortie du tantque\n" +
                    "fintantqueaffecttab_" + compteur + " :\n\n" +

                    "#chargement adresse tab\n" +
                    "lw $t2, " + dep + "($t5)\n" +

                    "#on met l'indice dans $v0\n" +
                    indice.toMIPS() +

                    "#si indice de tableau inférieur a 0\n" +
                    "bltz $v0, erreurAccesTab\n" +

                    "#longueur du tableau\n" +
                    "lw $t5, 0($t2)\n" +

                    "#longueur moins l'indice\n" +
                    "sub $t5, $t5, $v0\n" +

                    "#si indice supérieur a la longueur du tableau\n" +
                    "blez $t5, erreurAccesTab\n" +

                    "li $t3, -4\n" +
                    "mult $v0, $t3\n" +
                    "mflo $t4\n" +

                    "#on retire la place de la longueur a $t4\n" +
                    "add $t4, $t4, -4\n" +

                    "#on depile la valeur a mettre dans la variable\n" +
                    "add $sp, $sp, 4\n" +
                    "lw $v0, 0($sp)\n" +

                    "add $t2, $t2, $t4\n" +

                    "sw $v0, 0($t2)\n";

        return mips;


    }
}

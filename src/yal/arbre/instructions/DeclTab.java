package yal.arbre.instructions;

import yal.analyse.TDS;
import yal.analyse.entree.EntreeVariable;
import yal.analyse.symbole.Symbole;
import yal.arbre.expressions.Expression;
import yal.exceptions.AnalyseSemantiqueException;

public class DeclTab extends Instruction {

    private String idf;
    private Expression size;
    private int dep;
    private int idRegion;
    private static int cmpt= 0;
    private int compteur;


    public DeclTab(String nidf, Expression l) {
        super(l.getNoLigne());
        this.idf = nidf;
        this.size = l;
        this.compteur = cmpt;
        cmpt++;
    }

    public boolean isDeclTab() {
        return true;
    }

    @Override
    public void verifier() {
        idRegion = TDS.getInstance().getIdRegion();
        size.verifier();

        if(idRegion == 0) {
            if(!size.isCste()) {
                throw new AnalyseSemantiqueException(getNoLigne(), " erreur decl tab " + size + " n'est pas une constante ");
            }

        }

        if(!(size.getType().equals("entier"))) {
            throw new AnalyseSemantiqueException(getNoLigne(), "erreur type :\t longueur de " + idf + " \n\t" + size + " n'est pas entier");
        }

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

        String mips = "#Déclaration d'un tableau\n" +

                "#on range l'adresse du debut du tab\n" +
                "sw $sp, " + dep + "($s7)\n" +

                "#on met la longueur dans v0\n" +
                size.toMIPS() +

                "#on check si la longueur est > 0\n" +
                "blez $v0, erreurLongueurTab\n" +

                "#on range la longueur\n" +
                "sw $v0, 0($sp)\n" +

                "#on initialise le tableau\n" +
                "tantquedecltab_" + compteur + " :\n" +

                "beqz $v0, fintantquedecltab_" + compteur + "\n" +

                "addi $v0, $v0, -1\n" +
                "addi $sp, $sp, -4\n" +
                "sw $zero, 0($sp)\n" +

                "j tantquedecltab_" + compteur + "\n" +

                "fintantquedecltab_" + compteur + " :\n" +

                "addi $sp, $sp, -4\n" ;

        return mips;


    }
}

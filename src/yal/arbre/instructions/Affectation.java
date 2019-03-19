package yal.arbre.instructions;

        import yal.analyse.TDS;
        import yal.analyse.entree.EntreeVariable;
        import yal.analyse.symbole.Symbole;
        import yal.arbre.expressions.Expression;
        import yal.exceptions.AnalyseSemantiqueException;

public class Affectation extends Instruction{

    private String idf;
    private Expression exp;
    private int dep;
    private int idRegion;
    private static int cmpt= 0;
    private int compteur;

    public Affectation(String idf, Expression exp){
        super(exp.getNoLigne());
        this.idf = idf;
        this.exp = exp;
        this.compteur = cmpt;
        cmpt++;
    }

    @Override
    public void verifier(){

        EntreeVariable e = new EntreeVariable(idf);
        Symbole s = TDS.getInstance().identifier(e);

        if (s == null) {
            throw new AnalyseSemantiqueException(exp.getNoLigne(), "la variable  `" + idf + "`"+"n'est pas declaree");
        }

        this.dep = s.getDep();
        this.idRegion = s.getIdRegion();
        exp.verifier();
    }

//    @Override
//    public String toMIPS(){
//        StringBuilder affect = new StringBuilder(50);
//        affect.append("#affectation d'une variable \n");
//        affect.append(exp.toMIPS());
//        affect.append("sw $v0, ");
//        affect.append(dep);
//        affect.append("($s7)\n");
//        affect.append("\n");
//        return affect.toString();
//    }
//
//}

    @Override
    public String toMIPS() {

    String mips = "#affectation\n" +

            "#on empile la valeur qu'on veut mettre dans la variable\n" +
            "sw $v0, 0($sp)\n" +
            "add $sp, $sp, -4\n" +

            "#On recupere la base\n" +
            "move $t2, $s7\n" +

            "#on récupere le numéro de région de la variable\n" +
            "li $v1, " + idRegion + "\n" +

            "#tantque\n" +
            "tantqueaffect_" + compteur + " :\n" +

            "#on prend le numéro de région courant\n" +
            "lw $v0, 4($t2) \n" +
            "sub $v0, $v0, $v1\n" +

            "#on va a la fin si les numéros correspondent\n" +
            "beqz $v0, fintantqueaffect_" + compteur + "\n" +

            "#on essaye avec le numéro de région précédent sinon\n" +
            "lw $t2, 8($t2) \n" +
            "j tantqueaffect_" + compteur + "\n" +

            "#sortie du tantque\n" +
            "fintantqueaffect_" + compteur + " :\n\n" +

            "#on dépile la valeur qu'on veut mettre dans la variable\n" +
            "add $sp, $sp, 4\n" +
            "lw $v0, 0($sp)\n" +

            "sw $v0, " + dep + "($t2)\n" ;

       return mips;
    }
}
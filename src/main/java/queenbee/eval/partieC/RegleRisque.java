package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;

/** Un type de risque détectable sur une mesure (principe O : on ajoute des règles, on ne modifie pas l'évaluateur). */
public interface RegleRisque {

    /** Libellé du risque, utilisé dans les messages (ex. "Surchauffe"). */
    String nom();

    boolean estDeclenchee(Mesure mesure);
}

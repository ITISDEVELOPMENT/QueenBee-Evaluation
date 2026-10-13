package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;

/**
 * C1 (partie 1) — Humidité excessive : humidité STRICTEMENT supérieure à 90 %.
 * Nom du risque (exactement) : "Humidite excessive".
 *
 * Suivez le modèle de RegleSurchauffe (constante nommée, pas de nombre magique).
 * Rappel O : on ajoute ce type de risque SANS modifier EvaluateurRisques.
 */
public class RegleHumiditeExcessive implements RegleRisque {

    private static final double SEUIL_HUMIDITE_PCT = 90.0;

    @Override
    public String nom() {
        return "Humidite excessive";
    }

    @Override
    public boolean estDeclenchee(Mesure mesure) {
        return mesure.humiditePct() > SEUIL_HUMIDITE_PCT;
    }
}

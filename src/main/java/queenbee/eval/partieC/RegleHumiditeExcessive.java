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

    // TODO C1 : déclarer la constante du seuil
    private static final double SEUIL_HUMIDITE_EXCESSIVE_PCT = 90.0;

    @Override
    public String nom() {
        // TODO C1
        return "Humidite excessive";
    }

    @Override
    public boolean estDeclenchee(Mesure mesure) {
        // TODO C1
        return mesure.humiditePct() > SEUIL_HUMIDITE_EXCESSIVE_PCT;
    }
}

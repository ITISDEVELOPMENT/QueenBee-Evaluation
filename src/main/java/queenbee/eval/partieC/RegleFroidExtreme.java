package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;

/**
 * C1 (partie 2) — NOUVEAU BESOIN : Froid extrême, température STRICTEMENT inférieure à 5 °C
 * (risque de mortalité de la colonie).
 * Nom du risque (exactement) : "Froid extreme".
 */
public class RegleFroidExtreme implements RegleRisque {

    private static final double SEUIL_FROID_C = 5.0;

    @Override
    public String nom() {
        return "Froid extreme";
    }

    @Override
    public boolean estDeclenchee(Mesure mesure) {
        return mesure.temperatureC() < SEUIL_FROID_C;
    }
}

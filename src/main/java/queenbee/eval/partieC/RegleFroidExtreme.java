package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;

/**
 * C1 (partie 2) — NOUVEAU BESOIN : Froid extrême, température STRICTEMENT inférieure à 5 °C
 * (risque de mortalité de la colonie).
 * Nom du risque (exactement) : "Froid extreme".
 */
public class RegleFroidExtreme implements RegleRisque {

    // TODO C1 : déclarer la constante du seuil

    @Override
    public String nom() {
        // TODO C1
        return "Froid extreme";
    }

    @Override
    public boolean estDeclenchee(Mesure mesure) {
        // TODO C1
        return mesure.temperatureC() < 5;
    }
}

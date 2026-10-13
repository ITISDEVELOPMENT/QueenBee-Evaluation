package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;

public class RegleFroidExtreme implements RegleRisque {
    private static final double SEUIL_FROID_EXTREME_C = 5.0;

    @Override
    public String nom() {
        return "Froid extreme";
    }

    @Override
    public boolean estDeclenchee(Mesure mesure) {
        return mesure.temperatureC() < SEUIL_FROID_EXTREME_C;
    }
}

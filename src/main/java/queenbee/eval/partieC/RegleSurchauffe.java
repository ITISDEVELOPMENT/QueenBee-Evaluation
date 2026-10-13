package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;

/** EXEMPLE FOURNI (déjà complet) : modèle à suivre pour la question C1. */
public class RegleSurchauffe implements RegleRisque {

    private static final double SEUIL_SURCHAUFFE_C = 38.0;

    @Override
    public String nom() {
        return "Surchauffe";
    }

    @Override
    public boolean estDeclenchee(Mesure mesure) {
        return mesure.temperatureC() > SEUIL_SURCHAUFFE_C;
    }
}

package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;

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

package queenbee.eval.partieA;

import queenbee.eval.donnees.Mesure;

import java.util.Comparator;
import java.util.function.Predicate;

public final class ReglesAlerte {
    public static final double SEUIL_SURCHAUFFE_C = 38.0;
    public static final double SEUIL_HUMIDITE_PCT = 90.0;

    public static final double TEMPERATURE_MIN_C = -10.0;
    public static final double TEMPERATURE_MAX_C = 60.0;
    public static final double HUMIDITE_MIN_PCT = 0.0;
    public static final double HUMIDITE_MAX_PCT = 100.0;
    public static final double POIDS_MIN_KG = 0.0;
    public static final double POIDS_MAX_KG = 150.0;

    private ReglesAlerte() {
    }

    public static RegleAlerte surchauffe() {
        return mesure -> mesure.temperatureC() > SEUIL_SURCHAUFFE_C;
    }

    public static RegleAlerte humiditeExcessive() {
        return mesure -> mesure.humiditePct() > SEUIL_HUMIDITE_PCT;
    }

    public static Predicate<Mesure> estPlausible() {
        Predicate<Mesure> temperaturePlausible = mesure ->
                mesure.temperatureC() >= TEMPERATURE_MIN_C && mesure.temperatureC() <= TEMPERATURE_MAX_C;
        Predicate<Mesure> humiditePlausible = mesure ->
                mesure.humiditePct() >= HUMIDITE_MIN_PCT && mesure.humiditePct() <= HUMIDITE_MAX_PCT;
        Predicate<Mesure> poidsPlausible = mesure ->
                mesure.poidsKg() >= POIDS_MIN_KG && mesure.poidsKg() <= POIDS_MAX_KG;
        return temperaturePlausible.and(humiditePlausible).and(poidsPlausible);
    }

    public static Comparator<Mesure> plusRecentesDAbord() {
        return Comparator.comparing(Mesure::horodatage).reversed();
    }
}

package queenbee.eval.partieA;

import queenbee.eval.donnees.Mesure;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * PARTIE A — Expressions Lambda et interfaces fonctionnelles (3 points).
 *
 * AVANT (code d'origine, à ne pas reproduire) : une classe par règle.
 *
 *     public class RegleSurchauffeClassique implements RegleAlerte {
 *         public boolean estDeclenchee(Mesure m) { return m.temperatureC() > 38; }
 *     }
 *     RegleAlerte regle = new RegleSurchauffeClassique();
 *
 * APRÈS (ce qu'on attend) : RegleAlerte est une interface fonctionnelle,
 * chaque règle s'écrit en une ligne avec une expression Lambda.
 *
 * Consignes : utilisez les constantes ci-dessous (pas de nombre magique),
 * remplacez chaque « throw new UnsupportedOperationException » par votre code.
 */
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

    /** Expressions Lambda
     * A1 (0,5 pt) — Surchauffe : température STRICTEMENT supérieure à 38 °C.
     */
    public static RegleAlerte surchauffe() {
        // TODO A1
        throw new UnsupportedOperationException("TODO A1");
    }

    /**  Expressions Lambda
     * A2 (0,5 pt) — Humidité excessive : humidité STRICTEMENT supérieure à 90 %.
     */
    public static RegleAlerte humiditeExcessive() {
        // TODO A2
        throw new UnsupportedOperationException("TODO A2");
    }

    /**
     * A3 (1,5 pt) — Plausibilité physique d'une trame (UC-CY02).
     * Écrivez TROIS Predicate<Mesure> nommés (un par grandeur),
     * puis retournez leur composition avec and().
     */
    public static Predicate<Mesure> estPlausible() {
        // TODO A3
        throw new UnsupportedOperationException("TODO A3");
    }

    /**
     * A4 (0,5 pt) — Ordre d'affichage : les mesures les plus RÉCENTES d'abord (horodatage décroissant).
     * Indice : Comparator.comparing(...) avec une référence de méthode, puis reversed().
     */
    public static Comparator<Mesure> plusRecentesDAbord() {
        // TODO A4
        throw new UnsupportedOperationException("TODO A4");
    }
}

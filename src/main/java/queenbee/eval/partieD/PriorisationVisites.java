package queenbee.eval.partieD;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PARTIE D — Clean Code et Refactoring (5 points).
 *
 * Réécrivez le comportement du code hérité (classe PV du paquet « avant ») dans cette classe,
 * en appliquant les étapes vues en TD4, dans l'ordre :
 *
 *   1. Rename             : des noms qui disent ce qu'ils contiennent (pas de l, s, x, sc, r, t...).
 *   2. Extract Constant   : 38, 90, 2.5, 15 et les points 3, 2, 4, 1 deviennent des constantes nommées.
 *   3. Parameter Object   : DÉJÀ FAIT pour vous, utilisez le record EtatRuche (plus de tableau lu par indice).
 *   4. Extract Method     : le calcul du score va dans scoreDeRisque(...) ; méthodes courtes.
 *   5. Replace Loop with Pipeline : filtrage, tri et mise en forme avec un Stream
 *                                   (le tri « à la main » par double boucle disparaît).
 *   6. Remove Dead Code   : ne recopiez ni ancienScore, ni les commentaires inutiles.
 *
 * Interdits : appeler l'ancien code ou manipuler un tableau de chaînes ; modifier PV ou les tests.
 * Le test de caractérisation vérifie que votre sortie est EXACTEMENT celle de l'ancien code.
 *
 * Rappel de la règle métier : +3 si température > 38 ; +2 si humidité > 90 ;
 * +4 si perte de poids > 2,5 kg ; +1 si dernière visite > 15 jours (seuils STRICTS).
 * Sortie : "id : score" (score > 0 uniquement), tri par score décroissant puis id croissant.
 */
public class PriorisationVisites {

    public static final double TEMPERATURE_MAX_C = 38;
    public static final double HUMIDITE_MAX_PCT = 90;
    public static final double SEUIL_PERTE_POIDS_KG = 2.5;
    public static final int JOURS_MAX_SANS_VISITE = 15;

    public static final int SCORE_CHALEUR = 3;
    public static final int SCORE_HUMIDITE = 2;
    public static final int SCORE_PERTE_POIDS = 4;
    public static final int SCORE_VISITE_ANCIENNE = 1;

    /**
     * @return les lignes "id : score" des ruches du site ayant un score > 0,
     *         triées par score décroissant puis par identifiant
     */
    public List<String> visitesPrioritaires(List<EtatRuche> etats, String site) {
        return etats.stream()
                .filter(etat -> etat.site().equals(site))
                .map(etat -> new ScoreRuche(etat.id(), scoreDeRisque(etat)))
                .filter(ruche -> ruche.score() > 0)
                .sorted(Comparator.comparingInt(ScoreRuche::score).reversed()
                        .thenComparing(ScoreRuche::idRuche))
                .map(ScoreRuche::toString)
                .collect(Collectors.toList());
    }

    /** @return le score de risque d'une ruche (0 à 10) */
    public int scoreDeRisque(EtatRuche etat) {
        int score = 0;
        if (etat.temperatureC() > TEMPERATURE_MAX_C) {
            score += SCORE_CHALEUR;
        }
        if (etat.humiditePct() > HUMIDITE_MAX_PCT) {
            score += SCORE_HUMIDITE;
        }
        if (etat.perteDePoidsKg() > SEUIL_PERTE_POIDS_KG) {
            score += SCORE_PERTE_POIDS;
        }
        if (etat.joursDepuisDerniereVisite() > JOURS_MAX_SANS_VISITE) {
            score += SCORE_VISITE_ANCIENNE;
        }
        return score;
    }
}

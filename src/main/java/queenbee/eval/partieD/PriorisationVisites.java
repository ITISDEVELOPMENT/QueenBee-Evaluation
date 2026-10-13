package queenbee.eval.partieD;

import java.util.Comparator;
import java.util.List;

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


    private static final double SEUIL_TEMPERATURE_C = 38.0;
    private static final double SEUIL_HUMIDITE_PCT = 90.0;
    private static final double SEUIL_PERTE_DE_POIDS_KG = 2.5;
    private static final int SEUIL_JOURS_SANS_VISITE = 15;

    private static final int POINTS_SURCHAUFFE = 3;
    private static final int POINTS_HUMIDITE = 2;
    private static final int POINTS_PERTE_DE_POIDS = 4;
    private static final int POINTS_VISITE_EN_RETARD = 1;

    /**
     * @return les lignes "id : score" des ruches du site ayant un score > 0,
     *         triées par score décroissant puis par identifiant
     */
    public List<String> visitesPrioritaires(List<EtatRuche> etats, String site) {
        // TODO D
        return etats.stream()
                .filter(etat -> etat.site().equals(site))
                .map(etat -> new ScoreRuche(etat.id(), scoreDeRisque(etat)))
                .filter(scoreRuche -> scoreRuche.score() > 0)
                .sorted(Comparator.comparingInt(ScoreRuche::score).reversed()
                        .thenComparing(ScoreRuche::id))
                .map(ScoreRuche::enLigne)
                .toList();
    }

    /** @return le score de risque d'une ruche (0 à 10) */
    public int scoreDeRisque(EtatRuche etat) {
        return pointsSiSurchauffe(etat)
                + pointsSiHumiditeExcessive(etat)
                + pointsSiPerteDePoids(etat)
                + pointsSiVisiteEnRetard(etat);
    }

    private int pointsSiSurchauffe(EtatRuche etat) {
        return etat.temperatureC() > SEUIL_TEMPERATURE_C ? POINTS_SURCHAUFFE : 0;
    }

    private int pointsSiHumiditeExcessive(EtatRuche etat) {
        return etat.humiditePct() > SEUIL_HUMIDITE_PCT ? POINTS_HUMIDITE : 0;
    }

    private int pointsSiPerteDePoids(EtatRuche etat) {
        return etat.perteDePoidsKg() > SEUIL_PERTE_DE_POIDS_KG ? POINTS_PERTE_DE_POIDS : 0;
    }

    private int pointsSiVisiteEnRetard(EtatRuche etat) {
        return etat.joursDepuisDerniereVisite() > SEUIL_JOURS_SANS_VISITE ? POINTS_VISITE_EN_RETARD : 0;
    }

    private record ScoreRuche(String id, int score) {
        String enLigne() {
            return id + " : " + score;
        }
    }
}

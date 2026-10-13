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

    private static final double SEUIL_SURCHAUFFE_C = 38.0;
    private static final double SEUIL_HUMIDITE_PCT = 90.0;
    private static final double SEUIL_PERTE_DE_POIDS_KG = 2.5;
    private static final int SEUIL_JOURS_SANS_VISITE = 15;

    private static final int POINTS_SURCHAUFFE = 3;
    private static final int POINTS_HUMIDITE = 2;
    private static final int POINTS_PERTE_DE_POIDS = 4;
    private static final int POINTS_VISITE_ANCIENNE = 1;

    private static final Comparator<VisitePrioritaire> PAR_SCORE_DECROISSANT_PUIS_ID =
            Comparator.comparingInt(VisitePrioritaire::score).reversed()
                    .thenComparing(VisitePrioritaire::idRuche);

    private record VisitePrioritaire(String idRuche, int score) {

        String libelle() {
            return idRuche + " : " + score;
        }
    }

    /**
     * @return les lignes "id : score" des ruches du site ayant un score > 0,
     *         triées par score décroissant puis par identifiant
     */
    public List<String> visitesPrioritaires(List<EtatRuche> etats, String site) {
        return etats.stream()
                .filter(etat -> etat.site().equals(site))
                .map(etat -> new VisitePrioritaire(etat.id(), scoreDeRisque(etat)))
                .filter(visite -> visite.score() > 0)
                .sorted(PAR_SCORE_DECROISSANT_PUIS_ID)
                .map(VisitePrioritaire::libelle)
                .toList();
    }

    /** @return le score de risque d'une ruche (0 à 10) */
    public int scoreDeRisque(EtatRuche etat) {
        return points(etat.temperatureC() > SEUIL_SURCHAUFFE_C, POINTS_SURCHAUFFE)
                + points(etat.humiditePct() > SEUIL_HUMIDITE_PCT, POINTS_HUMIDITE)
                + points(etat.perteDePoidsKg() > SEUIL_PERTE_DE_POIDS_KG, POINTS_PERTE_DE_POIDS)
                + points(etat.joursDepuisDerniereVisite() > SEUIL_JOURS_SANS_VISITE, POINTS_VISITE_ANCIENNE);
    }

    private static int points(boolean risquePresent, int pointsDuRisque) {
        return risquePresent ? pointsDuRisque : 0;
    }
}

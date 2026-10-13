package queenbee.eval.partieD;

import java.util.Comparator;
import java.util.List;

/**
 * UC-C01 — Priorisation des visites d'un site selon un score de risque par ruche.
 *
 * Règle métier : +3 si température > 38 ; +2 si humidité > 90 ;
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

    private static final int SCORE_NUL = 0;

    private record RucheNotee(String id, int score) {

        String enLigne() {
            return id + " : " + score;
        }
    }

    private static final Comparator<RucheNotee> PAR_SCORE_DECROISSANT_PUIS_ID =
            Comparator.comparingInt(RucheNotee::score).reversed()
                    .thenComparing(RucheNotee::id);

    /**
     * @return les lignes "id : score" des ruches du site ayant un score > 0,
     *         triées par score décroissant puis par identifiant
     */
    public List<String> visitesPrioritaires(List<EtatRuche> etats, String site) {
        return etats.stream()
                .filter(etat -> etat.site().equals(site))
                .map(etat -> new RucheNotee(etat.id(), scoreDeRisque(etat)))
                .filter(rucheNotee -> rucheNotee.score() > SCORE_NUL)
                .sorted(PAR_SCORE_DECROISSANT_PUIS_ID)
                .map(RucheNotee::enLigne)
                .toList();
    }

    /** @return le score de risque d'une ruche (0 à 10) */
    public int scoreDeRisque(EtatRuche etat) {
        return pointsSurchauffe(etat)
                + pointsHumidite(etat)
                + pointsPerteDePoids(etat)
                + pointsVisiteAncienne(etat);
    }

    private int pointsSurchauffe(EtatRuche etat) {
        return etat.temperatureC() > SEUIL_SURCHAUFFE_C ? POINTS_SURCHAUFFE : SCORE_NUL;
    }

    private int pointsHumidite(EtatRuche etat) {
        return etat.humiditePct() > SEUIL_HUMIDITE_PCT ? POINTS_HUMIDITE : SCORE_NUL;
    }

    private int pointsPerteDePoids(EtatRuche etat) {
        return etat.perteDePoidsKg() > SEUIL_PERTE_DE_POIDS_KG ? POINTS_PERTE_DE_POIDS : SCORE_NUL;
    }

    private int pointsVisiteAncienne(EtatRuche etat) {
        return etat.joursDepuisDerniereVisite() > SEUIL_JOURS_SANS_VISITE ? POINTS_VISITE_ANCIENNE : SCORE_NUL;
    }
}

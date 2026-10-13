package queenbee.eval.partieD;

import java.util.Comparator;
import java.util.List;

public class PriorisationVisites {
    private static final double SEUIL_SURCHAUFFE_C = 38.0;
    private static final double SEUIL_HUMIDITE_PCT = 90.0;
    private static final double SEUIL_PERTE_DE_POIDS_KG = 2.5;
    private static final int SEUIL_JOURS_SANS_VISITE = 15;

    private static final int POINTS_SURCHAUFFE = 3;
    private static final int POINTS_HUMIDITE = 2;
    private static final int POINTS_PERTE_DE_POIDS = 4;
    private static final int POINTS_VISITE_ANCIENNE = 1;

    private static final int AUCUN_RISQUE = 0;

    private static final Comparator<VisitePrioritaire> PAR_URGENCE =
            Comparator.comparingInt(VisitePrioritaire::score).reversed()
                    .thenComparing(VisitePrioritaire::rucheId);

    private record VisitePrioritaire(String rucheId, int score) {
        String enLigne() {
            return rucheId + " : " + score;
        }
    }

    public List<String> visitesPrioritaires(List<EtatRuche> etats, String site) {
        return etats.stream()
                .filter(etat -> etat.site().equals(site))
                .map(etat -> new VisitePrioritaire(etat.id(), scoreDeRisque(etat)))
                .filter(visite -> visite.score() > AUCUN_RISQUE)
                .sorted(PAR_URGENCE)
                .map(VisitePrioritaire::enLigne)
                .toList();
    }

    public int scoreDeRisque(EtatRuche etat) {
        return pointsSiSurchauffe(etat)
                + pointsSiHumiditeExcessive(etat)
                + pointsSiPerteDePoids(etat)
                + pointsSiVisiteAncienne(etat);
    }

    private static int pointsSiSurchauffe(EtatRuche etat) {
        return etat.temperatureC() > SEUIL_SURCHAUFFE_C ? POINTS_SURCHAUFFE : AUCUN_RISQUE;
    }

    private static int pointsSiHumiditeExcessive(EtatRuche etat) {
        return etat.humiditePct() > SEUIL_HUMIDITE_PCT ? POINTS_HUMIDITE : AUCUN_RISQUE;
    }

    private static int pointsSiPerteDePoids(EtatRuche etat) {
        return etat.perteDePoidsKg() > SEUIL_PERTE_DE_POIDS_KG ? POINTS_PERTE_DE_POIDS : AUCUN_RISQUE;
    }

    private static int pointsSiVisiteAncienne(EtatRuche etat) {
        return etat.joursDepuisDerniereVisite() > SEUIL_JOURS_SANS_VISITE ? POINTS_VISITE_ANCIENNE : AUCUN_RISQUE;
    }
}

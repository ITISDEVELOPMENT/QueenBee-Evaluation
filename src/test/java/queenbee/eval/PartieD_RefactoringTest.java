package queenbee.eval;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import queenbee.eval.partieD.EtatRuche;
import queenbee.eval.partieD.PriorisationVisites;
import queenbee.eval.partieD.avant.PV;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de la partie D. NE PAS MODIFIER.
 *
 * Test de CARACTÉRISATION : on ne vérifie pas une spécification, on vérifie que le nouveau code
 * produit EXACTEMENT la même sortie que l'ancien code sur les mêmes données.
 */
@DisplayName("Partie D - Refactoring")
class PartieD_RefactoringTest {

    // R-BF-07 est placée avant R-BF-01 exprès : à score égal, l'ordre attendu est celui des identifiants.
    private static final List<String[]> LIGNES = List.of(
            new String[]{"R-BF-07", "Koudougou", "35.0", "60", "47.0", "50.0", "1"},
            new String[]{"R-BF-01", "Koudougou", "39.2", "65", "41.5", "42.0", "20"},
            new String[]{"R-BF-02", "Koudougou", "35.4", "61", "38.3", "38.0", "3"},
            new String[]{"R-BF-03", "Koudougou", "34.1", "93", "41.6", "45.0", "16"},
            new String[]{"R-BF-04", "Koudougou", "36.0", "70", "40.0", "40.5", "18"},
            new String[]{"R-BF-05", "Koudougou", "38.5", "50", "30.0", "30.2", "2"},
            new String[]{"R-BF-06", "Koudougou", "35.0", "91", "50.0", "50.0", "1"},
            new String[]{"R-CM-01", "Bamenda", "4.0", "80", "35.8", "36.0", "10"},
            new String[]{"R-CM-02", "Bamenda", "39.0", "95", "45.0", "48.0", "30"},
            new String[]{"R-SN-01", "Kedougou", "40.1", "50", "39.0", "40.0", "5"},
            new String[]{"R-SN-02", "Kedougou", "38.0", "90", "37.5", "40.0", "15"});

    private static List<EtatRuche> etats() {
        return LIGNES.stream().map(EtatRuche::depuisLigne).toList();
    }

    @ParameterizedTest(name = "D1 - caracterisation, site {0}")
    @ValueSource(strings = {"Koudougou", "Bamenda", "Kedougou", "Site inconnu"})
    void d1_memeSortieQueLAncienCode(String site) {
        List<String> attendu = PV.calc(LIGNES, site);

        List<String> obtenu = new PriorisationVisites().visitesPrioritaires(etats(), site);

        assertEquals(attendu, obtenu);
    }

    @Test
    @DisplayName("D1 - sortie de reference pour Koudougou (ex aequo departages par id)")
    void d1_sortieDeReference() {
        assertEquals(List.of("R-BF-03 : 7", "R-BF-01 : 4", "R-BF-07 : 4", "R-BF-05 : 3", "R-BF-06 : 2", "R-BF-04 : 1"),
                new PriorisationVisites().visitesPrioritaires(etats(), "Koudougou"));
    }

    @Test
    @DisplayName("D1 - score de risque d'une ruche, seuils stricts")
    void d1_scoreDeRisque() {
        PriorisationVisites priorisation = new PriorisationVisites();
        assertEquals(10, priorisation.scoreDeRisque(new EtatRuche("R", "S", 39.0, 95, 45.0, 48.0, 30)));
        assertEquals(0, priorisation.scoreDeRisque(new EtatRuche("R", "S", 38.0, 90, 37.5, 40.0, 15)));
    }

    @Test
    @DisplayName("D1 - le nouveau code ne delegue pas a l'ancien")
    void d1_neDeleguePasALAncienCode() throws IOException {
        String source = Files.readString(Path.of("src/main/java/queenbee/eval/partieD/PriorisationVisites.java"));
        assertFalse(source.contains("partieD.avant") || source.contains("PV.calc"),
                "PriorisationVisites ne doit pas appeler l'ancien code PV");
        assertFalse(source.contains("String[]"), "PriorisationVisites ne doit plus manipuler de String[]");
    }
}

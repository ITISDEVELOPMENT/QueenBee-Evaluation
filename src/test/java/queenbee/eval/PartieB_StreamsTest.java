package queenbee.eval;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import queenbee.eval.donnees.JeuDeDonnees;
import queenbee.eval.donnees.Mesure;
import queenbee.eval.partieB.TableauDeBordRucher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de la partie B. NE PAS MODIFIER.
 * Ces tests n'utilisent pas votre code de la partie A : une erreur en A ne vous pénalise pas en B.
 */
@DisplayName("Partie B - Streams")
class PartieB_StreamsTest {

    private final TableauDeBordRucher tableau =
            new TableauDeBordRucher(JeuDeDonnees.ruches(), JeuDeDonnees.mesuresValides());

    @Test
    @DisplayName("B1 - mesures valides : hors plage et rejeu elimines")
    void b1_mesuresValides() {
        TableauDeBordRucher brut = new TableauDeBordRucher(JeuDeDonnees.ruches(), JeuDeDonnees.mesuresBrutes());
        Predicate<Mesure> temperatureRaisonnable = m -> m.temperatureC() <= 60;

        List<Mesure> valides = brut.mesuresValides(temperatureRaisonnable);

        assertEquals(12, valides.size());
        assertTrue(valides.containsAll(JeuDeDonnees.mesuresValides()));
    }

    @Test
    @DisplayName("B2 - ruches d'un site, triees")
    void b2_ruchesDuSite() {
        assertEquals(List.of("R-BF-01", "R-BF-02", "R-BF-03"), tableau.ruchesDuSite("Koudougou"));
        assertEquals(List.of("R-CM-01", "R-CM-02"), tableau.ruchesDuSite("Bamenda"));
        assertEquals(List.of(), tableau.ruchesDuSite("Site inconnu"));
    }

    @Test
    @DisplayName("B3 - nombre de mesures declenchant une regle")
    void b3_nombreDeMesuresDeclenchant() {
        assertEquals(3, tableau.nombreDeMesuresDeclenchant(m -> m.temperatureC() > 38));
        assertEquals(2, tableau.nombreDeMesuresDeclenchant(m -> m.humiditePct() > 90));
        assertEquals(0, tableau.nombreDeMesuresDeclenchant(m -> m.poidsKg() > 100));
    }

    @Test
    @DisplayName("B4 - temperature moyenne par ruche")
    void b4_temperatureMoyenneParRuche() {
        Map<String, Double> moyennes = tableau.temperatureMoyenneParRuche();
        assertEquals(6, moyennes.size());
        assertEquals(36.85, moyennes.get("R-BF-01"), 0.001);
        assertEquals(17.1, moyennes.get("R-CM-01"), 0.001);
        assertEquals(39.3, moyennes.get("R-SN-01"), 0.001);
    }

    @Test
    @DisplayName("B5 - dernier poids d'une ruche")
    void b5_dernierPoids() {
        assertEquals(Optional.of(35.8), tableau.dernierPoids("R-CM-01"));
        assertEquals(Optional.of(41.5), tableau.dernierPoids("R-BF-01"));
        assertEquals(Optional.empty(), tableau.dernierPoids("R-XX-99"));
    }

    @Test
    @DisplayName("B6 - apiculteurs d'un site, sans doublon, tries")
    void b6_apiculteursDuSite() {
        assertEquals("Awa Ouedraogo, Issa Kabore", tableau.apiculteursDuSite("Koudougou"));
        assertEquals("Nadege Fon", tableau.apiculteursDuSite("Bamenda"));
        assertEquals("", tableau.apiculteursDuSite("Site inconnu"));
    }
}

package queenbee.eval;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import queenbee.eval.donnees.JeuDeDonnees;
import queenbee.eval.donnees.Mesure;
import queenbee.eval.partieA.ReglesAlerte;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Tests de la partie A. NE PAS MODIFIER. Le préfixe du nom (a1_, a2_...) = numéro de question. */
@DisplayName("Partie A - Lambda")
class PartieA_LambdaTest {

    private static Mesure mesure(double temperature, double humidite, double poids) {
        return new Mesure("R-TEST", LocalDateTime.of(2026, 10, 5, 6, 0), temperature, humidite, poids);
    }

    @Test
    @DisplayName("A1 - surchauffe : > 38 degres, seuil strict")
    void a1_surchauffe() {
        assertTrue(ReglesAlerte.surchauffe().estDeclenchee(mesure(39.2, 60, 40)));
        assertFalse(ReglesAlerte.surchauffe().estDeclenchee(mesure(38.0, 60, 40)), "38 pile ne declenche pas");
        assertFalse(ReglesAlerte.surchauffe().estDeclenchee(mesure(20.0, 60, 40)));
    }

    @Test
    @DisplayName("A2 - humidite excessive : > 90 %, seuil strict")
    void a2_humiditeExcessive() {
        assertTrue(ReglesAlerte.humiditeExcessive().estDeclenchee(mesure(30, 92, 40)));
        assertFalse(ReglesAlerte.humiditeExcessive().estDeclenchee(mesure(30, 90, 40)), "90 pile ne declenche pas");
    }

    @Test
    @DisplayName("A3 - plausibilite : mesure normale acceptee")
    void a3_mesureNormalePlausible() {
        assertTrue(ReglesAlerte.estPlausible().test(mesure(35, 60, 40)));
        assertTrue(ReglesAlerte.estPlausible().test(mesure(-10, 0, 0)), "les bornes sont incluses");
        assertTrue(ReglesAlerte.estPlausible().test(mesure(60, 100, 150)), "les bornes sont incluses");
    }

    @Test
    @DisplayName("A3 - plausibilite : chaque grandeur hors plage est rejetee")
    void a3_horsPlageRejetee() {
        assertFalse(ReglesAlerte.estPlausible().test(mesure(85, 60, 40)), "temperature");
        assertFalse(ReglesAlerte.estPlausible().test(mesure(-11, 60, 40)), "temperature");
        assertFalse(ReglesAlerte.estPlausible().test(mesure(35, 120, 40)), "humidite");
        assertFalse(ReglesAlerte.estPlausible().test(mesure(35, 60, -1)), "poids");
        assertFalse(ReglesAlerte.estPlausible().test(mesure(35, 60, 151)), "poids");
    }

    @Test
    @DisplayName("A4 - tri : les mesures les plus recentes d'abord")
    void a4_plusRecentesDAbord() {
        List<Mesure> triees = JeuDeDonnees.mesuresValides().stream()
                .sorted(ReglesAlerte.plusRecentesDAbord())
                .toList();
        assertEquals(LocalDateTime.of(2026, 10, 6, 6, 0), triees.get(0).horodatage());
        assertEquals(LocalDateTime.of(2026, 10, 5, 6, 0), triees.get(triees.size() - 1).horodatage());
    }
}

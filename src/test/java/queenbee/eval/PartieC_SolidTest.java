package queenbee.eval;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import queenbee.eval.donnees.Mesure;
import queenbee.eval.partieC.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Tests de la partie C. NE PAS MODIFIER. */
@DisplayName("Partie C - SOLID")
class PartieC_SolidTest {

    /** Faux canal : il n'envoie rien, il retient ce qu'on lui demande d'envoyer. Possible grâce au principe D. */
    static class FauxCanal implements CanalNotification {
        final List<Notification> recus = new ArrayList<>();

        @Override
        public void envoyer(String destinataire, String message) {
            recus.add(new Notification(destinataire, message));
        }
    }

    private static Mesure mesure(String ruche, double temperature, double humidite) {
        return new Mesure(ruche, LocalDateTime.of(2026, 10, 6, 6, 0), temperature, humidite, 40);
    }

    private static EvaluateurRisques evaluateurComplet() {
        return new EvaluateurRisques(List.of(
                new RegleSurchauffe(), new RegleHumiditeExcessive(), new RegleFroidExtreme()));
    }

    // ---------- C1 : principe O ----------

    @Test
    @DisplayName("C1 - regle humidite excessive (> 90 %)")
    void c1_regleHumidite() {
        RegleRisque regle = new RegleHumiditeExcessive();
        assertEquals("Humidite excessive", regle.nom());
        assertTrue(regle.estDeclenchee(mesure("R", 30, 93)));
        assertFalse(regle.estDeclenchee(mesure("R", 30, 90)));
    }

    @Test
    @DisplayName("C1 - regle froid extreme (< 5 degres)")
    void c1_regleFroid() {
        RegleRisque regle = new RegleFroidExtreme();
        assertEquals("Froid extreme", regle.nom());
        assertTrue(regle.estDeclenchee(mesure("R", 4.0, 60)));
        assertFalse(regle.estDeclenchee(mesure("R", 5.0, 60)));
    }

    @Test
    @DisplayName("C1 - l'evaluateur (non modifie) utilise les nouvelles regles")
    void c1_evaluateurAvecNouvellesRegles() {
        assertEquals(List.of("Surchauffe", "Humidite excessive"),
                evaluateurComplet().risquesDetectes(mesure("R", 39, 95)));
        assertEquals(List.of("Froid extreme"),
                evaluateurComplet().risquesDetectes(mesure("R", 4, 60)));
    }

    // ---------- C2 : principes S et D ----------

    @Test
    @DisplayName("C2 - une alerte par risque, envoyee au destinataire et journalisee")
    void c2_alertesEnvoyeesEtJournalisees() {
        FauxCanal canal = new FauxCanal();
        JournalAlertes journal = new JournalAlertes();
        ServiceAlertes service = new ServiceAlertes(evaluateurComplet(), canal, journal);

        int nombre = service.traiter(mesure("R-BF-03", 39, 95), "Issa");

        assertEquals(2, nombre);
        assertEquals(List.of(
                new Notification("Issa", "[R-BF-03] Surchauffe"),
                new Notification("Issa", "[R-BF-03] Humidite excessive")), canal.recus);
        assertEquals(List.of("[R-BF-03] Surchauffe", "[R-BF-03] Humidite excessive"), journal.entrees());
    }

    @Test
    @DisplayName("C2 - aucune alerte si aucun risque")
    void c2_aucunRisque() {
        FauxCanal canal = new FauxCanal();
        JournalAlertes journal = new JournalAlertes();
        ServiceAlertes service = new ServiceAlertes(evaluateurComplet(), canal, journal);

        assertEquals(0, service.traiter(mesure("R-BF-02", 35, 60), "Awa"));
        assertTrue(canal.recus.isEmpty());
        assertTrue(journal.entrees().isEmpty());
    }

    @Test
    @DisplayName("C2 - le service fonctionne avec n'importe quel canal (SMS reel)")
    void c2_serviceAvecCanalSms() {
        CanalSms sms = new CanalSms();
        ServiceAlertes service = new ServiceAlertes(evaluateurComplet(), sms, new JournalAlertes());

        service.traiter(mesure("R-CM-01", 4, 80), "Nadege");

        assertEquals(List.of(new Notification("Nadege", "[R-CM-01] Froid extreme")), sms.envoyes());
    }

    // ---------- C3 : principe L ----------

    @Test
    @DisplayName("C3 - push connecte : message delivre immediatement")
    void c3_pushConnecte() {
        CanalPush push = new CanalPush();
        push.seConnecter();

        push.envoyer("Awa", "[R-BF-01] Surchauffe");

        assertEquals(List.of(new Notification("Awa", "[R-BF-01] Surchauffe")), push.delivres());
        assertTrue(push.enAttente().isEmpty());
    }

    @Test
    @DisplayName("C3 - push hors reseau : pas d'exception, message en attente")
    void c3_pushHorsReseau() {
        CanalPush push = new CanalPush();

        assertDoesNotThrow(() -> push.envoyer("Awa", "[R-BF-01] Surchauffe"));

        assertTrue(push.delivres().isEmpty());
        assertEquals(1, push.enAttente().size());
    }

    @Test
    @DisplayName("C3 - reconnexion : les messages en attente sont delivres dans l'ordre")
    void c3_reconnexion() {
        CanalPush push = new CanalPush();
        push.envoyer("Awa", "message 1");
        push.envoyer("Awa", "message 2");

        push.seConnecter();

        assertEquals(List.of(new Notification("Awa", "message 1"), new Notification("Awa", "message 2")),
                push.delivres());
        assertTrue(push.enAttente().isEmpty());
    }

    /** Code écrit pour le type parent CanalNotification, sans rien savoir du sous-type reçu. */
    private static void diffuser(CanalNotification canal, List<String> destinataires, String message) {
        destinataires.forEach(destinataire -> canal.envoyer(destinataire, message));
    }

    @Test
    @DisplayName("C3 - substituabilite : un code ecrit pour CanalNotification marche avec CanalPush")
    void c3_substitution() {
        CanalPush push = new CanalPush();
        List<String> equipe = List.of("Awa", "Issa");

        assertDoesNotThrow(() -> diffuser(push, equipe, "[R-BF-01] Surchauffe"));
        push.seConnecter();
        push.seDeconnecter();
        push.envoyer("Moussa", "message suivant");

        assertEquals(List.of(new Notification("Awa", "[R-BF-01] Surchauffe"),
                new Notification("Issa", "[R-BF-01] Surchauffe")), push.delivres());
        assertEquals(List.of(new Notification("Moussa", "message suivant")), push.enAttente());
    }
}

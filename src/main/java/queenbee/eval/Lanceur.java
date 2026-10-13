package queenbee.eval;

import queenbee.eval.donnees.JeuDeDonnees;
import queenbee.eval.donnees.Mesure;
import queenbee.eval.partieA.ReglesAlerte;
import queenbee.eval.partieB.TableauDeBordRucher;
import queenbee.eval.partieC.CanalPush;
import queenbee.eval.partieC.CanalSms;
import queenbee.eval.partieC.EvaluateurRisques;
import queenbee.eval.partieC.JournalAlertes;
import queenbee.eval.partieC.RegleFroidExtreme;
import queenbee.eval.partieC.RegleHumiditeExcessive;
import queenbee.eval.partieC.RegleSurchauffe;
import queenbee.eval.partieC.ServiceAlertes;
import queenbee.eval.partieD.EtatRuche;
import queenbee.eval.partieD.PriorisationVisites;

import java.util.List;

/**
 * Démonstration des 4 parties. Une partie non terminée affiche « à compléter » au lieu de planter.
 * Pour la notation, seul « mvn test » fait foi.
 */
public class Lanceur {

    public static void main(String[] args) {
        executer("Partie A - Lambda", Lanceur::partieA);
        executer("Partie B - Streams", Lanceur::partieB);
        executer("Partie C - SOLID", Lanceur::partieC);
        executer("Partie D - Refactoring", Lanceur::partieD);
    }

    private static void executer(String titre, Runnable partie) {
        System.out.println("\n=== " + titre + " ===");
        try {
            partie.run();
        } catch (UnsupportedOperationException | NullPointerException e) {
            System.out.println("  -> a completer (" + e.getMessage() + ")");
        }
    }

    private static void partieA() {
        Mesure chaude = JeuDeDonnees.mesuresValides().get(1);
        System.out.println("Surchauffe sur " + chaude + " ? " + ReglesAlerte.surchauffe().estDeclenchee(chaude));
        long plausibles = JeuDeDonnees.mesuresBrutes().stream().filter(ReglesAlerte.estPlausible()).count();
        System.out.println("Trames plausibles : " + plausibles + " / " + JeuDeDonnees.mesuresBrutes().size());
    }

    private static void partieB() {
        TableauDeBordRucher brut = new TableauDeBordRucher(JeuDeDonnees.ruches(), JeuDeDonnees.mesuresBrutes());
        System.out.println("Mesures valides : " + brut.mesuresValides(ReglesAlerte.estPlausible()).size());

        TableauDeBordRucher tableau = new TableauDeBordRucher(JeuDeDonnees.ruches(), JeuDeDonnees.mesuresValides());
        System.out.println("Ruches de Koudougou : " + tableau.ruchesDuSite("Koudougou"));
        System.out.println("Mesures en surchauffe : " + tableau.nombreDeMesuresDeclenchant(ReglesAlerte.surchauffe()));
        System.out.println("Temperature moyenne par ruche : " + tableau.temperatureMoyenneParRuche());
        System.out.println("Dernier poids R-CM-01 : " + tableau.dernierPoids("R-CM-01").orElse(null));
        System.out.println("Apiculteurs de Koudougou : " + tableau.apiculteursDuSite("Koudougou"));
    }

    private static void partieC() {
        EvaluateurRisques evaluateur = new EvaluateurRisques(List.of(
                new RegleSurchauffe(), new RegleHumiditeExcessive(), new RegleFroidExtreme()));
        CanalSms sms = new CanalSms();
        JournalAlertes journal = new JournalAlertes();
        ServiceAlertes service = new ServiceAlertes(evaluateur, sms, journal);
        JeuDeDonnees.mesuresValides().forEach(m -> service.traiter(m, "coordinateur"));
        System.out.println("Journal : " + journal.entrees());

        CanalPush push = new CanalPush();
        push.envoyer("Awa", "[R-BF-01] Surchauffe");
        System.out.println("Hors reseau -> en attente : " + push.enAttente().size());
        push.seConnecter();
        System.out.println("Apres reconnexion -> delivres : " + push.delivres().size());
    }

    private static void partieD() {
        List<EtatRuche> etats = List.of(
                new EtatRuche("R-BF-01", "Koudougou", 39.2, 65, 41.5, 42.0, 20),
                new EtatRuche("R-BF-03", "Koudougou", 34.1, 93, 41.6, 45.0, 16));
        System.out.println(new PriorisationVisites().visitesPrioritaires(etats, "Koudougou"));
    }
}

package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;

import java.util.List;

public class ServiceAlertes {
    private final EvaluateurRisques evaluateur;
    private final CanalNotification canal;
    private final JournalAlertes journal;

    public ServiceAlertes(EvaluateurRisques evaluateur, CanalNotification canal, JournalAlertes journal) {
        this.evaluateur = evaluateur;
        this.canal = canal;
        this.journal = journal;
    }

    public int traiter(Mesure mesure, String destinataire) {
        List<String> messages = evaluateur.risquesDetectes(mesure).stream()
                .map(risque -> messageAlerte(mesure, risque))
                .toList();
        messages.forEach(message -> emettre(destinataire, message));
        return messages.size();
    }

    private static String messageAlerte(Mesure mesure, String risque) {
        return "[" + mesure.rucheId() + "] " + risque;
    }

    private void emettre(String destinataire, String message) {
        canal.envoyer(destinataire, message);
        journal.consigner(message);
    }
}

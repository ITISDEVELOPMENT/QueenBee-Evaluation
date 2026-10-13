package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;
import java.util.List;
/**
 * C2 (2,5 pts) — Réécrire ServiceAlertesAvant (paquet avant) en respectant S et D.
 *
 * S : le service ne détecte rien, n'envoie rien et ne trace rien lui-même. Il DÉLÈGUE à :
 *     - EvaluateurRisques (quels risques ?)
 *     - CanalNotification (envoyer)
 *     - JournalAlertes    (tracer)
 * D : ces trois collaborateurs sont REÇUS par le constructeur (injection de dépendances).
 *     Interdit : écrire « new CanalSms() » ou « new EvaluateurRisques(...) » dans cette classe.
 *     C'est ce qui permet au test de lui passer un faux canal.
 */
public class ServiceAlertes {

    // TODO C2 : déclarer les trois dépendances (private final, typées par les abstractions)
    private final EvaluateurRisques evaluateur;
    private final CanalNotification canal;
    private final JournalAlertes journal;

    public ServiceAlertes(EvaluateurRisques evaluateur, CanalNotification canal, JournalAlertes journal) {
        // TODO C2 : mémoriser les dépendances reçues
        this.evaluateur = evaluateur;
        this.canal = canal;
        this.journal = journal;
    }

    /**
     * Pour chaque risque détecté sur la mesure :
     *   1. construire le message "[idRuche] NomDuRisque"   (ex. "[R-BF-03] Surchauffe")
     *   2. l'envoyer au destinataire par le canal
     *   3. le consigner dans le journal
     *
     * @return le nombre d'alertes émises (0 si aucun risque)
     */
    public int traiter(Mesure mesure, String destinataire) {
        // TODO C2
        List<String> risques = evaluateur.risquesDetectes(mesure);
        for (String risque : risques) {
            String message = "[" + mesure.rucheId() + "] " + risque;
            canal.envoyer(destinataire, message);
            journal.consigner(message);
        }
        return risques.size();
    }
}

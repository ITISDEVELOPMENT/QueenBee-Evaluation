package queenbee.eval.partieC;

import java.util.ArrayList;
import java.util.List;

/**
 * C3 (2 pts) — Notification push qui RESPECTE le contrat de CanalNotification (principe L).
 *
 * Comparez avec NotificationPushAvant (paquet avant) qui lève une exception hors réseau :
 * c'est exactement ce qu'il ne faut pas faire.
 *
 * Comportement attendu :
 *   - Au départ, le téléphone est HORS RÉSEAU.
 *   - envoyer() quand connecté   : la notification va dans « délivrées ».
 *   - envoyer() quand hors réseau : la notification va dans « en attente » (AUCUNE exception).
 *   - seConnecter() : passe connecté ET délivre toutes les notifications en attente,
 *                     dans l'ordre d'arrivée ; la file d'attente est ensuite vide.
 *   - seDeconnecter() : repasse hors réseau.
 */
public class CanalPush implements CanalNotification {

    // TODO C3 : état de connexion + deux listes de Notification (délivrées, en attente)
    private final List<Notification> delivrees = new ArrayList<>();
    private final List<Notification> enAttente = new ArrayList<>();
    private boolean connecte = false;
    @Override
    public void envoyer(String destinataire, String message) {
        // TODO C3
        Notification notification = new Notification(destinataire, message);
        if (connecte) {
            delivrees.add(notification);
        } else {
            enAttente.add(notification);
        }
    }

    public void seConnecter() {
        // TODO C3
        this.connecte = true;
        delivrees.addAll(enAttente);
        enAttente.clear();
    }

    public void seDeconnecter() {
        // TODO C3
        this.connecte = false;
    }

    /** @return une copie non modifiable des notifications délivrées (List.copyOf) */
    public List<Notification> delivres() {
        // TODO C3
        return List.copyOf(delivrees);
    }

    /** @return une copie non modifiable des notifications en attente */
    public List<Notification> enAttente() {
        // TODO C3
        return List.copyOf(enAttente);
    }
}

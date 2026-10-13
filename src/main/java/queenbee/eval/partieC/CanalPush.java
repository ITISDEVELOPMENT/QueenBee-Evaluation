package queenbee.eval.partieC;

import java.util.ArrayList;
import java.util.List;

public class CanalPush implements CanalNotification {
    private boolean connecte;
    private final List<Notification> delivres = new ArrayList<>();
    private final List<Notification> enAttente = new ArrayList<>();

    @Override
    public void envoyer(String destinataire, String message) {
        Notification notification = new Notification(destinataire, message);
        if (connecte) {
            delivres.add(notification);
        } else {
            enAttente.add(notification);
        }
    }

    public void seConnecter() {
        connecte = true;
        delivres.addAll(enAttente);
        enAttente.clear();
    }

    public void seDeconnecter() {
        connecte = false;
    }

    public List<Notification> delivres() {
        return List.copyOf(delivres);
    }

    public List<Notification> enAttente() {
        return List.copyOf(enAttente);
    }
}

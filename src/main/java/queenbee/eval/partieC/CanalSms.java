package queenbee.eval.partieC;

import java.util.ArrayList;
import java.util.List;

/** Canal SMS (simulé : on mémorise les SMS « envoyés »). FOURNI. */
public class CanalSms implements CanalNotification {

    private final List<Notification> envoyes = new ArrayList<>();

    @Override
    public void envoyer(String destinataire, String message) {
        envoyes.add(new Notification(destinataire, message));
    }

    public List<Notification> envoyes() {
        return List.copyOf(envoyes);
    }
}

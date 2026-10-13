package queenbee.eval.partieC.avant;

import java.util.ArrayList;
import java.util.List;

/**
 * CODE EXISTANT — notification push. NE PAS MODIFIER.
 *
 * Violation de Liskov (L) : le CDC (UC-A02) dit « push si l'apiculteur est connecté,
 * sinon au prochain accès ». Or cette classe LÈVE UNE EXCEPTION quand le téléphone est hors réseau.
 * Un code qui utilise un canal de notification quelconque plante si on lui passe ce canal-là :
 * le sous-type ne respecte pas le contrat du type parent.
 */
public class NotificationPushAvant {

    private boolean connecte;
    private final List<String> delivres = new ArrayList<>();

    public void envoyer(String destinataire, String message) {
        if (!connecte) {
            throw new IllegalStateException("Téléphone hors réseau");
        }
        delivres.add(destinataire + " : " + message);
    }

    public void setConnecte(boolean connecte) {
        this.connecte = connecte;
    }

    public List<String> delivres() {
        return List.copyOf(delivres);
    }
}

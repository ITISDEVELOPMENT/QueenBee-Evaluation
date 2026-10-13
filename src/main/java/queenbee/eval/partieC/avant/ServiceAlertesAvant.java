package queenbee.eval.partieC.avant;

import queenbee.eval.donnees.Mesure;

import java.util.ArrayList;
import java.util.List;

/**
 * CODE EXISTANT — UC-A02 Réception d'une alerte. NE PAS MODIFIER : il sert de point de départ.
 *
 * Il fonctionne, mais :
 *  - S : la classe détecte les risques, envoie les SMS ET tient le journal (3 raisons de changer :
 *        l'équipe Data change les seuils, l'équipe Dev change le canal, l'équipe Cyber change la traçabilité).
 *  - O : chaque nouveau type de risque oblige à rouvrir la méthode et à ajouter un « else if ».
 *  - D : le service fabrique lui-même sa passerelle SMS (new PasserelleSms()) : impossible de la
 *        remplacer par un faux canal dans un test, impossible de passer au push sans modifier ce code.
 */
public class ServiceAlertesAvant {

    private final List<String> journal = new ArrayList<>();

    public int traiter(Mesure mesure, String destinataire) {
        PasserelleSms sms = new PasserelleSms();
        int nb = 0;
        if (mesure.temperatureC() > 38) {
            String message = "[" + mesure.rucheId() + "] Surchauffe";
            sms.envoyer(destinataire, message);
            journal.add(message);
            nb++;
        }
        if (mesure.humiditePct() > 90) {
            String message = "[" + mesure.rucheId() + "] Humidite excessive";
            sms.envoyer(destinataire, message);
            journal.add(message);
            nb++;
        }
        // Nouveau besoin : « Froid extrême » (< 5 °C) -> encore un if à ajouter ici...
        return nb;
    }

    public List<String> journal() {
        return List.copyOf(journal);
    }

    /** Passerelle SMS « réelle » (simulée par un affichage console). */
    static class PasserelleSms {
        void envoyer(String destinataire, String message) {
            System.out.println("SMS -> " + destinataire + " : " + message);
        }
    }
}

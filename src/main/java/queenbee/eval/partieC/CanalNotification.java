package queenbee.eval.partieC;

/**
 * Abstraction d'un canal de notification (principe D : le service dépend de cette interface,
 * pas d'un SMS ou d'un push concret).
 *
 * CONTRAT (que toute implémentation doit respecter — principe L) :
 *  1. envoyer() ne lève JAMAIS d'exception ;
 *  2. si le message ne peut pas être délivré tout de suite, il est conservé
 *     et délivré dès que possible (CDC UC-A02 : « sinon au prochain accès »).
 */
public interface CanalNotification {

    void envoyer(String destinataire, String message);
}

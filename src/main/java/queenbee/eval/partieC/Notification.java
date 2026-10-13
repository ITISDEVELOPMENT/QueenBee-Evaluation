package queenbee.eval.partieC;

/** Un message adressé à un destinataire (apiculteur, coordinateur...). */
public record Notification(String destinataire, String message) {
}

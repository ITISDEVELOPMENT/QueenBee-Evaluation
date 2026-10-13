package queenbee.eval.donnees;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Jeu de donnees commun a toute l'evaluation. NE PAS MODIFIER.
 *
 * 3 pays pilotes, 3 sites, 6 ruches, 2 releves par ruche (5 et 6 octobre 2026),
 * plus 2 trames anormales : un rejeu et une trame hors plage physique.
 */
public final class JeuDeDonnees {

    private static final LocalDateTime LUNDI = LocalDateTime.of(2026, 10, 5, 6, 0);
    private static final LocalDateTime MARDI = LocalDateTime.of(2026, 10, 6, 6, 0);

    private JeuDeDonnees() {
    }

    public static List<Ruche> ruches() {
        return List.of(
                new Ruche("R-BF-01", "Koudougou", "Awa Ouedraogo"),
                new Ruche("R-BF-02", "Koudougou", "Awa Ouedraogo"),
                new Ruche("R-BF-03", "Koudougou", "Issa Kabore"),
                new Ruche("R-CM-01", "Bamenda", "Nadege Fon"),
                new Ruche("R-CM-02", "Bamenda", "Nadege Fon"),
                new Ruche("R-SN-01", "Kedougou", "Moussa Diallo"));
    }

    /** Les 12 trames correctes (aucun rejeu, toutes dans les plages physiques). */
    public static List<Mesure> mesuresValides() {
        return List.of(
                new Mesure("R-BF-01", LUNDI, 34.5, 60, 42.0),
                new Mesure("R-BF-01", MARDI, 39.2, 65, 41.5),
                new Mesure("R-BF-02", LUNDI, 35.0, 58, 38.0),
                new Mesure("R-BF-02", MARDI, 35.4, 61, 38.3),
                new Mesure("R-BF-03", LUNDI, 33.8, 92, 45.0),
                new Mesure("R-BF-03", MARDI, 34.1, 93, 44.6),
                new Mesure("R-CM-01", LUNDI, 30.2, 75, 36.0),
                new Mesure("R-CM-01", MARDI, 4.0, 80, 35.8),
                new Mesure("R-CM-02", LUNDI, 31.0, 70, 50.0),
                new Mesure("R-CM-02", MARDI, 31.5, 72, 50.4),
                new Mesure("R-SN-01", LUNDI, 38.5, 55, 40.0),
                new Mesure("R-SN-01", MARDI, 40.1, 50, 39.0));
    }

    /**
     * Les trames telles qu'elles arrivent des passerelles : les 12 trames valides
     * + un rejeu de la trame R-BF-01 de mardi + une trame hors plage (capteur defaillant, 85 degres).
     */
    public static List<Mesure> mesuresBrutes() {
        List<Mesure> brutes = new ArrayList<>(mesuresValides());
        brutes.add(new Mesure("R-BF-01", MARDI, 39.2, 65, 41.5));
        brutes.add(new Mesure("R-CM-02", MARDI.plusHours(6), 85.0, 70, 50.4));
        return List.copyOf(brutes);
    }
}

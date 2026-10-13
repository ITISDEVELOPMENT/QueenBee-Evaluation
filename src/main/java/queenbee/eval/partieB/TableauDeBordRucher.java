package queenbee.eval.partieB;

import queenbee.eval.donnees.Mesure;
import queenbee.eval.donnees.Ruche;
import queenbee.eval.partieA.RegleAlerte;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * PARTIE B — Streams (6 points).
 *
 * Indicateurs du tableau de bord du coordinateur (UC-G01).
 *
 * RÈGLE : chaque méthode s'écrit avec UN pipeline Stream (source → opérations intermédiaires
 * → une opération terminale), SANS boucle for/while. Une méthode écrite avec une boucle
 * qui passe le test obtient la moitié des points.
 */
public class TableauDeBordRucher {

    private final List<Ruche> ruches;
    private final List<Mesure> mesures;

    public TableauDeBordRucher(List<Ruche> ruches, List<Mesure> mesures) {
        this.ruches = List.copyOf(ruches);
        this.mesures = List.copyOf(mesures);
    }

    /**
     * B1 (1 pt) — UC-CY02 : garder les mesures qui satisfont le prédicat de plausibilité
     * reçu en paramètre, puis supprimer les rejeux (trames en double).
     * Opérateurs attendus : filter, distinct.
     */
    public List<Mesure> mesuresValides(Predicate<Mesure> plausibilite) {
        return mesures.stream()
                .filter(plausibilite)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * B2 (1 pt) — Identifiants des ruches d'un site, triés par ordre alphabétique.
     * Liste vide si le site n'existe pas. Opérateurs attendus : filter, map, sorted.
     */
    public List<String> ruchesDuSite(String site) {
        return ruches.stream()
                .filter(r -> r.site().equals(site))
                .map(Ruche::id)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * B3 (1 pt) — Nombre de mesures qui déclenchent la règle d'alerte reçue en paramètre.
     * Opérateurs attendus : filter, count.
     */
    public long nombreDeMesuresDeclenchant(RegleAlerte regle) {
        return mesures.stream()
                .filter(m -> regle.estDeclenchee(m))
                .count();
    }

    /**
     * B4 (1 pt) — Température moyenne par ruche : Map idRuche -> moyenne des températures.
     * Collecteurs attendus : Collectors.groupingBy + Collectors.averagingDouble.
     */
    public Map<String, Double> temperatureMoyenneParRuche() {
        return mesures.stream()
                .collect(Collectors.groupingBy(Mesure::rucheId,
                        Collectors.averagingDouble(Mesure::temperatureC)));
    }

    /**
     * B5 (1 pt) — Poids relevé lors de la mesure la PLUS RÉCENTE d'une ruche.
     * Optional vide si la ruche n'a aucune mesure.
     * Opérateurs attendus : filter, max(Comparator), map sur l'Optional.
     */
    public Optional<Double> dernierPoids(String rucheId) {
        return mesures.stream()
                .filter(m -> m.rucheId().equals(rucheId))
                .max(Comparator.comparing(Mesure::horodatage))
                .map(Mesure::poidsKg);
    }

    /**
     * B6 (1 pt) — Apiculteurs d'un site, sans doublon, triés, séparés par ", ".
     * Exemple : "Awa Ouedraogo, Issa Kabore". Chaîne vide si le site n'existe pas.
     * Opérateurs attendus : filter, map, distinct, sorted, Collectors.joining.
     */
    public String apiculteursDuSite(String site) {
        return ruches.stream()
                .filter(r -> r.site().equals(site))
                .map(Ruche::apiculteur)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
    }
}

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

public class TableauDeBordRucher {
    private final List<Ruche> ruches;
    private final List<Mesure> mesures;

    public TableauDeBordRucher(List<Ruche> ruches, List<Mesure> mesures) {
        this.ruches = List.copyOf(ruches);
        this.mesures = List.copyOf(mesures);
    }

    public List<Mesure> mesuresValides(Predicate<Mesure> plausibilite) {
        return mesures.stream()
                .filter(plausibilite)
                .distinct()
                .toList();
    }

    public List<String> ruchesDuSite(String site) {
        return ruches.stream()
                .filter(ruche -> ruche.site().equals(site))
                .map(Ruche::id)
                .sorted()
                .toList();
    }

    public long nombreDeMesuresDeclenchant(RegleAlerte regle) {
        return mesures.stream()
                .filter(regle::estDeclenchee)
                .count();
    }

    public Map<String, Double> temperatureMoyenneParRuche() {
        return mesures.stream()
                .collect(Collectors.groupingBy(Mesure::rucheId,
                        Collectors.averagingDouble(Mesure::temperatureC)));
    }

    public Optional<Double> dernierPoids(String rucheId) {
        return mesures.stream()
                .filter(mesure -> mesure.rucheId().equals(rucheId))
                .max(Comparator.comparing(Mesure::horodatage))
                .map(Mesure::poidsKg);
    }

    public String apiculteursDuSite(String site) {
        return ruches.stream()
                .filter(ruche -> ruche.site().equals(site))
                .map(Ruche::apiculteur)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
    }
}

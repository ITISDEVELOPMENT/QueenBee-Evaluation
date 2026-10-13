package queenbee.eval.partieC;

import queenbee.eval.donnees.Mesure;

import java.util.List;

/**
 * Responsabilité unique : dire quels risques une mesure présente.
 *
 * FOURNI — CETTE CLASSE NE DOIT PAS ÊTRE MODIFIÉE (principe ouvert/fermé) :
 * pour ajouter un type de risque, on crée une nouvelle RegleRisque.
 */
public final class EvaluateurRisques {

    private final List<RegleRisque> regles;

    public EvaluateurRisques(List<RegleRisque> regles) {
        this.regles = List.copyOf(regles);
    }

    /** @return les noms des risques déclenchés, dans l'ordre des règles */
    public List<String> risquesDetectes(Mesure mesure) {
        return regles.stream()
                .filter(regle -> regle.estDeclenchee(mesure))
                .map(RegleRisque::nom)
                .toList();
    }
}

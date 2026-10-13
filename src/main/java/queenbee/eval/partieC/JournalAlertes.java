package queenbee.eval.partieC;

import java.util.ArrayList;
import java.util.List;

/** Responsabilité unique : tracer les alertes émises (exigence Cyber). FOURNI. */
public class JournalAlertes {

    private final List<String> entrees = new ArrayList<>();

    public void consigner(String message) {
        entrees.add(message);
    }

    public List<String> entrees() {
        return List.copyOf(entrees);
    }
}

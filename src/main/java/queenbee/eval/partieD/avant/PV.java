package queenbee.eval.partieD.avant;

import java.util.ArrayList;
import java.util.List;

/**
 * CODE HÉRITÉ — UC-C01 Priorisation des visites (prototype écrit en urgence pendant le pilote).
 * NE PAS MODIFIER : le test de caractérisation compare votre nouvelle classe à ce code.
 *
 * Chaque ligne d'entrée est un String[] :
 *   [0] id ruche, [1] site, [2] température °C, [3] humidité %, [4] poids actuel kg,
 *   [5] poids précédent kg, [6] jours depuis la dernière visite
 *
 * Score de risque : +3 si température > 38 ; +2 si humidité > 90 ;
 *                   +4 si perte de poids > 2,5 kg ; +1 si dernière visite > 15 jours.
 * Résultat : "id : score" pour les ruches du site avec un score > 0,
 *            triées par score décroissant puis par id croissant.
 */
public class PV {

    // calcule
    public static List<String> calc(List<String[]> l, String s) {
        List<String[]> r = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            String[] x = l.get(i);
            if (x[1].equals(s)) {
                int sc = 0;
                if (Double.parseDouble(x[2]) > 38) {
                    sc = sc + 3; // on ajoute 3
                }
                if (Double.parseDouble(x[3]) > 90) {
                    sc = sc + 2; // on ajoute 2
                }
                if (Double.parseDouble(x[5]) - Double.parseDouble(x[4]) > 2.5) {
                    sc = sc + 4;
                }
                if (Integer.parseInt(x[6]) > 15) {
                    sc = sc + 1;
                }
                if (sc > 0) {
                    r.add(new String[]{x[0], String.valueOf(sc)});
                }
            }
        }
        // tri
        for (int i = 0; i < r.size(); i++) {
            for (int j = i + 1; j < r.size(); j++) {
                int a = Integer.parseInt(r.get(i)[1]);
                int b = Integer.parseInt(r.get(j)[1]);
                if (b > a || (b == a && r.get(j)[0].compareTo(r.get(i)[0]) < 0)) {
                    String[] t = r.get(i);
                    r.set(i, r.get(j));
                    r.set(j, t);
                }
            }
        }
        List<String> res = new ArrayList<>();
        for (String[] y : r) {
            res.add(y[0] + " : " + y[1]);
        }
        return res;
    }

    // ancienne version, ne plus utiliser
    public static int ancienScore(String[] x) {
        int sc = 0;
        if (Double.parseDouble(x[2]) > 40) sc += 5;
        return sc;
    }
}

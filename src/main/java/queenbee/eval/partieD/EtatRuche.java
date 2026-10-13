package queenbee.eval.partieD;

/**
 * Introduce Parameter Object (FOURNI) : remplace le String[] lu par indice.
 * Chaque champ a un nom et un type.
 */
public record EtatRuche(String id,
                        String site,
                        double temperatureC,
                        double humiditePct,
                        double poidsActuelKg,
                        double poidsPrecedentKg,
                        int joursDepuisDerniereVisite) {

    /** Convertit une ligne au format de l'ancien code (voir PV) en EtatRuche. */
    public static EtatRuche depuisLigne(String[] ligne) {
        return new EtatRuche(
                ligne[0],
                ligne[1],
                Double.parseDouble(ligne[2]),
                Double.parseDouble(ligne[3]),
                Double.parseDouble(ligne[4]),
                Double.parseDouble(ligne[5]),
                Integer.parseInt(ligne[6]));
    }

    public double perteDePoidsKg() {
        return poidsPrecedentKg - poidsActuelKg;
    }
}

package queenbee.eval.donnees;

import java.time.LocalDateTime;

/**
 * Une trame envoyee par le capteur IoT d'une ruche (UC-T02).
 * Deux trames identiques (meme ruche, meme horodatage, memes valeurs) sont egales :
 * c'est un rejeu (UC-CY02).
 */
public record Mesure(String rucheId,
                     LocalDateTime horodatage,
                     double temperatureC,
                     double humiditePct,
                     double poidsKg) {
}

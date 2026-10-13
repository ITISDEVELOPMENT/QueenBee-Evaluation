package queenbee.eval.donnees;

/**
 * Une ruche suivie par la plateforme QueenBee.
 *
 * @param id         identifiant unique (ex. "R-BF-01")
 * @param site       nom du rucher (ex. "Koudougou")
 * @param apiculteur nom de l'apiculteur ou de l'apicultrice responsable
 */
public record Ruche(String id, String site, String apiculteur) {
}

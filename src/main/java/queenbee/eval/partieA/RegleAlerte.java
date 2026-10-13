package queenbee.eval.partieA;

import queenbee.eval.donnees.Mesure;

@FunctionalInterface
public interface RegleAlerte {

    boolean estDeclenchee(Mesure mesure);
}

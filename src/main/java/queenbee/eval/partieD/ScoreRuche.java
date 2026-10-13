package queenbee.eval.partieD;

public record ScoreRuche(String idRuche, int score) {

    @Override
    public String toString() {
        return idRuche + " : " + score;
    }
}

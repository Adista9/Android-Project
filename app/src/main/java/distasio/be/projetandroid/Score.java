package distasio.be.projetandroid;

/**
 * Created by Anthony on 24-12-16.
 */

public class Score {
    private int id;
    private String jeu;
    private String score;
    //private Utilisateur utilisateur;
    private int id_utilisateur;

    /***
     * GETTERS AND SETTERS
     */

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getJeu() {
        return jeu;
    }
    public void setJeu(String jeu) {
        this.jeu = jeu;
    }

    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }
    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", jeu='" + jeu + '\'' +
                ", score='" + score + '\'' +
                ", id_utilisateur='" + id_utilisateur + '\'' +
                '}';
    }
}

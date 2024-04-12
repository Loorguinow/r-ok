package fr.did.gameplay;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Score {

    private int left = 0;
    private int right = 0;

    /*
    Score pour qu'un joueur gagne. Si égal à 11, alors jeu en 21 points
    sauf si un joueur arrive à avoir 11 de score avant les 21 points à jouer.
     */
    private static final int WINNER_SCORE = 11;

    public static Score of() {
        return new Score();
    }

    private Score() {}

    /**
     * Incrémente le score du joueur sur l'écran à gauche
     */
    public void leftPoint() {
        this.left++;
        this.checkWinner();
    }

    /**
     * Incrémente le score du joueur sur l'écran à droite
     */
    public void rightPoint() {
        this.right++;
        this.checkWinner();
    }

    private String checkWinner() {
        if (this.left > Score.WINNER_SCORE - 1)
            return "G";
        else if (this.right > Score.WINNER_SCORE - 1)
            return "D";
        else
            return "";
    }

    /**
     * Montre le score sur le terminal
     */
    public void showScore() {
        log.info("Score de la partie : %d - %d".formatted(this.left, this.right));
    }
}

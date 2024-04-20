package fr.did.gameplay;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.jme3.scene.Node;

@Getter
@Slf4j
public class Score {

    private int left = 0;
    private int right = 0;

    private final AssetManager assetManager;

    private float pos_cam;

    private Node node;

    private BitmapText scoreText;

    /*
    Score pour qu'un joueur gagne. Si égal à 11, alors jeu en 21 points
    sauf si un joueur arrive à avoir 11 de score avant les 21 points à jouer.
     */
    private static final int WINNER_SCORE = 11;

    public static Score of(AssetManager assetManager,float pos_cam,Node n) {
        return new Score(assetManager,pos_cam,n);
    }

    private Score(AssetManager assetManager,float pos_cam,Node n) {
        this.assetManager = assetManager;
        this.pos_cam = pos_cam;
        this.node = n;
    }

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

    /**
     * Redémarre le score à 0
     */
    public void restart() {
        this.left = 0;
        this.right = 0;
    }

    public void DisplayScore(){
        // Charger une police de texte
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");

        // Créer un texte pour afficher le score
        this.scoreText = new BitmapText(font, false);
        scoreText.setSize(1f);
        scoreText.setText( this.left+" - "+this.right); // Initialiser le score à 0
        scoreText.setColor(ColorRGBA.White);
        float angleInDegrees = 90; // Angle de rotation en degrés
        scoreText.rotate(0, -(FastMath.DEG_TO_RAD * angleInDegrees), 0); // Convertir en radians avant de passer à la méthode rotate
        scoreText.setLocalTranslation(10, (pos_cam/100) -3, -1); // Positionner le texte en haut à gauche

        // Attacher le texte à la scène
        this.node.attachChild(scoreText);
    }
    public void updateNewScore() {
        scoreText.setText( this.left+" - "+this.right);
    }
}

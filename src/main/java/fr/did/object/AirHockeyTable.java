package fr.did.object;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;

public class AirHockeyTable extends SimpleApplication {
    private Integer pos_x;
    private Integer pos_y;
    private Integer pos_z;

    private Integer largeur;
    private Integer longueur;

    public static void main(String[] args) {
        AirHockeyTable app = new AirHockeyTable();
        app.start();
    }

    public void CreateTable() {
        // Créer un rectangle avec une largeur et une longueur spécifiées
        Quad quad = new Quad(this.longueur, this.largeur);
        Geometry rectangle = new Geometry("Rectangle", quad);

        // Créer un matériau pour le rectangle
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.White); // Couleur du rectangle (ici, blanc)

        // Appliquer le matériau au rectangle
        rectangle.setMaterial(material);

        // Positionner le rectangle à un emplacement spécifique
        rectangle.setLocalTranslation(new Vector3f(this.pos_x, this.pos_y, this.pos_z));

        // Ajouter le rectangle à la scène
        rootNode.attachChild(rectangle);


    }

    @Override
    public void simpleInitApp() {
        // Définir les paramètres de position et de taille de la table
        this.pos_x = 0;
        this.pos_y = 0;
        this.pos_z = 0;
        this.largeur = 3; // Largeur de la table
        this.longueur = 5; // Longueur de la table

        // Créer la table de air hockey
        CreateTable();
    }

}
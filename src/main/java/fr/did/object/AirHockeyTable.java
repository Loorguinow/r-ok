package fr.did.object;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AirHockeyTable extends SimpleApplication {

    private Float largeur;
    private Float longueur;

    private Node node;

    public static void main(String[] args) {
        Node root = new Node();
        AirHockeyTable Air = new AirHockeyTable(5f,2f,root);
        Air.start();
    }

    public void CreateTable() {

        // Créer le sol
        Box groundMesh = new Box(this.largeur , 0.1f, this.longueur );
        Geometry groundGeo = new Geometry("Ground", groundMesh);
        Material groundMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        groundMat.setColor("Color", ColorRGBA.White);
        groundGeo.setMaterial(groundMat);
        rootNode.attachChild(groundGeo);


        // Créer les murs autour de la table

        Material tableMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        tableMat.setColor("Color", ColorRGBA.Gray);

        Box wallMesh = new Box(0.1f, 1f, ((this.longueur/3f)));
        Geometry wallGeo1 = new Geometry("Wall1", wallMesh);
        Geometry wallGeo15 = new Geometry("Wall1", wallMesh);

        wallGeo1.setMaterial(tableMat);
        wallGeo15.setMaterial(tableMat);

        wallGeo1.setLocalTranslation(-(this.largeur), 0f, (this.longueur-(this.longueur/3f)));
        wallGeo15.setLocalTranslation(-(this.largeur), 0f, -(this.longueur-(this.longueur/3f)));

        Geometry wallGeo2 = new Geometry("Wall2", wallMesh);
        Geometry wallGeo25 = new Geometry("Wall2.5", wallMesh);


        wallGeo2.setMaterial(tableMat);
        wallGeo25.setMaterial(tableMat);

        wallGeo2.setLocalTranslation((this.largeur), 0f, -(this.longueur-(this.longueur/3f)));
        wallGeo25.setLocalTranslation((this.largeur), 0f, this.longueur-(this.longueur/3f));



        Box wallMesh2 = new Box(this.largeur, 1f, 0.1f);
        Geometry wallGeo3 = new Geometry("Wall3", wallMesh2);
        Geometry wallGeo4 = new Geometry("Wall4", wallMesh2);

        wallGeo3.setMaterial(tableMat);
        wallGeo4.setMaterial(tableMat);

        wallGeo3.setLocalTranslation(0f, 0f ,this.longueur);
        wallGeo4.setLocalTranslation(0f, 0f,-(this.longueur));

        rootNode.attachChild(wallGeo1);
        rootNode.attachChild(wallGeo15);

        rootNode.attachChild(wallGeo2);
        rootNode.attachChild(wallGeo25);

        rootNode.attachChild(wallGeo3);
        rootNode.attachChild(wallGeo4);

        // Ajouter la cage
        Box cageMesh = new Box(0.1f, 1f, (this.longueur/ 3f)); // Taille de la cage

        Geometry cageGeo1 = new Geometry("Cage", cageMesh);
        Material cageMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cageMat.setColor("Color", ColorRGBA.Red); // Couleur noire pour la cage
        cageGeo1.setMaterial(cageMat);
        cageGeo1.setLocalTranslation(-(this.largeur), 0f, 0); // Positionnée au milieu des murs
        rootNode.attachChild(cageGeo1);

        Geometry cageGeo2 = new Geometry("Cage", cageMesh);
        cageMat.setColor("Color", ColorRGBA.Red); // Couleur noire pour la cage
        cageGeo2.setMaterial(cageMat);
        cageGeo2.setLocalTranslation(this.largeur, 0f, 0f); // Positionnée au milieu des murs
        rootNode.attachChild(cageGeo2);
    }

    @Override
    public void simpleInitApp() {
        // Créer la table de air hockey
        this.CreateTable();
    }

}
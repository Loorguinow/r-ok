package fr.did.object;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import fr.did.exceptions.fr.did.object.FormException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


public class AirHockeyTable{

    private Float longueur;
    private Float largeur;
    private Node node;

    private final AssetManager assetManager;

    private BulletAppState bulletAppState;


    public static AirHockeyTable of(Float longueur, Float largeur,Node node,AssetManager assetManager,BulletAppState bulletAppState){
        AirHockeyTable Air = new AirHockeyTable(longueur,largeur,node,assetManager,bulletAppState);
        Air.CreateTable();
        return Air;
    }

    public AirHockeyTable(Float longueur, Float largeur, Node node, AssetManager assetManager,BulletAppState bulletAppState){
        this.longueur = longueur;
        this.largeur = largeur;
        this.node = node;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
    }


    public void CreateTable() {

        // Créer le sol
        Box groundMesh = new Box(this.largeur , 0.1f, this.longueur);
        Geometry groundGeo = new Geometry("Ground", groundMesh);
        Material groundMat = new Material(this.assetManager, "Common/MatDefs/Light/Lighting.j3md");
        groundMat.setTexture("DiffuseMap",this.assetManager.loadTexture("assets/Textures/Table/texture_table.png"));

        groundGeo.setMaterial(groundMat);

        BoxCollisionShape groundShape = new BoxCollisionShape(new Vector3f(this.largeur, 0.1f, this.longueur));
        RigidBodyControl rigidBodyControl = new RigidBodyControl(groundShape, 0f);
        groundGeo.addControl(rigidBodyControl);

        this.bulletAppState.getPhysicsSpace().add(rigidBodyControl);


        this.node.attachChild(groundGeo);


        //Declaration du materiau
        Material tableMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        tableMat.setTexture("DiffuseMap",this.assetManager.loadTexture("assets/Textures/Table/texture_mur.jpg"));

        //Création du premier Mur
        Box wallMesh = new Box(0.1f, 1f, (this.longueur));
        Geometry wallGeo1 = new Geometry("Wall1", wallMesh);
        wallGeo1.setMaterial(tableMat);
        wallGeo1.setLocalTranslation(-(this.largeur), 0f, 0f);
        BoxCollisionShape wall1Shape = new BoxCollisionShape(new Vector3f(0.1f, 1f, (this.longueur)));
        RigidBodyControl rigidBodyControlWall1 = new RigidBodyControl(wall1Shape, 0f);
        wallGeo1.addControl(rigidBodyControlWall1);
        this.bulletAppState.getPhysicsSpace().add(wallGeo1);
        this.node.attachChild(wallGeo1);

        //Creation du deuxième Mur
        Geometry wallGeo2 = new Geometry("Wall2", wallMesh);
        wallGeo2.setMaterial(tableMat);
        wallGeo2.setLocalTranslation((this.largeur), 0f, 0f);
        BoxCollisionShape wall2Shape = new BoxCollisionShape(new Vector3f(0.1f, 1f, (this.longueur)));
        RigidBodyControl rigidBodyControlWall2 = new RigidBodyControl(wall2Shape, 0f);
        wallGeo2.addControl(rigidBodyControlWall2);
        this.bulletAppState.getPhysicsSpace().add(wallGeo2);
        this.node.attachChild(wallGeo2);



        Box wallMesh2 = new Box(this.largeur/3f, 1f,0.1f);
        BoxCollisionShape wallLargeurShape = new BoxCollisionShape(new Vector3f(this.largeur/3f, 1f,0.1f));

        //CREATION MOITIE DU  3EME MUR
        Geometry wallGeo3 = new Geometry("Wall3", wallMesh2);
        wallGeo3.setMaterial(tableMat);
        wallGeo3.setLocalTranslation(this.largeur-(this.largeur/3f), 0f ,this.longueur);
        RigidBodyControl rigidBodyControlWall3 = new RigidBodyControl(wallLargeurShape, 0f);
        wallGeo3.addControl(rigidBodyControlWall3);
        this.bulletAppState.getPhysicsSpace().add(wallGeo3);
        this.node.attachChild(wallGeo3);

        //Creation de l'autre moitie du 3eme mur
        Geometry wallGeo35 = new Geometry("Wall3.5", wallMesh2);
        wallGeo35.setMaterial(tableMat);
        wallGeo35.setLocalTranslation(-(this.largeur-(this.largeur/3f)), 0f ,this.longueur);
        RigidBodyControl rigidBodyControlWall35 = new RigidBodyControl(wallLargeurShape, 0f);
        wallGeo35.addControl(rigidBodyControlWall35);
        this.bulletAppState.getPhysicsSpace().add(wallGeo35);
        this.node.attachChild(wallGeo35);


        Geometry wallGeo4 = new Geometry("Wall4", wallMesh2);
        wallGeo4.setMaterial(tableMat);
        wallGeo4.setLocalTranslation(this.largeur-(this.largeur/3f), 0f,-(this.longueur));
        RigidBodyControl rigidBodyControlWall4 = new RigidBodyControl(wallLargeurShape, 0f);
        wallGeo4.addControl(rigidBodyControlWall4);
        this.bulletAppState.getPhysicsSpace().add(wallGeo4);
        this.node.attachChild(wallGeo4);


        Geometry wallGeo45 = new Geometry("Wall4.5", wallMesh2);
        wallGeo45.setMaterial(tableMat);
        wallGeo45.setLocalTranslation(-(this.largeur-(this.largeur/3f)), 0f,-(this.longueur));
        RigidBodyControl rigidBodyControlWall45 = new RigidBodyControl(wallLargeurShape, 0f);
        wallGeo45.addControl(rigidBodyControlWall45);
        this.bulletAppState.getPhysicsSpace().add(wallGeo45);
        this.node.attachChild(wallGeo45);


        // Créer la cage
        Box cageMesh = new Box(this.largeur/3f, 1f,0.1f); // Taille de la cage

        Geometry cageGeo1 = new Geometry("Cage", cageMesh);
        Material cageMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cageMat.setColor("Color", ColorRGBA.Red); // Couleur noire pour la cage
        cageGeo1.setMaterial(cageMat);
        cageGeo1.setLocalTranslation(0f, 0f, this.longueur); // Positionnée au milieu des murs
        this.node.attachChild(cageGeo1);

        Geometry cageGeo2 = new Geometry("Cage", cageMesh);
        cageMat.setColor("Color", ColorRGBA.Red); // Couleur noire pour la cage
        cageGeo2.setMaterial(cageMat);
        cageGeo2.setLocalTranslation(0f, 0f, -this.longueur); // Positionnée au milieu des murs
        this.node.attachChild(cageGeo2);
    }


}
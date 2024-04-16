package fr.did.object;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Dome;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
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
        groundGeo.setLocalTranslation(0.0f, -0.1f, 0.0f);
        Material groundMat = new Material(this.assetManager, "Common/MatDefs/Light/Lighting.j3md");
        groundMat.setTexture("DiffuseMap",this.assetManager.loadTexture("assets/Textures/Table/texture_table.png"));

        groundGeo.setMaterial(groundMat);

        BoxCollisionShape groundShape = new BoxCollisionShape(new Vector3f(this.largeur, 0.1f, this.longueur));
        RigidBodyControl rigidBodyControl = new RigidBodyControl(groundShape, 0f);
        groundGeo.addControl(rigidBodyControl);

        this.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        this.node.attachChild(groundGeo);

        Geometry groundGeo2 = new Geometry("SolInvisible", groundMesh);
        Material groundMat2 = new Material(this.assetManager, "Common/MatDefs/Light/Lighting.j3md");
        groundGeo2.setMaterial(groundMat2);
        groundGeo2.setCullHint(Spatial.CullHint.Always);
        groundGeo2.setLocalTranslation(0f,1.22f,0f);
        RigidBodyControl rigidBodyControl2 = new RigidBodyControl(groundShape, 0f);
        groundGeo2.addControl(rigidBodyControl2);

        this.bulletAppState.getPhysicsSpace().add(rigidBodyControl2);
        this.node.attachChild(groundGeo2);



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

        Box cageIn = new Box(0.1f,1f,-2f);
        BoxCollisionShape cageInShape = new BoxCollisionShape(new Vector3f(0.1f,1f,-2f));
        BoxCollisionShape cageInShape2 = new BoxCollisionShape(new Vector3f(this.largeur/3f, 1f,0.1f));

        Geometry cageIn1 = new Geometry("Cage", cageIn);
        cageIn1.setMaterial(tableMat);
        cageIn1.setLocalTranslation(this.largeur/3f+0.1f, 0f, -this.longueur-2.1f);
        RigidBodyControl rigidBodyCageIn1 = new RigidBodyControl(cageInShape, 0f);
        cageIn1.addControl(rigidBodyCageIn1);
        this.bulletAppState.getPhysicsSpace().add(cageIn1);
        this.node.attachChild(cageIn1);


        Geometry cageIn2 = new Geometry("Cage",cageIn);
        cageIn2.setMaterial(tableMat);
        cageIn2.setLocalTranslation(-(this.largeur/3f), 0f, -this.longueur-2.1f);
        RigidBodyControl rigidBodyCageIn2 = new RigidBodyControl(cageInShape, 0f);
        cageIn2.addControl(rigidBodyCageIn2);
        this.bulletAppState.getPhysicsSpace().add(cageIn2);
        this.node.attachChild(cageIn2);


        Geometry cageIn3 = new Geometry("Cage",cageMesh);
        cageIn3.setMaterial(tableMat);
        cageIn3.setLocalTranslation(0.1f, 0f, -this.longueur-4f);
        RigidBodyControl rigidBodyCageIn3 = new RigidBodyControl(cageInShape2, 0f);
        cageIn3.addControl(rigidBodyCageIn3);
        this.bulletAppState.getPhysicsSpace().add(cageIn3);
        this.node.attachChild(cageIn3);

        Geometry cageGeo2 = new Geometry("Cage2", cageMesh);
        cageMat.setColor("Color", ColorRGBA.Red); // Couleur noire pour la cage
        cageGeo2.setMaterial(cageMat);
        cageGeo2.setLocalTranslation(0f, 0f, -this.longueur); // Positionnée au milieu des murs
        this.node.attachChild(cageGeo2);


        Geometry cageIn4 = new Geometry("Cage", cageIn);
        cageIn4.setMaterial(tableMat);
        cageIn4.setLocalTranslation(this.largeur/3f+0.1f, 0f, this.longueur+2.1f);
        RigidBodyControl rigidBodyCageIn4 = new RigidBodyControl(cageInShape, 0f);
        cageIn4.addControl(rigidBodyCageIn4);
        this.bulletAppState.getPhysicsSpace().add(cageIn4);
        this.node.attachChild(cageIn4);


        Geometry cageIn5 = new Geometry("Cage",cageIn);
        cageIn5.setMaterial(tableMat);
        cageIn5.setLocalTranslation(-(this.largeur/3f), 0f, this.longueur+2.1f);
        RigidBodyControl rigidBodyCageIn5 = new RigidBodyControl(cageInShape, 0f);
        cageIn5.addControl(rigidBodyCageIn5);
        this.bulletAppState.getPhysicsSpace().add(cageIn5);
        this.node.attachChild(cageIn5);


        Geometry cageIn6 = new Geometry("Cage",cageMesh);
        cageIn6.setMaterial(tableMat);
        cageIn6.setLocalTranslation(0.1f, 0f, this.longueur+4f);
        RigidBodyControl rigidBodyCageIn6 = new RigidBodyControl(cageInShape2, 0f);
        cageIn6.addControl(rigidBodyCageIn6);
        this.bulletAppState.getPhysicsSpace().add(cageIn6);
        this.node.attachChild(cageIn6);



    }


}
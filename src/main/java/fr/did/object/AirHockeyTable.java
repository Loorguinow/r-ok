package fr.did.object;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Dome;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import fr.did.exceptions.fr.did.object.FormException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public class AirHockeyTable{
    @Getter
    private Float longueur;
    @Getter
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
        float restitution = 3.0f;
        Box groundMesh = new Box(this.largeur , 0.1f, this.longueur);
        Geometry groundGeo = new Geometry("Ground", groundMesh);
        groundGeo.setLocalTranslation(0.0f, -0.1f, 0.0f);
        Material groundMat = new Material(this.assetManager, "Common/MatDefs/Light/Lighting.j3md");
        groundMat.setTexture("DiffuseMap",this.assetManager.loadTexture("assets/Textures/Table/texture_table.png"));

        groundGeo.setMaterial(groundMat);

        BoxCollisionShape groundShape = new BoxCollisionShape(new Vector3f(this.largeur, 0.1f, this.longueur));
        RigidBodyControl rigidBodyControl = new RigidBodyControl(groundShape, 0f);
        rigidBodyControl.setFriction(0f);
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
        rigidBodyControlWall1.setRestitution(restitution);
        rigidBodyControlWall1.setFriction(0f);
        wallGeo1.addControl(rigidBodyControlWall1);
        this.bulletAppState.getPhysicsSpace().add(wallGeo1);
        this.node.attachChild(wallGeo1);

        //Creation du deuxième Mur
        Geometry wallGeo2 = new Geometry("Wall2", wallMesh);
        wallGeo2.setMaterial(tableMat);
        wallGeo2.setLocalTranslation((this.largeur), 0f, 0f);
        BoxCollisionShape wall2Shape = new BoxCollisionShape(new Vector3f(0.1f, 1f, (this.longueur)));
        RigidBodyControl rigidBodyControlWall2 = new RigidBodyControl(wall2Shape, 0f);
        rigidBodyControlWall2.setRestitution(restitution);
        rigidBodyControlWall2.setFriction(0f);
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
        rigidBodyControlWall3.setRestitution(restitution);
        rigidBodyControlWall3.setFriction(0f);
        wallGeo3.addControl(rigidBodyControlWall3);
        this.bulletAppState.getPhysicsSpace().add(wallGeo3);
        this.node.attachChild(wallGeo3);

        //Creation de l'autre moitie du 3eme mur
        Geometry wallGeo35 = new Geometry("Wall3.5", wallMesh2);
        wallGeo35.setMaterial(tableMat);
        wallGeo35.setLocalTranslation(-(this.largeur-(this.largeur/3f)), 0f ,this.longueur);
        RigidBodyControl rigidBodyControlWall35 = new RigidBodyControl(wallLargeurShape, 0f);
        rigidBodyControlWall35.setRestitution(restitution);
        rigidBodyControlWall35.setFriction(0f);
        wallGeo35.addControl(rigidBodyControlWall35);
        this.bulletAppState.getPhysicsSpace().add(wallGeo35);
        this.node.attachChild(wallGeo35);


        Geometry wallGeo4 = new Geometry("Wall4", wallMesh2);
        wallGeo4.setMaterial(tableMat);
        wallGeo4.setLocalTranslation(this.largeur-(this.largeur/3f), 0f,-(this.longueur));
        RigidBodyControl rigidBodyControlWall4 = new RigidBodyControl(wallLargeurShape, 0f);
        rigidBodyControlWall4.setRestitution(restitution);
        rigidBodyControlWall4.setFriction(0f);
        wallGeo4.addControl(rigidBodyControlWall4);
        this.bulletAppState.getPhysicsSpace().add(wallGeo4);
        this.node.attachChild(wallGeo4);


        Geometry wallGeo45 = new Geometry("Wall4.5", wallMesh2);
        wallGeo45.setMaterial(tableMat);
        wallGeo45.setLocalTranslation(-(this.largeur-(this.largeur/3f)), 0f,-(this.longueur));
        RigidBodyControl rigidBodyControlWall45 = new RigidBodyControl(wallLargeurShape, 0f);
        rigidBodyControlWall45.setRestitution(restitution);
        rigidBodyControlWall45.setFriction(0f);
        wallGeo45.addControl(rigidBodyControlWall45);
        this.bulletAppState.getPhysicsSpace().add(wallGeo45);
        this.node.attachChild(wallGeo45);


        // Créer la cage
        Box cageMesh = new Box(this.largeur/3f, 0.6f,0.1f); // Taille de la cage

        Geometry cageGeo1 = new Geometry("Cage", cageMesh);
        Material cageMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cageMat.setColor("Color", ColorRGBA.Red); // Couleur noire pour la cage
        cageGeo1.setMaterial(cageMat);
        cageGeo1.setLocalTranslation(0f, 0f, this.longueur); // Positionnée au milieu des murs
        this.node.attachChild(cageGeo1);

        Box poleMesh = new Box(this.largeur/3f, 0.3f,0.1f); // Taille de la cage
        BoxCollisionShape poleCollionShape = new BoxCollisionShape(new Vector3f(this.largeur/3f, 0.3f,0.1f));

        Geometry poleGeo1 = new Geometry("Cage", poleMesh);
        poleGeo1.setMaterial(tableMat);
        poleGeo1.setLocalTranslation(0f, 0.9f, this.longueur); // Positionnée au milieu des murs
        RigidBodyControl poleRigidBodyControl1 = new RigidBodyControl(poleCollionShape, 0.0f);
        poleGeo1.addControl(poleRigidBodyControl1);
        this.bulletAppState.getPhysicsSpace().add(poleGeo1);
        this.node.attachChild(poleGeo1);

        Box cageIn = new Box(0.1f,1f,2f);
        BoxCollisionShape cageInShape = new BoxCollisionShape(new Vector3f(0.1f,1f,2f));
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


        Geometry cageIn3 = new Geometry("CageIn",cageMesh);
        cageIn3.setMaterial(tableMat);
        cageIn3.setLocalTranslation(0.1f, 0f, -this.longueur-4f);
        RigidBodyControl rigidBodyCageIn3 = new RigidBodyControl(cageInShape2, 0f);
        cageIn3.addControl(rigidBodyCageIn3);
        this.bulletAppState.getPhysicsSpace().add(cageIn3);
        this.node.attachChild(cageIn3);

        Geometry cageGeo2 = new Geometry("Cage", cageMesh);
        cageMat.setColor("Color", ColorRGBA.Red); // Couleur noire pour la cage
        cageGeo2.setMaterial(cageMat);
        cageGeo2.setLocalTranslation(0f, 0f, -this.longueur); // Positionnée au milieu des murs
        this.node.attachChild(cageGeo2);

        Geometry poleGeo2 = new Geometry("Cage", poleMesh);
        poleGeo2.setMaterial(tableMat);
        poleGeo2.setLocalTranslation(0f, 0.9f, -this.longueur); // Positionnée au milieu des murs
        RigidBodyControl poleRigidBodyControl2 = new RigidBodyControl(poleCollionShape, 0.0f);
        poleGeo2.addControl(poleRigidBodyControl2);
        this.bulletAppState.getPhysicsSpace().add(poleGeo2);
        this.node.attachChild(poleGeo2);

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


        Geometry cageIn6 = new Geometry("CageIn2",cageMesh);
        cageIn6.setMaterial(tableMat);
        cageIn6.setLocalTranslation(0.1f, 0f, this.longueur+4f);
        RigidBodyControl rigidBodyCageIn6 = new RigidBodyControl(cageInShape2, 0f);
        cageIn6.addControl(rigidBodyCageIn6);
        this.bulletAppState.getPhysicsSpace().add(cageIn6);
        this.node.attachChild(cageIn6);

        //Coin inférieur gauche
        Box cornerWallMesh = new Box(0.1f, 1f, (this.longueur*( 11.5f/100.0f))); //Théorème de Pythagore
        Geometry cornerWallGeo1 = new Geometry("CornerWall1", cornerWallMesh);
        cornerWallGeo1.setMaterial(tableMat);
        cornerWallGeo1.rotate(0.0f, -45*FastMath.DEG_TO_RAD, 0.0f);
        cornerWallGeo1.setLocalTranslation(-(this.largeur*(835.0f/1000.0f)), 0f, -this.longueur*(920.0f/1000.0f));
        BoxCollisionShape cornerWallShape = new BoxCollisionShape(new Vector3f(0.1f, 1f, (this.longueur*( 17.5f/100.0f))));
        RigidBodyControl rigidBodyControlCornerWall1 = new RigidBodyControl(cornerWallShape, 0f);
        rigidBodyControlCornerWall1.setRestitution(restitution);
        rigidBodyControlCornerWall1.setFriction(0f);
        cornerWallGeo1.addControl(rigidBodyControlCornerWall1);
        this.bulletAppState.getPhysicsSpace().add(cornerWallGeo1);
        this.node.attachChild(cornerWallGeo1);

        //Coin supérieur droit
        Geometry cornerWallGeo2 = new Geometry("CornerWall3", cornerWallMesh);
        cornerWallGeo2.setMaterial(tableMat);
        cornerWallGeo2.rotate(0.0f, -45*FastMath.DEG_TO_RAD, 0.0f);
        cornerWallGeo2.setLocalTranslation(this.largeur*(835.0f/1000.0f), 0f, this.longueur*(920.0f/1000.0f));
        RigidBodyControl rigidBodyControlCornerWall2 = new RigidBodyControl(cornerWallShape, 0f);
        rigidBodyControlCornerWall2.setRestitution(restitution);
        rigidBodyControlCornerWall2.setFriction(0f);
        cornerWallGeo2.addControl(rigidBodyControlCornerWall2);
        this.bulletAppState.getPhysicsSpace().add(cornerWallGeo2);
        this.node.attachChild(cornerWallGeo2);

        //Coin inférieur droit
        Geometry cornerWallGeo3 = new Geometry("CornerWall3", cornerWallMesh);
        cornerWallGeo3.setMaterial(tableMat);
        cornerWallGeo3.rotate(0.0f, 45*FastMath.DEG_TO_RAD, 0.0f);
        cornerWallGeo3.setLocalTranslation(-this.largeur*(835.0f/1000.0f), 0f, this.longueur*(920.0f/1000.0f));
        RigidBodyControl rigidBodyControlCornerWall3 = new RigidBodyControl(cornerWallShape, 0f);
        rigidBodyControlCornerWall3.setRestitution(restitution);
        rigidBodyControlCornerWall3.setFriction(0f);
        cornerWallGeo3.addControl(rigidBodyControlCornerWall3);
        this.bulletAppState.getPhysicsSpace().add(cornerWallGeo3);
        this.node.attachChild(cornerWallGeo3);

        //Coin supérieur gauche
        Geometry cornerWallGeo4 = new Geometry("CornerWall4", cornerWallMesh);
        cornerWallGeo4.setMaterial(tableMat);
        cornerWallGeo4.rotate(0.0f, 45*FastMath.DEG_TO_RAD, 0.0f);
        cornerWallGeo4.setLocalTranslation(this.largeur*(835.0f/1000.0f), 0f, -this.longueur*(920.0f/1000.0f));
        RigidBodyControl rigidBodyControlCornerWall4 = new RigidBodyControl(cornerWallShape, 0f);
        rigidBodyControlCornerWall4.setRestitution(restitution);
        rigidBodyControlCornerWall4.setFriction(0f);
        cornerWallGeo4.addControl(rigidBodyControlCornerWall4);
        this.bulletAppState.getPhysicsSpace().add(cornerWallGeo4);
        this.node.attachChild(cornerWallGeo4);

        // Créer la géométrie du sol de la cage
        Box cageFloorMesh = new Box(this.largeur / 3f, 0.1f, this.longueur/8f); // Taille du sol de la cage
        Geometry cageFloorGeo = new Geometry("CageFloor", cageFloorMesh);
        cageFloorGeo.setMaterial(tableMat); // Appliquer le même matériau que les autres parties de la cage
        cageFloorGeo.setLocalTranslation(0.1f, -0.6f, -this.longueur-1.9f); // Positionner sous les autres parties de la cage

        // Créer une forme de collision pour le sol de la cage
        BoxCollisionShape cageFloorShape = new BoxCollisionShape(new Vector3f(this.largeur / 3f, 0.1f, this.longueur/8f));

        // Ajouter un contrôle de corps rigide pour le sol de la cage
        RigidBodyControl cageFloorControl = new RigidBodyControl(cageFloorShape, 0f); // Masse de 0 car le sol est statique
        cageFloorGeo.addControl(cageFloorControl);

        // Créer le matériau pour le sol de la cage avec une couleur verte
        Material cageFloorMat = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cageFloorMat.setColor("Color", ColorRGBA.Green); // Définir la couleur verte

        // Appliquer le matériau au sol de la cage
        cageFloorGeo.setMaterial(cageFloorMat);

        // Ajouter le sol de la cage à la scène et à l'espace de physique
        this.node.attachChild(cageFloorGeo);
        this.bulletAppState.getPhysicsSpace().add(cageFloorControl);


        Geometry cageUp1 = new Geometry("CageUp", cageFloorMesh);
        cageUp1.setMaterial(tableMat); // Appliquer le même matériau que les autres parties de la cage
        cageUp1.setLocalTranslation(0.1f, 1f, -this.longueur-2.5f);

        this.node.attachChild(cageUp1);

        Geometry cageFloorGeo2 = new Geometry("CageFloor2", cageFloorMesh);
        cageFloorGeo2.setMaterial(tableMat); // Appliquer le même matériau que les autres parties de la cage
        cageFloorGeo2.setLocalTranslation(0.1f, -0.6f, this.longueur+1.9f); // Positionner sous les autres parties de la cage

        // Créer une forme de collision pour le sol de la cage


        // Ajouter un contrôle de corps rigide pour le sol de la cage
        RigidBodyControl cageFloorControl2 = new RigidBodyControl(cageFloorShape, 0f); // Masse de 0 car le sol est statique
        cageFloorGeo2.addControl(cageFloorControl2);

        // Appliquer le matériau au sol de la cage
        cageFloorGeo2.setMaterial(cageFloorMat);

        this.node.attachChild(cageFloorGeo2);
        this.bulletAppState.getPhysicsSpace().add(cageFloorControl2);

        Geometry cageUp2 = new Geometry("CageUp", cageFloorMesh);
        cageUp2.setMaterial(tableMat); // Appliquer le même matériau que les autres parties de la cage
        cageUp2.setLocalTranslation(0.1f, 1f, this.longueur+2.5f);

        this.node.attachChild(cageUp2);

    }


}
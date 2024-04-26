package fr.did.gameplay;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import fr.did.exceptions.fr.did.object.FormException;
import fr.did.object.*;
import fr.did.object.bonus.Bonus;
import fr.did.object.bonus.PillarBonus;
import fr.did.object.bonus.RacketSizeBonus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Getter
public class Session {

    private final Node node;
    private final AssetManager assetManager;

    private final BulletAppState bulletAppState;
    private final Camera camera;
    private final FlyByCamera cameraControler;
    private final Score score;
    private final AirHockeyTable table;
    private List<Racket> rackets;
    private List<Puck> pucks;
    private List<Bonus> bonuses;
    private boolean multiplePucksOrNot = false;
    private boolean multipleBonuses = false;
    private Random random = new Random();

    public static final float TABLE_LENGTH = 20.0f;
    public static final float TABLE_WIDTH = 10.0f;
    private RigidBodyControl wallCenterControl;

    public static Session of(Node node, AssetManager assetManager, BulletAppState bulletAppState, Camera camera, FlyByCamera cameraControler) {
        Session session = new Session(node, assetManager, bulletAppState, camera, cameraControler);
        session.spawnLights();
        session.spawnRackets();
        session.spawnPucks();
        session.spawnScore();
        return session;
    }

    private Session(Node node, AssetManager assetManager, BulletAppState bulletAppState, Camera camera, FlyByCamera cameraControler) {
        this.node = node;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.camera = camera;
        this.cameraControler = cameraControler;
        this.score = Score.of(assetManager,camera.getHeight(),this.node);
        this.table = AirHockeyTable.of(Session.TABLE_LENGTH, Session.TABLE_WIDTH, this.node, this.assetManager, this.bulletAppState);;
        try {
            this.rackets = new ArrayList<>(){};
            this.rackets.add(Racket.of(MobileObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false));


            this.rackets.add(Racket.of(MobileObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false));



            this.pucks = new ArrayList<>();
            this.pucks.add(Puck.of(MobileObjectForm.CYLINDER, node, assetManager, bulletAppState, false));
            //this.pucks.add(Puck.of(MobileObjectForm.CYLINDER, node, assetManager, bulletAppState, false));
            //this.pucks.add(Puck.of(MobileObjectForm.CYLINDER, node, assetManager, bulletAppState, false));


            createMiddleWall();

            this.bonuses = new ArrayList<>();
        } catch (FormException e) {
            log.error("Objet d'une forme inconnue", e);
        }
        this.initCamera();
    }

    private void  createMiddleWall(){
        Box wallMeshInvisibleWall = new Box(this.table.getLargeur(), 1f,0.1f);

        Geometry wallCenter  = new Geometry("WallCenter", wallMeshInvisibleWall);

        Material invisibleMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        invisibleMaterial.setColor("Color", new ColorRGBA(0,0,0,0)); // Réglez l'alpha sur 0 pour le rendre transparent
        invisibleMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha); // Activez la transparence

        // Appliquez le matériau à la géométrie
        wallCenter.setMaterial(invisibleMaterial);

        // Créer une forme de collision pour le sol de la cage
        BoxCollisionShape wallCenterShape = new BoxCollisionShape(new Vector3f(this.table.getLargeur(), 1f,0.1f));


        // Ajouter un contrôle de corps rigide pour le sol de la cage
        this.wallCenterControl = new RigidBodyControl(wallCenterShape, 0f);// Masse de 0 car le sol est statique
        this.wallCenterControl.removeCollideWithGroup(pucks.get(0).getRigidBodyControl().getCollisionGroup());
        this.wallCenterControl.setCollisionGroup(1);


        for(Puck p :pucks){
        p.getRigidBodyControl().addCollideWithGroup(3);
        p.getRigidBodyControl().removeCollideWithGroup(wallCenterControl.getCollisionGroup());}

        wallCenter.addControl(wallCenterControl);
        this.node.attachChild(wallCenter);
        this.bulletAppState.getPhysicsSpace().add(wallCenterControl);
    }

    /**
     * Donne les bons paramètres à la caméra
     */
    private void initCamera() {
        this.cameraControler.setMoveSpeed(10);
        this.camera.setLocation(new Vector3f(-1.8f*Session.TABLE_WIDTH, Session.TABLE_WIDTH*3.0f, 0.0f));
        this.camera.lookAt(new Vector3f(0f,0f,0f),Vector3f.UNIT_X);
    }

    /**
     * Fait apparaître les palets au centre du terrain
     * Le centre du terrain est en (0,0,0)
     */
    private void spawnPucks() {
        int numberPucks = this.pucks.size();
        if (numberPucks == 1)
            this.pucks.get(0).spawnObject();
        else if (numberPucks == 2) {
            int i;
            for (i=0;i<2;i++)
                this.pucks.get(i).spawnObject(new Vector3f(-(2*TABLE_WIDTH/4) + 2*i*(2*TABLE_WIDTH/4), 0.0f, 0.0f));
        }
        else {
            int i;
            for (i=0;i<3;i++)
                this.pucks.get(i).spawnObject(new Vector3f(-(2*TABLE_WIDTH/4) + i*(2*TABLE_WIDTH/4), 0.0f, 0.0f));
        }
    }


    /**
     * Fait apparaître les raquettes
     */
    private void spawnRackets() {
        this.rackets.get(0).spawnObject(new Vector3f(0.0f, 0.0f, ((-Session.TABLE_LENGTH/4)*3)));
        this.rackets.get(1).spawnObject(new Vector3f(0.0f, 0.0f, ((Session.TABLE_LENGTH/4)*3)));
    }

    /**
     * Créer l'environnement lumineux du jeu
     */
    private void spawnLights() {
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.DarkGray.mult(0.1f));
        DirectionalLight lamp = new DirectionalLight();
        lamp.setDirection(new Vector3f(-0.1f, -1.0f, -1.0f).normalizeLocal());
        this.node.addLight(ambientLight);
        this.node.addLight(lamp);
    }

    private void spawnScore(){
        this.score.DisplayScore();
    }

    public void maybeMultiplePucks() {
        this.multiplePucksOrNot = (this.random.nextInt(1,11) <= 3);
        if (this.multiplePucksOrNot) {
            int twoOrThree = this.random.nextInt(2,3);
            if (twoOrThree == 2) {
                try {
                    Puck puck = this.pucks.get(0);
                    puck.getBulletAppState().getPhysicsSpace().remove(puck.getRigidBodyControl());
                    puck.getNode().detachChild(puck.getGeometry());
                    this.getPucks().remove(0);
                    this.pucks.add(Puck.of(MobileObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false));
                    this.pucks.add(Puck.of(MobileObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false));
                    for(Puck p :this.pucks){
                        p.getRigidBodyControl().addCollideWithGroup(3);
                        p.getRigidBodyControl().removeCollideWithGroup(wallCenterControl.getCollisionGroup());}

                    this.spawnPucks();
                } catch (FormException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    Puck puck = this.pucks.get(0);
                    puck.getBulletAppState().getPhysicsSpace().remove(puck.getRigidBodyControl());
                    puck.getNode().detachChild(puck.getGeometry());
                    this.getPucks().remove(0);
                    this.pucks.add(Puck.of(MobileObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false));
                    this.pucks.add(Puck.of(MobileObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false));
                    this.pucks.add(Puck.of(MobileObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false));
                    for(Puck p :this.pucks){
                        p.getRigidBodyControl().addCollideWithGroup(3);
                        p.getRigidBodyControl().removeCollideWithGroup(wallCenterControl.getCollisionGroup());}

                    this.spawnPucks();
                } catch (FormException e) {
                    e.printStackTrace();
                }
            }
        this.multiplePucksOrNot = false;
        }
    }

    public void maybeBonuses() {
        boolean bonusesOrNot = (this.random.nextInt(1,11) <= 3);
        if (bonusesOrNot) {
            this.multipleBonuses = this.random.nextBoolean();
            int nBonuses = 1;
            if (this.multipleBonuses) nBonuses++;
            int i;
            int whichBonus;
            for (i=0;i<nBonuses;i++) {
                //System.out.println(i);
                whichBonus = this.random.nextInt(1,3);
                if (whichBonus == 1) {
                    try {
                        this.bonuses.add(RacketSizeBonus.of(StaticObjectForm.CUBE, true, this));
                        //System.out.println("TEST1");
                    } catch (FormException e) {
                        e.printStackTrace();
                    }
                } else if (whichBonus == 2) {
                    try {
                        this.bonuses.add(PillarBonus.of(this));
                        //System.out.println("TEST2");
                    } catch (FormException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void removeBonuses() {
        int i;
        int nbBonuses = this.bonuses.size();
        for (i=0;i<nbBonuses;i++) {
            this.bonuses.get(i).removeEffect();
            this.bonuses.remove(this.bonuses.get(i));
            i--;
            nbBonuses--;
            System.out.println(this.bonuses);
        }
    }
}

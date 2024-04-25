package fr.did.gameplay;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import fr.did.exceptions.fr.did.object.FormException;
import fr.did.object.AirHockeyTable;
import fr.did.object.ObjectForm;
import fr.did.object.Puck;
import fr.did.object.Racket;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j@Getter
public class Session {

    private final Node node;
    private final AssetManager assetManager;

    private final BulletAppState bulletAppState;
    private final Camera camera;
    private final FlyByCamera cameraControler;
    private final Score score;
    private final AirHockeyTable table;
    public List<Racket> rackets;
    private List<Puck> pucks;

    public static final float TABLE_LENGTH = 20.0f;
    public static final float TABLE_WIDTH = 10.0f;

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
            this.rackets.add(Racket.of(ObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false));


            this.rackets.add(Racket.of(ObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false));



            this.pucks = new ArrayList<>();
            this.pucks.add(Puck.of("cylinder", node, assetManager, bulletAppState, false));
        } catch (FormException e) {
            log.error("Objet d'une forme inconnue", e);
        }
        this.initCamera();
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
                this.pucks.get(i).spawnObject(new Vector3f(0.0f, 0.0f, -(TABLE_WIDTH/4) + 2*i*(TABLE_WIDTH/4)));
        }
        else {
            int i;
            for (i=0;i<3;i++)
                this.pucks.get(i).spawnObject(new Vector3f(0.0f, 0.0f, -(TABLE_WIDTH/4) + i*(TABLE_WIDTH/4)));
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
}

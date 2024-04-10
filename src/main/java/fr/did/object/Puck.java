package fr.did.object;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import fr.did.exceptions.fr.did.object.FormException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Puck {

    @Setter(AccessLevel.NONE)
    private static int idCounter = 0;

    private int id;
    private Geometry geometry;
    private Material material;
    private final AssetManager assetManager;
    private Node node;
    @Setter(AccessLevel.NONE)
    private float size = 100; //percentage
    private float mass = 10.0f;

    private CollisionShape collisionShape;
    private RigidBodyControl rigidBodyControl;

    public static Puck of(String form, Node node, AssetManager assetManager, boolean spawnOrNot) throws FormException {
        Puck puck = new Puck(node, assetManager);
        puck.constructPhysicalPuck(form, spawnOrNot);
        return puck;
    }

    public static Puck of(String form, Node node, AssetManager assetManager, boolean spawnOrNot, int size) throws FormException {
        Puck puck = new Puck(node, assetManager, size);
        puck.constructPhysicalPuck(form, spawnOrNot);
        return puck;
    }

    /**
     * Construit le palet et le fait potentiellement apparaître
     * @param form forme du palet
     * @param spawnOrNot Apparition ou non du palet
     */
    private void constructPhysicalPuck(String form, boolean spawnOrNot) throws FormException {
        Puck.formConstruct(form, this);
        this.collisionShape = CollisionShapeFactory.createBoxShape(this.getGeometry());
        this.rigidBodyControl = new RigidBodyControl(this.getCollisionShape(), this.getMass());
        this.geometry.addControl(this.rigidBodyControl);
        if (spawnOrNot) this.spawnPuck();
    }

    private Puck(Node node, AssetManager assetManager) {
        this.id = Puck.idCounter;
        Puck.idCounter++;
        this.material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        this.node = node;
        this.assetManager = assetManager;
    }

    private Puck(Node node, AssetManager assetManager, int size) {
        this(node, assetManager);
        this.size = size;
    }

    /**
     *
     * @param form Forme du palet
     * @param puck Instance de Puck pour créer la géométrie
     */
    public static void formConstruct(String form, Puck puck) throws FormException{

        if (form.equals("cylinder")) puck.createCylinder();
        else if (form.equals("prism")) puck.createPrism();
        else throw new FormException();
    }

    private void createPrism() {
        Cylinder cylinder = new Cylinder(2,3,this.size / 100.0f,1, true);
        this.geometry = new Geometry("Prism" + this.getId(), cylinder);
    }

    private void createCylinder() {
        Cylinder cylinder = new Cylinder(100,100,this.size / 100.0f,1, true);
        this.geometry = new Geometry("Cylinder" + this.getId(), cylinder);
        log.info(this.getGeometry().getName());
    }

    /**
     * Applique le matériel et la texture au palet
     */
    public void setItems() {
        this.geometry.setMaterial(this.material);
        this.material.setTexture("DiffuseMap", this.assetManager.loadTexture("assets/Textures/Puck/Blackish.jpg"));
    }

    /**
     * Fait apparaître le palet, en le plaçant à une position donnée
     * sur le terrain via une translation partant du centre du noeud,
     * et en lui donnant son aspect visuel.
     */
    public void spawnPuck(Vector3f translation) {
        this.geometry.rotate(0.0f, 0.0f, 90.0f * FastMath.DEG_TO_RAD);
        this.geometry.rotate(0.0f, 90.0f * FastMath.DEG_TO_RAD, 0.0f);
        this.geometry.setLocalTranslation(translation);
        this.setItems();
        this.node.attachChild(this.geometry);
    }

    /**
     * Fait apparaître le palet, en le plaçant au centre du noeud,
     * et en lui donnant son aspect visuel.
     */
    public void spawnPuck() {
        this.geometry.rotate(0.0f, 0.0f, 90.0f * FastMath.DEG_TO_RAD);
        this.geometry.rotate(0.0f, 90.0f * FastMath.DEG_TO_RAD, 0.0f);
        this.setItems();
        this.node.attachChild(this.geometry);
    }
}

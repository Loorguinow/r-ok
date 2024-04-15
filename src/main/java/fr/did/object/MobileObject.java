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

@Getter
@Setter
public abstract class MobileObject {

    @Setter(AccessLevel.NONE)
    protected static int idCounter = 0;

    protected int id;
    protected Geometry geometry;
    protected Material material;
    protected final AssetManager assetManager;
    protected Node node;
    @Setter(AccessLevel.NONE)
    protected float height;
    protected float globalSize = 100; //percentage
    protected float mass = 10.0f;

    protected CollisionShape collisionShape;
    protected RigidBodyControl rigidBodyControl;

    protected MobileObject(Node node, AssetManager assetManager) {
        this.id = MobileObject.idCounter;
        MobileObject.idCounter++;
        this.material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        this.node = node;
        this.assetManager = assetManager;
    }

    /**
     * Construit l'objet en lui appliquant la physique et en
     * le faisant apparaître si nécessaire
     * @param form forme de l'objet (prism, cylinder)
     * @param spawnOrNot apparition ou non de l'objet
     * @throws FormException exception de la forme de l'objet
     */
    protected void constructPhysicalObject(String form, boolean spawnOrNot) throws FormException {
        MobileObject.formConstruct(form, this);
        this.collisionShape = CollisionShapeFactory.createDynamicMeshShape(this.getGeometry());
        this.rigidBodyControl = new RigidBodyControl(this.getCollisionShape(), this.getMass());
        this.geometry.addControl(this.rigidBodyControl);
        if (spawnOrNot) this.spawnObject();
    }

    /**
     * Construit la forme de l'objet mobile
     * @param form Forme de l'objet mobile
     * @param mobileObject Instance de MobileObject pour créer la géométrie
     */
    protected static void formConstruct(String form, MobileObject mobileObject) throws FormException{

        if (form.equals("cylinder")) mobileObject.createCylinder();
        else if (form.equals("prism")) mobileObject.createPrism();
        else throw new FormException();
    }

    /**
     * Créer la géométrie en forme de prisme
     */
    protected void createPrism() {
        Cylinder cylinder = new Cylinder(2,3,this.globalSize / 100.0f, this.height, true);
        this.geometry = new Geometry("Prism" + this.getId(), cylinder);
    }

    /**
     * Créer la géométrie en forme de cylindre
     */
    protected void createCylinder() {
        Cylinder cylinder = new Cylinder(100,100,this.globalSize / 100.0f,this.height, true);
        this.geometry = new Geometry("Cylinder" + this.getId(), cylinder);
    }

    /**
     * Appose un matériau et la texture sur la forme géométrique.
     */
    protected void setItems() {
        this.geometry.setMaterial(this.material);
        this.setTextures();
    }

    protected abstract void setTextures();

    /**
     * Fait apparaître un objet mobile, en le plaçant à une position donnée
     * sur le terrain via une translation partant du centre du noeud,
     * et en lui appliquant son aspect visuel.
     */
    public void spawnObject(Vector3f translation) {
        this.geometry.removeControl(this.rigidBodyControl);
        this.geometry.rotate(0.0f, 0.0f, 90.0f * FastMath.DEG_TO_RAD);
        this.geometry.rotate(0.0f, 90.0f * FastMath.DEG_TO_RAD, 0.0f);
        this.geometry.setLocalTranslation(translation);
        this.setItems();
        this.geometry.addControl(this.rigidBodyControl);
        this.node.attachChild(this.geometry);
    }

    /**
     * Fait apparaître l'objet mobile, en le plaçant au centre du noeud,
     * et en lui appliquant son aspect visuel.
     */
    protected void spawnObject() {
        this.geometry.removeControl(this.rigidBodyControl);
        this.geometry.rotate(0.0f, 0.0f, 90.0f * FastMath.DEG_TO_RAD);
        this.geometry.rotate(0.0f, 90.0f * FastMath.DEG_TO_RAD, 0.0f);
        this.setItems();
        this.geometry.addControl(this.rigidBodyControl);
        this.node.attachChild(this.geometry);
    }
}

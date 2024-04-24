package fr.did.object;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import fr.did.exceptions.fr.did.object.FormException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PhysicalObject {

    @Setter(AccessLevel.NONE)
    protected static int idCounter = 0;

    protected int id;
    protected Geometry geometry;
    protected Material material;
    protected Node node;
    protected final AssetManager assetManager;
    protected final BulletAppState bulletAppState;
    @Setter(AccessLevel.NONE)

    protected float height;
    protected float globalSize = 100; //percentage
    protected float mass = 10.0f;

    protected CollisionShape collisionShape;
    protected RigidBodyControl rigidBodyControl;

    protected PhysicalObject(Node node, AssetManager assetManager, BulletAppState bulletAppState) {
        this.id = PhysicalObject.idCounter;
        PhysicalObject.idCounter++;
        this.material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        this.node = node;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
    }

    protected abstract void constructPhysicalObject(String form, boolean spawnOrNot) throws FormException;

    /**
     * Appose un matériau et la texture sur la forme géométrique.
     */
    protected void setItems() {
        this.geometry.setMaterial(this.material);
        this.setTextures();
    }

    protected abstract void setTextures();

    /**
     * Relie l'objet avec les physics, au gérant d'états des physics
     */
    protected void linkPhysics() {
        this.bulletAppState.getPhysicsSpace().add(this.rigidBodyControl);
    }

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
        this.linkPhysics();
        this.node.attachChild(this.geometry);
    }

    /**
     * Fait apparaître l'objet mobile, en le plaçant au centre du noeud,
     * et en lui appliquant son aspect visuel.
     */
    public void spawnObject() {
        this.geometry.removeControl(this.rigidBodyControl);
        this.geometry.rotate(0.0f, 0.0f, 90.0f * FastMath.DEG_TO_RAD);
        this.geometry.rotate(0.0f, 90.0f * FastMath.DEG_TO_RAD, 0.0f);
        this.setItems();
        this.geometry.addControl(this.rigidBodyControl);
        this.linkPhysics();
        this.node.attachChild(this.geometry);
    }
}

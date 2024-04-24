package fr.did.object;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import fr.did.exceptions.fr.did.object.FormException;

public class Bonus extends PhysicalObject{

    public static Bonus of(Node node, AssetManager assetManager, BulletAppState bulletAppState) {
        return new Bonus(node, assetManager, bulletAppState);
    }

    private Bonus(Node node, AssetManager assetManager, BulletAppState bulletAppState) {
        super(node, assetManager, bulletAppState);
    }

    @Override
    protected void constructPhysicalObject(String form, boolean spawnOrNot) {
        float length = this.globalSize/100.0f;
        Box box = new Box(length, this.height, length);
        this.geometry = new Geometry(String.format("Bonus%d", this.id), box);
        this.collisionShape = CollisionShapeFactory.createDynamicMeshShape(this.getGeometry());
        this.rigidBodyControl = new RigidBodyControl(this.getCollisionShape(), this.getMass());
        this.geometry.addControl(this.rigidBodyControl);
        if (spawnOrNot) this.spawnObject();
    }

    @Override
    protected void setTextures() {
        //todo
    }
}

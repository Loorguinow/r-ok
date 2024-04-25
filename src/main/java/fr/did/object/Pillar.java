package fr.did.object;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import fr.did.exceptions.fr.did.object.FormException;
import fr.did.gameplay.Session;
import fr.did.object.bonus.Bonus;

import java.util.Random;

public class Pillar extends PhysicalObject{

    protected float xWidth = Session.TABLE_WIDTH; //TABLE_WIDTH == half of the width of the table
    protected float zLength = Session.TABLE_LENGTH; //Same for length
    private Random random;
    private String side;

    public static Pillar of(Node node, AssetManager assetManager, BulletAppState bulletAppState, String side) throws FormException{
        Pillar pillar = new Pillar(node, assetManager, bulletAppState, side);
        pillar.constructPhysicalObject(StaticObjectForm.CYLINDER, true);
        return pillar;
    }

    private Pillar(Node node, AssetManager assetManager, BulletAppState bulletAppState, String side) {
        super(node, assetManager, bulletAppState);
        this.random = new Random();
        this.side = side;
        this.height = 0.8f;
        this.mass = 0.0f;
        this.globalSize = 200;
    }

    @Override
    protected void constructPhysicalObject(String form, boolean spawnOrNot) throws FormException {
        Pillar.formConstruct(form, this);
        this.collisionShape = CollisionShapeFactory.createDynamicMeshShape(this.getGeometry());
        this.rigidBodyControl = new RigidBodyControl(this.getCollisionShape(), this.getMass());
        this.geometry.addControl(this.rigidBodyControl);
        if (spawnOrNot) this.randomSpawn();
    }

    private static void formConstruct(String form, Pillar pillar) throws FormException{

        if (form.equals(StaticObjectForm.CYLINDER)) pillar.createCylinder();
        else throw new FormException();
    }

    protected void createCylinder() {
        Cylinder cylinder = new Cylinder(2, 3, this.globalSize / 100.0f, this.height, true);
        this.geometry = new Geometry("Pillar" + this.getId(), cylinder);
    }

    protected void randomSpawn() {
        this.geometry.removeControl(this.rigidBodyControl);
        boolean xSign = this.random.nextBoolean();
        int zSign;
        if (this.side == "L")
            zSign = -1;
        else
            zSign = 1;
        float x = this.random.nextInt((int)this.xWidth +1-2); //+1 car de 0 à this.xWidth -1; -2 car éloignement des bords
        float z = this.random.nextInt((int)this.zLength/2, (int)this.zLength +1-2); //Pareil mais pour la longueur
        if (!xSign) x = -x;
        z = z*zSign;
        this.geometry.addControl(this.rigidBodyControl);
        this.spawnObject(new Vector3f(x, 0.0f, z));
    }

    @Override
    protected void setTextures() {
        this.material.setTexture("DiffuseMap", this.assetManager.loadTexture("assets/Textures/Pillar/Pillar.jpg"));
    }
}

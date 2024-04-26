package fr.did.object.bonus;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import fr.did.exceptions.fr.did.object.FormException;
import fr.did.gameplay.Session;
import fr.did.object.PhysicalObject;
import fr.did.object.StaticObjectForm;
import lombok.Getter;

import java.util.Random;

@Getter
public abstract class Bonus extends PhysicalObject {

    protected float xWidth = Session.TABLE_WIDTH; //TABLE_WIDTH == half of the width of the table
    protected float zLength = Session.TABLE_LENGTH; //Same for length
    protected Random random = new Random();

    protected Geometry collisionGeometry;

    protected Bonus(Session session) {
        super(session.getNode(), session.getAssetManager(), session.getBulletAppState());
        this.mass = 0.0f;
        this.height = 0.4f;
    }

    @Override
    protected void constructPhysicalObject(String form, boolean spawnOrNot) throws FormException{
        Bonus.formConstruct(form, this);
        this.collisionShape = CollisionShapeFactory.createDynamicMeshShape(this.getCollisionGeometry());
        this.rigidBodyControl = new RigidBodyControl(this.getCollisionShape(), this.getMass());
        this.collisionGeometry.addControl(this.rigidBodyControl);
        if (spawnOrNot) this.randomSpawn();
    }

    @Override
    protected void setItems() {
        super.setItems();
        this.setCollisionItems();
    }

    private void setCollisionItems() {
        this.collisionGeometry.setMaterial(this.material);
    }

    protected static void formConstruct(String form, Bonus bonus) throws FormException {

        if (form.equals(StaticObjectForm.CUBE)) bonus.createCube();
        else throw new FormException();
    }

    protected void createCube() {
        float length = this.globalSize/150.0f;
        float collisionLength = length/8;
        Box box = new Box(length, length, this.height);
        Box collisionBox = new Box(collisionLength, collisionLength, this.height);
        this.geometry = new Geometry(String.format("Bonus%d", this.id), box);
        this.collisionGeometry = new Geometry(String.format("CollisionBonus%d", this.id), collisionBox);
    }

    /**
     * Fait apparaître sur le terrain de manière aléatoire le bonus
     */
    protected void randomSpawn() {
        this.collisionGeometry.removeControl(this.rigidBodyControl);
        boolean xSign = this.random.nextBoolean();
        boolean zSign = this.random.nextBoolean();
        float x = this.random.nextInt((int)this.xWidth +1-2); //+1 car de 0 à this.xWidth -1; -2 car éloignement des bords
        float z = this.random.nextInt((int)this.zLength +1-2); //Pareil mais pour la longueur
        if (!xSign) x = -x;
        if (!zSign) z = -z;
        this.collisionGeometry.addControl(this.rigidBodyControl);
        this.spawnObject(new Vector3f(x, 0.0f, z));
    }

    @Override
    public void spawnObject(Vector3f translation) {
        super.spawnObject(translation);
        this.geometry.rotate(0.0f, 0.0f, 180.0f * FastMath.DEG_TO_RAD);
        this.collisionGeometry.removeControl(this.rigidBodyControl);
        this.collisionGeometry.rotate(0.0f, 0.0f, 90.0f * FastMath.DEG_TO_RAD);
        this.collisionGeometry.rotate(0.0f, 90.0f * FastMath.DEG_TO_RAD, 0.0f);
        this.collisionGeometry.setLocalTranslation(translation);
        this.setItems();
        this.collisionGeometry.addControl(this.rigidBodyControl);
        this.linkPhysics();
        this.node.attachChild(this.collisionGeometry);
    }

    public abstract void makeEffect(String whichPlayer);

    public abstract void removeEffect();
}

package fr.did.object;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
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
public abstract class MobileObject extends PhysicalObject{

    protected MobileObject(Node node, AssetManager assetManager, BulletAppState bulletAppState) {
        super(node, assetManager, bulletAppState);
    }

    /**
     * Construit l'objet en lui appliquant la physique et en
     * le faisant apparaître si nécessaire
     * @param form forme de l'objet (prism, cylinder)
     * @param spawnOrNot apparition ou non de l'objet
     * @throws FormException exception de la forme de l'objet
     */
    @Override
    protected void constructPhysicalObject(String form, boolean spawnOrNot) throws FormException {
        MobileObject.formConstruct(form, this);
        this.collisionShape = CollisionShapeFactory.createDynamicMeshShape(this.getGeometry());
        this.rigidBodyControl = new RigidBodyControl(this.getCollisionShape(), this.getMass());
        if(form == ObjectForm.CYLINDER) {
            rigidBodyControl.setAngularFactor(0f);
        }
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
}

package fr.did.object;


import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import fr.did.exceptions.fr.did.object.FormException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Racket extends MobileObject{

    private static boolean firstPlayer = true;

    public static Racket of(String form, Node node, AssetManager assetManager, BulletAppState bulletAppState, boolean spawnOrNot) throws FormException {
        Racket racket = new Racket(node, assetManager, bulletAppState);
        racket.constructPhysicalObject(form, spawnOrNot);
        return racket;
    }


    public static Racket of(String form, Node node, AssetManager assetManager, BulletAppState bulletAppState, boolean spawnOrNot, int size) throws FormException {
        Racket racket = new Racket(node, assetManager, bulletAppState, size);
        racket.constructPhysicalObject(form, spawnOrNot);
        return racket;
    }

    private Racket(Node node, AssetManager assetManager, BulletAppState bulletAppState) {
        super(node, assetManager, bulletAppState);
        this.height = 0.7f;
        this.mass = 10f;
    }

    private Racket(Node node, AssetManager assetManager, BulletAppState bulletAppState, int globalSize) {
        this(node, assetManager, bulletAppState);
        this.globalSize = globalSize;
    }

    @Override
    protected void setTextures() {
        if (Racket.firstPlayer) {
            this.material.setTexture("DiffuseMap", this.assetManager.loadTexture("assets/Textures/Racket/racket_texture1.jpg"));
            Racket.firstPlayer = false;
        }
        else
            this.material.setTexture("DiffuseMap", this.assetManager.loadTexture("assets/Textures/Racket/racket_texture2.jpg"));
    }

    @Override
    protected void createCylinder() {

        Cylinder cylinder = new Cylinder(10,10,this.globalSize / 100.0f,this.height, true);
        this.geometry = new Geometry("Cylinder" + this.getId(), cylinder);
    }
}

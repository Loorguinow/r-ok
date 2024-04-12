package fr.did.object;


import com.jme3.asset.AssetManager;
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

    public static Racket of(String form, Node node, AssetManager assetManager, boolean spawnOrNot) throws FormException {
        Racket racket = new Racket(node, assetManager);
        racket.constructPhysicalObject(form, spawnOrNot);
        return racket;
    }

    public static Racket of(String form, Node node, AssetManager assetManager, boolean spawnOrNot, int size) throws FormException {
        Racket racket = new Racket(node, assetManager, size);
        racket.constructPhysicalObject(form, spawnOrNot);
        return racket;
    }

    private Racket(Node node, AssetManager assetManager) {
        super(node, assetManager);
        this.height = 0.8f;
    }

    private Racket(Node node, AssetManager assetManager, int globalSize) {
        this(node, assetManager);
        this.globalSize = globalSize;
    }

    @Override
    protected void setTextures() {
        this.material.setTexture("DiffuseMap", this.assetManager.loadTexture("assets/Textures/Racket/racket_texture.jpg"));
    }

    @Override
    protected void createCylinder() {

        Cylinder cylinder = new Cylinder(10,10,this.globalSize / 100.0f,this.height, true);
        this.geometry = new Geometry("Cylinder" + this.getId(), cylinder);
    }
}

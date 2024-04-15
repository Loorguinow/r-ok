package fr.did.object;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;
import fr.did.exceptions.fr.did.object.FormException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Puck extends MobileObject{

    public static Puck of(String form, Node node, AssetManager assetManager, BulletAppState bulletAppState, boolean spawnOrNot) throws FormException {
        Puck puck = new Puck(node, assetManager, bulletAppState);
        puck.constructPhysicalObject(form, spawnOrNot);
        return puck;
    }

    public static Puck of(String form, Node node, AssetManager assetManager, BulletAppState bulletAppState, boolean spawnOrNot, int size) throws FormException {
        Puck puck = new Puck(node, assetManager, bulletAppState, size);
        puck.constructPhysicalObject(form, spawnOrNot);
        return puck;
    }

    private Puck(Node node, AssetManager assetManager, BulletAppState bulletAppState) {
        super(node, assetManager, bulletAppState);
        this.height = 0.4f;
    }

    private Puck(Node node, AssetManager assetManager, BulletAppState bulletAppState,  int globalSize) {
        this(node, assetManager, bulletAppState);
        this.globalSize = globalSize;
    }

    @Override
    protected void setTextures() {
        this.material.setTexture("DiffuseMap", this.assetManager.loadTexture("assets/Textures/Puck/Blackish.jpg"));
    }
}

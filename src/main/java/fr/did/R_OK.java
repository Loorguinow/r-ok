package fr.did;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import fr.did.gameplay.Session;

public class R_OK extends SimpleApplication {
    public static void main(String[] args) {
        R_OK app = new R_OK();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        BulletAppState bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        Session game = Session.of(rootNode, assetManager, bulletAppState, this.cam, this.flyCam);
    }
}
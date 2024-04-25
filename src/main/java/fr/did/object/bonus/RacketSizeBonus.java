package fr.did.object.bonus;

import com.jme3.math.Vector3f;
import fr.did.exceptions.fr.did.object.FormException;
import fr.did.gameplay.Session;
import fr.did.object.MobileObjectForm;
import fr.did.object.Racket;

import java.util.List;
import java.util.Random;

public class RacketSizeBonus extends Bonus{

    private List<Racket> rackets;
    private Random random = new Random();
    private float effect;

    public static RacketSizeBonus of(String form, boolean spawnOrNot, Session session) throws FormException {
        RacketSizeBonus racketSizeBonus = new RacketSizeBonus(session);
        racketSizeBonus.constructPhysicalObject(form, spawnOrNot);
        return racketSizeBonus;
    }

    private RacketSizeBonus(Session session) {
        super(session);
        this.rackets = session.getRackets();
        this.effect = 50 * this.random.nextInt(1, 2);
    }

    @Override
    protected void setTextures() {
        this.material.setTexture("DiffuseMap", this.assetManager.loadTexture("assets/Textures/Bonus/mystery.png"));
    }

    @Override
    public void makeEffect(String whichPlayer) {
        if (whichPlayer == "L") {
            Racket originalRacket = this.rackets.get(0);
            Racket.firstPlayer = true;
            this.replaceRacket(originalRacket, 0);
            System.out.println(this.rackets);
        }
        else { //whichPLayer = "R"
            Racket originalRacket = this.rackets.get(1);
            this.replaceRacket(originalRacket, 1);
        }
    }

    private void replaceRacket(Racket racket, int index) {
        final int newSize = (int)(racket.getGlobalSize() + this.effect);
        Vector3f posRacket = racket.getGeometry().getWorldTranslation();
        try {
            Racket newRacket = Racket.of(MobileObjectForm.CYLINDER, this.node, this.assetManager, this.bulletAppState, false, newSize);
            this.rackets.add(index, newRacket);
            newRacket.spawnObject(posRacket);
            racket.getBulletAppState().getPhysicsSpace().remove(racket.getRigidBodyControl());
            racket.getNode().detachChild(racket.getGeometry());
            this.rackets.remove(racket);
        } catch (FormException e) {
            e.printStackTrace();
        }
    }
}

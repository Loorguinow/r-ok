package fr.did.object.bonus;

import fr.did.exceptions.fr.did.object.FormException;
import fr.did.gameplay.Session;
import fr.did.object.Racket;

import java.util.List;

public class RacketSizeBonus extends Bonus{

    private List<Racket> rackets;

    public static RacketSizeBonus of(String form, boolean spawnOrNot, Session session) throws FormException {
        RacketSizeBonus racketSizeBonus = new RacketSizeBonus(session);
        racketSizeBonus.constructPhysicalObject(form, spawnOrNot);
        return racketSizeBonus;
    }

    private RacketSizeBonus(Session session) {
        super(session);
        this.rackets = session.getRackets();
    }

    @Override
    protected void setTextures() {
        this.material.setTexture("DiffuseMap", this.assetManager.loadTexture("assets/Textures/Bonus/mystery.png"));
    }
}

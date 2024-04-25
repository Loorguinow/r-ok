package fr.did.object.bonus;

import fr.did.exceptions.fr.did.object.FormException;
import fr.did.gameplay.Session;
import fr.did.object.Pillar;
import fr.did.object.StaticObjectForm;

import java.util.ArrayList;
import java.util.List;

public class PillarBonus extends Bonus{

    private List<Pillar> pillars;

    public static PillarBonus of(Session session) throws FormException{
        PillarBonus pillarBonus = new PillarBonus(session);
        pillarBonus.constructPhysicalObject(StaticObjectForm.CUBE, true);
        return pillarBonus;
    }

    private PillarBonus(Session session) {
        super(session);
        this.pillars = new ArrayList<>();
    }

    @Override
    public void makeEffect(String whichPlayer) {
        if (whichPlayer == "L") {
            int i;
            for (i=0;i<1;i++)
                try {
                    this.pillars.add(Pillar.of(this.node, this.assetManager, this.bulletAppState, "L"));
                } catch (FormException e) {
                    e.printStackTrace();
                }
        }
        else {
            int i;
            for (i=0;i<1;i++)
                try {
                    this.pillars.add(Pillar.of(this.node, this.assetManager, this.bulletAppState, "L"));
                } catch (FormException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    protected void setTextures() {
        this.material.setTexture("DiffuseMap", this.assetManager.loadTexture("assets/Textures/Bonus/mystery.png"));
    }
}

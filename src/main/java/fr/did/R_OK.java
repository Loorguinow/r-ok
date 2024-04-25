package fr.did;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import fr.did.gameplay.RacketActionListener;
import fr.did.gameplay.Session;
import fr.did.object.Racket;

public class R_OK extends SimpleApplication {
    private RacketActionListener actionListener;

    private  RacketActionListener actionListener2;
    private Session game;

    private float speed = 1;
    public static void main(String[] args) {
        R_OK app = new R_OK();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        flyCam.setEnabled(false);

        BulletAppState bulletAppState = new BulletAppState();



        stateManager.attach(bulletAppState);
        Session game = Session.of(rootNode, assetManager, bulletAppState, this.cam, this.flyCam);
        this.game = game;
        // Ajouter les touches avec les actions correspondantes
        this.inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_Z));
        this.inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        this.inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_Q));
        this.inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));


        RacketActionListener actionRacket1 = new RacketActionListener(game.rackets.get(0));
        this.actionListener = actionRacket1;
        this.inputManager.addListener(actionRacket1,"Up", "Down", "Left", "Right");

        inputManager.addMapping("Left2", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right2", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("Up2", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Down2", new KeyTrigger(KeyInput.KEY_DOWN));

        RacketActionListener actionRacket2 = new RacketActionListener(game.rackets.get(1));
        this.actionListener2 = actionRacket2;
        this.inputManager.addListener(actionRacket2,"Up2", "Down2", "Left2", "Right2");


    }
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);

        Vector3f velocityRacketOne = game.getRackets().get(0).getRigidBodyControl().getLinearVelocity();
        Vector3f velocityRacketTwo = game.getRackets().get(1).getRigidBodyControl().getLinearVelocity();

        Float vitesse_max = 10f;




        //Mouvement Racket 1
        if (this.actionListener.getDirection_z_up() == 1){
            if ((this.speed<=20f)&&(velocityRacketOne.x<vitesse_max)){
                game.getRackets().get(0).getRigidBodyControl().setLinearVelocity(velocityRacketOne.add(this.speed,0f,0f));
            }
        }
        if (this.actionListener.getDirection_z_down() == 1){
            if ((this.speed<=20f)&&(velocityRacketOne.x>=-vitesse_max)){
                game.getRackets().get(0).getRigidBodyControl().setLinearVelocity(velocityRacketOne.add(-this.speed,0f,0f));
            }
        }


        if (this.actionListener.getDirection_x_left() == 1){
            if ((this.speed<=20f)&&(velocityRacketOne.z>=-vitesse_max)){
                game.getRackets().get(0).getRigidBodyControl().setLinearVelocity(velocityRacketOne.add(0f,0f,-this.speed));
            }
        }
        if (this.actionListener.getDirection_x_right() == 1){
            if ((this.speed<=20f)&&(velocityRacketOne.z<=vitesse_max)){
                game.getRackets().get(0).getRigidBodyControl().setLinearVelocity(velocityRacketOne.add(0f,0f,this.speed));
            }
        }

        //Mouvement Racket 1
        if (this.actionListener2.getDirection_z_up() == 1){
            if ((this.speed<=20f)&&(velocityRacketTwo.x<vitesse_max)){
                game.getRackets().get(1).getRigidBodyControl().setLinearVelocity(velocityRacketTwo.add(this.speed,0f,0f));
            }
        }
        if (this.actionListener2.getDirection_z_down() == 1){
            if ((this.speed<=20f)&&(velocityRacketTwo.x>=-vitesse_max)){
                game.getRackets().get(1).getRigidBodyControl().setLinearVelocity(velocityRacketTwo.add(-this.speed,0f,0f));
            }
        }


        if (this.actionListener2.getDirection_x_left() == 1){
            if ((this.speed<=20f)&&(velocityRacketTwo.z>=-vitesse_max)){
                game.getRackets().get(1).getRigidBodyControl().setLinearVelocity(velocityRacketTwo.add(0f,0f,-this.speed));
            }
        }
        if (this.actionListener2.getDirection_x_right() == 1){
            if ((this.speed<=20f)&&(velocityRacketTwo.z<=vitesse_max)){
                game.getRackets().get(1).getRigidBodyControl().setLinearVelocity(velocityRacketTwo.add(0f,0f,this.speed));
            }
        }
    }
}
package fr.did;

import com.bulletphysics.dynamics.RigidBody;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import fr.did.gameplay.GameListener;
import fr.did.gameplay.RacketActionListener;
import fr.did.gameplay.Score;
import fr.did.gameplay.Session;
import fr.did.object.Puck;
import fr.did.object.Racket;
import fr.did.object.bonus.Bonus;

public class R_OK extends SimpleApplication {
    private RacketActionListener actionListener;
    private  RacketActionListener actionListener2;
    private GameListener gameListener;
    private Session game;
    private float speed = 1;
    public static void main(String[] args) {
        R_OK app = new R_OK();
        app.start();
    }

    private boolean checkCollision(PhysicsCollisionEvent event, String objectAName, String objectBName) {
        String aName = event.getObjectA().getUserObject().toString();
        String bName = event.getObjectB().getUserObject().toString();

        // Vérifiez si les noms des objets en collision correspondent aux noms spécifiés
        if ((aName.equals(objectAName) && bName.equals(objectBName)) ||
                (aName.equals(objectBName) && bName.equals(objectAName))) {
            return true; // Il y a collision entre les objets spécifiés
        }

        return false; // Pas de collision entre les objets spécifiés
    }


    @Override
    public void simpleInitApp() {

        flyCam.setEnabled(false);

        BulletAppState bulletAppState = new BulletAppState();

        stateManager.attach(bulletAppState);
        Session game = Session.of(rootNode, assetManager, bulletAppState, this.cam, this.flyCam);
        this.game = game;

        PhysicsCollisionListener puckGoal = new PhysicsCollisionListener() {
            @Override
            public void collision(PhysicsCollisionEvent event) {
                int nbPuck = game.getPucks().size();
                int i;
                for (i = 0; i < nbPuck; i++) {
                    Puck p = game.getPucks().get(i);
                    if (checkCollision(event, p.getGeometry().toString(), "CageFloor (Geometry)")||checkCollision(event, p.getGeometry().toString(), "CageIn (Geometry)")) {
                        game.getScore().rightPoint();
                        game.getScore().updateNewScore();
                        if (nbPuck == 1) {
                            p.getGeometry().removeControl(p.getRigidBodyControl());
                            p.getGeometry().rotate(90.0f * FastMath.DEG_TO_RAD, 0.0f, 0f);
                            p.getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                            p.getGeometry().addControl(p.getRigidBodyControl());
                            p.spawnObject(new Vector3f(0f, 0.1f, -5f));

                            game.getRackets().get(0).getGeometry().removeControl(game.getRackets().get(0).getRigidBodyControl());
                            game.getRackets().get(0).getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                            game.getRackets().get(0).getGeometry().setLocalTranslation(0.0f, 0.1f, ((-Session.TABLE_LENGTH/4)*3));
                            game.getRackets().get(0).getGeometry().addControl(game.getRackets().get(0).getRigidBodyControl());

                            game.getRackets().get(1).getGeometry().removeControl(game.getRackets().get(1).getRigidBodyControl());
                            game.getRackets().get(1).getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                            game.getRackets().get(1).getGeometry().setLocalTranslation(0.0f, 0.1f, ((Session.TABLE_LENGTH/4)*3));
                            game.getRackets().get(1).getGeometry().addControl(game.getRackets().get(1).getRigidBodyControl());
<<<<<<< HEAD
                            game.maybeMultiplePucks();
                            game.removeBonuses();
                            game.maybeBonuses();
=======
                            //game.maybeMultiplePucks();
>>>>>>> d2d1bb52183f4d4830ece09d3d7ac7e304823433
                        } else {
                            p.getBulletAppState().getPhysicsSpace().remove(p.getRigidBodyControl());
                            p.getNode().detachChild(p.getGeometry());
                            game.getPucks().remove(p);
                            nbPuck--;
                        }

                    }

                    if (checkCollision(event, p.getGeometry().toString(), "CageFloor2 (Geometry)")||checkCollision(event, p.getGeometry().toString(), "CageIn2 (Geometry)")) {
                        game.getScore().leftPoint();
                        game.getScore().updateNewScore();
                        if (nbPuck == 1) {
                            p.getGeometry().removeControl(p.getRigidBodyControl());
                            p.getGeometry().rotate(90.0f * FastMath.DEG_TO_RAD, 0.0f, 0f);
                            p.getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                            p.getGeometry().addControl(p.getRigidBodyControl());
                            p.spawnObject(new Vector3f(0f, 0.1f, 5f));

                            game.getRackets().get(0).getGeometry().removeControl(game.getRackets().get(0).getRigidBodyControl());
                            game.getRackets().get(0).getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                            game.getRackets().get(0).getGeometry().setLocalTranslation(0.0f, 0.1f, ((-Session.TABLE_LENGTH/4)*3));
                            game.getRackets().get(0).getGeometry().addControl(game.getRackets().get(0).getRigidBodyControl());

                            game.getRackets().get(1).getGeometry().removeControl(game.getRackets().get(1).getRigidBodyControl());
                            game.getRackets().get(1).getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                            game.getRackets().get(1).getGeometry().setLocalTranslation(0.0f, 0.1f, ((Session.TABLE_LENGTH/4)*3));
                            game.getRackets().get(1).getGeometry().addControl(game.getRackets().get(1).getRigidBodyControl());
<<<<<<< HEAD
                            game.maybeMultiplePucks();
                            game.removeBonuses();
                            game.maybeBonuses();
=======
                            //game.maybeMultiplePucks();

>>>>>>> d2d1bb52183f4d4830ece09d3d7ac7e304823433

                        } else {
                            p.getBulletAppState().getPhysicsSpace().remove(p.getRigidBodyControl());
                            p.getNode().detachChild(p.getGeometry());
                            game.getPucks().remove(p);
                            nbPuck--;
                        }

                    }
                }
            }
        };
        PhysicsCollisionListener racketBonus = new PhysicsCollisionListener() {
            @Override
            public void collision(PhysicsCollisionEvent event) {
                int nbBonuses = game.getBonuses().size();
                int i;
                for (i=0;i<nbBonuses;i++) {
                    Racket racket0 = game.getRackets().get(0);
                    Racket racket1 = game.getRackets().get(1);
                    Bonus bonus = game.getBonuses().get(i);
                    if (checkCollision(event, racket0.getGeometry().toString(), bonus.getCollisionGeometry().toString())) {
                        bonus.makeEffect("L");
                        bonus.getBulletAppState().getPhysicsSpace().remove(bonus.getRigidBodyControl());
                        bonus.getNode().detachChild(bonus.getGeometry());
                        bonus.getNode().detachChild(bonus.getCollisionGeometry());
                        game.getPucks().remove(bonus);
                        nbBonuses--;
                    }
                    else if (checkCollision(event, racket1.getGeometry().toString(), bonus.getCollisionGeometry().toString())) {
                        bonus.makeEffect("R");
                        bonus.getBulletAppState().getPhysicsSpace().remove(bonus.getRigidBodyControl());
                        bonus.getNode().detachChild(bonus.getGeometry());
                        bonus.getNode().detachChild(bonus.getCollisionGeometry());
                        game.getPucks().remove(bonus);
                        nbBonuses--;
                    }
                }
            }
        };

        bulletAppState.getPhysicsSpace().addCollisionListener(puckGoal);
        bulletAppState.getPhysicsSpace().addCollisionListener(racketBonus);

        this.inputManager.addMapping("RestartRound", new KeyTrigger(KeyInput.KEY_P));
        this.gameListener = new GameListener(this.game);
        this.inputManager.addListener(this.gameListener, "RestartRound");

        // Ajouter les touches avec les actions correspondantes
        this.inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_Z));
        this.inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        this.inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_Q));
        this.inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));


        RacketActionListener actionRacket1 = new RacketActionListener(game.getRackets().get(0));
        this.actionListener = actionRacket1;
        this.inputManager.addListener(actionRacket1,"Up", "Down", "Left", "Right");

        inputManager.addMapping("Left2", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right2", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("Up2", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("Down2", new KeyTrigger(KeyInput.KEY_DOWN));

        RacketActionListener actionRacket2 = new RacketActionListener(game.getRackets().get(1));
        this.actionListener2 = actionRacket2;
        this.inputManager.addListener(actionRacket2,"Up2", "Down2", "Left2", "Right2");


    }
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);

        if (this.game.getScore().getRight() == Score.WINNER_SCORE){
            System.out.println("ROUGE A GAGNÉ LA PARTIE AVEC UN SCORE DE "+this.game.getScore().getScoreText().getText());
            this.game.getScore().setRight(0);
            this.game.getScore().setLeft(0);
            this.game.getScore().updateNewScore();
        }

        else if (this.game.getScore().getLeft() == Score.WINNER_SCORE){
            System.out.println("BLEU A GAGNÉ LA PARTIE AVEC UN SCORE DE "+this.game.getScore().getScoreText().getText());
            this.game.getScore().setRight(0);
            this.game.getScore().setLeft(0);
            this.game.getScore().updateNewScore();
        }

        Vector3f velocityRacketOne = game.getRackets().get(0).getRigidBodyControl().getLinearVelocity();
        Vector3f velocityRacketTwo = game.getRackets().get(1).getRigidBodyControl().getLinearVelocity();

        Float vitesse_max = 10f;

        if (this.gameListener.roundRestartedOrNot) {

        }

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
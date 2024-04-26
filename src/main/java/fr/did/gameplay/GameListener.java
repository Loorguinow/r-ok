package fr.did.gameplay;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import fr.did.object.Puck;

public class GameListener implements ActionListener {

    private Session game;
    public boolean roundRestartedOrNot;

    public GameListener(Session game) {
        this.game = game;
        this.roundRestartedOrNot = false;
    }

    @Override
    public void onAction(String s, boolean b, float v) {
        if (s.equals("RestartRound"))
            if (b) {
                this.roundRestartedOrNot = true;
                Puck p = this.game.getPucks().get(0);
                int size = this.game.getPucks().size();
                int i;
                if (size == 2) {
                    for (i = 0; i < size; i++) {
                        p = this.game.getPucks().get(i);
                        p.getGeometry().removeControl(p.getRigidBodyControl());
                        p.getGeometry().rotate(90.0f * FastMath.DEG_TO_RAD, 0.0f, 0f);
                        p.getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                        p.getGeometry().addControl(p.getRigidBodyControl());
                        int iBis;
                        if (i % 2 == 0) iBis = 1;
                        else iBis = -1;
                        p.spawnObject(new Vector3f(((-(2 * Session.TABLE_WIDTH / 4) + 2*i * (2 * Session.TABLE_WIDTH / 4))), 0.0f, 0.0f));
                    }
                } else {

                    p.getGeometry().removeControl(p.getRigidBodyControl());
                    p.getGeometry().rotate(90.0f * FastMath.DEG_TO_RAD, 0.0f, 0f);
                    p.getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                    p.getGeometry().addControl(p.getRigidBodyControl());
                    p.spawnObject(new Vector3f(0f, 0.1f, -0.0f));
                    for (i = 1; i < size; i++) {
                        p = this.game.getPucks().get(i);
                        p.getGeometry().removeControl(p.getRigidBodyControl());
                        p.getGeometry().rotate(90.0f * FastMath.DEG_TO_RAD, 0.0f, 0f);
                        p.getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                        p.getGeometry().addControl(p.getRigidBodyControl());
                        int iBis;
                        if (i % 2 == 0) iBis = 1;
                        else iBis = -1;
                        p.spawnObject(new Vector3f(((-(2 * Session.TABLE_WIDTH / 4) + 2 * (i - 1) * (2 * Session.TABLE_WIDTH / 4))), 0.1f, 0.0f));
                    }
                }

                game.getRackets().get(0).getGeometry().removeControl(game.getRackets().get(0).getRigidBodyControl());
                game.getRackets().get(0).getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                game.getRackets().get(0).getGeometry().setLocalTranslation(0.0f, 0.1f, ((-Session.TABLE_LENGTH / 4) * 3));
                game.getRackets().get(0).getGeometry().addControl(game.getRackets().get(0).getRigidBodyControl());

                game.getRackets().get(1).getGeometry().removeControl(game.getRackets().get(1).getRigidBodyControl());
                game.getRackets().get(1).getRigidBodyControl().setLinearVelocity(new Vector3f(0, 0, 0));
                game.getRackets().get(1).getGeometry().setLocalTranslation(0.0f, 0.1f, ((Session.TABLE_LENGTH / 4) * 3));
                game.getRackets().get(1).getGeometry().addControl(game.getRackets().get(1).getRigidBodyControl());
                this.roundRestartedOrNot = false;
            }
    }
}

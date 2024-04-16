package fr.did.gameplay;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import fr.did.object.Racket;
import lombok.Getter;

@Getter
public class RacketActionListener implements ActionListener {
    private Racket racket;
    private Integer direction_x = 0;
    private Integer direction_z = 0;

    public RacketActionListener(Racket racket) {
        this.racket = racket;
    }

    @Override
    public void onAction(String nom, boolean presse, float temps) {
        this.direction_x = 0;
        this.direction_z = 0;
        if (nom.equals("Up")) {
            if (presse) {
                this.direction_z = 1;

            }
        } else if (nom.equals("Down")) {
            if (presse) {
                this.direction_z = -1;

            }
        } else if (nom.equals("Left")) {
            if(presse) {
                this.direction_x = 1;
            }
        } else if (nom.equals("Right")) {
            if(presse) {
                this.direction_x = -1;
            }


        }


        if (nom.equals("Up2")) {
            if (presse) {
                this.direction_z = 1;

            }
        } else if (nom.equals("Down2")) {
            if (presse) {
                this.direction_z = -1;

            }
        } else if (nom.equals("Left2")) {
            if(presse) {
                this.direction_x = 1;
            }
        } else if (nom.equals("Right2")) {
            if(presse) {
                this.direction_x = -1;
            }


        }

    }
}
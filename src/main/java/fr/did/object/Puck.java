package fr.did.object;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import fr.did.exceptions.fr.did.object.FormException;
import jme3utilities.mesh.Prism;
import lombok.Getter;

@Getter
public class Puck {

    private Geometry geometry;
    private Material material;
    private final AssetManager assetManager;
    private Node node;

    public static Puck of(String form, Node node, AssetManager assetManager, Boolean spawnOrNot) throws FormException {
        return new Puck(form, node, assetManager, spawnOrNot);
    }

    private Puck(String form, Node node, AssetManager assetManager, boolean spawnOrNot) throws FormException{
        this.material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        if (form.equals("cylinder")) this.createCylinder();
        else if (form.equals("prism")) this.createPrism();
        else throw new FormException();
        this.node = node;
        this.assetManager = assetManager;
        if (spawnOrNot) this.spawnPuck();
    }

    private void createPrism() {
        Prism prism = new Prism(3, 1, 1, true);
        this.geometry = new Geometry("Prism", prism);
    }

    private void createCylinder() {
        Cylinder cylinder = new Cylinder(100,100,1,1);
        this.geometry = new Geometry("Cylinder", cylinder);
    }

    /**
     * Applique le matériel et la texture au palet
     */
    public void setItems() {
        this.geometry.setMaterial(this.material);
        //this.material.setTexture("ColorMap", this.assetManager.loadTexture("assets/Textures/Terrain/BrickWall/BrickWall.jpg"));
    }

    /**
     * Fait apparaître le palet, en le plaçant au milieu
     * du terrain, et en lui donnant son aspect visuel.
     */
    public void spawnPuck() {
        this.geometry.rotate(90.0f * FastMath.DEG_TO_RAD, 0.0f, 90.0f * FastMath.DEG_TO_RAD);
        this.setItems();
        this.node.attachChild(this.geometry);
    }
}

package elements;

import utils.Drawing;

import java.awt.*;

public class Laser extends Element {

    private static boolean active;

    public Laser(String imageName) {
        super(imageName);
        this.isTransposable = true;
        this.isMortal = false;
    }

    @Override
    public void autoDraw(Graphics g) {
        if(active){
            Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static void activate() {
        active = true;
    }

    public static void deactivate() {
        active = false;
    }
}

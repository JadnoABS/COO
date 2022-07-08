package elements;

import utils.Consts;
import utils.Drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class LaserGun extends BackgroundElement{

    private String[] images = {"gun.png", "gun2.png", "gun3.png", "gun4.png"};
    private int currentImage = 0;
    private long startTime;
    private long animationTime = 1000;
    private long timeBetweenShots = 5000;
    private long shotTime = 2000;

    public LaserGun(String imageName) {
        super(imageName);
        this.isTransposable = false;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Muda a imagem correspondente ao lasergun para produzir uma animacao
     *
     * @param imgIndex
     */
    private void changeImage(int imgIndex) {
        try {
            imageIcon = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + images[imgIndex]);
            Image img = imageIcon.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIZE, Consts.CELL_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
            imageIcon = new ImageIcon(bi);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Alem de desenhar a LaserGun na tela, verifica se ela esta parada, atirando ou preparando para atirar (animacao)
     *
     * @param g
     */
    public void autoDraw(Graphics g) {
        if(!Laser.isActive()) {
            if(System.currentTimeMillis() - timeBetweenShots >= startTime + animationTime) {
                startTime = System.currentTimeMillis();
                currentImage = 3;
                this.changeImage(currentImage);
                Laser.activate();
            } else if(System.currentTimeMillis() - timeBetweenShots >= startTime) {
                if(currentImage < 3) {
                    currentImage++;
                }
                this.changeImage(currentImage);
            }

        } else if( System.currentTimeMillis() - shotTime >= startTime ) {
            startTime = System.currentTimeMillis();
            currentImage = 0;
            this.changeImage(currentImage);
            Laser.deactivate();
        }

        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
}

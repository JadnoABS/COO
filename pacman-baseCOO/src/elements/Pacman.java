package elements;

import utils.Consts;
import utils.Drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;


public class Pacman extends ElementMove  {

	private String[] images = new String[2];
	private String[] up = {"ghost1_up.png", "ghost2_up.png"};
	private String[] down = {"ghost1_down.png", "ghost2_down.png"};
	private String[] left = {"ghost1_left.png", "ghost2_left.png"};
	private String[] right = {"ghost1_right.png", "ghost2_right.png"};
	private long animationStartTime;
	private final int animationTime = 200;
	private int currentImage = 0;

    private int score=0;
    private int remainingScore=0;
    private int numberLifes=1;
    private int numberDotstoEat=0;
    private int numberGhosttoEat=4;
	private int ghostEatenOnCurrentPowerPellet = 0;
    private long startTimePower=0;
    
    
    public Pacman(String imageName) {
        super(imageName);
        this.isMortal = true;
        this.animationStartTime = System.currentTimeMillis();
    }
    
    public int getScore(){
    	return this.score;
    }
    
    public int getRemainingScore(){
    	return this.remainingScore;
    }

	public int getLifes() {
		return this.numberLifes;
	}
	
	public int getNumberDotstoEat() {
		return this.numberDotstoEat;
	}
	
	public long getStartTimePower() {
		return this.startTimePower;
	}
	
	
	public void setStartTimePower(long start){
		this.startTimePower=start;
	}
	
	public void setRemainingScore(int remainingScore){
		this.remainingScore=remainingScore;
	}
	
	public void setNumberLifes(int numberLifes){
		this.numberLifes=numberLifes;
	}
	
	public void addLife(){
		this.numberLifes++;
	}
	

	
	
	public void addNumberDotstoEat() {
		this.numberDotstoEat++;
	}
	public void minusNumberDotstoEat() {
		this.numberDotstoEat--;
	}
	public void minusNumberGhotstoEat() {
		this.numberGhosttoEat--;
	}
	
	public void addScore(int i) {
		score=score+i;
	}
	
	public void addRemainingScore(int i) {
		this.remainingScore=this.remainingScore+i;
	}

	private void changeImage(int imgIndex) {

		try {
			switch (ElementMove.mov) {
				case 1: //LEFT
					images = left;
					break;
				case 2: //RIGHT
					images = right;
					break;
				case 3: //UP
					images = up;
					break;
				case 4: //DOWN
					images = down;
					break;
				default:
					images = right;
			}
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

    @Override
    public void autoDraw(Graphics g){
        if(System.currentTimeMillis() - this.animationStartTime > this.animationTime) {
			if(currentImage == 0)
				currentImage = 1;
			else currentImage = 0;

			changeImage(currentImage);
			this.animationStartTime = System.currentTimeMillis();
		}

		Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

	public int getNumberGhosttoEat() {
		return numberGhosttoEat;
	}


	public int getGhostEatenOnCurrentPowerPellet() {
		return ghostEatenOnCurrentPowerPellet;
	}

	public void setGhostEatenOnCurrentPowerPellet(int ghostEatenOnCurrentPowerPellet) {
		this.ghostEatenOnCurrentPowerPellet = ghostEatenOnCurrentPowerPellet;
	}

	public void addGhostEatenOnCurrentPowerPellet() {
		this.ghostEatenOnCurrentPowerPellet++;
	}
}

package control;

import elements.*;

import utils.Consts;
import utils.Drawing;
import utils.Stage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
public class GameScreen extends javax.swing.JFrame implements KeyListener {
    
    private Pacman pacman;
    private final GameController controller = new GameController();
    private GameState gameState;
    int cont = 0; 
    String fileName="jogo.ser";
    
    public GameScreen() {
        // Resolvendo o problema do jogo nao abrir
        this.setVisible(true);

    	Main.time = System.currentTimeMillis();
        Drawing.setGameScreen(this);
        initComponents();
        gameState = new GameState(new Stage(Main.level), new ArrayList<Element>());
        
        this.addKeyListener(this);   
        
        this.setSize(Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                     Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);
        pacman = null;
        if(Main.openSavedGame){
        	try{
        		openSavedGame(fileName);
                pacman = (Pacman) gameState.getElemArray().get(0);
        	}
        	catch(FileNotFoundException e1){
        	 		System.err.println("Arquivo jogo.ser não existente. Iniciando novo jogo ...");
                	fillInitialElemArrayFromMatrix(gameState.getStage().getMatrix());
        	}
        	catch(IOException e1){
             		e1.printStackTrace();
            }
            catch(ClassNotFoundException e1){
             		e1.printStackTrace();
            }
        	 		
        }
        else {
        	fillInitialElemArrayFromMatrix(gameState.getStage().getMatrix());

        }
    }
    
    public Pacman getPacman(){
    	return pacman;
    }    
    
    private void fillInitialElemArrayFromMatrix(int [][]matrix) {
        pacman = new Pacman("ghost1.png");
        this.addElement(pacman);

        Blinky blinky=new Blinky("pacman2.png");
        this.addElement(blinky);

        Pinky pinky=new Pinky("pacman3.png");
        this.addElement(pinky);

        Inky inky=new Inky("pacman4.png");
        this.addElement(inky);

        Clyde clyde=new Clyde("pacman1.png");
        this.addElement(clyde);

        for (int i=0;i<Consts.NUM_CELLS; i=i+1){
        	for(int j=0; j<Consts.NUM_CELLS; j=j+1){
        		switch (matrix[i][j]) {
        		case 1:
        			Wall wall1=new Wall("bricks6.png");
        			wall1.setPosition (i,j);
        			this.addElement(wall1);
        			break;
                case 0:
                    PacDots pacDot=new PacDots("pac-dot.png");
                    pacDot.setPosition (i,j);
                    this.addElement(pacDot);
                    pacman.addNumberDotstoEat();
                    break;
                case 6:
                    PowerPellet power=new PowerPellet("power_Pellet.png");
                    power.setPosition (i,j);
                    this.addElement(power);
                    break;
                case 2:
                    blinky.setPosition(i,j);
                    break;
                case 3:
                    pinky.setPosition(i,j);
                    break;
                case 4:
                    inky.setPosition(i,j);
                    break;
                case 5:
                    clyde.setPosition(i,j);
                    break;
                case 9:
                    pacman.setPosition(i,j);
                    break;
                case 10:
                    LaserGun laserGun = new LaserGun("gun.png");
                    laserGun.setPosition(i,j);
                    this.addElement(laserGun);
                    break;
                    case 11:
                        Laser laser = new Laser("laser.png");
                        laser.setPosition(i,j);
                        this.addElement(laser);
                default:
                    break;
        		}
            }
        }

    }

    private void openSavedGame(String fileName) throws FileNotFoundException,IOException, ClassNotFoundException{
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(fileName));
        gameState = (GameState) entrada.readObject();
        entrada.close();
    }

	public final void addElement(Element elem) {
        gameState.getElemArray().add(elem);
    }
    
    public void removeElement(Element elem) {
        gameState.getElemArray().remove(elem);
    }
    
    public void reStartGame(int numberLifes){
    	gameState.getElemArray().clear();
    	gameState.setElemArray(new ArrayList<Element>());
        pacman = null;
        
        gameState.setStage(new Stage(Main.level));
    	fillInitialElemArrayFromMatrix(gameState.getStage().getMatrix());
    	((Pacman)gameState.getElemArray().get(0)).setNumberLifes(numberLifes);
    }
    
    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
     
        Graphics g2 = g.create(getInsets().right, getInsets().top, getWidth() - getInsets().left, getHeight() - getInsets().bottom);
      
           
        for (int i = 0; i < Consts.NUM_CELLS; i=i+1) {
            for (int j = 0; j < Consts.NUM_CELLS; j=j+1) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + gameState.getStage().getBackground());
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIZE, i * Consts.CELL_SIZE, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
                    
                } catch (IOException ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Font currentFont = g2.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
        g2.setFont(newFont);
        if(Main.level == 1){
            g2.setColor(Color.BLACK);
        } else {
            g2.setColor(Color.WHITE);
        }

        cont++;
        this.controller.drawAllElements(gameState.getElemArray(), g2);
        this.controller.processAllElements(gameState.getElemArray(), gameState.getStage().getMatrix(),cont);
        this.setTitle(pacman.getStringPosition()+" Score:"+pacman.getScore()+" Lifes:"+pacman.getLifes()+" Level:" + Main.level+" NumberDots:"+pacman.getNumberDotstoEat() + " NumberGhosts:"+pacman.getNumberGhosttoEat());
      
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }
    
    public void go() {
        TimerTask task = new TimerTask() {
            
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            ElementMove.mov = 3; //move up image;
            pacman.setMovDirection(Pacman.MOVE_UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            ElementMove.mov = 4; //move down image
            pacman.setMovDirection(Pacman.MOVE_DOWN);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            ElementMove.mov = 1; //move left image
            pacman.setMovDirection(Pacman.MOVE_LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            ElementMove.mov = 2; //move right image
            pacman.setMovDirection(Pacman.MOVE_RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pacman.setMovDirection(Pacman.STOP);
        } else if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            saveElemArrayandStage(); 
 
        } 
    }

    private void saveElemArrayandStage() {
        try {
            ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(fileName));
            saida.writeObject(this.gameState);
            saida.close();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo nao encontrado!");
            System.out.println(e);
        } catch (IOException e) {
            System.out.println("Erro na E/S!");
            System.out.println(e);
        }
 	}



    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pacman Level" + Main.level);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(50, 50));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
         pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void dispose(){
		super.dispose();
	}
}

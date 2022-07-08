package control;

import elements.*;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import utils.Consts;
import utils.Position;

public class GameController {

	/**
	 * Desenha todos os elementos na tela
	 *
	 * @param elemArray
	 * @param g
	 */
    public void drawAllElements(ArrayList<Element> elemArray, Graphics g){
    	Pacman pacman=(Pacman) elemArray.get(0);
    	int numberGhost=pacman.getNumberGhosttoEat();
    	for(int i=numberGhost+1; i<elemArray.size(); i++){
            elemArray.get(i).autoDraw(g);

			if(elemArray.get(i) instanceof EatenGhostScore)
				if(((EatenGhostScore) elemArray.get(i)).getElapsedTime() >= 2000)
					elemArray.remove(i);
        }
        
        for(int i=0;i<=numberGhost;i++){
        	elemArray.get(i).autoDraw(g);
        }
        
    }

	/**
	 * Processa as interacoes entre os elementos do jogo
	 *
	 * @param elements
	 * @param matrix
	 * @param cont
	 */
    public void processAllElements(ArrayList<Element> elements, int [][]matrix, int cont){
        if(elements.isEmpty())
            return;
    	Pacman pacman = (Pacman)elements.get(0);
    	int numberGhost = pacman.getNumberGhosttoEat();

    	checkElementColideWall(elements, numberGhost);
    	boolean overlapGhostPacman=checkOverlapGhostPacman(elements,pacman, numberGhost);
		boolean overlapLaser = checkOverlapLaserPacman(elements, pacman);

        if(overlapGhostPacman || overlapLaser) {
        	pacman.setNumberLifes(pacman.getLifes()-1);
        	if(pacman.getLifes()>0){
				pacman.setPosition(1,1); // PACMAN VOLTA PARA A POSICAO INICIAL QUANDO PERDE UMA VIDA
//        		Main.gamePacMan.reStartGame(pacman.getLifes());
        	}
        	else{
        		Main.gamePacMan.dispose();
        		JOptionPane.showMessageDialog(null, "Fim do jogo");
        		System.exit(0);
        	}
        		
        }
        else if(pacman.getNumberDotstoEat() == 0){  
        	Main.level += 1;
        	if(Main.level>=5){
        		Main.gamePacMan.dispose();
        		JOptionPane.showMessageDialog(null, "Fim do jogo");
        		System.exit(0);
        	}
        	else{
        		Main.gamePacMan.reStartGame(pacman.getLifes());
        	}
        }
        else{
	        checkPacmanEatSomeOneAndOrTimeFruittoDesappear(elements,pacman);
	        checkTimetoAppearFruit(elements,matrix);
	        checkTimeGhostBeNormal(elements,pacman);
	
	        pacman.move();
	        for (int i=1;i<=pacman.getNumberGhosttoEat();i++){
	            ElementMove elementMove = (ElementMove)elements.get(i);
	            if (!elementMove.isMortal()){
	            	elementMove.move();
	            }
	            else{
	            	if (elementMove.isMortal() && cont%2==0){
	            		elementMove.move();
	            	}
	            }
	        }
        }
    }

	/**
	 * Verifica se o Pacman colidiu com um Ghost (quando este nao esta mortal)
	 *
	 * @param elements
	 * @param pacman
	 * @param numberGhost
	 * @return
	 */
	private boolean checkOverlapGhostPacman(ArrayList<Element> elements, Pacman pacman,int numberGhost) {
        boolean overlapGhostPacman=false;
        for (int i=1;i<=numberGhost;i++){
        	if(elements.get(i).overlap(pacman) & !elements.get(i).isMortal()){
        		overlapGhostPacman=true;
        	}
        }
        return overlapGhostPacman;
	}

	/**
	 * Verifica se o Pacman passou pelo raio laser ativo
	 *
	 * @param elements
	 * @param pacman
	 * @return
	 */
	private boolean checkOverlapLaserPacman(ArrayList<Element> elements, Pacman pacman) {
		for (Element element : elements) {
			if(element instanceof Laser) {
				if(Laser.isActive() && element.overlap(pacman)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Verifica se cada um dos objetos moveis colidiu com uma parede
	 *
	 * @param elements
	 * @param numberGhost
	 */
	private void checkElementColideWall(ArrayList<Element> elements, int numberGhost) {
    	for (int i=0;i<=numberGhost;i++){
        	ElementMove elementMove = (ElementMove)elements.get(i);
        	if (!isValidPosition(elements, elementMove)) {
				if(i > 0){ // CASO O ELEMENTO SEJA UM GHOST -> NA COLISAO ELE MUDA DE DIRECAO PARA DESVIAR
					switch(elementMove.getMoveDirection()) {
						case ElementMove.MOVE_UP:
							elementMove.backToLastPosition();
							elementMove.setFollowDirection(ElementMove.MOVE_LEFT);
							elementMove.setMovDirection(ElementMove.MOVE_DOWN);
							break;
						case ElementMove.MOVE_DOWN:
							elementMove.backToLastPosition();
							elementMove.setFollowDirection(ElementMove.MOVE_LEFT);
							elementMove.setMovDirection(ElementMove.MOVE_UP);
							break;
						case ElementMove.MOVE_LEFT:
							elementMove.backToLastPosition();
							elementMove.setFollowDirection(ElementMove.MOVE_DOWN);
							elementMove.setMovDirection(ElementMove.MOVE_RIGHT);
							break;
						case ElementMove.MOVE_RIGHT:
							elementMove.backToLastPosition();
							elementMove.setFollowDirection(ElementMove.MOVE_DOWN);
							elementMove.setMovDirection(ElementMove.MOVE_LEFT);
							break;
						default:
							elementMove.backToLastPosition();
							elementMove.setMovDirection(ElementMove.STOP);
					}
				} else {
					elementMove.backToLastPosition();
					elementMove.setMovDirection(ElementMove.STOP);
				}
				//return;
        	}
        }
		
	}

	/**
	 * Verifica se o Pacman colidiu com algum Ghost mortal ou uma fruta e adiciona os pontos e vidas necessarios para a logica do jogo
	 *
	 * @param elements
	 * @param pacman
	 */
	private void checkPacmanEatSomeOneAndOrTimeFruittoDesappear(ArrayList<Element> elements, Pacman pacman) {

        Element eTemp;
        for(int i =1; i < elements.size(); i++){
            eTemp = elements.get(i);
            if(pacman.overlap(eTemp)){
                if(eTemp.isTransposable() && eTemp.isMortal()){
                    elements.remove(eTemp);
                    if (eTemp instanceof Ghost){
						// Correcao do erro dos pontos ganhos no power pellet
						pacman.addGhostEatenOnCurrentPowerPellet();
						int score = (int) Math.pow(2, pacman.getGhostEatenOnCurrentPowerPellet()) * 100;
						pacman.minusNumberGhotstoEat();
						pacman.addScore(score);
						pacman.addRemainingScore(score);

						// Para pintar na tela o numero de pontos ganhos
						elements.add(new EatenGhostScore(score, eTemp.getPos()));
                    }
                    
                    if (eTemp instanceof ElementGivePoint){                     
                      pacman.addScore(((ElementGivePoint) eTemp).getNumberPoints());
                      pacman.addRemainingScore(((ElementGivePoint) eTemp).getNumberPoints());
                      
                      if (eTemp instanceof PacDots){
                    	  pacman.minusNumberDotstoEat();
                      }
                      if (eTemp instanceof PowerPellet){
                    	  for(int k=1;k<=pacman.getNumberGhosttoEat(); k++){
                    		  ((Ghost)elements.get(k)).changeGhosttoBlue("ghostBlue.png");
                    	  }
						  pacman.setGhostEatenOnCurrentPowerPellet(0);
                    	  pacman.setStartTimePower(System.currentTimeMillis());
                      }
                      
                    }
                }
                int remainingScore=pacman.getRemainingScore();
                if(remainingScore>4000){
                	pacman.addLife();
                	pacman.setRemainingScore(remainingScore-4000);
                }
                
            }   
            else{
            	if (eTemp instanceof Cherry){
            		long elapsed = System.currentTimeMillis()-((Cherry)eTemp).getStartTime();
            		if (elapsed>=15000){
            			elements.remove(eTemp);
            		}
            		
            		
            	}
            	if (eTemp instanceof Strawberry){
            		long elapsed = System.currentTimeMillis()-((Strawberry)eTemp).getStartTime();
            		if (elapsed>=15000){
            			elements.remove(eTemp);
            		}
            	}
                 	
            }
        }
        
	}

	/**
	 * Controla o tempo de spawn das frutas
	 *
	 * @param elements
	 * @param matrix
	 */
	private void checkTimetoAppearFruit(ArrayList<Element> elements,  int [][]matrix) {
        
        long elapsedTime = System.currentTimeMillis()-Main.time;
        if (elapsedTime%75000<=10){
        	Strawberry straw=new Strawberry("strawberry.png");
        	straw.setStartTime(System.currentTimeMillis());
        	Position pos=getValidRandomPositionMatrix(matrix);
            straw.setPosition (pos.getX(),pos.getY());
            elements.add(straw);
        }
        if (elapsedTime%50000<=10){
        	Cherry cherry=new Cherry("cherry.png");
        	cherry.setStartTime(System.currentTimeMillis());
        	Position pos=getValidRandomPositionMatrix(matrix);
            cherry.setPosition (pos.getX(),pos.getY());
            elements.add(cherry);
        }
		
	}

	/**
	 * Retorna uma posicao aleatoria do mapa do jogo
	 *
	 * @param matrix
	 * @return
	 */
	private Position getValidRandomPositionMatrix(int[][] matrix) {
		Random gerador = new Random();
		int x;
		int y;
		Position pos=new Position(0,0);
		do{
			x=gerador.nextInt(Consts.NUM_CELLS);		
			y=gerador.nextInt(Consts.NUM_CELLS);
		}while(matrix[x][y]==1);
		pos.setPosition(x, y);
		return pos;
	}

	/**
	 * Controla o tempo em que o Ghost fica mortal
	 *
	 * @param elements
	 * @param pacman
	 */
	private void checkTimeGhostBeNormal(ArrayList<Element> elements,
			Pacman pacman) {
        long start=pacman.getStartTimePower();
        if (start!=0){
        	long elapsedTimePower = System.currentTimeMillis()-start;
        	if(elapsedTimePower>=7000){
        		
        		pacman.setStartTimePower(0);
        		Element e;
        		for (int i=1;i<=pacman.getNumberGhosttoEat();i++){
        			e = elements.get(i);
        			if(e instanceof Blinky){
        				((Blinky) e).changeGhosttoNormal("blinky.png");
        			}
        			if(e instanceof Pinky){
        				((Pinky) e).changeGhosttoNormal("pinky.png");
        			}
        			if(e instanceof Inky){
        				((Inky) e).changeGhosttoNormal("inky.png");
        			}
        			if(e instanceof Clyde){
        				((Clyde) e).changeGhosttoNormal("clyde.png");
        			}
        			
                }		
        			
        	}
        	
        }
        

		
	}

	/**
	 * Verifica se o elemento se encontra em uma posicao valida,
	 * ou seja, se ele nao ocupa o mesmo espaco que um elemento nao transposable
	 *
	 * @param elemArray
	 * @param elem
	 * @return
	 */
	public boolean isValidPosition(ArrayList<Element> elemArray, Element elem){
        Element elemAux;
        for(int i = 1; i < elemArray.size(); i++){
            elemAux = elemArray.get(i);            
            if(!elemAux.isTransposable())
                if(elemAux.overlap(elem))
                    return false;
        }        
        return true;
    }
}

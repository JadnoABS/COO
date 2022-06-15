package control;

import javax.swing.JFrame;

// Problemas a serem resolvidos:
// - (Perguntar pra professora sobre) Fazer o pacman so ganhar vida com 10000 pontos
// - (PERGUNTAR PRA PROF) Trocar os botoes da tela inicial por um menu
// - (RESOLVIDO) Manter os fantasmas se movendo
// - (RESOLVIDO) Jogo continua se o pacman perde uma vida
// - (ACHO QUE RESOLVIDO) Jogo nao abre as vezes
// - (RESOLVIDO) Implementar os metodos de salvar jogo
// - Incluir classe de teste com JUnit (pelo menos 3 metodos de teste)
// - Incluir o nivel 4 (tem que ser mais dificil e inlcuir um elemento adicional)
// - Trocar a imagem do fantasma para uma que indique q qtd de pontos ganhos ao comer ele
// - Verificar a hierarquia da classe Element
// - Documentar cada um dos metodos
// - Elaborar um diagrama de classes
// - Verificar se algo mais esta fora da especificacao


public class Main {

	public static InitialScreen initialScreen; 
	public static int level = 1; 
	public static boolean openSavedGame = false; 
	public static long time;
	
	public static GameScreen gamePacMan;
	
    public static void main(String[] args) {
    	
    	initialScreen = new InitialScreen();
    	
    	initialScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	initialScreen.setVisible(true);
        
    }
	
    public static void startGame(){
    	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main.gamePacMan = new GameScreen();
//                gamePacMan.setVisible(true);
                gamePacMan.createBufferStrategy(2);
                gamePacMan.go();
            }
        });
    }
    
}

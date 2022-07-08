package control;

import javax.swing.JFrame;

// Problemas a serem resolvidos:
// - (RESOLVIDO) Fazer o pacman so ganhar vida com 10000 pontos
// - (RESOLVIDO) Trocar os botoes da tela inicial por um menu
// - (RESOLVIDO) Manter os fantasmas se movendo
// - (RESOLVIDO) Jogo continua se o pacman perde uma vida
// - (ACHO QUE RESOLVIDO) Jogo nao abre as vezes
// - (RESOLVIDO) Implementar os metodos de salvar jogo
// - (RESOLVIDO) Trocar a imagem do fantasma para uma que indique q qtd de pontos ganhos ao comer ele
// - (RESOLVIDO) Incluir o nivel 4 (tem que ser mais dificil e inlcuir um elemento adicional)
// - (RESOLVIDO) Documentar cada um dos metodos
// - (RESOLVIDO) Elaborar um diagrama de classes
// - Incluir classe de teste com JUnit (pelo menos 3 metodos de teste)
// - Verificar a hierarquia da classe Element
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


package control;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import utils.Consts;

public class InitialScreen extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private JButton startButton;
	private JButton openButton;
	private final String nomeImagemInicial = "inicialimagem.png";
	private static String[] levels = { "Level 1", "Level 2", "Level 3" };
	
	private JComboBox<String> box;
	
	public InitialScreen(){
		configureInitialScreen();
		configureMenu();
	}
	
	private void configureInitialScreen(){
		int sizeWidth = Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right;
		int sizeHeight = Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom;
		
		setSize(sizeWidth, sizeHeight);
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ACH2003 - Pacman");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);		
	    

        try{
        	setContentPane(new JLabel(new ImageIcon(new File(".").getCanonicalPath() + Consts.PATH + nomeImagemInicial)));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }	
        //pack();
	}

	private void configureMenu() {
		JMenu menu;
		JMenuBar menuBar;
		JMenuItem menuItem;
		JMenuItem subItem;

		menuBar = new JMenuBar();
		menu = new JMenu("Jogar");
		menuBar.add(menu);

		menuItem = new JMenu("Iniciar novo jogo");
		subItem = new JMenuItem("Level 1");
		subItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Main.level = 1;
				new HandlerStartButton().actionPerformed(actionEvent);
			}
		});
		menuItem.add(subItem);

		subItem = new JMenuItem("Level 2");
		subItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Main.level = 2;
				new HandlerStartButton().actionPerformed(actionEvent);
			}
		});
		menuItem.add(subItem);

		subItem = new JMenuItem("Level 3");
		subItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Main.level = 3;
				new HandlerStartButton().actionPerformed(actionEvent);
			}
		});
		menuItem.add(subItem);

		subItem = new JMenuItem("Level 4");
		subItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Main.level = 4;
				new HandlerStartButton().actionPerformed(actionEvent);
			}
		});
		menuItem.add(subItem);

		menu.add(menuItem);

		menuItem = new JMenuItem("Abrir jogo salvo");
		menuItem.addActionListener(new HandlerOpenButton());
		menu.add(menuItem);

		this.setJMenuBar(menuBar);
	}

	public class HandlerStartButton implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Main.initialScreen.setVisible(false);  
	    	Main.initialScreen.dispose();
			Main.startGame();
		}
	}

 
	public class HandlerOpenButton implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Main.initialScreen.setVisible(false);  
	    	Main.initialScreen.dispose();
	    	Main.openSavedGame = true;
	    	Main.startGame();
		}
	}
}
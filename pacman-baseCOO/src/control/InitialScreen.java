package control;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import javax.sound.sampled.*;

import javax.swing.*;

import org.w3c.dom.ls.LSOutput;
import utils.Consts;

public class InitialScreen extends javax.swing.JFrame implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String nomeImagemInicial = "menu.gif";

  public InitialScreen() {
    configureInitialScreen();
    configureMenu();
  }

  /**
   * Configuracao dos elementos da tela inicial (menu e tamanho da tela)
   *
   */
  private void configureInitialScreen() {
    int sizeWidth = Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right;
    int sizeHeight = Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom;

    setSize(sizeWidth, sizeHeight);

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("ACH2003 - Pacman");
    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    // setLocation(new java.awt.Point(20, 20));
    setLocationRelativeTo(null);
    try {
      reproduzirAudio("pacmanSong.wav");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    setResizable(false);

    try {
      setContentPane(new JLabel(new ImageIcon(new File(".").getCanonicalPath() + Consts.PATH + nomeImagemInicial)));
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    // pack();
  }

  /**
   * Toca musica na tela inicial
   *
   * @param arquivo
   * @throws FileNotFoundException
   */
  public void reproduzirAudio(String arquivo) throws FileNotFoundException {
    try {
      // URL do som
      AudioInputStream audioInputStream = AudioSystem
          .getAudioInputStream(new File("imgs/" + arquivo).getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(-15.0f); // Reduz o volume
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY); // Para repetir o som.
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("Arquivo não encontrado!");
    } catch (Exception ex) {
      System.out.println("Erro ao executar SOM!");
      ex.printStackTrace();
    }

  }

  /**
   * Configuracao da MenuBar com as opcoes de novo jogo (com escolha de fase)
   * e de abrir jogo salvo
   */
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

  public class HandlerStartButton implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      Main.initialScreen.setVisible(false);
      Main.initialScreen.dispose();
      Main.startGame();
    }
  }

  public class HandlerOpenButton implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      Main.initialScreen.setVisible(false);
      Main.initialScreen.dispose();
      Main.openSavedGame = true;
      Main.startGame();
    }
  }
}

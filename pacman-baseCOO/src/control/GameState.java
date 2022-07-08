package control;

import elements.Element;
import utils.Stage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Essa classe guarda o estado do jogo, ou seja, a fase atual e
 * o array de elementos que compoem o jogo
 *
 * Quando o jogo eh salvo, a instancia dessa classe vai ser serializada em um
 * arquivo
 */
public class GameState implements Serializable {
  private Stage stage;
  private ArrayList<Element> elemArray;

  public GameState(Stage stage, ArrayList<Element> elemArray) {
    this.stage = stage;
    this.elemArray = elemArray;
  }

  public ArrayList<Element> getElemArray() {
    return elemArray;
  }

  public void setElemArray(ArrayList<Element> elemArray) {
    this.elemArray = elemArray;
  }

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }
}

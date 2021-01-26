/**
 * @author MrBruh
 */

import javax.swing.*; 
 
/** 
 * Main class to initialize the game
 */
public class Main {  
  private static JFrame frame;
  public static void main(String[] args) {  
    frame = new JFrame();
    MChessGUI gui = new MChessGUI(frame);
    gui.onClick();
  }  
}  
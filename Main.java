/**
 * @author MrBruh
 */

import javax.swing.*; 
 
/** 
 * Main class to initialize the game
 */
public class Main { 
   
  public static void main(String[] args) {  
    JFrame frame = new JFrame();
    MChessGUI gui = new MChessGUI(frame);
    gui.onClick();
  }  
}  
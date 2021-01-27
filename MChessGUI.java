/**
 * @author MrBruh 
 */

import javax.swing.*;  

/**
 * A class to handle all the GUI elements
 */
public class MChessGUI {
    private int frameWidth = 800;
    private int frameHight = 597;
    
    private JFrame f;
    private MChessBoard board;

    /**
     * The constructor of MChessGUI that sets up the GUI of the game
     * 
     * @param frame The frame to which all the elements will be added
     */
    public MChessGUI(JFrame frame) {
        f = frame;

        board = new MChessBoard(frame);
                
        f.setSize(frameWidth, frameHight);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }

}

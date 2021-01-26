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
    private JButton b;
    private MChessBoard board;

    public MChessGUI(JFrame frame) {
        f = frame;

        board = new MChessBoard(frame);


        b = new JButton("click");
        b.setBounds(130,100,100, 40);
                
        f.add(b);//adding button in JFrame  
                
        f.setSize(frameWidth, frameHight);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    }
    
    void onClick() {
        System.out.println("woo");
    }

}

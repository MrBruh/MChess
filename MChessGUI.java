/**
 * @author MrBruh 
 */

import javax.swing.*;  

/**
 * A class to handle all the GUI elements
 */
public class MChessGUI {
    private JFrame f;
    private JButton b;
    private MChessBoard board;

    public MChessGUI(JFrame frame) {
        f = frame;

        board = new MChessBoard(frame);


        b = new JButton("click");
        b.setBounds(130,100,100, 40);
                
        f.add(b);//adding button in JFrame  
                
        f.setSize(800,600);//400 width and 500 height  
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible  
    }
    
    void onClick() {
        System.out.println("woo");
    }

}

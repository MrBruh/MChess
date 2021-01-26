/**
 * @author MrBruh
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

/**
 * A class to hold information about individual tiles
 */
public class MChessTile {

    private int tileSize = 75;  // Size of the tile

    private int xMatrixPos;     // The x position of the tile on the board
    private int yMatrixPos;     // The y position of the tile on the board
    private JButton tileButton = new JButton();
    private boolean holdsPiece = false;     // Whether or not the tile has a piece on it 

    /**
     * A Constructor that initializes the tile with the bare minimum of information
     * 
     * @param frame The frame to which the button is to be added
     * @param x The x position of the tile on the board
     * @param y The y position of the tile on the board
     */
    public MChessTile(JFrame frame, int x, int y) {
        xMatrixPos = x;
        yMatrixPos = y;
        Color colour;
        if(xMatrixPos % 2 == 0 ^ yMatrixPos % 2 == 0 ) {
            colour = new Color(232, 235, 239);
        } else {
            colour = new Color(125, 135, 150);
        }

        tileButton.setBackground(colour);
        tileButton.setBounds(xMatrixPos * tileSize,
                             yMatrixPos * tileSize,
                             tileSize,
                             tileSize );
        tileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(holdsPiece) {
                    //
                } else {
                    //
                }
            }
        });
        frame.add(tileButton);
    }
}

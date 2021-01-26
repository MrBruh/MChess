/**
 * @author MrBruh
 */

import javax.swing.*;  

/**
 * A class to hold and manipulate information about the board
 */
public class MChessBoard {
    private MChessTile[][] _boardMatrix; // This matrix holds all the indiviual tiles
    private int boardSize = 8; // This defines the board size

    /**
     * Constructor which initializes the board tiles
     * 
     * @param frame The frame to which all the elements will be added
     */
    public MChessBoard(JFrame frame){
        // Create the Matrix
        _boardMatrix = new MChessTile[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {

            for (int j = 0; j < boardSize; j++) {
                _boardMatrix[i][j] = new MChessTile(frame, i, j);
            }
        }
    }
}

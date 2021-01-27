/**
 * @author MrBruh
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage; 

/**
 * A class to hold information about individual tiles
 */
public class MChessTile {

    private int tileSize = 70;  // Size of the tile

    private int[] matrixPos;    // The position of the tile on the board
    private MChessTileButton tileButton = new MChessTileButton();
    private MChessBoard board;
    private MChessPiece piece = null;

    private MChessTile self = this;     // I know this looks dumb, but it has to be done 

    private BufferedImage canvasImage = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB) ;
    private static final Icon selectedIcon1 = new ImageIcon("Graphics/Selected1.png");
    private static final Icon selectedIcon2 = new ImageIcon("Graphics/Selected2.png");

    private Icon combinedIcon = null;

    enum tileState {
        TILE_NONE,          // When the tile is neither selected or targeted
        TILE_SELECTED,      // When the tile is selected
        TILE_TARGETED,      // When the tile is targeted for an attack
        TILE_MOVE_TARGETED  // When the tile is target for a move
    }

    private tileState state = tileState.TILE_NONE;

    /**
     * A Constructor that initializes the tile with the bare minimum of information
     * 
     * @param frame The frame to which the button is to be added
     * @param board Every tile stores a reference to the board to be able to modify other tiles
     * @param x The x position of the tile on the board
     * @param y The y position of the tile on the board
     */
    public MChessTile(JFrame frame, MChessBoard board, int x, int y) {
        this.board = board;
        matrixPos = new int[]{x, y};
        // Choosing a colour using the XOR operator
        Color colour;
        if(matrixPos[0] % 2 == 0 ^ matrixPos[1] % 2 == 0 ) {
            colour = new Color(232, 235, 239);
        } else {
            colour = new Color(125, 135, 150);
        }

        tileButton.setBackground(colour);
        tileButton.setBounds(matrixPos[0] * tileSize,
                             matrixPos[1] * tileSize,
                             tileSize,
                             tileSize );

        tileButton.setPressedBackgroundColor(colour);
        tileButton.setEnabled(false);
        tileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch(state){
                    case TILE_NONE:
                        tileButton.setIcon(combineIcons(selectedIcon1));
                        board.setSelectedTile(self);
                        state = tileState.TILE_SELECTED;
                        board.drawTileMovement(self, false);
                        break;
                    case TILE_SELECTED:
                        tileButton.setIcon(piece.getIcon());
                        state = tileState.TILE_NONE;
                        board.drawTileMovement(self, true);
                        break;
                    case TILE_TARGETED:
                        state = tileState.TILE_NONE;
                        break;
                    case TILE_MOVE_TARGETED:
                        board.movePieceToTile(self);
                        state = tileState.TILE_NONE;
                        break;
                    default:
                        break;
                }
            }
        });
        frame.add(tileButton);
    }

    /**
     * Assigns a new piece to the tile with no game logic
     * 
     * @param piece the piece that is on the tile
     */
    public void assignPiece(MChessPiece piece) {
        this.piece = piece;
        updatePressable(piece);
    }

    /** 
     * Assigns a new piece to the tile while performing game logic
     * 
     * @param piece
     */
    public void movePiece(MChessPiece piece) {
        this.piece = piece;
        updatePressable(piece);
    }

    /**
     * Returns the piece on the tile
     * 
     * @return piece on the tile
     */
    public MChessPiece getPiece() {
        return piece;
    }

    /**
     * 
     */
    public void targetMove() {
        tileButton.setEnabled(true);
        state = tileState.TILE_MOVE_TARGETED;
        tileButton.setIcon(selectedIcon2);
    }

    /**
     * 
     */
    public void unTargetMove() {
        tileButton.setEnabled(false);
        state = tileState.TILE_NONE;
        tileButton.setIcon(null);
    }

    /**
     * 
     * 
     * @param piece
     */
    private void updatePressable(MChessPiece piece) {
        if(piece == null) {
            tileButton.setIcon(null);
            tileButton.setEnabled(false);
        } else {
            tileButton.setEnabled(true);
            tileButton.setIcon(piece.getIcon());
        }
    }

    /**
     * 
     * 
     * @return
     */
    public int[] getPos() {
        return new int[]{matrixPos[0], matrixPos[1]};
    }

    /**
     * 
     * 
     * @param selectedIcon
     * @return
     */
    private Icon combineIcons(Icon selectedIcon) {
        BufferedImage tempImage = canvasImage;
        Graphics g = tempImage.createGraphics();
        selectedIcon.paintIcon(null, g, 0, 0);
        piece.getIcon().paintIcon(null, g, 0, 0);
        g.dispose();
        return new ImageIcon(tempImage);
    }
}

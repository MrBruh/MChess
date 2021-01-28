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

    private static final Icon selectedIcon1 = new ImageIcon("Graphics/Selected1.png");
    private static final Icon selectedIcon2 = new ImageIcon("Graphics/Selected2.png");
    private static final Icon TargetIcon1 = new ImageIcon("Graphics/Target1.png");
    private static final Icon EtherealIcon2 = new ImageIcon("Graphics/Ethereal1.png");

    enum tileState {
        TILE_NONE,          // When the tile is neither selected or targeted
        TILE_SELECTED,      // When the tile is selected
        TILE_TARGETED,      // When the tile is targeted for an attack
        TILE_MOVE_TARGETED, // When the tile is target for a move
        TILE_DISABLED       // When the tile has a piece that can't move
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
            colour = new Color(125, 135, 150);
        } else {
            colour = new Color(232, 235, 239);
        }

        // Set the parameters of the tile button
        tileButton.setBackground(colour);
        tileButton.setBounds(matrixPos[0] * tileSize,
                             matrixPos[1] * tileSize,
                             tileSize,
                             tileSize );

        tileButton.setPressedBackgroundColor(colour);
        // By default all buttons are disabled
        tileButton.setEnabled(false);
        // Add the action listener of the button
        tileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Behave diffently when clicked depeding on the tile's state
                switch(state){
                    case TILE_NONE:
                        // Select the tile when there is a piece
                        tileButton.setIcon(combineIcons(selectedIcon1));
                        board.setSelectedTile(self);
                        board.drawTileMovement(self, false);
                        setState(tileState.TILE_SELECTED);
                        break;
                    case TILE_SELECTED:
                        // Unselect the tile
                        unselectTile();
                        break;
                    case TILE_TARGETED:
                        board.getGUI().addCaptured(piece);
                        board.movePieceToTile(self);
                        break;
                    case TILE_MOVE_TARGETED:
                        // Move piece to tile if move targeted
                        board.movePieceToTile(self);
                        break;
                    case TILE_DISABLED:
                        break;
                    default:
                        break;
                }
            }
        });
        frame.add(tileButton);
    }

    /**
     * Prevents the tile's piece from being selected
     */
    public void disableTile() {
        setState(tileState.TILE_DISABLED);
    }

    /**
     * Allows the tile's piece to be selected
     */
    public void enableTile() {
        setState(tileState.TILE_NONE);
    }

    /**
     * Assigns a new piece to the tile with no game logic
     * 
     * @param piece The piece that is on the tile
     */
    public void assignPiece(MChessPiece piece) {
        if(piece != null) {
            piece.assignTile(this);
        }
        this.piece = piece;
        updatePressable(piece);
    }

    /** 
     * Assigns a new piece to the tile while performing game logic
     * 
     * @param piece The piece that is on the tile
     */
    public void movePiece(MChessPiece piece) {
        if(piece != null) {
            piece.assignTile(this);
        }
        this.piece = piece;
        updatePressable(piece);
    }

    /**
     * Function to unselect tile if the tile has been selected
     */
    public void unselectTile() {
        if(state == tileState.TILE_SELECTED) {
            tileButton.setIcon(piece.getIcon());
            setState(tileState.TILE_NONE);
            board.drawTileMovement(self, true); 
        }
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
     * Targets or untargets this tile for movement
     * 
     * @param unTarget
     * @return
     */
    public boolean targetMove(boolean unTarget, MChessPiece targetingPiece) {
        if(piece == null) {
            if(unTarget){
                // Disable button and remove icon
                tileButton.setEnabled(false); 
                setState(tileState.TILE_NONE);   
                tileButton.setIcon(null);
            } else {
                // Enable button and add icon
                tileButton.setEnabled(true);        
                setState(tileState.TILE_MOVE_TARGETED);    
                tileButton.setIcon(selectedIcon2); 
            }
            return false;
        } else {
            if(unTarget && state != tileState.TILE_NONE) {
                // Remove target icon  
                setState(tileState.TILE_DISABLED);    
                tileButton.setIcon(piece.getIcon());
            } else if (!targetingPiece.getColour().equals(piece.getColour()) && !targetingPiece.isNotMoveAttacker()) {
                tileButton.setEnabled(true);
                setState(tileState.TILE_TARGETED);
                tileButton.setIcon(combineIcons(TargetIcon1));
            } 
            return true;
        }    
    }

    private void setState(tileState state) {
        this.state = state;
        if(piece != null) {
            /*if(piece.getColour().equals("white")) {
                switch (state) {
                    case TILE_NONE:
                        System.out.println("Setting state to none");
                        break;
                    case TILE_SELECTED:
                        System.out.println("Setting state to selected");
                        break;
                    case TILE_TARGETED:
                        System.out.println("Setting state to targeted");
                        break;
                    case TILE_MOVE_TARGETED:
                        System.out.println("Setting state to move");
                        break;
                    case TILE_DISABLED:
                        System.out.println("Setting state to disabled");
                        break;
                    default:
                        break;
                }
            }*/
        }
    }

    /**
     * Helper function to update the icon and the button of the tile
     * when the piece reference is updated
     * 
     * @param piece
     */
    private void updatePressable(MChessPiece piece) {
        if(piece == null) { 
            tileButton.setIcon(null);            // Remove icon
            tileButton.setEnabled(false);        // Disable button
        } else {
            tileButton.setIcon(piece.getIcon()); // Add new icon
            tileButton.setEnabled(true);         // Enable button
        }
    }

    /**
     * Gets the position of the tile
     * 
     * @return Returns position of the tile
     */
    public int[] getPos() {
        return new int[]{matrixPos[0], matrixPos[1]};
    }

    /**
     * A helper fucntion to combine a movement icon and the piece icon
     * 
     * @param selectedIcon The movement icon to put under the piece
     * @return Returns a piece on a momvement tile icon
     */
    private Icon combineIcons(Icon selectedIcon) {
        // Create an empty image
        BufferedImage tempImage = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);
        // Create graphics for drawing    
        Graphics g = tempImage.createGraphics();
        // Draw icons
        selectedIcon.paintIcon(null, g, 0, 0);
        piece.getIcon().paintIcon(null, g, 0, 0);
        // Finish drawing
        g.dispose();
        // Return a combined icon
        return new ImageIcon(tempImage);
    }
}

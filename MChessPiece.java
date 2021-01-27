/**
 * @author MrBruh
 */

import java.util.List;

import javax.swing.ImageIcon;

/**
 * A class to hold information about individual chess pieces
 */
public class MChessPiece {

    private List<Integer> movementRanges;     // All pieces must have movement
                                              // (Even if range is 0)
    private List<int[]> jumpPositions = null; // Not all pieces can jump

    private ImageIcon pieceIcon;
    private String pieceColour;
    private boolean isEthereal = false;

    /**
     * Constructor for a piece that doesn't jump
     * 
     * @param movementRanges The movement range list of the piece
     * @param pieceIcon The icon of the piece
     */
    public MChessPiece(List<Integer> movementRanges, ImageIcon pieceIcon, String pieceColour) {
        this.movementRanges = movementRanges;
        this.pieceIcon = pieceIcon;
        this.pieceColour = pieceColour;
    }

    /**
     * Constructor for a piece that does jump
     * 
     * @param movementRanges The movement range list of the piece
     * @param jumpPositions The jump positions of the piece
     * @param pieceIcon The icon of the piece
     */
    public MChessPiece(List<Integer> movementRanges, List<int[]> jumpPositions, ImageIcon pieceIcon, String pieceColour) {
        this.movementRanges = movementRanges;
        this.jumpPositions = jumpPositions;
        this.pieceIcon = pieceIcon;
        this.pieceColour = pieceColour;
    }

    /**
     * Gets the movement ranges of the piece
     * 
     * @return The movement ranges list of the piece
     */
    public List<Integer> getMovementRanges() {
        return movementRanges;
    }

    /**
     * Gets the jump positions of the piece
     * 
     * @return The jump positions of the piece
     */
    public List<int[]> getJumpPositions() {
        return jumpPositions;
    }

    /**
     * Gets the icon of the piece
     * 
     * @return The icon of the piece
     */
    public ImageIcon getIcon() {
        return pieceIcon;
    }
    
    /**
     * Gets the colour of the piece
     * 
     * @return The colour of the piece
     */
    public String getColour() {
        return pieceColour;
    }
}

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

    /**
     * Constructor for a piece that doesn't jump
     * 
     * @param movementRanges The movement range list of the piece
     * @param pieceIcon The icon of the piece
     */
    public MChessPiece(List<Integer> movementRanges, ImageIcon pieceIcon) {
        this.movementRanges = movementRanges;
        this.pieceIcon = pieceIcon;
    }

    /**
     * Constructor for a piece that does jump
     * 
     * @param movementRanges The movement range list of the piece
     * @param jumpPositions The jump positions of the piece
     * @param pieceIcon The icon of the piece
     */
    public MChessPiece(List<Integer> movementRanges, List<int[]> jumpPositions, ImageIcon pieceIcon) {
        this.movementRanges = movementRanges;
        this.jumpPositions = jumpPositions;
        this.pieceIcon = pieceIcon;
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
}

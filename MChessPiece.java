/**
 * @author MrBruh
 */

import java.util.List;

import javax.swing.ImageIcon;

/**
 * A class to hold information about individual chess pieces
 */
public class MChessPiece {

    private List<Integer> movementRanges;
    private List<int[]> jumpPositions;

    private ImageIcon pieceIcon;

    /**
     * 
     * @param movementRanges
     * @param pieceIcon
     */
    public MChessPiece(List<Integer> movementRanges, ImageIcon pieceIcon) {
        this.movementRanges = movementRanges;
        this.pieceIcon = pieceIcon;
    }

    /**
     * 
     * @param movementRanges
     * @param jumpPositions
     * @param pieceIcon
     */
    public MChessPiece(List<Integer> movementRanges, List<int[]> jumpPositions, ImageIcon pieceIcon) {
        this.movementRanges = movementRanges;
        this.jumpPositions = jumpPositions;
        this.pieceIcon = pieceIcon;
    }

    /**
     * 
     * @return
     */
    public List<Integer> getMovementRanges() {
        return movementRanges;
    }

    /**
     * 
     * @return
     */
    public List<int[]> getJumpPositions() {
        return jumpPositions;
    }

    /**
     * 
     * @return
     */
    public ImageIcon getIcon() {
        return pieceIcon;
    }
}

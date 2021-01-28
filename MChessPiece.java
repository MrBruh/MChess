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
    private boolean isCaptured = false;
    private boolean isKingPiece = false;

    private boolean isPawnFirstMove = false;
    private List<Integer> pawnFirstMovementRanges;

    private MChessTile tile;

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
     * Assigns a tile to the piece
     * 
     * @param tile the tile to be assigned to the piece
     */
    public void assignTile(MChessTile tile) {
        this.tile = tile;
    }

    /**
     * Prevents the piece's tile from being selected
     */
    public void disableTile() {
        if(tile != null) {
            tile.disableTile();
        }
    }

    /**
     * Allows the piece's tile to be selected
     */
    public void enableTile() {
        if(tile != null) {
            tile.enableTile();
        }
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
     * Gets the first movement ranges of the pawn
     * 
     * @return The first movement ranges list of the pawn
     */
    public List<Integer> getFirstMovementRanges() {
        return pawnFirstMovementRanges;
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

    /**
     * Returns whether or not the piece is captured
     * 
     * @return Whether or not the piece is captured
     */
    public boolean isCaptured() {
       return isCaptured; 
    }

    /**
     * Sets the piece's captured state
     * 
     * @param isCaptured The captured state to which to set the piece
     */
    public void setCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }

    /**
     * Returns whether or not the piece is the king piece
     * 
     * @return Whether or not the piece is the king piece
     */
    public boolean isKingPiece() {
        return isKingPiece; 
     }
 
     /**
      * Sets king piece state
      * 
      * @param isKingPiece The state to set the king piece state
      */
     public void setKingPiece(boolean isKingPiece) {
         this.isKingPiece = isKingPiece;
     }

    /**
     * Used to set piece as a pawn with a first move
     */
    public void initializePawn(List<Integer> movementRanges) {
      isPawnFirstMove = true;
      pawnFirstMovementRanges = movementRanges;  
    }

    /**
     * Returns whether or not the piece is a pawn with a first move
     * 
     * @return Whether or not the piece is a pawn with a first move
     */
    public boolean isPawnFirstMove() {
        return isPawnFirstMove;
    }

    /**
      * Sets the pawn first move state
      * 
      * @param isCaptured The state to set the pawn first move state
      */
      public void setPawnFirstMove(boolean isPawnFirstMove) {
        this.isPawnFirstMove = isPawnFirstMove;
    }
}

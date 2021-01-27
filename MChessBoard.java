/**
 * @author MrBruh
 */

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.*;  

/**
 * A class to hold and manipulate information about the board
 */
public class MChessBoard {

    private MChessTile[][] boardMatrix; // This matrix holds all the indiviual tiles
    private int boardSize = 8; // This defines the board size
    private MChessTile selectedTile = null; // The piece that is selected
    private String pieceColourBlack = "black";
    private String pieceColourWhite = "white";

    /**
     * Constructor which initializes the board tiles
     * 
     * @param frame The frame to which all the elements will be added
     */
    public MChessBoard(JFrame frame){
        // Create the Matrix
        boardMatrix = new MChessTile[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardMatrix[i][j] = new MChessTile(frame, this, j, i);
            }
        }

        // Create a movement list for a king
        List<Integer> kingMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            kingMovement.add(1);
        }
        // Create and place kings using the king movement and icon
        MChessPiece whiteKing = new MChessPiece(kingMovement, new ImageIcon("Graphics/kw.png"), pieceColourWhite);
        MChessPiece blackKing = new MChessPiece(kingMovement, new ImageIcon("Graphics/kb.png"), pieceColourBlack);
        boardMatrix[7][3].assignPiece(whiteKing);
        boardMatrix[0][3].assignPiece(blackKing);

        // Create a movement list for a bishop
        List<Integer> bishopMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            if(i % 2 == 1) {
                bishopMovement.add(7);
            } else {
                bishopMovement.add(0);
            }
        }
        // Create and place bishops using the bishop movement and icon
        MChessPiece whiteBishop1 = new MChessPiece(bishopMovement, new ImageIcon("Graphics/bw.png"), pieceColourWhite);
        MChessPiece whiteBishop2 = new MChessPiece(bishopMovement, new ImageIcon("Graphics/bw.png"), pieceColourWhite);
        MChessPiece blackBishop1 = new MChessPiece(bishopMovement, new ImageIcon("Graphics/bb.png"), pieceColourBlack);
        MChessPiece blackBishop2 = new MChessPiece(bishopMovement, new ImageIcon("Graphics/bb.png"), pieceColourBlack);
        boardMatrix[7][2].assignPiece(whiteBishop1);
        boardMatrix[7][5].assignPiece(whiteBishop2);
        boardMatrix[0][2].assignPiece(blackBishop1);
        boardMatrix[0][5].assignPiece(blackBishop2);

        // Create a movement list for a queen
        List<Integer> queenMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            queenMovement.add(7);
        }
        // Create and place queens using the queen movement and icon
        MChessPiece whiteQueen = new MChessPiece(queenMovement, new ImageIcon("Graphics/qw.png"), pieceColourWhite);
        MChessPiece blackQueen = new MChessPiece(queenMovement, new ImageIcon("Graphics/qb.png"), pieceColourBlack);
        boardMatrix[7][4].assignPiece(whiteQueen);
        boardMatrix[0][4].assignPiece(blackQueen);
    }

    /**
     * This function is called by a selected tile to let the board know
     * that it has been selected.
     * 
     * @param tile The tile that is selected
     */
    public void setSelectedTile(MChessTile tile) {
        // Unselect the old tile if one has been already selected
        if(selectedTile != null){
            drawTileMovement(selectedTile, true);
            selectedTile.unselectTile();
        }
        selectedTile = tile;
    }

    /**
     * This moves a piece by pass the piece reference from the old tile
     * to the new tile, and unreferencing the old tile
     * 
     * @param newTile The new tile that a piece is moved to
     */
    public void movePieceToTile(MChessTile newTile) {
        // Undraw movement tiles
        drawTileMovement(selectedTile, true);
        // Move the piece to the new tile
        newTile.movePiece(selectedTile.getPiece());
        // Remove the reference from the old tile
        selectedTile.assignPiece(null);
        selectedTile = null;
    }

    /**
     * A function to draw movement tiles based around the movement list
     * of the reference tile's piece.
     * 
     * I would have has this as a private helper function, but is was just
     * a bit easier to make it public
     * 
     * @param tile The reference tile to draw around
     * @param unTarget Whether or not to undraw or draw the movement tiles
     */
    public void drawTileMovement(MChessTile tile, boolean unTarget) {
        // Get the piece movement ranges and the tile's position
        List<Integer> movementRanges = tile.getPiece().getMovementRanges();
        ListIterator<Integer> itr = movementRanges.listIterator();
        int[] currentPos = tile.getPos();

        // Iterate over all the ranges in the movement range list
        while(itr.hasNext()) {
            // Get range and index
            int index = itr.nextIndex();
            int range = itr.next();
            // Loop over the range of the movement range, and draw a movement
            // tile across every tile in the range
            for(int i = 0; i < range; i++) {
                // Convert the movement range into relative position
                int[] boardPos = movementRangeToBoardPos(index, i + 1, currentPos.clone());
                // Check that the relative position is valid
                if(!checkValidPos(boardPos)){
                    continue; // Skip drawing if the position is invalid
                }
                // Depending on whether or not to draw or undraw, draw or
                // undraw the movement tiles
                if (boardMatrix[boardPos[1]][boardPos[0]].targetMove(unTarget, selectedTile.getPiece())) {
                    break; // Stop looping if the tile obstructs movement
                }
            }
        }
    }

    /**
     * A helper function to check if a position is on the board or not
     * 
     * @param boardPos The position to verify
     * @return True if the position is on the board, false otherwise
     */
    private boolean checkValidPos(int[] boardPos) {
        for(int i = 0; i < boardPos.length; i++){
            if(boardPos[i] < 0 || boardPos[i] > 7){
                return false;
            }
        }
        return true;
    }

    /**
     * This converts a movement range list into a board position
     * 
     * @param index The index is used to decide direction
     * @param range The range is used to move x amount if that direction
     * @param currentPos The relative position from which to move
     * @return Returns the new position
     */
    private int[] movementRangeToBoardPos(int index, int range, int[] currentPos) {
        switch (index) {
            case 0: // North
                currentPos[1] -= range;
                return currentPos;
            case 1: // Northeast
                currentPos[0] += range;
                currentPos[1] -= range;
                return currentPos;
            case 2: // East
                currentPos[0] += range;
                return currentPos;
            case 3: // Southeast
                currentPos[0] += range;
                currentPos[1] += range;
                return currentPos;
            case 4: // South
                currentPos[1] += range;
                return currentPos;
            case 5: // Southwest
                currentPos[0] -= range;
                currentPos[1] += range;
                return currentPos;
            case 6: // West
                currentPos[0] -= range;
                return currentPos;
            case 7: // Northwest
                currentPos[0] -= range;
                currentPos[1] -= range;
                return currentPos;
            default:
                // Required to prevent compiler from complaining
                return currentPos;
        }
    }
}

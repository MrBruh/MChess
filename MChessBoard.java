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

        List<Integer> kingMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            kingMovement.add(1);
        }
        MChessPiece whiteKing = new MChessPiece(kingMovement, new ImageIcon("Graphics/kw.png"));
        boardMatrix[1][2].assignPiece(whiteKing);

        List<Integer> bishopMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            if(i % 2 == 1) {
                bishopMovement.add(7);
            } else {
                bishopMovement.add(0);
            }
        }
        MChessPiece blackBishop = new MChessPiece(bishopMovement, new ImageIcon("Graphics/bb.png"));
        boardMatrix[3][3].assignPiece(blackBishop);
    }

    /**
     * 
     * @param tile
     */
    public void setSelectedTile(MChessTile tile) {
        selectedTile = tile;
    }

    /**
     * 
     * @param newTile
     */
    public void movePieceToTile(MChessTile newTile) {
        drawTileMovement(selectedTile, true);
        newTile.movePiece(selectedTile.getPiece());
        selectedTile.assignPiece(null);
        selectedTile = null;
    }

    /**
     * 
     * 
     * @param tile
     * @param unTarget
     */
    public void drawTileMovement(MChessTile tile, boolean unTarget) {
        List<Integer> movementRanges = tile.getPiece().getMovementRanges();
        ListIterator<Integer> itr = movementRanges.listIterator();
        int[] currentPos = tile.getPos();
        while(itr.hasNext()) {
            int index = itr.nextIndex();
            int value = itr.next();
            for(int i = 0; i < value; i++) {
                System.out.println("draw " + i);
                int[] boardPos = movementRangeToBoardPos(index, i + 1, currentPos.clone());
                if(!checkValidPos(boardPos)){
                    System.out.println("skipping: " + boardPos[0] + ", " + boardPos[1]);
                    continue;
                }
                if(unTarget) {
                    boardMatrix[boardPos[1]][boardPos[0]].unTargetMove();
                } else {
                    boardMatrix[boardPos[1]][boardPos[0]].targetMove();
                }
            }
        }
    }

    /**
     * 
     * 
     * @param boardPos
     * @return
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
     * 
     * 
     * @param index
     * @param range
     * @param currentPos
     * @return
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
                return currentPos;
        }
    }
}

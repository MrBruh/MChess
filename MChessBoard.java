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
        boardMatrix[1][1].assignPiece(whiteKing);
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
        newTile.movePiece(selectedTile.getPiece());
        selectedTile.assignPiece(null);
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
        while(itr.hasNext()) {
            int index = itr.nextIndex();
            int value = itr.next();
            for(int i = 0; i < value; i++) {
                int[] boardPos = movementRangeToBoardPos(index, tile.getPos());
                if(unTarget) {
                    boardMatrix[boardPos[0]][boardPos[1]].unTargetMove();
                } else {
                    boardMatrix[boardPos[0]][boardPos[1]].targetMove();
                }
            }
        }
    }

    private int[] movementRangeToBoardPos(int index, int[] range) {
        switch (index) {
            case 0: // North
                range[1] -= 1;
                return range;
            case 1: // Northeast
                range[0] += 1;
                range[1] -= 1;
                return range;
            case 2: // East
                range[0] += 1;
                return range;
            case 3: // Southeast
                range[0] += 1;
                range[1] += 1;
                return range;
            case 4: // South
                range[1] += 1;
                return range;
            case 5: // Southwest
                range[0] -= 1;
                range[1] += 1;
                return range;
            case 6: // West
                range[0] -= 1;
                return range;
            case 7: // Northwest
                range[0] -= 1;
                range[1] -= 1;
                return range;
            default:
                return range;
        }
    }
}

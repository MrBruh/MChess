/**
 * @author MrBruh
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    private MChessGUI gui;
    
    private List<MChessPiece> whitePieceList = new ArrayList<>();
    private List<MChessPiece> blackPieceList = new ArrayList<>();

    private int turns = 0;

    /**
     * Constructor which initializes the board tiles
     * 
     * @param frame The frame to which all the elements will be added
     */
    public MChessBoard(JFrame frame, MChessGUI gui){
        this.gui = gui;

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
        whiteKing.setKingPiece(true);
        blackKing.setKingPiece(true);

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

        // Create a movement list for a queen
        List<Integer> queenMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            queenMovement.add(7);
        }
        // Create and place queens using the queen movement and icon
        MChessPiece whiteQueen = new MChessPiece(queenMovement, new ImageIcon("Graphics/qw.png"), pieceColourWhite);
        MChessPiece blackQueen = new MChessPiece(queenMovement, new ImageIcon("Graphics/qb.png"), pieceColourBlack);

        List<Integer> rookMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            if(i % 2 == 0) {
                rookMovement.add(7);
            } else {
                rookMovement.add(0);
            }
        }
        MChessPiece whiteRook1 = new MChessPiece(rookMovement, new ImageIcon("Graphics/rw.png"), pieceColourWhite);
        MChessPiece whiteRook2 = new MChessPiece(rookMovement, new ImageIcon("Graphics/rw.png"), pieceColourWhite);
        MChessPiece blackRook1 = new MChessPiece(rookMovement, new ImageIcon("Graphics/rb.png"), pieceColourBlack);
        MChessPiece blackRook2 = new MChessPiece(rookMovement, new ImageIcon("Graphics/rb.png"), pieceColourBlack);

        List<Integer> knightMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            knightMovement.add(0);
        }
        List<int[]> knightJumps = new ArrayList<>();
        knightJumps.add(new int[]{-2, 1});
        knightJumps.add(new int[]{-2, -1});
        knightJumps.add(new int[]{-1, 2});
        knightJumps.add(new int[]{1, 2});
        knightJumps.add(new int[]{2, 1});
        knightJumps.add(new int[]{2, -1});
        knightJumps.add(new int[]{-1, -2});
        knightJumps.add(new int[]{1, -2});
        MChessPiece whiteKnight1 = new MChessPiece(knightMovement, knightJumps, new ImageIcon("Graphics/nw.png"), pieceColourWhite);
        MChessPiece whiteKnight2 = new MChessPiece(knightMovement, knightJumps, new ImageIcon("Graphics/nw.png"), pieceColourWhite);
        MChessPiece blackKnight1 = new MChessPiece(knightMovement, knightJumps, new ImageIcon("Graphics/nb.png"), pieceColourBlack);
        MChessPiece blackKnight2 = new MChessPiece(knightMovement, knightJumps, new ImageIcon("Graphics/nb.png"), pieceColourBlack);

        List<Integer> whitePawnMovement = new ArrayList<>();
        whitePawnMovement.add(1);
        for(int i = 0; i < 7; i++) {
            whitePawnMovement.add(0);
        }
        List<Integer> whitePawnFirstMovement = new ArrayList<>();
        whitePawnFirstMovement.add(2);
        for(int i = 0; i < 7; i++) {
            whitePawnFirstMovement.add(0);
        }

        List<Integer> blackPawnMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            if(i == 4) {
                blackPawnMovement.add(1);
            }  else {
                blackPawnMovement.add(0);
            }
        }
        List<Integer> blackPawnFirstMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            if(i == 4) {
                blackPawnFirstMovement.add(2);
            }  else {
                blackPawnFirstMovement.add(0);
            }
        }

        // Must add the pieces in this order to the piece lists
        whitePieceList.add(whiteRook1);
        whitePieceList.add(whiteKnight1);
        whitePieceList.add(whiteBishop1);
        whitePieceList.add(whiteQueen);
        whitePieceList.add(whiteKing);
        whitePieceList.add(whiteBishop2);
        whitePieceList.add(whiteKnight2);
        whitePieceList.add(whiteRook2);
        for(int i = 0; i < 8; i++) {
            whitePieceList.add(new MChessPiece(whitePawnMovement, new ImageIcon("Graphics/pw.png"), pieceColourWhite));
            whitePieceList.get(8 + i).initializePawn(whitePawnFirstMovement);
        }

        blackPieceList.add(blackRook1);
        blackPieceList.add(blackKnight1);
        blackPieceList.add(blackBishop1);
        blackPieceList.add(blackQueen);
        blackPieceList.add(blackKing);
        blackPieceList.add(blackBishop2);
        blackPieceList.add(blackKnight2);
        blackPieceList.add(blackRook2);
        for(int i = 0; i < 8; i++) {
            blackPieceList.add(new MChessPiece(blackPawnMovement, new ImageIcon("Graphics/pb.png"), pieceColourBlack));
            blackPieceList.get(8 + i).initializePawn(blackPawnFirstMovement);
        }

        resetPositions();
    }

    public void resetPositions() {
        // I know it's unconventional but it's conveniant
        for(int i = 2; i < 6; i++){
            for(int j = 0; j < 8; j++){
                if(boardMatrix[i][j].getPiece() != null){
                    boardMatrix[i][j].assignPiece(null);
                }
            }
        }
        resetPieceList(whitePieceList, 7, 6);
        resetPieceList(blackPieceList, 0, 1);
        turns = 0;
        disableBlack();
    }

    private void resetPieceList(List<MChessPiece> pieceList, int heavyRow, int pawnRow) {
        ListIterator<MChessPiece> itr = pieceList.listIterator();
        int index;
        while(itr.hasNext()) {
            index = itr.nextIndex();
            if(index < 8) {
                boardMatrix[heavyRow][index].assignPiece(itr.next());
            } else { 
                boardMatrix[pawnRow][index - 8].assignPiece(itr.next());
            }
        }
    }

    /**
     * Disables black pieces and enables white pieces
     */
    public void disableWhite() {
        for(MChessPiece piece : whitePieceList) {
            piece.disableTile();
        }
        for(MChessPiece piece : blackPieceList) {
            piece.enableTile();
        }
    }

    /**
     * Disables black pieces and enables white pieces
     */
    public void disableBlack() {
        for(MChessPiece piece : blackPieceList) {
            piece.disableTile();
        }
        for(MChessPiece piece : whitePieceList) {
            piece.enableTile();
        }
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
        if(selectedTile.getPiece().isPawnFirstMove()) {
            selectedTile.getPiece().setPawnFirstMove(false);
        }
        newTile.movePiece(selectedTile.getPiece());
        // Remove the reference from the old tile
        selectedTile.assignPiece(null);
        selectedTile = null;

        // Set the next turn
        turns += 1;
        if(turns % 2 == 0) {
            disableBlack();
        } else {
            disableWhite();
        }

        gui.updateTurn(turns);
    }

    /**
     * 
     * 
     * @return
     */
    public MChessGUI getGUI() {
        return gui;
    }

    /**
     * 
     * 
     * @return
     */
    public int getTurns() {
        return turns;
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
        List<Integer> movementRanges; 
        if(tile.getPiece().isPawnFirstMove()) {
            movementRanges = tile.getPiece().getFirstMovementRanges();
        } else {
            movementRanges = tile.getPiece().getMovementRanges();
        }
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

        if(tile.getPiece().getJumpPositions() != null) {
            drawTileJumps(tile, unTarget, tile.getPiece().getJumpPositions());
        }
    }

    /**
     * 
     * 
     * @param tile
     * @param unTarget
     * @param jumpPositions
     */
    private void drawTileJumps(MChessTile tile, boolean unTarget, List<int[]> jumpPositions) {
        ListIterator<int[]> itr = jumpPositions.listIterator();
        while(itr.hasNext()) {
            // Get jump positions
            int[] jumpPos = itr.next();
            int[] boardPos = new int[]{jumpPos[0] + tile.getPos()[0], jumpPos[1] + tile.getPos()[1]};

            if(!checkValidPos(boardPos)){
                continue; // Skip drawing if the position is invalid
            }
            // Depending on whether or not to draw or undraw, draw or
            // undraw the movement tiles
            boardMatrix[boardPos[1]][boardPos[0]].targetMove(unTarget, selectedTile.getPiece());
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

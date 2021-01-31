/**
 * @author MrBruh
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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

    private Map<String, List<Integer>> movementRangesMap = new HashMap<>();
    private Map<String, List<int[]>> attackPatternsMap = new HashMap<>();

    private int turns = 0;

    /**
     * Constructor which initializes the board tiles
     * 
     * @param frame The frame to which all the elements will be added
     */
    public MChessBoard(JFrame frame, MChessGUI gui){
        this.gui = gui;

        // Create the board matrix
        boardMatrix = new MChessTile[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardMatrix[i][j] = new MChessTile(frame, this, j, i);
            }
        }

        // This huge section is basically defining all the pieces and their
        // abilities and movements

        createMovementRanges();
        createAttackPatterns();

        // Create and place kings using the king movement and icon
        MChessPiece whiteKing = new MChessPiece(movementRangesMap.get("king"), new ImageIcon("Graphics/kw.png"), pieceColourWhite);
        MChessPiece blackKing = new MChessPiece(movementRangesMap.get("king"), new ImageIcon("Graphics/kb.png"), pieceColourBlack);
        whiteKing.setKingPiece(true);
        blackKing.setKingPiece(true);

        // Create and place bishops using the bishop movement and icon
        MChessPiece whiteBishop1 = new MChessPiece(movementRangesMap.get("bishop"), new ImageIcon("Graphics/bw.png"), pieceColourWhite);
        MChessPiece whiteBishop2 = new MChessPiece(movementRangesMap.get("bishop"), new ImageIcon("Graphics/bw.png"), pieceColourWhite);
        MChessPiece blackBishop1 = new MChessPiece(movementRangesMap.get("bishop"), new ImageIcon("Graphics/bb.png"), pieceColourBlack);
        MChessPiece blackBishop2 = new MChessPiece(movementRangesMap.get("bishop"), new ImageIcon("Graphics/bb.png"), pieceColourBlack);
        whiteBishop1.setEthereal(true);
        whiteBishop2.setEthereal(true);
        blackBishop1.setEthereal(true);
        blackBishop2.setEthereal(true);

        // Create and place queens using the queen movement and icon
        MChessPiece whiteQueen = new MChessPiece(movementRangesMap.get("queen"), new ImageIcon("Graphics/qw.png"), pieceColourWhite);
        MChessPiece blackQueen = new MChessPiece(movementRangesMap.get("queen"), new ImageIcon("Graphics/qb.png"), pieceColourBlack);

        MChessPiece whiteRook1 = new MChessPiece(movementRangesMap.get("rook"), new ImageIcon("Graphics/rw.png"), pieceColourWhite);
        MChessPiece whiteRook2 = new MChessPiece(movementRangesMap.get("rook"), new ImageIcon("Graphics/rw.png"), pieceColourWhite);
        MChessPiece blackRook1 = new MChessPiece(movementRangesMap.get("rook"), new ImageIcon("Graphics/rb.png"), pieceColourBlack);
        MChessPiece blackRook2 = new MChessPiece(movementRangesMap.get("rook"), new ImageIcon("Graphics/rb.png"), pieceColourBlack);
        whiteRook1.setAttackPattern(attackPatternsMap.get("rook"));
        whiteRook2.setAttackPattern(attackPatternsMap.get("rook"));
        blackRook1.setAttackPattern(attackPatternsMap.get("rook"));
        blackRook2.setAttackPattern(attackPatternsMap.get("rook"));

        List<int[]> knightJumps = new ArrayList<>();
        knightJumps.add(new int[]{-2, 1});
        knightJumps.add(new int[]{-2, -1});
        knightJumps.add(new int[]{-1, 2});
        knightJumps.add(new int[]{1, 2});
        knightJumps.add(new int[]{2, 1});
        knightJumps.add(new int[]{2, -1});
        knightJumps.add(new int[]{-1, -2});
        knightJumps.add(new int[]{1, -2});
        MChessPiece whiteKnight1 = new MChessPiece(movementRangesMap.get("knight"), knightJumps, new ImageIcon("Graphics/nw.png"), pieceColourWhite);
        MChessPiece whiteKnight2 = new MChessPiece(movementRangesMap.get("knight"), knightJumps, new ImageIcon("Graphics/nw.png"), pieceColourWhite);
        MChessPiece blackKnight1 = new MChessPiece(movementRangesMap.get("knight"), knightJumps, new ImageIcon("Graphics/nb.png"), pieceColourBlack);
        MChessPiece blackKnight2 = new MChessPiece(movementRangesMap.get("knight"), knightJumps, new ImageIcon("Graphics/nb.png"), pieceColourBlack);
        whiteKnight1.setAttackPattern(attackPatternsMap.get("knight"));
        whiteKnight2.setAttackPattern(attackPatternsMap.get("knight"));
        blackKnight1.setAttackPattern(attackPatternsMap.get("knight"));
        blackKnight2.setAttackPattern(attackPatternsMap.get("knight"));

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
            // Define pawns in a loop because there lots of them
            whitePieceList.add(new MChessPiece(movementRangesMap.get("wPawn"), new ImageIcon("Graphics/pw.png"), pieceColourWhite));
            whitePieceList.get(8 + i).initializePawn(movementRangesMap.get("wPawnFirst"));
            whitePieceList.get(8 + i).setNotMoveAttacker(true);
            whitePieceList.get(8 + i).setAttackPattern(attackPatternsMap.get("wPawn"));
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
            // Define pawns in a loop because there lots of them
            blackPieceList.add(new MChessPiece(movementRangesMap.get("bPawn"), new ImageIcon("Graphics/pb.png"), pieceColourBlack));
            blackPieceList.get(8 + i).initializePawn(movementRangesMap.get("bPawnFirst"));
            blackPieceList.get(8 + i).setNotMoveAttacker(true);
            blackPieceList.get(8 + i).setAttackPattern(attackPatternsMap.get("bPawn"));
        }

        // Put all the pieces in their proper positions
        resetPositions();
    }

    /**
     * This is where the movement ranges of all the pieces are defined
     * The movement ranges define how far a piece can move in each direction
     * index 0 is north, going clockwise to index 7, which is northwest
     */
    private void createMovementRanges() {
        // Create a movement list for a king
        List<Integer> kingMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            kingMovement.add(1);
        }

        // Create a movement list for a bishop
        List<Integer> bishopMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            if(i % 2 == 1) {
                bishopMovement.add(3);
            } else {
                bishopMovement.add(0);
            }
        }

        // Create a movement list for a queen
        List<Integer> queenMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            queenMovement.add(7);
        }

        // Create a movement list for a rook
        List<Integer> rookMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++) {
            if(i % 2 == 0) {
                rookMovement.add(7);
            } else {
                rookMovement.add(0);
            }
        }

        // Create a movement list for a knight
        List<Integer> knightMovement = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            knightMovement.add(0);
        }

        // Create a movement list for a white pawn and it's first move
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

        // Create a movement list for a black pawn and it's first move
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

        // Put all the movement ranges into a map
        movementRangesMap.put("king", kingMovement);
        movementRangesMap.put("queen", queenMovement);
        movementRangesMap.put("bishop", bishopMovement);
        movementRangesMap.put("rook", rookMovement);
        movementRangesMap.put("knight", knightMovement);
        movementRangesMap.put("wPawn", whitePawnMovement);
        movementRangesMap.put("wPawnFirst", whitePawnFirstMovement);
        movementRangesMap.put("bPawn", blackPawnMovement);
        movementRangesMap.put("bPawnFirst", blackPawnFirstMovement);
    }

    /**
     * This helper functions is where attack patterns are created, which store
     * relative positions.
     */
    private void createAttackPatterns() {
        List<int[]> whitePawnAttacks = new ArrayList<>();
        whitePawnAttacks.add(new int[]{1, -1});
        whitePawnAttacks.add(new int[]{-1, -1});

        List<int[]> blackPawnAttacks = new ArrayList<>();
        blackPawnAttacks.add(new int[]{1, 1});
        blackPawnAttacks.add(new int[]{-1, 1});

        List<int[]> knightAttacks = new ArrayList<>();
        knightAttacks.add(new int[]{0, 1});
        knightAttacks.add(new int[]{0, -1});
        knightAttacks.add(new int[]{1, 0});
        knightAttacks.add(new int[]{-1, 0});

        List<int[]> rookAttacks = new ArrayList<>();
        rookAttacks.add(new int[]{1, 1});
        rookAttacks.add(new int[]{1, -1});
        rookAttacks.add(new int[]{1, -1});
        rookAttacks.add(new int[]{-1, -1});

        attackPatternsMap.put("wPawn", whitePawnAttacks);
        attackPatternsMap.put("bPawn", blackPawnAttacks);
        attackPatternsMap.put("knight", knightAttacks);
        attackPatternsMap.put("rook", rookAttacks);
    }

    /**
     * This function is called at the start of every new game, and dereferences
     * the piece reference from the middle tiles, and then sets all the pieces
     * to their proper positions by using resetPieceList, reset turn number and
     * disables black pieces 
     */
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

    /**
     * This helper function puts all the pieces into their proper places, given
     * The rows(This is why the pieces had to be add to the piece list in a
     * specific order)
     * The first 8 pieces in the list are non-pawns(heavy), and are put into
     * their designated row. Then next 8 pieces are pawns, which get put into
     * their designated row.
     * 
     * @param pieceList The piece list to place the pieces from
     * @param heavyRow The row to put the non-pawns into
     * @param pawnRow The row to put the pawns into
     */
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
     * that it has been selected. Is also used to unselect tiles.
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
     * This moves a piece by passing the piece reference from the old tile
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
        // Update the turn in the GUI
        gui.updateTurn(turns);
    }

    /**
     * This updates the GUI after a piece has attacked another without moving
     * 
     * @param newTile The new tile that a piece is moved to
     */
    public void attackPieceToTile(MChessTile newTile) {
        // Undraw movement tiles
        drawTileMovement(selectedTile, true);
        // Remove the reference from the attack tile
        newTile.assignPiece(null);
        // Unselect the selected piece
        selectedTile.unselectTile();
        selectedTile = null;

        // Set the next turn
        turns += 1;
        if(turns % 2 == 0) {
            disableBlack();
        } else {
            disableWhite();
        }
        // Update the GUI
        gui.updateTurn(turns);
    }

    /**
     * This returns the GUI reference of the board
     * 
     * @return The gui reference of the board
     */
    public MChessGUI getGUI() {
        return gui;
    }

    /**
     * This function returns the number of turns that have passed, which is
     * used by the GUI to determine who's turn it is to display
     * 
     * @return The number of turns that have passed so far
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
                if (boardMatrix[boardPos[1]][boardPos[0]].targetMove(unTarget, selectedTile.getPiece()) &&
                    !tile.getPiece().isEthereal()) {
                    break; // Stop looping if the tile obstructs movement
                }
            }
        }
        // This draws the jump positions of the piece if it has any
        if(tile.getPiece().getJumpPositions() != null) {
            drawTileJumps(tile, unTarget, tile.getPiece().getJumpPositions());
        }
        // This draws the attack patterns of the piece if it has any
        if(tile.getPiece().getAttackPattern() != null) {
            drawTileAttacks(tile, unTarget, tile.getPiece().getAttackPattern());
        }
    }

    /**
     * This helper method has almost the same function as above, 
     * but since jump positions are already relative, we don't have
     * to bother converting
     * 
     * @param tile The reference tile to draw around
     * @param unTarget Whether or not to undraw or draw the movement tiles
     * @param jumpPositions The jump positions to use
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
            // Draw the jump position
            boardMatrix[boardPos[1]][boardPos[0]].targetMove(unTarget, selectedTile.getPiece());
        }
    }

    /**
     * Again, the same as above but for attack patterns instead
     * 
     * @param tile The reference tile to draw around
     * @param unTarget Whether or not to undraw or draw the movement tiles
     * @param attackPattern The attack pattern to use
     */
    private void drawTileAttacks(MChessTile tile, boolean unTarget, List<int[]> attackPattern) {
        ListIterator<int[]> itr = attackPattern.listIterator();
        while(itr.hasNext()) {
            // Get attack positions
            int[] attackPos = itr.next();
            int[] boardPos = new int[]{attackPos[0] + tile.getPos()[0], attackPos[1] + tile.getPos()[1]};

            if(!checkValidPos(boardPos)){
                continue; // Skip drawing if the position is invalid
            }
            // Draw the attack position
            boardMatrix[boardPos[1]][boardPos[0]].targetAttack(unTarget, selectedTile.getPiece());
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
     * This converts a movement range list and current position into a board position
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

/**
 * @author MrBruh 
 */

import java.awt.*;
import java.awt.Font;
import java.awt.event.*; 
import java.awt.image.BufferedImage;


import javax.swing.*;  

/**
 * A class to handle all the GUI elements
 */
public class MChessGUI {
    private int frameWidth = 760;
    private int frameHight = 597;
    
    private JFrame mainFrame = new JFrame();
    private JFrame instructionsFrame = new JFrame();
    private MChessBoard board;
    private Font textFont = new Font("Dialog", Font.PLAIN, 24);
    private Font buttonFont = new Font("Dialog", Font.PLAIN, 18);
    private Font instructionsFont = new Font("Dialog", Font.PLAIN, 20);
    private JLabel turnIndicator = new JLabel("<html>");
    private JLabel whiteCapturedLabel = new JLabel("<html><div style='text-align: center;'><b>White<b></div></html>");
    private JLabel blackCapturedLabel = new JLabel("<html><div style='text-align: center;'><b>Black<b></div></html>");
    private JLabel instructionsLabel = new JLabel("<html>");
    private JButton forfeitButton = new JButton("Forfeit");
    private JButton playAgainButton = new JButton("Play Again");
    private JButton startButton = new JButton("Start");
    private boolean gameWon = false;

    private JLabel whiteCapturedDisplayLabel = new JLabel();
    private JLabel blackCapturedDisplayLabel = new JLabel();
    private BufferedImage whiteCapturedImage;
    private BufferedImage blackCapturedImage;
    private Graphics whiteCapturedGraphics;
    private Graphics blackCapturedGraphics;
    private int whiteCapturedCount = 0;
    private int blackCapturedCount = 0;
    
    private String whiteTurnText = "<html><div style='text-align: center;'><b>White's turn<b></div></html>";
    private String blackTurnText = "<html><div style='text-align: center;'><b>Black's turn<b></div></html>";

    /**
     * The constructor of MChessGUI that sets up the GUI of the game
     */
    public MChessGUI() {

        // This huge section just sets up the GUI elements, and defines the
        // MChess board at the end
        setupFrame(mainFrame);
        setupFrame(instructionsFrame);
        mainFrame.setVisible(false);
        instructionsFrame.setVisible(true);

        turnIndicator.setBounds(580, 50, 180, 50);
        turnIndicator.setFont(textFont);
        turnIndicator.setOpaque(false);
        turnIndicator.setFocusable(false);
        turnIndicator.setText(whiteTurnText);

        mainFrame.add(turnIndicator);

        whiteCapturedLabel.setBounds(580, 317, 180, 50);
        whiteCapturedLabel.setFont(textFont);
        whiteCapturedLabel.setOpaque(false);
        whiteCapturedLabel.setFocusable(false);

        mainFrame.add(whiteCapturedLabel);

        whiteCapturedDisplayLabel.setBounds(560, 322, 190, 150);
        whiteCapturedDisplayLabel.setOpaque(false);
        whiteCapturedDisplayLabel.setFocusable(false);

        mainFrame.add(whiteCapturedDisplayLabel);

        blackCapturedLabel.setBounds(580, 432, 180, 50);
        blackCapturedLabel.setFont(textFont);
        blackCapturedLabel.setOpaque(false);
        blackCapturedLabel.setFocusable(false);

        mainFrame.add(blackCapturedLabel);

        blackCapturedDisplayLabel.setBounds(560, 437, 190, 150);
        blackCapturedDisplayLabel.setOpaque(false);
        blackCapturedDisplayLabel.setFocusable(false);

        mainFrame.add(blackCapturedDisplayLabel);

        forfeitButton.setBounds(590, 130, 140, 50);
        forfeitButton.setFont(buttonFont);
        forfeitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(board.getTurns() % 2 == 0) {
                    setWinner("white");
                } else {
                    setWinner("black");
                }
            }
        });

        mainFrame.add(forfeitButton);

        playAgainButton.setBounds(590, 185, 140, 50);
        playAgainButton.setFont(buttonFont);
        playAgainButton.setEnabled(false);
        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                forfeitButton.setEnabled(true);
                playAgainButton.setEnabled(false);
                resetGame();
            }
        });

        mainFrame.add(playAgainButton);

        startButton.setBounds(600, 250, 140, 50);
        startButton.setFont(buttonFont);
        startButton.setEnabled(true);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instructionsFrame.setVisible(false);
                mainFrame.setVisible(true);
            }
        });

        instructionsFrame.add(startButton);

        instructionsLabel.setBounds(25, 0, 550, 597);
        instructionsLabel.setFont(instructionsFont); 
        instructionsLabel.setOpaque(false);
        instructionsLabel.setFocusable(false);
        instructionsLabel.setText("<html><p style='margin-top:10px'>" +
                                        "INSTRUCTIONS:<br>" +
                                        "MChess is played similar to Chess, but with some key differences.<br>" +
                                        "Firstly, you don't have to worry about common chess mechanics, all you have to do is to take the enemy's king piece to win.<br>" + 
                                        "Secondly, glowing pieces are those who have different abilities than of their regular counter parts.<br>" + 
                                        "Each piece has a move that they can perform when selected based on the tile colour.<br>" + 
                                        "A green tile is a regular move, a red tile is a regualar capture,<br>" + 
                                        "A purple tile is a move that isn't block by pieces, and an orange tile captures without moving there.<br>" + 
                                        "Have fun!</html>");

        instructionsFrame.add(instructionsLabel);

        resetCapturedImages();

        board = new MChessBoard(mainFrame, this);

        // This fixes the issue of some things not displaying properly
        instructionsFrame.setVisible(false);
        instructionsFrame.setVisible(true);
        
    }

    /**
     * This help functions sets up a given frame to default MChess parameters
     * 
     * @param frame The frame that is to be setup up with the defualt parameters
     */
    private void setupFrame(JFrame frame) {
        frame.setSize(frameWidth, frameHight);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setTitle("MChess");
    }

    /**
     * This function sets the win condition for the game
     * The loser is a parameter instead of the winner because the king piece is
     * the one that detects that it has been captured, and the player who loses
     * thier king piece is the loser
     * 
     * @param loser the player that lost
     */
    private void setWinner(String loser) {
        if(loser.equals("black")) {
            turnIndicator.setText("<html><div style='text-align: center;'><b>White wins!<b></div></html>");
        } else {
            turnIndicator.setText("<html><div style='text-align: center;'><b>Black wins!<b></div></html>");
        }
        playAgainButton.setEnabled(true);
        forfeitButton.setEnabled(false);
        gameWon = true;
    }

    /**
     * This helper function is called at the beginning of every new roung to
     * reset all the variables, positions, and images on the board and GUI.
     */
    private void resetGame(){
        board.resetPositions();
        turnIndicator.setText(whiteTurnText);
        resetCapturedImages();
        gameWon = false;
    }
    
    /**
     * This function is called at the end of every turn to update the turn
     * indicator text based on the number of turns so far. Won't do anything
     * if the game is in the win state
     * 
     * @param turns The number of turns to base the next turn off of
     */
    public void updateTurn(int turns) {
        if(gameWon){
            return;
        }
        if(turns % 2 == 0) {
            turnIndicator.setText(whiteTurnText);
        } else {
            turnIndicator.setText(blackTurnText);
        }
    }

    /**
     * Thing function handles the capturing mechanics of a piece.
     * It dereferences the tile from the piece and puts it into a captured
     * state. Then the piece's icon is added to the captured section of the GUI
     * based on colour and how pieces have been captured.
     * 
     * @param piece The piece to add to the captured collection
     */
    public void addCaptured(MChessPiece piece) {
        // Derefernce the tile and set the piece to be captured
        piece.assignTile(null);
        piece.setCaptured(true);
        // If the piece was a king piece, then set a winner
        if(piece.isKingPiece()){
            setWinner(piece.getColour());
        }
        // Depending on the colour, add the piece to either the black or white
        // collection, and add it to the second row if there are too many
        // pieces
        if(piece.getColour().equals("white")) {
            if( blackCapturedCount >= 7) {
                piece.getIcon().paintIcon(null, blackCapturedGraphics, ((blackCapturedCount - 7)* 20), 20);
            } else {
                piece.getIcon().paintIcon(null, blackCapturedGraphics, (blackCapturedCount * 20), 0);
            }
            blackCapturedDisplayLabel.setIcon(new ImageIcon(blackCapturedImage));
            blackCapturedCount += 1;
        } else {
            if( whiteCapturedCount >= 7) {
                piece.getIcon().paintIcon(null, whiteCapturedGraphics, ((whiteCapturedCount - 7) * 20), 20);
            } else {
                piece.getIcon().paintIcon(null, whiteCapturedGraphics, (whiteCapturedCount * 20), 0);
            }
            whiteCapturedDisplayLabel.setIcon(new ImageIcon(whiteCapturedImage));
            whiteCapturedCount += 1;
        }
    }

    /**
     * This helper fucntion resets the images for displaying the captured
     * pieces
     */
    private void resetCapturedImages() {
        whiteCapturedDisplayLabel.setIcon(null);
        blackCapturedDisplayLabel.setIcon(null);
        whiteCapturedImage = new BufferedImage(200, 90, BufferedImage.TYPE_INT_ARGB);
        blackCapturedImage = new BufferedImage(200, 90, BufferedImage.TYPE_INT_ARGB);
        whiteCapturedGraphics = whiteCapturedImage.getGraphics();
        blackCapturedGraphics = blackCapturedImage.getGraphics();
        whiteCapturedCount = 0;
        blackCapturedCount = 0;
    }
}

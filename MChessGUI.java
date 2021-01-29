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
    
    private JFrame f;
    private MChessBoard board;
    private Font textFont = new Font("Dialog", Font.PLAIN, 24);
    private Font buttonFont = new Font("Dialog", Font.PLAIN, 18);
    private JLabel turnIndicator = new JLabel("<html>");
    private JLabel whiteCapturedLabel = new JLabel("<html><div style='text-align: center;'><b>White<b></div></html>");
    private JLabel blackCapturedLabel = new JLabel("<html><div style='text-align: center;'><b>Black<b></div></html>");
    private JButton forfeitButton = new JButton("Forfeit");
    private JButton playAgainButton = new JButton("Play Again");
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
     * 
     * @param frame The frame to which all the elements will be added
     */
    public MChessGUI(JFrame frame) {
        f = frame;

        f.setSize(frameWidth, frameHight);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setTitle("MChess");

        turnIndicator.setBounds(580, 50, 180, 50);
        turnIndicator.setFont(textFont);
        turnIndicator.setOpaque(false);
        turnIndicator.setFocusable(false);
        turnIndicator.setText(whiteTurnText);

        f.add(turnIndicator);

        whiteCapturedLabel.setBounds(580, 317, 180, 50);
        whiteCapturedLabel.setFont(textFont);
        whiteCapturedLabel.setOpaque(false);
        whiteCapturedLabel.setFocusable(false);

        f.add(whiteCapturedLabel);

        whiteCapturedDisplayLabel.setBounds(560, 322, 190, 150);
        whiteCapturedDisplayLabel.setOpaque(false);
        whiteCapturedDisplayLabel.setFocusable(false);

        f.add(whiteCapturedDisplayLabel);

        blackCapturedLabel.setBounds(580, 432, 180, 50);
        blackCapturedLabel.setFont(textFont);
        blackCapturedLabel.setOpaque(false);
        blackCapturedLabel.setFocusable(false);

        f.add(blackCapturedLabel);

        blackCapturedDisplayLabel.setBounds(560, 437, 190, 150);
        blackCapturedDisplayLabel.setOpaque(false);
        blackCapturedDisplayLabel.setFocusable(false);

        f.add(blackCapturedDisplayLabel);

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

        f.add(forfeitButton);

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

        f.add(playAgainButton);

        resetCapturedImages();

        board = new MChessBoard(frame, this);
        
        f.setVisible(false);
        f.setVisible(true);
        
    }

    /**
     * 
     * @param winner
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

    private void resetGame(){
        board.resetPositions();
        turnIndicator.setText(whiteTurnText);
        resetCapturedImages();
        gameWon = false;
    }
    
    /**
     * 
     * 
     * @param turns
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
     * 
     * 
     * @param piece
     */
    public void addCaptured(MChessPiece piece) {
        piece.assignTile(null);
        piece.setCaptured(true);
        if(piece.isKingPiece()){
            setWinner(piece.getColour());
        }
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
                System.out.println("over");
            } else {
                piece.getIcon().paintIcon(null, whiteCapturedGraphics, (whiteCapturedCount * 20), 0);
            }
            whiteCapturedDisplayLabel.setIcon(new ImageIcon(whiteCapturedImage));
            whiteCapturedCount += 1;
        }
    }

    /**
     * 
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

    public void testfunc(int i) {
        //
    }

}

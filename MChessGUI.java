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

        whiteCapturedLabel.setBounds(580, 307, 180, 50);
        whiteCapturedLabel.setFont(textFont);
        whiteCapturedLabel.setOpaque(false);
        whiteCapturedLabel.setFocusable(false);

        f.add(whiteCapturedLabel);

        whiteCapturedDisplayLabel.setBounds(580, 357, 180, 70);
        whiteCapturedDisplayLabel.setOpaque(true);
        whiteCapturedDisplayLabel.setFocusable(false);

        f.add(whiteCapturedDisplayLabel);

        blackCapturedLabel.setBounds(580, 427, 180, 50);
        blackCapturedLabel.setFont(textFont);
        blackCapturedLabel.setOpaque(false);
        blackCapturedLabel.setFocusable(false);

        f.add(blackCapturedLabel);

        blackCapturedDisplayLabel.setBounds(580, 477, 180, 70);
        blackCapturedDisplayLabel.setOpaque(true);
        blackCapturedDisplayLabel.setFocusable(false);

        f.add(blackCapturedDisplayLabel);

        forfeitButton.setBounds(610, 150, 100, 50);
        forfeitButton.setFont(buttonFont);
        forfeitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("give up");
            }
        });

        f.add(forfeitButton);

        resetCapturedImages();

        board = new MChessBoard(frame, this);
        
        f.setVisible(false);
        f.setVisible(true);
        
    }
    
    /**
     * 
     * 
     * @param turns
     */
    public void updateTurn(int turns) {
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
        if(piece.getColour().equals("white")) {
            piece.getIcon().paintIcon(null, blackCapturedGraphics, (blackCapturedCount * 20), 0);
            blackCapturedDisplayLabel.setIcon(new ImageIcon(blackCapturedImage));
            blackCapturedCount += 1;
        } else {
            piece.getIcon().paintIcon(null, whiteCapturedGraphics, (whiteCapturedCount * 20), 0);
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
        whiteCapturedImage = new BufferedImage(200, 70, BufferedImage.TYPE_INT_ARGB);
        blackCapturedImage = new BufferedImage(200, 70, BufferedImage.TYPE_INT_ARGB);
        whiteCapturedGraphics = whiteCapturedImage.getGraphics();
        blackCapturedGraphics = blackCapturedImage.getGraphics();
        whiteCapturedCount = 0;
        blackCapturedCount = 0;
    }

    public void testfunc(int i) {
        //
    }

}

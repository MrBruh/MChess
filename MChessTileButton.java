/**
 * @author MrBruh
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;

/**
 * This class exists because of Java Swing shenanigans.
 * Basically a JButton that doesn't change colour when pressed
 */
public class MChessTileButton extends JButton { 
    private Color pressedBackgroundColor;

    /**
     * This is the constructor that is normally called, 
     */
    public MChessTileButton() {
        this(null);
    }

    /**
     * The JButton constructor normally requires a constructor with a
     * String parameter to set as the button text  
     * 
     * @param text The text that is to be displayed on the button
     */
    public MChessTileButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
    }

    /**
     * This is to override the drawing of the tiles so that they don't 
     * change colour when pressed 
     * 
     * @param g The graphics of the button that are to be editted
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    /**
     * This function returns the background colour of the button when pressed 
     * 
     * @return Returns the background colour of the button when pressed
     */
    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    /**
     * This function sets the background colour of the button when pressed 
     * 
     * @param pressedBackgroundColor The background colour of the button when pressed 
     */
    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}

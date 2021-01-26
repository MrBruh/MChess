/**
 * @author MrBruh
 */

import javax.swing.*;  

/** 
 * Main class to initialize the game
 */
public class Main {  

  public static void main(String[] args) {  
    JFrame f=new JFrame();//creating instance of JFrame  
              
    JButton b=new JButton("click");//creating instance of JButton  
    b.setBounds(130,100,100, 40);//x axis, y axis, width, height  
              
    f.add(b);//adding button in JFrame  
              
    f.setSize(800,600);//400 width and 500 height  
    f.setLayout(null);//using no layout managers  
    f.setVisible(true);//making the frame visible  
  }  
}  
package game;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Controls extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8129494051712857352L;
	public static final int WIDTH = 650;
	public static final int HEIGHT = 678;
	
	JFrame window;
	JPanel control;
	private BufferedImage b1;
	private BufferedImage b2;
	private BufferedImage b3;
	private BufferedImage b4;
	
	public Controls () {
		
		window = new JFrame();
		window.setTitle("Controls Bomberman");
		window.setBounds(0, 0, WIDTH, HEIGHT);
		window.setLocation(200, 50);
	    window.setResizable(false); 
	    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    window.getContentPane().setLayout(new CardLayout());
	    try {
			b1 = ImageIO.read(getClass().getClassLoader().getResource("resources/b1-d.png"));
			b2 = ImageIO.read(getClass().getClassLoader().getResource("resources/b2-d.png"));
			b3 = ImageIO.read(getClass().getClassLoader().getResource("resources/b3-d.png"));
			b4 = ImageIO.read(getClass().getClassLoader().getResource("resources/b4-d.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	   
	    //Creates and sets up the game panel where the game is played on
	   
	    this.setPreferredSize(new Dimension (WIDTH, HEIGHT));
	    this.setLayout(null);
	    window.add(this);
	    this.setBackground(Color.BLUE);
	    this.setVisible(true);
	    window.setVisible(true);
	}
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.setColor(Color.white);
        g.drawString("PLAYER 1:", 10 ,72 );
        g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
        g.drawImage(b1,40,5, this);
		g.drawString("UP: W", 10 ,92 );
		g.drawString("DOWN: S", 10, 112);
		g.drawString("LEFT: A", 10, 132);
		g.drawString("RIGHT: D", 10, 152);
		g.drawString("BOMB: LEFT SHIFT", 10, 172);
		
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.setColor(Color.black);
        g.drawString("PLAYER 2:", 400 ,400 );
        g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
        g.drawImage(b2,420,330, this);
		g.drawString("UP: UP ARROW", 400 ,420 );
		g.drawString("DOWN: DOWN ARROW", 400, 440);
		g.drawString("LEFT: LEFT ARROW", 400, 460);
		g.drawString("RIGHT: RIGHT ARROW", 400, 480);
		g.drawString("BOMB: CONTROL (CTRL)", 400, 500);
		
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.setColor(Color.green);
        g.drawString("PLAYER 3:", 400 ,72 );
        g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
        g.drawImage(b3,420,5, this);
		g.drawString("UP: I", 400 ,92 );
		g.drawString("DOWN: K", 400, 112);
		g.drawString("LEFT: J", 400, 132);
		g.drawString("RIGHT: L", 400, 152);
		g.drawString("BOMB: SPACE", 400, 172);
		
		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        g.setColor(Color.RED);
        g.drawString("PLAYER 4:", 10 ,400 );
        g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
        g.drawImage(b4,40,330, this);
		g.drawString("UP: NUMPAD 8", 10 ,420 );
		g.drawString("DOWN: NUMPAD 5", 10, 440);
		g.drawString("LEFT: NUMPAD 4", 10, 460);
		g.drawString("RIGHT: NUMPAD 6", 10, 480);
		g.drawString("BOMB: ENTER", 10, 500);
       
    }
}

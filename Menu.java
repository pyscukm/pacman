package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Menu extends JPanel {
	
	private static final long serialVersionUID = -8011492670069316909L;
	
	private static final int FRAME_RATE = 33;
	private static int mode = -1;
	
	public final int WIDTH = 650;
	public final int HEIGHT = 678;
	
	JButton newGame = new JButton("New Game");
	JButton controls = new JButton("Controls");
	static String[] maps = {"map1", "map2"};
	static JComboBox<String> mapChooser = new JComboBox<String>(maps);
	
	JFrame window ;
	JPanel menu;
	static JSpinner numOfPlayersSpinner = new JSpinner(new SpinnerNumberModel(2,2,4,1));
	private BufferedImage whiteB;
	private BufferedImage blackB;
	
	public static void main(String args[]){
		new Menu();
		
		while ( true ){
			try {
				Thread.sleep(FRAME_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if ( mode > -1 ){
				switch(mode){
					case 0 :
						Game g = new Game(Integer.parseInt(numOfPlayersSpinner.getValue().toString()), 
														   mapChooser.getSelectedItem().toString());
						g.load();
						g.start();
						break;
					case 1 :
						new Controls();
						break;
						
				}
				
				mode = -1;
			}
		}
	}
	
	public Menu()  {
		try {
			whiteB = ImageIO.read(getClass().getClassLoader().getResource("resources/b_menu.png"));
			blackB = ImageIO.read(getClass().getClassLoader().getResource("resources/b_menu_2.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 	window = new JFrame();
    	window.setVisible(true);
		window.setTitle("Main menu BOMBERMAN");
		window.setBounds(0, 0, WIDTH, HEIGHT);
		window.setLocation(200, 50);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    window.add(this);
	    
	    this.add(newGame);
	    this.add(controls);
	    this.setBackground(Color.BLUE);
	  
	    newGame.addActionListener(new MyActionListener());
	    controls.addActionListener(new MyActionListener());
	    
	    this.add(numOfPlayersSpinner);
	    numOfPlayersSpinner.setBackground(Color.blue);
	    numOfPlayersSpinner.setBounds(50, 50, 100, 100);
	    
	    numOfPlayersSpinner.setVisible(true);
	    numOfPlayersSpinner.addChangeListener(new ChangeListener(){
	    	@Override
			public void stateChanged(ChangeEvent e) {
				numOfPlayersSpinner.getValue();
			}
	    });
	    
	    this.add(mapChooser);
	    mapChooser.setBounds(310,190,100,20);
    }
	
	private class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == newGame){
		        mode = 0;
		    }
			else if(e.getSource() == controls) {
		        mode = 1;
		    }
		}
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        g.setColor(Color.RED);
		g.drawString("BOMBERMAN", 150, 150);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        g.setColor(Color.RED);
		g.drawString("Version: 1.0", 20 ,600 );
		g.drawString("Author: PYS0030", 400 ,600 );
		g.drawImage(blackB,25,200, this);
		g.drawImage(whiteB,300,190, this);
       
    }
    
    	
}



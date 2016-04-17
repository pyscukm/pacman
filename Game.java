package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Game extends Canvas implements KeyListener{
	private static final long serialVersionUID = 4746373613923743106L;
	
	public final int WIDTH = 650;
	public final int HEIGHT = 678;
	public final int FRAME_RATE = 33;	
	public final int SIZE = 50;
	public final int BOARD_SIZE = 11;
	
	JFrame window;
	JPanel game;
	BufferStrategy bs;
	
	IMovable[] players;
	int[][] playerPosition;
	
	List<IObject> objects;
	
	public int numOfPlayers;
	public boolean isPlaying = false;
	
	public String mapName = "map1";
	
	
	private Map<String, BufferedImage> map = new HashMap<String,BufferedImage>();
	
	private BufferedImage bombSprite;
	private BufferedImage flameSprite;
	private BufferedImage bombImpotenceSprite;
	private BufferedImage plusBombSprite;
	private BufferedImage plusFlameSprite;
	private BufferedImage imortalitySprite;
	
	
	public Game(int numberOfPlayers, String map){
		this.numOfPlayers = numberOfPlayers;
		this.mapName = map;
		
		window = new JFrame();
		window.setTitle("Hra Bomberman");
		window.setBounds(0, 0, WIDTH, HEIGHT);
		window.setLocation(200, 50);
	    window.setResizable(false); 
	    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
	    window.addKeyListener(this);
	    setBounds(0, 0, WIDTH, HEIGHT);
	    
	   
	    game = new JPanel();
	    game.setPreferredSize(new Dimension (WIDTH, HEIGHT));
	    game.setLayout(null);
	    game.add(this);
	    window.add(game);
	    game.setVisible(true);
	    window.setVisible(true);
	    
	    //double buffering
	    createBufferStrategy(2);
	    bs = getBufferStrategy();
	}
	
	
	public void load(){
		players = new Bomberman[numOfPlayers];
		
		switch(numOfPlayers){
			case 4 : players[3] = new Bomberman("b4");
			case 3 : players[2] = new Bomberman("b3");
			case 2 : players[1] = new Bomberman("b2");
			case 1 : players[0] = new Bomberman("b1");
		}
		
		playerPosition = new int[numOfPlayers][2];
		
		try {
			map.put(IObject.FLOOR ,ImageIO.read(getClass().getClassLoader().getResource("resources/floor.png")));
			map.put(Block.code ,ImageIO.read(getClass().getClassLoader().getResource("resources/block.png")) );
			map.put(Wall.code ,ImageIO.read(getClass().getClassLoader().getResource("resources/wall.png")) );
			
			bombSprite = ImageIO.read(getClass().getClassLoader().getResource("resources/bomb1.png"));
			flameSprite = ImageIO.read(getClass().getClassLoader().getResource("resources/flame.png"));
			bombImpotenceSprite = ImageIO.read(getClass().getClassLoader().getResource("resources/bomb_impotent.png"));
			plusBombSprite = ImageIO.read(getClass().getClassLoader().getResource("resources/bomb_add.png"));
			plusFlameSprite = ImageIO.read(getClass().getClassLoader().getResource("resources/flame_add.png"));
			imortalitySprite = ImageIO.read(getClass().getClassLoader().getResource("resources/imortal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
		loadMap();
		loadPlayers();
		
		isPlaying = true;
		
		play();
	}
	
	
	
	private void play(){
		int second = 0;
		
		while ( isPlaying ){
			paintGame();
			window.requestFocus();
			
			checkBombColision();
			
			try { Thread.sleep (FRAME_RATE);}
	        catch (InterruptedException e){}
			
			second++;
			
			if ( second == 15 ){
				second = 0;
				
				List<IObject> tmp = new ArrayList<IObject>();
				tmp.addAll(objects);
				
				for ( IObject o : tmp ){
					if ( !o.isVisible() ){
						objects.remove(o);
					}
				}
			}
			
			if( !window.isVisible() || checkForWinner() ){
	            isPlaying = false;
	        }
		}
		
		paintGame();
	}
	
	private boolean checkForWinner(){
		int numOfAlivePlayers = 0;
		
		for ( IMovable p : players ){
			if ( p.isAlive() ){
				numOfAlivePlayers++;
			}
		}
		
		if ( numOfAlivePlayers <= 1 ){
			return true;
		}
		
		return false;
	}
	
	private void paintGame(){
		Graphics g = null;
		
		try{
			g = bs.getDrawGraphics();
		}catch(Exception e){}
	    
		
		if ( g != null ){
			paintFloor(g);
			paintBombs(g);
			paintFlames(g);
			paintBonus(g);
			paintWalls(g);
			paintPlayers(g);
			
			paintWinner(g);
			
			bs.show();
		}
	}
	



	private void paintBombs(Graphics g){
		for ( IMovable player : players ){
			for ( IExplode bomb : player.getBombs() ){
				if ( bomb.isVisible() ){
					g.drawImage(bombSprite, bomb.getX()*SIZE, bomb.getY()*SIZE, this);
				}
			}
		}
	}
	
	private void paintBonus(Graphics g){
		for ( IObject o : objects ){
			if ( o instanceof ICollectable ){
				if ( o.isVisible() ){
					if ( o instanceof BAddBomb ){
						g.drawImage(plusBombSprite, o.getX()*SIZE, o.getY()*SIZE, this);
					}
					else if ( o instanceof BAddFlame ){
						g.drawImage(plusFlameSprite, o.getX()*SIZE, o.getY()*SIZE, this);
					}
					else if ( o instanceof BImortality ){
						g.drawImage(imortalitySprite, o.getX()*SIZE, o.getY()*SIZE, this);
					}
					else if ( o instanceof CImpotence ){
						g.drawImage(bombImpotenceSprite, o.getX()*SIZE, o.getY()*SIZE, this);
					}
				}
			}
		}
	}
	
	private void paintFlames(Graphics g){
		for ( IObject o : objects ){
			if ( o instanceof Flame ){
				if ( o.isVisible() ){
					g.drawImage(flameSprite, o.getX()*SIZE, o.getY()*SIZE, this);
				}
			}
		}
	}
	
	private void paintWalls(Graphics g){
		for ( IObject obj : objects ){
			if ( obj.isVisible() && !(obj instanceof ICollectable) && !(obj instanceof Flame) ){
				g.drawImage(map.get(obj.getCode()),obj.getX() * SIZE ,obj.getY() * SIZE ,this);
			}
		}
	}
	
	private void paintPlayers(Graphics g) {
		for ( IMovable b : players){
			if ( b.isAlive() ){
				Bomberman bomberman = (Bomberman) b;
				g.drawImage(bomberman.getImage(), bomberman.getX()*SIZE, bomberman.getY()*SIZE, this);
				bomberman.setDirection(-1);
			}
		}
	}
	
	private void paintWinner(Graphics g){
		int numOfAlivePlayers = 0;
		String alivePlayer = null;
		
		for ( IMovable p : players ){
			if ( p.isAlive() ){
				numOfAlivePlayers++;
				alivePlayer = p.getName();
			}
		}
		
		g.setFont(new Font("Impact", Font.BOLD, 50));
		g.setColor(Color.RED);
		
		if ( numOfAlivePlayers == 1 ){
			g.drawString(alivePlayer + " WINS !", 100, 300);
		}
		else if ( numOfAlivePlayers == 0 ){
			g.drawString("IT'S A DRAW !", 180, 300);
		}
	}
	
	private void checkColision(){
		List<IObject> tmp = new ArrayList<IObject>();
		tmp.addAll(objects);
		
		//Projedeme všechny hrace
		for ( IMovable player : players ){
			if ( player.isAlive() ){
				for ( IObject obj : tmp ){
					if ( (obj instanceof Flame) ) {
						if ( obj.isVisible() && player.getX() == obj.getX() && player.getY() == obj.getY() && !player.isImortal() ){
							player.lifeDown();
						}
						else {
							continue;
						}
					}
					
					if ( !(obj instanceof ICollectable) ){
						if ( player.getX() == obj.getX() && player.getY() == obj.getY() ){
							player.cancelMove();
							break;
						}
					}
					else {
						if ( player.getX() == obj.getX() && player.getY() == obj.getY() && obj.isVisible() ){
							addBonusToPlayer((Bomberman)player, (ICollectable)obj);
							((ICollectable)obj).dismiss();
						}
					}
				}
				
				for ( IMovable p : players ){
					for ( IExplode b : p.getBombs() ){
						if ( b.isVisible() ){
							if ( player.getX() == b.getX() && player.getY() == b.getY() ){
								player.cancelMove();
								break;
							}
						}
					}
				}
			}
		}
	}
	
	private void addBonusToPlayer(Bomberman player, ICollectable bonus){
		if ( bonus instanceof BAddBomb ){
			player.addBombNumber();
		}
		else if ( bonus instanceof BAddFlame ){
			player.addFlameRange();
		}
		else if ( bonus instanceof BImortality ){
			player.setImortal(true);
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					try{
						Thread.sleep(BImortality.EFFECT_TIME_MS);
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						player.setImortal(false);
					}
				}
			}).start();
		}
		else if ( bonus instanceof CImpotence ){
			player.setBombImpotence(true);
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					try{
						Thread.sleep(CImpotence.EFFECT_TIME_MS);
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						player.setBombImpotence(false);
					}
				}
			}).start();
		}
	}
	
	private void checkBombColision(){
		for ( IMovable player : players ){
			List<IExplode> tmpBomb = new ArrayList<IExplode>();
			tmpBomb.addAll(player.getBombs());
			
			for ( IExplode bomb : tmpBomb ){
				if ( !bomb.isVisible() ){
					
					//Do tohoto ukladame vsechny zasazene objekty
					List<IObject> tmpObject = new ArrayList<IObject>();
					//Zasazene playery
					List<IMovable> tmpPlayer = new ArrayList<IMovable>();
					
					//Do toho se ulozi jestli byl zasazen nejaky objekt po ceste nahoru
					boolean objectUp = false;
					//po ceste doprava
					boolean objectRight = false;
					boolean objectDown = false;
					boolean objectLeft = false;
					
					boolean playerUp = false;
					boolean playerRight = false;
					boolean playerDown = false;
					boolean playerLeft = false;
					
					for ( int i=0 ; i <= bomb.getBombRange() ; i++ ){
						int plusX = bomb.getX()+i;
						int minusX = bomb.getX()-i;
						int plusY = bomb.getY()+i;
						int minusY = bomb.getY()-i;
						
						for ( IObject o : objects ){
							// Pokud objekt je na stejne X souradnici a na stejne Y souradnici (po ceste nahoru) jako "plamen"
							if ( o.getX() == bomb.getX() && o.getY() == minusY ){
								//A pokud jsme zatim na nic nenarazili
								if ( !objectUp && !playerUp ){
									//tak na neco naraz
									objectUp = true;
									//A pridej objekt do trefenych objektu
									tmpObject.add(o);
								}
							}
							// Doprava
							else if ( o.getX() == plusX && o.getY() == bomb.getY() ){
								if ( !objectRight && !playerRight ){
									objectRight = true;
									tmpObject.add(o);
								}
							}
							// Dolu
							else if ( o.getX() == bomb.getX() && o.getY() == plusY ){
								if ( !objectDown && !playerDown  ){
									objectDown = true;
									tmpObject.add(o);
								}
							}
							// Doleva
							else if ( o.getX() == minusX && o.getY() == bomb.getY() ){
								if ( !objectLeft && !playerLeft ){
									objectLeft = true;
									tmpObject.add(o);
								}
							}
						}
						
						for ( IMovable p : players ){
							if ( p.getX() == bomb.getX() && p.getY() == minusY ){
								if ( !playerUp && !objectUp ){
									//Plamen musi pokracovat, pokud player STOJI primo na bombe
									if ( i > 0 ) playerUp = true;
									tmpPlayer.add(p);
								}
							}
							else if ( p.getX() == plusX && p.getY() == bomb.getY() ){
								if ( !playerRight && !objectRight ){
									if ( i > 0 ) playerRight = true;
									tmpPlayer.add(p);
								}
							}
							else if ( p.getX() == bomb.getX() && p.getY() == plusY ){
								if ( !playerDown && !objectDown ){
									if ( i > 0 ) playerDown = true;
									tmpPlayer.add(p);
								}
							}
							else if ( p.getX() == minusX && p.getY() == bomb.getY() ){
								if ( !playerLeft && !objectLeft ){
									if ( i > 0 ) playerLeft = true;
									tmpPlayer.add(p);
								}
							}
						}
						
						if ( minusY > 0 && (!objectUp && !playerUp)  ) {
							Flame up = new Flame(bomb.getX(), minusY);
							objects.add(up);
						}
						if ( plusX < BOARD_SIZE && (!objectRight && !playerRight) ) {
							Flame right = new Flame(bomb.getX()+i, bomb.getY());
							objects.add(right);
						}
						if ( plusY < BOARD_SIZE && (!objectDown && !playerDown) ) {
							Flame down = new Flame(bomb.getX(), bomb.getY()+i);
							objects.add(down);
						}
						if ( minusX > 0 && (!objectLeft && !playerLeft)  ) {
							Flame left = new Flame(bomb.getX()-i, bomb.getY());
							objects.add(left);
						}
					}
					
					//Kontrola zasazenych objektu
					for ( IObject o : tmpObject ){
						boolean wasRemoved = false;
						if ( o instanceof Wall || o instanceof ICollectable){
							objects.remove(o);
							wasRemoved = true;
						}
						
						
						/**
						 * pokud byla zed odstranena, vytvorime bonus.
						 * 
						 */
						if ( wasRemoved && o instanceof Wall ){
							ICollectable bonus = makeRandomBonusOrCurse(o.getX(), o.getY());
							
							if ( bonus != null ){
								objects.add(bonus);
							}
						}
					}
					
					
					//kontrola zasazenych playeru
					for ( IMovable p : tmpPlayer ){
						if ( p.isAlive() && !p.isImortal() ){
							p.lifeDown();
						}
					}
					
					//Oddelat bombu z playera protoze buchla
					player.getBombs().remove(bomb);
				}
			}
		}
	}

	private ICollectable makeRandomBonusOrCurse(int x, int y) {
		double d = Math.random();
		
		// 50% sance ze to bude bonus
		if ( d < 0.5 ){
			double c = Math.random();
			
			if ( c < 0.5 ){
				//50% ze to bude pridani bomby
				BAddBomb addBomb = new BAddBomb(x,y);
				//System.out.println("BONUS: add bomb");
				return addBomb;
			}
			else if ( c < 0.8 ){
				//30% ze to bude pridani plamenu
				BAddFlame addFlame = new BAddFlame(x,y);
				//System.out.println("BONUS: add flame");
				return addFlame;
			}
			else {
				//20% ze to bude nesmrtelnost
				BImortality imortality = new BImortality(x,y);
				//System.out.println("BONUS: imortality");
				return imortality;
			}
		}
		// 50% sance ze to bude znevyhodneni
		else {
			double c = Math.random();
		//50% z 50% ze to bude impotence bomb
			if ( c < 0.25 ){
				CImpotence impotence = new CImpotence(x,y);
				//System.out.println("CURSE: IMPOTENCE");
				return impotence;
			}
		}
		
		return null;
	}

	private void paintFloor(Graphics g){
		for(int i = 0; i < BOARD_SIZE; i++)
	    {
	        for(int j = 0; j < BOARD_SIZE; j++)
	        {
	        	g.drawImage(map.get(IObject.FLOOR),i * SIZE + SIZE  ,j * SIZE + SIZE ,this);
	        }
	    }
	}
	
	private void loadPlayers(){
		for ( int i=0 ; i<players.length ; i++ ){
			Bomberman bomberman = (Bomberman) players[i];
			bomberman.initBomber("Bomberman " + (i+1) , playerPosition[i][0]+1, playerPosition[i][1]+1);
		}
	}
	
	private void loadMap(){
		try{
			FileReader fr = new FileReader("src/resources/"+mapName+".txt");
			BufferedReader br = new BufferedReader(fr);
	        
	        objects = new ArrayList<IObject>();
	        
	        for(int i = 0; i < BOARD_SIZE; i++)
	        {
	          String row = br.readLine();
	          
	          for(int j = 0; j < row.length(); j++)
	          {
	        	  String letter = ""+row.charAt(j);
	        	  
	        	  if ( letter.equals("1") ){
	        		  playerPosition[0][0] = j;
	        		  playerPosition[0][1] = i;
	        	  }
	        	  else if ( letter.equals("2") ){
	        		  if ( playerPosition.length >= 2 ){
		        		  playerPosition[1][0] = j;
		        		  playerPosition[1][1] = i;
	        		  }
	        	  }
	        	  else if ( letter.equals("3") ){
	        		  if ( playerPosition.length >= 3 ){
		        		  playerPosition[2][0] = j;
		        		  playerPosition[2][1] = i;
	        		  }
	        	  }
	        	  else if ( letter.equals("4") ){
	        		  if ( playerPosition.length >= 4 ){
		        		  playerPosition[3][0] = j;
		        		  playerPosition[3][1] = i;
	        		  }
	        	  }
	        	  
	        	  if ( letter.equals(Wall.code) ){
	        		  objects.add(new Wall(j+1,i+1));
	        	  }
	        	  else if ( letter.equals(Block.code) ){
	        		  objects.add(new Block(j+1,i+1));
	        	  }
	          }
	        }
	        
	        br.close();
		}catch(Exception e){
			System.out.println("Mapa nebyla nalezena");
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int[] move = new int[]{-1,-1,-1,-1};
		
		switch( e.getKeyCode() ){
			case KeyEvent.VK_W:
				move[0] = Bomberman.UP;
				break;
				case KeyEvent.VK_UP:
				move[1] = Bomberman.UP;
				break;
			case KeyEvent.VK_I:
				move[2] = Bomberman.UP;
				break;
			case KeyEvent.VK_NUMPAD8:
				move[3] = Bomberman.UP;
				break;
				
			case KeyEvent.VK_S:
				move[0] = Bomberman.DOWN;
				break;
			case KeyEvent.VK_DOWN:
				move[1] = Bomberman.DOWN;
				break;
			case KeyEvent.VK_K:
				move[2] = Bomberman.DOWN;
				break;
			case KeyEvent.VK_NUMPAD5:
				move[3] = Bomberman.DOWN;
				break;
				
			case KeyEvent.VK_A:
				move[0] = Bomberman.LEFT;
				break;
			case KeyEvent.VK_LEFT:	
				move[1] = Bomberman.LEFT;
				break;
			case KeyEvent.VK_J:
				move[2] = Bomberman.LEFT;
				break;
			case KeyEvent.VK_NUMPAD4:	
				move[3] = Bomberman.LEFT;
				break;
				
			case KeyEvent.VK_D:
				move[0] = Bomberman.RIGHT;
				break;
			case KeyEvent.VK_RIGHT:	
				move[1] = Bomberman.RIGHT;
				break;
			case KeyEvent.VK_L:
				move[2] = Bomberman.RIGHT;
				break;
			case KeyEvent.VK_NUMPAD6:	
				move[3] = Bomberman.RIGHT;
				break;
				
			case KeyEvent.VK_SHIFT:
				move[0] = Bomberman.BOMB;
				break;
			case KeyEvent.VK_CONTROL:	
				move[1] = Bomberman.BOMB;
				break;	
			case KeyEvent.VK_SPACE:
				move[2] = Bomberman.BOMB;
				break;
			case KeyEvent.VK_ENTER:	
				move[3] = Bomberman.BOMB;
				break;	
		}
		
		switch ( numOfPlayers ){
			case 4 :
				players[3].doMove(move[3]);
			case 3 :
				players[2].doMove(move[2]);
			case 2 :
				players[1].doMove(move[1]);
			case 1 :
				players[0].doMove(move[0]);
				break;
		}
		
		checkColision();
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}

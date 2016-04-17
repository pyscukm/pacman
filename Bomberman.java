package game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Bomberman implements IMovable {
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int BOMB = 4;

	private BufferedImage currentImage;
	private BufferedImage[] bomberImage = new BufferedImage[4];
		
	private List<Bomb> bombs = new ArrayList<Bomb>();
	
	private String name;
	private int health;
	private int speed;
	private int bombRange;
	private int bombNumber;
	private boolean bombImpotence;
	private boolean alive;
	private boolean imortal;
	private int xPos;
	private int yPos;
	
	private int direction = -1;
	
	public Bomberman(String type){
		try {
			bomberImage[DOWN] = ImageIO.read(getClass().getClassLoader().getResource("resources/" + type + "-d.png"));
			bomberImage[UP] = ImageIO.read(getClass().getClassLoader().getResource("resources/" + type + "-u.png"));
			bomberImage[LEFT] = ImageIO.read(getClass().getClassLoader().getResource("resources/" + type + "-l.png"));
			bomberImage[RIGHT] = ImageIO.read(getClass().getClassLoader().getResource("resources/" + type + "-r.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initBomber(String name, int xPos, int yPos){
		this.name = name;
		this.alive = true;
		this.bombImpotence = false;
		this.bombNumber = 1;
		this.bombRange = 1;
		this.speed = 1;
		this.health = 1;
		
		this.xPos = xPos;
		this.yPos = yPos;
		
		setImage(bomberImage[DOWN]);
	}
	
	public void addBombNumber(){
		this.bombNumber++;
	}
	
	public void addFlameRange(){
		this.bombRange++;
	}
	

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		setAlive(health > 0);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getBombRange() {
		return bombRange;
	}

	public void setBombRange(int bombRange) {
		this.bombRange = bombRange;
	}

	public int getBombNumber() {
		return bombNumber;
	}

	public void setBombNumber(int bombNumber) {
		this.bombNumber = bombNumber;
	}

	public boolean isBombImpotence() {
		return bombImpotence;
	}

	public void setBombImpotence(boolean bombImpotence) {
		this.bombImpotence = bombImpotence;
	}

	@Override
	public int getX() {
		return xPos;
	}

	public void setX(int xPos) {
		this.xPos = xPos;
	}

	@Override
	public int getY() {
		return yPos;
	}

	public void setY(int yPos) {
		this.yPos = yPos;
	}

	@Override
	public boolean isAlive() {
		return this.alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	@Override
	public void moveDown(int speed) {
		this.yPos += speed;
		
		if ( speed > 0 ) {
			direction = DOWN;
			setImage(bomberImage[DOWN]);
		}
		else {
			direction = UP;
			setImage(bomberImage[UP]);
		}
	}

	@Override
	public void moveRight(int speed) {
		this.xPos += speed;
		
		if ( speed > 0  ) {
			direction = RIGHT;
			setImage(bomberImage[RIGHT]);
		}
		else {
			direction = LEFT;
			setImage(bomberImage[LEFT]);
		}
	}

	@Override
	public void doMove(int KEY_EVENT) {
		if ( isAlive() ){
			
			switch( KEY_EVENT ){
				case UP:
					moveDown(-this.speed);
					break;
				case DOWN:
					moveDown(this.speed);
					break;
				case LEFT:
					moveRight(-this.speed);
					break;
				case RIGHT:
					moveRight(this.speed);
					break;
				case BOMB:
					bomb();
					break;	
			}
		}
	}
	

	private void bomb() {
		if ( !bombImpotence && (bombs.size() < this.bombNumber) ){
			bombs.add(new Bomb(this));
		}
	}

	@Override
	public void cancelMove() {
		if ( direction >= 0  ){
			switch( direction ){
				case UP:
					this.yPos += this.speed;
					break;
				case DOWN:
					this.yPos -= this.speed;
					break;
				case LEFT:
					this.xPos += this.speed;
					break;
				case RIGHT:
					this.xPos -= this.speed;
					break;
			}
			
			direction = -1;
		}
	}

	public BufferedImage getImage() {
		return currentImage;
	}

	public void setImage(BufferedImage currentImage) {
		this.currentImage = currentImage;
	}
	
	@Override
	public List<Bomb> getBombs(){
		return this.bombs;
	}
	
	public void setBombs(List<Bomb> b){
		 this.bombs = b;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public void lifeDown() {
		this.health--;
		if ( health == 0 ){
			this.alive = false;
		}
	}

	@Override
	public boolean isImortal() {
		return imortal;
	}

	public void setImortal(boolean imortal) {
		this.imortal = imortal;
	}


}

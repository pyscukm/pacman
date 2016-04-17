package game;

import java.util.List;

public interface IMovable {
	public void moveDown(int speed);
	public void moveRight(int speed);
	public void doMove(int KEY_EVENT);
	public int getX();
	public int getY();
	public String getName();
	public boolean isAlive();
	public boolean isImortal();
	public void cancelMove();
	public List<Bomb> getBombs();
	public void lifeDown();
	public int getHealth();
}

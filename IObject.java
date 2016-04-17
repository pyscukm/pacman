package game;

public interface IObject {
	public boolean isVisible();
	public int getX();
	public int getY();
	public String getCode();
	public static final String FLOOR = "_";
}

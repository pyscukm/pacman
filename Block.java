package game;

public class Block implements IObject {

	public static final String code = "X";
	private final boolean visible = true;
	
	private int posX;
	private int posY;
	
	public Block(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	
	@Override
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public int getX() {
		return this.posX;
	}

	@Override
	public int getY() {
		return this.posY;
	}

	public String getCode() {
		return code;
	}

}

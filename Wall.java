package game;

public class Wall implements IObject {

	public static final String code = "Z";
	private boolean visible;
	
	private int posX;
	private int posY;
	
	public Wall(int x, int y){
		this.visible = true;
		this.posY = y;
		this.posX = x;
	}
	
	public void remove(){
		this.visible = false;
	}
	
	@Override
	public boolean isVisible() {
		return this.visible;
	}

	
	public String getCode() {
		return code;
	}

	@Override
	public int getX() {
		return this.posX;
	}

	@Override
	public int getY() {
		return this.posY;
	}

}

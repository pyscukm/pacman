package game;

public class Flame implements IObject {
	private final int FLAME_TIME_MS = 500;
	
	private int x;
	private int y;
	private boolean visible;
	
	public Flame(int x, int y){
		this.x = x;
		this.y = y;
		this.visible = true;
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(FLAME_TIME_MS);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					visible = false;
				}
			}
		}).start();
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public String getCode() {
		return null;
	}
}

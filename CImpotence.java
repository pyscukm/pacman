package game;

public class CImpotence implements ICollectable {

	public static final int EFFECT_TIME_MS = 5000;

	private final int ALIVE_TIME_MS = 5000;
	
	private int x;
	private int y;
	private boolean alive;
	private boolean visible;
	
	public CImpotence(int x, int y){
		this.x = x;
		this.y = y;
		this.alive = true;
		this.visible = true;
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(ALIVE_TIME_MS);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					dismiss();
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

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void dismiss() {
		alive = false;
		visible = false;
	}

}

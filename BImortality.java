package game;

public class BImortality implements ICollectable {

	public static final int EFFECT_TIME_MS = 10000;
	
	private final int ALIVE_TIME_MS = 5000;
	private int x;
	private int y;
	private boolean visible;
	private boolean alive;
	
	
	public BImortality(int x, int y){
		this.x = x;
		this.y = y;
		this.alive = true;
		this.visible = true;
		
		//spustíme vlakno ktery bude spat 5 sekund a potom bonus "zabije"
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
	
	
	
	public void setX(int x){
		this.x = x;
	}
	
	@Override
	public int getX() {
		return x;
	}

	public void setY(int y){
		this.y = y;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	public void setAlive(boolean alive){
		this.alive = alive;
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}


	@Override
	public String getCode() {
		return null;
	}



	@Override
	public void dismiss() {
		this.alive = false;
		this.visible = false;
	}

}

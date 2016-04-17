package game;

public class BAddBomb implements ICollectable {

	private final int ALIVE_TIME_MS = 10000;
	
	private int x;
	private int y;
	private boolean visible;
	private boolean alive;
	
	public BAddBomb(int x, int y){
		this.x = x;
		this.y = y;
		this.alive = true;
		this.visible = true;
		
		//spustíme vlakno ktery bude spat 10 sekund a potom bonus "zabije"
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
	
	public void setVisible(boolean visible){
		this.visible = visible;
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
		this.alive = false;
		this.visible = false;
	}

}

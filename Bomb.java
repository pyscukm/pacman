package game;

public class Bomb implements IExplode {

	private int x;
	private int y;
	private int bombRange;
	private boolean visible;
	
	private int timeMs = 3000;
	
	public Bomb(Bomberman b){
		this.x = b.getX();
		this.y = b.getY();
		this.visible = true;
		this.bombRange = b.getBombRange();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				try{
					Thread.sleep(timeMs);
					visible = false;
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}).start();;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}


	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public int timer() {
		return this.timeMs;
	}


	@Override
	public String getCode() {
		return null;
	}

	@Override
	public int getBombRange() {
		return this.bombRange;
	}

	public void setBombRange(int bombRange) {
		this.bombRange = bombRange;
	}
}

import java.awt.Color;


public class Constants {

	/* Change this to make the screen larger */
	public static int HEIGHT = 300; 
	public static int WIDTH = 300;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int SCALE = 10;
	public static final int REFRESH_TIME = 20;
	
	/* Game states */
	public static final int GAME_ON = 0;
	public static final int GAME_OVER = 1;
	public static final int GAME_PAUSE = 2;
	
	/* Change any colours */
	public static final Color BACKGROUND_COLOUR = new Color(4422679);
	public static final Color SNAKE_COLOUR = Color.BLUE;
	public static final Color CHERRY_COLOUR = Color.RED;
	public static final Color SCORE_COLOUR = Color.ORANGE;
	
}

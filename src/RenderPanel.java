import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;


public class RenderPanel extends JPanel {
	private int miScore;
	private int miCurrentPanel;

	private Point mPoint;

	private NibblesMain mSnake;

	
	public RenderPanel(NibblesMain snake) {
		mSnake = snake;
		miCurrentPanel = Constants.GAME_ON;
	} 

	
	/* @Override - JPanel */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(miCurrentPanel == Constants.GAME_ON) {
			// Show nibbles view
			
			g.setColor(Constants.BACKGROUND_COLOUR);
			g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

			// Fill the body of the snake with colour
			for(Point point : mSnake.malSnakeParts) {
				g.setColor(Constants.SNAKE_COLOUR);
				g.fillRect(point.x * Constants.SCALE, point.y * Constants.SCALE, Constants.SCALE, Constants.SCALE);
			}

			// Fill colour for the cherry
			g.setColor(Constants.CHERRY_COLOUR);
			g.fillRect(mSnake.pCherry.x * Constants.SCALE, mSnake.pCherry.y * Constants.SCALE, Constants.SCALE, Constants.SCALE);

			// Show score
			g.setColor(Constants.SCORE_COLOUR);
			g.drawString("Score: "+miScore, Constants.WIDTH - 90, 20);
			
		} else {
			//Show Game over view
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
			g.setColor(Color.WHITE);
			g.drawString("Game over!", 40, 40);
			g.drawString("Press space to continue", 40 , 60);
		}
	}


	public void repaintScore(int score) {
		miScore = score;
		this.repaint();
	}

	/* Switches between a game over view and the Nibbles view */
	public void showPanel(int i) {
		miCurrentPanel = i;
		this.repaint();
	}
}

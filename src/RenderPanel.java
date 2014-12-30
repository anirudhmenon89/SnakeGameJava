import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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

		if(miCurrentPanel == Constants.GAME_PAUSE) {
			// Show game pause view
			
			Graphics2D g2d = (Graphics2D) g;

			// Set a semi-transparent black background
			g2d.setColor(Color.BLACK);
			setAlpha(g2d, 0.5f);
			g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

			// Set white coloured text which on opaque background
			g.setColor(Color.WHITE);
			setAlpha(g2d, 1.0f);
			g.drawString("Game Paused!", 40, 40);
			g.drawString("Press space to resume", 40 , 60);
		} 
		
		if(miCurrentPanel == Constants.GAME_OVER) {
			//Show Game over view
			
			Graphics2D g2d = (Graphics2D) g;
			
			// Set a semi-transparent black background
			setAlpha(g2d, 0.5f);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
			
			// Set white coloured text which on opaque background
			setAlpha(g2d, 1.0f);
			g.setColor(Color.WHITE);
			g.drawString("Game over!", 40, 40);
			g.drawString("Press space to restart", 40 , 60);
		}
	}


	private void setAlpha(Graphics2D g2d, float alpha) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
	}


	public void repaintScore(int score) {
		miScore = score;
		this.repaint();
	}

	/* Switches between different game states */
	public void showPanel(int i) {
		miCurrentPanel = i;
		this.repaint();
	}
}

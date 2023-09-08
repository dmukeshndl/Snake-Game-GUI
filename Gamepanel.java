package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;



public class Gamepanel extends JPanel implements ActionListener{

	
	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH=600;
	static final int SCRREN_HEIGHT=600;
	static final int UNIT_SIZE=25;
	static final int GAME_UNITS=((SCREEN_WIDTH*SCRREN_HEIGHT)/UNIT_SIZE);
	static final int DEALY=75;
	final int x[]=new int[GAME_UNITS];
	final int y[]=new int[GAME_UNITS];
	int bodyParts=6;
	int appleEateen;
	int applex;
	int appley;
	char direction='R';
	boolean running=false;
	Timer timer;
	Random random;
	
	Gamepanel()
	{
		random =new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCRREN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new mykeyAdapter());
		startGame();
	}
	public void startGame()
	{
		newApple();
		running=true;
		timer=new Timer(DEALY,this);
		timer.start();
		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g)
	{
		if(running)
		{
			for(int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++)
			{
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCRREN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			g.setColor(Color.red);
			g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);
			for(int i=0;i<bodyParts;i++)
			{
				if(i==0)
				{
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else
				{
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.RED);
			g.setFont(new Font("Ink free",Font.BOLD,40));
			FontMetrics metrics=getFontMetrics(g.getFont());
			g.drawString("Score: "+appleEateen, (SCREEN_WIDTH-metrics.stringWidth("Score: "+appleEateen))/2, g.getFont().getSize());

		}
		else
		{
			gameOver(g);
		}
	}
	public void newApple()
	{
		applex=random.nextInt((int)( SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appley=random.nextInt((int)( SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void move()
	{
		for(int i=bodyParts;i>0;i--)
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction)
		{
		case'U':
			y[0]=y[0]-UNIT_SIZE;
			break;
		case'D':
			y[0]=y[0]+UNIT_SIZE;
			break;
		case'L':
			x[0]=x[0]-UNIT_SIZE;
			break;
		case'R':
			x[0]=x[0]+UNIT_SIZE;
			break;
		}
	}
	public void checkApple()
	{
		if((x[0]==applex)&&(y[0]==appley))
		{
			bodyParts++;
			appleEateen++;
			newApple();
		}
	}
	public void checkCollisions()
	{
		//check if head collides with body
		for(int i=bodyParts;i>0;i--)
		{
			
			if((x[0]==x[i] && y[0]==y[i]))
			{
				running=false;
			}
		}
		//check if head collodes with body
		if(x[0]<0)
		{
			running=false;
		}
		//check if hesd touches to right
		if(x[0]>SCREEN_WIDTH)
		{
			running=false;
		}
		//check if hesd touches to top
		if(y[0]<0)
		{
			running=false;
		}
		//check if head touches to bottom
		if(y[0]>SCRREN_HEIGHT)
		{
			running=false;
		}
		if(!running)
		{
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g)
	{
		//dislay score
		g.setColor(Color.RED);
		g.setFont(new Font("Ink free",Font.BOLD,40));
		FontMetrics metrics1=getFontMetrics(g.getFont());
		g.drawString("Score: "+appleEateen, (SCREEN_WIDTH-metrics1.stringWidth("Score: "+appleEateen))/2, g.getFont().getSize());
		//setup game over text
		g.setColor(Color.RED);
		g.setFont(new Font("Ink free",Font.BOLD,75));
		FontMetrics metrics2=getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2, SCRREN_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running)
		{
			move();
			checkApple();
			checkCollisions();
			
		}
		repaint();
		
	}
	public class  mykeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed (KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				if(direction!='R')
				{
					direction='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction!='L')
				{
					direction='R';
				}
				break;
	
			case KeyEvent.VK_UP:
				if(direction!='D')
				{
					direction='U';
				}
				break;
			
			case KeyEvent.VK_DOWN:
				if(direction!='U')
				{
					direction='D';
				}
				break;
			
			}
		}
		
		
		
	}
	{
		
	}

}

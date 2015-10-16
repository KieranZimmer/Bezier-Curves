package bezierCurves;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;


public class ControlPanel extends JPanel implements Runnable,KeyListener,MouseListener,MouseMotionListener{
	private MainPanel main;
	private static int pWidth = 1920, pHeight = 1080;
	private boolean running;
	private Thread thread;
	private Graphics2D graphics;
	private Image image;
	private BufferedImage bgImage;
	private Point[] points, drawPoints;
	private int degree, curPoint;
	private boolean shift,ctrl,updated;
	
	public ControlPanel(MainPanel main) {
		this.setDoubleBuffered(false);
		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(pWidth, pHeight));
		this.setFocusable(true);
		this.requestFocus();
		this.main = main;
    addKeyListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
	}
	
	public static void main(String[] args) {
		new MainPanel();
	}
	
	public void addNotify() {
		super.addNotify();
		startGame();
	}
	
	public void stopGame() {
		running = false;
	}
	
	public void startGame() {
		if (thread == null || !running) {
			thread = new Thread(this);
		}
		thread.start();
	}
	
	public int min(int n,int m) {
	  return n > m ? m : n;
	}
	
	public void bezierStart() {
	  for (int i = 0;i <= 100;i++) {
	    bezier(points,i);
	  }
	}
	
	public void bezier(Point[] arr,int t) {
	  if (arr.length == 1) {
	    drawPoints[t] = arr[0];
	    //System.out.println(t);
	  }
	  else {
	    Point[] brr = new Point[arr.length - 1];
	    for (int i = 0;i < brr.length;i++) { 
	      //System.out.println(i);
	      brr[i] = new Point(arr[i].x + (arr[i + 1].x - arr[i].x) * t / 100, arr[i].y + (arr[i + 1].y - arr[i].y) * t / 100);
	    }
	    bezier(brr,t);
	  }
	}
	
	public void run() {
		running = true;
		
		init();
		
		while (running) {
			if (updated) update();
			
			drawGame();
	    
			draw();

			drawScreen();
		}
		
		System.exit(0);
	}
	
	public void init() {
	  degree = 2;
	  curPoint = 1;
	  shift = ctrl = false;
	  updated = true;
	  points = new Point[degree + 1];
	  for (int i = 0;i <= degree;i++) {
	    points[i] = new Point(1920 / (degree + 2) * (i + 1),540);
	  }
	  drawPoints = new Point[101];
	}
	
	public void update() {
	  bezierStart();
	  updated = false;
	}
	
	public void draw() {
    if (ctrl) {
      graphics.setColor(Color.red);
      for (int i = 0;i < degree;i++) graphics.draw(new Line2D.Double(points[i].x,points[i].y,points[i + 1].x,points[i + 1].y));
    }
    graphics.setColor(Color.black);
		for (int i = 0;i < 100;i++) {
		  graphics.draw(new Line2D.Double(drawPoints[i].x,drawPoints[i].y,drawPoints[i + 1].x,drawPoints[i + 1].y));
		}
    for (int i = 0;i <= degree;i++) {
      graphics.setColor(Color.black);
      graphics.fillOval((int)points[i].x - 2,(int)points[i].y - 2, 5, 5);
      graphics.setColor(Color.blue);
      graphics.drawString((i + 1) + " " + points[i].toString(),(int)points[i].x - 2,(int)points[i].y - 5);
    }
    graphics.setColor(Color.red);
    graphics.drawString("Degree: " + degree, 5, 12);
    graphics.drawString("Current Point: " + curPoint, 5, 24);
    if (shift) graphics.drawString("SHIFT", 5, 36);
    if (ctrl) graphics.drawString("CONTROL", 5, 48);
	}

	public void drawGame() {
		if (image == null) {
			image = createImage(pWidth, pHeight);
			
			if (image == null) {
				System.out.println("Cannot create buffer");
				return;
			}
			else
				graphics = (Graphics2D)image.getGraphics();
		}
		
		if (bgImage == null) {
			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, pWidth, pHeight);
		}
		else {
			graphics.drawImage(bgImage, 0, 0, null);
		}
	}
	
	public void drawScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			if (g != null && image != null) {
				g.drawImage(image, 0, 0, null);
				g.dispose();
			}
		}catch(Exception e) {System.out.println("Graphics objects error");}
	}

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = !shift;
    if (e.getKeyCode() == KeyEvent.VK_CONTROL) ctrl = !ctrl;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //System.out.println(e.getKeyChar());
    if (shift && e.getKeyChar() >= 50 && e.getKeyChar() <= 56) {
      degree = e.getKeyChar() - 48;
      /*Point[] temp = points.clone();
      points = new Point[degree + 1];
      for (int i = 0;i < min(temp.length,degree + 1);i++) {
        points[i] = temp[i];
      }*/
      points = new Point[degree + 1];
      for (int i = 0;i <= degree;i++) {
        points[i] = new Point(1920 / (degree + 2) * (i + 1),540);
      }
      curPoint = 1;
      updated = true;
    }
    else if (e.getKeyChar() >= 49 && e.getKeyChar() <= 49 + degree) {
      curPoint = e.getKeyChar() - 48;
    }
  }

  @Override
  public void mouseClicked(MouseEvent me) {

  }

  @Override
  public void mouseEntered(MouseEvent me) {

  }

  @Override
  public void mouseExited(MouseEvent me) {

  }

  @Override
  public void mousePressed(MouseEvent me) {
    points[curPoint - 1].move(me.getX(), me.getY());
    updated = true;
  }

  @Override
  public void mouseReleased(MouseEvent me) {

  }

  @Override
  public void mouseDragged(MouseEvent me) {
    points[curPoint - 1].move(me.getX(), me.getY());
    updated = true;
  }

  @Override
  public void mouseMoved(MouseEvent me) {

    
  }
}

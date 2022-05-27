/*
 * ==========================================================================================
 * AnimationViewer.java : Moves shapes around on the screen according to different paths.
 * It is the main drawing area where shapes are added and manipulated.
 * YOUR UPI:zzou832
 * ==========================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;


class AnimationViewer extends JComponent implements Runnable {
	private Thread animationThread = null;    // the thread for animation
    private static int DELAY = 120;         // the current animation speed
    ArrayList<Shape> shapes = new ArrayList<Shape>(); //create the ArrayList to store shapes
    private Painter painter = new GraphicsPainter();;
    private ShapeType currentShapeType=Shape.DEFAULT_SHAPETYPE; // the current shape type,
    private PathType currentPathType=Shape.DEFAULT_PATHTYPE;  // the current path type
    private Color currentColor=Shape.DEFAULT_COLOR; // the current fill colour of a shape
    private int marginWidth=Shape.DEFAULT_MARGIN_WIDTH, marginHeight = Shape.DEFAULT_MARGIN_HEIGHT, currentWidth=Shape.DEFAULT_WIDTH, currentHeight=Shape.DEFAULT_HEIGHT;
	private String currentText = Shape.DEFAULT_TEXT;
     /** Constructor of the AnimationViewer */
    public AnimationViewer(boolean isGraphicsVersion) {
		start();
		addMouseListener(new MyMouseAdapter());
    }
    /** create a new shape
     * @param x     the x-coordinate of the mouse position
     * @param y    the y-coordinate of the mouse position */
	protected void createNewShape(int x, int y) {
		int size = Math.min(currentWidth, currentHeight);
		switch (currentShapeType) {
			case RECTANGLE: {
				shapes.add( new RectangleShape(x, y,currentWidth,currentHeight,marginWidth,marginHeight,currentColor,currentPathType,currentText));
				break;
			} case SQUARE: {
				shapes.add( new SquareShape(x, y,size,marginWidth,marginHeight,currentColor,currentPathType,currentText));
				break;
			} case OVAL: {
				shapes.add( new OvalShape(x, y,currentWidth,currentHeight,marginWidth,marginHeight,currentColor,currentPathType,currentText));
				break;
			}
		}
	}
    /** returns the string representation of a object */
	public String toString() {
		StringBuilder sb = new StringBuilder("Shapes:\n");
		for (Shape s: shapes)
			sb.append(s.toString() + "\n");
		return sb.toString();
	}
	public void setCurrentText(String text) {
		currentText = text;
		for (Shape currentShape: shapes)
			if ( currentShape.isSelected())
				currentShape.setText(currentText);
	}
	/** get the current shape type
	 * @return currentShapeType - the shape type */
	public ShapeType getCurrentShapeType() { return currentShapeType; }
	/** set the current shape type
	 * @param st the new shape type */
    public void setCurrentShapeType(int st) {
		currentShapeType = ShapeType.getShapeType(st);
	}
	/** get the current path type
	 * @return currentPathType - the path type */
	public String getCurrentText() {
		return currentText;
	}
	public PathType getCurrentPathType() { return currentPathType; }
	/** set the current path type
	 * @param pt the new path type */
	public void setCurrentPathType(int pt) {
		currentPathType = PathType.getPathType(pt);
	}
	/** get the current width
	 * @return currentWidth - the width value */
	public int getCurrentWidth() { return currentWidth; }
	/** set the current width and the width for all currently selected shapes
	 * @param w	the new width value */
	public void setCurrentWidth(int w) {
		currentWidth = w;
		for (Shape currentShape: shapes)
			if ( currentShape.isSelected())
				currentShape.setWidth(currentWidth);
	}
	/** get the current height
	 * @return currentHeight - the height value */
	public int getCurrentHeight() { return currentHeight; }
	/** set the current height and the height for all currently selected shapes
	 * @param h	the new height value */
	public void setCurrentHeight(int h) {
		currentHeight = h;
		for (Shape currentShape: shapes)
			if ( currentShape.isSelected())
				currentShape.setHeight(currentHeight);
	}
	/** get the current fill colour
	 * @return currentColor - the fill colour value */
	public Color getCurrentColor() { return currentColor; }
	/** set the current color and the color for all currently selected shapes
	 * @param bc	the new color value */
	public void setCurrentColor(Color bc) {
		currentColor = bc;
		for (Shape currentShape: shapes)
			if ( currentShape.isSelected())
				currentShape.setColor(currentColor);
	}
    /**    move and paint all shapes within the animation area
     * @param g    the Graphics control */
    public void paintComponent(Graphics g) {
		painter.setGraphics(g);
		super.paintComponent(g);
		for (Shape currentShape: shapes) {
			currentShape.move();
			currentShape.draw(painter);
			currentShape.drawHandles(painter);
			currentShape.drawString(painter);
		}
	}

    // you don't need to make any changes after this line ______________
    class MyMouseAdapter extends MouseAdapter {
		public void mouseClicked( MouseEvent e ) {
			boolean found = false;
			for (Shape currentShape: shapes)
				if ( currentShape.contains( e.getPoint()) ) { // if the mousepoint is within a shape, then set the shape to be selected/deselected
					currentShape.setSelected( ! currentShape.isSelected() );
					found = true;
				}
			if (!found) createNewShape(e.getX(), e.getY());
		}
	}
	/**    update the painting area
     * @param g    the graphics control */
    public void update(Graphics g){ paint(g); }
    /** reset the margin size of all shapes from our ArrayList */
    public void resetMarginSize() {
        marginWidth = getWidth();
        marginHeight = getHeight() ;
        for (Shape currentShape: shapes)
			currentShape.setMarginSize(marginWidth,marginHeight );
    }
	public void start() {
        animationThread = new Thread(this);
        animationThread.start();
    }
    public void stop() {
        if (animationThread != null) {
            animationThread = null;
        }
    }
    public void run() {
        Thread myThread = Thread.currentThread();
        while(animationThread==myThread) {
            repaint();
            pause(DELAY);
        }
    }
    private void pause(int milliseconds) {
        try {
            Thread.sleep((long)milliseconds);
        } catch(InterruptedException ie) {}
    }
	public boolean loadShapes(String filename) {
    	File myFile = new File(filename);
    	try {
			Scanner myScanner = new Scanner(myFile);
			while (myScanner.hasNextLine()) {
				String string = myScanner.nextLine();
				String[] arrayData = string.split(",");
				currentShapeType = ShapeType.values()[Integer.parseInt(arrayData[0])];
				createNewShape(Integer.parseInt(arrayData[1]), Integer.parseInt(arrayData[2]));
			}
			myScanner.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.printf("ERROR: The file %s does not exist.\n", filename);
			return false;
		} catch (Exception e) {
			System.out.println("An error occurred.");
			return false;
		}
	}
}

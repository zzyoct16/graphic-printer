/* ==============================================
 *  Shape.java : The superclass of all shapes.
 *  A shape defines various properties, including selected, colour, width and height.
 *  YOUR UPI: zzou832
 *  ===============================================================================
 */

import java.awt.*;
abstract class Shape {
    public static final PathType DEFAULT_PATHTYPE = PathType.BOUNCE;
    public static final ShapeType DEFAULT_SHAPETYPE = ShapeType.RECTANGLE;
    public static final int DEFAULT_X = 0, DEFAULT_Y = 0, DEFAULT_WIDTH=50, DEFAULT_HEIGHT=100, DEFAULT_MARGIN_WIDTH=600, DEFAULT_MARGIN_HEIGHT=800;
    public static final Color DEFAULT_COLOR=Color.orange;
    public static final String DEFAULT_TEXT = "A2";
    public int x, y, width=DEFAULT_WIDTH, height=DEFAULT_HEIGHT, marginWidth=DEFAULT_MARGIN_WIDTH, marginHeight=DEFAULT_MARGIN_HEIGHT; // the margin of the animation panel area
    protected MovingPath path = new BouncingPath(1, 2);            // the moving path
    protected Color color=DEFAULT_COLOR;
    protected boolean selected = false;    // draw handles if selected
    protected String text = DEFAULT_TEXT;

    /** default constructor to create a shape with default values */
    public Shape() {}
    /** constructor to create a shape
     * @param x         the x-coordinate of the new shape
     * @param y        the y-coordinate of the new shape
     * @param w         the width of the new shape
     * @param h         the height of the new shape
     * @param mw         the margin width of the animation panel
     * @param mh        the margin height of the animation panel
     * @param c        the colour of the new shape
     * @param typeOfPath         the path of the new shape  */
    public Shape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
        this.x = x;
        this.y = y;
        marginWidth = mw;
        marginHeight = mh;
        width = w;
        height = h;
        color = c;
        setPath(pt);
        
    }
    public Shape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt,String text) {
        this.x = x;
        this.y = y;
        marginWidth = mw;
        marginHeight = mh;
        width = w;
        height = h;
        color = c;
        setPath(pt);
        this.text=text;
    }

    public String toString() {
        return String.format("%s:[x=%d,y=%d,w=%d,h=%d,mw=%d,mh=%d,c=%s,area=%.0f,text=%s]", this.getClass().getName(), x,y,width,height,marginWidth,marginHeight,color,getArea(),text);
    }
    
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
    
    public void drawString(Painter g) {
        g.drawString(text, x, y, width, height);
    }

    /** Return a string representation of the shape, containing
     * the String representation of each element. */
    
    /** Set the path of the shape.
     * @param pathID     the path  */
    public void setPath(PathType pathID) {
        switch (pathID) {
            case BOUNCE : {
                path = new BouncingPath(1, 2);
                break;
            } case BOUNDARY: {
            	path = new BoundaryPath(5);
            	break;
			}
		}
    }
    /** Return the x-coordinate of the shape.
     * @return the x coordinate */
    public int getX() { return this.x; }
	/** Set the x-coordinate of the shape.
	 * @param x	 the x-coordinate */
	public void setX(int x) { this.x = x; }
	/** Return the y-coordinate of the shape.
     * @return the y coordinate */
    public int getY() { return this.y;}
	/** Set the y-coordinate of the shape.
	 * @param y	 the y-coordinate	 */
	public void setY(int y) { this.y = y; }
    /** Return the width of the shape.
     * @return the width     */
	public int getWidth() { return width; }
	/** Set the width of the shape.
	 * @param w	 the width */
	public void setWidth(int w) { width = w; }
    /** Return the height of the shape.
     * @return the height     */
	public int getHeight() {return height; }
    /** Set the height of the shape.
     * @param h     the height value */
    public void setHeight(int h) { height = h; }
    /** Return the selected property of the shape.
     * @return the selected property  */
    public boolean isSelected() { return selected; }
    /** Set the selected property of the shape.
     *    When the shape is selected, its handles are shown.
     * @param s     the selected value */
    public void setSelected(boolean s) { selected = s; }
    /** Return the fill colour of the shape.
     * @return the fill colour */
	public Color getColor() { return color; }
    /** Set the fill colour of the shape.
     * @param c     the fill colour */
    public void setColor(Color fc) { color = fc; }
    /** Reset the margin for the shape
     * @param w     the margin width
     * @param h     the margin height */
    public void setMarginSize(int w, int h) {
        marginWidth = w;
        marginHeight = h;
    }
    /** Draw the handles of the shape
     * @param g     the Graphics control */
    public void drawHandles(Painter g) {
        g.drawHandles(selected, x, y, width, height);
    }
	/** Returns whether the point is in the shape
	 * @param g	 the Graphics control */
    public abstract boolean contains(Point p);
    /** draw the shape
     * @param g     the Graphics control */
    public abstract void draw(Painter g);

    public abstract double getArea();
    /** move the shape by the path  */
    public void move() {
        path.move();
    }
    /* Inner class ===================================================================== Inner class
     *    MovingPath : The superclass of all paths. It is an inner class.
     *    A path can change the current position of the shape.
     *    =============================================================================== */
    abstract class MovingPath {
        protected int deltaX, deltaY; // moving distance
        /** constructor  */
        public MovingPath() { }
        /** move the shape according to the path */
        public abstract void move();
    }
    /*
     *  ===============================================================================
     *  BouncingPath : A Bouncing path.
     *  ===============================================================================
     */
    class BouncingPath extends MovingPath {
         /** constructor to initialise values for a bouncing path */
        public BouncingPath(int dx, int dy) {
            deltaX = dx;
            deltaY = dy;
         }
        /** move the shape */
        public void move() {
             x = x + deltaX;
             y = y + deltaY;
             if ((x < 0) && (deltaX < 0)) {
                 deltaX = -deltaX;
                 x = 0;
             }
             else if ((x + width > marginWidth) && (deltaX > 0)) {
                 deltaX = -deltaX;
                 x = marginWidth - width;
             }
             if ((y< 0) && (deltaY < 0)) {
                 deltaY = -deltaY;
                 y = 0;
             }
             else if((y + height > marginHeight) && (deltaY > 0)) {
                 deltaY = -deltaY;
                 y = marginHeight - height;
             }
        }
    }
    /*
     *  ===============================================================================
     *  BoundaryPath : A boundary path.
     *  ===============================================================================
     */
	class BoundaryPath extends MovingPath {
		private Direction direction = Direction.DOWN;
		/** constructor to initialise values for a boundary path */
		public BoundaryPath(int step) { deltaX = deltaY = step; }
		/** move the shape */
		public void move() {
			int h = marginHeight - height;
			int w = marginWidth - width;
		  	switch  (direction) {
				case DOWN : { // move downwards
			  		y += deltaY;
			  		if (y > h) {
						y = h;
						direction = direction.next();
			  		}
			  		break;
				} case RIGHT : { // move to the right
			  		x += deltaX;
			  		if (x > w) {
						x = w;
						direction = direction.next();
			  		}
			  		break;
				} case UP : {
			  		y -= deltaY; // move upwards
			  		if (y < 0) {
						y = 0;
						direction = direction.next();
			  		}
			  		break;
				} case LEFT : { // move to the left
			   		x -= deltaX;
					if (x < 0) {
				  		x = 0;
				  		direction = direction.next();
			  		}
			   		break;
				}
		  	}
		}
	}
}
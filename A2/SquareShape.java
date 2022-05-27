/*
 *	===============================================================================
 *	SquareShape.java : A shape that is a square.
 *  YOUR UPI:zzou832
 *	=============================================================================== */
import java.awt.*;
class SquareShape extends RectangleShape {
    /** constructors */
    public SquareShape() {
		width = Math.min(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		height = Math.min(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	public SquareShape(int x, int y, int s, int mw, int mh, Color c, PathType pt) {
		super(x ,y ,s, s ,mw ,mh, c, pt);
	}
	public SquareShape(int x, int y, int s, int mw, int mh, Color c, PathType pt, String t) {
		super(x ,y ,s, s ,mw ,mh, c, pt, t);
	}
	/** modify both width and height of a square */
	@Override
    public void setWidth(int side) {
        super.setWidth(side);
        super.setHeight(side);
    }
	/** modify both width and height of a square */
	@Override
    public void setHeight(int side) {
        super.setWidth(side);
        super.setHeight(side);
    }
}
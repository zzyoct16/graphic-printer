/*
 *  ============================================================================================
 *  Implementation of the Painter interface that delegates drawing to a Graphics object
 *  YOUR UPI: zzou832
 *  ============================================================================================
 */
import java.awt.*;
import java.awt.Graphics2D;

class GraphicsPainter implements Painter {
	private Graphics2D g;
	/** constructors */
	public GraphicsPainter() {}
	public GraphicsPainter(Graphics g) { this.g = (Graphics2D) g;}
	/** set the graphics element
	*	@param g	the graphics object */
	public void setGraphics(Graphics g) {
		this.g = (Graphics2D)g;
	}
	@Override
	/** set the color
	*	@param color	the color object */
	public void setPaint(Color c) {
		g.setPaint(c);
	}
	/** draw 4 corners if a shape is selected
	*	@param isSelected, x, y, width, heigth */
	@Override
	public void drawHandles(boolean isSelected, int x, int y, int width, int height) {
		if (isSelected) {
            g.setPaint(Color.black);
            g.fillRect(x -2, y-2, 4, 4);
            g.fillRect(x + width -2, y + height -2, 4, 4);
            g.fillRect(x -2, y + height -2, 4, 4);
            g.fillRect(x + width -2, y-2, 4, 4);
        }
	}
	public void fillRect(int x, int y, int width, int height){
		g.fillRect(x,y,width,height);
	}
	public void fillOval(int x, int y, int width, int height){
		g.fillOval(x,y,width,height);
	}
	public void drawString(String text, int x, int y, int width, int height) {
		g.setPaint(Color.black);
		g.drawString(text, x, y+height);
	}
}

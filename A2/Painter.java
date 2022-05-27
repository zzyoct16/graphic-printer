/*
 *  ============================================================================================
 *  Painter.java : Painter interface
 *  YOUR UPI: zzou832
 *  ============================================================================================
 */
import java.awt.*;
interface Painter {
	/** set the graphics element
	*	@param g	the graphics object */
	public void setGraphics(Graphics g);
	/** set the color
	*	@param color	the color object */
	public void setPaint(Color color);
	/** draw 4 corners if a shape is selected
	*	@param isSelected, x, y, width, heigth */
	public void drawHandles(boolean isSelected, int x, int y, int width, int height);
	public void fillRect(int x, int y, int width, int height);
		
	public void fillOval(int x, int y, int width, int height);
	
	public void drawString(String text, int x, int y, int width, int height);
}
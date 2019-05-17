package plotkit.geom;

import java.awt.geom.Rectangle2D;

/**
 * Immutable class that defines the bounding rectangle of some object.
 * <P>
 * Unlike {@link java.awt.Rectangle} this object is immutable and does not expose it's field members.
 */
public class Rect
{
	// Constants
	public static final Rect Empty = new Rect(0, 0, 0, 0);

	// State vars
	private final double x;
	private final double y;
	private final double width;
	private final double height;

	public Rect(double aX, double aY, double aWidth, double aHeight)
	{
		x = aX;
		y = aY;
		width = aWidth;
		height = aHeight;
	}

	/**
	 * Instantiates the corresponding Rectangle2D.
	 * <P>
	 * TODO: Consider just supporting the relevant methods (that do not mutate the Rectangle2D).
	 */
	public Rectangle2D formRectangle2D()
	{
		Rectangle2D retRect = new Rectangle2D.Double(x, y, width, height);
		return retRect;
	}

	/**
	 * Returns the width of the rectangle.
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * Returns the height of the rectangle.
	 */
	public double getY()
	{
		return y;
	}

	/**
	 * Returns the width of the rectangle.
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * Returns the height of the rectangle.
	 */
	public double getHeight()
	{
		return height;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(width);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rect other = (Rect) obj;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

}

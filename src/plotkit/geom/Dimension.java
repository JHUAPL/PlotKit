package plotkit.geom;

/**
 * Immutable class that defines the dimension (width and height) of some object.
 * <P>
 * Unlike {@link java.awt.Dimension} this object is immutable and does not expose it's field members.
 */
public class Dimension
{
	// State vars
	private final double width;
	private final double height;

	public Dimension(double aWidth, double aHeight)
	{
		width = aWidth;
		height = aHeight;
	}

	/**
	 * Returns the width of the dimension.
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * Returns the height of the dimension.
	 */
	public double getHeight()
	{
		return height;
	}
}

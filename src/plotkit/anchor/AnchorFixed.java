package plotkit.anchor;

import java.awt.Graphics2D;

/**
 * An Anchor type that supports placement of Painters at a constant fixed distance from the baseline.
 */
public class AnchorFixed implements Anchor
{
	// Constants
	/** Anchor that places the Painter at the baseline of the plot. */
	public static AnchorFixed Base = new AnchorFixed(0.0);

	// Attributes
	private final double offset;

	public AnchorFixed(double aOffset)
	{
		offset = aOffset;
	}

	@Override
	public double getPositionX(Graphics2D g2d, boolean isWestSide)
	{
		if (isWestSide == true)
			return -offset;
		else
			return offset;
	}

	@Override
	public double getPositionY(Graphics2D g2d, boolean isNorthSide)
	{
		if (isNorthSide == true)
			return -offset;
		else
			return offset;
	}

}

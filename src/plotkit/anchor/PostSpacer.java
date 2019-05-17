package plotkit.anchor;

import java.awt.Graphics2D;

import plotkit.Painter;

/**
 * Deprecated class that is too be phased out.
 * <P>
 * Currently the only class dependent on this is LegendPainter from the svp package.
 * <P>
 * A more flexible Anchor should be used to layout the LegendPainter.
 */
@Deprecated
public class PostSpacer
{
	// Attributes
	private final Painter refPainter;
	private final double offsetLen;

	public PostSpacer(Painter aPainter, double aOffsetLen)
	{
		refPainter = aPainter;
		offsetLen = aOffsetLen;
	}

	/**
	 * Returns the vertical space that this Spacer would need when it is painted.
	 */
	public double getHeight(Graphics2D g2d)
	{
		return refPainter.getHeightForAxisX(g2d) + offsetLen;
	}

	/**
	 * Returns the horizontal space that this Spacer would need when it is painted.
	 */
	public double getWidth(Graphics2D g2d)
	{
		return refPainter.getWidthForAxisY(g2d) + offsetLen;
	}

}

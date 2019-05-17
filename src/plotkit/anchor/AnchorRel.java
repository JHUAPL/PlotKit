package plotkit.anchor;

import java.awt.Graphics2D;

import plotkit.Painter;

/**
 * An Anchor type that supports placement of Painters at a relative position to another Painter.
 */
public class AnchorRel implements Anchor
{
	// Attributes
	private final Painter refPainter;
	private final double offsetLen;

	public AnchorRel(Painter aPainter, double aOffsetLen)
	{
		refPainter = aPainter;
		offsetLen = aOffsetLen;
	}

	@Override
	public double getPositionX(Graphics2D g2d, boolean isWestSide)
	{
		// TODO: This is a defective implementation.
		// TODO: This assumes the refPainter is positioned at the baseline. That may not be the case.
		int zios_2019Jan15;

		if (isWestSide == true)
			return -(refPainter.getWidthForAxisY(g2d) + offsetLen);
		else
			return refPainter.getWidthForAxisY(g2d) + offsetLen;
	}

	@Override
	public double getPositionY(Graphics2D g2d, boolean isNorthSide)
	{
		// TODO: This is a defective implementation.
		// TODO: This assumes the refPainter is positioned at the baseline. That may not be the case.
		int zios_2019Jan15;

		return refPainter.getHeightForAxisX(g2d) + offsetLen;
	}

}

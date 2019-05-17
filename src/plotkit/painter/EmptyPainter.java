package plotkit.painter;

import java.awt.Graphics2D;

import plotkit.*;
import plotkit.geom.Dimension;

/**
 * Painter that renders nothing to the screen and occupies no space. This Painter has no state data and thus is a
 * singleton.
 */
public class EmptyPainter implements Painter
{
	// Constants
	public static final EmptyPainter Instance = new EmptyPainter();

	/** Singleton Constructor */
	private EmptyPainter()
	{
		; // Nothing to do
	}

	@Override
	public String getDescription()
	{
		String retStr = "Empty:Painter";
		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		return 0;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		return 0;
	}

	@Override
	public void renderAxisX(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isNorthSide)
	{
		; // Nothing to do
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		; // Nothing to do
	}

}

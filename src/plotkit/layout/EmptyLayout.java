package plotkit.layout;

import java.awt.Graphics2D;
import java.util.List;

import com.google.common.collect.ImmutableList;

import plotkit.*;
import plotkit.anchor.Anchor;
import plotkit.geom.Rect;
import plotkit.misc.LogicError;

/**
 * Layout which occupies no space and renders nothing.
 * <P>
 * This Layout has no state and is immutable - thus Painters can not be added to it.
 */
public class EmptyLayout implements Layout
{
	// Constants
	public static final EmptyLayout Instance = new EmptyLayout();

	/**
	 * Singleton Constructor
	 */
	private EmptyLayout()
	{
		; // Nothing to do
	}

	@Override
	public void addPainter(Painter aPainter, Anchor aAnchor)
	{
		throw new LogicError("Painters can not be added to EmptyLayout.");
	}

	@Override
	public void addPainter(Painter aPainter)
	{
		throw new LogicError("Painters can not be added to EmptyLayout.");
	}

	@Override
	public List<Painter> getPainters()
	{
		return ImmutableList.of();
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
	public void renderSideNorth(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		; // Nothing to do
	}

	@Override
	public void renderSideSouth(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		; // Nothing to do
	}

	@Override
	public void renderSideWest(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		; // Nothing to do
	}

	@Override
	public void renderSideEast(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		; // Nothing to do
	}

}

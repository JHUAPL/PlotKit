package plotkit.painter;

import java.awt.Graphics2D;
import java.util.List;

import com.google.common.collect.ImmutableList;

import plotkit.AxisTransform;
import plotkit.Painter;
import plotkit.geom.Dimension;
import plotkit.util.AxisUtil;

/**
 * Painter that renders nothing to the screen. This Painter is composed of a list of child Painters.
 * <P>
 * The child Painters are used to calculate the summation of space required when rendering. The child painters however
 * are not rendered.
 * <P>
 * A typical use case for this Painter is to force some group of plot's axis to occupy a minimum space. If one desires
 * to allow a collection of plots to have the same x-axis space then this HiddenPainter can be installed which will
 * force the axis to request a certain space but cause nothing to actually be rendered - thus forcing all axis with this
 * HiddenPainter to be installed to require the space of this HiddenPainter.
 */
public class HiddenPainter implements Painter
{
	// Constants
	public static final HiddenPainter Empty = new HiddenPainter(ImmutableList.of());

	// Attributes
	private final ImmutableList<Painter> childList;

	public HiddenPainter(List<Painter> aChildList)
	{
		childList = ImmutableList.copyOf(aChildList);
	}

	@Override
	public String getDescription()
	{
		String retStr = "Hidden: " + childList.size();
		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		// Delegate to the AxisUtil
		double retVal = AxisUtil.calcHeightForAxisX(g2d, childList);
		return retVal;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		// Delegate to the AxisUtil
		double retVal = AxisUtil.calcWidthForAxisY(g2d, childList);
		return retVal;
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

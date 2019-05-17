package plotkit.util;

import java.awt.Graphics2D;
import java.util.List;

import plotkit.Painter;

/**
 * Collection of utility methods used to work with plot axis.
 */
public class AxisUtil
{
	/**
	 * Utility method to calculate the number of pixels that should be reserved to render the Y-Axis
	 *
	 * @param g2d
	 *        Graphics context of where to render the axis.
	 * @param aPainterList
	 *        The list of Painters that will be used to draw the x-axis.
	 */
	public static double calcHeightForAxisX(Graphics2D g2d, List<Painter> aPainterList)
	{
		double maxH = 0;
		for (Painter aPainter : aPainterList)
		{
			double tmpH = aPainter.getHeightForAxisX(g2d);
			if (tmpH > maxH)
				maxH = tmpH;
		}

		return maxH;
	}

	/**
	 * Utility method to calculate the number of pixels that should be reserved to render the Y-Axis
	 *
	 * @param g2d
	 *        Graphics context of where to render the axis.
	 * @param aPainterList
	 *        The list of Painters that will be used to draw the y-axis.
	 */
	public static double calcWidthForAxisY(Graphics2D g2d, List<Painter> aPainterList)
	{
		double maxW = 0;
		for (Painter aPainter : aPainterList)
		{
			double tmpW = aPainter.getWidthForAxisY(g2d);
			if (tmpW > maxW)
				maxW = tmpW;
		}

		return maxW;
	}

}

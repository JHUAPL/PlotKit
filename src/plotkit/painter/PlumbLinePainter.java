package plotkit.painter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Objects;

import plotkit.*;
import plotkit.geom.Dimension;

public class PlumbLinePainter implements Painter
{
	// Attributes
	private final Color color;

	public PlumbLinePainter(Color aColor)
	{
		color = Objects.requireNonNull(aColor);
	}

	/**
	 * Returns the color used to paint the plumb line.
	 */
	public Color getColor()
	{
		return color;
	}

	@Override
	public String getDescription()
	{
		String retStr = "rgb: " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		return 1;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		return 1;
	}

	@Override
	public void renderAxisX(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isNorthSide)
	{
		double dimW = aAxisDim.getWidth();
		double dimH = aAxisDim.getHeight();
		double xPos = 0;
		double yPos = 0;

		if (isNorthSide == true)
			yPos = dimH - 1;

		g2d.setColor(color);
		g2d.draw(new Line2D.Double(xPos, yPos, xPos + dimW, yPos));
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		double dimW = aAxisDim.getWidth();
		double dimH = aAxisDim.getHeight();
		double xPos = 0;
		double yPos = 0;

		if (isWestSide == true)
			xPos = dimW - 1;

		g2d.setColor(color);
		g2d.draw(new Line2D.Double(xPos, yPos, xPos, yPos + dimH));
	}

}

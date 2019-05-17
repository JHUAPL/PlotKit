package plotkit.painter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;

import plotkit.*;
import plotkit.cadence.Cadence;
import plotkit.geom.Dimension;
import plotkit.misc.LogicError;
import plotkit.text.TextProvider;

public class TimeLinePainter implements Painter
{
	// Attributes
	private final TextProvider textProvider;
	private final Cadence cadence;
	private final Font font;
	private final Color color;
	private final int padBetweenText;
	private final int padSize;

	/**
	 * @param aTextProvider
	 *        The TextProvider used to transform from model vars to the corresponding text.
	 * @param aCadence
	 *        The spacing that defines where each interval is placed.
	 * @param aFont
	 *        The font associated with the text to be rendered.
	 * @param aColor
	 *        The foreground color.
	 * @param aPadBetweenText
	 *        The space (in pixels) to maintain between the text and the number line.
	 */
	public TimeLinePainter(TextProvider aTextProvider, Cadence aCadence, Font aFont, Color aColor, int aPadBetweenText)
	{
		textProvider = Objects.requireNonNull(aTextProvider);
		cadence = Objects.requireNonNull(aCadence);
		font = Objects.requireNonNull(aFont);
		color = Objects.requireNonNull(aColor);
		padBetweenText = aPadBetweenText;
		padSize = aPadBetweenText;
	}

	/**
	 * Returns the cadence (spacing between each interval).
	 */
	public Cadence getCadence()
	{
		return cadence;
	}

	/**
	 * Returns the color used to render the foreground.
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Returns the font used to render the text.
	 */
	public Font getFont()
	{
		return font;
	}

	@Override
	public String getDescription()
	{
		String retStr = "oLen: dynamic" + "; pbt: " + padBetweenText;
		retStr += "; rgb: " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
		retStr += "; font: " + font.getName() + "," + font.getStyle() + "," + font.getSize();
		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		// Retrieve the FontMetrics
		FontMetrics metrics = g2d.getFontMetrics(font);
		double fmHeight = metrics.getHeight();

		double retVal = fmHeight + padSize;
		return retVal;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		String nomText = textProvider.getNominalTextForX();

		// Retrieve the FontMetrics
		FontMetrics metrics = g2d.getFontMetrics(font);
		double textW = metrics.stringWidth(nomText);

		double retVal = textW;
		return retVal;
	}

	@Override
	public void renderAxisX(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isNorthSide)
	{
		// Set up the rendering props
		g2d.setFont(font);
		g2d.setColor(color);

		// Bail if the dimensions are not sufficient
		if (aAxisDim.getWidth() < 3 || aAxisDim.getHeight() < 3)
			return;

		// Retrieve the FontMetrics and calculate the nominal text height
		FontMetrics metrics = g2d.getFontMetrics(font);
		double fmHeight = metrics.getHeight();
		double nomH = fmHeight;

		// Calculate non-changing values
		double maxY = aAxisDim.getHeight() - 1;
		double yPos = 0;
		if (isNorthSide == true)
			yPos = maxY;

		double midY = yPos + nomH / 2.0 + padSize / 2.0;
		double markL = padSize + nomH;

		// Get the initial text
		double mValInit = aAxisTransform.getPlotValForAxisVal(0);
		String targText = textProvider.getTextForX(mValInit);

		// Iterate via the Cadence
		double begAX = 0;
		double endAX = 0;

		Iterator<Double> iter = cadence.getIter(aAxisTransform, 0, aAxisDim.getWidth());
		while (iter.hasNext() == true)
		{
			// Retrieve the next model value
			double mVal = iter.next();

			// Render the vertical bar
			double pX = aAxisTransform.getAxisValForPlotVal(mVal);
			double pY = 0;
//			if (isNorthSide == true)
//				pY = (maxY - offsetLength) - markL;
			g2d.draw(new Line2D.Double(pX, pY, pX, pY + markL));

			// Render the interval line(s)
			endAX = pX;
			renderIntervalX(g2d, targText, begAX, endAX, midY);

			// Update the begAX to reflect the "previous" endAX
			begAX = pX;

			// Calculate the "next" targText
			targText = textProvider.getTextForX(mVal);

		}

		// Render the final interval
		endAX = aAxisDim.getWidth();
		renderIntervalX(g2d, targText, begAX, endAX, midY);
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		throw new LogicError("Unsupported operation...");
	}

	/**
	 * Helper method that renders the specified interval. The text will be centered in the given axis interval.
	 * <P>
	 * If the interval is too small then the text will not be rendered
	 *
	 * @param g2d
	 *        The graphics context used for rendering.
	 * @param aText
	 *        The text to be rendered.
	 * @param aBegX
	 *        The beginning of the interval (units in axis space)
	 * @param aEndX
	 *        The end of the interval (units in axis space)
	 * @param aMidY
	 *        The center position (units in axis space) of where the text should be placed
	 */
	private void renderIntervalX(Graphics2D g2d, String aText, double aBegX, double aEndX, double aMidY)
	{
		// Bail if interval is empty
		if (aBegX == aEndX)
			return;

		// Retrieve the FontMetrics
		FontMetrics metrics = g2d.getFontMetrics(font);
		double fmHeight = metrics.getHeight();
		double fmDescent = metrics.getDescent();
		double textW = metrics.stringWidth(aText);

		// Calculate the width of the span and the center point along the x-axis
		double dimW = aEndX - aBegX;
		double cX = aBegX + (dimW / 2.0);

		// Position the text properly
		double xText = cX - textW / 2.0;
		if (xText < aBegX + padBetweenText)
			xText = aBegX + padBetweenText;
		if (xText + textW + padBetweenText > aEndX && aBegX == 0)
			xText = aEndX - (textW + padBetweenText);

		// Render the text
//		double yText = aMidY + fmHeight / 2.0;
		double yText = aMidY + fmHeight / 2.0 - fmDescent;
		g2d.drawString(aText, (float) xText, (float) yText);

		// Draw the left side of the interval
		double tmpX = cX - (textW / 2.0 + padBetweenText);
		if (tmpX > aBegX)
			g2d.draw(new Line2D.Double(aBegX, aMidY, tmpX, aMidY));

		// Draw the right side of the interval
		tmpX = cX + (textW / 2.0 + padBetweenText);
		if (tmpX < aEndX)
			g2d.draw(new Line2D.Double(tmpX, aMidY, aEndX, aMidY));
	}

}

package plotkit.painter;

import java.awt.*;
import java.util.Objects;

import plotkit.AxisTransform;
import plotkit.Painter;
import plotkit.geom.Dimension;

/**
 * Immutable class that defines a {@link Painter} used to render a multi line title.
 * <P>
 * The title will be split up by '\n' characters and will be drawn on seperate lines.
 */
public class MultiTitlePainter implements Painter
{
	// Attributes
	private final String title;
	private final Color color;
	private final Font font;
	private final TextAnchor anchor;
	private final int offsetLength;

	/**
	 * @param aTitle
	 *        The text used for the title.
	 * @param aColor
	 *        The color associated with the text to be rendered.
	 * @param aFont
	 *        The font associated with the text to be rendered.
	 * @param aAncher
	 *        Determines where the text will be anchored.
	 * @param aOffsetLength
	 *        The offset length (pixels) before the tick should be rendered. TextTickPainters are typically associated
	 *        with a PlainTickPainter - the length of that Tick should be used here.
	 */
	public MultiTitlePainter(String aTitle, Color aColor, Font aFont, TextAnchor aAnchor, int aOffsetLength)
	{
		title = aTitle;
		color = Objects.requireNonNull(aColor);
		font = Objects.requireNonNull(aFont);
		anchor = Objects.requireNonNull(aAnchor);
		offsetLength = aOffsetLength;
	}

	/**
	 * Returns the title.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Returns the color used to render the text.
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

	/**
	 * Returns the Anchor used when for the text.
	 */
	public TextAnchor getAnchor()
	{
		return anchor;
	}

	/**
	 * Returns the offset length (in pixels).
	 */
	public int getOffsetLength()
	{
		return offsetLength;
	}

	@Override
	public String getDescription()
	{
		String retStr = "title: " + title + "; oLen: " + offsetLength;
		retStr += "; rgb: " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
		retStr += "; font: " + font.getName() + "," + font.getStyle() + "," + font.getSize();

		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		// Break the title up by lines
		String[] textArr = title.split("\n");

		// Retrieve the FontMetrics
		FontMetrics metrics = g2d.getFontMetrics(font);
		double fmHeight = metrics.getHeight();

		// Compute the total heights
		double fullH = fmHeight * textArr.length;

		double retVal = offsetLength + fullH;
		return retVal;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		// Break the title up by lines
		String[] textArr = title.split("\n");

		// Retrieve the FontMetrics
		FontMetrics metrics = g2d.getFontMetrics(font);

		// Compute the maximum width
		double maxW = 0;
		for (String aText : textArr)
		{
			double tmpW = metrics.stringWidth(aText);
			if (tmpW > maxW)
				maxW = tmpW;
		}

		double retVal = offsetLength + maxW;
		return retVal;
	}

	@Override
	public void renderAxisX(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isNorthSide)
	{
		// Calculate non-changing values
		double maxX = aAxisDim.getWidth() - 1;
		double maxY = aAxisDim.getHeight() - 1;
		double yPos = offsetLength;
		if (isNorthSide == true)
			yPos = maxY - offsetLength;

		// Set up the rendering props
		g2d.setFont(font);
		g2d.setColor(color);

		// Break the title up into individual lines
		String[] textArr = title.split("\n");

		// Retrieve the FontMetrics
		FontMetrics metrics = g2d.getFontMetrics(font);
		double fmHeight = metrics.getHeight();

		// Compute the total height and largest width
		double fullH = fmHeight * textArr.length;
		double fullW = 0;
		for (String aText : textArr)
		{
			double tmpW = metrics.stringWidth(aText);
			if (tmpW > fullW)
				fullW = tmpW;
		}

		double xPosInit, xPos;
		if (anchor == TextAnchor.Lead)
			xPosInit = maxX - fullW;
		else if (anchor == TextAnchor.Tail)
			xPosInit = 0L;
		else
			xPosInit = (maxX / 2.0) - (fullW / 2.0);

		yPos = offsetLength;
		if (isNorthSide == true)
			yPos = maxY - fullH;

		// Render the individual lines of text
		for (String aTextStr : textArr)
		{
			double textW = metrics.stringWidth(aTextStr);

			xPos = xPosInit - (textW / 2.0);
			yPos += fmHeight;

			// Render the tick text
			g2d.drawString(aTextStr, (float) xPos, (float) yPos);
		}
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		// Calculate non-changing values
		double maxX = aAxisDim.getWidth() - 1;
		double maxY = aAxisDim.getHeight() - 1;
		double xPos = offsetLength;
		if (isWestSide == true)
			xPos = maxX - offsetLength;

		// Set up the rendering props
		g2d.setFont(font);
		g2d.setColor(color);

		// Break the title up into individual lines
		String[] textArr = title.split("\n");

		// Retrieve the FontMetrics
		FontMetrics metrics = g2d.getFontMetrics(font);
		double fmHeight = metrics.getHeight();

		// Compute the total heights
		double fullH = fmHeight * textArr.length;

		double yPos;
		if (anchor == TextAnchor.Lead)
			yPos = maxY - fullH;
		else if (anchor == TextAnchor.Tail)
			yPos = 0L;
		else
			yPos = (maxY / 2.0) - (fullH / 2.0);

		// Render the individual lines of text
		for (String aTextStr : textArr)
		{
			double textW = metrics.stringWidth(aTextStr);

			xPos = offsetLength + ((maxX - offsetLength) / 2.0) - (textW / 2.0);
			yPos += fmHeight;

			// Render the tick text
			g2d.drawString(aTextStr, (float) xPos, (float) yPos);
		}
	}

}

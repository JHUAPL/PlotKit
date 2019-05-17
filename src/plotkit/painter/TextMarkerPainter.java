package plotkit.painter;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import plotkit.*;
import plotkit.geom.Dimension;
import plotkit.geom.Rect;
import plotkit.text.TextProvider;

public class TextMarkerPainter implements Painter, Marker
{
	// Attributes
	private final TextProvider textProvider;
	private final Color bgColor;
	private final Color fgColor;
	private final Font font;
	private final TextAnchor anchor;
	private final double angle;
	private final int padSize;

	// Derived vars
	private final double angRad;

	// State vars
	private double markerX;
	private double markerY;

	/**
	 * @param aTextProvider
	 *        The TextProvider used to transform from model vars to the corresponding text.
	 * @param aBgColor
	 *        The color associated with the background to be rendered.
	 * @param aFgColor
	 *        The color associated with the text to be rendered.
	 * @param aFont
	 *        The font associated with the text to be rendered.
	 * @param aAngle
	 *        The angle (in degrees) for which the text should be rotated.
	 * @param aPadSize
	 *        The number of pixels for which this marker will be padded.
	 */
	public TextMarkerPainter(TextProvider aTextProvider, Color aBgColor, Color aFgColor, Font aFont, TextAnchor aAnchor,
			double aAngle, int aPadSize)
	{
		textProvider = Objects.requireNonNull(aTextProvider);
		bgColor = Objects.requireNonNull(aBgColor);
		fgColor = Objects.requireNonNull(aFgColor);
		font = Objects.requireNonNull(aFont);
		anchor = Objects.requireNonNull(aAnchor);
		angle = aAngle;
		padSize = aPadSize;

		angRad = Math.toRadians(angle);

		markerX = Double.NaN;
		markerY = Double.NaN;
	}

	public TextMarkerPainter(TextProvider aTextProvider, Color aBgColor, Color aFgColor, Font aFont, TextAnchor aAnchor,
			double aAngle)
	{
		this(aTextProvider, aBgColor, aFgColor, aFont, aAnchor, aAngle, 2);
	}

	/**
	 * Returns the background color used to render the text.
	 */
	public Color getColorBG()
	{
		return bgColor;
	}

	/**
	 * Returns the foreground color used to render the text.
	 */
	public Color getColorFG()
	{
		return fgColor;
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
	 * Returns the angle (in degrees) to rotate the text.
	 */
	public double getAngle()
	{
		return angle;
	}

	/**
	 * Returns the pad size (in pixels) used by this TextMarkerPainter. The pad size is applied to each side of the
	 * TextMarkerPainter.
	 */
	public int getPadSize()
	{
		return padSize;
	}

	@Override
	public String getDescription()
	{
		String retStr = "padW: " + padSize;
		retStr += "; rgb: " + fgColor.getRed() + "," + fgColor.getGreen() + "," + fgColor.getBlue();
		retStr += "; font: " + font.getName() + "," + font.getStyle() + "," + font.getSize();

		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		String tmpText = textProvider.getNominalTextForX();
		Rect bRect = TextUtil.calcBoundingRect(g2d, tmpText, font, angRad);

		double retVal = bRect.getHeight() + padSize;
		return retVal;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		String tmpText = textProvider.getNominalTextForY();
		Rect bRect = TextUtil.calcBoundingRect(g2d, tmpText, font, angRad);

		double retVal = bRect.getWidth() + padSize;
		return retVal;
	}

	@Override
	public void renderAxisX(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isNorthSide)
	{
		// Bail if no valid marker-x position
		if (Double.isNaN(markerX) == true)
			return;

		// Calculate non-changing values
		double maxY = aAxisDim.getHeight() - 1;
		double yPos = 0;
		if (isNorthSide == true)
			yPos = maxY;

		// Retrieve the text to be rendered
		double mVal = markerX;
		String tmpText = textProvider.getTextForX(mVal);

		// Bail if the marker position is not within the viewable axis
		double xPos = aAxisTransform.getAxisValForPlotVal(mVal);
		if (xPos < 0 || xPos > aAxisDim.getWidth())
			return;

		// Retrieve the TextLayout
		g2d.rotate(angRad, 0, 0);
		TextLayout textLayout = new TextLayout(tmpText, font, g2d.getFontRenderContext());
		g2d.rotate(-angRad, 0, 0);

		// Determine the point at which to draw the text
		double xPosText = xPos - TextUtil.calcOffset(g2d, anchor, textLayout, true);
		double yPosText = yPos + textLayout.getAscent();
		if (isNorthSide == true)
			yPosText = yPos;

		// Render the border box
		Rectangle tmpRect = textLayout.getPixelBounds(g2d.getFontRenderContext(), (float) xPosText, (float) yPosText);
		Shape tmpShape = new Rectangle2D.Double(tmpRect.getX() - padSize, tmpRect.getY() - padSize,
				tmpRect.getWidth() + (padSize * 2), tmpRect.getHeight() + (padSize * 2));

		// Rotate the g2d
		g2d.rotate(angRad, xPos, yPos);

		g2d.setColor(bgColor);
		g2d.fill(tmpShape);

		g2d.setColor(fgColor);
		g2d.draw(tmpShape);

		// Render the tick text
		g2d.setFont(font);
		g2d.drawString(tmpText, (float) xPosText, (float) yPosText);

		// Undo the g2d rotation
		g2d.rotate(-angRad, xPos, yPos);
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		// Bail if no valid marker-x position
		if (Double.isNaN(markerY) == true)
			return;

		// Calculate non-changing values
		double maxX = aAxisDim.getWidth() - 1;
		double maxY = aAxisDim.getHeight() - 1;
		double xPos = 0;
		if (isWestSide == true)
			xPos = maxX;

		// Retrieve the text to be rendered
		double mVal = markerY;
		String tmpText = textProvider.getTextForY(mVal);

		// Bail if the marker position is not within the viewable axis
		double yPos = aAxisTransform.getAxisValForPlotVal(mVal);
		if (yPos < 0 || yPos > aAxisDim.getHeight())
			return;

		// TODO: Y-Axis needs to be inverted (Perhaps apply an AffineTransform to g2d?)
		yPos = maxY - yPos;

		// Retrieve the TextLayout
		g2d.rotate(angRad, 0, 0);
		TextLayout textLayout = new TextLayout(tmpText, font, g2d.getFontRenderContext());
		g2d.rotate(-angRad, 0, 0);

		// Determine the point at which to draw the text
		double xPosText = xPos;
		double yPosText = yPos + TextUtil.calcOffset(g2d, anchor, textLayout, false);
		if (isWestSide == true)
			xPosText = xPos - textLayout.getPixelBounds(g2d.getFontRenderContext(), 0, 0).width;

		// Render the border box
		Rectangle tmpRect = textLayout.getPixelBounds(g2d.getFontRenderContext(), (float) xPosText, (float) yPosText);
		Shape tmpShape = new Rectangle2D.Double(tmpRect.getX() - padSize, tmpRect.getY() - padSize,
				tmpRect.getWidth() + (padSize * 2), tmpRect.getHeight() + (padSize * 2));

		// Rotate the g2d
		g2d.rotate(angRad, xPos, yPos);

		g2d.setColor(bgColor);
		g2d.fill(tmpShape);

		g2d.setColor(fgColor);
		g2d.draw(tmpShape);

		// Render the tick text
		g2d.setFont(font);
		g2d.drawString(tmpText, (float) xPosText, (float) yPosText);

		// Undo the g2d rotation
		g2d.rotate(-angRad, xPos, yPos);
	}

	@Override
	public void setMarker(double aMarkerX, double aMarkerY)
	{
		markerX = aMarkerX;
		markerY = aMarkerY;
	}

}

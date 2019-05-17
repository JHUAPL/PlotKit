package plotkit.painter;

import java.awt.*;
import java.awt.font.TextLayout;
import java.util.*;

import plotkit.*;
import plotkit.cadence.Cadence;
import plotkit.geom.Dimension;
import plotkit.geom.Rect;
import plotkit.text.TextProvider;

/**
 * Immutable class that defines a {@link Painter} used to render the text associated with a group of plot ticks.
 */
public class TextTickPainter implements Painter
{
	// Attributes
	private final TextProvider textProvider;
	private final Cadence cadence;
	private final Color color;
	private final Font font;
	private final TextAnchor anchor;
	private final double angle;
	private final boolean overDrawF;

	// Derived vars
	private final double angRad;

	/**
	 * @param aTextProvider
	 *        The TextProvider used to transform from model vars to the corresponding text.
	 * @param aCadence
	 *        The spacing between each tick.
	 * @param aColor
	 *        The color associated with the text to be rendered.
	 * @param aFont
	 *        The font associated with the text to be rendered.
	 * @param aAnchor
	 *        The location where the rendered text will be anchored.
	 * @param aAngle
	 *        The angle (in degrees) for which the text should be rotated.
	 * @param aOverDrawF
	 *        Flag that causes this TextTickPainter to draw past the standard axis range. This is useful in cases where
	 *        it would be ideal to render the text ticks past the standard range so that text will not pop in and out as
	 *        it enters the axis boundaries but rather will be clipped by the view. The graphics context should be
	 *        clipped if this is set to true.
	 */
	public TextTickPainter(TextProvider aTextProvider, Cadence aCadence, Color aColor, Font aFont, TextAnchor aAnchor,
			double aAngle, boolean aOverDrawF)
	{
		textProvider = Objects.requireNonNull(aTextProvider);
		cadence = Objects.requireNonNull(aCadence);
		color = Objects.requireNonNull(aColor);
		font = Objects.requireNonNull(aFont);
		anchor = Objects.requireNonNull(aAnchor);
		angle = aAngle;
		overDrawF = aOverDrawF;

		angRad = Math.toRadians(angle);
	}

	/**
	 * Returns the cadence (spacing between ticks in pixels).
	 */
	public Cadence getCadence()
	{
		return cadence;
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
	 * Returns the angle (in degrees) to rotate the text.
	 */
	public double getAngle()
	{
		return angle;
	}

	@Override
	public String getDescription()
	{
		String retStr = "cad: " + cadence + "; ang: " + angle;
		retStr += "; rgb: " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
		retStr += "; font: " + font.getName() + "," + font.getStyle() + "," + font.getSize();

		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		String tmpText = textProvider.getNominalTextForX();
		Rect bRect = TextUtil.calcBoundingRect(g2d, tmpText, font, angRad);
		double retVal = bRect.getHeight();
		return retVal;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		String tmpText = textProvider.getNominalTextForY();
		Rect bRect = TextUtil.calcBoundingRect(g2d, tmpText, font, angRad);
		double retVal = bRect.getWidth();
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

		// Calculate non-changing values
		double maxY = aAxisDim.getHeight() - 1;
		double yPos = 0;
		if (isNorthSide == true)
			yPos = maxY;

		// Expand the Axis dimensions by the height of the nominal text. This allows for text to be (partially) displayed
		// even though the actual point may not be visible on the screen. This is better than just having the text pop
		// into
		// the axis when it's corresponding value is in range of the axis.
		AxisTransform targAxisTransform = aAxisTransform;
		Dimension targDim = aAxisDim;
		if (overDrawF == true)
		{
			String nomText = textProvider.getNominalTextForX();
			Rect bRect = TextUtil.calcBoundingRect(g2d, nomText, font, angRad);
			int nomWidth = (int) bRect.getWidth();

			targAxisTransform = new ScrolledAxisTransform(aAxisTransform, -nomWidth);
			targDim = new Dimension(aAxisDim.getWidth() + nomWidth * 2, aAxisDim.getHeight());
		}

		// Iterate via the Cadence
		Iterator<Double> iter = cadence.getIter(targAxisTransform, 0, targDim.getWidth());
		while (iter.hasNext() == true)
		{
			double mVal = iter.next();
			String tmpText = textProvider.getTextForX(mVal);

			g2d.rotate(angRad, 0, 0);
			TextLayout textLayout = new TextLayout(tmpText, font, g2d.getFontRenderContext());
			g2d.rotate(-angRad, 0, 0);

			// Determine the point at which to perform the rotation
			double xPos = aAxisTransform.getAxisValForPlotVal(mVal);

			// Print some debug info
//			Rectangle textRectSideSpec = aTextLayout.getPixelBounds(null, 0, 0);
//			System.out.println("isNorthSide: " + isNorthSide + " textRectSideSpec.height: " + textRectSideSpec.height + " textRectSideSpec.y: " + textRectSideSpec.y);

			// Determine the point at which to draw the text
			double xPosText = xPos - TextUtil.calcOffset(g2d, anchor, textLayout, true);
			double yPosText = yPos + textLayout.getAscent();
			if (isNorthSide == true)
				yPosText = yPos;

			// Render the tick text
			g2d.rotate(angRad, xPos, yPos);
			g2d.drawString(tmpText, (float) xPosText, (float) yPosText);
			g2d.rotate(-angRad, xPos, yPos);
		}
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		// Bail if the dimensions are not sufficient
		if (aAxisDim.getWidth() < 3 || aAxisDim.getHeight() < 3)
			return;

		// Set up the rendering props
		g2d.setFont(font);
		g2d.setColor(color);

		// Calculate non-changing values
		double maxX = aAxisDim.getWidth() - 1;
		double maxY = aAxisDim.getHeight() - 1;
		double posSX = 0;
		if (isWestSide == true)
			posSX = maxX;

		// Expand the Axis dimensions by the height of the nominal text. This allows for text to be (partially) displayed
		// even though the actual point may not be visible on the screen. This is better than just having the text pop
		// into
		// the axis when it's corresponding value is in range of the axis.
		AxisTransform targAxisTransform = aAxisTransform;
		Dimension targDim = aAxisDim;
		if (overDrawF == true)
		{
			String nomText = textProvider.getNominalTextForY();
			Rect bRect = TextUtil.calcBoundingRect(g2d, nomText, font, angRad);
			int nomHeight = (int) bRect.getHeight();

			targAxisTransform = new ScrolledAxisTransform(aAxisTransform, -nomHeight);
			targDim = new Dimension(aAxisDim.getWidth(), aAxisDim.getHeight() + nomHeight * 2);
		}

		// Iterate via the Cadence
		Iterator<Double> iter = cadence.getIter(targAxisTransform, 0, targDim.getHeight());
		while (iter.hasNext() == true)
		{
			double mVal = iter.next();
			String tmpText = textProvider.getTextForY(mVal);

			g2d.rotate(angRad, 0, 0);
			TextLayout textLayout = new TextLayout(tmpText, font, g2d.getFontRenderContext());
			g2d.rotate(-angRad, 0, 0);

			// Determine the point at which to perform the rotation
			double posAY = aAxisTransform.getAxisValForPlotVal(mVal);
			double posSY = maxY - posAY;
			if (aAxisTransform.isInverted() == true)
				posSY = posAY;

			// Print some debug info
//			Rectangle textRectSideSpec = aTextLayout.getPixelBounds(null, 0, 0);
//			System.out.println("isNorthSide: " + isNorthSide + " textRectSideSpec.height: " + textRectSideSpec.height + " textRectSideSpec.y: " + textRectSideSpec.y);

			// Determine the point at which to draw the text
			double xPosText = posSX;
			double yPosText = posSY + TextUtil.calcOffset(g2d, anchor, textLayout, false);
			if (isWestSide == true)
				xPosText = posSX - textLayout.getPixelBounds(g2d.getFontRenderContext(), 0, 0).width;

			// Render the tick text
			g2d.rotate(angRad, posSX, posSY);
			g2d.drawString(tmpText, (float) xPosText, (float) yPosText);
			g2d.rotate(-angRad, posSX, posSY);
		}
	}

	/**
	 * Private class to allow the axis to be virtually "scrolled". This is needed when the text will be drawn with the
	 * overDrawF flag. This allows the Axis boundaries to be expanded in negative direction.
	 *
	 */
	private class ScrolledAxisTransform implements AxisTransform
	{
		// Attributes
		private final AxisTransform refAT;
		private final double scrollAmt;

		public ScrolledAxisTransform(AxisTransform aAxisTransform, double aScrollAmt)
		{
			refAT = aAxisTransform;
			scrollAmt = aScrollAmt;
		}

		@Override
		public double getAxisValForPlotVal(double aPlotVal)
		{
			return refAT.getAxisValForPlotVal(aPlotVal) - scrollAmt;
		}

		@Override
		public double getPlotValForAxisVal(double aAxisVal)
		{
			return refAT.getPlotValForAxisVal(aAxisVal + scrollAmt);
		}

		@Override
		public boolean isInverted()
		{
			return refAT.isInverted();
		}

	}

}

package plotkit.painter;

import java.awt.*;
import java.awt.font.TextLayout;

import plotkit.geom.Rect;
import plotkit.misc.LogicError;

public class TextUtil
{

	/**
	 * Utility method that returns where the text should drawn relative to the anchor.
	 * <P>
	 * TODO: There are design issues with this method. It does not (can not?) properly handle (all cases that involve)
	 * rotated text.
	 */
	public static double calcOffset(Graphics2D g2d, TextAnchor aAnchor, TextLayout aTextLayout, boolean isAxisX)
	{
		Rectangle pRect;

		switch (aAnchor)
		{
			case Lead:
				return 0;

			case Tail:
				pRect = aTextLayout.getPixelBounds(g2d.getFontRenderContext(), 0, 0);
				if (isAxisX == true)
					return pRect.getWidth();
				else
					return pRect.getHeight();

			case Center:
				int zios_clean;
				pRect = aTextLayout.getPixelBounds(g2d.getFontRenderContext(), 0, 0);
				if (isAxisX == true)
					return pRect.getWidth() / 2.0;
				else
					return aTextLayout.getAscent() / 2.0;
//				return pRect.getHeight() / 2.0;

			case CenterFull:
				pRect = aTextLayout.getPixelBounds(g2d.getFontRenderContext(), 0, 0);
				if (isAxisX == true)
					return pRect.getWidth() / 2.0;
				else
					return aTextLayout.getAscent() / 2.0 - aTextLayout.getDescent() / 2.0;
//				return pRect.getHeight() / 2.0 - aTextLayout.getDescent() / 2.0;

			default:
				throw new LogicError("Unsupported anchor type: " + aAnchor);
		}
	}

	/**
	 * Utility method that returns the bounding box dimensions of the specified text. The text will be rotated by the
	 * angle attribute.
	 * <P>
	 * The returned dimensions will always be positive.
	 *
	 * @param g2d
	 *        Graphics context used for rendering.
	 * @param aText
	 *        The text of interest
	 * @param aFont
	 *        The font used to render the text.
	 * @param aAngRad
	 *        The angle (in radians) at which the text should be rotated.
	 */
	public static Rect calcBoundingRect(Graphics2D g2d, String aText, Font aFont, double aAngRad)
	{
		// TODO: this method should be equivalent to commented calcBoundingRect_ORIG() - but that method does not appear
		// to work on the Apple platform

		// Always evaluate the angle as a positive value
		aAngRad = Math.abs(aAngRad);

		// Retrieve the unrotated bounding box
		TextLayout textLayout = new TextLayout(aText, aFont, g2d.getFontRenderContext());
		Rectangle bRect = textLayout.getPixelBounds(g2d.getFontRenderContext(), 0, 0);
//		Rectangle bRect = textLayout.getPixelBounds(null, 0, 0);
//		System.out.println("----> TextLayout -> descent: " + textLayout.getDescent() + " ascent: " + textLayout.getAscent() + " advance: " + textLayout.getAdvance()
//		+ "   bounds: " + bRect + "   angDeg: " + Math.toDegrees(aAngRad));

		// Manually calculate the rotated bounding box
		double cosA = Math.cos(aAngRad);
		double sinA = Math.sin(aAngRad);

		double pX = (bRect.getX() * cosA) + (bRect.getY() * sinA);
		double pY = (bRect.getX() * sinA) + (bRect.getY() * cosA);
		double pW = (bRect.width * cosA) + (bRect.height * sinA);
		double pH = (bRect.width * sinA) + (bRect.height * cosA);

		Rect retRect = new Rect(pX, pY, pW, pH);
		return retRect;
	}

//	public static Rect calcBoundingRect_ORIG(Graphics2D g2d, String aText, Font aFont, double aAngRad)
//	{
//		// TODO: this method should be equivalent to uncommented calcBoundingRect() - but does not appear to work on the Apple platform
//
//		// Always evaluate the angle as a positive value
//		aAngRad = Math.abs(aAngRad);
//
//		// Retrieve the rotated bounding box
//		g2d.rotate(aAngRad, 0, 0);
//		TextLayout textLayout = new TextLayout(aText, aFont, g2d.getFontRenderContext());
//		Rectangle bRect = textLayout.getPixelBounds(g2d.getFontRenderContext(), 0, 0);
////		Rectangle bRect = textLayout.getPixelBounds(null, 0, 0);
////		System.out.println("----> TextLayout -> descent: " + textLayout.getDescent() + " ascent: " + textLayout.getAscent() + " advance: " + textLayout.getAdvance()
////		+ "   bounds: " + bRect + "   angDeg: " + Math.toDegrees(aAngRad));
//		g2d.rotate(-aAngRad, 0, 0);
//
//		// Retrieve the rotated bounding box
//		double pX = bRect.getX();
//		double pY = bRect.getY();
//		double pW = bRect.getWidth();
//		double pH = bRect.getHeight();
//
//		Rect retRect = new Rect(pX, pY, pW, pH);
//		return retRect;
//	}

}

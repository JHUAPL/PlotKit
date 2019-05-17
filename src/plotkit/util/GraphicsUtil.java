package plotkit.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GraphicsUtil
{

	/**
	 * Configure {@link RenderingHints} on a {@link Graphics2D} object to render with sub-pixel accuracy enabled.
	 * <p>
	 * This method encapsulates the set of rendering hints we have found largely through trial and error that yield
	 * proper results across multiple platforms. Platform to platform so of the hints configured here may be unnecessary,
	 * but given the platform and API dependent nature of these values it seems an appropriate route to take.
	 * </p>
	 * Source: Scott Turner Email - 2017May09
	 * <P>
	 * 
	 * @param graphics
	 *        the graphics context to adjust the rendering hints
	 * 
	 * @return the original hints, which can be restored with {@link Graphics2D#setRenderingHints(java.util.Map)}
	 */
	public static RenderingHints configureHintsForSubpixelQuality(Graphics2D graphics)
	{

		RenderingHints original = graphics.getRenderingHints();

		/*
		 * Enable quality rendering hint, as that is what we are chasing.
		 */
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		/*
		 * Turn on Anti-Aliasing, as this is necessary for rendering subpixel stuff accurately and smoothly.
		 */
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/*
		 * Grant found that enabling pure stroke mode gives proper subpixel stroking, necessary for rendering shapes and
		 * other 2D API objects accurately.
		 */
		graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

		/*
		 * Turn on the alpha interpolation to quality, as we are rendering transparent images.
		 */
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		/*
		 * Improve the quality of output fonts.
		 */
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

		return original;
	}

}
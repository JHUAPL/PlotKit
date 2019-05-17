package plotkit.layout;

import java.awt.Graphics2D;
import java.util.*;

import com.google.common.collect.ImmutableList;

import plotkit.*;
import plotkit.anchor.*;
import plotkit.geom.*;
import plotkit.misc.LogicError;

/**
 * Standard Layout used to handle positioning and rendering of Painters.
 */
public class PlainLayout implements Layout
{
	// State vars
	private Map<Painter, Anchor> layoutMap;

	// Cache vars
	private double cMaxH;
	private double cMaxW;

	/**
	 * Constructor
	 */
	public PlainLayout()
	{
		layoutMap = new LinkedHashMap<>();

		cMaxH = Double.NaN;
		cMaxW = Double.NaN;
	}

	/**
	 * Returns the Anchor associated with the specified Painter.
	 * <P>
	 * Throws a LogicError if this Layout does not contain the specified Painter.
	 */
	public Anchor getAnchor(Painter aPainter)
	{
		Anchor retAnchor = layoutMap.get(aPainter);
		if (retAnchor == null)
			throw new LogicError("Painter is not installed on this layout. Painter: " + aPainter);

		return retAnchor;
	}

	@Override
	public List<Painter> getPainters()
	{
		return ImmutableList.copyOf(layoutMap.keySet());
	}

	@Override
	public void addPainter(Painter aPainter, Anchor aAnchor)
	{
		if (aAnchor == null)
			throw new NullPointerException();

		if (layoutMap.get(aPainter) != null)
			throw new LogicError("The Painter has already been added to the Layout. It can not be added again!");

		layoutMap.put(aPainter, aAnchor);
	}

	/**
	 * {@inheritDoc}
	 * <P>
	 * The default position is defined as the corresponding baseline associated with this Layout.
	 */
	@Override
	public void addPainter(Painter aPainter)
	{
		// Delegate
		addPainter(aPainter, AnchorFixed.Base);
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		// Utilize the cached values
		if (Double.isNaN(cMaxH) == false)
			return cMaxH;

		// Calculate the space needed for a horizontal configuration
		double maxH = 0;
		for (Painter aPainter : layoutMap.keySet())
		{
			// TODO: Are we properly accounting for the Anchor?
			int zios_2019Jan15;
			Anchor tmpAnchor = layoutMap.get(aPainter);
			double posY = tmpAnchor.getPositionX(g2d, false);

			double tmpH = aPainter.getHeightForAxisX(g2d) + posY;
			if (tmpH > maxH)
				maxH = tmpH;
		}

		cMaxH = maxH;
		return cMaxH;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		// Utilize the cached values
		if (Double.isNaN(cMaxW) == false)
			return cMaxW;

		// Calculate the space needed for a vertical configuration
		double maxW = 0;
		for (Painter aPainter : layoutMap.keySet())
		{
			// TODO: Are we properly accounting for the Anchor?
			int zios_2019Jan15;
			Anchor tmpAnchor = layoutMap.get(aPainter);
			double posX = tmpAnchor.getPositionX(g2d, false);

			double tmpW = aPainter.getWidthForAxisY(g2d) + posX;
			if (tmpW > maxW)
				maxW = tmpW;
		}

		cMaxW = maxW;
		return cMaxW;
	}

	@Override
	public void renderSideNorth(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		renderAxisX(g2d, aRect, aAxisTransform, true);
	}

	@Override
	public void renderSideSouth(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		renderAxisX(g2d, aRect, aAxisTransform, false);
	}

	@Override
	public void renderSideWest(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		renderAxisY(g2d, aRect, aAxisTransform, true);
	}

	@Override
	public void renderSideEast(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		renderAxisY(g2d, aRect, aAxisTransform, false);
	}

	/**
	 * Helper method used to render horizontal region outside of the plot (North / South).
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 * @param aRect
	 *        Rectangle used to define the region of interest.
	 * @param aAxisTransform
	 *        AxisTransform used to convert between model space and axis space units.
	 * @param isNorthSide
	 *        Defines whether we are rendering the North side or South side of the plot.
	 */
	private void renderAxisX(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform, boolean isNorthSide)
	{
		// Bail if aRect is less that 1 pixel wide or 1 pixel high
		if (aRect.getWidth() <= 0 || aRect.getHeight() <= 0)
			return;

		// Retrieve a graphics context appropriate for painting the Painters
		double rootX = aRect.getX();
		double rootY = aRect.getY();
		double rootW = aRect.getWidth();
		double rootH = aRect.getHeight();

		// TODO: It would be much more robust if we could create a temporal Graphics2D and then dispose of it.
		// Unfortunately we can
		// TODO: not since this library is being used with the buggy FXGraphics2D!

		int zios_2019Jan03;
//		g2d = (Graphics2D)g2d.create();
		g2d.translate(rootX, rootY);
//		g2d.clipRect(0, 0, tmpW, tmpH);
//		g2d = (Graphics2D)g2d.create(tmpX, tmpY, tmpW, tmpH);

		// Render the Painters
		Dimension axisDim = new Dimension(rootW, rootH);
		for (Painter aPainter : layoutMap.keySet())
		{
			// Determine where the Painter should be positioned
			Anchor tmpAnchor = layoutMap.get(aPainter);
			double baseX = 0;
			double baseY = tmpAnchor.getPositionX(g2d, isNorthSide);

			// Render the Painter relative to the transformed location
			g2d.translate(baseX, baseY);
			aPainter.renderAxisX(g2d, axisDim, aAxisTransform, isNorthSide);
			g2d.translate(-baseX, -baseY);
		}

		// Restore the Graphics2D
		g2d.translate(-rootX, -rootY);
	}

	/**
	 * Helper method used to render vertical region outside of the plot (West / East).
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 * @param aRect
	 *        Rectangle used to define the region of interest.
	 * @param aAxisTransform
	 *        AxisTransform used to convert between model space and axis space units.
	 * @param isWestSide
	 *        Defines whether we are rendering the West side or East side of the plot.
	 */
	private void renderAxisY(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform, boolean isWestSide)
	{
		// Bail if aRect is less that 1 pixel wide or 1 pixel high
		if (aRect.getWidth() <= 0 || aRect.getHeight() <= 0)
			return;

		// Retrieve a graphics context appropriate for painting the Painters
		double rootX = aRect.getX();
		double rootY = aRect.getY();
		double rootW = aRect.getWidth();
		double rootH = aRect.getHeight();

		// TODO: It would be much more robust if we could create a temporal Graphics2D and then dispose of it.
		// TODO: We can not since this library is being used with the defective FXGraphics2D!
		// TODO: zios-2019Jan03
//		g2d = (Graphics2D)g2d.create();
		g2d.translate(rootX, rootY);
//		g2d.clipRect(0, 0, tmpW, tmpH);
//		g2d = (Graphics2D)g2d.create(tmpX, tmpY, tmpW, tmpH);

		// Render the Painters
		Dimension axisDim = new Dimension(rootW, rootH);
		for (Painter aPainter : layoutMap.keySet())
		{
			// Determine where the Painter should be positioned
			Anchor tmpAnchor = layoutMap.get(aPainter);
			double baseX = tmpAnchor.getPositionX(g2d, isWestSide);
			double baseY = 0;

			// Render the Painter relative to the transformed location
			g2d.translate(baseX, baseY);
			aPainter.renderAxisY(g2d, axisDim, aAxisTransform, isWestSide);
			g2d.translate(-baseX, -baseY);
		}

		// Restore the Graphics2D
		g2d.translate(-rootX, -rootY);
	}

}

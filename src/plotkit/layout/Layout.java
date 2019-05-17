package plotkit.layout;

import java.awt.Graphics2D;
import java.util.List;

import plotkit.*;
import plotkit.anchor.Anchor;
import plotkit.geom.Rect;

/**
 * Interface that defines the methods needed to arrange and render a collection of Painters.
 * <P>
 * Implementers of this interface will be responsible for the actual positioning of the Painters and for calling each
 * Painter's render*() method with the proper arguments.
 */
public interface Layout
{
	/**
	 * Adds a Painter to this Layout. The Painter will be added so that it is positioned at the specified anchor.
	 */
	public void addPainter(Painter aPainter, Anchor aAnchor);

	/**
	 * Adds a Painter to this Layout. The Painter will be added so that it is positioned at the base line.
	 * <P>
	 * TODO: Should the wording be changed so that "the base line." becomes "the default position."
	 */
	public void addPainter(Painter aPainter);

	/**
	 * Returns the list of Painters that have been added to this Layout.
	 */
	public List<Painter> getPainters();

	/**
	 * Returns the number of pixels that should be reserved to render the X-Axis
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 */
	public double getHeightForAxisX(Graphics2D g2d);

	/**
	 * Returns the number of pixels that should be reserved to render the Y-Axis
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 */
	public double getWidthForAxisY(Graphics2D g2d);

	/**
	 * Renders the installed Painters to the North side of the plot.
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 * @param aRect
	 *        Rectangle used to define the northern region.
	 * @param aAxisTransform
	 *        AxisTransform used to convert between model space and axis space units.
	 */
	public void renderSideNorth(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform);

	/**
	 * Renders the installed Painters to the South side of the plot.
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 * @param aRect
	 *        Rectangle used to define the southern region.
	 * @param aAxisTransform
	 *        AxisTransform used to convert between model space and axis space units.
	 */
	public void renderSideSouth(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform);

	/**
	 * Renders the installed Painters to the West side of the plot.
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 * @param aRect
	 *        Rectangle used to define the western region.
	 * @param aAxisTransform
	 *        AxisTransform used to convert between model space and axis space units.
	 */
	public void renderSideWest(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform);

	/**
	 * Renders the installed Painters to the East side of the plot.
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 * @param aRect
	 *        Rectangle used to define the eastern region.
	 * @param aAxisTransform
	 *        AxisTransform used to convert between model space and axis space units.
	 */
	public void renderSideEast(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform);

}

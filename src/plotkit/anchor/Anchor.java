package plotkit.anchor;

import java.awt.Graphics2D;

/**
 * Interface that describes how a Painter should be placed (anchored) within a layout.
 */
public interface Anchor
{
	/**
	 * Returns where the Painter should be positioned relative to the plot's baseline.
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 * @param isWestSide
	 */
	public double getPositionX(Graphics2D g2d, boolean isWestSide);

	/**
	 * Returns where the Painter should be positioned relative to the plot's baseline.
	 *
	 * @param g2d
	 *        The relevant graphics context.
	 * @param isNorthSide
	 */
	public double getPositionY(Graphics2D g2d, boolean isNorthSide);

}

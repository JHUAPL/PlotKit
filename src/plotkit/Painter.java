// Copyright (C) 2024 The Johns Hopkins University Applied Physics Laboratory LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package plotkit;

import java.awt.Graphics2D;

import plotkit.geom.Dimension;

/**
 * Interface that defines the methods needed to render a portion or section of a plot. Some examples of painters would
 * be: (plumb line, ticks, text, ...)
 * <P>
 * Implementers of this interface will be perform the actual rendering of the different plot components.
 *
 * @author lopeznr1
 */
public interface Painter
{
	/**
	 * Returns a short textual description of this Painter.
	 */
	public String getDescription();

//	/**
//	 * Returns the dimension of the space required by this Painter.
//	 */
//	public Dimension getDimension(Graphics2D g2);

	/**
	 * Returns the vertical space that this Painter would need when painting a X-Axis.
	 */
	public double getHeightForAxisX(Graphics2D g2d);

	/**
	 * Returns the horizontal space that this Painter would need when painting a Y-Axis.
	 */
	public double getWidthForAxisY(Graphics2D g2d);

	/**
	 * Renders the Painter to the specified graphics context. The graphics context is assumed to be bounded to the
	 * specified width and height.
	 *
	 * @param g2d
	 *    The relevant graphics context.
	 * @param aAxisDim
	 *    The dimension (in pixel space) of the Axis boundaries.
	 * @param aAxisTransform
	 *    AxisTranform to translate between plot (model) coordinates and axis (pixel) coordinates.
	 * @param isNorthSide
	 *    True if the North axis is being rendered rather than the South axis.
	 */
	public void renderAxisX(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isNorthSide);

	/**
	 * Renders the Painter to the specified graphics context. The graphics context is assumed to be bounded to the
	 * specified width and height.
	 *
	 * @param g2d
	 *    The relevant graphics context.
	 * @param aAxisDim
	 *    The dimension (in pixel space) of the Axis boundaries.
	 * @param aAxisTransform
	 *    AxisTranform to translate between plot (model) coordinates and axis (pixel) coordinates.
	 * @param isWestSide
	 *    True if the West axis is being rendered rather than the East axis.
	 */
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide);
}

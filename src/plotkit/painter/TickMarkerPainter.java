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
package plotkit.painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Objects;

import plotkit.AxisTransform;
import plotkit.Painter;
import plotkit.geom.Dimension;

/**
 * Immutable class that defines a {@link Painter} that will render plain ticks at regular (integral) intervals.
 *
 * @author lopeznr1
 */
public class TickMarkerPainter implements Painter, Marker
{
	// Attributes
	private final Color color;
	private final int length;

	// State vars
	private double markerX;
	private double markerY;

	/**
	 * @param aCadence
	 *    The spacing between each tick.
	 * @param aColor
	 *    The color associated with the tick to be rendered.
	 * @param aLength
	 *    The length (pixels) of each tick.
	 */
	public TickMarkerPainter(Color aColor, int aLength)
	{
		color = Objects.requireNonNull(aColor);
		length = aLength;
	}

	/**
	 * Returns the color used to paint the ticks.
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Returns the length (in pixels) of the ticks to be rendered.
	 */
	public int getLength()
	{
		return length;
	}

	@Override
	public String getDescription()
	{
		String retStr = "olen: " + length;
		retStr += "; rgb: " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();

		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		return length;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		return length;
	}

	@Override
	public void renderAxisX(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isNorthSide)
	{
		// Bail if the dimensions are not sufficient
		if (aAxisDim.getWidth() < 3 || aAxisDim.getHeight() < 3)
			return;

		double dimH = aAxisDim.getHeight();
		double maxY = dimH - 1;

		double yPos = 0;
		if (isNorthSide == true)
			yPos = maxY - length;

		// Bail if the marker position is not within the viewable axis
		double xPos = aAxisTransform.getAxisValForPlotVal(markerX);
		if (xPos < 0 || xPos > aAxisDim.getWidth())
			return;

		// Render the tick
		g2d.setColor(color);
		g2d.draw(new Line2D.Double(xPos, yPos, xPos, yPos + length));
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		// Bail if the dimensions are not sufficient
		if (aAxisDim.getWidth() < 3 || aAxisDim.getHeight() < 3)
			return;

		double dimW = aAxisDim.getWidth();
		double dimH = aAxisDim.getHeight();
		double maxX = dimW - 1;
		double maxY = dimH - 1;

		double xPos = 0;
		if (isWestSide == true)
			xPos = maxX - length;

		// Bail if the marker position is not within the viewable axis
		double yPos = aAxisTransform.getAxisValForPlotVal(markerY);
		if (yPos < 0 || yPos > aAxisDim.getHeight())
			return;

		// TODO: Y-Axis needs to be inverted (Perhaps apply an AffineTransform to g2d?)
		yPos = maxY - yPos;

		// Render the tick
		g2d.setColor(color);
		g2d.draw(new Line2D.Double(xPos, yPos, xPos + length, yPos));
	}

	@Override
	public void setMarker(double aMarkerX, double aMarkerY)
	{
		markerX = aMarkerX;
		markerY = aMarkerY;
	}

}

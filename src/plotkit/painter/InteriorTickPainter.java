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
import java.util.Iterator;
import java.util.Objects;

import plotkit.AxisTransform;
import plotkit.Painter;
import plotkit.cadence.Cadence;
import plotkit.geom.Dimension;

/**
 * Immutable class that defines a {@link Painter} that will render plain ticks at regular (integral) intervals.
 * <P>
 * The ticks will be rendered on the interior of the Plot rather than the exterior.
 *
 * @author lopeznr1
 */
public class InteriorTickPainter implements Painter
{
	// Attributes
	private final Cadence cadence;
	private final Color color;
	private final int length;

	/**
	 * @param aCadence
	 *    The spacing between each tick.
	 * @param aColor
	 *    The color associated with the tick to be rendered.
	 * @param aLength
	 *    The length (pixels) of each tick.
	 */
	public InteriorTickPainter(Cadence aCadence, Color aColor, int aLength)
	{
		cadence = Objects.requireNonNull(aCadence);
		color = Objects.requireNonNull(aColor);
		length = aLength;
	}

	/**
	 * Returns the cadence (spacing between ticks in pixels).
	 */
	public Cadence getCadence()
	{
		return cadence;
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
		String retStr = "cad: " + cadence + "; len: " + length;
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

		// Set up the rendering props
		g2d.setColor(color);

		double dimW = aAxisDim.getWidth();
		double dimH = aAxisDim.getHeight();
		double maxX = dimW;
		double maxY = dimH;

		double posSY = 0 - (1 + length);
		if (isNorthSide == true)
			posSY = maxY;

		// Iterate via the Cadence
		Iterator<Double> iter = cadence.getIter(aAxisTransform, 0, dimW);
		while (iter.hasNext() == true)
		{
			double mVal = iter.next();

			double posAX = aAxisTransform.getAxisValForPlotVal(mVal);
			double posSX = posAX;
			if (aAxisTransform.isInverted() == true)
				posSX = maxX - posAX;

			g2d.draw(new Line2D.Double(posSX, posSY, posSX, posSY + length));
		}
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		// Bail if the dimensions are not sufficient
		if (aAxisDim.getWidth() < 3 || aAxisDim.getHeight() < 3)
			return;

		// Set up the rendering props
		g2d.setColor(color);

		double dimW = aAxisDim.getWidth();
		double dimH = aAxisDim.getHeight();
		double maxX = dimW;
		double maxY = dimH;

		double xPos = 0 - (1 + length);
		if (isWestSide == true)
			xPos = maxX;
		double posSX = xPos;

		// Iterate via the Cadence
		Iterator<Double> iter = cadence.getIter(aAxisTransform, 0, dimH);
		while (iter.hasNext() == true)
		{
			double mVal = iter.next();

			double posAY = aAxisTransform.getAxisValForPlotVal(mVal);
			double posSY = maxY - posAY;
			if (aAxisTransform.isInverted() == true)
				posSY = posAY;

			g2d.draw(new Line2D.Double(posSX, posSY, posSX + length, posSY));
		}
	}

}

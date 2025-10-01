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

import java.awt.Graphics2D;

import plotkit.AxisTransform;
import plotkit.Painter;
import plotkit.geom.Dimension;

/**
 * Painter that renders nothing to the screen and occupies no space. This Painter has no state data and thus is a
 * singleton.
 *
 * @author lopeznr1
 */
public class EmptyPainter implements Painter
{
	// Constants
	public static final EmptyPainter Instance = new EmptyPainter();

	/** Singleton Constructor */
	private EmptyPainter()
	{
		; // Nothing to do
	}

	@Override
	public String getDescription()
	{
		String retStr = "Empty:Painter";
		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		return 0;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		return 0;
	}

	@Override
	public void renderAxisX(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isNorthSide)
	{
		; // Nothing to do
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		; // Nothing to do
	}

}

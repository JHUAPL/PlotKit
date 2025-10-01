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
package plotkit.layout;

import java.awt.Graphics2D;
import java.util.List;

import com.google.common.collect.ImmutableList;

import plotkit.AxisTransform;
import plotkit.Painter;
import plotkit.anchor.Anchor;
import plotkit.geom.Rect;
import plotkit.misc.LogicError;

/**
 * Layout which occupies no space and renders nothing.
 * <P>
 * This Layout has no state and is immutable - thus Painters can not be added to it.
 *
 * @author lopeznr1
 */
public class EmptyLayout implements Layout
{
	// Constants
	public static final EmptyLayout Instance = new EmptyLayout();

	/**
	 * Singleton Constructor
	 */
	private EmptyLayout()
	{
		; // Nothing to do
	}

	@Override
	public void addPainter(Painter aPainter, Anchor aAnchor)
	{
		throw new LogicError("Painters can not be added to EmptyLayout.");
	}

	@Override
	public void addPainter(Painter aPainter)
	{
		throw new LogicError("Painters can not be added to EmptyLayout.");
	}

	@Override
	public List<Painter> getPainters()
	{
		return ImmutableList.of();
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
	public void renderSideNorth(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		; // Nothing to do
	}

	@Override
	public void renderSideSouth(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		; // Nothing to do
	}

	@Override
	public void renderSideWest(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		; // Nothing to do
	}

	@Override
	public void renderSideEast(Graphics2D g2d, Rect aRect, AxisTransform aAxisTransform)
	{
		; // Nothing to do
	}

}

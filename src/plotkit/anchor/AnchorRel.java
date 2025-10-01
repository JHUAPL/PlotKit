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
package plotkit.anchor;

import java.awt.Graphics2D;

import plotkit.Painter;

/**
 * An Anchor type that supports placement of Painters at a relative position to another Painter.
 *
 * @author lopeznr1
 */
public class AnchorRel implements Anchor
{
	// Attributes
	private final Painter refPainter;
	private final double offsetLen;

	public AnchorRel(Painter aPainter, double aOffsetLen)
	{
		refPainter = aPainter;
		offsetLen = aOffsetLen;
	}

	@Override
	public double getPositionX(Graphics2D g2d, boolean isWestSide)
	{
		// TODO: This is a defective implementation.
		// TODO: This assumes the refPainter is positioned at the baseline. That may not be the case.
		int zios_2019Jan15;

		if (isWestSide == true)
			return -(refPainter.getWidthForAxisY(g2d) + offsetLen);
		else
			return refPainter.getWidthForAxisY(g2d) + offsetLen;
	}

	@Override
	public double getPositionY(Graphics2D g2d, boolean isNorthSide)
	{
		// TODO: This is a defective implementation.
		// TODO: This assumes the refPainter is positioned at the baseline. That may not be the case.
		int zios_2019Jan15;

		return refPainter.getHeightForAxisX(g2d) + offsetLen;
	}

}

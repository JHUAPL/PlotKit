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
 * Deprecated class that is too be phased out.
 * <P>
 * Currently the only class dependent on this is LegendPainter from the svp package.
 * <P>
 * A more flexible Anchor should be used to layout the LegendPainter.
 *
 * @author lopeznr1
 */
@Deprecated
public class PostSpacer
{
	// Attributes
	private final Painter refPainter;
	private final double offsetLen;

	public PostSpacer(Painter aPainter, double aOffsetLen)
	{
		refPainter = aPainter;
		offsetLen = aOffsetLen;
	}

	/**
	 * Returns the vertical space that this Spacer would need when it is painted.
	 */
	public double getHeight(Graphics2D g2d)
	{
		return refPainter.getHeightForAxisX(g2d) + offsetLen;
	}

	/**
	 * Returns the horizontal space that this Spacer would need when it is painted.
	 */
	public double getWidth(Graphics2D g2d)
	{
		return refPainter.getWidthForAxisY(g2d) + offsetLen;
	}

}

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

/**
 * An Anchor type that supports placement of Painters at a constant fixed distance from the baseline.
 *
 * @author lopeznr1
 */
public class AnchorFixed implements Anchor
{
	// Constants
	/** Anchor that places the Painter at the baseline of the plot. */
	public static AnchorFixed Base = new AnchorFixed(0.0);

	// Attributes
	private final double offset;

	public AnchorFixed(double aOffset)
	{
		offset = aOffset;
	}

	@Override
	public double getPositionX(Graphics2D g2d, boolean isWestSide)
	{
		if (isWestSide == true)
			return -offset;
		else
			return offset;
	}

	@Override
	public double getPositionY(Graphics2D g2d, boolean isNorthSide)
	{
		if (isNorthSide == true)
			return -offset;
		else
			return offset;
	}

}

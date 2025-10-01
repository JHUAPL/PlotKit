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
 * Interface that describes how a Painter should be placed (anchored) within a layout.
 *
 * @author lopeznr1
 */
public interface Anchor
{
	/**
	 * Returns where the Painter should be positioned relative to the plot's baseline.
	 *
	 * @param g2d
	 *    The relevant graphics context.
	 * @param isWestSide
	 */
	public double getPositionX(Graphics2D g2d, boolean isWestSide);

	/**
	 * Returns where the Painter should be positioned relative to the plot's baseline.
	 *
	 * @param g2d
	 *    The relevant graphics context.
	 * @param isNorthSide
	 */
	public double getPositionY(Graphics2D g2d, boolean isNorthSide);

}

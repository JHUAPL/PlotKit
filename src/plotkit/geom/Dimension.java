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
package plotkit.geom;

/**
 * Immutable class that defines the dimension (width and height) of some object.
 * <P>
 * Unlike {@link java.awt.Dimension} this object is immutable and does not expose it's field members.
 *
 * @author lopeznr1
 */
public class Dimension
{
	// State vars
	private final double width;
	private final double height;

	public Dimension(double aWidth, double aHeight)
	{
		width = aWidth;
		height = aHeight;
	}

	/**
	 * Returns the width of the dimension.
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * Returns the height of the dimension.
	 */
	public double getHeight()
	{
		return height;
	}
}

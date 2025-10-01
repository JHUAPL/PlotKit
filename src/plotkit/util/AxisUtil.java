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
package plotkit.util;

import java.awt.Graphics2D;
import java.util.List;

import plotkit.Painter;

/**
 * Collection of utility methods used to work with plot axis.
 *
 * @author lopeznr1
 */
public class AxisUtil
{
	/**
	 * Utility method to calculate the number of pixels that should be reserved to render the Y-Axis
	 *
	 * @param g2d
	 *    Graphics context of where to render the axis.
	 * @param aPainterList
	 *    The list of Painters that will be used to draw the x-axis.
	 */
	public static double calcHeightForAxisX(Graphics2D g2d, List<Painter> aPainterList)
	{
		double maxH = 0;
		for (Painter aPainter : aPainterList)
		{
			double tmpH = aPainter.getHeightForAxisX(g2d);
			if (tmpH > maxH)
				maxH = tmpH;
		}

		return maxH;
	}

	/**
	 * Utility method to calculate the number of pixels that should be reserved to render the Y-Axis
	 *
	 * @param g2d
	 *    Graphics context of where to render the axis.
	 * @param aPainterList
	 *    The list of Painters that will be used to draw the y-axis.
	 */
	public static double calcWidthForAxisY(Graphics2D g2d, List<Painter> aPainterList)
	{
		double maxW = 0;
		for (Painter aPainter : aPainterList)
		{
			double tmpW = aPainter.getWidthForAxisY(g2d);
			if (tmpW > maxW)
				maxW = tmpW;
		}

		return maxW;
	}

}

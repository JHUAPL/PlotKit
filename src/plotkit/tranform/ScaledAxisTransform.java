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
package plotkit.tranform;

import com.google.common.collect.Range;

import plotkit.AxisTransform;

/**
 * AxisTransform that defines a bounded linear conversion of plot space to axis space.
 *
 * @author lopeznr1
 */
public class ScaledAxisTransform implements AxisTransform
{
	// Attributes
	private final double minVal;
	private final double maxVal;
	private final double maxAxisVal;

	/**
	 * Constructor
	 *
	 * @param aRange
	 *    The range of values covered by the axis.
	 * @param aMaxAxisVal
	 *    The maximum possible axis value. The minimum possible axis value is assumed to be zero.
	 */
	public ScaledAxisTransform(Range<Double> aRange, double aMaxAxisVal)
	{
		minVal = aRange.lowerEndpoint();
		maxVal = aRange.upperEndpoint();
		maxAxisVal = aMaxAxisVal;
	}

	@Override
	public double getAxisValForPlotVal(double aPlotVal)
	{
		double range = maxVal - minVal;
		double tmpVal = (aPlotVal - minVal) / range;

		return tmpVal * maxAxisVal;
	}

	@Override
	public double getPlotValForAxisVal(double aAxisVal)
	{
		double delta = aAxisVal / (maxAxisVal + 0.0);

		double range = maxVal - minVal;
		return minVal + range * delta;
	}

}

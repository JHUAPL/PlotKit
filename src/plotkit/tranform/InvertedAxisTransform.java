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

import plotkit.AxisTransform;

/**
 * Wrapper AxisTransform that will mark this AxisTransform as one that should be displayed in an inverted fashion.
 * <P>
 * See the mehod {@link #isInverted()} for details.
 *
 * @author lopeznr1
 */
public class InvertedAxisTransform implements AxisTransform
{
	// Attributes
	private final AxisTransform refAT;
	private final double maxAxisVal;

	public InvertedAxisTransform(AxisTransform aAxisTransform, double aMaxAxisVal)
	{
		refAT = aAxisTransform;
		maxAxisVal = aMaxAxisVal;
	}

	@Override
	public double getAxisValForPlotVal(double aPlotVal)
	{
		return refAT.getAxisValForPlotVal(aPlotVal);
//		double tmpAxisVal = refAT.getAxisValForPlotVal(aPlotVal);
//		return maxAxisVal - tmpAxisVal;
	}

	@Override
	public double getPlotValForAxisVal(double aAxisVal)
	{
		return refAT.getPlotValForAxisVal(aAxisVal);
//		double tmpAxisVal = maxAxisVal - aAxisVal;
//		return refAT.getPlotValForAxisVal(tmpAxisVal);
	}

	@Override
	public boolean isInverted()
	{
		return !refAT.isInverted();
	}

}

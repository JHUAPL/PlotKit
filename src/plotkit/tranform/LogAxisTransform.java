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
import plotkit.misc.LogicError;

/**
 * Implementation of {@link AxisTransform} where the axis will utilize a logarithmic scale.
 *
 * @author lopeznr1
 */
public class LogAxisTransform implements AxisTransform
{
	// Attributes
	private final double minDV;
	private final double maxDV;
	private final double scaleFact;
//	private final double scrollAmt;

	private final double maxLV;

	/**
	 * Standard Constructor
	 *
	 * @param aMinDV
	 *    The minimum data value
	 * @param aMaxDV
	 *    The maximum data value
	 * @param aScaleFact
	 *    The scalar factor
	 * @param aScrollAmt
	 *    The scroll amount (currently not used).
	 */
	public LogAxisTransform(double aMinDV, double aMaxDV, double aScaleFact, double aScrollAmt)
	{
		minDV = aMinDV;
		maxDV = aMaxDV;
		scaleFact = aScaleFact;
//		scrollAmt = aScrollAmt;

		maxLV = Math.log(maxDV / minDV);
	}

	@Override
	public double getAxisValForPlotVal(double aTmpDV)
	{
		double tmpLV = Math.log(aTmpDV / minDV);
		double retAV = ((tmpLV / maxLV) * (maxDV - minDV)) / scaleFact;
		return retAV;
	}

	@Override
	public double getPlotValForAxisVal(double aAxisVal)
	{
		int zios_2020Sep24; // Finish logic
		throw new LogicError("Not implemented....");
//		double retPV = Math.log(aA)
//		return
	}

}

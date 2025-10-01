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
package plotkit.cadence;

import java.util.Iterator;

/**
 * Iterator that returns values (in the model space) which step over the (model space) range by at a logarithmic
 * cadence.
 *
 * @author lopeznr1
 */
public class ModelLogIterator implements Iterator<Double>
{
	// Attributes
	private final double minDV;
	private final double maxDV;
	private final int numSteps;

	// State vars
	private int currStep;

	double deltaPV;
	double currPV;

	/**
	 * Standard Constructor
	 *
	 * @param aMinDV
	 *    Minimum data value
	 * @param aMaxDV
	 *    Maximum data value
	 * @param aNumSteps
	 *    Number of data points (ticks) to provide
	 */
	public ModelLogIterator(double aMinDV, double aMaxDV, int aNumSteps)
	{
		minDV = aMinDV;
		maxDV = aMaxDV;
		numSteps = aNumSteps;

		currStep = 0;

		// Transform to the "plotting" domain
//		double minPV = Math.log(minVal / minVal);
		double minPV = 0.0;
		double maxPV = Math.log(maxDV / minDV);
		deltaPV = (maxPV - minPV) / (aNumSteps - 1);

		currPV = minPV;
		if (numSteps == 1)
			currPV = minPV + ((maxPV - minPV) / 2.0);
	}

	@Override
	public boolean hasNext()
	{
		if (currStep < numSteps)
			return true;

		return false;
	}

	@Override
	public Double next()
	{
		currStep++;

		double retVal = Math.exp(currPV) * minDV;
		currPV += deltaPV;

		return retVal;
	}

}

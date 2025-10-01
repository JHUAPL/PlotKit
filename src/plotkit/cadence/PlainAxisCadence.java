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

import plotkit.AxisTransform;
import plotkit.misc.LogicError;

/**
 * Cadence where the ticks occur at regular fixed beat over the axis space.
 *
 * @author lopeznr1
 */
public class PlainAxisCadence implements Cadence
{
	// Attributes
	private final int beat;
	private final int valMark;

	/**
	 * @see PlainAxisCadence
	 *
	 * @param aBeat
	 *    The spacing between ticks (in axis space).
	 * @param aValMark
	 *    The value (in axis space) which will be used to align the other ticks.
	 */
	public PlainAxisCadence(int aBeat, int aValMark)
	{
		beat = aBeat;
		valMark = aValMark;

		// Insanity checks
		if (beat <= 0)
			throw new LogicError("Invalid args: " + beat);
	}

	/**
	 * Returns the value (in axis units) which is used to align the other ticks.
	 */
	public int getAlignValue()
	{
		return valMark;
	}

	/**
	 * Returns the spacing (in axis units) of each tick.
	 */
	public int getBeat()
	{
		return beat;
	}

	@Override
	public Iterator<Double> getIter(AxisTransform aAxisTransform, double aAxisBeg, double aAxisEnd)
	{
		return new AxisStepIterator(aAxisTransform, beat, aAxisBeg, aAxisEnd, valMark);
	}

	@Override
	public String toString()
	{
		return "[PAC: " + beat + ", " + valMark + "]";
	}
}

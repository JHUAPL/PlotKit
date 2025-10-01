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
 * Interface that defines how to iterate over a Axis. The Cadence is used to define where "ticks" should occur on an
 * axis.
 *
 * @author lopeznr1
 */
public interface Cadence
{
	// Constants
	public static final Cadence Invalid = new InvalidCadence();

	/**
	 * Returns an iterator to allow traversal of the specified AxisTransform according to this Cadence's specification.
	 *
	 * @param aAxisTransform
	 *    AxisTranform to translate between plot (model) coordinates and axis (pixel) coordinates.
	 * @param aAxisBeg
	 *    The smallest value on the interval to be visited. Units are in axis space (pixels).
	 * @param aAxisEnd
	 *    The largest value on the interval to be visited. Units are in axis space (pixels).
	 */
	public Iterator<Double> getIter(AxisTransform aAxisTransform, double aAxisBeg, double aAxisEnd);

	/**
	 * Implementation of {@link Cadence} that is defined as the "invalid" cadence.
	 *
	 * @author lopeznr1
	 */
	class InvalidCadence implements Cadence
	{
		/** Singleton Constructor */
		private InvalidCadence()
		{
			; // Nothing to do
		}

		@Override
		public Iterator<Double> getIter(AxisTransform aAxisTransform, double aAxisBeg, double aAxisEnd)
		{
			throw new LogicError("Invalid Cadence can not be iterated over!");
		}

	}
}

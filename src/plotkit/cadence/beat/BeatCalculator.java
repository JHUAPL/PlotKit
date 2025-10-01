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
package plotkit.cadence.beat;

import plotkit.AxisTransform;

/**
 * Interface that defines the dynamic beat that should be used for the specified AxisTransform.
 *
 * @author lopeznr1
 */
public interface BeatCalculator
{

	/**
	 * Returns the dynamic beat that should be used for the specified AxisTransform.
	 *
	 * @param aAxisTransform
	 * @return A dynamic beat that is appropriate for the specified AxisTransform.
	 */
	public double calcDynamicBeat(AxisTransform aAxisTransform);

}

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

/**
 * Collection of miscellaneous utility methods that have not been properly organized.
 *
 * @author lopeznr1
 */
public class MiscUtil
{

	/**
	 * Eventually this method should go away. Please use: Guava ver 21 and use the method Doubles.constrainToRange()
	 * <P>
	 * Or update to guava ver21
	 */
	public static double clampToRange(double aMinVal, double aMaxVal, double aVal)
	{
		int zios_move; // We should no longer need this utility method.

		if (aVal < aMinVal)
			return aMinVal;

		if (aVal > aMaxVal)
			return aMaxVal;

		return aVal;
	}

}

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
package plotkit.text;

/**
 * Interface that allows the corresponding text to a specific value to be retrieved.
 * <P>
 * Implementors of this class should be immutable.
 * <P>
 * The model coordinate is defined as the native units of the plot.
 *
 * @author lopeznr1
 */
public interface TextProvider
{
	/**
	 * Returns the nominal text to use for the X-Axis.
	 * <P>
	 * The nominal text is the typical (or largest) string that will be returned by this TickTextProvider. This text
	 * should be used to compute the space needed when calculating the area needed to properly render the X-Axis.
	 */
	public String getNominalTextForX();

	/**
	 * Returns the nominal text to use for the Y-Axis.
	 * <P>
	 * The nominal text is the typical (or largest) string that will be returned by this TickTextProvider. This text
	 * should be used to compute the space needed when calculating the area needed to properly render the Y-Axis.
	 */
	public String getNominalTextForY();

	/**
	 * Method which returns the corresponding text to the specified (model coordinates) position on the X-Axis.
	 */
	public String getTextForX(double aMX);

	/**
	 * Method which returns the corresponding text to the specified (model coordinates) position on the Y-Axis.
	 */
	public String getTextForY(double aMY);
}

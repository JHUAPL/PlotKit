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
package plotkit;

/**
 * Interface that defines the transformation from axis (pixel) coordinates to plot (model) coordinates.
 * <P>
 * The axis coordinates are defined in pixel units.
 * <P>
 * The plot coordinates are defined in model units.
 *
 * @author lopeznr1
 */
public interface AxisTransform
{
	/**
	 * Method which returns the axis position corresponding to the model position.
	 */
	public double getAxisValForPlotVal(double aPlotVal);

	/**
	 * Method which returns the model position corresponding to the axis position.
	 */
	public double getPlotValForAxisVal(double aAxisVal);

	/**
	 * Method which describes if the Axis should be rendered in an inverted fashion.
	 * <P>
	 * For the x-axis the standard rendering is increasing from left to right.
	 * <P>
	 * For the y-axis the standard rendering is increasing from bottom to top.
	 * <P>
	 * TODO: Consider renaming this as showAsInverted()
	 * <P>
	 * TODO: If this is true perhaps our transform functions should actually return inverted values - if so then the
	 * AxisTransform must be aware of the actual maxAxisVal. Also the Cadence logic will need to be changed to support
	 * stepping in reverse rather than throwing a LogicError. Currently as it is, all rendering logic has to manually
	 * perform the inversion. This seems tedious and error prone... but perhaps this is really not the case. The
	 * rendering logic still need to be retrofitted to account for this design change.
	 */
	public default boolean isInverted()
	{
		int zios_clean;
		return false;
	}

}

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
import java.util.List;

import com.google.common.collect.*;

import plotkit.AxisTransform;
import plotkit.misc.LogicError;

/**
 * Cadence where the ticks occur at well defined fixed positions (model space).
 * <P>
 * The position of of ticks will be specified at construction time.
 *
 * @author lopeznr1
 */
public class FixedModelCadence implements Cadence
{
	// Attributes
	private final ImmutableList<Double> modelValueList;

	/**
	 * @see FixedModelCadence
	 *
	 * @param aModelValueList
	 *    The list of model values where ticks should be placed.
	 */
	public FixedModelCadence(List<Double> aModelValueList)
	{
		modelValueList = ImmutableList.copyOf(aModelValueList);

		// Insanity checks
		if (modelValueList.isEmpty() == true)
			throw new LogicError("Invalid args -> Empty List: modelValueList");
	}

	/**
	 * Returns the frequency of ticks (pixel space). Ex: Return value of 250 will mean there will be ticks every 250
	 * pixels.
	 */
	public ImmutableList<Double> getModelValues()
	{
		return modelValueList;
	}

	@Override
	public Iterator<Double> getIter(AxisTransform aAxisTransform, double aAxisBeg, double aAxisEnd)
	{
		// Compute the model space range corresponding to the axis space (range).
		double plotBeg = aAxisTransform.getPlotValForAxisVal(aAxisBeg);
		double plotEnd = aAxisTransform.getPlotValForAxisVal(aAxisEnd);

		return Iterators.filter(modelValueList.iterator(), Range.closed(plotBeg, plotEnd));
	}

	@Override
	public String toString()
	{
		return "[FMC items: " + modelValueList.size() + "]";
	}
}

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
package plotkit.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * TemporalAdjuster that contains a list of child TemporalAdjusters.
 * <P>
 * This MultiTemporalAdjuster will apply the child TemporalAdjusters in an ordered fashion.
 * <P>
 * This class really should be part of the java library. There are a number of API methods that take a TemporalAdjuster
 * as an argument - and being able to pass a combined TemporalAdjuster would be a useful feature.
 *
 * @author lopeznr1
 */
public class MultiTemporalAdjuster implements TemporalAdjuster
{
	// Attributes
	private ImmutableList<TemporalAdjuster> childList;

	/**
	 * Constructor which takes a list of TemporalAdjusters
	 */
	public MultiTemporalAdjuster(List<TemporalAdjuster> aChildList)
	{
		childList = ImmutableList.copyOf(aChildList);
	}

	/**
	 * Constructor which takes an array of TemporalAdjusters
	 *
	 * @see MultiTemporalAdjuster
	 */
	public MultiTemporalAdjuster(TemporalAdjuster... aChildArr)
	{
		childList = ImmutableList.copyOf(aChildArr);
	}

	/**
	 * Returns the list of child TemporalAdjusters
	 */
	public ImmutableList<TemporalAdjuster> getChildTemporalAdjusterList()
	{
		return childList;
	}

	@Override
	public Temporal adjustInto(Temporal aTemporal)
	{
		Temporal retTemporal = aTemporal;
		for (TemporalAdjuster aTemporalAdjuster : childList)
			retTemporal = retTemporal.with(aTemporalAdjuster);

		return retTemporal;
	}

}

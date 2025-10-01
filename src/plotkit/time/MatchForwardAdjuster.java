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

import java.time.Duration;
import java.time.temporal.*;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import plotkit.misc.LogicError;

/**
 * TemporalAdjuster that will adjust the appropriate TemporalField so that it matches one of the values in the
 * matchList. Adjustments will be made such that the next item in the matchList will be selected. If there is no value
 * that is greater in the matchList then the first item will be selected. Hence the list will "loop" back.
 *
 * @author lopeznr1
 */
public class MatchForwardAdjuster implements TemporalAdjuster
{
	// Attributes
	private final TemporalField modTF;
	private final ImmutableList<Integer> matchList;

	/**
	 * @param aModTF
	 *    The TemporalField that will be adjusted.
	 * @param aMatchList
	 *    An ordered list of possible values to match against. After surpassing the very last value the TemporalField
	 *    will be set to the first value.
	 */
	public MatchForwardAdjuster(TemporalField aModTF, List<Integer> aMatchList)
	{
		modTF = aModTF;
		matchList = ImmutableList.copyOf(aMatchList);

		// Ensure the list in a nonempty ordered list
		if (matchList.isEmpty() == true)
			throw new LogicError("At least one item must be specified in the matchList");

		if (Ordering.natural().isOrdered(matchList) == false)
			throw new LogicError("Items in the matchList must be ordered");
	}

	/**
	 * @param aModTF
	 *    The TemporalField that will be adjusted.
	 * @param aMatchArr
	 *    An ordered array of possible values to match against. After surpassing the very last value the TemporalField
	 *    will be set to the first value.
	 */
	public MatchForwardAdjuster(TemporalField aModTF, Integer... aMatchArr)
	{
		this(aModTF, Arrays.asList(aMatchArr));
	}

	/**
	 * Returns the nominal Duration of the advance amount.
	 * <P>
	 * The nominal Duration is the typical duration this TemporalAdjuster will adjust the specified Temporal.
	 * <P>
	 * Currently this is calculated as the base TemporalUnit's duration divided by the number of items in the matchList
	 *
	 * @see Duration
	 */
	public Duration getNominalDuration()
	{
		TemporalUnit rangeTU = modTF.getRangeUnit();

		// If the matchList has only 1 item then the nominal duration is simply the duration of the TemporalUnit.
		if (matchList.size() == 1)
			return rangeTU.getDuration();

		// Assume the duration is evenly split up along the base TemporalUnit
		long numSec = rangeTU.getDuration().getSeconds() / matchList.size();
		return Duration.ofSeconds(numSec);
	}

	@Override
	public Temporal adjustInto(Temporal aTemporal)
	{
		// Retrieve the original value
		int currVal = aTemporal.get(modTF);

		int newVal = 1;
		for (int aMatchVal : matchList)
		{
			// Nothing needs to be adjusted
			if (currVal == aMatchVal)
				return aTemporal;

			if (currVal > aMatchVal)
				continue;

			// Advance forward to the next valid value
			newVal = aMatchVal;
			return aTemporal.with(modTF, newVal);
		}

		TemporalUnit jumpTU = modTF.getRangeUnit();
		return aTemporal.with(modTF, matchList.get(0)).plus(1, jumpTU);
	}

}

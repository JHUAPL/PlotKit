package plotkit.cadence.beat;

import plotkit.AxisTransform;
import plotkit.misc.LogicError;

import com.google.common.collect.Range;

/**
 * BeatCalculator where the beat will occur within the defined specified (TickSpacingRange) range.
 * <P>
 * The actual beat will be defined such that it is a power of 2 of the specified nominal beat.
 */
public class AutoBeatCalculator implements BeatCalculator
{
	// Attributes
	private final Range<Double> tickSpacingRange;
	private final double nomBeat;

	/**
	 * @see AutoBeatCalculator
	 *
	 * @param aTickSpacingRange
	 *        Defines the range in axis space of the spacing between any 2 ticks. The lower bound is the minimum number
	 *        of pixels to allow the spacing between any 2 ticks. The upper bound is maximum number of pixels to allow
	 *        the spacing between any 2 ticks. Ticks will be spaced within this range.
	 * @param aNomBeat
	 *        The nominal beat in model range. This nominal beat will be adjusted to satisfy the constraints of
	 *        aTickSpacingRange. The resulting adjustment is known as the "dynamic beat" and is obtained via
	 *        {@link #calcDynamicBeat(AxisTransform)}.
	 */
	public AutoBeatCalculator(Range<Double> aTickSpacingRange, double aNomBeat)
	{
		tickSpacingRange = aTickSpacingRange;
		nomBeat = aNomBeat;

		// Insanity checks
		if (tickSpacingRange.isEmpty())
			throw new LogicError("Invalid args -> tickSpacingRange must not be empty.");
		if (nomBeat <= 0)
			throw new LogicError("Invalid args -> nomBeat: " + nomBeat);
	}

	/**
	 * Returns the nominal beat.
	 */
	public double getNominalBeat()
	{
		return nomBeat;
	}

	/**
	 * Returns the valid range (in axis space) over which ticks are allowed.
	 *
	 * @return
	 */
	public Range<Double> getTickSpacingRange()
	{
		return tickSpacingRange;
	}

	@Override
	public double calcDynamicBeat(AxisTransform aAxisTransform)
	{
		double minPixVal = tickSpacingRange.lowerEndpoint();
		double maxPixVal = tickSpacingRange.upperEndpoint();

		double nomAxisBeg = 0;
		double pValBeg = aAxisTransform.getPlotValForAxisVal(nomAxisBeg);
		double pValEndMin = aAxisTransform.getPlotValForAxisVal(nomAxisBeg + minPixVal);
		double pValEndMax = aAxisTransform.getPlotValForAxisVal(nomAxisBeg + maxPixVal);
		double pValDeltaMin = pValEndMin - pValBeg;
		double pValDeltaMax = pValEndMax - pValBeg;

		double retBeat = nomBeat;
		if (retBeat > pValDeltaMax)
		{
			while (retBeat > pValDeltaMax)
				retBeat = retBeat / 2;
		}
		else if (retBeat < pValDeltaMin)
		{
			while (retBeat < pValDeltaMin)
				retBeat = retBeat * 2;
		}

		return retBeat;
	}

}

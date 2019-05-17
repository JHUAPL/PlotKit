package plotkit.cadence;

import java.util.Iterator;

import plotkit.AxisTransform;
import plotkit.cadence.beat.BeatCalculator;
import plotkit.misc.LogicError;

/**
 * Cadence where the ticks occur at a fixed beat over the model space.
 * <P>
 * The actual beat will be a multiple of the (dynamic) beat calculated from the specified BeatCalculator.
 */
public class DynamicModelCadence implements Cadence
{
	// Attributes
	private final BeatCalculator refBeatCalc;
	private final double multiFact;
	private final double valMark;

	/**
	 *
	 * @param aBeatCalc
	 *        The BeatCalculator for which ticks will occur at a multiple of.
	 * @param aMultiFact
	 *        The multiplier for the number of ticks to place. For example if this value is 4 then there will be 4
	 *        additional ticks for each tick defined in the DynamicBeatCalculator.
	 * @param aValMark
	 *        The value (in model space) which will be used to align the other ticks.
	 */
	public DynamicModelCadence(BeatCalculator aBeatCalc, double aMultiFact, double aValMark)
	{
		refBeatCalc = aBeatCalc;
		multiFact = aMultiFact;
		valMark = aValMark;

		// Insanity checks
		if (refBeatCalc == null)
			throw new LogicError("Invalid args -> dynamicBeatCalc == null.");
		if (multiFact <= 0)
			throw new LogicError("Invalid args -> multiFact: " + multiFact);
		if (Double.isNaN(valMark) == true)
			throw new LogicError("Invalid args -> valMark: " + valMark);

	}

	/**
	 * Returns the reference BeatCalculator.
	 */
	public BeatCalculator getBeatCalculator()
	{
		return refBeatCalc;
	}

	/**
	 * Returns the value (in model units) which is used to align the other ticks.
	 */
	public double getAlignValue()
	{
		return valMark;
	}

	/**
	 * Returns the multiplier for the number of ticks to place.
	 */
	public double getMultiFact()
	{
		return multiFact;
	}

	@Override
	public Iterator<Double> getIter(AxisTransform aAxisTransform, double aAxisBeg, double aAxisEnd)
	{
		// Calculate the dyamic beat to use with aAxisTransform
		double dynamicBeat = refBeatCalc.calcDynamicBeat(aAxisTransform) * (1.0 / multiFact);

		// Compute the model space range corresponding to the axis space (range).
		double xValBeg = aAxisTransform.getPlotValForAxisVal(aAxisBeg);
		double xValEnd = aAxisTransform.getPlotValForAxisVal(aAxisEnd);

		return new ModelStepIterator(dynamicBeat, xValBeg, xValEnd, valMark);
	}

	@Override
	public String toString()
	{
		return "[DMC: " + multiFact + ", " + valMark + "]";
	}

}

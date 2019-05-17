package plotkit.cadence;

import java.util.Iterator;

import plotkit.AxisTransform;
import plotkit.misc.LogicError;

/**
 * Cadence where the ticks occur at regular fixed beat over the model space.
 * <P>
 * The frequency and positioning of ticks will be determined automatically based on the attributes.
 */
public class AutoModelCadence implements Cadence
{
	// Attributes
	private final double tickFreq;
	private final double valMark;
	private final double targBeat;
	private final boolean targBeatB;

	/**
	 * @see AutoModelCadence
	 *
	 * @param aTickFreq
	 *        The frequency of ticks per pixels. Ex: A value of 250 will mean there will be ticks every 250 pixels.
	 * @param aValMark
	 *        The value (in model space) which will be used to align the other ticks.
	 * @param aTargBeat
	 *        The spacing between ticks (in model space). Used only if aTarBeatB == true.
	 * @param aTargBeatB
	 *        Determines if aTargBeat should be used.
	 */
	public AutoModelCadence(double aTickFreq, double aValMark, double aTargBeat, boolean aTargBeatB)
	{
		tickFreq = aTickFreq;
		valMark = aValMark;
		targBeat = aTargBeat;
		targBeatB = aTargBeatB;

		// Insanity checks
		if (tickFreq <= 0)
			throw new LogicError("Invalid args -> tickFreq: " + tickFreq);
		if (Double.isNaN(valMark) == true)
			throw new LogicError("Invalid args -> alignVal: " + valMark);
		if (targBeatB == true && (Double.isNaN(aTargBeat) == true || aTargBeat <= 0))
			throw new LogicError("Invalid args -> aTargBeat: " + aTargBeat);
	}

	/**
	 * Returns the value (in model units) which is used to align the other ticks.
	 */
	public double getAlignValue()
	{
		return valMark;
	}

	/**
	 * Returns the frequency of ticks (pixel space). Ex: Return value of 250 will mean there will be ticks every 250
	 * pixels.
	 */
	public double getTickFreq()
	{
		return tickFreq;
	}

	/**
	 * Returns the spacing (in model units) of each tick. This will only be used if targBeatB == true.
	 */
	public double getTargBeat()
	{
		return targBeat;
	}

	/**
	 * Returns whether we should utilize the targBeat when calculating the auto ticks.
	 */
	public boolean getTargBeatB()
	{
		return targBeatB;
	}

	@Override
	public Iterator<Double> getIter(AxisTransform aAxisTransform, double aAxisBeg, double aAxisEnd)
	{
		// Determine the number of ticks needed
		double tmpL = aAxisEnd - aAxisBeg;
		int numTicks = (int) Math.round(tmpL / tickFreq);

		// Compute the model space range corresponding to the axis space (range).
		double plotBeg = aAxisTransform.getPlotValForAxisVal(aAxisBeg);
		double plotEnd = aAxisTransform.getPlotValForAxisVal(aAxisEnd);

		// Calculate the beat
		double beat = 0;
		if (targBeatB == false)
		{
			beat = (plotEnd - plotBeg) / numTicks;
			if (beat > 1)
				beat = Math.round(beat);
		}
		else
		{
			beat = calcBeat(plotBeg, plotEnd, numTicks, targBeat);
		}

		return new ModelStepIterator(beat, plotBeg, plotEnd, valMark);
	}

	@Override
	public String toString()
	{
		return "[AMC: " + tickFreq + ", " + valMark + "]";
	}

	/**
	 * Utility helper method to compute the beat that should be used.
	 * <P>
	 * The computation is based on:
	 * <UL>
	 * <LI>The specified range of values
	 * <LI>The number of desired ticks
	 * <LI>A target beat
	 * </UL>
	 */
	private static double calcBeat(double aValBeg, double aValEnd, int aNumTicks, double aTargBeat)
	{
		// Insanity check
		if (aTargBeat <= 0)
			throw new LogicError("Invalid parms. aTargBeat: " + aTargBeat);

		// Calculate the default rawBeat
		double rawBeat = (aValEnd - aValBeg) / aNumTicks;

		// Set the actual beat so that it is a function of X^2N
		double preBeat = aTargBeat;
		double actBeat = aTargBeat;
		if (actBeat < rawBeat)
		{
			while (actBeat < rawBeat)
			{
				preBeat = actBeat;
				actBeat *= 2;
			}
		}
		else
		{
			while (actBeat > rawBeat)
			{
				preBeat = actBeat;
				actBeat /= 2;
			}
		}

		if (Math.abs(rawBeat - preBeat) < Math.abs(actBeat - rawBeat))
			actBeat = preBeat;

		return actBeat;
	}

}

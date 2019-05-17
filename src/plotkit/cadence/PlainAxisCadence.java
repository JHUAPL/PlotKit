package plotkit.cadence;

import java.util.Iterator;

import plotkit.AxisTransform;
import plotkit.misc.LogicError;

/**
 * Cadence where the ticks occur at regular fixed beat over the axis space.
 */
public class PlainAxisCadence implements Cadence
{
	// Attributes
	private final int beat;
	private final int valMark;

	/**
	 * @see PlainAxisCadence
	 *
	 * @param aBeat
	 *        The spacing between ticks (in axis space).
	 * @param aValMark
	 *        The value (in axis space) which will be used to align the other ticks.
	 */
	public PlainAxisCadence(int aBeat, int aValMark)
	{
		beat = aBeat;
		valMark = aValMark;

		// Insanity checks
		if (beat <= 0)
			throw new LogicError("Invalid args: " + beat);
	}

	/**
	 * Returns the value (in axis units) which is used to align the other ticks.
	 */
	public int getAlignValue()
	{
		return valMark;
	}

	/**
	 * Returns the spacing (in axis units) of each tick.
	 */
	public int getBeat()
	{
		return beat;
	}

	@Override
	public Iterator<Double> getIter(AxisTransform aAxisTransform, double aAxisBeg, double aAxisEnd)
	{
		return new AxisStepIterator(aAxisTransform, beat, aAxisBeg, aAxisEnd, valMark);
	}

	@Override
	public String toString()
	{
		return "[PAC: " + beat + ", " + valMark + "]";
	}
}

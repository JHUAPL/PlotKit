package plotkit.cadence;

import java.util.Iterator;

import plotkit.AxisTransform;
import plotkit.misc.LogicError;

/**
 * Implementation of {@link Cadence} where the ticks occur at regular fixed beat over the model space.
 * 
 * @author lopeznr1
 */
public class PlainModelCadence implements Cadence
{
	// Attributes
	private final double beat;
	private final double valMark;

	/**
	 * @see PlainModelCadence
	 *
	 * @param aBeat
	 *        The spacing between ticks (in model space).
	 * @param aValMark
	 *        The value (in model space) which will be used to align the other ticks.
	 */
	public PlainModelCadence(double aBeat, double aValMark)
	{
		beat = aBeat;
		valMark = aValMark;

		// Insanity checks
		if (beat <= 0)
			throw new LogicError("Invalid args -> beat: " + beat);
		if (Double.isNaN(valMark) == true)
			throw new LogicError("Invalid args -> alignVal: " + valMark);
	}

	/**
	 * Returns the value (in model units) which is used to align the other ticks.
	 */
	public double getAlignValue()
	{
		return valMark;
	}

	/**
	 * Returns the spacing (in model units) of each tick.
	 */
	public double getBeat()
	{
		return beat;
	}

	@Override
	public Iterator<Double> getIter(AxisTransform aAxisTransform, double aAxisBeg, double aAxisEnd)
	{
		// Compute the model space range corresponding to the axis space (range).
		double xValBeg = aAxisTransform.getPlotValForAxisVal(aAxisBeg);
		double xValEnd = aAxisTransform.getPlotValForAxisVal(aAxisEnd);

		return new ModelStepIterator(beat, xValBeg, xValEnd, valMark);

	}

	@Override
	public String toString()
	{
		return "[PMC: " + beat + ", " + valMark + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(beat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valMark);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlainModelCadence other = (PlainModelCadence) obj;
		if (Double.doubleToLongBits(beat) != Double.doubleToLongBits(other.beat))
			return false;
		if (Double.doubleToLongBits(valMark) != Double.doubleToLongBits(other.valMark))
			return false;
		return true;
	}

}

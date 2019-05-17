package plotkit.cadence;

import java.util.Iterator;

import plotkit.misc.LogicError;

/**
 * Iterator that returns values (in the model space) which step over the (model space) range by a constant delta value.
 */
public class ModelStepIterator implements Iterator<Double>
{
	// Attributes
	private final double beat;
	private final double valBeg;
	private final double valEnd;
	private final double valMark;

	// State vars
	private double valCurr;

	/**
	 * Forms an iterator that returns values (in model space) that range from aValBeg to aValEnd. The values will be
	 * evenly spaced by the specified beat. Note the values aValBeg and aValEnd may not be returned if they are not a
	 * multiple of the specified beat and the specified alignment value.
	 *
	 * @param aBeat
	 *        The spacing between ticks (model space).
	 * @param aValBeg
	 *        The smallest value (in model space) that appears in the view.
	 * @param aValEnd
	 *        The largest value (in model space) that appears in the view. Values will be returned until this value has
	 *        been reached (or surpassed).
	 * @param aValMark
	 *        The value (in model space) which will be used to align the other ticks.
	 */
	public ModelStepIterator(double aBeat, double aValBeg, double aValEnd, double aValMark)
	{
		beat = aBeat;
		valBeg = aValBeg;
		valEnd = aValEnd;
		valMark = aValMark;

		// Insanity check
		if (valEnd < valBeg && beat >= 0)
			throw new LogicError("Invalid parameters. valBeg: " + valBeg + " valEnd: " + valEnd + " beat: " + beat);
		if (valEnd > valBeg && beat <= 0)
			throw new LogicError("Invalid parameters. valBeg: " + valBeg + " valEnd: " + valEnd + " beat: " + beat);
		if (Double.isFinite(aValBeg) == false || Double.isFinite(aValEnd) == false)
			throw new LogicError("Invalid parameters. Must be finite. valBeg: " + valBeg + " valEnd: " + valEnd);

		// Initialize valCurr to the first value that should be returned.
		// It should be in the rang: [valBeg, valBeg + beat]
		valCurr = Math.ceil(valBeg / beat) * beat;

		// Adjust the valCurr to be aligned with the alignment value (valMark)
		double offSet = valMark - (Math.ceil(valMark / beat) * beat);
		valCurr += offSet;

		// Ensure the first value occurs at or after aValBeg
		if (valCurr < aValBeg)
			next();
	}

	@Override
	public boolean hasNext()
	{
		// We still have items if we have not passed the max value
		return valCurr <= valEnd;
	}

	@Override
	public Double next()
	{
		double retVal = valCurr;

		// Increment to the next tick
		valCurr += beat;

		// Return the corresponding model value
		return retVal;
	}

}

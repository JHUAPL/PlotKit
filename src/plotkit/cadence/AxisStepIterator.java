package plotkit.cadence;

import java.util.Iterator;

import plotkit.AxisTransform;

/**
 * Iterator that returns values (in the model space) which step over the (axis space) range by a constant delta value.
 */
public class AxisStepIterator implements Iterator<Double>
{
	// Attributes
	private final AxisTransform axisTransform;
	private final double beat;
	private final double posBeg;
	private final double posEnd;
	private final double posMark;

	// State vars
	private double posCurr;

	/**
	 * Forms an iterator that returns values (in model space) that range from (axis space) aPosBeg to aPosEnd. The values
	 * will be evenly spaced by the specified beat. Note the model values corresponding to aPosBeg and aPosEnd may not be
	 * returned if they are not a multiple of the specified beat and the specified alignment value.
	 * 
	 * @param aAxisTransform
	 *        AxisTranform to translate between plot (model) coordinates and axis (pixel) coordinates.
	 * @param aBeat
	 *        The spacing between ticks (axis space).
	 * @param aPosBeg
	 *        The smallest position (in axis space) that appears in the view.
	 * @param aPosEnd
	 *        The largest position (in axis space) that appears in the view. Values will be returned until this value has
	 *        been reached (or surpassed).
	 * @param aPosMark
	 *        The position (in axis space) which will be used to align the other ticks.
	 * @param aIsAxisX
	 *        True if we are iterating over the X-Axis rather than Y-Axis.
	 */
	public AxisStepIterator(AxisTransform aAxisTransform, double aBeat, double aPosBeg, double aPosEnd, double aPosMark)
	{
		axisTransform = aAxisTransform;
		beat = aBeat;
		posBeg = aPosBeg;
		posEnd = aPosEnd;
		posMark = aPosMark;

		// Initialize posCurr to the first position that should be returned.
		// It should be in the rang: [posBeg, posBeg + beat]
		posCurr = Math.ceil(posBeg / beat) * beat;

		// Adjust the posCurr to be aligned with the alignment position (posMark)
		double offSet = posMark - (Math.ceil(posMark / beat) * beat);
		posCurr += offSet;
	}

	@Override
	public boolean hasNext()
	{
		// We still have items if we have not passed the max position
		return posCurr <= posEnd;
	}

	@Override
	public Double next()
	{
		double retVal = posCurr;

		// Increment to the next tick
		posCurr += beat;

		// Return the corresponding model value
		return axisTransform.getPlotValForAxisVal(retVal);
	}

}

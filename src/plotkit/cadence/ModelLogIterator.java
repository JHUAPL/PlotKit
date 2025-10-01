package plotkit.cadence;

import java.util.Iterator;

/**
 * Iterator that returns values (in the model space) which step over the (model
 * space) range by at a logarithmic cadence.
 *
 * @author lopeznr1
 */
public class ModelLogIterator implements Iterator<Double>
{
	// Attributes
	private final double minDV;
	private final double maxDV;
	private final int numSteps;

	// State vars
	private int currStep;

	double deltaPV;
	double currPV;

	/**
	 * Standard Constructor
	 *
	 * @param aMinDV    Minimum data value
	 * @param aMaxDV    Maximum data value
	 * @param aNumSteps Number of data points (ticks) to provide
	 */
	public ModelLogIterator(double aMinDV, double aMaxDV, int aNumSteps)
	{
		minDV = aMinDV;
		maxDV = aMaxDV;
		numSteps = aNumSteps;

		currStep = 0;

		// Transform to the "plotting" domain
//		double minPV = Math.log(minVal / minVal);
		double minPV = 0.0;
		double maxPV = Math.log(maxDV / minDV);
		deltaPV = (maxPV - minPV) / (aNumSteps - 1);

		currPV = minPV;
		if (numSteps == 1)
			currPV = minPV + ((maxPV - minPV) / 2.0);
	}

	@Override
	public boolean hasNext()
	{
		if (currStep < numSteps)
			return true;

		return false;
	}

	@Override
	public Double next()
	{
		currStep++;

		double retVal = Math.exp(currPV) * minDV;
		currPV += deltaPV;

		return retVal;
	}

}

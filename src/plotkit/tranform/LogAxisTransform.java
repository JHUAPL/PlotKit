package plotkit.tranform;

import plotkit.AxisTransform;
import plotkit.misc.LogicError;

/**
 * Implementation of {@link AxisTransform} where the axis will utilize a logarithmic scale.
 *
 * @author lopeznr1
 */
public class LogAxisTransform implements AxisTransform
{
	// Attributes
	private final double minDV;
	private final double maxDV;
	private final double scaleFact;
//	private final double scrollAmt;

	private final double maxLV;

	/**
	 * Standard Constructor
	 *
	 * @param aMinDV
	 *        The minimum data value
	 * @param aMaxDV
	 *        The maximum data value
	 * @param aScaleFact
	 *        The scalar factor
	 * @param aScrollAmt
	 *        The scroll amount (currently not used).
	 */
	public LogAxisTransform(double aMinDV, double aMaxDV, double aScaleFact, double aScrollAmt)
	{
		minDV = aMinDV;
		maxDV = aMaxDV;
		scaleFact = aScaleFact;
//		scrollAmt = aScrollAmt;

		maxLV = Math.log(maxDV / minDV);
	}

	@Override
	public double getAxisValForPlotVal(double aTmpDV)
	{
		double tmpLV = Math.log(aTmpDV / minDV);
		double retAV = ((tmpLV / maxLV) * (maxDV - minDV)) / scaleFact;
		return retAV;
	}

	@Override
	public double getPlotValForAxisVal(double aAxisVal)
	{
		int zios_2020Sep24; // Finish logic
		throw new LogicError("Not implemented....");
//		double retPV = Math.log(aA)
//		return
	}

}

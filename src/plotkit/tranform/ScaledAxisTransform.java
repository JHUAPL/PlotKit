package plotkit.tranform;

import plotkit.AxisTransform;

import com.google.common.collect.Range;

/**
 * AxisTransform that defines a bounded linear conversion of plot space to axis space.
 */
public class ScaledAxisTransform implements AxisTransform
{
	// Attributes
	private final double minVal;
	private final double maxVal;
	private final double maxAxisVal;

	/**
	 * Constructor
	 * 
	 * @param aRange
	 *        The range of values covered by the axis.
	 * @param aMaxAxisVal
	 *        The maximum possible axis value. The minimum possible axis value is assumed to be zero.
	 */
	public ScaledAxisTransform(Range<Double> aRange, double aMaxAxisVal)
	{
		minVal = aRange.lowerEndpoint();
		maxVal = aRange.upperEndpoint();
		maxAxisVal = aMaxAxisVal;
	}

	@Override
	public double getAxisValForPlotVal(double aPlotVal)
	{
		double range = maxVal - minVal;
		double tmpVal = (aPlotVal - minVal) / range;

		return tmpVal * maxAxisVal;
	}

	@Override
	public double getPlotValForAxisVal(double aAxisVal)
	{
		double delta = aAxisVal / (maxAxisVal + 0.0);

		double range = maxVal - minVal;
		return minVal + range * delta;
	}

}

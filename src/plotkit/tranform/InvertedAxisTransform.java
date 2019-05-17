package plotkit.tranform;

import plotkit.AxisTransform;

/**
 * Wrapper AxisTransform that will mark this AxisTransform as one that should be displayed in an inverted fashion.
 * <P>
 * See the mehod {@link #isInverted()} for details.
 */
public class InvertedAxisTransform implements AxisTransform
{
	// Attributes
	private final AxisTransform refAT;
	private final double maxAxisVal;

	public InvertedAxisTransform(AxisTransform aAxisTransform, double aMaxAxisVal)
	{
		refAT = aAxisTransform;
		maxAxisVal = aMaxAxisVal;
	}

	@Override
	public double getAxisValForPlotVal(double aPlotVal)
	{
		return refAT.getAxisValForPlotVal(aPlotVal);
//		double tmpAxisVal = refAT.getAxisValForPlotVal(aPlotVal);
//		return maxAxisVal - tmpAxisVal;
	}

	@Override
	public double getPlotValForAxisVal(double aAxisVal)
	{
		return refAT.getPlotValForAxisVal(aAxisVal);
//		double tmpAxisVal = maxAxisVal - aAxisVal;
//		return refAT.getPlotValForAxisVal(tmpAxisVal);
	}

	@Override
	public boolean isInverted()
	{
		return !refAT.isInverted();
	}

}

package plotkit.demo.data;

import plotkit.AxisTransform;

/**
 * Simple AxisTransform where pixels and model units are equivalent.
 */
public class SimpleAxisTransform implements AxisTransform
{
	// Singleton
	public static final SimpleAxisTransform Default = new SimpleAxisTransform();

	/**
	 * Private constructor to enforce Singleton
	 */
	private SimpleAxisTransform()
	{
		; // Nothing to do
	}

	@Override
	public double getAxisValForPlotVal(double aPlotVal)
	{
		return aPlotVal;
	}

	@Override
	public double getPlotValForAxisVal(double aAxisVal)
	{
		return aAxisVal;
	}

}

package plotkit.cadence.beat;

import plotkit.AxisTransform;

/**
 * Interface that defines the dynamic beat that should be used for the specified AxisTransform.
 */
public interface BeatCalculator
{

	/**
	 * Returns the dynamic beat that should be used for the specified AxisTransform.
	 *
	 * @param aAxisTransform
	 * @return A dynamic beat that is appropriate for the specified AxisTransform.
	 */
	public double calcDynamicBeat(AxisTransform aAxisTransform);

}

package plotkit.tranform;

import plotkit.AxisTransform;

/**
 * AxisTransform that defines an open ended linear conversion of plot space to axis space.
 * <P>
 * The conversion is: plotVal = minVal + ((axisVal + scollAmt) * scaleFact)
 */
public class PlainAxisTransform implements AxisTransform
{
	// Attributes
	private final double minVal;
	private final double maxVal;
	private final double scaleFact;
	private final double scrollAmt;

	/**
	 * Constructor
	 * 
	 * @param aMinVal
	 *        The corresponding plot value at the axis origin.
	 * @param aScaleFact
	 *        The scaler value used to convert between axis space and plot space.
	 * @param aScrollAmt
	 *        The amount the axis has been scrolled. This transformation is applied after the scale factor.
	 */
	public PlainAxisTransform(double aMinVal, double aMaxVal, double aScaleFact, double aScrollAmt)
	{
		minVal = aMinVal;
		maxVal = aMaxVal;
		scaleFact = aScaleFact;
		scrollAmt = aScrollAmt;
	}

	/**
	 * Returns a derived PlainAxisTransform but with the specified scale factor.
	 * <P>
	 * An attempt will be made so that the focalVal is maintained at the same position.
	 */
	public PlainAxisTransform deriveWithScaleFactAndFocal(double aScaleFact, double aFocalVal, double aPlotW)
	{
		// Compute the axis position of the focalVal with the current AxisTransform's scaleFact
		double currAxisX = ((aFocalVal - minVal) / scaleFact) - scrollAmt;

		// Compute the axis position of the focalVal with the replacement AxisTransform's scaleFact
		double evalAxisX = ((aFocalVal - minVal) / aScaleFact) - scrollAmt;

		// Set the tarScrollAmt to maintain the focalVal at its current axis position
		double targScrollAmt = scrollAmt + (evalAxisX - currAxisX);

//		// TODO: The code below performs the actual centering of the focalVal
//		// TODO: Do you want to enable this????
//		// Compute the target axis position
//		double pickAxisX = aPlotW / 2.0;
//		double pickScrollAmt = ((aFocalVal - minVal) / aScaleFact) - pickAxisX;
//
//		// Compute the amount we should allow the scroll amount to be shifted
//		double maxShiftAmt = 3;
//		double dScrollAmt = pickScrollAmt - targScrollAmt;
//		if (dScrollAmt > maxShiftAmt)
//			dScrollAmt = maxShiftAmt;
//		else if (dScrollAmt < -maxShiftAmt)
//			dScrollAmt = -maxShiftAmt;
//
//		targScrollAmt += dScrollAmt;
//		// TODO: DONE

		return new PlainAxisTransform(minVal, maxVal, aScaleFact, targScrollAmt);
	}

	/**
	 * Returns a derived PlainAxisTransform but with the specified scale factor.
	 */
	public PlainAxisTransform deriveWithScrollAmt(double aScrollAmt)
	{
		return new PlainAxisTransform(minVal, maxVal, scaleFact, aScrollAmt);
	}

	public PlainAxisTransform deriveWithPlotRange(double aMinVal, double aMaxVal)
	{
		return new PlainAxisTransform(aMinVal, aMaxVal, scaleFact, scrollAmt);
	}

	/**
	 * Returns the "zoom" factor of this PlainAxisTransform.
	 */
	public double getScaleFact()
	{
		return scaleFact;
	}

	/**
	 * Returns the scrollAmt of this PlainAxisTransform.
	 */
	public double getScrollAmt()
	{
		return scrollAmt;
	}

	/**
	 * Returns the maximum plot value
	 */
	public double getMinPlotVal()
	{
		return minVal;

	}

	/**
	 * Returns the maximum plot value
	 */
	public double getMaxPlotVal()
	{
		return maxVal;

	}

	@Override
	public double getAxisValForPlotVal(double aPlotVal)
	{
		return ((aPlotVal - minVal) / scaleFact) - scrollAmt;
	}

	@Override
	public double getPlotValForAxisVal(double aAxisVal)
	{
		return minVal + ((aAxisVal + scrollAmt) * scaleFact);
	}

}

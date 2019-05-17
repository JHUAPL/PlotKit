package plotkit;

/**
 * Interface that defines the transformation from axis (pixel) coordinates to plot (model) coordinates.
 * <P>
 * The axis coordinates are defined in pixel units.
 * <P>
 * The plot coordinates are defined in model units.
 */
public interface AxisTransform
{
	/**
	 * Method which returns the axis position corresponding to the model position.
	 */
	public double getAxisValForPlotVal(double aPlotVal);

	/**
	 * Method which returns the model position corresponding to the axis position.
	 */
	public double getPlotValForAxisVal(double aAxisVal);

	/**
	 * Method which describes if the Axis should be rendered in an inverted fashion.
	 * <P>
	 * For the x-axis the standard rendering is increasing from left to right.
	 * <P>
	 * For the y-axis the standard rendering is increasing from bottom to top.
	 * <P>
	 * TODO: Consider renaming this as showAsInverted()
	 * <P>
	 * TODO: If this is true perhaps our transform functions should actually return inverted values - if so then the
	 * AxisTransform must be aware of the actual maxAxisVal. Also the Cadence logic will need to be changed to support
	 * stepping in reverse rather than throwing a LogicError. Currently as it is, all rendering logic has to manually
	 * perform the inversion. This seems tedious and error prone... but perhaps this is really not the case. The
	 * rendering logic still need to be retrofitted to account for this design change.
	 */
	public default boolean isInverted()
	{
		int zios_clean;
		return false;
	}

}

package plotkit.text;

/**
 * Interface that allows the corresponding text to a specific value to be retrieved.
 * <P>
 * Implementors of this class should be immutable.
 * <P>
 * The model coordinate is defined as the native units of the plot.
 */
public interface TextProvider
{
	/**
	 * Returns the nominal text to use for the X-Axis.
	 * <P>
	 * The nominal text is the typical (or largest) string that will be returned by this TickTextProvider. This text
	 * should be used to compute the space needed when calculating the area needed to properly render the X-Axis.
	 */
	public String getNominalTextForX();

	/**
	 * Returns the nominal text to use for the Y-Axis.
	 * <P>
	 * The nominal text is the typical (or largest) string that will be returned by this TickTextProvider. This text
	 * should be used to compute the space needed when calculating the area needed to properly render the Y-Axis.
	 */
	public String getNominalTextForY();

	/**
	 * Method which returns the corresponding text to the specified (model coordinates) position on the X-Axis.
	 */
	public String getTextForX(double aMX);

	/**
	 * Method which returns the corresponding text to the specified (model coordinates) position on the Y-Axis.
	 */
	public String getTextForY(double aMY);
}

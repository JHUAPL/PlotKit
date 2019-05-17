package plotkit.util;

/**
 * Collection of miscellaneous utility methods that have not been properly organized.
 */
public class MiscUtil
{

	/**
	 * Eventually this method should go away. Please use: Guava ver 21 and use the method Doubles.constrainToRange()
	 * <P>
	 * Or update to guava ver21
	 */
	public static double clampToRange(double aMinVal, double aMaxVal, double aVal)
	{
		int zios_move; // We should no longer need this utility method.

		if (aVal < aMinVal)
			return aMinVal;

		if (aVal > aMaxVal)
			return aMaxVal;

		return aVal;
	}

}

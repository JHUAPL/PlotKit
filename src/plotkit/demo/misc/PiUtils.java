package plotkit.demo.misc;

import plotkit.misc.LogicError;
import glum.gui.GuiUtil;

public class PiUtils
{
	// Constants
	// public static char PiChar = '';
	public static char PiChar = '\u03C0';

	/**
	 * Utility method that returns a value that when multiplied by PI approximates aVal.
	 * <P>
	 * If the specified value is not a multiple of PI then NaN will be returned.
	 * 
	 * A number will be considered a scaler of PI if the following expression is true: <BR>
	 * A * PI == aVal ----> where A is an integer or a fixed decimal no smaller than 0.01
	 */
	public static double getPiScalar(double aVal)
	{
		// Use an epsilon of twice the ulp of aMX
		// Consider other values such as 4X, 10X, 100X...
		double epsilon = Math.ulp(aVal) * 2.0;

		// Calculate the original raw value before it was scaled by PI
		double rawValD = aVal / Math.PI;

		// Determine if the rawVal approximates an integer scaled by PI
		if (Math.abs(Math.round(rawValD) - rawValD) < epsilon)
			return Math.round(rawValD);

		// Determine if the rawVal approximates a fixed decimal (no smaller than 0.01) scaled by PI
		double rawVal100 = rawValD * 100;
		if (Math.abs(Math.round(rawVal100) - rawVal100) < epsilon * 100)
			return Math.rint(rawVal100) / 100;

		return Double.NaN;
	}

	/**
	 * Utility method that returns the true if the specified number is a scaler of PI.
	 * <P>
	 * A number will be considered a scaler of PI if the following expression is true: <BR>
	 * A * PI == aVal ----> where A is an integer or a fixed decimal no smaller than 0.01
	 */
	public static boolean isScalarOfPi(double aVal)
	{
		// Use an epsilon of twice the ulp of aMX
		// Consider other values such as 4X, 10X, 100X...
		double epsilon = Math.ulp(aVal) * 2.0;

		// Calculate the original raw value before it was scaled by PI
		double rawValD = aVal / Math.PI;

		// Determine if the rawVal approximates an integer scaled by PI
		if (Math.abs(Math.round(rawValD) - rawValD) < epsilon)
			return true;

		// Determine if the rawVal approximates a fixed decimal (no smaller than 0.01) scaled by PI
		double rawVal100 = rawValD * 100;
		if (Math.abs(Math.round(rawVal100) - rawVal100) < epsilon * 100)
			return true;

		return false;
	}

	/**
	 * Utility method that will read a double from the specified string.
	 * <P>
	 * If the String ends with the PI then returned value will be multiplied by PI.
	 * <P>
	 * Throws a LogicError if we failed to read a number.
	 */
	public static double readDouble(String aStr)
	{
		double retVal = GuiUtil.readDouble(aStr, Double.NaN);
		if (Double.isNaN(retVal) == true && aStr.charAt(aStr.length() - 1) == PiUtils.PiChar)
			retVal = GuiUtil.readDouble(aStr.substring(0, aStr.length() - 1), Double.NaN) * Math.PI;
		if (Double.isNaN(retVal) == true)
			throw new LogicError("Invalid Input: " + aStr);

		return retVal;
	}

}

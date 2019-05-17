package plotkit.demo.misc;

import plotkit.misc.LogicError;

/**
 * This class provides a collection of utility methods which serve as place holders for code still in development.
 * <P>
 * Thus any method will be defined as deprecated - so that is shows up as a warning in the IDE.
 */
public class DebugUtil
{

	/**
	 * Prints out a message that the logic has not fully been developed.
	 * <P>
	 * The line number, class name, and method will be logged to the error console.
	 */
	@Deprecated
	public static void wrnincompleteLogic()
	{
		Throwable tmpThrowable = new Throwable();
		StackTraceElement ste = tmpThrowable.getStackTrace()[1];
		System.err.println(
				"Incomplete code: " + ste.getClassName() + "." + ste.getMethodName() + "  -> L:" + ste.getLineNumber());
	}

	/**
	 * Throws a LogicError. This should be used as a place holder for code that is not complete and should not be run.
	 * <P>
	 * The line number, class name, and method will be logged to the error console.
	 */
	@Deprecated
	public static void errIncompleteLogic()
	{
		throw new LogicError("Incomplete code.");
	}

}

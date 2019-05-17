package plotkit.misc;

/**
 * Custom exception specific to the plotkit package.
 */
public class LogicError extends RuntimeException
{
	public LogicError(String aMessage)
	{
		super(aMessage);
	}

}

package plotkit.text;

/**
 * TextProvider that always returns the same string (invalidStr)
 */
public class InvalidTextProvider implements TextProvider
{
	// Constants
	public static final InvalidTextProvider Default = new InvalidTextProvider("---");

	// Attributes
	private final String invalidStr;

	/**
	 * Forms an {@link InvalidTextProvider} with the specified invalid string.
	 */
	public InvalidTextProvider(String aInvalidStr)
	{
		invalidStr = aInvalidStr;
	}

	@Override
	public String getNominalTextForX()
	{
		return invalidStr;
	}

	@Override
	public String getNominalTextForY()
	{
		return invalidStr;
	}

	@Override
	public String getTextForX(double aMX)
	{
		return invalidStr;
	}

	@Override
	public String getTextForY(double aMY)
	{
		return invalidStr;
	}

}

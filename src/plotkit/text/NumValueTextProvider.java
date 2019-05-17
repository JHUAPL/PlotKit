package plotkit.text;

import java.text.NumberFormat;

public class NumValueTextProvider implements TextProvider
{
	// Attributes
	private final NumberFormat valueFmt;
	private final String nomValue;

	/**
	 * TextProvider that provides texts formatted with the specified NumberFormat.
	 * 
	 * @param aValueFmt
	 *        The NumberFormat used to format the values.
	 * @param aNomValue
	 *        The string that will be used for the nominal text.
	 */
	public NumValueTextProvider(NumberFormat aValueFmt, String aNomValue)
	{
		valueFmt = aValueFmt;
		nomValue = aNomValue;
	}

	@Override
	public String getNominalTextForX()
	{
		return nomValue;
	}

	@Override
	public String getNominalTextForY()
	{
		return nomValue;
	}

	@Override
	public String getTextForX(double aMX)
	{
		return valueFmt.format(aMX);
	}

	@Override
	public String getTextForY(double aMY)
	{
		return valueFmt.format(aMY);
	}

}

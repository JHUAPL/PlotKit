package plotkit.demo.data.text;

import java.text.DecimalFormat;

import plotkit.text.TextProvider;

public class RawTextProvider implements TextProvider
{
	// Constants
	public final static RawTextProvider Default = new RawTextProvider("8888.88", "8888.88");

	// Attributes
	private final DecimalFormat xFormatter;
	private final DecimalFormat yFormatter;
	private final String nomTextX;
	private final String nomTextY;

	public RawTextProvider(String aNomTextX, String aNomTextY)
	{
		xFormatter = new DecimalFormat("###0.00");
		yFormatter = new DecimalFormat("###0.00");
		nomTextX = aNomTextX;
		nomTextY = aNomTextY;
	}

	@Override
	public String getNominalTextForX()
	{
		return nomTextX;
	}

	@Override
	public String getNominalTextForY()
	{
		return nomTextY;
	}

	@Override
	public String getTextForX(double aMX)
	{
		// Return the formatted text
		return xFormatter.format(aMX);
	}

	@Override
	public String getTextForY(double aMY)
	{
		// Return the formatted text
		return yFormatter.format(aMY);
	}

}

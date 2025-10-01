// Copyright (C) 2024 The Johns Hopkins University Applied Physics Laboratory LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package plotkit.demo.data.text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.google.common.math.DoubleMath;

import plotkit.demo.misc.PiUtils;
import plotkit.text.TextProvider;

/**
 * TextProvider that returns a string which is expressed as a multiple of PI. The string will be appended with the PI
 * char.
 * <P>
 * Integral values (within an unspecified tolerance) scaled by PI will be displayed as an integer.
 * <P>
 * Fixed decimal place values (within an unspecified tolerance) scaled by PI will be displayed as a fixed decimal.
 *
 * @author lopeznr1
 */
public class PiTextProvider implements TextProvider
{
	public static final PiTextProvider Default = new PiTextProvider("8888.88~~" + PiUtils.PiChar);

	// Attributes
	private final NumberFormat act100Format;
	private final NumberFormat estSmallFormat;
	private final NumberFormat estLargeFormat;
	private final String nominalStr;

	/**
	 * Forms a {@link PiTextProvider} with the specified nominal string. The nominal string should be the typical(or
	 * largest) string that will be provided by this TextProvider.
	 */
	public PiTextProvider(String aNominalStr)
	{
		act100Format = new DecimalFormat("0.00");
		estSmallFormat = new DecimalFormat("0.000");
		estLargeFormat = new DecimalFormat("0.00E0");
		nominalStr = aNominalStr;
	}

	@Override
	public String getNominalTextForX()
	{
		return nominalStr;
	}

	@Override
	public String getNominalTextForY()
	{
		return nominalStr;
	}

	@Override
	public String getTextForX(double aMX)
	{
		return getScalarText(aMX);
	}

	@Override
	public String getTextForY(double aMY)
	{
		return getScalarText(aMY);
	}

	/**
	 * Helper method that performs the value to text (approximation) transformation.
	 *
	 * @param aVal
	 *    The value that will be expressed as a scaler of PI.
	 */
	private String getScalarText(double aVal)
	{
		double piScaler = PiUtils.getPiScalar(aVal);
		if (Double.isNaN(piScaler) == false)
		{
			if (DoubleMath.isMathematicalInteger(piScaler) == true)
				return "" + Math.round(piScaler) + PiUtils.PiChar;
			else
				synchronized (this)
				{
					return act100Format.format(piScaler) + PiUtils.PiChar;
				}
		}

		synchronized (this)
		{
			piScaler = aVal / Math.PI;
			if (piScaler < 1000.0)
				return estSmallFormat.format(piScaler) + PiUtils.PiChar + "~";
			else
				return estLargeFormat.format(piScaler) + PiUtils.PiChar + "~";

		}

	}

}
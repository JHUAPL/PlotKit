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
	 *    The NumberFormat used to format the values.
	 * @param aNomValue
	 *    The string that will be used for the nominal text.
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

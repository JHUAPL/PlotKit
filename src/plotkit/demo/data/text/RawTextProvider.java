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

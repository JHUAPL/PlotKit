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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import plotkit.text.TextProvider;

public class WattTimeTextProvider implements TextProvider
{
	// Constants
	public final static WattTimeTextProvider DemoDefault = formDefaultProvider();

	// Attributes
	private final LocalDateTime minDateTime;
	private final long secPerModelUnit;
	private final double wattPerModelUnit;

	private final DateTimeFormatter dateTimeFormatter;
	private final DecimalFormat wattFormatter;

	public WattTimeTextProvider(LocalDateTime aMinDateTime, int aSecPerPixel, double aWattPerPixel)
	{
		minDateTime = aMinDateTime;
		secPerModelUnit = aSecPerPixel;
		wattPerModelUnit = aWattPerPixel;

//		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMMdd HH:mm:ss");
		dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		wattFormatter = new DecimalFormat("###0.00");
	}

	@Override
	public String getNominalTextForX()
	{
		return "88:88";
	}

	@Override
	public String getNominalTextForY()
	{
		return "500.00 W";
	}

	@Override
	public String getTextForX(double aMX)
	{
		// Transform the virtual position to the offset DateTime
		long secOffset = (long) aMX * secPerModelUnit;
		LocalDateTime vDateTime = minDateTime.plusSeconds(secOffset);

		// Return the formatted text
		return dateTimeFormatter.format(vDateTime);
	}

	@Override
	public String getTextForY(double aMY)
	{
		// Transform the virtual position to the offset wattage
		String unitStr = "w";
		double wattOffset = aMY * wattPerModelUnit;

		if (wattOffset > 1000)
		{
			unitStr = "KW";
			wattOffset = wattOffset / 1000.0;
		}

		return wattFormatter.format(wattOffset) + " " + unitStr;
	}

	/**
	 * Utility helper method to form a default "test" TextProvider
	 * <P>
	 * TODO: Perhaps move this to a "Defaults" class.
	 */
	private static WattTimeTextProvider formDefaultProvider()
	{
		LocalDateTime minDateTime = LocalDateTime.of(2016, 06, 15, 14, 00);
		int secPerPixel = 60;
		int wattPerPixel = 1;

		WattTimeTextProvider retTP = new WattTimeTextProvider(minDateTime, secPerPixel, wattPerPixel);
		return retTP;
	}

}

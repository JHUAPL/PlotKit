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
package plotkit.painter;

import java.awt.*;
import java.awt.font.TextLayout;
import java.util.Objects;

import plotkit.AxisTransform;
import plotkit.Painter;
import plotkit.geom.Dimension;
import plotkit.geom.Rect;

/**
 * Immutable class that defines a {@link Painter} used to render the a title text.
 *
 * @author lopeznr1
 */
public class TitlePainter implements Painter
{
	// Attributes
	private final String title;
	private final Color color;
	private final Font font;
	private final TextAnchor anchor;
	private final double angle;

	// Derived vars
	private final double angRad;

	/**
	 * @param aTitle
	 *    The text used for the title.
	 * @param aColor
	 *    The color associated with the text to be rendered.
	 * @param aFont
	 *    The font associated with the text to be rendered.
	 * @param aAnchor
	 *    The location where the rendered text will be anchored.
	 * @param aAngle
	 *    The angle (in degrees) for which the text should be rotated.
	 */
	public TitlePainter(String aTitle, Color aColor, Font aFont, TextAnchor aAnchor, double aAngle)
	{
		title = aTitle;
		color = Objects.requireNonNull(aColor);
		font = Objects.requireNonNull(aFont);
		anchor = Objects.requireNonNull(aAnchor);
		angle = aAngle;

		angRad = Math.toRadians(angle);
	}

	/**
	 * Returns the title.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Returns the color used to render the text.
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Returns the font used to render the text.
	 */
	public Font getFont()
	{
		return font;
	}

	/**
	 * Returns the Anchor used when for the text.
	 */
	public TextAnchor getAnchor()
	{
		return anchor;
	}

	/**
	 * Returns the angle (in degrees) to rotate the text.
	 */
	public double getAngle()
	{
		return angle;
	}

	@Override
	public String getDescription()
	{
		String retStr = "title: " + title;
		retStr += "; rgb: " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
		retStr += "; font: " + font.getName() + "," + font.getStyle() + "," + font.getSize();

		return retStr;
	}

	@Override
	public double getHeightForAxisX(Graphics2D g2d)
	{
		Rect bRect = TextUtil.calcBoundingRect(g2d, title, font, angRad);
		double retVal = bRect.getHeight();
		return retVal;
	}

	@Override
	public double getWidthForAxisY(Graphics2D g2d)
	{
		Rect bRect = TextUtil.calcBoundingRect(g2d, title, font, angRad);
		double retVal = bRect.getWidth();
		return retVal;
	}

	@Override
	public void renderAxisX(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isNorthSide)
	{
		// Calculate non-changing values
		double maxX = aAxisDim.getWidth() - 1;
		double maxY = aAxisDim.getHeight() - 1;
		double yPos = 0;
		if (isNorthSide == true)
			yPos = maxY;

//		// Calculate the bounding box of the text
//		Rect bRect = TextUtil.calcBoundingRect(g2d, title, font, angRad);

		// Calculate the bounding box of the text
		g2d.rotate(angRad, 0, 0);
		TextLayout textLayout = new TextLayout(title, font, g2d.getFontRenderContext());
		g2d.rotate(-angRad, 0, 0);

		// TODO: Is the below correct???
		// Determine the point at which to perform the rotation and draw the text
		double xPos = maxX / 2.0;

		int zios_2019Jan03;
//		double xPosText = xPos - TextUtil.calcOffset(g2d, anchor, textLayout, true);
		double xPosText = xPos + TextUtil.calcOffset(g2d, anchor, textLayout, true);
		double yPosText = yPos + textLayout.getAscent();
		if (isNorthSide == true)
			yPosText = yPos;

//System.out.println("xPos: " + xPos + "    xPosText: " + xPosText + "  calcOffset: " + TextUtil.calcOffset(g2d, anchor, textLayout, true));

		// Render the title text
		g2d.setFont(font);
		g2d.setColor(color);

		g2d.rotate(angRad, xPos, yPos);
		g2d.drawString(title, (float) xPosText, (float) yPosText);
		g2d.rotate(-angRad, xPos, yPos);
	}

	@Override
	public void renderAxisY(Graphics2D g2d, Dimension aAxisDim, AxisTransform aAxisTransform, boolean isWestSide)
	{
		// Calculate non-changing values
		double maxX = aAxisDim.getWidth() - 1;
		double maxY = aAxisDim.getHeight() - 1;
		double xPos = 0;
		if (isWestSide == true)
			xPos = maxX;

		// Calculate the bounding box of the text
		Rect bRect = TextUtil.calcBoundingRect(g2d, title, font, angRad);

		// Determine the point at which to perform the rotation and draw the text
		double yPos = maxY / 2.0;
		if (anchor == TextAnchor.Lead)
			yPos = maxY - bRect.getY() - bRect.getHeight();
		else if (anchor == TextAnchor.Tail)
			yPos = 0L - bRect.getY();
		else
			yPos = maxY / 2.0 + (bRect.getHeight() / 2);

		// Render the title text
		g2d.setFont(font);
		g2d.setColor(color);

		g2d.rotate(angRad, xPos, yPos);
		g2d.drawString(title, (float) xPos, (float) yPos);
		g2d.rotate(-angRad, xPos, yPos);
	}

}

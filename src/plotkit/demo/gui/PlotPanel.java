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
package plotkit.demo.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.google.common.collect.ImmutableList;

import plotkit.AxisTransform;
import plotkit.Painter;
import plotkit.anchor.*;
import plotkit.cadence.*;
import plotkit.demo.data.SimpleAxisTransform;
import plotkit.demo.data.text.WattTimeTextProvider;
import plotkit.geom.Rect;
import plotkit.layout.*;
import plotkit.painter.*;
import plotkit.text.TextProvider;
import plotkit.tranform.PlainAxisTransform;
import plotkit.util.GraphicsUtil;

public class PlotPanel extends JPanel implements MouseMotionListener
{
	// State vars
	private AxisTransform xAxisTransform;
	private AxisTransform yAxisTransform;
	private PlainLayout demoLayout;

	private boolean drawWestAxis;
	private boolean drawEastAxis;
	private boolean drawSouthAxis;
	private boolean drawNorthAxis;

	private int currVX;
	private int currVY;
	private double mouseMX;
	private double mouseMY;

	// Cache vars
	private ImmutableList<Marker> cMarkerList;
	private Rect cPlotRect;

	public PlotPanel()
	{
		xAxisTransform = SimpleAxisTransform.Default;
		yAxisTransform = SimpleAxisTransform.Default;
		demoLayout = formDemoLayout();

		drawWestAxis = true;
		drawEastAxis = false;
		drawNorthAxis = false;
		drawSouthAxis = true;

		currVX = 0;
		currVY = 0;
		mouseMX = Double.NaN;
		mouseMY = Double.NaN;

		cMarkerList = ImmutableList.of();
		cPlotRect = Rect.Empty;

		// Register for events of interest
		addMouseMotionListener(this);

		// Properly setup the DemoLayout
		setDemoLayout(demoLayout);
	}

	/**
	 * Returns a copy of the currently installed demo Layout.
	 */
	public PlainLayout getDemoLayout()
	{
		// TODO: Return a copy not the actual layout.
		int zios_2019Jan15;
		return demoLayout;
	}

	/**
	 * Sets in the Layout to be used as the demo Layout.
	 * <P>
	 * The demo Layout will be used on all 4 sides (North, South, West, East) of the plot.
	 */
	public void setDemoLayout(PlainLayout aLayout)
	{
		// TODO: Save a copy not the actual layout.
		int zios_2019Jan15;
		demoLayout = aLayout;

		// Update our list of Markers
		List<Marker> tmpMarkerList = new ArrayList<>();
		for (Painter aPainter : demoLayout.getPainters())
		{
			if (aPainter instanceof Marker == true)
				tmpMarkerList.add((Marker) aPainter);
		}
		cMarkerList = ImmutableList.copyOf(tmpMarkerList);

		// Notify all of the Markers (associated with our axisStyle)
		for (Marker aMarker : cMarkerList)
			aMarker.setMarker(mouseMX, mouseMY);

		// Time for a repaint
		repaint();
	}

	/** Returns true if the East side should be drawn. */
	public boolean isDrawEastSide()
	{
		return drawEastAxis;
	}

	/** Returns true if the West side should be drawn. */
	public boolean isDrawWestSide()
	{
		return drawWestAxis;
	}

	/** Returns true if the North side should be drawn. */
	public boolean isDrawNorthSide()
	{
		return drawNorthAxis;
	}

	/** Returns true if the South side should be drawn. */
	public boolean isDrawSouthSide()
	{
		return drawSouthAxis;
	}

	/** Returns true if the East side should be drawn. */
	public void setDrawEastSide(boolean aBool)
	{
		drawEastAxis = aBool;
		repaint();
	}

	/** Returns true if the West side should be drawn. */
	public void setDrawWestSide(boolean aBool)
	{
		drawWestAxis = aBool;
		repaint();
	}

	/** Returns true if the North side should be drawn. */
	public void setDrawNorthSide(boolean aBool)
	{
		drawNorthAxis = aBool;
		repaint();
	}

	/** Returns true if the South side should be drawn. */
	public void setDrawSouthSide(boolean aBool)
	{
		drawSouthAxis = aBool;
		repaint();
	}

	/**
	 * Sets in the current virtual-x
	 */
	public void setVirtX(int aVX)
	{
		currVX = aVX;
		xAxisTransform = new PlainAxisTransform(0, 0, 1.0, currVX);

		repaint();
	}

	/**
	 * Sets in the current virtual-x
	 */
	public void setVirtY(int aVY)
	{
		currVY = aVY;
		yAxisTransform = new PlainAxisTransform(0, 0, 1.0, currVY);

		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent aEvent)
	{
		; // Nothing to do
	}

	@Override
	public void mouseMoved(MouseEvent aEvent)
	{
		// Respond to mouse move events only when <CTRL> is pressed
		boolean isCtrl = (aEvent.getModifiersEx() & MouseEvent.CTRL_DOWN_MASK) == MouseEvent.CTRL_DOWN_MASK;
		if (isCtrl == false)
			return;

		// Assume no valid mouse model point
		mouseMX = Double.NaN;
		mouseMY = Double.NaN;

		// Get a local copy of cPlotRect
		Rectangle2D plotRect = cPlotRect.formRectangle2D();

		// Retrieve the mouse point (screen coordinates)
		double mouseSX = aEvent.getPoint().getX();
		double mouseSY = aEvent.getPoint().getY();

		if (plotRect.contains(mouseSX, mouseSY) == true)
		{
			// Transform the mouse point from screen coordinates to axis coordinates
			double axisX = mouseSX - plotRect.getMinX();
			double axisY = plotRect.getMaxY() - mouseSY;

			// Transform from axis coordinates to model coordinates
			mouseMX = xAxisTransform.getPlotValForAxisVal(axisX);
			mouseMY = yAxisTransform.getPlotValForAxisVal(axisY);
		}

		// Notify all of the Markers (associated with our axisStyle)
		for (Marker aMarker : cMarkerList)
			aMarker.setMarker(mouseMX, mouseMY);

		// Time for a repaint
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Rect tmpRect;
		double tmpX, tmpY;

		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		// Set up the render hints
		GraphicsUtil.configureHintsForSubpixelQuality(g2d);

		// Compute the plot boundaries
		double yAxisWidth = demoLayout.getWidthForAxisY(g2d);
		double xAxisHeight = demoLayout.getHeightForAxisX(g2d);
		cPlotRect = getPlotRectangle(xAxisHeight, yAxisWidth);

		// Extract the cPlotRect specific vars
		double plotBX = cPlotRect.getX();
		double plotBY = cPlotRect.getY();
		double plotW = cPlotRect.getWidth();
		double plotH = cPlotRect.getHeight();
		double plotEX = plotBX + plotW;
		double plotEY = plotBY + plotH;

		// Render the plot
		g2d.setColor(Color.GREEN.darker());
		g2d.draw(cPlotRect.formRectangle2D());

		// Retrieve the Layouts for associated with the different sides
		Layout northLayout = demoLayout;
		if (drawNorthAxis == false)
			northLayout = EmptyLayout.Instance;
		Layout southLayout = demoLayout;
		if (drawSouthAxis == false)
			southLayout = EmptyLayout.Instance;
		Layout westLayout = demoLayout;
		if (drawWestAxis == false)
			westLayout = EmptyLayout.Instance;
		Layout eastLayout = demoLayout;
		if (drawEastAxis == false)
			eastLayout = EmptyLayout.Instance;

		// Render the North side
		double northSideHeight = demoLayout.getHeightForAxisX(g2d);

		tmpX = plotBX;
		tmpY = plotBY - northSideHeight;
		tmpRect = new Rect(tmpX, tmpY, plotW, northSideHeight);
		northLayout.renderSideNorth(g2d, tmpRect, xAxisTransform);

		// Render the South axis
		double southSideHeight = demoLayout.getHeightForAxisX(g2d);

		tmpX = plotBX;
		tmpY = plotEY + 1;
		tmpRect = new Rect(tmpX, tmpY, plotW, southSideHeight);
		southLayout.renderSideSouth(g2d, tmpRect, xAxisTransform);

		// Render the West axis
		double westSideWidth = demoLayout.getWidthForAxisY(g2d);

		tmpX = plotBX - westSideWidth;
		tmpY = plotBY;
		tmpRect = new Rect(tmpX, tmpY, westSideWidth, plotH);
		westLayout.renderSideWest(g2d, tmpRect, yAxisTransform);

		// Render the East axis
		double eastSideWidth = demoLayout.getWidthForAxisY(g2d);

		tmpX = plotEX + 1;
		tmpY = plotBY;
		tmpRect = new Rect(tmpX, tmpY, eastSideWidth, plotH);
		eastLayout.renderSideEast(g2d, tmpRect, yAxisTransform);
	}

	/**
	 * Helper method that forms the rectangle which contains the plot.
	 * <P>
	 * This method will take into account the Axis that are enabled and returned a reduced region so that the axis can be
	 * properly rendered.
	 * <P>
	 * The returned rectangle is in screen coordinates.
	 */
	private Rect getPlotRectangle(double xAxisHeight, double yAxisWidth)
	{
		double plotBX = 5;
		if (drawWestAxis == true)
			plotBX += yAxisWidth;

		double plotEX = getWidth() - 5;
		if (drawEastAxis == true)
			plotEX -= yAxisWidth;

		double plotBY = 5;
		if (drawNorthAxis == true)
			plotBY += xAxisHeight;

		double plotEY = getHeight() - 5;
		if (drawSouthAxis == true)
			plotEY -= xAxisHeight;

		// Render the plot
		double plotW = plotEX - plotBX;
		double plotH = plotEY - plotBY;
		Rect plotRect = new Rect(plotBX, plotBY, plotW, plotH);

		return plotRect;
	}

	/**
	 * Utility method that returns a default Layout. The default Layout is composed of a variety of painters used to
	 * demonstrate the flexibility of the PlotKit library.
	 * <P>
	 * This default Layout is used in the demo app on all 4 sides.
	 */
	private static PlainLayout formDemoLayout()
	{
		TextProvider textProvider = WattTimeTextProvider.DemoDefault;
		Font textFont = UIManager.getFont("Label.font");

		Cadence cad005 = new PlainModelCadence(5, 0);
		Cadence cad015 = new PlainModelCadence(15, 0);
		Cadence cad060 = new PlainModelCadence(60, 0);
//		Cadence cad120 = new PlainModelCadence(120, 0);
		Cadence cad120 = new PlainModelCadence(30.25 * Math.PI, 0);
		Cadence cad200 = new PlainAxisCadence(200, 0);

		PlainLayout retLayout = new PlainLayout();
		retLayout.addPainter(new ExteriorTickPainter(cad005, Color.RED.darker(), 3));
		retLayout.addPainter(new ExteriorTickPainter(cad015, Color.RED.darker(), 5));
		retLayout.addPainter(new ExteriorTickPainter(cad060, Color.RED.darker(), 8));
		retLayout.addPainter(new ExteriorTickPainter(cad120, Color.RED, 11));
		retLayout.addPainter(new ExteriorTickPainter(cad200, Color.BLUE, 15));

		Anchor textTickAnchor = new AnchorFixed(11);
		retLayout.addPainter(
				new TextTickPainter(textProvider, cad120, Color.BLACK, textFont, TextAnchor.Lead, 0.00, true),
				textTickAnchor);

		retLayout.addPainter(new TextMarkerPainter(textProvider, Color.RED.darker().darker(), Color.WHITE, textFont,
				TextAnchor.Lead, 0.00), textTickAnchor);
		retLayout.addPainter(new PlumbLinePainter(Color.RED));

		return retLayout;
	}

}

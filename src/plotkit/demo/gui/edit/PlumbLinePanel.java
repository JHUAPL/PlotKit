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
package plotkit.demo.gui.edit;

import glum.gui.panel.ColorInputPanel;

import java.awt.event.ActionListener;

import plotkit.Painter;
import plotkit.painter.PlumbLinePainter;
import net.miginfocom.swing.MigLayout;

public class PlumbLinePanel extends SpawnPanel implements ActionListener
{
	// Gui vars
	private ColorInputPanel colorCIP;

	public PlumbLinePanel(ActionListener aListener)
	{
		formUI();

		addActionListener(aListener);
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent aEvent)
	{
		notifyListeners(this, ID_UPDATE, "");
	}

	@Override
	public boolean isReady()
	{
		return true;
	}

	@Override
	public Painter getPainter()
	{
		return new PlumbLinePainter(colorCIP.getColorConfig());
	}

	@Override
	public void setPainter(Painter aPainter)
	{
		PlumbLinePainter tmpPainter = (PlumbLinePainter) aPainter;

		colorCIP.setColorConfig(tmpPainter.getColor());
	}

	/**
	 * Helper method that forms the UI
	 */
	private void formUI()
	{
		setLayout(new MigLayout("", "", ""));

		colorCIP = new ColorInputPanel(true, true, false);
		colorCIP.addActionListener(this);
		add("", colorCIP);
	}

}

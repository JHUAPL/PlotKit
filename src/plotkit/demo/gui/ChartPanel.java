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

import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import net.miginfocom.swing.MigLayout;

public class ChartPanel extends JPanel implements AdjustmentListener
{
	// Gui vars
	private PlotPanel refPP;
	private JScrollBar nsSB;
	private JScrollBar weSB;

	public ChartPanel(PlotPanel aPlotPanel)
	{
		setLayout(new MigLayout("", "", ""));
		setPreferredSize(new Dimension(800, 600));

		nsSB = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 5000);
		nsSB.setValue(5000);
		nsSB.addAdjustmentListener(this);
		weSB = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 5000);
		weSB.addAdjustmentListener(this);
		refPP = aPlotPanel;
		add(nsSB, "growy,pushY");
		add(refPP, "growx,growy,pushx,wrap");
		add(weSB, "skip 1,growx");
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent aEvent)
	{
		Object source = aEvent.getSource();
		if (source == nsSB)
			refPP.setVirtY(5000 - nsSB.getValue());
		if (source == weSB)
			refPP.setVirtX(weSB.getValue());
	}

}

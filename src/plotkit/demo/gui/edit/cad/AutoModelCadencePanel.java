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
package plotkit.demo.gui.edit.cad;

import glum.gui.component.GNumberField;
import glum.unit.NumberUnit;
import glum.unit.Unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.google.common.collect.Range;

import plotkit.cadence.*;
import net.miginfocom.swing.MigLayout;

public class AutoModelCadencePanel extends SpawnPanel implements ActionListener
{
	// Constants
	private final Range<Double> RangeTickFreq = Range.closed(1.0, Double.POSITIVE_INFINITY);
	private final Range<Double> RangeCadenceAlign = Range.closed(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	private final Range<Double> RangeCadenceBeat = Range.closed(0.0001, Double.POSITIVE_INFINITY);

	// Gui vars
	private GNumberField tickFreqNF;
	private GNumberField valMarkNF;
	private GNumberField targBeatNF;
	private JCheckBox targBeatCB;

	public AutoModelCadencePanel()
	{
		formUI();
		setCadence(new AutoModelCadence(250, 100, 500, true));
	}

	@Override
	public void actionPerformed(ActionEvent aEvent)
	{
		notifyListeners(this, ID_UPDATE);
		updateGui();
	}

	@Override
	public boolean isReady()
	{
		boolean isReady = true;
		isReady &= tickFreqNF.isValidInput() == true;
		isReady &= valMarkNF.isValidInput() == true;
		isReady &= targBeatCB.isSelected() == false || targBeatNF.isValidInput() == true;
		return isReady;
	}

	@Override
	public Cadence getCadence()
	{
		double tickFreq = tickFreqNF.getValue();
		double alignVal = valMarkNF.getValue();
		double cadence = targBeatNF.getValue();
		boolean useCadence = targBeatCB.isSelected();
		return new AutoModelCadence(tickFreq, alignVal, cadence, useCadence);
	}

	@Override
	public void setCadence(Cadence aCadence)
	{
		AutoModelCadence tmpCadence = (AutoModelCadence) aCadence;

		double tickFreq = tmpCadence.getTickFreq();
		double valMark = tmpCadence.getAlignValue();
		double targBeat = tmpCadence.getTargBeat();
		boolean useTargBeat = tmpCadence.getTargBeatB();

		tickFreqNF.setValue(tickFreq);
		valMarkNF.setValue(valMark);
		targBeatNF.setValue(targBeat);
		targBeatCB.setSelected(useTargBeat);
	}

	/**
	 * Helper method that forms the UI
	 */
	private void formUI()
	{
		setLayout(new MigLayout("", "[right][]", ""));
		Unit numUnit = new NumberUnit("", "", 1.0, new DecimalFormat("0.####"));

		JLabel tickFreqL = new JLabel("Tick Freq:");
		tickFreqL.setToolTipText("Frequency of ticks (1 tick every n-pixels)");
		tickFreqNF = new GNumberField(this, numUnit, RangeTickFreq);
		add("", tickFreqL);
		add("w 40::", tickFreqNF);

		JLabel valMarkL = new JLabel("Align Val:");
		valMarkL.setToolTipText("Core model value to align to.");
		valMarkNF = new GNumberField(this, numUnit, RangeCadenceAlign);
		add("gapleft 20", valMarkL);
		add("w 60::,wrap", valMarkNF);

		targBeatCB = new JCheckBox("Target Beat:");
		targBeatCB.addActionListener(this);
		targBeatCB.setToolTipText("Target Beat (Rounded to the nearest X^2N)");
		targBeatNF = new GNumberField(this, numUnit, RangeCadenceBeat);
		add("", targBeatCB);
		add("w 40::", targBeatNF);
	}

	/**
	 * Helper method to keep the UI components synchronized.
	 */
	private void updateGui()
	{

		boolean isEnabled = targBeatCB.isSelected();
		targBeatNF.setEditable(isEnabled);
	}

}

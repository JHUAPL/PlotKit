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

import javax.swing.JLabel;

import com.google.common.collect.Range;

import plotkit.cadence.*;
import net.miginfocom.swing.MigLayout;

public class PlainModelCadencePanel extends SpawnPanel implements ActionListener
{
	// Constants
	private final Range<Double> RangeCadenceBeat = Range.closed(0.00001, Double.POSITIVE_INFINITY);
	private final Range<Double> RangeCadenceAlign = Range.closed(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

	// Gui vars
	private GNumberField cadenceNF;
	private GNumberField valMarkNF;

	public PlainModelCadencePanel()
	{
		formUI();
	}

	@Override
	public void actionPerformed(ActionEvent aEvent)
	{
		notifyListeners(this, ID_UPDATE);
	}

	@Override
	public boolean isReady()
	{
		boolean isReady = true;
		isReady &= cadenceNF.isValidInput() == true;
		isReady &= valMarkNF.isValidInput() == true;
		return isReady;
	}

	@Override
	public Cadence getCadence()
	{
		double beat = cadenceNF.getValue();
		double alignVal = valMarkNF.getValue();
		return new PlainModelCadence(beat, alignVal);
	}

	@Override
	public void setCadence(Cadence aCadence)
	{
		double beat = ((PlainModelCadence) aCadence).getBeat();
		cadenceNF.setValue(beat);

		double valMark = ((PlainModelCadence) aCadence).getAlignValue();
		valMarkNF.setValue(valMark);
	}

	/**
	 * Helper method that forms the UI
	 */
	private void formUI()
	{
		setLayout(new MigLayout("", "[right][]", ""));
		Unit numUnit = new NumberUnit("", "", 1.0, new DecimalFormat("0.####"));

		JLabel cadenceL = new JLabel("Cadence:");
		cadenceNF = new GNumberField(this, numUnit, RangeCadenceBeat);
		add("", cadenceL);
		add("w 40::", cadenceNF);

		JLabel valMarkL = new JLabel("Align Val:");
		valMarkL.setToolTipText("Core model value to align to.");
		valMarkNF = new GNumberField(this, numUnit, RangeCadenceAlign);
		add("gapleft 20", valMarkL);
		add("w 60::", valMarkNF);
	}

}

package plotkit.demo.gui.edit.cad;

import glum.gui.component.GNumberField;
import glum.unit.*;

import java.awt.event.*;

import javax.swing.JLabel;

import com.google.common.collect.Range;

import plotkit.cadence.*;
import net.miginfocom.swing.MigLayout;

public class PlainAxisCadencePanel extends SpawnPanel implements ActionListener
{
	// Constants
	private final Range<Double> RangeCadenceBeat = Range.closed(1.0, 900.0);
	private final Range<Double> RangeCadenceAlign = Range.closed(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

	// Gui vars
	private GNumberField cadenceNF;
	private GNumberField valMarkNF;

	public PlainAxisCadencePanel()
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
		int beat = cadenceNF.getValueAsInt(1);
		int alignVal = valMarkNF.getValueAsInt(0);
		return new PlainAxisCadence(beat, alignVal);
	}

	@Override
	public void setCadence(Cadence aCadence)
	{
		double beat = ((PlainAxisCadence) aCadence).getBeat();
		cadenceNF.setValue(beat);

		double valMark = ((PlainAxisCadence) aCadence).getAlignValue();
		valMarkNF.setValue(valMark);
	}

	/**
	 * Helper method that forms the UI
	 */
	private void formUI()
	{
		setLayout(new MigLayout("", "[right][]", ""));
		Unit intUnit = new NumberUnit("", "", 1.0, 0);

		JLabel cadenceL = new JLabel("Cadence:");
		cadenceNF = new GNumberField(this, intUnit, RangeCadenceBeat);
		add("", cadenceL);
		add("w 40::", cadenceNF);

		JLabel valMarkL = new JLabel("Align Val:");
		valMarkL.setToolTipText("Core axis position to align to.");
		valMarkNF = new GNumberField(this, intUnit, RangeCadenceAlign);
		add("gapleft 20", valMarkL);
		add("w 60::", valMarkNF);
	}

}

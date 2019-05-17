package plotkit.demo.gui.edit.cad;

import glum.gui.component.GNumberField;
import glum.unit.NumberUnit;
import glum.unit.Unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JLabel;

import plotkit.cadence.*;
import net.miginfocom.swing.MigLayout;

public class PlainModelCadencePanel extends SpawnPanel implements ActionListener
{
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
		cadenceNF = new GNumberField(this, numUnit, 0.00001, Double.POSITIVE_INFINITY);
		add("", cadenceL);
		add("w 40::", cadenceNF);

		JLabel valMarkL = new JLabel("Align Val:");
		valMarkL.setToolTipText("Core model value to align to.");
		valMarkNF = new GNumberField(this, numUnit, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		add("gapleft 20", valMarkL);
		add("w 60::", valMarkNF);
	}

}

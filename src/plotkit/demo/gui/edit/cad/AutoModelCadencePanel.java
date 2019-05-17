package plotkit.demo.gui.edit.cad;

import glum.gui.component.GNumberField;
import glum.unit.NumberUnit;
import glum.unit.Unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import plotkit.cadence.*;
import net.miginfocom.swing.MigLayout;

public class AutoModelCadencePanel extends SpawnPanel implements ActionListener
{
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
		tickFreqNF = new GNumberField(this, numUnit, 1, Double.POSITIVE_INFINITY);
		add("", tickFreqL);
		add("w 40::", tickFreqNF);

		JLabel valMarkL = new JLabel("Align Val:");
		valMarkL.setToolTipText("Core model value to align to.");
		valMarkNF = new GNumberField(this, numUnit, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		add("gapleft 20", valMarkL);
		add("w 60::,wrap", valMarkNF);

		targBeatCB = new JCheckBox("Target Beat:");
		targBeatCB.addActionListener(this);
		targBeatCB.setToolTipText("Target Beat (Rounded to the nearest X^2N)");
		targBeatNF = new GNumberField(this, numUnit, 0.0001, Double.POSITIVE_INFINITY);
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

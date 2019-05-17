package plotkit.demo.gui.edit.cad;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;

import plotkit.cadence.*;
import plotkit.demo.misc.PiUtils;
import net.miginfocom.swing.MigLayout;

import com.google.common.collect.ImmutableList;
import com.google.common.math.DoubleMath;

public class FixedModelCadencePanel extends SpawnPanel implements ActionListener
{
	// Constants
	public final Color ColorERR = Color.RED.darker().darker();

	// Gui vars
	private JTextArea valueTA;
	private JLabel warnL;

	public FixedModelCadencePanel()
	{
		formUI();
		doUpdateUI();
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
		isReady &= warnL.getText() == null;
		return isReady;
	}

	@Override
	public Cadence getCadence()
	{
		List<Double> valueList = getValues();
		return new FixedModelCadence(valueList);
	}

	@Override
	public void setCadence(Cadence aCadence)
	{
		List<Double> valueList = ((FixedModelCadence) aCadence).getModelValues();

		String valueStr = "";
		for (Double aValue : valueList)
		{
			double piScalar = PiUtils.getPiScalar(aValue);
			if (Double.isNaN(piScalar) == false)
			{
				if (DoubleMath.isMathematicalInteger(piScalar) == true)
					valueStr += Math.round(piScalar) + PiUtils.PiChar;
				else
					valueStr += piScalar + PiUtils.PiChar;
			}
			else
				valueStr += aValue;

			valueStr += " ";
		}
		valueTA.setText(valueStr);

		warnL.setText("");
	}

	/**
	 * Helper method that updates internal UI components
	 */
	private void doUpdateUI()
	{
		List<Double> valueList = ImmutableList.of();

		// Determine if there are any issues
		String errStr = null;
		try
		{
			valueList = getValues();
			if (valueList.size() == 0)
				errStr = "Please enter at least one value.";
		} catch (Exception aExp)
		{
			errStr = aExp.getMessage();
		}

		warnL.setText(errStr);
	}

	/**
	 * Helper method that forms the UI
	 */
	private void formUI()
	{
		setLayout(new MigLayout("", "[right][]", ""));
		PlainDocument valueDoc = new PlainDocument();
		valueDoc.addDocumentListener(new DocListener());

		JLabel valueL = new JLabel("Values:");
		valueL.setToolTipText("List of model values where the ticks will be placed.");
		valueTA = new JTextArea(2, 0);
		valueTA.setDocument(valueDoc);
		valueTA.setLineWrap(true);
		valueTA.setWrapStyleWord(true);
		JScrollPane tmpPane = new JScrollPane(valueTA);
		add(valueL, "");
		add(tmpPane, "growx,growy,pushx,pushy,span,wrap");

		warnL = new JLabel("");
		warnL.setForeground(ColorERR);
		add(warnL, "growx,span");
	}

	/**
	 * Helper method that returns the list of values.
	 * <P>
	 * Any invalid input will be cause an Exception to be thrown.
	 */
	private List<Double> getValues()
	{
		List<Double> retList = new ArrayList<>();

		String valueStr = valueTA.getText();
		for (String aStr : valueStr.split("\\s+"))
		{
			aStr = aStr.trim();
			if (aStr.isEmpty() == true)
				continue;

			double tmpVal = PiUtils.readDouble(aStr);
			retList.add(tmpVal);
		}

		return retList;
	}

	/**
	 * Private Class that manages the DocumentEvents.
	 */
	private class DocListener implements DocumentListener
	{

		@Override
		public void changedUpdate(DocumentEvent aEvent)
		{
			doUpdateUI();
			notifyListeners(this, ID_UPDATE);
		}

		@Override
		public void insertUpdate(DocumentEvent aEvent)
		{
			doUpdateUI();
			notifyListeners(this, ID_UPDATE);
		}

		@Override
		public void removeUpdate(DocumentEvent aEvent)
		{
			doUpdateUI();
			notifyListeners(this, ID_UPDATE);
		}

	}

}
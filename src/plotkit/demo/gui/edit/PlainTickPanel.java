package plotkit.demo.gui.edit;

import glum.gui.component.GNumberField;
import glum.gui.panel.ColorInputPanel;
import glum.unit.NumberUnit;
import glum.unit.Unit;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import com.google.common.collect.Range;

import plotkit.Painter;
import plotkit.cadence.Cadence;
import plotkit.demo.gui.edit.cad.CadenceInputPanel;
import plotkit.painter.ExteriorTickPainter;
import net.miginfocom.swing.MigLayout;

public class PlainTickPanel extends SpawnPanel implements ActionListener
{
	// Constants
	private final Range<Double> RangeLength = Range.closed(0.0, 100.0);

	// Gui vars
	private CadenceInputPanel cadenceIP;
	private ColorInputPanel colorIP;
	private GNumberField lengthNF;

	public PlainTickPanel(ActionListener aListener)
	{
		formUI();

		addActionListener(aListener);
	}

	@Override
	public void actionPerformed(ActionEvent aEvent)
	{
		notifyListeners(this, ID_UPDATE, "");
	}

	@Override
	public boolean isReady()
	{
		boolean isReady = true;
		isReady &= cadenceIP.isReady() == true;
		return isReady;
	}

	@Override
	public Painter getPainter()
	{
		Cadence cadence = cadenceIP.getCadenceConfig();
		Color color = colorIP.getColorConfig();
		int length = lengthNF.getValueAsInt(0);
		return new ExteriorTickPainter(cadence, color, length);
	}

	@Override
	public void setPainter(Painter aPainter)
	{
		ExteriorTickPainter tmpPainter = (ExteriorTickPainter) aPainter;

		cadenceIP.setCadenceConfig(tmpPainter.getCadence());
		colorIP.setColorConfig(tmpPainter.getColor());
		lengthNF.setValue(tmpPainter.getLength());
	}

	/**
	 * Helper method that forms the UI
	 */
	private void formUI()
	{
		setLayout(new MigLayout("", "[right][]", ""));
		Unit intUnit = new NumberUnit("", "", 1.0, 0);

		cadenceIP = new CadenceInputPanel();
		cadenceIP.addActionListener(this);
		add("growx,span,wrap", cadenceIP);

		colorIP = new ColorInputPanel(true, true, false);
		colorIP.addActionListener(this);
		add("span,wrap", colorIP);

		lengthNF = new GNumberField(this, intUnit, RangeLength);
		add("", new JLabel("Tick Length:"));
		add("w 40::", lengthNF);

	}

}

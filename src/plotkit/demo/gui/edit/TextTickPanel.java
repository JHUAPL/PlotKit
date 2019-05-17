package plotkit.demo.gui.edit;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import javax.swing.JLabel;

import glum.gui.component.*;
import glum.gui.panel.*;
import glum.unit.*;
import net.miginfocom.swing.MigLayout;
import plotkit.Painter;
import plotkit.cadence.Cadence;
import plotkit.demo.data.text.*;
import plotkit.demo.gui.edit.cad.CadenceInputPanel;
import plotkit.painter.*;
import plotkit.text.*;

public class TextTickPanel extends SpawnPanel implements ActionListener, TextProviderChangeListener
{
	// Gui vars
	private CadenceInputPanel cadenceIP;
	private ColorInputPanel colorIP;
	private FontInputPanel fontIP;
	private GComboBox<TextAnchor> anchorBox;
	private GNumberField angleNF;

	// State vars
	private TextProvider refTextProvider;

	public TextTickPanel(ActionListener aListener, TextProviderChanger aTextProviderChanger)
	{
		refTextProvider = InvalidTextProvider.Default;

		formUI();

		addActionListener(aListener);

		// Register for events of interest
		aTextProviderChanger.addListener(this);
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
		isReady &= cadenceIP.isReady() == true;
		return isReady;
	}

	@Override
	public Painter getPainter()
	{
		Cadence cadence = cadenceIP.getCadenceConfig();
		Color color = colorIP.getColorConfig();
		Font font = fontIP.getFontConfig();
		TextAnchor anchor = anchorBox.getChosenItem();
		double angle = angleNF.getValue();
		return new TextTickPainter(refTextProvider, cadence, color, font, anchor, angle, true);
	}

	@Override
	public void setPainter(Painter aPainter)
	{
		TextTickPainter tmpPainter = (TextTickPainter) aPainter;

		cadenceIP.setCadenceConfig(tmpPainter.getCadence());
		colorIP.setColorConfig(tmpPainter.getColor());
		fontIP.setFontConfig(tmpPainter.getFont());
		anchorBox.setChosenItem(tmpPainter.getAnchor());
		angleNF.setValue(tmpPainter.getAngle());
	}

	@Override
	public void textProviderChanged(Object source, TextProvider aTextProvider)
	{
		// Save off the TextProvider that will be used for any returned Painter
		refTextProvider = Objects.requireNonNull(aTextProvider);
	}

	/**
	 * Helper method that forms the UI
	 */
	private void formUI()
	{
		setLayout(new MigLayout("", "[right][]", ""));
		Unit angUnit = new NumberUnit("", "", 1.0);

		cadenceIP = new CadenceInputPanel();
		cadenceIP.addActionListener(this);
		add("growx,span,wrap", cadenceIP);

		colorIP = new ColorInputPanel(true, true);
		colorIP.addActionListener(this);
		add("growx,span,wrap", colorIP);

		fontIP = new FontInputPanel();
		fontIP.addActionListener(this);
		add("growx,span,wrap", fontIP);

		angleNF = new GNumberField(this, angUnit, -180, 180);
		anchorBox = new GComboBox<>(this, TextAnchor.values());
		add("", new JLabel("Angle:"));
		add("w 40::", angleNF);
		add("", new JLabel("Anchor:"));
		add("", anchorBox);
	}

}

package plotkit.demo.gui.edit;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import javax.swing.JLabel;

import glum.gui.GuiUtil;
import glum.gui.component.*;
import glum.gui.panel.*;
import glum.unit.*;
import net.miginfocom.swing.MigLayout;
import plotkit.Painter;
import plotkit.demo.data.text.*;
import plotkit.painter.*;
import plotkit.text.*;

public class TextMarkerPanel extends SpawnPanel implements ActionListener, TextProviderChangeListener
{
	// Gui vars
	private ColorInputPanel bgCIP;
	private ColorInputPanel fgCIP;
	private FontInputPanel fontIP;
	private GComboBox<TextAnchor> anchorBox;
	private GNumberField angleNF;
	private GNumberField padSizeNF;

	// State vars
	private TextProvider refTextProvider;

	public TextMarkerPanel(ActionListener aListener, TextProviderChanger aTextProviderChanger)
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
		return true;
	}

	@Override
	public Painter getPainter()
	{
		Color bgColor = bgCIP.getColorConfig();
		Color fgColor = fgCIP.getColorConfig();
		Font font = fontIP.getFontConfig();
		TextAnchor anchor = anchorBox.getChosenItem();
		double angle = angleNF.getValue();
		int padSize = padSizeNF.getValueAsInt(0);
		return new TextMarkerPainter(refTextProvider, bgColor, fgColor, font, anchor, angle, padSize);
	}

	@Override
	public void setPainter(Painter aPainter)
	{
		TextMarkerPainter tmpPainter = (TextMarkerPainter) aPainter;

		bgCIP.setColorConfig(tmpPainter.getColorBG());
		fgCIP.setColorConfig(tmpPainter.getColorFG());
		fontIP.setFontConfig(tmpPainter.getFont());
		anchorBox.setChosenItem(tmpPainter.getAnchor());
		angleNF.setValue(tmpPainter.getAngle());
		padSizeNF.setValue(tmpPainter.getPadSize());
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
		Unit intUnit = new NumberUnit("", "", 1.0, 0);

		// BG-Color area
		add("growx,span,wrap", new JLabel("BG Color:"));

		bgCIP = new ColorInputPanel(true, true);
		bgCIP.addActionListener(this);
		add("growx,span,wrap", bgCIP);

		add("growx,h 4!,span,wrap", GuiUtil.createDivider());

		// FG-Color area
		add("growx,span,wrap", new JLabel("FG Color:"));

		fgCIP = new ColorInputPanel(true, true);
		fgCIP.addActionListener(this);
		add("growx,span,wrap", fgCIP);

		fontIP = new FontInputPanel();
		fontIP.addActionListener(this);
		add("growx,span,wrap", fontIP);

		angleNF = new GNumberField(this, angUnit, -180, 180);
		anchorBox = new GComboBox<>(this, TextAnchor.values());
		add("", new JLabel("Angle:"));
		add("w 40::", angleNF);
		add("", new JLabel("Anchor:"));
		add("wrap", anchorBox);

		padSizeNF = new GNumberField(this, intUnit, 0, 100);
		add("", new JLabel("Pad Size:"));
		add("w 40::", padSizeNF);
	}

}

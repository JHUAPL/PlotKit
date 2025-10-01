package plotkit.demo.gui.edit;

import glum.gui.panel.ColorInputPanel;

import java.awt.event.ActionListener;

import plotkit.Painter;
import plotkit.painter.PlumbLinePainter;
import net.miginfocom.swing.MigLayout;

public class PlumbLinePanel extends SpawnPanel implements ActionListener
{
	// Gui vars
	private ColorInputPanel colorCIP;

	public PlumbLinePanel(ActionListener aListener)
	{
		formUI();

		addActionListener(aListener);
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent aEvent)
	{
		notifyListeners(this, ID_UPDATE, "");
	}

	@Override
	public boolean isReady()
	{
		return true;
	}

	@Override
	public Painter getPainter()
	{
		return new PlumbLinePainter(colorCIP.getColorConfig());
	}

	@Override
	public void setPainter(Painter aPainter)
	{
		PlumbLinePainter tmpPainter = (PlumbLinePainter) aPainter;

		colorCIP.setColorConfig(tmpPainter.getColor());
	}

	/**
	 * Helper method that forms the UI
	 */
	private void formUI()
	{
		setLayout(new MigLayout("", "", ""));

		colorCIP = new ColorInputPanel(true, true, false);
		colorCIP.addActionListener(this);
		add("", colorCIP);
	}

}

package plotkit.demo.gui;

import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import net.miginfocom.swing.MigLayout;

public class ChartPanel extends JPanel implements AdjustmentListener
{
	// Gui vars
	private PlotPanel refPP;
	private JScrollBar nsSB;
	private JScrollBar weSB;

	public ChartPanel(PlotPanel aPlotPanel)
	{
		setLayout(new MigLayout("", "", ""));
		setPreferredSize(new Dimension(800, 600));

		nsSB = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 5000);
		nsSB.setValue(5000);
		nsSB.addAdjustmentListener(this);
		weSB = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 5000);
		weSB.addAdjustmentListener(this);
		refPP = aPlotPanel;
		add(nsSB, "growy,pushY");
		add(refPP, "growx,growy,pushx,wrap");
		add(weSB, "skip 1,growx");
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent aEvent)
	{
		Object source = aEvent.getSource();
		if (source == nsSB)
			refPP.setVirtY(5000 - nsSB.getValue());
		if (source == weSB)
			refPP.setVirtX(weSB.getValue());
	}

}

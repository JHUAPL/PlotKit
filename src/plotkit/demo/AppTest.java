package plotkit.demo;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import plotkit.demo.gui.*;

public class AppTest
{
	public void runTestA()
	{
		PlotPanel plotPanel = new PlotPanel();
//		plotPanel.setPreferredSize(new Dimension(800, 600));

		ChartPanel chartPanel = new ChartPanel(plotPanel);
		DemoPanel demoPanel = new DemoPanel(plotPanel);
		JSplitPane tmpSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, demoPanel, chartPanel);
		tmpSP.setBorder(null);

		JPanel tmpPanel = new JPanel(new MigLayout("", "", ""));
		tmpPanel.add("growx,growy,pushx,pushy", tmpSP);

		JFrame tmpFrame = new JFrame();
		tmpFrame.add(tmpPanel);
		tmpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tmpFrame.setTitle("Plot - TestA");
		tmpFrame.pack();
		tmpFrame.setLocationRelativeTo(null);
		tmpFrame.setVisible(true);
	}

	/**
	 * Entry point to allow testing of the axis rendering logic.
	 */
	public static void main(String[] args)
	{

		// Startup and show the application
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				AppTest tmpAppTest = new AppTest();
				tmpAppTest.runTestA();
			}
		});

	}

}

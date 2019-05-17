package plotkit.demo.gui.edit;

import plotkit.Painter;
import plotkit.misc.LogicError;

/**
 * BlankPanel that is utilized when there is no selected item to be configured (or if the selected item can not be
 * configured).
 */
public class BlankPanel extends SpawnPanel
{
	@Override
	public boolean isReady()
	{
		return false;
	}

	@Override
	public Painter getPainter()
	{
		throw new LogicError("Unsupported operation.");
	}

	@Override
	public void setPainter(Painter aPainter)
	{
		; // Nothing to do
	}

}

package plotkit.demo.gui.edit.cad;

import plotkit.cadence.Cadence;
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
	public Cadence getCadence()
	{
		throw new LogicError("Unsupported operation.");
	}

	@Override
	public void setCadence(Cadence aCadence)
	{
		; // Nothing to do
	}

}

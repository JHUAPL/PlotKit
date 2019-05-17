package plotkit.demo.gui.edit.cad;

import plotkit.cadence.Cadence;
import glum.gui.panel.GPanel;

public abstract class SpawnPanel extends GPanel
{
	/**
	 * Returns true if the SpawnPanel has been properly configured.
	 */
	public abstract boolean isReady();

	/**
	 * Returns the Cadence that reflects this panels GUI.
	 */
	public abstract Cadence getCadence();

	/**
	 * Sets in the Cadence that should be used to configure the GUI.
	 */
	public abstract void setCadence(Cadence aCadence);

}

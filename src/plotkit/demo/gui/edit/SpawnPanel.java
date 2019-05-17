package plotkit.demo.gui.edit;

import plotkit.Painter;
import glum.gui.panel.GPanel;

public abstract class SpawnPanel extends GPanel
{
	/**
	 * Returns true if the SpawnPanel has been properly configured.
	 */
	public abstract boolean isReady();

	/**
	 * Returns the Painter that reflects this panels GUI.
	 */
	public abstract Painter getPainter();

	/**
	 * Sets in the Painter that should be used to configure the GUI.
	 */
	public abstract void setPainter(Painter aPainter);

}

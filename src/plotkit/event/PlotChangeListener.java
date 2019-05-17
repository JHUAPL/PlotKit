package plotkit.event;

public interface PlotChangeListener
{
	/**
	 * Notification that the plot has changed.
	 */
	public void plotChanged(Object aSrc, Reason aReason);
}

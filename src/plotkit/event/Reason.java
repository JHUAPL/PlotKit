package plotkit.event;

/**
 * Enum of all of the Reasons a plot may be changed.
 */
public enum Reason
{
	/** A data set was changed or updated. */
	DataChange,

	/** A resize action triggered the change. */
	Resize,

	/** A scroll action triggered the change. */
	Scroll,

	/** A zoom action triggered the change. */
	Zoom,

	/** A view action triggered the change. */
	View,

}

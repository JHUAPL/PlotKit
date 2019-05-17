package plotkit.demo.gui.add;

/**
 * Enum that describes where an insertion should be made.
 */
public enum InsertPos
{
	/** Item should be added at the very end. */
	AtEndOfList("At end of list"),

	/** Item should be added just before the current selection. */
	BeforeSelection("Before Selection"),

	/** Item should be added just after the current selection. */
	AfterSelection("After Selection");

	/** The display name associated with the enum. */
	private String dispName;

	InsertPos(String aDisplayName)
	{
		dispName = aDisplayName;
	}

	@Override
	public String toString()
	{
		return dispName;
	}

}

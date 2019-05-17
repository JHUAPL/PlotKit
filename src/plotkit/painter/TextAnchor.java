package plotkit.painter;

/**
 * Enum which describes the point at which text should be anchored too.
 */
public enum TextAnchor
{
	/**
	 * Text should be anchored to the center of the relevant dimension.
	 * <P>
	 * If the dimension is the y-axis then the accent will be considered but will NOT include the descent.
	 */
	Center,

	/**
	 * Text should be anchored to the center of the relevant dimension.
	 * <P>
	 * If the dimension is the y-axis then the accent and the descent will be considered.
	 * <P>
	 * If the dimension is the x-axis then this is equivalent to {@link PivotPoint#Center}
	 */
	CenterFull,

	/**
	 * Text should be anchored to the start of the relevant dimension.
	 * <P>
	 * If the dimension is the y-axis then the baseline of the font will be used.
	 */
	Lead,

	/**
	 * Text should be anchored to the end of the relevant dimension.
	 * <P>
	 * If the dimension is the y-axis then the top of the bounding box will be used.
	 */
	Tail;
}

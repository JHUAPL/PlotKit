package plotkit.time;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * TemporalAdjuster that contains a list of child TemporalAdjusters.
 * <P>
 * This MultiTemporalAdjuster will apply the child TemporalAdjusters in an ordered fashion.
 * <P>
 * This class really should be part of the java library. There are a number of API methods that take a TemporalAdjuster
 * as an argument - and being able to pass a combined TemporalAdjuster would be a useful feature.
 */
public class MultiTemporalAdjuster implements TemporalAdjuster
{
	// Attributes
	private ImmutableList<TemporalAdjuster> childList;

	/**
	 * Constructor which takes a list of TemporalAdjusters
	 */
	public MultiTemporalAdjuster(List<TemporalAdjuster> aChildList)
	{
		childList = ImmutableList.copyOf(aChildList);
	}

	/**
	 * Constructor which takes an array of TemporalAdjusters
	 *
	 * @see MultiTemporalAdjuster
	 */
	public MultiTemporalAdjuster(TemporalAdjuster... aChildArr)
	{
		childList = ImmutableList.copyOf(aChildArr);
	}

	/**
	 * Returns the list of child TemporalAdjusters
	 */
	public ImmutableList<TemporalAdjuster> getChildTemporalAdjusterList()
	{
		return childList;
	}

	@Override
	public Temporal adjustInto(Temporal aTemporal)
	{
		Temporal retTemporal = aTemporal;
		for (TemporalAdjuster aTemporalAdjuster : childList)
			retTemporal = retTemporal.with(aTemporalAdjuster);

		return retTemporal;
	}

}

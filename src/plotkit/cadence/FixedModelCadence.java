package plotkit.cadence;

import java.util.Iterator;
import java.util.List;

import plotkit.AxisTransform;
import plotkit.misc.LogicError;

import com.google.common.collect.*;

/**
 * Cadence where the ticks occur at well defined fixed positions (model space).
 * <P>
 * The position of of ticks will be specified at construction time.
 */
public class FixedModelCadence implements Cadence
{
	// Attributes
	private final ImmutableList<Double> modelValueList;

	/**
	 * @see FixedModelCadence
	 *
	 * @param aModelValueList
	 *        The list of model values where ticks should be placed.
	 */
	public FixedModelCadence(List<Double> aModelValueList)
	{
		modelValueList = ImmutableList.copyOf(aModelValueList);

		// Insanity checks
		if (modelValueList.isEmpty() == true)
			throw new LogicError("Invalid args -> Empty List: modelValueList");
	}

	/**
	 * Returns the frequency of ticks (pixel space). Ex: Return value of 250 will mean there will be ticks every 250
	 * pixels.
	 */
	public ImmutableList<Double> getModelValues()
	{
		return modelValueList;
	}

	@Override
	public Iterator<Double> getIter(AxisTransform aAxisTransform, double aAxisBeg, double aAxisEnd)
	{
		// Compute the model space range corresponding to the axis space (range).
		double plotBeg = aAxisTransform.getPlotValForAxisVal(aAxisBeg);
		double plotEnd = aAxisTransform.getPlotValForAxisVal(aAxisEnd);

		return Iterators.filter(modelValueList.iterator(), Range.closed(plotBeg, plotEnd));
	}

	@Override
	public String toString()
	{
		return "[FMC items: " + modelValueList.size() + "]";
	}
}

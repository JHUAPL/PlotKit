package plotkit.demo.data.text;

import java.util.*;

import plotkit.text.TextProvider;

import com.google.common.collect.ImmutableList;

/**
 * Object that will be used to route notification of TextProvider change events.
 */
public class TextProviderChanger
{
	private List<TextProviderChangeListener> listenerList;

	public TextProviderChanger()
	{
		listenerList = new ArrayList<>();
	}

	/**
	 * Registers a Listener with this TextProviderChanger
	 */
	public synchronized void addListener(TextProviderChangeListener aListener)
	{
		Objects.requireNonNull(aListener);

		listenerList.add(aListener);
	}

	/**
	 * Sends out notification to the listeners of the specified TextProvider.
	 */
	public synchronized void notifyListeners(TextProvider aTextProvider)
	{
		ImmutableList<TextProviderChangeListener> tmpList = ImmutableList.copyOf(listenerList);

		for (TextProviderChangeListener aListener : tmpList)
			aListener.textProviderChanged(this, aTextProvider);
	}

}

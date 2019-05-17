package plotkit.demo.data.text;

import plotkit.text.TextProvider;

/**
 * Interface that defines an object as listening for when a TextProvider is changed.
 */
public interface TextProviderChangeListener
{
	/**
	 * Method will be called when a TextProvider has been changed.
	 * 
	 * @param source
	 *        The object that caused the event.
	 * @param aTextProvider
	 *        The new TextProvider to be used.
	 */
	public void textProviderChanged(Object source, TextProvider aTextProvider);

}

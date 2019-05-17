package plotkit.demo.misc;

import java.awt.Component;

import javax.swing.*;

/**
 * Renderer which will show just the simple class name of the specified object.
 * <P>
 * If the specified object is a class then the corresponding simple class name will be used.
 * <P>
 * If the specified object is just a generic object, then the simple class name will be used from the Object's class.
 */
public class PlainClassNameRenderer extends DefaultListCellRenderer
{

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object aObj, int index, boolean isSelected,
			boolean hasFocus)
	{
		String tmpStr = null;
		if (aObj instanceof Class)
			tmpStr = ((Class<?>) aObj).getSimpleName();
		else if (aObj != null)
			tmpStr = aObj.getClass().getSimpleName();

		JLabel retL = (JLabel) super.getListCellRendererComponent(list, aObj, index, isSelected, hasFocus);
		retL.setText(tmpStr);
		return retL;
	}

}

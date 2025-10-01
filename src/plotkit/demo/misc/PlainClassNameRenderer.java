// Copyright (C) 2024 The Johns Hopkins University Applied Physics Laboratory LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package plotkit.demo.misc;

import java.awt.Component;

import javax.swing.*;

/**
 * Renderer which will show just the simple class name of the specified object.
 * <P>
 * If the specified object is a class then the corresponding simple class name will be used.
 * <P>
 * If the specified object is just a generic object, then the simple class name will be used from the Object's class.
 *
 * @author lopeznr1
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

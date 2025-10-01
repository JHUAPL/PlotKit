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
package plotkit.demo.gui;

import glum.gui.panel.itemList.ItemHandler;
import plotkit.Painter;
import plotkit.misc.LogicError;

/**
 * {@link ItemHandler} for handaling {@link Painter}s.
 * 
 * @author lopeznr1
 */
public class PainterItemHandler implements ItemHandler<Painter, LookUp>
{
	@Override
	public Object getValue(Painter aItem, LookUp aLookUp)
	{
		switch (aLookUp)
		{
			case Description:
				return aItem.getDescription();

			case Type:
				return aItem.getClass().getSimpleName();

			default:
				return null;
		}
	}

	@Override
	public void setValue(Painter aItem, LookUp aLookUp, Object aValue)
	{
		throw new LogicError("Unsupported operation");
	}

}

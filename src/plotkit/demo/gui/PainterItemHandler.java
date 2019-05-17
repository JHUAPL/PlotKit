package plotkit.demo.gui;

import glum.gui.panel.itemList.BasicItemHandler;
import glum.gui.panel.itemList.query.QueryAttribute;
import glum.gui.panel.itemList.query.QueryComposer;

import java.util.Collection;

import plotkit.Painter;
import plotkit.misc.LogicError;

public class PainterItemHandler extends BasicItemHandler<Painter>
{
	public PainterItemHandler(QueryComposer<?> aComposer)
	{
		super(aComposer.getItems());
	}

	public PainterItemHandler(Collection<QueryAttribute> aQueryAttrList)
	{
		super(aQueryAttrList);
	}

	@Override
	public Object getColumnValue(Painter aItem, int colNum)
	{
		// Insanity check
		if (colNum < 0 && colNum >= fullAttributeList.size())
			return null;

		LookUp lookUp = (LookUp) fullAttributeList.get(colNum).refKey;
		switch (lookUp)
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
	public void setColumnValue(Painter aItem, int colNum, Object aValue)
	{
		throw new LogicError("Unsupported operation");
	}

}

package plotkit.demo.gui;

import glum.gui.panel.itemList.BasicItemHandler;
import glum.gui.panel.itemList.query.QueryComposer;
import plotkit.Painter;
import plotkit.misc.LogicError;

public class PainterItemHandler extends BasicItemHandler<Painter, LookUp>
{
	public PainterItemHandler(QueryComposer<LookUp> aComposer)
	{
		super(aComposer);
	}

	@Override
	public Object getColumnValue(Painter aItem, LookUp aLookUp)
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
	public void setColumnValue(Painter aItem, LookUp aLookUp, Object aValue)
	{
		throw new LogicError("Unsupported operation");
	}

}

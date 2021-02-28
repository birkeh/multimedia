package at.windesign.application.serie;

import org.zkoss.zul.*;

public class serieResultRenderer implements ListitemRenderer
{
	private Listbox serieList;

	@Override
	public void render(Listitem item, Object o, int i) throws Exception
	{
		final serieData data = (serieData) o;

		Listcell selectedCell        = new Listcell();
		Listcell serieNameCell       = new Listcell(data.getSeriesName());
		Listcell serieFirstAiredCell = new Listcell(String.valueOf(data.getSeriesFirstAired()));
		Listcell serieOverviewCell   = new Listcell(data.getSeriesOverview());

		Checkbox checkbox = new Checkbox();
		checkbox.setParent(selectedCell);
		selectedCell.setParent(item);
		serieNameCell.setParent(item);
		serieFirstAiredCell.setParent(item);
		serieOverviewCell.setParent(item);

		item.setValue(data);
	}
}
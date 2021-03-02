package at.windesign.application.serie;

import at.windesign.application.movie.movieData;
import org.zkoss.zul.*;

import java.text.DecimalFormat;

public class serieDiscoverRenderer implements ListitemRenderer
{
	private Listbox serieList;

	@Override
	public void render(Listitem item, Object o, int i) throws Exception
	{
		final serieData data   = (serieData) o;
		DecimalFormat   format = new DecimalFormat("#.0");

		Listcell selectedCell        = new Listcell();
		Listcell serieNameCell       = new Listcell(data.getSeriesName());
		Listcell serieVoteCount      = new Listcell(String.valueOf(data.getSeriesVoteCount()));
		Listcell serieVoteAverage    = new Listcell(format.format(data.getSeriesVoteAverage()));
		Listcell serieFirstAiredCell = new Listcell(String.valueOf(data.getSeriesFirstAired()));
		Listcell serieOverviewCell   = new Listcell(data.getSeriesOverview());

		Checkbox checkbox = new Checkbox();
		checkbox.setParent(selectedCell);
		selectedCell.setParent(item);
		serieNameCell.setParent(item);
		serieVoteCount.setParent(item);
		serieVoteAverage.setParent(item);
		serieFirstAiredCell.setParent(item);
		serieOverviewCell.setParent(item);

		item.setValue(data);
	}
}
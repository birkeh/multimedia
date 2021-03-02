package at.windesign.application.movie;

import org.zkoss.zul.*;

import java.text.DecimalFormat;

public class movieDiscoverRenderer implements ListitemRenderer
{
	private Listbox movieList;

	@Override
	public void render(Listitem item, Object o, int i) throws Exception
	{
		final movieData data   = (movieData) o;
		DecimalFormat format = new DecimalFormat("#.0");

		Listcell selectedCell        = new Listcell();
		Listcell movieNameCell       = new Listcell(data.getMovieTitle());
		Listcell movieVoteCount      = new Listcell(String.valueOf(data.getVoteCount()));
		Listcell movieVoteAverage    = new Listcell(format.format(data.getVoteAverage()));
		Listcell movieFirstAiredCell = new Listcell(String.valueOf(data.getReleaseDate()));
		Listcell movieOverviewCell   = new Listcell(data.getOverview());

		Checkbox checkbox = new Checkbox();
		checkbox.setParent(selectedCell);
		selectedCell.setParent(item);
		movieNameCell.setParent(item);
		movieVoteCount.setParent(item);
		movieVoteAverage.setParent(item);
		movieFirstAiredCell.setParent(item);
		movieOverviewCell.setParent(item);

		item.setValue(data);
	}
}
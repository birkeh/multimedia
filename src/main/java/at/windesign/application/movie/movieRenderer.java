package at.windesign.application.movie;

import at.windesign.application.movie.movieData;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class movieRenderer implements ListitemRenderer
{
	private Listbox movieList;

	@Override
	public void render(Listitem item, Object o, int i) throws Exception
	{
		final movieData data = (movieData) o;

		Listcell movieNameCell       = new Listcell(data.getMovieTitle());
		Listcell movieFirstAiredCell = new Listcell(String.valueOf(data.getReleaseDate()));
		Listcell movieResolutionCell = new Listcell(data.getResolution());
		Listcell movieOverviewCell   = new Listcell(data.getOverview());

		movieNameCell.setStyle(data.getMovieStyle());
		movieNameCell.setParent(item);
		movieFirstAiredCell.setParent(item);
		movieResolutionCell.setParent(item);
		movieOverviewCell.setParent(item);

		item.setValue(data);
	}
}

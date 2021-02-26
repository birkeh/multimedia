package at.windesign.application.movie;

import org.zkoss.zul.*;

public class resultRenderer implements ListitemRenderer
{
	private Listbox movieList;

	@Override
	public void render(Listitem item, Object o, int i) throws Exception
	{
		final movieData data = (movieData) o;

		Listcell selectedCell        = new Listcell();
		Listcell movieNameCell       = new Listcell(data.getMovieTitle());
		Listcell movieFirstAiredCell = new Listcell(String.valueOf(data.getReleaseDate()));
		Listcell movieOverviewCell   = new Listcell(data.getOverview());

		Checkbox checkbox = new Checkbox();
		checkbox.setParent(selectedCell);
		selectedCell.setParent(item);
		movieNameCell.setParent(item);
		movieFirstAiredCell.setParent(item);
		movieOverviewCell.setParent(item);

		item.setValue(data);
	}
}
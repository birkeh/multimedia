package at.windesign.application.movie;

import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.results.ResultList;
import org.apache.http.client.HttpClient;
import org.yamj.api.common.http.SimpleHttpClientBuilder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class movieDiscoverForwardComposer extends GenericForwardComposer<Component>
{
	private static final long            serialVersionUID = 1L;
	private final        movieDataSource ds               = movieDataSource.INSTANCE;
	private              int             opacity          = 80;

	@Wire
	private Window discoverMovie;

	@Wire
	private Groupbox groupboxGenre;

	@Wire
	private Div divGenre;

	@Wire
	private Groupbox groupboxSearch;

	@Wire
	protected Textbox searchText;

	@Wire
	protected Textbox searchYear;

	@Wire
	protected Listbox resultList;

	@Wire
	protected Button searchButton;

	@Wire
	protected Button cancelButton;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		int hD = (int) self.getDesktop().getAttribute("desktopHeight");
		int wD = (int) self.getDesktop().getAttribute("desktopWidth");

		discoverMovie.setWidth(wD - 10 + "px");
		discoverMovie.setHeight(hD - 10 + "px");

		SortedMap<Integer, Listheader> listHeader = new TreeMap<>();

		ListModelList resultListModel = new ListModelList();
		resultList.setModel(resultListModel);
		resultList.setAttribute("listModelList", resultListModel);

		resultList.getListhead().getChildren().clear();
		Listhead   head       = resultList.getListhead();
		Listheader selected   = new Listheader("");
		Listheader movieName  = new Listheader("Movie Name");
		Listheader voteCount = new Listheader("vote count");
		Listheader voteAverage = new Listheader("vote average");
		Listheader firstAired = new Listheader("First Aired");
		Listheader overview   = new Listheader("Overview");

		selected.setHflex("min");
		movieName.setHflex("min");
		voteCount.setHflex("min");
		voteAverage.setHflex("min");
		firstAired.setHflex("min");
		overview.setHflex("min");

		head.appendChild(selected);
		head.appendChild(movieName);
		head.appendChild(voteCount);
		head.appendChild(voteAverage);
		head.appendChild(firstAired);
		head.appendChild(overview);

		movieDiscoverRenderer renderer = new movieDiscoverRenderer();
		resultList.setItemRenderer(renderer);

		HttpClient        httpClient = new SimpleHttpClientBuilder().build();
		TheMovieDbApi     api        = new TheMovieDbApi("a33271b9e54cdcb9a80680eaf5522f1b", httpClient);
		ResultList<Genre> genres     = api.getGenreMovieList("de-DE");

		List<Genre> genreList = genres.getResults();
		int         cols      = genreList.size() / 5;
		if(cols < 1)
			cols = 1;
		else if(genreList.size() % 5 != 0)
			cols++;

		divGenre.setStyle("display: grid; grid-template-columns: repeat(" + cols + ", 1fr); grid-auto-rows: 40px; align-items: left; justify-items: left;");

		for(Genre genre : genreList)
		{
			Checkbox checkbox = new Checkbox();
			checkbox.setLabel(genre.getName());
			checkbox.setAttribute("genreID", genre.getId());
			divGenre.insertBefore(checkbox, null);
		}
	}
}

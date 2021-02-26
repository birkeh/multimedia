package at.windesign.application.movie;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.enumeration.SearchType;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.HttpTools;
import org.apache.http.client.HttpClient;
import org.yamj.api.common.http.SimpleHttpClientBuilder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class movieSearchSelectorComposer extends SelectorComposer<Component>
{
	private static final long    serialVersionUID = 1L;
	private              Listbox m_movieList;

	@Wire
	private Window searchMovie;

	@Wire
	protected Textbox searchText;

	@Wire
	protected Textbox searchYear;

	@Wire
	protected Listbox resultList;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		final Execution execution = Executions.getCurrent();

		if(execution.getArg().containsKey("movieList"))
			m_movieList = (Listbox) execution.getArg().get("movieList");
	}

	@Listen("onClick = #searchButton")
	public void doSearch()
	{
		try
		{
			search();
		}
		catch(Exception e)
		{
		}
	}

	@Listen("onClick = #saveButton")
	public void save()
	{
		ListModelList   resultListModel = (ListModelList) resultList.getModel();
		List<movieData> dataList        = resultListModel.getInnerList();

		for(int index = 0; index < resultListModel.size(); index++)
		{
			Listitem item = resultList.getItemAtIndex(index);

			if(item != null)
			{
				Listcell cell = (Listcell) item.getChildren().get(0);

				if(cell != null)
				{
					Checkbox box = (Checkbox) cell.getChildren().get(0);

					if(box != null)
					{
						if(box.isChecked())
						{
							movieData data     = dataList.get(index);
							movieData newMovie = new movieData();

							try
							{
								newMovie.fromTMDB(data.getMovieID());
								Messagebox.show(newMovie.getMovieTitle());
							}
							catch(Exception e)
							{
							}
						}
					}
				}
			}
		}
		searchMovie.onClose();
	}

	@Listen("onClick = #cancelButton")
	public void cancel()
	{
		searchMovie.onClose();
	}

	public void search() throws MovieDbException
	{
		int maxPage = 2;
		int page;

		HttpClient httpClient;

		httpClient = new SimpleHttpClientBuilder().build();

		TheMovieDbApi api = new TheMovieDbApi("a33271b9e54cdcb9a80680eaf5522f1b", httpClient);

		String sText  = searchText.getText();
		String sYearT = searchYear.getText();
		int    sYear;

		if(sYearT.isEmpty())
			sYear = 0;
		else
			sYear = Integer.parseInt(sYearT);

		if(sText.isEmpty())
			return;

		movieData     movie;
		ListModelList resultListModel = (ListModelList) resultList.getModel();

		resultListModel.clear();

		for(page = 1; page <= maxPage; page++)
		{
			ResultList<MovieInfo> movies = api.searchMovie(sText, page, "de-DE", true, sYear, 0, SearchType.PHRASE);
			if(movies.getResults().size() == 0)
				break;

			for(MovieInfo resultMovie : movies.getResults())
			{
				if(!exist(resultMovie.getId()))
				{
					movie = new movieData();

					movie.setMovieID(resultMovie.getId());
					movie.setMovieTitle(resultMovie.getTitle());
					movie.setReleaseDate(string2Date(resultMovie.getReleaseDate()));
					movie.setOverview(resultMovie.getOverview());

					resultListModel.add(movie);
					movie.setModel(resultListModel);
				}
			}
		}
	}

	Date string2Date(String strDate)
	{
		if(strDate == null)
			return new Date(1900, 1, 1);
		else if(strDate.isEmpty())
			return new Date(1900, 1, 1);
		else
			return Date.valueOf(strDate);
	}

	boolean exist(int id)
	{
		ListModelList   list     = (ListModelList) m_movieList.getModel();
		List<movieData> dataList = list.getInnerList();

		for(movieData data : dataList)
		{
			if(id == data.getMovieID())
				return true;
		}
		return false;
	}
}

package at.windesign.application.movie;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.discover.WithBuilder;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.results.ResultList;
import org.apache.http.client.HttpClient;
import org.yamj.api.common.http.SimpleHttpClientBuilder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;

public class movieDiscoverSelectorComposer extends SelectorComposer<Component>
{
	private static final long    serialVersionUID = 1L;
	private              Listbox m_movieList;

	@Wire
	private Window discoverMovie;

	@Wire
	private Div divGenre;

	@Wire
	protected Textbox searchText;

	@Wire
	protected Textbox searchYearGTE;

	@Wire
	protected Textbox searchYearLTE;

	@Wire
	protected Textbox searchVoteCountGTE;

	@Wire
	protected Textbox searchVoteCountLTE;

	@Wire
	protected Textbox searchVoteGTE;

	@Wire
	protected Textbox searchVoteLTE;

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
	public void save() throws FileNotFoundException
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
								newMovie.save();
							}
							catch(Exception e)
							{
							}
						}
					}
				}
			}
			OutputStream tempFile    = new FileOutputStream("redir");
			PrintStream  printStream = new PrintStream(tempFile);
			printStream.print("movie");
			printStream.close();

			Executions.sendRedirect("");
		}
		discoverMovie.onClose();
	}

	@Listen("onClick = #cancelButton")
	public void cancel()
	{
		discoverMovie.onClose();
	}

	public void search() throws MovieDbException
	{
		int maxPage = 10;
		int page;

		HttpClient httpClient;
		httpClient = new SimpleHttpClientBuilder().build();

		TheMovieDbApi api = new TheMovieDbApi("a33271b9e54cdcb9a80680eaf5522f1b", httpClient);

		String sText         = searchText.getText();
		String sYearGTE      = searchYearGTE.getText();
		String sYearLTE      = searchYearLTE.getText();
		int    yearGTE;
		int    yearLTE;
		String sVoteCountGTE = searchVoteCountGTE.getText();
		String sVoteCountLTE = searchVoteCountLTE.getText();
		int    voteCountGTE;
		int    voteCountLTE;
		String sVoteGTE      = searchVoteGTE.getText();
		String sVoteLTE      = searchVoteLTE.getText();
		float  voteGTE;
		float  voteLTE;

		if(sYearGTE.isEmpty())
			yearGTE = 0;
		else
			yearGTE = Integer.parseInt(sYearGTE);

		if(sYearLTE.isEmpty())
			yearLTE = 0;
		else
			yearLTE = Integer.parseInt(sYearLTE);

		if(yearGTE > 0 && yearLTE == 0)
			yearLTE = 5000;

		if(sVoteCountGTE.isEmpty())
			voteCountGTE = 0;
		else
			voteCountGTE = Integer.parseInt(sVoteCountGTE);

		if(sVoteCountLTE.isEmpty())
			voteCountLTE = 0;
		else
			voteCountLTE = Integer.parseInt(sVoteCountLTE);

		if(sVoteGTE.isEmpty())
			voteGTE = 0;
		else
			voteGTE = Float.parseFloat(sVoteGTE);

		if(sVoteLTE.isEmpty())
			voteLTE = 0;
		else
			voteLTE = Float.parseFloat(sVoteLTE);

		WithBuilder genres = null;
		boolean     first  = true;

		List<Checkbox> genreList = divGenre.getChildren();
		for(Checkbox genre : genreList)
		{
			if(genre.isChecked())
			{
				int genreID = (int) genre.getAttribute("genreID");
				if(first)
				{
					genres = new WithBuilder(genreID);
					first  = false;
				}
				else
					genres.and(genreID);
			}
		}

		movieData     movie;
		ListModelList resultListModel = (ListModelList) resultList.getModel();

		resultListModel.clear();

		for(page = 1; page <= maxPage; page++)
		{
			Discover discover = new Discover();
			discover.page(page);
			discover.language("de-DE");
			discover.includeAdult(true);

			if(yearGTE != 0)
				discover.primaryReleaseDateGte(yearGTE + "-01-01");
			else
				discover.primaryReleaseDateGte("");
			if(yearLTE != 0)
				discover.primaryReleaseDateLte(yearLTE + "-12-31");
			else
				discover.primaryReleaseDateLte("");
			discover.voteCountGte(voteCountGTE);
			discover.voteCountLte(voteCountLTE);
			discover.voteAverageGte(voteGTE);
			discover.voteAverageLte(voteLTE);
			discover.withGenres(genres);
			discover.withKeywords(sText);

			ResultList<MovieBasic> movies = api.getDiscoverMovies(discover);
			if(movies.getResults().size() == 0)
				break;

			for(MovieBasic resultMovie : movies.getResults())
			{
				if(!exist(resultMovie.getId()))
				{
					movie = new movieData();

					movie.setMovieID(resultMovie.getId());
					movie.setMovieTitle(resultMovie.getTitle());
					movie.setVoteCount(resultMovie.getVoteCount());
					movie.setVoteAverage(resultMovie.getVoteAverage());
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

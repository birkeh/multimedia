package at.windesign.application.serie;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.discover.WithBuilder;
import com.omertron.themoviedbapi.model.tv.TVBasic;
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
import java.util.List;

public class serieDiscoverSelectorComposer extends SelectorComposer<Component>
{
	private static final long    serialVersionUID = 1L;
	private              Listbox m_serieList;

	@Wire
	private Window discoverSerie;

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

		if(execution.getArg().containsKey("serieList"))
			m_serieList = (Listbox) execution.getArg().get("serieList");
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
		List<serieData> dataList        = resultListModel.getInnerList();

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
							serieData data     = dataList.get(index);
							serieData newSerie = new serieData();

							try
							{
								newSerie.fromTMDB(data.getSeriesID());
								newSerie.save();
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
			printStream.print("serie");
			printStream.close();

			Executions.sendRedirect("");
		}
		discoverSerie.onClose();
	}

	@Listen("onClick = #cancelButton")
	public void cancel()
	{
		discoverSerie.onClose();
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

		serieData     serie;
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

			ResultList<TVBasic> series = api.getDiscoverTV(discover);
			if(series.getResults().size() == 0)
				break;

			for(TVBasic resultSerie : series.getResults())
			{
				if(!exist(resultSerie.getId()))
				{
					serie = new serieData();

					serie.setSeriesID(resultSerie.getId());
					serie.setSeriesName(resultSerie.getName());
					serie.setSeriesVoteCount(resultSerie.getVoteCount());
					serie.setSeriesVoteAverage(resultSerie.getVoteAverage());
					serie.setSeriesFirstAired(string2Date(resultSerie.getFirstAirDate()));
					serie.setSeriesOverview(resultSerie.getOverview());

					resultListModel.add(serie);
					serie.setModel(resultListModel);
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
		ListModelList   list     = (ListModelList) m_serieList.getModel();
		List<serieData> dataList = list.getInnerList();

		for(serieData data : dataList)
		{
			if(id == data.getSeriesID())
				return true;
		}
		return false;
	}
}

package at.windesign.application.serie;

import at.windesign.application.movie.movieData;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.enumeration.SearchType;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
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

public class serieSearchSelectorComposer extends SelectorComposer<Component>
{
	private static final long    serialVersionUID = 1L;
	private              Listbox m_serieList;

	@Wire
	private Window searchSerie;

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
		searchSerie.onClose();
	}

	@Listen("onClick = #cancelButton")
	public void cancel()
	{
		searchSerie.onClose();
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

		serieData     serie;
		ListModelList resultListModel = (ListModelList) resultList.getModel();

		resultListModel.clear();

		for(page = 1; page <= maxPage; page++)
		{
			ResultList<TVBasic> series = api.searchTV(sText, page, "de-DE", sYear, SearchType.PHRASE);
			if(series.getResults().size() == 0)
				break;

			for(TVBasic resultSerie : series.getResults())
			{
				if(!exist(resultSerie.getId()))
				{
					serie = new serieData();

					serie.setSeriesID(resultSerie.getId());
					serie.setSeriesName(resultSerie.getName());
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

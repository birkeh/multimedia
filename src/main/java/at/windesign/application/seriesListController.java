package at.windesign.application;


import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedMap;
import java.util.TreeMap;

public class seriesListController extends GenericForwardComposer
{
	protected Listbox        seriesList; // autowired
	private   ListModelList  seriesListModel; // the model of the listbox
	private   seriesRenderer renderer;

	private final SeriesDataSource ds = SeriesDataSource.INSTANCE;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		int minSeason = 0;
		int maxSeason = 0;

		try
		{
			Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT		MIN(seasonNumber) minSeason," +
							"       MAX(seasonNumber) maxSeason" +
							" FROM		season;"
			);

			while(rs.next())
			{
				minSeason = rs.getInt("minSeason");
				maxSeason = rs.getInt("maxSeason");
			}
		} catch(SQLException e)
		{
			e.printStackTrace();
		}

		seriesListModel = new ListModelList();
		seriesList.setModel(seriesListModel);

		Listhead   head       = seriesList.getListhead();
		Listheader seriesName = new Listheader("Series Name");
		Listheader firstAired = new Listheader("First Aired");
		Listheader resolution = new Listheader("Resolution");

		seriesName.setHflex("min");
		firstAired.setHflex("min");
		resolution.setHflex("min");

		head.appendChild(seriesName);
		head.appendChild(firstAired);
		head.appendChild(resolution);

		if(minSeason < 1)
			minSeason = 1;

		for(int i = minSeason;i <= maxSeason;i++)
		{
			Listheader seasonHeader = new Listheader("Season " + i);
			seasonHeader.setHflex("min");
			head.appendChild(seasonHeader);
		}

		renderer = new seriesRenderer();
		seriesList.setItemRenderer(renderer);

		try
		{
			Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT		serie.seriesID seriesID," +
							" 			serie.seriesName seriesName," +
							"           YEAR(serie.firstAired) seriesFirstAired," +
							"           serie.resolution seriesResolution," +
							"           serie.cliffhanger seriesCliffhanger," +
							"           serie.status seriesStatus," +
							"           serie.download seriesDownload," +
							"           serie.posterPath seriesPoster," +
							"           serie.backdropPath seriesBackdrop," +
							"           season.seasonNumber seasonNumber," +
							"           episode.episodeNumber episodeNumber," +
							"           episode.state episodeState," +
							"           ( SELECT MIN(s.seasonNumber) FROM season s WHERE s.seriesID = serie.seriesID ) minSeason," +
							"           ( SELECT MAX(s.seasonNumber) FROM season s WHERE s.seriesID = serie.seriesID ) maxSeason" +
							" FROM		serie" +
							" LEFT JOIN	season ON serie.seriesID = season.seriesID" +
							" LEFT JOIN	episode ON serie.seriesID = episode.seriesID AND season.seasonNumber = episode.seasonNumber" +
							" WHERE     season.seasonNumber != 0 OR" +
							"           serie.seriesID >= 1000000" +
							" ORDER BY	serie.seriesName," +
							" 			serie.firstAired," +
							" 			season.seasonNumber," +
							" 			episode.episodeNumber;"
			);

			// fetch all events from database
			Serie                       serie        = new Serie();
			int                         oldID        = -1;
			SortedMap<Integer, Integer> episodeState = new TreeMap<>();

			while(rs.next())
			{
				if(rs.getInt("seriesID") != oldID)
				{
					if(oldID != -1)
					{
						serie.setEpisodeState(episodeState);
						seriesListModel.add(serie);
						episodeState = new TreeMap<>();
					}

					oldID = rs.getInt("seriesID");
					serie = new Serie();

					serie.setSeriesID(rs.getInt("seriesID"));
					serie.setSeriesName(rs.getString("seriesName"));
					serie.setSeriesFirstAired(rs.getInt("seriesFirstAired"));
					serie.setSeriesResolution(rs.getString("seriesResolution"));
					serie.setSeriesCliffhanger(rs.getBoolean("seriesCliffhanger"));
					serie.setSeriesStatus(rs.getString("seriesStatus"));
					serie.setSeriesDownload(rs.getString("seriesDownload"));
					serie.setSeriesPoster(rs.getString("seriesPoster"));
					serie.setSeriesBackdrop(rs.getString("seriesBackdrop"));
					serie.setMinSeason(rs.getInt("minSeason"));
					serie.setMaxSeason(rs.getInt("maxSeason"));
				}
				episodeState.put((rs.getInt("seasonNumber") << 8) + rs.getInt("episodeNumber"), rs.getInt("episodeState"));
			}
		} catch(
				SQLException e)

		{
			e.printStackTrace();
		} finally
		{
			ds.close();
		}
	}
}

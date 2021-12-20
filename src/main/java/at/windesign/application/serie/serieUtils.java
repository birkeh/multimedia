package at.windesign.application.serie;

import at.windesign.application.movie.movieDataSource;
import org.zkoss.zul.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class serieUtils
{
	static private Integer stateWidth  = 4;
	static private Integer stateHeight = 20;

	static public void loadSeries(serieDataSource ds, Listbox seriesList)
	{
		List<serieData>                serieList  = new ArrayList<>();
		int                            minSeason  = 0;
		int                            maxSeason  = 0;
		SortedMap<Integer, Integer>    maxEpisode = new TreeMap<>();
		SortedMap<Integer, Listheader> listHeader = new TreeMap<>();

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
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		ListModelList seriesListModel = new ListModelList();
		seriesList.setModel(seriesListModel);
		seriesList.setAttribute("listModelList", seriesListModel);

		seriesList.getListhead().getChildren().clear();
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

		for(int i = minSeason; i <= maxSeason; i++)
		{
			Listheader seasonHeader = new Listheader("Season " + i);
			head.appendChild(seasonHeader);
			maxEpisode.put(i, 0);
			listHeader.put(i, seasonHeader);
		}

		serieRenderer renderer = new serieRenderer();
		seriesList.setItemRenderer(renderer);

		try
		{
			Statement stmt = ds.getStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT		serie.seriesID seriesID," +
							" 			serie.seriesName seriesName," +
							"			serie.originalName seriesOriginalName," +
							"           serie.backdropPath seriesBackdrop," +
							"			serie.createdBy seriesCreatedBy," +
							"			serie.homepage seriesHomepage," +
							"			serie.lastAired seriesLastAired," +
							"			serie.languages seriesLanguages," +
							"			serie.networks seriesNetworks," +
							"			serie.nrEpisodes seriesNrEpisodes," +
							"			serie.nrSeasons seriesNrSeasons," +
							"			serie.originCountries seriesOriginCountries," +
							"			serie.originalLanguage seriesOriginalLanguage," +
							"			serie.popularity seriesPopularity," +
							"           serie.posterPath seriesPoster," +
							"			serie.productionCompanies seriesProductionCompanies," +
							"			serie.type seriesType," +
							"			serie.voteAverage seriesVoteAverage," +
							"			serie.voteCount seriesVoteCount," +
							"			serie.overview seriesOverview," +
							"           serie.firstAired seriesFirstAired," +
							"			serie.cast seriesCast," +
							"			serie.crew seriesCrew," +
							"			serie.genre seriesGenre," +
							"			serie.imdbid seriesIMDBID," +
							"			serie.freebasemid seriesFreebaseMID," +
							"			serie.freebaseid seriesFreebaseID," +
							"			serie.tvdbid seriesTVDBID," +
							"			serie.tvrageid seriesTVRageID," +
							"           serie.status seriesStatus," +
							"           serie.download seriesDownload," +
							"			serie.localPath seriesLocalPath," +
							"           serie.resolution seriesResolution," +
							"           serie.cliffhanger seriesCliffhanger," +
							"			season._id season_ID," +
							"			IF(IFNULL(season.airDate, '1900-01-01') = '','1900-01-01', IFNULL(season.airDate, '1900-01-01')) seasonAirDate," +
							"			season.name seasonName," +
							"			season.overview seasonOverview," +
							"			season.id seasonID," +
							"			season.posterPath seasonPosterPath," +
							"           season.seasonNumber seasonNumber," +
							"			episode.id episodeID," +
							"			episode.name episodeName," +
							"           episode.episodeNumber episodeNumber," +
							"			IF(IFNULL(episode.airDate, '1900-01-01') = '','1900-01-01', IFNULL(episode.airDate, '1900-01-01')) episodeAirDate," +
							"			episode.guestStars episodeGuestStars," +
							"			episode.overview episodeOverview," +
							"			episode.productioncode episodeProductionCode," +
							"			episode.stillPath episodeStillPath," +
							"			episode.voteAverage episodeVoteAverage," +
							"			episode.voteCount episodeVoteCount," +
							"			episode.crew episodeCrew," +
							"           episode.state episodeState" +
							" FROM		serie" +
							" LEFT JOIN	season ON serie.seriesID = season.seriesID" +
							" LEFT JOIN	episode ON serie.seriesID = episode.seriesID AND season.seasonNumber = episode.seasonNumber" +
							" WHERE     (season.seasonNumber != 0 AND episode.episodeNumber IS NOT NULL) OR" +
							"           serie.seriesID >= 1000000" +
							" ORDER BY	serie.seriesName," +
							" 			serie.firstAired," +
							" 			season.seasonNumber," +
							" 			episode.episodeNumber;"
			);

			// fetch all events from database
			serieData   serie        = new serieData();
			seasonData  season       = new seasonData();
			episodeData episode      = new episodeData();
			int         oldSerieID   = -1;
			int         oldSeasonID  = -1;
			int         oldEpisodeID = -1;

			serie.setSeriesID(-1);

			while(rs.next())
			{
				int serieID   = rs.getInt("seriesID");
				int seasonID  = rs.getInt("seasonID");
				int episodeID = rs.getInt("episodeID");

				if(serieID != oldSerieID)
				{
					oldSerieID = serieID;
					serie = newSerie(rs);
					serieList.add(serie);

					oldSeasonID = seasonID;
					season = newSeason(rs);

					oldEpisodeID = episodeID;
					episode = newEpisode(rs);

					switch(rs.getInt("episodeState"))
					{
						case 1:
							serie.addStateInit();
							break;
						case 2:
							serie.addStateProd();
							break;
						case 3:
							serie.addStateDone();
							break;
					}
				}
				else if(seasonID != oldSeasonID)
				{
					oldSeasonID = seasonID;
					season = newSeason(rs);

					oldEpisodeID = episodeID;
					episode = newEpisode(rs);

					switch(rs.getInt("episodeState"))
					{
						case 1:
							serie.addStateInit();
							break;
						case 2:
							serie.addStateProd();
							break;
						case 3:
							serie.addStateDone();
							break;
					}
				}
				else if(episodeID != oldEpisodeID)
				{
					oldEpisodeID = episodeID;
					episode = newEpisode(rs);

					switch(rs.getInt("episodeState"))
					{
						case 1:
							serie.addStateInit();
							break;
						case 2:
							serie.addStateProd();
							break;
						case 3:
							serie.addStateDone();
							break;
					}

					if(season.getSeasonNumber() != 0)
					{
						if(maxEpisode.get(season.getSeasonNumber()) < episode.getEpisodeNumber())
							maxEpisode.put(season.getSeasonNumber(), episode.getEpisodeNumber());
					}
				}

				season.setSerie(serie);
				episode.setSerie(serie);
				episode.setSeason(season);

				serie.setSeason(season.getSeasonNumber(), season);
				season.setEpisode(episode.getEpisodeNumber(), episode);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ds.close();
		}

		for(serieData serie : serieList)
		{
			seriesListModel.add(serie);
			serie.setModel(seriesListModel);
		}

		for(int i = minSeason; i <= maxSeason; i++)
		{
			int        size   = maxEpisode.get(i) * stateWidth;
			Listheader header = listHeader.get(i);
			header.setWidth(size + 40 + "px");
		}
	}

	static protected episodeData newEpisode(ResultSet rs) throws SQLException
	{
		episodeData episode = new episodeData();

		episode.setEpisodeID(rs.getInt("episodeID"));
		episode.setEpisodeName(rs.getString("episodeName"));
		episode.setEpisodeNumber(rs.getInt("episodeNumber"));
		episode.setEpisodeAirDate(rs.getDate("episodeAirDate"));
		episode.setEpisodeGuestStars(rs.getString("episodeGuestStars"));
		episode.setEpisodeOverview(rs.getString("episodeOverview"));
		episode.setEpisodeProductionCode(rs.getString("episodeProductionCode"));
		episode.setEpisodeStillPath(rs.getString("episodeStillPath"));
		episode.setEpisodeVoteAverage(rs.getDouble("episodeVoteAverage"));
		episode.setEpisodeVoteCount(rs.getInt("episodeVoteCount"));
		episode.setEpisodeCrew(rs.getString("episodeCrew"));
		episode.setEpisodeState(rs.getInt("episodeState"));

		return episode;
	}

	static protected seasonData newSeason(ResultSet rs) throws SQLException
	{
		seasonData season = new seasonData();

		season.setSeason_ID(rs.getString("season_ID"));
		season.setSeasonAirDate(rs.getDate("seasonAirDate"));
		season.setSeasonName(rs.getString("seasonName"));
		season.setSeasonOverview(rs.getString("seasonOverview"));
		season.setSeasonID(rs.getInt("seasonID"));
		season.setSeasonPosterPath(rs.getString("seasonPosterPath"));
		season.setSeasonNumber(rs.getInt("seasonNumber"));

		return season;
	}

	static protected serieData newSerie(ResultSet rs) throws SQLException
	{
		serieData serie = new serieData();

		serie.setSeriesID(rs.getInt("seriesID"));
		serie.setSeriesName(rs.getString("seriesName"));
		serie.setSeriesOriginalName(rs.getString("seriesOriginalName"));
		serie.setSeriesBackdrop(rs.getString("seriesBackdrop"));
		serie.setSeriesCreatedBy(rs.getString("seriesCreatedBy"));
		serie.setSeriesHomepage(rs.getString("seriesHomepage"));
		serie.setSeriesLastAired(rs.getDate("seriesLastAired"));
		serie.setSeriesLanguages(rs.getString("seriesLanguages"));
		serie.setSeriesNetworks(rs.getString("seriesNetworks"));
		serie.setSeriesNrEpisodes(rs.getInt("seriesNrEpisodes"));
		serie.setSeriesNrSeasons(rs.getInt("seriesNrSeasons"));
		serie.setSeriesOriginCountries(rs.getString("seriesOriginCountries"));
		serie.setSeriesOriginalLanguage(rs.getString("seriesOriginalLanguage"));
		serie.setSeriesPopularity(rs.getDouble("seriesPopularity"));
		serie.setSeriesPoster(rs.getString("seriesPoster"));
		serie.setSeriesProductionCompanies(rs.getString("seriesProductionCompanies"));
		serie.setSeriesType(rs.getString("seriesType"));
		serie.setSeriesVoteAverage(rs.getDouble("seriesVoteAverage"));
		serie.setSeriesVoteCount(rs.getInt("seriesVoteCount"));
		serie.setSeriesOverview(rs.getString("seriesOverview"));
		serie.setSeriesFirstAired(rs.getDate("seriesFirstAired"));
		serie.setSeriesCast(rs.getString("seriesCast"));
		serie.setSeriesCrew(rs.getString("seriesCrew"));
		serie.setSeriesGenre(rs.getString("seriesGenre"));
		serie.setSeriesIMDBID(rs.getString("seriesIMDBID"));
		serie.setSeriesFreebaseMID(rs.getString("seriesFreebaseMID"));
		serie.setSeriesFreebaseID(rs.getString("seriesFreebaseID"));
		serie.setSeriesTVDBID(rs.getString("seriesTVDBID"));
		serie.setSeriesTVRageID(rs.getString("seriesTVRageID"));
		serie.setSeriesStatus(rs.getString("seriesStatus"));
		serie.setSeriesDownload(rs.getString("seriesDownload"));
		serie.setSeriesLocalPath(rs.getString("seriesLocalPath"));
		serie.setSeriesResolution(rs.getString("seriesResolution"));
		serie.setSeriesCliffhanger(rs.getBoolean("seriesCliffhanger"));

		return serie;
	}

	static public void serieMetrics(Tab tabSeries, serieDataSource ds)
	{
		int seriesTotal = 0;

		try
		{
			Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT COUNT(1) CNT " +
					"FROM     multimedia.serie;");

			while(rs.next())
			{
				seriesTotal = rs.getInt("CNT");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ds.close();
		}

		tabSeries.setLabel("TV Shows (T: " + seriesTotal + ")");
	}
}

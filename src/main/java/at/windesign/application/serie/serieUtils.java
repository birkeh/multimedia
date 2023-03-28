package at.windesign.application.serie;

import at.windesign.application.movie.movieDataSource;
import org.ini4j.Ini;
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

	static public void loadSeries(serieDataSource ds, Listbox seriesList, boolean useFilter, boolean filterInitialize, boolean filterInProgress, boolean filterDone)
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
//OPTIMIZE					"			serie.originalName seriesOriginalName," +
					"           serie.backdropPath seriesBackdrop," +
//OPTIMIZE					"			serie.createdBy seriesCreatedBy," +
//OPTIMIZE					"			serie.homepage seriesHomepage," +
//OPTIMIZE					"			serie.lastAired seriesLastAired," +
//OPTIMIZE					"			serie.languages seriesLanguages," +
//OPTIMIZE					"			serie.networks seriesNetworks," +
					"			serie.nrEpisodes seriesNrEpisodes," +
					"			serie.nrSeasons seriesNrSeasons," +
					"			serie.originCountries seriesOriginCountries," +
//OPTIMIZE					"			serie.originalLanguage seriesOriginalLanguage," +
//OPTIMIZE					"			serie.popularity seriesPopularity," +
//OPTIMIZE					"           serie.posterPath seriesPoster," +
					"			serie.productionCompanies seriesProductionCompanies," +
//OPTIMIZE					"			serie.type seriesType," +
//OPTIMIZE					"			serie.voteAverage seriesVoteAverage," +
//OPTIMIZE					"			serie.voteCount seriesVoteCount," +
					"			serie.overview seriesOverview," +
					"           serie.firstAired seriesFirstAired," +
					"			serie.cast seriesCast," +
					"			serie.crew seriesCrew," +
					"			serie.genre seriesGenre," +
//OPTIMIZE					"			serie.imdbid seriesIMDBID," +
//OPTIMIZE					"			serie.freebasemid seriesFreebaseMID," +
//OPTIMIZE					"			serie.freebaseid seriesFreebaseID," +
//OPTIMIZE					"			serie.tvdbid seriesTVDBID," +
//OPTIMIZE					"			serie.tvrageid seriesTVRageID," +
					"           serie.status seriesStatus," +
					"           serie.download seriesDownload," +
					"			serie.localPath seriesLocalPath," +
					"           serie.resolution seriesResolution," +
					"           serie.cliffhanger seriesCliffhanger," +
//OPTIMIZE					"			season._id season_ID," +
//OPTIMIZE					"			IF(IFNULL(season.airDate, '1900-01-01') = '','1900-01-01', IFNULL(season.airDate, '1900-01-01')) seasonAirDate," +
//OPTIMIZE					"			season.name seasonName," +
//OPTIMIZE					"			season.overview seasonOverview," +
					"			season.id seasonID," +
//OPTIMIZE					"			season.posterPath seasonPosterPath," +
					"           season.seasonNumber seasonNumber," +
					"			episode.id episodeID," +
//OPTIMIZE					"			episode.name episodeName," +
					"           episode.episodeNumber episodeNumber," +
//OPTIMIZE					"			IF(IFNULL(episode.airDate, '1900-01-01') = '','1900-01-01', IFNULL(episode.airDate, '1900-01-01')) episodeAirDate," +
//OPTIMIZE					"			episode.guestStars episodeGuestStars," +
//OPTIMIZE					"			episode.overview episodeOverview," +
//OPTIMIZE					"			episode.productioncode episodeProductionCode," +
//OPTIMIZE					"			episode.stillPath episodeStillPath," +
//OPTIMIZE					"			episode.voteAverage episodeVoteAverage," +
//OPTIMIZE					"			episode.voteCount episodeVoteCount," +
//OPTIMIZE					"			episode.crew episodeCrew," +
					"           episode.state episodeState" +
					" FROM		serie" +
					" JOIN		season ON (season.seasonNumber != 0 AND serie.seriesID = season.seriesID)" +
					" JOIN		episode ON (episode.episodeNumber IS NOT NULL AND episode.state IS NOT NULL AND serie.seriesID = episode.seriesID AND season.seasonNumber = episode.seasonNumber)" +
					" WHERE     serie.seriesID < 1000000" +
					" UNION" +
					" SELECT	serie.seriesID seriesID," +
					" 			serie.seriesName seriesName," +
//OPTIMIZE			"			serie.originalName seriesOriginalName," +
					"           serie.backdropPath seriesBackdrop," +
//OPTIMIZE			"			serie.createdBy seriesCreatedBy," +
//OPTIMIZE			"			serie.homepage seriesHomepage," +
//OPTIMIZE			"			serie.lastAired seriesLastAired," +
//OPTIMIZE			"			serie.languages seriesLanguages," +
//OPTIMIZE			"			serie.networks seriesNetworks," +
					"			serie.nrEpisodes seriesNrEpisodes," +
					"			serie.nrSeasons seriesNrSeasons," +
					"			serie.originCountries seriesOriginCountries," +
//OPTIMIZE			"			serie.originalLanguage seriesOriginalLanguage," +
//OPTIMIZE			"			serie.popularity seriesPopularity," +
//OPTIMIZE			"           serie.posterPath seriesPoster," +
					"			serie.productionCompanies seriesProductionCompanies," +
//OPTIMIZE			"			serie.type seriesType," +
//OPTIMIZE			"			serie.voteAverage seriesVoteAverage," +
//OPTIMIZE			"			serie.voteCount seriesVoteCount," +
					"			serie.overview seriesOverview," +
					"           serie.firstAired seriesFirstAired," +
					"			serie.cast seriesCast," +
					"			serie.crew seriesCrew," +
					"			serie.genre seriesGenre," +
//OPTIMIZE			"			serie.imdbid seriesIMDBID," +
//OPTIMIZE			"			serie.freebasemid seriesFreebaseMID," +
//OPTIMIZE			"			serie.freebaseid seriesFreebaseID," +
//OPTIMIZE			"			serie.tvdbid seriesTVDBID," +
//OPTIMIZE			"			serie.tvrageid seriesTVRageID," +
					"           serie.status seriesStatus," +
					"           serie.download seriesDownload," +
					"			serie.localPath seriesLocalPath," +
					"           serie.resolution seriesResolution," +
					"           serie.cliffhanger seriesCliffhanger," +
//OPTIMIZE			"			season._id season_ID," +
//OPTIMIZE			"			IF(IFNULL(season.airDate, '1900-01-01') = '','1900-01-01', IFNULL(season.airDate, '1900-01-01')) seasonAirDate," +
//OPTIMIZE			"			season.name seasonName," +
//OPTIMIZE			"			season.overview seasonOverview," +
					"			0 seasonID," +
//OPTIMIZE			"			season.posterPath seasonPosterPath," +
					"           0 seasonNumber," +
					"			0 episodeID," +
//OPTIMIZE			"			episode.name episodeName," +
					"           0 episodeNumber," +
//OPTIMIZE			"			IF(IFNULL(episode.airDate, '1900-01-01') = '','1900-01-01', IFNULL(episode.airDate, '1900-01-01')) episodeAirDate," +
//OPTIMIZE			"			episode.guestStars episodeGuestStars," +
//OPTIMIZE			"			episode.overview episodeOverview," +
//OPTIMIZE			"			episode.productioncode episodeProductionCode," +
//OPTIMIZE			"			episode.stillPath episodeStillPath," +
//OPTIMIZE			"			episode.voteAverage episodeVoteAverage," +
//OPTIMIZE			"			episode.voteCount episodeVoteCount," +
//OPTIMIZE			"			episode.crew episodeCrew," +
					"           0 episodeState" +
					" FROM		serie" +
					" WHERE     serie.seriesID >= 1000000" +
					" ORDER BY	seriesName," +
					" 			seriesFirstAired;"
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
					serie      = newSerie(rs);
					serieList.add(serie);

					oldSeasonID = seasonID;
					season      = newSeason(rs);

					oldEpisodeID = episodeID;
					episode      = newEpisode(rs);

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
					season      = newSeason(rs);

					oldEpisodeID = episodeID;
					episode      = newEpisode(rs);

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
					episode      = newEpisode(rs);

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
			boolean insert = false;

			if(useFilter)
			{
				if(filterInitialize)
				{
					if(serie.getStateInit() > 0)
						insert = true;
				}
				if(filterInProgress)
				{
					if(serie.getStateProg() > 0)
						insert = true;
				}
				if(filterDone)
				{
					if(serie.getStateDone() > 0 && serie.getStateInit() == 0 && serie.getStateProg() == 0)
						insert = true;
				}
			}
			else
				insert = true;

			if(insert)
			{
				seriesListModel.add(serie);
				serie.setModel(seriesListModel);
			}
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
//OPTIMIZE		episode.setEpisodeName(rs.getString("episodeName"));
		episode.setEpisodeNumber(rs.getInt("episodeNumber"));
//OPTIMIZE		episode.setEpisodeAirDate(rs.getDate("episodeAirDate"));
//OPTIMIZE		episode.setEpisodeGuestStars(rs.getString("episodeGuestStars"));
//OPTIMIZE		episode.setEpisodeOverview(rs.getString("episodeOverview"));
//OPTIMIZE		episode.setEpisodeProductionCode(rs.getString("episodeProductionCode"));
//OPTIMIZE		episode.setEpisodeStillPath(rs.getString("episodeStillPath"));
//OPTIMIZE		episode.setEpisodeVoteAverage(rs.getDouble("episodeVoteAverage"));
//OPTIMIZE		episode.setEpisodeVoteCount(rs.getInt("episodeVoteCount"));
//OPTIMIZE		episode.setEpisodeCrew(rs.getString("episodeCrew"));
		episode.setEpisodeState(rs.getInt("episodeState"));

		return episode;
	}

	static protected seasonData newSeason(ResultSet rs) throws SQLException
	{
		seasonData season = new seasonData();

//OPTIMIZE		season.setSeason_ID(rs.getString("season_ID"));
//OPTIMIZE		season.setSeasonAirDate(rs.getDate("seasonAirDate"));
//OPTIMIZE		season.setSeasonName(rs.getString("seasonName"));
//OPTIMIZE		season.setSeasonOverview(rs.getString("seasonOverview"));
		season.setSeasonID(rs.getInt("seasonID"));
//OPTIMIZE		season.setSeasonPosterPath(rs.getString("seasonPosterPath"));
		season.setSeasonNumber(rs.getInt("seasonNumber"));

		return season;
	}

	static protected serieData newSerie(ResultSet rs) throws SQLException
	{
		serieData serie = new serieData();

		serie.setSeriesID(rs.getInt("seriesID"));
		serie.setSeriesName(rs.getString("seriesName"));
//OPTIMIZE		serie.setSeriesOriginalName(rs.getString("seriesOriginalName"));
		serie.setSeriesBackdrop(rs.getString("seriesBackdrop"));
//OPTIMIZE		serie.setSeriesCreatedBy(rs.getString("seriesCreatedBy"));
//OPTIMIZE		serie.setSeriesHomepage(rs.getString("seriesHomepage"));
//OPTIMIZE		serie.setSeriesLastAired(rs.getDate("seriesLastAired"));
//OPTIMIZE		serie.setSeriesLanguages(rs.getString("seriesLanguages"));
//OPTIMIZE		serie.setSeriesNetworks(rs.getString("seriesNetworks"));
		serie.setSeriesNrEpisodes(rs.getInt("seriesNrEpisodes"));
		serie.setSeriesNrSeasons(rs.getInt("seriesNrSeasons"));
		serie.setSeriesOriginCountries(rs.getString("seriesOriginCountries"));
//OPTIMIZE		serie.setSeriesOriginalLanguage(rs.getString("seriesOriginalLanguage"));
//OPTIMIZE		serie.setSeriesPopularity(rs.getDouble("seriesPopularity"));
//OPTIMIZE		serie.setSeriesPoster(rs.getString("seriesPoster"));
		serie.setSeriesProductionCompanies(rs.getString("seriesProductionCompanies"));
//OPTIMIZE		serie.setSeriesType(rs.getString("seriesType"));
//OPTIMIZE		serie.setSeriesVoteAverage(rs.getDouble("seriesVoteAverage"));
//OPTIMIZE		serie.setSeriesVoteCount(rs.getInt("seriesVoteCount"));
		serie.setSeriesOverview(rs.getString("seriesOverview"));
		serie.setSeriesFirstAired(rs.getDate("seriesFirstAired"));
		serie.setSeriesCast(rs.getString("seriesCast"));
		serie.setSeriesCrew(rs.getString("seriesCrew"));
		serie.setSeriesGenre(rs.getString("seriesGenre"));
//OPTIMIZE		serie.setSeriesIMDBID(rs.getString("seriesIMDBID"));
//OPTIMIZE		serie.setSeriesFreebaseMID(rs.getString("seriesFreebaseMID"));
//OPTIMIZE		serie.setSeriesFreebaseID(rs.getString("seriesFreebaseID"));
//OPTIMIZE		serie.setSeriesTVDBID(rs.getString("seriesTVDBID"));
//OPTIMIZE		serie.setSeriesTVRageID(rs.getString("seriesTVRageID"));
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

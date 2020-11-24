package at.windesign.application.serie;

import java.sql.*;
import java.util.SortedMap;
import java.util.TreeMap;

public class seasonData
{
	private int                             m_seasonID;
	private String                            m_season_ID;
	private Date                            m_seasonAirDate;
	private String                          m_seasonName;
	private String                          m_seasonOverview;
	private String                          m_seasonPosterPath;
	private int                             m_seasonNumber;
	private serieData                       m_serie;
	private SortedMap<Integer, episodeData> m_episodes = new TreeMap<>();

	public seasonData()
	{
	}

	public int getSeasonID()
	{
		return m_seasonID;
	}

	public void setSeasonID(int seasonID)
	{
		m_seasonID = seasonID;
	}

	public String getSeason_ID()
	{
		return m_season_ID;
	}

	public void setSeason_ID(String season_ID)
	{
		m_season_ID = season_ID;
	}

	public Date getSeasonAirDate()
	{
		return m_seasonAirDate;
	}

	public void setSeasonAirDate(Date seasonAirDate)
	{
		m_seasonAirDate = seasonAirDate;
	}

	public String getSeasonName()
	{
		return m_seasonName;
	}

	public void setSeasonName(String seasonName)
	{
		m_seasonName = seasonName;
	}

	public String getSeasonOverview()
	{
		return m_seasonOverview;
	}

	public void setSeasonOverview(String seasonOverview)
	{
		m_seasonOverview = seasonOverview;
	}

	public String getSeasonPosterPath()
	{
		return m_seasonPosterPath;
	}

	public void setSeasonPosterPath(String seasonPosterPath)
	{
		m_seasonPosterPath = seasonPosterPath;
	}

	public int getSeasonNumber()
	{
		return m_seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber)
	{
		m_seasonNumber = seasonNumber;
	}

	public serieData getSerie()
	{
		return m_serie;
	}

	public void setSerie(serieData serie)
	{
		m_serie = serie;
	}

	public SortedMap<Integer, episodeData> getEpisodes()
	{
		return m_episodes;
	}

	public void setEpisodes(SortedMap<Integer, episodeData> episodes)
	{
		m_episodes = episodes;
	}

	public void setEpisode(int episode, episodeData data)
	{
		m_episodes.put(episode, data);
	}

	public void setEpisodeState(int episode, int state)
	{
		episodeData eData = m_episodes.get(episode);

		if(eData == null)
			return;

		eData.setEpisodeState(state);
	}

	public boolean save()
	{
/*
		try
		{
			Statement stmt = ds.getStatement();
			stmt.execute("DELETE FROM serie WHERE seriesID=" + m_seriesID + ";");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			ds.close();
		}

		try
		{
			Connection conn = ds.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO serie (" +
														 "seriesID, " +
														 "seriesName, " +
														 "originalName, " +
														 "backdropPath, " +
														 "createdBy, " +
														 "homepage, " +
														 "lastAired, " +
														 "languages, " +
														 "networks, " +
														 "nrEpisodes, " +
														 "nrSeasons, " +
														 "originCountries, " +
														 "originalLanguage, " +
														 "popularity, " +
														 "posterPath, " +
														 "productionCompanies, " +
														 "type, " +
														 "voteAverage, " +
														 "voteCount, " +
														 "overview, " +
														 "firstAired, " +
														 "cast, " +
														 "crew, " +
														 "genre, " +
														 "imdbid, " +
														 "freebasemid, " +
														 "freebaseid, " +
														 "tvdbid, " +
														 "tvrageid, " +
														 "status, " +
														 "download, " +
														 "localPath, " +
														 "resolution, " +
														 "cliffhanger" +
														 ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"
														);
			ps.setInt(1, m_seriesID);
			ps.setString(2, m_seriesName);
			ps.setString(3, m_seriesOriginalName);
			ps.setString(4, m_seriesBackdrop);
			ps.setString(5, m_seriesCreatedBy);
			ps.setString(6, m_seriesHomepage);
			ps.setDate(7, m_seriesLastAired);
			ps.setString(8, m_seriesLanguages);
			ps.setString(9, m_seriesNetworks);
			ps.setInt(10, m_seriesNrEpisodes);
			ps.setInt(11, m_seriesNrSeasons);
			ps.setString(12, m_seriesOriginCountries);
			ps.setString(13, m_seriesOriginalLanguage);
			ps.setDouble(14, m_seriesPopularity);
			ps.setString(15, m_seriesPoster);
			ps.setString(16, m_seriesProductionCompanies);
			ps.setString(17, m_seriesType);
			ps.setDouble(18, m_seriesVoteAverage);
			ps.setInt(19, m_seriesVoteCount);
			ps.setString(20, m_seriesOverview);
			ps.setDate(21, m_seriesFirstAired);
			ps.setString(22, m_seriesCast);
			ps.setString(23, m_seriesCrew);
			ps.setString(24, m_seriesGenre);
			ps.setString(25, m_seriesIMDBID);
			ps.setString(26, m_seriesFreebaseMID);
			ps.setString(27, m_seriesFreebaseID);
			ps.setString(28, m_seriesTVDBID);
			ps.setString(29, m_seriesTVRageID);
			ps.setString(30, m_seriesStatus);
			ps.setString(31, m_seriesDownload);
			ps.setString(32, m_seriesLocalPath);
			ps.setString(33, m_seriesResolution);
			ps.setBoolean(34, m_seriesCliffhanger);

			boolean ret = ps.execute();

			for(int season : getSeasons().keySet())
			{
				getSeasons().get(season).save();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			ds.close();
		}
*/

		for(int episode : getEpisodes().keySet())
		{
			getEpisodes().get(episode).save();
		}

		return true;
	}
}

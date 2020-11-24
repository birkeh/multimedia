package at.windesign.application.serie;

import java.sql.*;

public class episodeData
{
	private int        m_episodeID;
	private int        m_episodeNumber;
	private String     m_episodeName;
	private Date       m_episodeAirDate;
	private String     m_episodeGuestStars;
	private String     m_episodeOverview;
	private String     m_episodeProductionCode;
	private int        m_seasonNumber;
	private int        m_seasonID;
	private int        m_seriesID;
	private String     m_episodeStillPath;
	private double     m_episodeVoteAverage;
	private int        m_episodeVoteCount;
	private String     m_episodeCrew;
	private int        m_episodeState;
	private serieData  m_serie;
	private seasonData m_season;

	public int getEpisodeID()
	{
		return m_episodeID;
	}

	public void setEpisodeID(int episodeID)
	{
		m_episodeID = episodeID;
	}

	public int getEpisodeNumber()
	{
		return m_episodeNumber;
	}

	public void setEpisodeNumber(int episodeNumber)
	{
		m_episodeNumber = episodeNumber;
	}

	public String getEpisodeName()
	{
		return m_episodeName;
	}

	public void setEpisodeName(String episodeName)
	{
		m_episodeName = episodeName;
	}

	public Date getEpisodeAirDate()
	{
		return m_episodeAirDate;
	}

	public void setEpisodeAirDate(Date episodeAirDate)
	{
		m_episodeAirDate = episodeAirDate;
	}

	public String getEpisodeGuestStars()
	{
		return m_episodeGuestStars;
	}

	public void setEpisodeGuestStars(String episodeGuestStars)
	{
		m_episodeGuestStars = episodeGuestStars;
	}

	public String getEpisodeOverview()
	{
		return m_episodeOverview;
	}

	public void setEpisodeOverview(String episodeOverview)
	{
		m_episodeOverview = episodeOverview;
	}

	public String getEpisodeProductionCode()
	{
		return m_episodeProductionCode;
	}

	public void setEpisodeProductionCode(String episodeProductionCode)
	{
		m_episodeProductionCode = episodeProductionCode;
	}

	public int getSeasonNumber()
	{
		return m_seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber)
	{
		m_seasonNumber = seasonNumber;
	}

	public int getSeasonID()
	{
		return m_seasonID;
	}

	public void setSeasonID(int seasonID)
	{
		m_seasonID = seasonID;
	}

	public int getSeriesID()
	{
		return m_seriesID;
	}

	public void setSeriesID(int seriesID)
	{
		m_seriesID = seriesID;
	}

	public String getEpisodeStillPath()
	{
		return m_episodeStillPath;
	}

	public void setEpisodeStillPath(String episodeStillPath)
	{
		m_episodeStillPath = episodeStillPath;
	}

	public double getEpisodeVoteAverage()
	{
		return m_episodeVoteAverage;
	}

	public void setEpisodeVoteAverage(double episodeVoteAverage)
	{
		m_episodeVoteAverage = episodeVoteAverage;
	}

	public int getEpisodeVoteCount()
	{
		return m_episodeVoteCount;
	}

	public void setEpisodeVoteCount(int episodeVoteCount)
	{
		m_episodeVoteCount = episodeVoteCount;
	}

	public String getEpisodeCrew()
	{
		return m_episodeCrew;
	}

	public void setEpisodeCrew(String episodeCrew)
	{
		m_episodeCrew = episodeCrew;
	}

	public int getEpisodeState()
	{
		return m_episodeState;
	}

	public void setEpisodeState(int episodeState)
	{
		m_episodeState = episodeState;
	}

	public serieData getSerie()
	{
		return m_serie;
	}

	public void setSerie(serieData serie)
	{
		m_serie = serie;
	}

	public seasonData getSeason()
	{
		return m_season;
	}

	public void setSeason(seasonData season)
	{
		m_season = season;
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
		return true;
	}
}

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
	private String     m_episodeStillPath;
	private double     m_episodeVoteAverage;
	private int        m_episodeVoteCount;
	private String     m_episodeCrew;
	private int        m_episodeState;
	private serieData  m_serie;
	private seasonData m_season;

	private final serieDataSource ds = serieDataSource.INSTANCE;

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
		try
		{
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute("DELETE FROM episode WHERE id=" + m_episodeID + ";");

			PreparedStatement ps = prepareStatement(conn);
			save(ps);
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

		return true;
	}

	public boolean save(PreparedStatement ps)
	{
		try
		{
			ps.setInt(1, m_episodeID);
			ps.setString(2, m_episodeName);
			ps.setInt(3, m_episodeNumber);
			ps.setDate(4, m_episodeAirDate);
			ps.setString(5, m_episodeGuestStars);
			ps.setString(6, m_episodeOverview);
			ps.setString(7, m_episodeProductionCode);
			ps.setInt(8, m_season.getSeasonNumber());
			ps.setInt(9, m_season.getSeasonID());
			ps.setInt(10, m_serie.getSeriesID());
			ps.setString(11, m_episodeStillPath);
			ps.setDouble(12, m_episodeVoteAverage);
			ps.setInt(13, m_episodeVoteCount);
			ps.setString(14, m_episodeCrew);
			ps.setInt(15, m_episodeState);

			boolean ret = ps.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
		}

		return true;
	}

	public PreparedStatement prepareStatement(Connection conn) throws SQLException
	{
		PreparedStatement ps = conn.prepareStatement("INSERT INTO episode (" +
				"id, " +
				"name, " +
				"episodeNumber, " +
				"airDate, " +
				"guestStars, " +
				"overview, " +
				"productioncode, " +
				"seasonNumber, " +
				"seasonID, " +
				"seriesID, " +
				"stillPath, " +
				"voteAverage, " +
				"voteCount, " +
				"crew, " +
				"state " +
				") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);"
		);

		return ps;
	}
}

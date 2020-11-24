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

	private final serieDataSource ds = serieDataSource.INSTANCE;

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
		try
		{
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute("DELETE FROM season WHERE id=" + m_seasonID + ";");

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
			ps.setString(1, m_season_ID);
			ps.setDate(2, m_seasonAirDate);
			ps.setString(3, m_seasonName);
			ps.setString(4, m_seasonOverview);
			ps.setInt(5, m_seasonID);
			ps.setString(6, m_seasonPosterPath);
			ps.setInt(7, m_seasonNumber);
			ps.setInt(8, m_serie.getSeriesID());

			boolean ret = ps.execute();

			if(m_episodes.size() > 0)
			{
				PreparedStatement psEpisode = getEpisodes().get(getEpisodes().firstKey()).prepareStatement(ps.getConnection());

				for(int episode : getEpisodes().keySet())
				{
					getEpisodes().get(episode).save(psEpisode);
				}
			}
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
		PreparedStatement ps = conn.prepareStatement("INSERT INTO season (" +
				"_id, " +
				"airDate, " +
				"name, " +
				"overview, " +
				"id, " +
				"posterPath, " +
				"seasonNumber, " +
				"seriesID " +
				") VALUES (?,?,?,?,?,?,?,?);"
		);

		return ps;
	}
}

package at.windesign.application.serie;

import java.sql.Date;
import java.util.SortedMap;

public class seasonData
{
	private int                             m_seasonID;
	private int                             m_season_ID;
	private Date                            m_seasonAirDate;
	private String                          m_seasonName;
	private String                          m_seasonOverview;
	private String                          m_seasonPosterPath;
	private int                             m_seasonNumber;
	private serieData                       m_serie;
	private SortedMap<Integer, episodeData> m_episodes;

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

	public int getSeason_ID()
	{
		return m_season_ID;
	}

	public void setSeason_ID(int season_ID)
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
}

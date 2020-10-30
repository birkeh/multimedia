package at.windesign.application;

import java.util.SortedMap;

class Serie
{
	private int                         m_seriesID;
	private String                      m_seriesName;
	private int                         m_seriesFirstAired;
	private String                      m_seriesResolution;
	private boolean                     m_seriesCliffhanger;
	private String                      m_seriesStatus;
	private String                      m_seriesDownload;
	private String                      m_seriesPoster;
	private String                      m_seriesBackdrop;
	private SortedMap<Integer, Integer> m_episodeState;
	private int                         m_minSeason;
	private int                         m_maxSeason;

	public Serie()
	{
	}

	public Serie(int seriesID, String seriesName, int seriesFirstAired, String seriesResolution, boolean seriesCliffhanger, String seriesStatus, String seriesDownload, String seriesPoster, String seriesBackdrop, SortedMap<Integer, Integer> episodeState, int minSeason, int maxSeason)
	{
		m_seriesID = seriesID;
		m_seriesName = seriesName;
		m_seriesFirstAired = seriesFirstAired;
		m_seriesResolution = seriesResolution;
		m_seriesCliffhanger = seriesCliffhanger;
		m_seriesStatus = seriesStatus;
		m_seriesDownload = seriesDownload;
		m_seriesPoster = seriesPoster;
		m_seriesBackdrop = seriesBackdrop;
		m_episodeState = episodeState;
		m_minSeason = minSeason;
		m_maxSeason = maxSeason;
	}

	public int getSeriesID()
	{
		return m_seriesID;
	}

	public void setSeriesID(int seriesID)
	{
		m_seriesID = seriesID;
	}

	public String getSeriesName()
	{
		return m_seriesName;
	}

	public void setSeriesName(String seriesName)
	{
		m_seriesName = seriesName;
	}

	public int getSeriesFirstAired()
	{
		return m_seriesFirstAired;
	}

	public void setSeriesFirstAired(int seriesFirstAired)
	{
		m_seriesFirstAired = seriesFirstAired;
	}

	public String getSeriesResolution()
	{
		return m_seriesResolution;
	}

	public void setSeriesResolution(String seriesResolution)
	{
		m_seriesResolution = seriesResolution;
	}

	public boolean getSeriesCliffhanger()
	{
		return m_seriesCliffhanger;
	}

	public void setSeriesCliffhanger(boolean seriesCliffhanger)
	{
		m_seriesCliffhanger = seriesCliffhanger;
	}

	public String getSeriesStatus()
	{
		return m_seriesStatus;
	}

	public void setSeriesStatus(String seriesStatus)
	{
		m_seriesStatus = seriesStatus;
	}

	public String getSeriesDownload()
	{
		return m_seriesDownload;
	}

	public void setSeriesDownload(String seriesDownload)
	{
		m_seriesDownload = seriesDownload;
	}

	public String getSeriesPoster()
	{
		return m_seriesPoster;
	}

	public void setSeriesPoster(String seriesPoster)
	{
		m_seriesPoster = seriesPoster;
	}

	public String getSeriesBackdrop()
	{
		return m_seriesBackdrop;
	}

	public void setSeriesBackdrop(String seriesBackdrop)
	{
		m_seriesBackdrop = seriesBackdrop;
	}

	public SortedMap<Integer, Integer> getEpisodeState()
	{
		return m_episodeState;
	}

	public void setEpisodeState(SortedMap<Integer, Integer> episodeState)
	{
		m_episodeState = episodeState;
	}

	public int getMinSeason()
	{
		return m_minSeason;
	}

	public void setMinSeason(int minSeason)
	{
		m_minSeason = minSeason;
	}

	public int getMaxSeason()
	{
		return m_maxSeason;
	}

	public void setMaxSeason(int maxSeason)
	{
		m_maxSeason = maxSeason;
	}
}
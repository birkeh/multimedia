package at.windesign.application.serie;

import java.awt.*;
import java.util.Objects;
import java.util.SortedMap;

class serie
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
	private String                      m_seriesOverview;
	private SortedMap<Integer, Integer> m_episodeState;
	private int                         m_minSeason;
	private int                         m_maxSeason;
	private int                         m_stateInit;
	private int                         m_stateProg;
	private int                         m_stateDone;

	public serie()
	{
	}

	public serie(int seriesID, String seriesName, int seriesFirstAired, String seriesResolution, boolean seriesCliffhanger, String seriesStatus, String seriesDownload, String seriesPoster, String seriesBackdrop, String seriesOverview, SortedMap<Integer, Integer> episodeState, int minSeason, int maxSeason, int stateInit, int stateProg, int stateDone)
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
		m_seriesOverview = seriesOverview;
		m_episodeState = episodeState;
		m_minSeason = minSeason;
		m_maxSeason = maxSeason;
		m_stateInit = stateInit;
		m_stateProg = stateProg;
		m_stateDone = stateDone;
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

	public String getSeriesOverview()
	{
		return m_seriesOverview;
	}

	public void setSeriesOverview(String seriesOverview)
	{
		m_seriesOverview = seriesOverview;
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

	public int getStateInit()
	{
		return m_stateInit;
	}

	public void setStateInit(int stateInit)
	{
		m_stateInit = stateInit;
	}

	public int getStateProg()
	{
		return m_stateProg;
	}

	public void setStateProg(int stateProg)
	{
		m_stateProg = stateProg;
	}

	public int getStateDone()
	{
		return m_stateDone;
	}

	public void setStateDone(int stateDone)
	{
		m_stateDone = stateDone;
	}

	public String getSeriesStyle()
	{
		String style = "";

		if(m_seriesCliffhanger)
			style = style + "font-style: italic; ";

		if(Objects.equals(m_seriesStatus, "Returning Series"))
			style = style + "font-weight: bold; ";

		String color = "";

		if(m_stateProg > 0)
			color = "background-color: #0000C0; color: #FFFFFF";
		else if(m_stateInit > 0)
			color = "background-color: #C0C0C0;";
		else if(m_stateDone > 0)
			color = "background-color: #00C000;";

		style += color;

		return style;
	}
}
package at.windesign.application.serie;

import java.awt.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.SortedMap;

import static org.zkoss.zk.ui.util.Clients.alert;

class serieData
{
	private int     m_seriesID;
	private String  m_seriesName;
	private String  m_seriesOriginalName;
	private String  m_seriesBackdrop;
	private String  m_seriesCreatedBy;
	private String  m_seriesHomepage;
	private Date    m_seriesLastAired;
	private String  m_seriesLanguages;
	private String  m_seriesNetworks;
	private int     m_seriesNrEpisodes;
	private int     m_seriesNrSeasons;
	private String  m_seriesOriginCountries;
	private String  m_seriesOriginalLanguage;
	private int     m_seriesPopularity;
	private String  m_seriesPoster;
	private String  m_seriesProductionCompanies;
	private String  m_seriesType;
	private double  m_seriesVoteAverage;
	private int     m_seriesVoteCount;
	private String  m_seriesOverview;
	private Date    m_seriesFirstAired;
	private String  m_seriesCast;
	private String  m_seriesCrew;
	private String  m_seriesGenre;
	private String  m_seriesIMDBID;
	private String  m_seriesFreebaseMID;
	private String  m_seriesFreebaseID;
	private String  m_seriesTVDBID;
	private String  m_seriesTVRageID;
	private String  m_seriesStatus;
	private String  m_seriesDownload;
	private String  m_seriesLocalPath;
	private String  m_seriesResolution;
	private boolean m_seriesCliffhanger;

	private SortedMap<Integer, Integer> m_episodeState;

	private int m_minSeason;
	private int m_maxSeason;
	private int m_stateInit;
	private int m_stateProg;
	private int m_stateDone;

	private final serieDataSource ds = serieDataSource.INSTANCE;

	public serieData()
	{
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

	public String getSeriesOriginalName()
	{
		return m_seriesOriginalName;
	}

	public void setSeriesOriginalName(String seriesOriginalName)
	{
		m_seriesOriginalName = seriesOriginalName;
	}

	public String getSeriesBackdrop()
	{
		return m_seriesBackdrop;
	}

	public void setSeriesBackdrop(String seriesBackdrop)
	{
		m_seriesBackdrop = seriesBackdrop;
	}

	public String getSeriesCreatedBy()
	{
		return m_seriesCreatedBy;
	}

	public void setSeriesCreatedBy(String seriesCreatedBy)
	{
		m_seriesCreatedBy = seriesCreatedBy;
	}

	public String getSeriesHomepage()
	{
		return m_seriesHomepage;
	}

	public void setSeriesHomepage(String seriesHomepage)
	{
		m_seriesHomepage = seriesHomepage;
	}

	public Date getSeriesLastAired()
	{
		return m_seriesLastAired;
	}

	public void setSeriesLastAired(Date seriesLastAired)
	{
		m_seriesLastAired = seriesLastAired;
	}

	public String getSeriesLanguages()
	{
		return m_seriesLanguages;
	}

	public void setSeriesLanguages(String seriesLanguages)
	{
		m_seriesLanguages = seriesLanguages;
	}

	public String getSeriesNetworks()
	{
		return m_seriesNetworks;
	}

	public void setSeriesNetworks(String seriesNetworks)
	{
		m_seriesNetworks = seriesNetworks;
	}

	public int getSeriesNrEpisodes()
	{
		return m_seriesNrEpisodes;
	}

	public void setSeriesNrEpisodes(int seriesNrEpisodes)
	{
		m_seriesNrEpisodes = seriesNrEpisodes;
	}

	public int getSeriesNrSeasons()
	{
		return m_seriesNrSeasons;
	}

	public void setSeriesNrSeasons(int seriesNrSeasons)
	{
		m_seriesNrSeasons = seriesNrSeasons;
	}

	public String getSeriesOriginCountries()
	{
		return m_seriesOriginCountries;
	}

	public void setSeriesOriginCountries(String seriesOriginCountries)
	{
		m_seriesOriginCountries = seriesOriginCountries;
	}

	public String getSeriesOriginalLanguage()
	{
		return m_seriesOriginalLanguage;
	}

	public void setSeriesOriginalLanguage(String seriesOriginalLanguage)
	{
		m_seriesOriginalLanguage = seriesOriginalLanguage;
	}

	public int getSeriesPopularity()
	{
		return m_seriesPopularity;
	}

	public void setSeriesPopularity(int seriesPopularity)
	{
		m_seriesPopularity = seriesPopularity;
	}

	public String getSeriesPoster()
	{
		return m_seriesPoster;
	}

	public void setSeriesPoster(String seriesPoster)
	{
		m_seriesPoster = seriesPoster;
	}

	public String getSeriesProductionCompanies()
	{
		return m_seriesProductionCompanies;
	}

	public void setSeriesProductionCompanies(String seriesProductionCompanies)
	{
		m_seriesProductionCompanies = seriesProductionCompanies;
	}

	public String getSeriesType()
	{
		return m_seriesType;
	}

	public void setSeriesType(String seriesType)
	{
		m_seriesType = seriesType;
	}

	public double getSeriesVoteAverage()
	{
		return m_seriesVoteAverage;
	}

	public void setSeriesVoteAverage(double seriesVoteAverage)
	{
		m_seriesVoteAverage = seriesVoteAverage;
	}

	public int getSeriesVoteCount()
	{
		return m_seriesVoteCount;
	}

	public void setSeriesVoteCount(int seriesVoteCount)
	{
		m_seriesVoteCount = seriesVoteCount;
	}

	public String getSeriesOverview()
	{
		return m_seriesOverview;
	}

	public void setSeriesOverview(String seriesOverview)
	{
		m_seriesOverview = seriesOverview;
	}

	public Date getSeriesFirstAired()
	{
		return m_seriesFirstAired;
	}

	public void setSeriesFirstAired(Date seriesFirstAired)
	{
		m_seriesFirstAired = seriesFirstAired;
	}

	public String getSeriesCast()
	{
		return m_seriesCast;
	}

	public void setSeriesCast(String seriesCast)
	{
		m_seriesCast = seriesCast;
	}

	public String getSeriesCrew()
	{
		return m_seriesCrew;
	}

	public void setSeriesCrew(String seriesCrew)
	{
		m_seriesCrew = seriesCrew;
	}

	public String getSeriesGenre()
	{
		return m_seriesGenre;
	}

	public void setSeriesGenre(String seriesGenre)
	{
		m_seriesGenre = seriesGenre;
	}

	public String getSeriesIMDBID()
	{
		return m_seriesIMDBID;
	}

	public void setSeriesIMDBID(String seriesIMDBID)
	{
		m_seriesIMDBID = seriesIMDBID;
	}

	public String getSeriesFreebaseMID()
	{
		return m_seriesFreebaseMID;
	}

	public void setSeriesFreebaseMID(String seriesFreebaseMID)
	{
		m_seriesFreebaseMID = seriesFreebaseMID;
	}

	public String getSeriesFreebaseID()
	{
		return m_seriesFreebaseID;
	}

	public void setSeriesFreebaseID(String seriesFreebaseID)
	{
		m_seriesFreebaseID = seriesFreebaseID;
	}

	public String getSeriesTVDBID()
	{
		return m_seriesTVDBID;
	}

	public void setSeriesTVDBID(String seriesTVDBID)
	{
		m_seriesTVDBID = seriesTVDBID;
	}

	public String getSeriesTVRageID()
	{
		return m_seriesTVRageID;
	}

	public void setSeriesTVRageID(String seriesTVRageID)
	{
		m_seriesTVRageID = seriesTVRageID;
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

	public String getSeriesLocalPath()
	{
		return m_seriesLocalPath;
	}

	public void setSeriesLocalPath(String seriesLocalPath)
	{
		m_seriesLocalPath = seriesLocalPath;
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

	public SortedMap<Integer, Integer> getEpisodeState()
	{
		return m_episodeState;
	}

	public void setEpisodeState(SortedMap<Integer, Integer> episodeState)
	{
		m_episodeState = episodeState;
	}

	public void setEpisodeState(int season, int episode, int state)
	{
		m_episodeState.put(season << 8 + episode, state);
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

	public boolean save()
	{
		try
		{
			Statement stmt = ds.getStatement();
			int cnt = stmt.executeUpdate(
					"UPDATE		serie" +
							" SET		seriesName='" + m_seriesName + "'," +
							"			originalName='" + m_seriesOriginalName + "'," +
							"			backdropPath='" + m_seriesBackdrop + "'," +
							"			backdropPath='" + m_seriesCreatedBy + "'," +
							"			backdropPath='" + m_seriesHomepage + "'," +
							"			backdropPath='" + m_seriesLastAired + "'," +
							"			backdropPath='" + m_seriesLanguages + "'," +
							"			backdropPath='" + m_seriesNetworks + "'," +
							"			backdropPath=" + m_seriesNrEpisodes + "," +
							"			backdropPath=" + m_seriesNrSeasons + "," +
							"			backdropPath='" + m_seriesOriginCountries + "'," +
							"			backdropPath='" + m_seriesOriginalLanguage + "'," +
							"			backdropPath=" + m_seriesPopularity + "," +
							"			posterPath='" + m_seriesPoster + "'," +
							"			posterPath='" + m_seriesProductionCompanies + "'," +
							"			posterPath='" + m_seriesType + "'," +
							"			posterPath=" + m_seriesVoteAverage + "," +
							"			posterPath=" + m_seriesVoteCount + "," +
							"			overview='" + m_seriesOverview + "'," +
							"			firstAired='" + m_seriesFirstAired + "'," +
							"			resolution='" + m_seriesCast + "'," +
							"			resolution='" + m_seriesCrew + "'," +
							"			resolution='" + m_seriesGenre + "'," +
							"			resolution='" + m_seriesIMDBID + "'," +
							"			resolution='" + m_seriesFreebaseMID + "'," +
							"			resolution='" + m_seriesFreebaseID + "'," +
							"			resolution='" + m_seriesTVDBID + "'," +
							"			resolution='" + m_seriesTVRageID + "'," +
							"			status='" + m_seriesStatus + "'," +
							"			download='" + m_seriesDownload + "'," +
							"			localPath='" + m_seriesLocalPath + "'," +
							"			resolution='" + m_seriesResolution + "'," +
							"			cliffhanger=" + m_seriesCliffhanger +
							" WHERE		seriesID=" + m_seriesID + ";");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ds.close();
		}
/*
				"SELECT		serie.seriesID seriesID," +
						" 			serie.seriesName seriesName," +
						"           YEAR(serie.firstAired) seriesFirstAired," +
						"           serie.resolution seriesResolution," +
						"           serie.cliffhanger seriesCliffhanger," +
						"           serie.status seriesStatus," +
						"           serie.download seriesDownload," +
						"			serie.localPath seriesLocalPath," +
						"           serie.posterPath seriesPoster," +
						"           serie.backdropPath seriesBackdrop," +
						"			serie.overview seriesOverview," +
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
*/
		return true;
	}
}
package at.windesign.application.serie;

import org.zkoss.zul.ListModelList;

import java.sql.*;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

class serieData
{
	private int                            m_seriesID;
	private String                         m_seriesName;
	private String                         m_seriesOriginalName;
	private String                         m_seriesBackdrop;
	private String                         m_seriesCreatedBy;
	private String                         m_seriesHomepage;
	private Date                           m_seriesLastAired;
	private String                         m_seriesLanguages;
	private String                         m_seriesNetworks;
	private int                            m_seriesNrEpisodes;
	private int                            m_seriesNrSeasons;
	private String                         m_seriesOriginCountries;
	private String                         m_seriesOriginalLanguage;
	private double                         m_seriesPopularity;
	private String                         m_seriesPoster;
	private String                         m_seriesProductionCompanies;
	private String                         m_seriesType;
	private double                         m_seriesVoteAverage;
	private int                            m_seriesVoteCount;
	private String                         m_seriesOverview;
	private Date                           m_seriesFirstAired;
	private String                         m_seriesCast;
	private String                         m_seriesCrew;
	private String                         m_seriesGenre;
	private String                         m_seriesIMDBID;
	private String                         m_seriesFreebaseMID;
	private String                         m_seriesFreebaseID;
	private String                         m_seriesTVDBID;
	private String                         m_seriesTVRageID;
	private String                         m_seriesStatus;
	private String                         m_seriesDownload;
	private String                         m_seriesLocalPath;
	private String                         m_seriesResolution;
	private boolean                        m_seriesCliffhanger;
	private SortedMap<Integer, seasonData> m_seasons = new TreeMap<>();
	private ListModelList                  m_model;

	private int m_stateInit;
	private int m_stateProg;
	private int m_stateDone;

	private final serieDataSource ds = serieDataSource.INSTANCE;

	public serieData()
	{
		m_stateInit = 0;
		m_stateProg = 0;
		m_stateDone = 0;
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

	public double getSeriesPopularity()
	{
		return m_seriesPopularity;
	}

	public void setSeriesPopularity(double seriesPopularity)
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

	public SortedMap<Integer, seasonData> getSeasons()
	{
		return m_seasons;
	}

	public void setSeasons(SortedMap<Integer, seasonData> seasons)
	{
		m_seasons = seasons;
	}

	public void setSeason(int season, seasonData data)
	{
		m_seasons.put(season, data);
	}

	public int getMinSeason()
	{
		return m_seasons.get(m_seasons.firstKey()).getSeasonNumber();
	}

	public int getMaxSeason()
	{
		return m_seasons.get(m_seasons.lastKey()).getSeasonNumber();
	}

	public int getStateInit()
	{
		return m_stateInit;
	}

	public void setStateInit(int stateInit)
	{
		m_stateInit = stateInit;
	}

	public void addStateInit()
	{
		m_stateInit++;
	}

	public int getStateProg()
	{
		return m_stateProg;
	}

	public void setStateProg(int stateProg)
	{
		m_stateProg = stateProg;
	}

	public void addStateProd()
	{
		m_stateProg++;
	}

	public int getStateDone()
	{
		return m_stateDone;
	}

	public void setStateDone(int stateDone)
	{
		m_stateDone = stateDone;
	}

	public void addStateDone()
	{
		m_stateDone++;
	}

	public ListModelList getModel()
	{
		return m_model;
	}

	public void setModel(ListModelList model)
	{
		m_model = model;
	}

	public void recalcState()
	{
		m_stateInit = 0;
		m_stateProg = 0;
		m_stateDone = 0;

		for(int season : m_seasons.keySet())
		{
			seasonData sData = m_seasons.get(season);

			for(int episode : sData.getEpisodes().keySet())
			{
				episodeData eData = sData.getEpisodes().get(episode);

				switch(eData.getEpisodeState())
				{
					case 1:
						m_stateInit++;
						break;
					case 2:
						m_stateProg++;
						break;
					case 3:
						m_stateDone++;
						break;
				}
			}
		}
	}

	public void setEpisodeState(int season, int episode, int state)
	{
		seasonData sData = m_seasons.get(season);

		if(sData == null)
			return;

		sData.setEpisodeState(episode, state);
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
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute("DELETE FROM serie WHERE seriesID=" + m_seriesID + ";");

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

			stmt.execute("DELETE FROM season WHERE seriesID=" + m_seriesID + ";");
			stmt.execute("DELETE FROM episode WHERE seriesID=" + m_seriesID + ";");

			if(m_seasons.size() > 0)
			{
				ps = getSeasons().get(getSeasons().firstKey()).prepareStatement(conn);

				for(int season : getSeasons().keySet())
				{
					getSeasons().get(season).save(ps);
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
			ds.close();
		}

		return true;
	}
}
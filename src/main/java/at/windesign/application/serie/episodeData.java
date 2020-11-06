package at.windesign.application.serie;

import java.sql.Date;

public class episodeData
{
	private int  m_episodeID;
	private int  m_episodeNumber;
	private String m_episodeName;
	private Date m_episodeAirDate;
	private String m_episodeGuestStars;
	private String m_episodeOverview;
	private String m_episodeProductionCode;
	private int m_seasonNumber;
	private int m_seasonID;
	private int m_seriesID;
	private String m_episodeStillPath;
	private double m_episodeVoteAverage;
	private int m_episodeVoteCount;
	private String m_episodeCrew;
	private int m_episodeState;
	private serieData m_serie;
	private seasonData m_season;

	public int getEpisodeID() { return m_episodeID; }
	public void setEpisodeID(int episodeID) { m_episodeID = episodeID; }

	public int getEpisodeNumber() { return m_episodeNumber; }
	public void setEpisodeNumber(int episodeNumber) { m_episodeNumber = episodeNumber; }

	public String getEpisodeName() { return m_episodeName; }
	public void setEpisodeName(String episodeName) { m_episodeName = episodeName; }

	public Date getEpisodeAirDate() { return m_episodeAirDate; }
	public void setEpisodeAirDate(Date episodeAirDate) { m_episodeAirDate = episodeAirDate; }

	public String getEpisodeGuestStars() { return m_episodeGuestStars; }
	public void setEpisodeGuestStars(String episodeGuestStars) { m_episodeGuestStars = episodeGuestStars; }

	public String getEpisodeOverview() { return m_episodeOverview; }
	public void setEpisodeOverview(String episodeOverview) { m_episodeOverview = episodeOverview; }

	public String getEpisodeProductionCode() { return m_episodeProductionCode; }
	public void setEpisodeProductionCode(String episodeProductionCode) { m_episodeProductionCode = episodeProductionCode; }

	public int getSeasonNumber() { return m_seasonNumber; }
	public void setSeasonNumber(int seasonNumber) { m_seasonNumber = seasonNumber; }

	public int getSeasonID() { return m_seasonID; }
	public void setSeasonID(int seasonID) { m_seasonID = seasonID; }

	public int getSeriesID() { return m_seriesID; }
	public void setSeriesID(int seriesID) { m_seriesID = seriesID; }

	public String getEpisodeStillPath() { return m_episodeStillPath; }
	public void setEpisodeStillPath(String episodeStillPath) { m_episodeStillPath = episodeStillPath; }

	public double getEpisodeVoteAverage() { return m_episodeVoteAverage; }
	public void setEpisodeVoteAverage(double episodeVoteAverage) { m_episodeVoteAverage = episodeVoteAverage; }

	public int getEpisodeVoteCount() { return m_episodeVoteCount; }
	public void setEpisodeVoteCount(int episodeVoteCount) { m_episodeVoteCount = episodeVoteCount; }

	public String getEpisodeCrew() { return m_episodeCrew; }
	public void setEpisodeCrew(String episodeCrew) { m_episodeCrew = episodeCrew; }

	public int getEpisodeState() { return m_episodeState; }
	public void setEpisodeState(int episodeState) { m_episodeState = episodeState; }

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
}

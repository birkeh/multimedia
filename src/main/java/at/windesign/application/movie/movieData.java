package at.windesign.application.movie;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.Language;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import com.omertron.themoviedbapi.model.credits.MediaCreditCrew;
import com.omertron.themoviedbapi.model.media.Video;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.movie.ProductionCompany;
import com.omertron.themoviedbapi.model.movie.ProductionCountry;
import com.omertron.themoviedbapi.model.network.Network;
import com.omertron.themoviedbapi.model.person.PersonBasic;
import com.omertron.themoviedbapi.tools.HttpTools;
import org.apache.http.client.HttpClient;
import org.yamj.api.common.http.SimpleHttpClientBuilder;
import org.zkoss.zul.ListModelList;

import java.sql.*;
import java.util.List;
import java.util.Objects;

public class movieData
{
	private int           m_movieID;
	private String        m_movieTitle;
	private String        m_originalTitle;
	private String        m_backdropPath;
	private String        m_posterPath;
	private String        m_overview;
	private Date          m_releaseDate;
	private String        m_genre;
	private String        m_IMDBID;
	private String        m_originalLanguage;
	private double        m_popularity;
	private String        m_productionCompanies;
	private String        m_productionCountries;
	private double        m_voteAverage;
	private int           m_voteCount;
	private String        m_adult;
	private String        m_belongsToCollection;
	private double        m_budget;
	private String        m_homepage;
	private double        m_revenue;
	private int           m_runtime;
	private String        m_spokenLanguages;
	private String        m_status;
	private String        m_tagline;
	private String        m_video;
	private String        m_cast;
	private String        m_crew;
	private int           m_state;
	private String        m_localPath;
	private String        m_resolution;
	private ListModelList m_model;

	private final movieDataSource ds = movieDataSource.INSTANCE;

	public movieData()
	{

	}

	public int getMovieID()
	{
		return (m_movieID);
	}

	public void setMovieID(int movieID)
	{
		m_movieID = movieID;
	}

	public String getMovieTitle()
	{
		return (m_movieTitle);
	}

	public void setMovieTitle(String movieTitle)
	{
		m_movieTitle = movieTitle;
	}

	public String getOriginalTitle()
	{
		return (m_originalTitle);
	}

	public void setOriginalTitle(String originalTitle)
	{
		m_originalTitle = originalTitle;
	}

	public String getBackdropPath()
	{
		return (m_backdropPath);
	}

	public void setBackdropPath(String backdropPath)
	{
		m_backdropPath = backdropPath;
	}

	public String getPosterPath()
	{
		return (m_posterPath);
	}

	public void setPosterPath(String posterPath)
	{
		m_posterPath = posterPath;
	}

	public String getOverview()
	{
		return (m_overview);
	}

	public void setOverview(String overview)
	{
		m_overview = overview;
	}

	public Date getReleaseDate()
	{
		return (m_releaseDate);
	}

	public void setReleaseDate(Date releaseDate)
	{
		m_releaseDate = releaseDate;
	}

	public String getGenre()
	{
		return (m_genre);
	}

	public void setGenre(String genre)
	{
		m_genre = genre;
	}

	public String getIMDBID()
	{
		return (m_IMDBID);
	}

	public void setIMDBID(String IMDBID)
	{
		m_IMDBID = IMDBID;
	}

	public String getOriginalLanguage()
	{
		return (m_originalLanguage);
	}

	public void setOriginalLanguage(String originalLanguage)
	{
		m_originalLanguage = originalLanguage;
	}

	public double getPopularity()
	{
		return (m_popularity);
	}

	public void setPopularity(double popularity)
	{
		m_popularity = popularity;
	}

	public String getProductionCompanies()
	{
		return (m_productionCompanies);
	}

	public void setProductionCompanies(String productionCompanies)
	{
		m_productionCompanies = productionCompanies;
	}

	public String getProductionCountries()
	{
		return (m_productionCountries);
	}

	public void setProductionCountries(String productionCountries)
	{
		m_productionCountries = productionCountries;
	}

	public double getVoteAverage()
	{
		return (m_voteAverage);
	}

	public void setVoteAverage(double voteAverage)
	{
		m_voteAverage = voteAverage;
	}

	public int getVoteCount()
	{
		return (m_voteCount);
	}

	public void setVoteCount(int voteCount)
	{
		m_voteCount = voteCount;
	}

	public String getAdult()
	{
		return (m_adult);
	}

	public void setAdult(String adult)
	{
		m_adult = adult;
	}

	public String getBelongsToCollection()
	{
		return (m_belongsToCollection);
	}

	public void setBelongsToCollection(String belongsToCollection)
	{
		m_belongsToCollection = belongsToCollection;
	}

	public double getBudget()
	{
		return (m_budget);
	}

	public void setBudget(double budget)
	{
		m_budget = budget;
	}

	public String getHomepage()
	{
		return (m_homepage);
	}

	public void setHomepage(String homepage)
	{
		m_homepage = homepage;
	}

	public double getRevenue()
	{
		return (m_revenue);
	}

	public void setRevenue(double revenue)
	{
		m_revenue = revenue;
	}

	public int getRuntime()
	{
		return (m_runtime);
	}

	public void setRuntime(int runtime)
	{
		m_runtime = runtime;
	}

	public String getSpokenLanguages()
	{
		return (m_spokenLanguages);
	}

	public void setSpokenLanguages(String spokenLanguages)
	{
		m_spokenLanguages = spokenLanguages;
	}

	public String getStatus()
	{
		return (m_status);
	}

	public void setStatus(String status)
	{
		m_status = status;
	}

	public String getTagline()
	{
		return (m_tagline);
	}

	public void setTagline(String tagline)
	{
		m_tagline = tagline;
	}

	public String getVideo()
	{
		return (m_video);
	}

	public void setVideo(String video)
	{
		m_video = video;
	}

	public String getCast()
	{
		return (m_cast);
	}

	public void setCast(String cast)
	{
		m_cast = cast;
	}

	public String getCrew()
	{
		return (m_crew);
	}

	public void setCrew(String crew)
	{
		m_crew = crew;
	}

	public int getState()
	{
		return (m_state);
	}

	public void setState(int state)
	{
		m_state = state;
	}

	public String getLocalPath()
	{
		return (m_localPath);
	}

	public void setLocalPath(String localPath)
	{
		m_localPath = localPath;
	}

	public String getResolution()
	{
		return (m_resolution);
	}

	public void setResolution(String resolution)
	{
		m_resolution = resolution;
	}

	public ListModelList getModel()
	{
		return (m_model);
	}

	public void setModel(ListModelList model)
	{
		m_model = model;
	}

	public boolean save()
	{
		try
		{
			Connection conn = ds.getConnection();
			Statement  stmt = conn.createStatement();
			stmt.execute("DELETE FROM movie WHERE movieID=" + m_movieID + ";");

			PreparedStatement ps = conn.prepareStatement("INSERT INTO movie (" +
														 "movieID, " +
														 "movieTitle, " +
														 "originalTitle, " +
														 "backdropPath, " +
														 "posterPath, " +
														 "overview, " +
														 "releaseDate, " +
														 "genre, " +
														 "imdbid, " +
														 "originalLanguage, " +
														 "popularity, " +
														 "productionCompanies, " +
														 "productionCountries, " +
														 "voteAverage, " +
														 "voteCount, " +
														 "adult, " +
														 "belongsToCollection, " +
														 "budget, " +
														 "homepage, " +
														 "revenue, " +
														 "runtime, " +
														 "spokenLanguages, " +
														 "status, " +
														 "tagline, " +
														 "video, " +
														 "`cast`, " +
														 "crew, " +
														 "state, " +
														 "localPath, " +
														 "resolution " +
														 ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
														);
			ps.setInt(1, m_movieID);
			ps.setString(2, m_movieTitle);
			ps.setString(3, m_originalTitle);
			ps.setString(4, m_backdropPath);
			ps.setString(5, m_posterPath);
			ps.setString(6, m_overview);
			ps.setDate(7, m_releaseDate);
			ps.setString(8, m_genre);
			ps.setString(9, m_IMDBID);
			ps.setString(10, m_originalLanguage);
			ps.setDouble(11, m_popularity);
			ps.setString(12, m_productionCompanies);
			ps.setString(13, m_productionCountries);
			ps.setDouble(14, m_voteAverage);
			ps.setInt(15, m_voteCount);
			ps.setString(16, m_adult);
			ps.setString(17, m_belongsToCollection);
			ps.setDouble(18, m_budget);
			ps.setString(19, m_homepage);
			ps.setDouble(20, m_revenue);
			ps.setInt(21, m_runtime);
			ps.setString(22, m_spokenLanguages);
			ps.setString(23, m_status);
			ps.setString(24, m_tagline);
			ps.setString(25, m_video);
			ps.setString(26, m_cast);
			ps.setString(27, m_crew);
			ps.setInt(28, m_state);
			ps.setString(29, m_localPath);
			ps.setString(30, m_resolution);

			boolean ret = ps.execute();
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

	public boolean delete()
	{
		try
		{
			Connection conn = ds.getConnection();
			Statement  stmt = conn.createStatement();
			stmt.execute("DELETE FROM movie WHERE movieID=" + m_movieID + ";");
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

	String personListToString(List<PersonBasic> personList)
	{
		String  str   = "";
		boolean first = true;

		for(PersonBasic p : personList)
		{
			if(first)
				first = false;
			else
				str = str + ",";
			str = str + p.getName();
		}

		return str;
	}

	String stringListToString(List<String> stringList)
	{
		String  str   = "";
		boolean first = true;

		for(String s : stringList)
		{
			if(first)
				first = false;
			else
				str = str + ",";
			str = str + s;
		}

		return str;
	}

	String networkListToString(List<Network> networkList)
	{
		String  str   = "";
		boolean first = true;

		for(Network n : networkList)
		{
			if(first)
				first = false;
			else
				str = str + ",";
			str = str + n.getName();
		}

		return str;
	}

	String productionCompaniesListToString(List<ProductionCompany> productionCompanyList)
	{
		String  str   = "";
		boolean first = true;

		for(ProductionCompany c : productionCompanyList)
		{
			if(first)
				first = false;
			else
				str = str + ",";
			str = str + c.getName();
		}

		return str;
	}

	String productionCountriesListToString(List<ProductionCountry> productionCountryList)
	{
		String  str   = "";
		boolean first = true;

		for(ProductionCountry c : productionCountryList)
		{
			if(first)
				first = false;
			else
				str = str + ",";
			str = str + c.getName();
		}

		return str;
	}

	String spokenLanguagesListToString(List<Language> spokenLanguagesList)
	{
		String  str   = "";
		boolean first = true;

		for(Language c : spokenLanguagesList)
		{
			if(first)
				first = false;
			else
				str = str + ",";
			str = str + c.getName();
		}

		return str;
	}


	String videoListToString(List<Video> videoList)
	{
		String  str   = "";
		boolean first = true;

		for(Video c : videoList)
		{
			if(first)
				first = false;
			else
				str = str + ",";
			str = str + c.getName();
		}

		return str;
	}

	String castListToString(List<MediaCreditCast> castList)
	{
		String  str   = "";
		boolean first = true;

		for(MediaCreditCast p : castList)
		{
			if(first)
				first = false;
			else
				str = str + "|";
			str = str + p.getName() + "," + p.getCharacter();
		}

		return str;
	}

	String crewListToString(List<MediaCreditCrew> crewList)
	{
		String  str   = "";
		boolean first = true;

		for(MediaCreditCrew p : crewList)
		{
			if(first)
				first = false;
			else
				str = str + "|";
			str = str + p.getName() + "," + p.getJob();
		}

		return str;
	}

	String genreListToString(List<Genre> genreList)
	{
		String  str   = "";
		boolean first = true;

		for(Genre g : genreList)
		{
			if(first)
				first = false;
			else
				str = str + ",";
			str = str + g.getName();
		}

		return str;
	}

	Date string2Date(String strDate)
	{
		if(strDate == null)
			return new Date(1900, 1, 1);
		else if(strDate.isEmpty())
			return new Date(1900, 1, 1);
		else
			return Date.valueOf(strDate);
	}

	public boolean fromTMDB(int id) throws MovieDbException
	{
		HttpClient httpClient;
		HttpTools  httpTools;

		httpClient = new SimpleHttpClientBuilder().build();
		httpTools = new HttpTools(httpClient);

		TheMovieDbApi api = new TheMovieDbApi("a33271b9e54cdcb9a80680eaf5522f1b", httpClient);
		return fromTMDB(id, api);
	}

	public boolean fromTMDB(int id, TheMovieDbApi api) throws MovieDbException
	{
		String appendToResponse = "credits,external_ids";

		MovieInfo movie = api.getMovieInfo(id, "de", appendToResponse);

		m_movieID = movie.getId();
		m_movieTitle = movie.getTitle();
		m_originalTitle = movie.getOriginalTitle();
		m_backdropPath = movie.getBackdropPath();
		m_posterPath = movie.getPosterPath();
		m_overview = movie.getOverview();
		m_releaseDate = string2Date(movie.getReleaseDate());
		m_genre = genreListToString(movie.getGenres());
		m_IMDBID = movie.getImdbID();
		m_originalLanguage = movie.getOriginalLanguage();
		m_popularity = movie.getPopularity();
		m_productionCompanies = productionCompaniesListToString(movie.getProductionCompanies());
		m_productionCountries = productionCountriesListToString(movie.getProductionCountries());
		m_voteAverage = movie.getVoteAverage();
		m_voteCount = movie.getVoteCount();
//		m_adult = movie;
//		m_belongsToCollection = movie;
		m_budget = movie.getBudget();
		m_homepage = movie.getHomepage();
		m_revenue = movie.getRevenue();
		m_runtime = movie.getRuntime();
		m_spokenLanguages = spokenLanguagesListToString(movie.getSpokenLanguages());
		m_status = movie.getStatus();
		m_tagline = movie.getTagline();
		m_video = videoListToString(movie.getVideos());
		m_cast = castListToString(movie.getCast());
		m_crew = crewListToString(movie.getCrew());
		m_state = 1;

		return true;
	}

	void copyFrom(movieData movie)
	{
		setLocalPath(movie.getLocalPath());
		setResolution(movie.getResolution());
		setState(movie.getState());
	}

	public String getMovieStyle()
	{
		String style = "";

		String color = "";

		if(m_state == 1)
			color = "background-color: #C0C0C0;";
		else if(m_state == 2)
			color = "background-color: #0000C0; color: #FFFFFF";
		else
			color = "background-color: #00C000;";

		style += color;

		return style;
	}
}

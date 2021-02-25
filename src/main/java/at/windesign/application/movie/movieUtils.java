package at.windesign.application.movie;

import org.zkoss.zul.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class movieUtils
{
	static public void loadMovies(movieDataSource ds, Listbox moviesList)
	{
		List<movieData>                movieList  = new ArrayList<>();
		SortedMap<Integer, Listheader> listHeader = new TreeMap<>();

		ListModelList movieListModel = new ListModelList();
		moviesList.setModel(movieListModel);
		moviesList.setAttribute("listModelList", movieListModel);

		moviesList.getListhead().getChildren().clear();
		Listhead   head       = moviesList.getListhead();
		Listheader movieName  = new Listheader("Movie Name");
		Listheader firstAired = new Listheader("First Aired");
		Listheader resolution = new Listheader("Resolution");
		Listheader overview   = new Listheader("Overview");

		movieName.setHflex("min");
		firstAired.setHflex("min");
		resolution.setHflex("min");
		overview.setHflex("min");

		head.appendChild(movieName);
		head.appendChild(firstAired);
		head.appendChild(resolution);
		head.appendChild(overview);

		movieRenderer renderer = new movieRenderer();
		moviesList.setItemRenderer(renderer);

		try
		{
			Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT   movieID, " +
					"         movieTitle, " +
					"         originalTitle, " +
					"         backdropPath, " +
					"         posterPath, " +
					"         overview, " +
					"         releaseDate, " +
					"         genre, " +
					"         imdbid, " +
					"         originalLanguage, " +
					"         popularity, " +
					"         productionCompanies, " +
					"         productionCountries, " +
					"         voteAverage, " +
					"         voteCount, " +
					"         adult, " +
					"         belongsToCollection, " +
					"         budget, " +
					"         homepage, " +
					"         revenue, " +
					"         runtime, " +
					"         spokenLanguages, " +
					"         status, " +
					"         tagline, " +
					"         video, " +
					"         `cast`, " +
					"         crew, " +
					"         state, " +
					"         localPath, " +
					"         resolution " +
					"FROM     multimedia.movie " +
					"ORDER BY movieTitle, " +
					"         releaseDate;"
											);

			// fetch all events from database
			movieData movie;

			while(rs.next())
			{
				movie = new movieData();

				int movieID = rs.getInt("movieID");

				movie.setMovieID(rs.getInt("movieID"));
				movie.setMovieTitle(rs.getString("movieTitle"));
				movie.setOriginalTitle(rs.getString("originalTitle"));
				movie.setBackdropPath(rs.getString("backdropPath"));
				movie.setPosterPath(rs.getString("posterPath"));
				movie.setOverview(rs.getString("overview"));
				movie.setReleaseDate(rs.getDate("releaseDate"));
				movie.setGenre(rs.getString("genre"));
				movie.setIMDBID(rs.getString("IMDBID"));
				movie.setOriginalLanguage(rs.getString("originalLanguage"));
				movie.setPopularity(rs.getDouble("popularity"));
				movie.setProductionCompanies(rs.getString("productionCompanies"));
				movie.setProductionCountries(rs.getString("productionCountries"));
				movie.setVoteAverage(rs.getDouble("voteAverage"));
				movie.setVoteCount(rs.getInt("voteCount"));
				movie.setAdult(rs.getString("adult"));
				movie.setBelongsToCollection(rs.getString("belongsToCollection"));
				movie.setBudget(rs.getDouble("budget"));
				movie.setHomepage(rs.getString("homepage"));
				movie.setRevenue(rs.getDouble("revenue"));
				movie.setRuntime(rs.getInt("runtime"));
				movie.setSpokenLanguages(rs.getString("spokenLanguages"));
				movie.setStatus(rs.getString("status"));
				movie.setTagline(rs.getString("tagline"));
				movie.setVideo(rs.getString("video"));
				movie.setCast(rs.getString("cast"));
				movie.setCrew(rs.getString("crew"));
				movie.setState(rs.getInt("state"));
				movie.setLocalPath(rs.getString("localPath"));
				movie.setResolution(rs.getString("resolution"));

				movieList.add(movie);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ds.close();
		}

		for(movieData movie : movieList)
		{
			movieListModel.add(movie);
			movie.setModel(movieListModel);
		}
	}
}

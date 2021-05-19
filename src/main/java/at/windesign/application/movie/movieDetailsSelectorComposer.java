package at.windesign.application.movie;

import com.omertron.themoviedbapi.MovieDbException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

public class movieDetailsSelectorComposer extends SelectorComposer<Component>
{
	private static final long serialVersionUID = 1L;

	@Wire
	private Window detailsMovie;

	@Wire
	protected Textbox localPath;

	@Wire
	protected Combobox resolution;

	@Wire
	protected Radiogroup progress;

	@Listen("onClick = #saveButton")
	public void save()
	{
		movieData movie = getCurrentValues();
		movie.save();

		Listitem      item   = (Listitem) detailsMovie.getAttribute("item");
		movieData     movie1 = (movieData) detailsMovie.getAttribute("movie");
		ListModelList model  = movie1.getModel();
		int           index  = item.getIndex();

		movie.setModel(model);
		model.remove(index);
		model.add(index, movie);

		Window mainWindow = (Window)Path.getComponent("/mainWindow");
		Tab tabMovies = (Tab)mainWindow.getFellow("tabMovies");
		movieDataSource ds = movieDataSource.INSTANCE;
		movieUtils.movieMetrics(tabMovies, ds);

		detailsMovie.onClose();
	}

	@Listen("onClick = #cancelButton")
	public void cancel()
	{
		detailsMovie.onClose();
	}

	@Listen("onClick = #updateButton")
	public void update()
	{
		movieData movie = (movieData) detailsMovie.getAttribute("movie");
		movieData movieNew;

		try
		{
			movieNew = new movieData();
			movieNew.fromTMDB(movie.getMovieID());
			movieNew.copyFrom(movie);

			movieNew.save();

			Listitem      item  = (Listitem) detailsMovie.getAttribute("item");
			ListModelList model = movie.getModel();

			int index = item.getIndex();

			movieNew.setModel(model);
			model.remove(index);
			model.add(index, movieNew);
		}
		catch(MovieDbException e)
		{
			e.printStackTrace();
		}

		detailsMovie.onClose();
	}

	@Listen("onClick = #deleteButton")
	public void delete()
	{
		movieData movie = (movieData) detailsMovie.getAttribute("movie");

		Messagebox.show("Are you sure to delete \"" + movie.getMovieTitle() + "\"?",
						"Question", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener()
						{
							public void onEvent(Event e)
							{
								if("onOK".equals(e.getName()))
								{
									movie.delete();
									Listitem item      = (Listitem) detailsMovie.getAttribute("item");
									Listbox  movieList = item.getListbox();
									movieList.removeItemAt(item.getIndex());

									detailsMovie.onClose();
								}
								else if("onCancel".equals(e.getName()))
								{
									detailsMovie.onClose();
								}
							}
						}
					   );

		Window mainWindow = (Window)Path.getComponent("/mainWindow");
		Tab tabMovies = (Tab)mainWindow.getFellow("tabMovies");
		movieDataSource ds = movieDataSource.INSTANCE;
		movieUtils.movieMetrics(tabMovies, ds);

		detailsMovie.onClose();
	}

	private movieData getCurrentValues()
	{
		movieData movie = (movieData) detailsMovie.getAttribute("movie");

		movie.setLocalPath(localPath.getValue());
		movie.setResolution(resolution.getValue());
		movie.setState(progress.getSelectedIndex() + 1);

		return movie;
	}
}

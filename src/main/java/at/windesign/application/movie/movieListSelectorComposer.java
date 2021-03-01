package at.windesign.application.movie;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.LabelElement;

import java.util.HashMap;
import java.util.Map;

public class movieListSelectorComposer extends SelectorComposer<Component>
{
	@Wire
	private Listbox movieList;

	@Listen("onDoubleClick = #movieList")
	public void onClickMovieList()
	{
		Listitem item = movieList.getSelectedItem();

		if(item == null)
			return;

		movieData m = (movieData) item.getValue();

		Map<String, Object> arguments = new HashMap<String, Object>();

		arguments.put("movie", m);
		arguments.put("item", item);

		String template = "/movie/detailsMovie.zul";
		Window window   = (Window) Executions.createComponents(template, null, arguments);

		window.doModal();
	}

	@Listen("onClick = #updateAll")
	public void onClickUpdateAll()
	{
		Map<String, Object> arguments = new HashMap<String, Object>();

		arguments.put("movieList", movieList);

		String template = "/movie/updateMovie.zul";
		Window window   = (Window) Executions.createComponents(template, null, arguments);

		window.doModal();
	}

	@Listen("onClick = #searchMovie")
	public void onSearchMovie()
	{
		Map<String, Object> arguments = new HashMap<String, Object>();

		arguments.put("movieList", movieList);

		String template = "/movie/searchMovie.zul";
		Window window   = (Window) Executions.createComponents(template, null, arguments);

		window.doModal();
	}

	@Listen("onClick = menuitem")
	public void onMoviePopup(MouseEvent event)
	{
		String label = ((LabelElement)event.getTarget()).getLabel();

		if(label.compareToIgnoreCase("set Init") == 0)
			setState(1);
		else if(label.compareToIgnoreCase("set Progress") == 0)
			setState(2);
		else if(label.compareToIgnoreCase("set Done") == 0)
			setState(3);
		else if(label.compareToIgnoreCase("update") == 0)
			updateMovie();
		else if(label.compareToIgnoreCase("delete") == 0)
			deleteMovie();
	}

	public void setState(int state)
	{
		Listitem item = movieList.getSelectedItem();

		if(item == null)
			return;

		movieData m = (movieData) item.getValue();
		m.setState(state);
		m.save();

		ListModelList model = m.getModel();
		int index = item.getIndex();
		model.remove(index);
		model.add(index, m);
	}

	public void updateMovie()
	{
		Listitem item = movieList.getSelectedItem();

		if(item == null)
			return;

		movieData m = (movieData) item.getValue();
		movieData mNew = new movieData();

		try
		{
			mNew.fromTMDB(m.getMovieID());
			mNew.copyFrom(m);
			mNew.save();

			ListModelList model = m.getModel();
			int index = item.getIndex();

			mNew.setModel(model);
			model.remove(index);
			model.add(index, mNew);
		}
		catch(Exception e)
		{
		}
	}

	public void deleteMovie()
	{
		Listitem item = movieList.getSelectedItem();

		if(item == null)
			return;

		movieData m = (movieData) item.getValue();

		Messagebox.show("Are you sure to delete \"" + m.getMovieTitle() + "\"?",
						"Question", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener()
						{
							public void onEvent(Event e)
							{
								if("onOK".equals(e.getName()))
								{
									m.delete();
									movieList.removeItemAt(item.getIndex());
								}
								else if("onCancel".equals(e.getName()))
								{
									return;
								}
							}
						}
					   );
	}
}

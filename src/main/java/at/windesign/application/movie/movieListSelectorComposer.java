package at.windesign.application.movie;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

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
}

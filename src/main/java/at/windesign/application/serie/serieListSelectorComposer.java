package at.windesign.application.serie;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.LabelElement;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public class serieListSelectorComposer extends SelectorComposer<Component>
{
	@Wire
	private Listbox seriesList;

	@Listen("onDoubleClick = #seriesList")
	public void onClickSeriesList()
	{
		Listitem item = seriesList.getSelectedItem();

		if(item == null)
			return;

		serieData s = (serieData) item.getValue();

		Map<String, Object> arguments = new HashMap<String, Object>();

		arguments.put("serie", s);
		arguments.put("item", item);

		String template = "/serie/detailsSerie.zul";
		Window window   = (Window) Executions.createComponents(template, null, arguments);

		window.doModal();
	}

	@Listen("onClick = #updateAll")
	public void onClickUpdateAll()
	{
		update(true);
	}

	@Listen("onClick = #updateOpen")
	public void onClickUpdateOpen()
	{
		update(false);
	}

	private void update(boolean all)
	{
		Map<String, Object> arguments = new HashMap<String, Object>();

		arguments.put("seriesList", seriesList);
		arguments.put("all", all);

		String template = "/serie/updateSerie.zul";
		Window window   = (Window) Executions.createComponents(template, null, arguments);

		window.doModal();
	}

	@Listen("onClick = #searchSerie")
	public void onSearchSerie()
	{
		Map<String, Object> arguments = new HashMap<String, Object>();

		arguments.put("serieList", seriesList);

		String template = "/serie/searchSerie.zul";
		Window window   = (Window) Executions.createComponents(template, null, arguments);

		window.doModal();
	}

	@Listen("onClick = #discoverSerie")
	public void onDiscoverSerie()
	{
		Map<String, Object> arguments = new HashMap<String, Object>();

		arguments.put("serieList", seriesList);

		String template = "/serie/discoverSerie.zul";
		Window window   = (Window) Executions.createComponents(template, null, arguments);

		window.doModal();
	}

	@Listen("onClick = menuitem")
	public void onSeriePopup(MouseEvent event)
	{
		String label = ((LabelElement)event.getTarget()).getLabel();

		if(label.compareToIgnoreCase("set all Progress to Done") == 0)
			setAllProgressToDone();
		else if(label.compareToIgnoreCase("update") == 0)
			updateSerie();
		else if(label.compareToIgnoreCase("delete") == 0)
			deleteSerie();
	}

	public void setAllProgressToDone()
	{
		Listitem item = seriesList.getSelectedItem();

		if(item == null)
			return;

		serieData s = (serieData) item.getValue();

		SortedMap<Integer, seasonData> seasons = s.getSeasons();

		for(seasonData season : seasons.values())
		{
			SortedMap<Integer, episodeData> episodes = season.getEpisodes();

			for(episodeData episode : episodes.values())
			{
				if(episode.getEpisodeState() == 2)
					episode.setEpisodeState(3);
			}
		}
		s.save();
		s.recalcState();

		ListModelList model = s.getModel();
		int index = item.getIndex();
		model.remove(index);
		model.add(index, s);
	}

	public void updateSerie()
	{
		Listitem item = seriesList.getSelectedItem();

		if(item == null)
			return;

		serieData m = (serieData) item.getValue();
		serieData mNew = new serieData();

		try
		{
			mNew.fromTMDB(m.getSeriesID());
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

	public void deleteSerie()
	{
		Listitem item = seriesList.getSelectedItem();

		if(item == null)
			return;

		serieData s = (serieData) item.getValue();

		Messagebox.show("Are you sure to delete \"" + s.getSeriesName() + "\"?",
						"Question", Messagebox.OK | Messagebox.CANCEL,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener()
						{
							public void onEvent(Event e)
							{
								if("onOK".equals(e.getName()))
								{
									s.delete();
									seriesList.removeItemAt(item.getIndex());
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

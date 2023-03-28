package at.windesign.application.serie;

import org.ini4j.Ini;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.LabelElement;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

public class serieListSelectorComposer extends SelectorComposer<Component>
{
	@Wire
	private Listbox seriesList;

	@Wire
	private Checkbox useFilter;

	@Wire
	private Checkbox filterInitialized;

	@Wire
	private Checkbox filterInProgress;

	@Wire
	private Checkbox filterDone;

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
		String label = ((LabelElement) event.getTarget()).getLabel();

		if(label.compareToIgnoreCase("set all Progress to Done") == 0)
			setAllProgressToDone();
		else if(label.compareToIgnoreCase("update") == 0)
			updateSerie();
		else if(label.compareToIgnoreCase("delete") == 0)
			deleteSerie();
		else if(label.compareToIgnoreCase("open download link...") == 0)
			openDownloadLinkSerie();
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
		int           index = item.getIndex();
		model.remove(index);
		model.add(index, s);
	}

	public void openDownloadLinkSerie()
	{
		Listitem item = seriesList.getSelectedItem();

		if(item == null)
			return;

		serieData s            = (serieData) item.getValue();
		String    downloadLink = s.getSeriesDownload();

		if(downloadLink == null)
			downloadLink = "";

//		if(downloadLink.length() == 0)
//			return;

		Executions.getCurrent().sendRedirect(downloadLink,"_blank");
	}

	public void updateSerie()
	{
		Listitem item = seriesList.getSelectedItem();

		if(item == null)
			return;

		serieData m    = (serieData) item.getValue();
		serieData mNew = new serieData();

		try
		{
			mNew.fromTMDB(m.getSeriesID());
			mNew.copyFrom(m);
			mNew.save();

			ListModelList model = m.getModel();
			int           index = item.getIndex();

			mNew.setModel(model);
			model.remove(index);
			model.add(index, mNew);
		} catch(Exception e)
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

							Window          mainWindow = (Window) Path.getComponent("/mainWindow");
							Tab             tabSeries  = (Tab) mainWindow.getFellow("tabSeries");
							serieDataSource ds         = serieDataSource.INSTANCE;
							serieUtils.serieMetrics(tabSeries, ds);
						} else if("onCancel".equals(e.getName()))
						{
							return;
						}
					}
				}
		);
	}

	@Listen("onClick = #useFilter")
	public void onUseFilter()
	{
		if(useFilter.isChecked())
		{
			filterInitialized.setDisabled(false);
			filterInProgress.setDisabled(false);
			filterDone.setDisabled(false);
		} else
		{
			filterInitialized.setDisabled(true);
			filterInProgress.setDisabled(true);
			filterDone.setDisabled(true);
		}
	}

	@Listen("onClick = #applyFilter")
	public void onApplyFilter()
	{
		Ini ini = null;
		try
		{
			File iniFile = new File(System.getProperty("java.io.tmpdir") + "/multimedia.ini");
			if(!iniFile.exists())
				iniFile.createNewFile();
			ini = new Ini(iniFile);
			ini.put("main", "currentTab", 0);
			ini.put("filter", "useFilter", useFilter.isChecked());
			ini.put("filter", "filterInitialized", filterInitialized.isChecked());
			ini.put("filter", "filterInProgress", filterInProgress.isChecked());
			ini.put("filter", "filterDone", filterDone.isChecked());

			ini.store();

			Executions.sendRedirect("");
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

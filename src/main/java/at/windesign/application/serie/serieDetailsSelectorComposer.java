package at.windesign.application.serie;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbTV;
import info.movito.themoviedbapi.model.tv.TvSeason;
import info.movito.themoviedbapi.model.tv.TvSeries;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import java.util.Iterator;
import java.util.List;

//a33271b9e54cdcb9a80680eaf5522f1b

public class serieDetailsSelectorComposer extends SelectorComposer<Component>
{
	private static final long serialVersionUID = 1L;

	@Wire
	private Window detailsSerie;

	@Wire
	protected Textbox downloadLink;

	@Wire
	protected Textbox localPath;

	@Wire
	protected Combobox resolution;

	@Wire
	protected Checkbox cliffhanger;

	@Listen("onClick = #saveButton")
	public void save()
	{
		serieData serie = getCurrentValues();
		serie.recalcState();
		serie.save();

		Listitem      item   = (Listitem) detailsSerie.getAttribute("item");
		serieData     serie1 = (serieData) detailsSerie.getAttribute("serie");
		ListModelList model  = serie1.getModel();
		int           index  = item.getIndex();

		serie.setModel(model);
		model.remove(index);
		model.add(index, serie);

		detailsSerie.onClose();
	}

	@Listen("onClick = #cancelButton")
	public void cancel()
	{
		detailsSerie.onClose();
	}

	@Listen("onClick = #updateButton")
	public void update()
	{
		serieData serie = (serieData) detailsSerie.getAttribute("serie");
/*
		TmdbApi  tmdbAPI = new TmdbApi("a33271b9e54cdcb9a80680eaf5522f1b");
		TmdbTV   tmdbTV  = tmdbAPI.getTvSeries();
		TvSeries series  = tmdbTV.getSeries(60948, "de");

		TvSeason season = tmdbAPI.getTvSeasons().getSeason(60948, 2, "de");
*/

		detailsSerie.onClose();
	}

	@Listen("onClick = #deleteButton")
	public void delete()
	{
		serieData serie = (serieData) detailsSerie.getAttribute("serie");

		Messagebox.show("Are you sure to delete \"" + serie.getSeriesName() + "\"?", "Confirm Dialog", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener()
		{
			public void onEvent(Event evt) throws InterruptedException
			{
				if(evt.getName().equals("onYES"))
				{
					alert("Data Saved!");
					detailsSerie.onClose();
				}
				else
				{
					detailsSerie.onClose();
				}
			}
		});
	}

	private serieData getCurrentValues()
	{
		serieData serie = (serieData) detailsSerie.getAttribute("serie");

		serie.setSeriesDownload(downloadLink.getValue());
		serie.setSeriesLocalPath(localPath.getValue());
		serie.setSeriesResolution(resolution.getValue());
		serie.setSeriesCliffhanger(cliffhanger.isChecked());

		for(Component comp : detailsSerie.getChildren())
		{
			String id = comp.getId();
			if(id.startsWith("groupboxSeason"))
				parseSeason((Groupbox) comp, serie);
		}

		return serie;
	}

	private void parseSeason(Groupbox groupbox, serieData serie)
	{
		int season = Integer.parseInt(groupbox.getId().substring(14));

		for(Component comp : groupbox.getChildren())
		{
			String id = comp.getId();
			if(id.startsWith("radiogroupSeason"))
			{
				Radiogroup group = (Radiogroup) comp;

				int episode = Integer.parseInt(id.substring(id.indexOf("_") + 1));
				int state   = group.getSelectedIndex() + 1;

				serie.setEpisodeState(season, episode, state);
			}
		}
	}
}
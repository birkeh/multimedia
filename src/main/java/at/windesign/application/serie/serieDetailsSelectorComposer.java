package at.windesign.application.serie;

import com.omertron.themoviedbapi.MovieDbException;
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
		serieData serieNew;

		try
		{
			serieNew = new serieData();
			serieNew.fromTMDB(serie.getSeriesID());
			serieNew.setSeriesDownload(serie.getSeriesDownload());
			serieNew.setSeriesLocalPath(serie.getSeriesLocalPath());
			serieNew.setSeriesResolution(serie.getSeriesResolution());
			serieNew.setSeriesCliffhanger(serie.getSeriesCliffhanger());

			for(int season : serieNew.getSeasons().keySet())
			{
				seasonData sDataOriginal = serie.getSeasons().get(season);

				if(sDataOriginal != null)
				{
					seasonData sData = serieNew.getSeasons().get(season);

					for(int episode : sData.getEpisodes().keySet())
					{
						episodeData eDataOriginal = sDataOriginal.getEpisodes().get(episode);

						if(eDataOriginal != null)
						{
							episodeData eData = sData.getEpisodes().get(episode);

							eData.setEpisodeState(eDataOriginal.getEpisodeState());
						}
					}
				}
			}

			serieNew.recalcState();
			serieNew.save();

			Listitem      item  = (Listitem) detailsSerie.getAttribute("item");
			ListModelList model = serie.getModel();

			int index = item.getIndex();

			serieNew.setModel(model);
			model.remove(index);
			model.add(index, serieNew);
		}
		catch(MovieDbException e)
		{
			e.printStackTrace();
		}

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
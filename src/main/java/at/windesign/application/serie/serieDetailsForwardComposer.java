package at.windesign.application.serie;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.SortedMap;
import java.util.TreeMap;

public class serieDetailsForwardComposer extends GenericForwardComposer<Component>
{
	private static final long            serialVersionUID = 1L;
	private final        serieDataSource ds               = serieDataSource.INSTANCE;
	private              int             opacity          = 80;

	@Wire
	private Window detailsSerie;

	@Wire
	protected Groupbox overview;

	@Wire
	protected Label overviewLabel;

	@Wire
	protected Groupbox settings;

	@Wire
	protected Textbox downloadLink;

	@Wire
	protected Textbox localPath;

	@Wire
	protected Combobox resolution;

	@Wire
	protected Checkbox cliffhanger;

	@Wire
	protected Hbox buttons;

	@Wire
	protected Button saveButton;

	@Wire
	protected Button cancelButton;

	@Wire
	protected Button updateButton;

	@Wire
	protected Button deleteButton;

	private serieData m_serie;
	private Listitem  m_item;
	private Listitem  m_list;

	protected TreeMap<String, Radiogroup> radioGroups = new TreeMap<>();

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		overview.setStyle("opacity: " + opacity + "%");
		settings.setStyle("opacity: " + opacity + "%");

		int hD = (int) self.getDesktop().getAttribute("desktopHeight");
		int wD = (int) self.getDesktop().getAttribute("desktopWidth");

		detailsSerie.setWidth(wD - 10 + "px");
		detailsSerie.setHeight(hD - 10 + "px");

		if(arg.containsKey("serie"))
			m_serie = (serieData) arg.get("serie");
		else
			detailsSerie.detach();

		if(arg.containsKey("item"))
			m_item = (Listitem) arg.get("item");
		else
			detailsSerie.detach();

		detailsSerie.setAttribute("serie", m_serie);
		detailsSerie.setAttribute("item", m_item);

		if(m_serie.getSeriesBackdrop() != null)
		{
			String uri = "http://image.tmdb.org/t/p/original" + m_serie.getSeriesBackdrop();
			URL    url = new URL(uri);
			AImage img = new AImage(url);

			int w  = Integer.parseInt(detailsSerie.getWidth().replace("px", ""));
			int h  = Integer.parseInt(detailsSerie.getHeight().replace("px", ""));
			int wI = img.getWidth();
			int hI = img.getHeight();
			int wR = w;
			int hR = hI * w / wI;

			if(hR < h)
			{
				hR = h;
				wR = wI * h / hI;
			}

			String size   = wR + "px " + hR + "px";
			String format = "overflow:auto; background:url('%1$s') no-repeat center center fixed; -webkit-background-size: " + size + "; -moz-background-size: \" + size + \"; -o-background-size: \" + size + \"; background-size: \" + size + \";";
			String style  = String.format(format, uri);
			detailsSerie.setContentStyle(style);
		}

		Statement stmt = ds.getStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT		resolution" +
						" FROM		resolution" +
						" ORDER BY	sort;"
		);

		while(rs.next())
		{
			resolution.appendItem(rs.getString("resolution"));
		}

		detailsSerie.setTitle(m_serie.getSeriesName() + " (" + m_serie.getSeriesFirstAired() + ")");
		overviewLabel.setValue(m_serie.getSeriesOverview());
		downloadLink.setValue(m_serie.getSeriesDownload());
		localPath.setValue(m_serie.getSeriesLocalPath());
		resolution.setValue(m_serie.getSeriesResolution());
		cliffhanger.setChecked(m_serie.getSeriesCliffhanger());

		for(int s = m_serie.getMinSeason(); s <= m_serie.getMaxSeason(); s++)
		{
			if(s < 1)
				continue;

			addSeasonPanel(m_serie.getSeasons().get(s));
		}
	}

	protected void addSeasonPanel(seasonData season)
	{
		SortedMap<Integer, episodeData> episodes     = season.getEpisodes();
		int                             seasonNumber = season.getSeasonNumber();

		if(episodes.size() == 0)
			return;

		int maxEpisode = 0;

		Groupbox groupbox = new Groupbox();
		groupbox.setId("groupboxSeason" + season.getSeasonNumber());
		groupbox.setStyle("opacity: " + opacity + "%;");
		groupbox.setContentStyle(" overflow: auto;");

		Caption caption = new Caption();
		caption.setLabel("Season " + season.getSeasonNumber());
		groupbox.insertBefore(caption, null);

		for(int key : episodes.keySet())
		{
			int _episode = key;

			Radiogroup radiogroup = new Radiogroup();
			radiogroup.setName("radiogroupSeason" + seasonNumber + "_" + _episode);
			radiogroup.setId("radiogroupSeason" + seasonNumber + "_" + _episode);

			radioGroups.put("radiogroupSeason" + seasonNumber + "_" + _episode, radiogroup);

			groupbox.insertBefore(radiogroup, null);

			maxEpisode = _episode;
		}


		NoDOM nodom = new NoDOM();
		groupbox.insertBefore(nodom, null);

		Div div = new Div();
		nodom.insertBefore(div, null);

		Label dummy = new Label();
		div.insertBefore(dummy, null);

		for(int key : episodes.keySet())
		{
			int _episode = key;

			Label episodeLabel = new Label();
			episodeLabel.setValue("" + _episode);
			div.insertBefore(episodeLabel, null);
		}

		addButton("init", "buttonInit", seasonNumber, episodes.firstKey(), episodes.lastKey(), 0, div);
		for(int key : episodes.keySet())
		{
			int _episode = key & 0xFF;
			int _state   = episodes.get(key).getEpisodeState();

			addRadio("init" + seasonNumber + "_" + _episode, "radiogroupSeason" + seasonNumber + "_" + _episode, _state == 1, div);
		}

		addButton("prog", "buttonProg", seasonNumber, episodes.firstKey(), episodes.lastKey(), 1, div);
		for(int key : episodes.keySet())
		{
			int _episode = key & 0xFF;
			int _state   = episodes.get(key).getEpisodeState();

			addRadio("prog" + seasonNumber + "_" + _episode, "radiogroupSeason" + seasonNumber + "_" + _episode, _state == 2, div);
		}

		addButton("done", "buttonDone", seasonNumber, episodes.firstKey(), episodes.lastKey(), 2, div);
		for(int key : episodes.keySet())
		{
			int _episode = key & 0xFF;
			int _state   = episodes.get(key).getEpisodeState();

			addRadio("done" + seasonNumber + "_" + _episode, "radiogroupSeason" + seasonNumber + "_" + _episode, _state == 3, div);
		}

		String styleString = "display: grid; " +
				"grid-template-areas: \"a ";

		for(int i = 0; i < maxEpisode; i++)
			styleString += "a ";
		styleString = styleString + "\"; grid-auto-columns: max-content; grid-gap: 10px; justify-items: center;";
		div.setStyle(styleString);

		detailsSerie.insertBefore(groupbox, buttons);
	}

	protected void addButton(String name, String idPrefix, int seasonNumber, int firstEpisode, int lastEpisode, int id, Div div)
	{
		Button button = new Button();
		button.setLabel(name);
		button.setId(idPrefix + seasonNumber);
		div.insertBefore(button, null);

		button.addEventListener("onClick", new EventListener<Event>()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				for(int i = firstEpisode; i <= lastEpisode; i++)
				{
					Radiogroup group = radioGroups.get("radiogroupSeason" + seasonNumber + "_" + i);
					group.setSelectedIndex(id);
				}
			}
		});
	}

	/**
	 * @param id      id of the radio button
	 * @param group   name of the radio button group
	 * @param checked set the radio button to selected / not selected
	 * @param div     parent DIV the radio button is created in
	 */
	protected void addRadio(String id, String group, boolean checked, Div div)
	{
		Radio radio = new Radio();
		radio.setId(id);
		radio.setRadiogroup(group);
		div.insertBefore(radio, null);
		radio.setChecked(checked);
	}
}

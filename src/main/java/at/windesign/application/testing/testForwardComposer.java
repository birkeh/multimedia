package at.windesign.application.testing;

import at.windesign.application.serie.serieDataSource;
import org.zkoss.idom.Text;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedMap;

public class testForwardComposer extends GenericForwardComposer<Component>
{
	private static final long            serialVersionUID = 1L;
	private final        serieDataSource ds               = serieDataSource.INSTANCE;
	private              int             opacity          = 70;

	@Wire
	private Window test;

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
	protected Button deleteButton;

//	private serie m_serie;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

//		overview.setStyle("opacity: " + opacity + "%");
//		settings.setStyle("opacity: " + opacity + "%");
//
//		try
//		{
			int hD = (int) self.getDesktop().getAttribute("desktopHeight");
			int wD = (int) self.getDesktop().getAttribute("desktopWidth");

			hD = 500;
			test.setWidth(wD - 10 + "px");
			test.setHeight(hD - 10 + "px");

//
//			if(arg.containsKey("serie"))
//				m_serie = (serie) arg.get("serie");
//			else
//				detailsSerie.detach();
//
//			if(m_serie.getSeriesBackdrop() != null)
//			{
//				String uri = "http://image.tmdb.org/t/p/original" + m_serie.getSeriesBackdrop();
//				URL    url = new URL(uri);
//				AImage img = new AImage(url);
//
//				int w  = Integer.parseInt(detailsSerie.getWidth().replace("px", ""));
//				int h  = Integer.parseInt(detailsSerie.getHeight().replace("px", ""));
//				int wI = img.getWidth();
//				int hI = img.getHeight();
//				int wR = w;
//				int hR = hI * w / wI;
//
//				if(hR < h)
//				{
//					hR = h;
//					wR = wI * h / hI;
//				}
//
//				String size   = String.valueOf(wR) + "px " + String.valueOf(hR) + "px";
//				String format = "background:url('%1$s') no-repeat center center fixed; -webkit-background-size: " + size + "; -moz-background-size: \" + size + \"; -o-background-size: \" + size + \"; background-size: \" + size + \";";
//				String style  = String.format(format, uri);
//				detailsSerie.setContentStyle(style);
//			}
//
//			try
//			{
//				Statement stmt = ds.getStatement();
//				ResultSet rs = stmt.executeQuery(
//						"SELECT		resolution" +
//								" FROM		resolution" +
//								" ORDER BY	sort;"
//				);
//
//				while(rs.next())
//				{
//					resolution.appendItem(rs.getString("resolution"));
//				}
//			}
//			catch(SQLException e)
//			{
//				e.printStackTrace();
//			}
//			finally
//			{
//				ds.close();
//			}
//
//			detailsSerie.setTitle(m_serie.getSeriesName() + " (" + m_serie.getSeriesFirstAired() + ")");
//			overviewLabel.setValue(m_serie.getSeriesOverview());
//			downloadLink.setValue(m_serie.getSeriesDownload());
//			localPath.setValue(m_serie.getSeriesLocalPath());
//			resolution.setValue(m_serie.getSeriesResolution());
//			cliffhanger.setChecked(m_serie.getSeriesCliffhanger());
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			test.detach();
//		}
//		finally
//		{
//		}
//
//
//		for(int i = m_serie.getMinSeason(); i <= m_serie.getMaxSeason(); i++)
//		{
//			if(i < 1)
//				continue;
//
//			addSeasonPanel(i, m_serie.getEpisodeState());
//		}
	}

	protected void addSeasonPanel(int season, SortedMap<Integer, Integer> state)
	{
		int maxEpisode = 0;
		String styleString = "display: grid; " +
				"grid-template-columns: 4fr ";

		Groupbox groupbox = new Groupbox();
		groupbox.setId("groupboxSeason" + season);

		Caption caption = new Caption();
		caption.setLabel("Season " + season);
		groupbox.insertBefore(caption, null);

		for(int key : state.keySet())
		{
			int _season = key >> 8;
			int _episode = key & 0xFF;

			if(_season == season)
			{
				Radiogroup radiogroup = new Radiogroup();
				radiogroup.setName("radiogroupSeason" + _season + "_" + _episode);
				radiogroup.setId("radiogroupSeason" + _season + "_" + _episode);
				groupbox.insertBefore(radiogroup, null);

				maxEpisode = _episode;
			}
			else if(_season > season)
				break;
		}

		NoDOM nodom = new NoDOM();
		groupbox.insertBefore(nodom, null);

		Div div = new Div();
		//div.setWidth("100%");
		nodom.insertBefore(div, null);

		Label dummy = new Label();
		div.insertBefore(dummy, null);

		for(int key : state.keySet())
		{
			int _season = key >> 8;
			int _episode = key & 0xFF;

			if(_season == season)
			{
				Label episodeLabel = new Label();
				episodeLabel.setValue("" + _episode);
				div.insertBefore(episodeLabel, null);
			}
			else if(_season > season)
				break;
		}

		Button buttonInit = new Button();
		buttonInit.setLabel("init");
		buttonInit.setId("buttonInit" + season);
		div.insertBefore(buttonInit, null);

		for(int key : state.keySet())
		{
			int _season = key >> 8;
			int _episode = key & 0xFF;

			if(_season == season)
			{
				Radio radio = new Radio();
				radio.setId("init" + _season + "_" + _episode);
				radio.setRadiogroup("radiogroupSeason" + _season + "_" + _episode);
				div.insertBefore(radio, null);
			}
			else if(_season > season)
				break;
		}

		Button buttonProg = new Button();
		buttonProg.setLabel("prog");
		buttonProg.setId("buttonProg" + season);
		div.insertBefore(buttonProg, null);

		for(int key : state.keySet())
		{
			int _season = key >> 8;
			int _episode = key & 0xFF;

			if(_season == season)
			{
				Radio radio = new Radio();
				radio.setId("prog" + _season + "_" + _episode);
				radio.setRadiogroup("radiogroupSeason" + _season + "_" + _episode);
				div.insertBefore(radio, null);
			}
			else if(_season > season)
				break;
		}

		Button buttonDone = new Button();
		buttonDone.setLabel("done");
		buttonDone.setId("buttonDone" + season);
		div.insertBefore(buttonDone, null);

		for(int key : state.keySet())
		{
			int _season = key >> 8;
			int _episode = key & 0xFF;

			if(_season == season)
			{
				Radio radio = new Radio();
				radio.setId("done" + _season + "_" + _episode);
				radio.setRadiogroup("radiogroupSeason" + _season + "_" + _episode);
				div.insertBefore(radio, null);
			}
			else if(_season > season)
				break;
		}

		for(int i = 0;i < maxEpisode;i++)
		{
			styleString = styleString + "2fr ";
		}
		styleString = styleString + "; grid-gap: 10px; justify-items: center;";
		div.setStyle(styleString);

//		detailsSerie.insertBefore(groupbox, buttons);
	}
}

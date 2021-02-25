package at.windesign.application.movie;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TreeMap;

public class movieDetailsForwardComposer extends GenericForwardComposer<Component>
{
	private static final long            serialVersionUID = 1L;
	private final        movieDataSource ds               = movieDataSource.INSTANCE;
	private              int             opacity          = 80;

	@Wire
	private Window detailsMovie;

	@Wire
	protected Groupbox overview;

	@Wire
	protected Label overviewLabel;

	@Wire
	protected Groupbox settings;

	@Wire
	protected Textbox localPath;

	@Wire
	protected Combobox resolution;

	@Wire
	protected Radiogroup progress;

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

	private movieData m_movie;
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

		detailsMovie.setWidth(wD - 10 + "px");
		detailsMovie.setHeight(hD - 10 + "px");

		if(arg.containsKey("movie"))
			m_movie = (movieData) arg.get("movie");
		else
			detailsMovie.detach();

		if(arg.containsKey("item"))
			m_item = (Listitem) arg.get("item");
		else
			detailsMovie.detach();

		detailsMovie.setAttribute("movie", m_movie);
		detailsMovie.setAttribute("item", m_item);

		if(m_movie.getBackdropPath() != null)
		{
			String uri = "http://image.tmdb.org/t/p/original" + m_movie.getBackdropPath();
			URL    url = new URL(uri);
			AImage img = new AImage(url);

			int w  = Integer.parseInt(detailsMovie.getWidth().replace("px", ""));
			int h  = Integer.parseInt(detailsMovie.getHeight().replace("px", ""));
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
			detailsMovie.setContentStyle(style);
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

		detailsMovie.setTitle(m_movie.getMovieTitle() + " (" + m_movie.getReleaseDate() + ")");
		overviewLabel.setValue(m_movie.getOverview());
		localPath.setValue(m_movie.getLocalPath());
		resolution.setValue(m_movie.getResolution());
		progress.setSelectedIndex(m_movie.getState()-1);
	}
}

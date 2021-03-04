package at.windesign.application.movie;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class movieDetailsForwardComposer extends GenericForwardComposer<Component>
{
	private static final long            serialVersionUID = 1L;
	private final        movieDataSource ds               = movieDataSource.INSTANCE;
	private              int             opacity          = 80;

	@Wire
	private Window detailsMovie;

	@Wire
	protected Groupbox castBox;

	@Wire
	protected Groupbox crewBox;

	@Wire
	protected Groupbox genreBox;

	@Wire
	protected Groupbox productionCompaniesBox;

	@Wire
	protected Groupbox productionCountriesBox;

	@Wire
	protected Groupbox overview;

	@Wire
	protected Label overviewLabel;

	@Wire
	protected Listbox castList;

	@Wire
	protected Listbox crewList;

	@Wire
	protected Listbox genreList;

	@Wire
	protected Listbox productionCompaniesList;

	@Wire
	protected Listbox productionCountriesList;

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
		castBox.setStyle("opacity: " + opacity + "%");
		crewBox.setStyle("opacity: " + opacity + "%");
		genreBox.setStyle("opacity: " + opacity + "%");
		productionCompaniesBox.setStyle("opacity: " + opacity + "%");
		productionCountriesBox.setStyle("opacity: " + opacity + "%");
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

		String title = m_movie.getMovieTitle() + " (" + m_movie.getReleaseDate() + ")";

		if(!m_movie.getOriginalTitle().isEmpty())
		{
			title = title + " [" + m_movie.getOriginalTitle() + "]";
		}

		detailsMovie.setTitle(title);
		overviewLabel.setValue(m_movie.getOverview());

		for(String cast : m_movie.getCast().split("\\|"))
		{
			if(cast.isEmpty())
				continue;

			String   name     = cast.substring(0, cast.indexOf(","));
			String   role     = cast.substring(cast.indexOf(",") + 1);
			Listitem item     = new Listitem();
			Listcell nameCell = new Listcell();
			Listcell roleCell = new Listcell();

			nameCell.setLabel(name);
			roleCell.setLabel(role);

			item.appendChild(nameCell);
			item.appendChild(roleCell);

			castList.appendChild(item);
		}

		for(String crew : m_movie.getCrew().split("\\|"))
		{
			if(crew.isEmpty())
				continue;

			String   name     = crew.substring(0, crew.indexOf(","));
			String   job      = crew.substring(crew.indexOf(",") + 1);
			Listitem item     = new Listitem();
			Listcell nameCell = new Listcell();
			Listcell jobCell  = new Listcell();

			nameCell.setLabel(name);
			jobCell.setLabel(job);

			item.appendChild(nameCell);
			item.appendChild(jobCell);

			crewList.appendChild(item);
		}

		for(String genre : m_movie.getGenre().split(","))
		{
			if(genre.isEmpty())
				continue;

			Listitem item     = new Listitem();
			Listcell genreCell = new Listcell();

			genreCell.setLabel(genre);

			item.appendChild(genreCell);

			genreList.appendChild(item);
		}

		for(String productionCompany : m_movie.getProductionCompanies().split(","))
		{
			if(productionCompany.isEmpty())
				continue;

			Listitem item     = new Listitem();
			Listcell productionCompanyCell = new Listcell();

			productionCompanyCell.setLabel(productionCompany);

			item.appendChild(productionCompanyCell);

			productionCompaniesList.appendChild(item);
		}

		for(String productionCountry : m_movie.getProductionCountries().split(","))
		{
			if(productionCountry.isEmpty())
				continue;

			Listitem item     = new Listitem();
			Listcell productionCountryCell = new Listcell();

			productionCountryCell.setLabel(productionCountry);

			item.appendChild(productionCountryCell);

			productionCountriesList.appendChild(item);
		}

		localPath.setValue(m_movie.getLocalPath());
		resolution.setValue(m_movie.getResolution());
		progress.setSelectedIndex(m_movie.getState() - 1);
	}
}

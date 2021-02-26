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
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class movieSearchForwardComposer extends GenericForwardComposer<Component>
{
	private static final long            serialVersionUID = 1L;
	private final        movieDataSource ds               = movieDataSource.INSTANCE;
	private              int             opacity          = 80;

	@Wire
	private Window searchMovie;

	@Wire
	protected Textbox searchText;

	@Wire
	protected Textbox searchYear;

	@Wire
	protected Listbox resultList;

	@Wire
	protected Button searchButton;

	@Wire
	protected Button cancelButton;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		int hD = (int) self.getDesktop().getAttribute("desktopHeight");
		int wD = (int) self.getDesktop().getAttribute("desktopWidth");

		searchMovie.setWidth(wD - 10 + "px");
		searchMovie.setHeight(hD - 10 + "px");

		SortedMap<Integer, Listheader> listHeader = new TreeMap<>();

		ListModelList resultListModel = new ListModelList();
		resultList.setModel(resultListModel);
		resultList.setAttribute("listModelList", resultListModel);

		resultList.getListhead().getChildren().clear();
		Listhead   head       = resultList.getListhead();
		Listheader selected   = new Listheader("");
		Listheader movieName  = new Listheader("Movie Name");
		Listheader firstAired = new Listheader("First Aired");
		Listheader overview   = new Listheader("Overview");

		selected.setHflex("min");
		movieName.setHflex("min");
		firstAired.setHflex("min");
		overview.setHflex("min");

		head.appendChild(selected);
		head.appendChild(movieName);
		head.appendChild(firstAired);
		head.appendChild(overview);

		resultRenderer renderer = new resultRenderer();
		resultList.setItemRenderer(renderer);
	}
}

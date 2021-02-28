package at.windesign.application.serie;

import at.windesign.application.movie.movieResultRenderer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.util.SortedMap;
import java.util.TreeMap;

public class serieSearchForwardComposer extends GenericForwardComposer<Component>
{
	private static final long            serialVersionUID = 1L;
	private final        serieDataSource ds               = serieDataSource.INSTANCE;
	private              int             opacity          = 80;

	@Wire
	private Window searchSerie;

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

		searchSerie.setWidth(wD - 10 + "px");
		searchSerie.setHeight(hD - 10 + "px");

		SortedMap<Integer, Listheader> listHeader = new TreeMap<>();

		ListModelList resultListModel = new ListModelList();
		resultList.setModel(resultListModel);
		resultList.setAttribute("listModelList", resultListModel);

		resultList.getListhead().getChildren().clear();
		Listhead   head       = resultList.getListhead();
		Listheader selected   = new Listheader("");
		Listheader serieName  = new Listheader("TV Show Name");
		Listheader firstAired = new Listheader("First Aired");
		Listheader overview   = new Listheader("Overview");

		selected.setHflex("min");
		serieName.setHflex("min");
		firstAired.setHflex("min");
		overview.setHflex("min");

		head.appendChild(selected);
		head.appendChild(serieName);
		head.appendChild(firstAired);
		head.appendChild(overview);

		serieResultRenderer renderer = new serieResultRenderer();
		resultList.setItemRenderer(renderer);
	}
}

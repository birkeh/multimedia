package at.windesign.application.movie;

import at.windesign.application.serie.serieDataSource;
import at.windesign.application.serie.serieUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

public class movieListForwardComposer extends GenericForwardComposer
{
	protected Listbox movieList; // autowired

	@Wire
	private Tab tabMovies;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		movieDataSource ds = movieDataSource.INSTANCE;
		movieUtils.loadMovies(ds, movieList);

		ds = movieDataSource.INSTANCE;
		movieUtils.movieMetrics(tabMovies, ds);
	}
}

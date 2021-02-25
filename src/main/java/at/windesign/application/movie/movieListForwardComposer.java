package at.windesign.application.movie;

import at.windesign.application.serie.serieDataSource;
import at.windesign.application.serie.serieUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;

public class movieListForwardComposer extends GenericForwardComposer
{
	protected Listbox movieList; // autowired

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		movieDataSource ds = movieDataSource.INSTANCE;
		movieUtils.loadMovies(ds, movieList);
	}
}

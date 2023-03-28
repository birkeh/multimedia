package at.windesign.application.movie;

import at.windesign.application.serie.serieDataSource;
import at.windesign.application.serie.serieUtils;
import org.ini4j.Ini;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.io.File;

public class movieListForwardComposer extends GenericForwardComposer
{
	protected Listbox movieList; // autowired

	@Wire
	protected Checkbox useFilterMovie;

	@Wire
	protected Checkbox filterInitializedMovie;

	@Wire
	protected Checkbox filterInProgressMovie;

	@Wire
	protected Checkbox filterDoneMovie;

	@Wire
	protected Checkbox applyFilterMovie;

	@Wire
	private Tab tabMovies;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		boolean bUseFilter        = false;
		boolean bFilterInitialize = false;
		boolean bFilterInProgress = false;
		boolean bFilterDone       = false;

		File iniFile = new File(System.getProperty("java.io.tmpdir") + "/multimedia.ini");
		if(iniFile.exists())
		{
			Ini ini = new Ini(iniFile);

			bUseFilter        = Boolean.parseBoolean(ini.get("filter", "useFilterMovie"));
			bFilterInitialize = Boolean.parseBoolean(ini.get("filter", "filterInitializedMovie"));
			bFilterInProgress = Boolean.parseBoolean(ini.get("filter", "filterInProgressMovie"));
			bFilterDone       = Boolean.parseBoolean(ini.get("filter", "filterDoneMovie"));

			useFilterMovie.setChecked(bUseFilter);
			filterInitializedMovie.setChecked(bFilterInitialize);
			filterInProgressMovie.setChecked(bFilterInProgress);
			filterDoneMovie.setChecked(bFilterDone);

			filterInitializedMovie.setDisabled(!bUseFilter);
			filterInProgressMovie.setDisabled(!bUseFilter);
			filterDoneMovie.setDisabled(!bUseFilter);
		}

		movieDataSource ds = movieDataSource.INSTANCE;
		movieUtils.loadMovies(ds, movieList, bUseFilter, bFilterInitialize, bFilterInProgress, bFilterDone);

		ds = movieDataSource.INSTANCE;
		movieUtils.movieMetrics(tabMovies, ds);
	}
}

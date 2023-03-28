package at.windesign.application.serie;


import at.windesign.application.movie.movieDataSource;
import at.windesign.application.movie.movieUtils;
import org.ini4j.Ini;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class serieListForwardComposer extends GenericForwardComposer
{
	protected Listbox seriesList; // autowired

	@Wire
	protected Checkbox useFilter;

	@Wire
	protected Checkbox filterInitialized;

	@Wire
	protected Checkbox filterInProgress;

	@Wire
	protected Checkbox filterDone;

	@Wire
	protected Checkbox applyFilter;

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

			bUseFilter        = Boolean.parseBoolean(ini.get("filter", "useFilter"));
			bFilterInitialize = Boolean.parseBoolean(ini.get("filter", "filterInitialized"));
			bFilterInProgress = Boolean.parseBoolean(ini.get("filter", "filterInProgress"));
			bFilterDone       = Boolean.parseBoolean(ini.get("filter", "filterDone"));

			useFilter.setChecked(bUseFilter);
			filterInitialized.setChecked(bFilterInitialize);
			filterInProgress.setChecked(bFilterInProgress);
			filterDone.setChecked(bFilterDone);

			filterInitialized.setDisabled(!bUseFilter);
			filterInProgress.setDisabled(!bUseFilter);
			filterDone.setDisabled(!bUseFilter);
		}

		serieDataSource ds = serieDataSource.INSTANCE;
		serieUtils.loadSeries(ds, seriesList, bUseFilter, bFilterInitialize, bFilterInProgress, bFilterDone);

		Window mainWindow = (Window) Path.getComponent("/mainWindow");
		Tab    tabSeries  = (Tab) mainWindow.getFellow("tabSeries");
		ds = serieDataSource.INSTANCE;
		serieUtils.serieMetrics(tabSeries, ds);
	}
}

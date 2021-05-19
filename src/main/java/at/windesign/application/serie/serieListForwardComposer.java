package at.windesign.application.serie;


import at.windesign.application.movie.movieDataSource;
import at.windesign.application.movie.movieUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class serieListForwardComposer extends GenericForwardComposer
{
	protected Listbox       seriesList; // autowired

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		serieDataSource ds = serieDataSource.INSTANCE;
		serieUtils.loadSeries(ds, seriesList);

		Window          mainWindow = (Window) Path.getComponent("/mainWindow");
		Tab             tabSeries  = (Tab)mainWindow.getFellow("tabSeries");
		ds         = serieDataSource.INSTANCE;
		serieUtils.serieMetrics(tabSeries, ds);
	}
}

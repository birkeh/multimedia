package at.windesign.application.serie;


import org.zkoss.zk.ui.Component;
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
	}
}

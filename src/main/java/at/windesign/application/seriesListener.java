package at.windesign.application;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import static org.zkoss.zk.ui.util.Clients.alert;

public class seriesListener implements EventListener
{
	Listbox m_seriesList;

	public seriesListener()
	{
	}

	public void setSeriesList(Listbox seriesList)
	{
		m_seriesList = seriesList;
	}

	public boolean isAsap()
	{
// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onEvent(Event arg)
	{
		Listitem item = m_seriesList.getSelectedItem();
		Serie    s    = (Serie) item.getValue();
		Messagebox.show(s.getSeriesName());
	}
}

package at.windesign.application.main;

import jdk.internal.event.Event;
import org.zkoss.lang.Library;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.ini4j.Ini;

import java.io.*;
import java.net.URL;

public class indexForwardComposer extends GenericForwardComposer<Component>
{
	private static final long serialVersionUID = 1L;

	@Wire
	private Window mainWindow;

	@Wire
	protected Label width;

	@Wire
	protected Label height;

	@Wire
	protected Tabbox tabbox;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		int     tabIndex          = 0;

		File iniFile = new File(System.getProperty("java.io.tmpdir") + "/multimedia.ini");
		if(iniFile.exists())
		{
			Ini ini = new Ini(iniFile);
			tabIndex = Integer.parseInt(ini.get("main", "currentTab"));
		}

		tabbox.setSelectedIndex(tabIndex);
	}

	public void onSelect$tabbox(ForwardEvent fe)
	{
		int index = tabbox.getSelectedIndex();
		Ini ini   = null;
		try
		{
			File iniFile = new File(System.getProperty("java.io.tmpdir") + "/multimedia.ini");
			if(!iniFile.exists())
				iniFile.createNewFile();
			ini = new Ini(iniFile);
			ini.put("main", "currentTab", index);
			ini.store();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

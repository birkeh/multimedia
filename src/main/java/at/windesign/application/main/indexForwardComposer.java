package at.windesign.application.main;

import org.zkoss.lang.Library;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import java.net.URL;

public class indexForwardComposer extends GenericForwardComposer<Component>
{
	private static final long    serialVersionUID = 1L;

	@Wire
	private Window mainWindow;

	@Wire
	protected Label width;

	@Wire
	protected Label height;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);
	}
}

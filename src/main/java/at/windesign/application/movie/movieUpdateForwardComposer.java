package at.windesign.application.movie;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Window;

public class movieUpdateForwardComposer extends GenericForwardComposer<Component>
{
	private static final long    serialVersionUID = 1L;
	private              int     opacity          = 80;
	private              Listbox m_movieList;

	@Wire
	private Window updateMovie;

	@Wire
	protected Label movieLabel;

	@Wire
	protected Progressmeter movieProgress;

	@Wire
	protected Label movieProgressLabel;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);
	}
}

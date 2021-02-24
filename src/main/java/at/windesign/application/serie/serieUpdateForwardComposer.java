package at.windesign.application.serie;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;

public class serieUpdateForwardComposer extends GenericForwardComposer<Component>
{
	private static final long            serialVersionUID = 1L;
	private              int             opacity          = 80;
	private              Listbox         m_seriesList;
	private              boolean         m_all;

	@Wire
	private Window updateSerie;

	@Wire
	protected Label serieLabel;

	@Wire
	protected Progressmeter serieProgress;

	@Wire
	protected Label serieProgressLabel;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);
	}
}

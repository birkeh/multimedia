package at.windesign.application.serie;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class serieDetailsSelectorComposer extends SelectorComposer<Component>
{
	private static final long serialVersionUID = 1L;

	@Wire
	private Window detailsSerie;

	@Wire
	protected Label title;

	private serie m_serie;

	@Listen("onClick = #saveButton")
	public void save()
	{
		detailsSerie.onClose();
	}

	@Listen("onClick = #cancelButton")
	public void cancel()
	{
		detailsSerie.onClose();
	}

	@Listen("onClick = #deleteButton")
	public void delete()
	{
		detailsSerie.onClose();
	}
}
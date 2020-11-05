package at.windesign.application.serie;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import java.util.HashMap;
import java.util.Map;

public class serieListSelectorComposer extends SelectorComposer<Component>
{
	@Wire
	private Listbox seriesList;

	@Listen("onDoubleClick = #seriesList")
	public void onClick()
	{
		Listitem item = seriesList.getSelectedItem();

		if(item == null)
			return;

		serieData s = (serieData) item.getValue();

		Map<String, Object> arguments = new HashMap<String, Object>();

		arguments.put("serie", s);

		String template = "/serie/detailsSerie.zul";
		Window window   = (Window) Executions.createComponents(template, null, arguments);

		window.doModal();
	}
}

package at.windesign.application.serie;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import java.net.URL;

public class serieDetailsForwardComposer extends GenericForwardComposer<Component>
{
	private static final long    serialVersionUID = 1L;

	@Wire
	private Window resultWin;

	@Wire
	protected Label title;

	private serie m_serie;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		int hD = (int)self.getDesktop().getAttribute("desktopHeight");
		int wD = (int)self.getDesktop().getAttribute("desktopWidth");

		resultWin.setWidth(wD - 10 + "px");
		resultWin.setHeight(hD - 10 + "px");

		if(arg.containsKey("serie"))
			m_serie = (serie) arg.get("serie");
		else
			resultWin.detach();

		String uri = "http://image.tmdb.org/t/p/original" + m_serie.getSeriesBackdrop();
		URL    url = new URL(uri);
		AImage img = new AImage(url);

		int w = Integer.parseInt(resultWin.getWidth().replace("px", ""));
		int h = Integer.parseInt(resultWin.getHeight().replace("px", ""));
		int wI = img.getWidth();
		int hI = img.getHeight();
		int wR = w;
		int hR = hI*w/wI;

		if(hR < h)
		{
			hR = h;
			wR = wI*h/hI;
		}

		String size = String.valueOf(wR) + "px " + String.valueOf(hR) + "px";
		String format = "background:url('%1$s') no-repeat center center fixed; -webkit-background-size: " + size + "; -moz-background-size: \" + size + \"; -o-background-size: \" + size + \"; background-size: \" + size + \";";
		String style = String.format(format, uri);
		resultWin.setContentStyle(style);
//		resultWin.setTitle(m_serie.getSeriesName());
//		resultWin.setTitle(resultWin.getWidth() + " x " + resultWin.getHeight());
//		resultWin.setTitle(m_serie.getParentWidth() + " x " + m_serie.getParentHeight());
//		resultWin.setTitle(wD + " x " + hD);
	}
}

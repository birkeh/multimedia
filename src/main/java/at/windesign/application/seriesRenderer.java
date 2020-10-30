package at.windesign.application;

import org.zkoss.image.Images;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.SortedMap;
import java.util.TreeMap;

public class seriesRenderer implements ListitemRenderer
{
	private Integer stateWidth  = 4;
	private Integer stateHeight = 20;

	private Listbox seriesList;

	@Override
	public void render(Listitem item, Object o, int i) throws Exception
	{
		final Serie data = (Serie) o;

		Listcell seriesNameCell       = new Listcell(data.getSeriesName());
		Listcell seriesFirstAiredCell = new Listcell(String.valueOf(data.getSeriesFirstAired()));
		Listcell seriesResolution     = new Listcell(data.getSeriesResolution());

		seriesNameCell.setParent(item);
		seriesFirstAiredCell.setParent(item);
		seriesResolution.setParent(item);

		for(int s = 1; s <= data.getMaxSeason(); s++)
		{
			Listcell imageCell = new Listcell();
			imageCell.setImageContent(Images.encode("bla.png", getStateImage(s, data.getEpisodeState())));
			imageCell.setParent(item);
		}

		item.setValue(data);
	}

	private BufferedImage getStateImage(Integer curSeason, SortedMap<Integer, Integer> state)
	{
		int                         season      = -1;
		int                         episode     = -1;
		SortedMap<Integer, Integer> seasonState = new TreeMap<>();

		for(int key : state.keySet())
		{
			season = key >> 8;
			episode = key & 0xFF;

			if(season == curSeason)
				seasonState.put(episode, state.get(key));
			else if(season > curSeason)
				break;
		}

		if(seasonState.size() == 0)
		{
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			Graphics2D    g2d   = image.createGraphics();

			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, 1, 1);

			return image;
		}

		BufferedImage image    = new BufferedImage(stateWidth * seasonState.lastKey(), stateHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D    g2d      = image.createGraphics();
		Color         colGrey  = new Color(192, 192, 192);
		Color         colBlue  = new Color(0, 0, 192);
		Color         colGreen = new Color(0, 192, 0);
		Color         colBlack = new Color(0, 0, 0);
		Color         col      = colBlack;

		g2d.setColor(colBlack);
		g2d.fillRect(0, 0, stateWidth * seasonState.lastKey(), stateHeight);

		for(Integer key : seasonState.keySet())
		{
			switch(seasonState.get(key))
			{
				case 0:
					col = colBlack;
					break;
				case 1:
					col = colGrey;
					break;
				case 2:
					col = colBlue;
					break;
				case 3:
					col = colGreen;
					break;
			}

			g2d.setColor(col);
			g2d.fillRect((key - 1) * stateWidth, 0, stateWidth - 1, stateHeight);
		}

		return image;
	}
}
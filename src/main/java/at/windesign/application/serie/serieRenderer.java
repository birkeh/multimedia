package at.windesign.application.serie;

import org.zkoss.image.Images;
import org.zkoss.zul.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.SortedMap;
import java.util.TreeMap;

public class serieRenderer implements ListitemRenderer
{
	private Integer stateWidth  = 4;
	private Integer stateHeight = 20;

	private Listbox seriesList;

	@Override
	public void render(Listitem item, Object o, int i) throws Exception
	{
		final serieData data = (serieData) o;

		Listcell seriesNameCell       = new Listcell(data.getSeriesName());
		Listcell seriesFirstAiredCell = new Listcell(String.valueOf(data.getSeriesFirstAired()));
		Listcell seriesResolution     = new Listcell(data.getSeriesResolution());

		seriesNameCell.setStyle(data.getSeriesStyle());
		seriesNameCell.setParent(item);
		seriesFirstAiredCell.setParent(item);
		seriesResolution.setParent(item);

		for(int s = 1; s <= data.getMaxSeason(); s++)
		{
			Listcell imageCell = new Listcell();
			imageCell.setImageContent(Images.encode("bla.png", getStateImage(data.getSeasons().get(s))));
			imageCell.setParent(item);
			imageCell.setTooltiptext(getStateText(data.getSeasons().get(s)));
		}

		item.setValue(data);
	}

	private BufferedImage getStateImage(seasonData s)
	{
		SortedMap<Integer, episodeData> episodes = s.getEpisodes();

		if(episodes.size() == 0)
		{
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			Graphics2D    g2d   = image.createGraphics();

			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, 1, 1);

			return image;
		}

		BufferedImage image    = new BufferedImage(stateWidth * episodes.lastKey(), stateHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D    g2d      = image.createGraphics();
		Color         colGrey  = new Color(192, 192, 192);
		Color         colBlue  = new Color(0, 0, 192);
		Color         colGreen = new Color(0, 192, 0);
		Color         colBlack = new Color(0, 0, 0);
		Color         colWhite = new Color(255, 255, 255);
		Color         col      = colBlack;

		g2d.setColor(colWhite);
		g2d.fillRect(0, 0, stateWidth * episodes.lastKey(), stateHeight);

		for(Integer key : episodes.keySet())
		{
			switch(episodes.get(key).getEpisodeState())
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

			g2d.setColor(colBlack);
			g2d.fillRect((key - 1) * stateWidth, 0, stateWidth, stateHeight);
			g2d.setColor(col);
			g2d.fillRect((key - 1) * stateWidth, 0, stateWidth - 1, stateHeight);
		}

		return image;
	}

	private String getStateText(seasonData s)
	{
		SortedMap<Integer, episodeData> episodes = s.getEpisodes();

		if(episodes.size() == 0)
			return "";

		String stateInit = "";
		String stateProg = "";
		String stateDone = "";

		for(Integer key : episodes.keySet())
		{
			switch(episodes.get(key).getEpisodeState())
			{
				case 0:
					break;
				case 1:
					if(stateInit != "")
						stateInit = stateInit + ", ";
					stateInit = stateInit + key;
					break;
				case 2:
					if(stateProg != "")
						stateProg = stateProg + ", ";
					stateProg = stateProg + key;
					break;
				case 3:
					if(stateDone != "")
						stateDone = stateDone + ", ";
					stateDone = stateDone + key;
					break;
			}
		}

		if(stateInit == "")
			stateInit = "none";

		if(stateProg == "")
			stateProg = "none";

		if(stateDone == "")
			stateDone = "none";

		return "Init: " + stateInit + "\nProgress: " + stateProg + "\nDone: " + stateDone;
	}
}
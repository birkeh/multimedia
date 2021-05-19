package at.windesign.application.serie;

import com.omertron.themoviedbapi.MovieDbException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

//a33271b9e54cdcb9a80680eaf5522f1b

public class serieUpdateSelectorComposer extends SelectorComposer<Component>
{
	private static final long    serialVersionUID = 1L;
	private              int     opacity          = 80;
	private              Listbox m_seriesList;
	private              boolean m_all;

	@Wire
	private Window updateSerie;

	@Wire
	protected Label serieLabel;

	@Wire
	protected Progressmeter serieProgress;

	@Wire
	protected Label serieProgressLabel;

	@Wire
	private Timer timer;

	private static final String QUEUE       = "queue";
	private static final String START       = "start";
	private static final int    ONE_HUNDRED = 100;

	private boolean isFinish;

	private int    progress;
	private String serieName;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		final Execution execution = Executions.getCurrent();

		if(execution.getArg().containsKey("seriesList"))
			m_seriesList = (Listbox) execution.getArg().get("seriesList");
		else
			updateSerie.detach();

		if(execution.getArg().containsKey("all"))
			m_all = (boolean) execution.getArg().get("all");
		else
			updateSerie.detach();

		try
		{
			Events.echoEvent("onAddNameEvent", timer, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Listen("onAddNameEvent = #timer")
	public void processingSeries()
	{
		try
		{
			timer.start();

			if(itsNotProcessing())
			{
				EventQueue queue = lookupQueue();
				setUp(queue);
				start(queue);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private boolean itsNotProcessing()
	{
		return !EventQueues.exists(QUEUE);
	}

	private EventQueue lookupQueue()
	{
		return EventQueues.lookup(QUEUE);
	}

	private void setUp(final EventQueue queue)
	{
		progress = 0;

		queue.subscribe((Event event) ->
						{
							doLongOperation();
						}
				,
						(Event event) ->
						{
							removeQueue();
						});
	}

	private void removeQueue()
	{
		EventQueues.remove(QUEUE);
	}

	private void start(EventQueue queue)
	{
		queue.publish(new Event(START));
		timer.start();
	}

	private void doLongOperation() throws IOException
	{
		doProcess();
	}

	public void doProcess() throws IOException, IllegalStateException
	{
		int total   = 0;
		int current = 0;

		ListModelList   list     = (ListModelList) m_seriesList.getModel();
		List<serieData> dataList = list.getInnerList();

		for(serieData data : dataList)
		{
			if(m_all == true)
				total++;
			else
			{
				if(data.isActive())
					total++;
			}
		}

		try
		{
			for(serieData data : dataList)
			{
				if(m_all == true || data.isActive() == true)
				{
					progress = current * 100 / total;
					serieName = data.getSeriesName();

					updateSerie(data);

					current++;
				}
			}
			isFinish = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
		}
	}

	@Listen("onTimer = #timer")
	public void fetchingSimulatorTimer() throws FileNotFoundException, IllegalStateException
	{
		try
		{
			updateProgressmeter();
			updateLabel();
		}
		catch(Exception e)
		{
		}

	}

	private void updateProgressmeter()
	{
		try
		{
			serieProgress.setValue(progress);
			serieProgressLabel.setValue(progress + "%");
			serieLabel.setValue(serieName);
		}
		catch(Exception e)
		{
		}
	}

	private void updateLabel() throws FileNotFoundException, IllegalStateException
	{
		try
		{
			if(isFinish)
			{
				serieProgressLabel.setValue("Finish!");
				updateSerie.detach();

				OutputStream tempFile    = new FileOutputStream(System.getProperty("java.io.tmpdir") + "/redir");
				PrintStream  printStream = new PrintStream(tempFile);
				printStream.print("serie");
				printStream.close();

				Executions.sendRedirect("");
			}
		}
		catch(Exception e)
		{
		}
	}

	private boolean isTheProgressmeterFull()
	{
		return serieProgress.getValue() == ONE_HUNDRED;
	}

	private void stop()
	{
		timer.stop();
	}

	@Listen("onClick = #btnCancel")
	public void doCancel()
	{
		try
		{
//			isBreak = true;
			removeQueue();
			timer.stop();
		}
		catch(Exception e)
		{
		}
	}

	private void updateSerie(serieData serie)
	{
		serieData serieNew;

		try
		{
			serieNew = new serieData();
			serieNew.fromTMDB(serie.getSeriesID());
			serieNew.copyFrom(serie);

			serieNew.save();
		}
		catch(MovieDbException e)
		{
			e.printStackTrace();
		}
	}
}
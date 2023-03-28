package at.windesign.application.movie;

import com.omertron.themoviedbapi.MovieDbException;
import org.ini4j.Ini;
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

public class movieUpdateSelectorComposer extends SelectorComposer<Component>
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

	@Wire
	private Timer timer;

	private static final String QUEUE       = "queue";
	private static final String START       = "start";
	private static final int    ONE_HUNDRED = 100;

	private boolean isFinish;

	private int    progress;
	private String movieName;

	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);

		final Execution execution = Executions.getCurrent();

		if(execution.getArg().containsKey("movieList"))
			m_movieList = (Listbox) execution.getArg().get("movieList");
		else
			updateMovie.detach();

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
	public void processingMovies()
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

		ListModelList   list     = (ListModelList) m_movieList.getModel();
		List<movieData> dataList = list.getInnerList();
		total = dataList.size();

		try
		{
			for(movieData data : dataList)
			{
				progress = current * 100 / total;
				movieName = data.getMovieTitle();

				updateMovie(data);

				current++;
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
			movieProgress.setValue(progress);
			movieProgressLabel.setValue(progress + "%");
			movieLabel.setValue(movieName);
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
				movieProgressLabel.setValue("Finish!");
				updateMovie.detach();
				Executions.sendRedirect("");
			}
		}
		catch(Exception e)
		{
		}
	}

	private boolean isTheProgressmeterFull()
	{
		return movieProgress.getValue() == ONE_HUNDRED;
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

	private void updateMovie(movieData movie)
	{
		movieData movieNew;

		try
		{
			movieNew = new movieData();
			movieNew.fromTMDB(movie.getMovieID());
			movieNew.copyFrom(movie);

			movieNew.save();
		}
		catch(MovieDbException e)
		{
			e.printStackTrace();
		}
	}
}
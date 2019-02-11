package interview.com.hiya;

import android.os.AsyncTask;

import java.util.List;

/**
 * Class that obtains Cursor for data and returns list of objects upon completion
 */
public class GetCursorTask<T> extends AsyncTask<Void, Void, List<T>> {

    private TaskListener<T> mTaskListener;
    private DataFetcher<T> mFetcher;

    public GetCursorTask(DataFetcher<T> fetcher) {
        mFetcher = fetcher;
    }

    public void setTaskListener(TaskListener<T> taskListener) {
        mTaskListener = taskListener;
    }

    @Override
    protected List<T> doInBackground(Void... params) {
        List<T> data = mFetcher.getData();
        return data;
    }

    @Override
    protected void onPostExecute(List<T> entities) {
        if (mTaskListener != null) {
            mTaskListener.onComplete(entities);
        }
    }

    public interface DataFetcher<T> {
        List<T> getData();
    }

    public interface TaskListener<T> {
        void onComplete(List<T> entities);
    }
}
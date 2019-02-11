package interview.com.hiya;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    FloatingActionButton mFab;
    GetCursorTask<Call> mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTask();
            }
        });

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionUtils.requestPermissionsIfNeeded(ScrollingActivity.this);
            }
        });
        mFab.setVisibility(View.GONE);

        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CallListAdapter());
    }

    private void refreshTask() {
        if (mTask == null || mTask.getStatus() == AsyncTask.Status.FINISHED) {
            mSwipeRefreshLayout.setRefreshing(true);
            mTask = new GetCursorTask<Call>(new CallsFetcher(this));
            mTask.setTaskListener(new GetCursorTask.TaskListener<Call>() {
                @Override
                public void onComplete(List<Call> entities) {
                    CallListAdapter adapter = (CallListAdapter) mRecyclerView.getAdapter();
                    adapter.setData(entities);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
            mTask.execute();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        PermissionUtils.requestPermissionsIfNeeded(this);
        refreshTask();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(PermissionUtils.handlePermissionsResult(this, permissions, grantResults)) {
            refreshTask();
        } else {
            mFab.setVisibility(View.VISIBLE);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

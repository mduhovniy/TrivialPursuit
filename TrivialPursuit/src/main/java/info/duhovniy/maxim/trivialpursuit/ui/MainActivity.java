package info.duhovniy.maxim.trivialpursuit.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.duhovniy.maxim.trivialpursuit.R;
import info.duhovniy.maxim.trivialpursuit.data.Answer;
import info.duhovniy.maxim.trivialpursuit.data.Question;
import info.duhovniy.maxim.trivialpursuit.network.NetworkConstants;
import info.duhovniy.maxim.trivialpursuit.network.SimpleQueryService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView questionText;
    private RecyclerView recyclerView;

    private AnswersAdapter answersAdapter;
    private ArrayList<Answer> answersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        questionText = (TextView) findViewById(R.id.question_text);
        questionText.setMovementMethod(new ScrollingMovementMethod());

        recyclerView = (RecyclerView) findViewById(R.id.question_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        answersAdapter = new AnswersAdapter(recyclerView, answersList);
        recyclerView.setAdapter(answersAdapter);

        IntentFilter filter = new IntentFilter();

        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(NetworkConstants.SIMPLE_QUERY_SERVICE);
        filter.addAction(NetworkConstants.LIST_QUERY_SERVISE);
        filter.addAction(NetworkConstants.SUBJECT_QUERY_SERVICE);
        filter.addAction(NetworkConstants.ADD_QUERY_SERVICE);


        QueryReceiver queryReceiver = new QueryReceiver();
        registerReceiver(queryReceiver, filter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_play);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SimpleQueryService.class);
                startService(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class QueryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Question q;
            if (intent.getAction().equals(NetworkConstants.SIMPLE_QUERY_SERVICE)) {
                q = intent.getBundleExtra(NetworkConstants.RESPONSE_MESSAGE)
                        .getParcelable(NetworkConstants.RESPONSE);
                if (q != null) {
                    answersAdapter.updateList(q.getAnswerList());
                    questionText.setText(q.getQuery());
                }
            } else if (intent.getAction().equals(NetworkConstants.ADD_QUERY_SERVICE)) {
                Toast.makeText(getApplicationContext(), intent.getStringExtra(NetworkConstants.RESPONSE_MESSAGE),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}

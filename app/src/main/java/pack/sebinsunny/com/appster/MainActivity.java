package pack.sebinsunny.com.appster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mblog;

    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mblog = (RecyclerView) findViewById(R.id.blo);
        mblog.setHasFixedSize(true);
        mblog.setLayoutManager(new LinearLayoutManager(this));

        mdatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    protected void onStart() {

        super.onStart();
        FirebaseRecyclerAdapter<App, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<App, BlogViewHolder>(
                App.class, R.layout.blow_row, BlogViewHolder.class, mdatabase
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, App model, final int position) {

                final String post_key = getRef(position).toString();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 1) {


                            Toast.makeText(MainActivity.this, "p", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        };

        mblog.setAdapter(firebaseRecyclerAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent i = new Intent(MainActivity.this, PostActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(final View view) {
        int itemPosition = mblog.getChildLayoutPosition(view);
        //  String item = .get(itemPosition);
        Log.d("ddd", String.valueOf(itemPosition));
        Toast.makeText(MainActivity.this, itemPosition, Toast.LENGTH_LONG).show();
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setTitle(String title) {
            TextView posttitle = (TextView) mview.findViewById(R.id.t1);
            posttitle.setText(title);


        }

        public void setDesc(String desc) {

            TextView postdesc = (TextView) mview.findViewById(R.id.t2);
            postdesc.setText(desc);


        }


        public void setImage(Context cts, String image) {
            ImageView postimage = (ImageView) mview.findViewById(R.id.img);
            Picasso.with(cts).load(image).into(postimage);


        }


    }

}

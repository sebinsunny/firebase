package pack.sebinsunny.com.appster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static pack.sebinsunny.com.appster.R.id.submit;

public class PostActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 1;
    private ImageButton select;
    private EditText post, title;
    private Uri image = null;
    private Button b;
    private StorageReference storage;
    private ProgressDialog progress;
    private DatabaseReference data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        select = (ImageButton) findViewById(R.id.imageSelect);
        title = (EditText) findViewById(R.id.title);
        post = (EditText) findViewById(R.id.desc);
        b = (Button) findViewById(submit);
        progress = new ProgressDialog(this);
        data = FirebaseDatabase.getInstance().getReference().child("Blog");

        storage = FirebaseStorage.getInstance().getReference();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, GALLERY_REQUEST);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startposting();
            }
        });


    }

    private void startposting() {
        progress.setMessage("posting......");
        progress.show();
        final String titlev = title.getText().toString().trim();
        final String postv = post.getText().toString().trim();
        if (!TextUtils.isEmpty(titlev) && !TextUtils.isEmpty(postv) && image != null) {
            StorageReference filepath = storage.child("blog_images").child(image.getLastPathSegment());
            filepath.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getDownloadUrl();
                    DatabaseReference npost = data.push();
                    npost.child("title").setValue(titlev);
                    npost.child("desc").setValue(postv);
                    npost.child("image").setValue(downloadurl.toString());


                    progress.dismiss();
                    startActivity(new Intent(PostActivity.this, MainActivity.class));
                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GALLERY_REQUEST == 1 && resultCode == RESULT_OK) {
            image = data.getData();
            select.setImageURI(image);
        }
    }
}

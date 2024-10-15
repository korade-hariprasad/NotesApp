package miniproject.notes;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNoteActivity extends AppCompatActivity {

    private EditText etTitle, etBody;
    private TextView tvTime;
    private boolean isNewNote;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setOnClickListeners();
    }

    private void initViews() {
        etTitle = findViewById(R.id.etTitle);
        etBody = findViewById(R.id.etBody);
        tvTime = findViewById(R.id.tvTime);
        isNewNote = getIntent().getStringExtra("new").equals("yes");
        dbHelper = new DatabaseHelper(this);

        if (!isNewNote) {
            etTitle.setText(getIntent().getStringExtra("title"));
            etBody.setText(getIntent().getStringExtra("body"));
            tvTime.setText(getIntent().getStringExtra("time"));
        }
    }

    private void setOnClickListeners() {

        findViewById(R.id.btnClose).setOnClickListener(v->{
            exitWithoutSaving();
        });

        findViewById(R.id.btnSave).setOnClickListener(v->{
            String title = etTitle.getText().toString().trim();
            String body = etBody.getText().toString().trim();

            String time = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().getTime());

            if(title.isEmpty()||body.isEmpty()){
                Toast.makeText(this, "Title or body cannot be empty", Toast.LENGTH_SHORT).show();
            }else{
                NoteModel note = new NoteModel();
                note.setBody(body);
                note.setTitle(title);
                note.setTime(time);
                if(isNewNote){
                    if(dbHelper.insertNote(note)>0){
                        Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(this, "Cannot create note. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    note.setId(getIntent().getIntExtra("id", -1));
                    if(dbHelper.updateNote(note)>0){
                        Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(this, "Cannot update note. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.btnDelete).setOnClickListener(v->{
            if(isNewNote) finish();
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false).setPositiveButton("Yes", (dialogInterface, i) -> {
                    if(dbHelper.deleteNote(getIntent().getIntExtra("id", -1))>0){
                        Toast.makeText(EditNoteActivity.this, "Note Deleted!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(EditNoteActivity.this, "Note cannot be Deleted!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss()).setTitle("Delete Note?").setMessage("Are you sure you want to delete?");
                builder.create().show();
            }
        });
    }

    private void exitWithoutSaving() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false).setPositiveButton("Yes", (dialogInterface, i) -> finish())
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .setTitle("Discard Changes?").setMessage("Your changes are not saved. Are you sure you want to reset?");
        builder.create().show();
    }
}
package miniproject.notes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvCount;
    private RecyclerView recyclerView;
    private ArrayList<NoteModel> list;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvCount = findViewById(R.id.tvCount);
        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getData();

        findViewById(R.id.fabNewNote).setOnClickListener(v->{
            Intent i = new Intent(this, EditNoteActivity.class);
            i.putExtra("new", "yes");
            startActivity(i);
        });
    }

    private void getData(){
        list = dbHelper.getAllNotes();
        recyclerView.setAdapter(new NotesAdapter(list));
        tvCount.setText(String.format("%d Note(s)", list.size()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
package miniproject.notes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private ArrayList<NoteModel> list;

    public NotesAdapter(ArrayList<NoteModel> list){this.list = list;}
    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_card, parent, false);
        return (new ViewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvBody.setText(list.get(position).getBody());
        holder.tvTime.setText(list.get(position).getTime());
        holder.itemView.setOnClickListener(v->{
            Intent i = new Intent(holder.itemView.getContext(), EditNoteActivity.class);
            i.putExtra("new", "no");
            i.putExtra("id", list.get(position).getId());
            i.putExtra("title", list.get(position).getTitle());
            i.putExtra("time", list.get(position).getTime());
            i.putExtra("body", list.get(position).getBody());
            holder.itemView.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvTime, tvBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}

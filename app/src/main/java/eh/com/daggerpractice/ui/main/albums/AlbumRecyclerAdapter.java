package eh.com.daggerpractice.ui.main.albums;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import eh.com.daggerpractice.R;
import eh.com.daggerpractice.model.Album;

public class AlbumRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List< Album > albums = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_list_item,parent,false);

        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((AlbumViewHolder)holder).bind(albums.get(position));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void setPosts(List<Album> albums)
    {
        this.albums = albums;
        notifyDataSetChanged();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder
    {
        TextView txttitle;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.title);

        }

        public void bind(Album album)
        {
            txttitle.setText(album.getTitle());
        }
    }
}

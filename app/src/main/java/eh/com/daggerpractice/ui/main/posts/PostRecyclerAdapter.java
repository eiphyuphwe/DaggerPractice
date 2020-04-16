package eh.com.daggerpractice.ui.main.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import eh.com.daggerpractice.R;
import eh.com.daggerpractice.model.Post;

public class PostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List< Post > posts = new ArrayList<>();

    onItemClickListener itemClickListener;
    public void setItemClickListener(onItemClickListener onItemClickListener )
    {
       this.itemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_list_item,parent,false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((PostViewHolder)holder).bind(posts.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts)
    {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder
    {
        TextView txttitle;
        ImageView imgfavourite;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.title);
            imgfavourite = itemView.findViewById ( R.id.img_favourite );

        }

        public void bind(Post post,onItemClickListener itemClickListener)
        {
            txttitle.setText(post.getTitle());
            itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick (View v) {

                    imgfavourite.setImageResource ( R.drawable.ic_heart_selected );
                    itemClickListener.onItemClicked ( post );
                }
            } );
        }

    }

    interface  onItemClickListener{

         void onItemClicked(Post post);

    }
}

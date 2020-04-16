package eh.com.daggerpractice.ui.main.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;
import eh.com.daggerpractice.R;
import eh.com.daggerpractice.SessionManager;
import eh.com.daggerpractice.model.Post;
import eh.com.daggerpractice.util.VerticalSpacingItemDecoration;
import eh.com.daggerpractice.viewmodel.ViewModelProviderFactory;

public class PostFragment extends DaggerFragment implements PostRecyclerAdapter.onItemClickListener {
    RecyclerView recyclerView;
    PostViewModel postViewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    PostRecyclerAdapter adapter;
    @Inject
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        postViewModel = ViewModelProviders.of(this,providerFactory).get(PostViewModel.class);

        initRecyclerView();
        subscribeObservers();
    }

    private void initRecyclerView(){
        adapter.setItemClickListener ( this );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);

    }

    private void subscribeObservers()
    {

        postViewModel.observerPosts(sessionManager.getAuthUser().getValue().data.getId()).removeObservers(getViewLifecycleOwner());
        postViewModel.observerPosts(sessionManager.getAuthUser().getValue().data.getId()).observe(getViewLifecycleOwner(), new Observer< Resource< List< Post > > >() {
            @Override
            public void onChanged(Resource< List< Post > > listResource) {
              //  adapter.setPosts(listPostResource.data);
                if(listResource !=null)
                {
                    switch (listResource.status)
                    {
                        case ERROR:
                        {
                            Toast.makeText(getActivity(), listResource.message,Toast.LENGTH_LONG).show();
                            break;
                        }
                        case LOADING:
                        {
                            break;
                        }
                        case SUCCESS:
                        {
                            adapter.setPosts(listResource.data);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onItemClicked (Post post) {

        postViewModel.saveFavouritePost ( post );

    }
}

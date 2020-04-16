package eh.com.daggerpractice.ui.main.albums;

import android.os.Bundle;
import android.util.Log;
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
import eh.com.daggerpractice.model.Album;
import eh.com.daggerpractice.ui.main.posts.Resource;
import eh.com.daggerpractice.util.VerticalSpacingItemDecoration;
import eh.com.daggerpractice.viewmodel.ViewModelProviderFactory;

public class AlbumFragment extends DaggerFragment {

    RecyclerView rcyAlbum;
    @Inject
    AlbumRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    AlbumViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcyAlbum = view.findViewById(R.id.recycler_album);
        viewModel = ViewModelProviders.of(this,providerFactory).get(AlbumViewModel.class);

        initRecyclerView();
        subscribeObserver();
    }

    private void initRecyclerView(){
        rcyAlbum.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        rcyAlbum.addItemDecoration(itemDecoration);
        rcyAlbum.setAdapter(adapter);

    }

    private void subscribeObserver()
    {
        viewModel.getAlbumsbyUserId().removeObservers(getViewLifecycleOwner());
        viewModel.getAlbumsbyUserId().observe(getViewLifecycleOwner(), new Observer< Resource< List< Album > > >() {
            @Override
            public void onChanged(Resource< List< Album > > listResource) {

                switch (listResource.status) {
                    case SUCCESS: {
                        adapter.setPosts(listResource.data);
                        break;
                    }
                    case LOADING: {
                        Log.d("Album", "loading");
                        break;
                    }
                    case ERROR:
                    {
                        Toast.makeText(getActivity(),listResource.message,Toast.LENGTH_LONG).show();
                        break;
                    }
                }

            }
        });
    }
}

package eh.com.daggerpractice.ui.main.favourite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import eh.com.daggerpractice.model.Post;
import eh.com.daggerpractice.util.VerticalSpacingItemDecoration;
import eh.com.daggerpractice.viewmodel.ViewModelProviderFactory;

public class FavouriteFragment extends DaggerFragment {

    RecyclerView rcyAlbum;
    @Inject
    FavouritePostRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory providerFactory;

    FavouriteViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcyAlbum = view.findViewById(R.id.recycler_fav);
        viewModel = ViewModelProviders.of(this,providerFactory).get( FavouriteViewModel.class);

        initRecyclerView();
        viewModel.loadFavouritePosts ();
        subscribeObserver ();
    }

    private void initRecyclerView(){
        rcyAlbum.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
        rcyAlbum.addItemDecoration(itemDecoration);
        rcyAlbum.setAdapter(adapter);

    }

    private void subscribeObserver()
    {
        viewModel.observeFavouritePosts ().removeObservers(getViewLifecycleOwner());
        viewModel.observeFavouritePosts ().observe ( getViewLifecycleOwner (), new Observer< List< Post > > () {
           @Override
           public void onChanged (List< Post > posts) {
               adapter.setPosts ( posts );
           }
       } );
    }
}

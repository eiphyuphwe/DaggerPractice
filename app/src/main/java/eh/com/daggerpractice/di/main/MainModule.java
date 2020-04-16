package eh.com.daggerpractice.di.main;

import dagger.Module;
import dagger.Provides;
import eh.com.daggerpractice.network.main.MainApi;
import eh.com.daggerpractice.ui.main.albums.AlbumRecyclerAdapter;
import eh.com.daggerpractice.ui.main.favourite.FavouritePostRecyclerAdapter;
import eh.com.daggerpractice.ui.main.posts.PostRecyclerAdapter;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit)
    {
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static PostRecyclerAdapter providePostRecyclerAdapter()
    {
        return new PostRecyclerAdapter ();
    }

    @MainScope
    @Provides
    static AlbumRecyclerAdapter provideAlbumRecyclerAdapter()
    {
        return new AlbumRecyclerAdapter();
    }

    @MainScope
    @Provides
    static FavouritePostRecyclerAdapter provideFavouritePostRecyclerAdapter(){
        return new FavouritePostRecyclerAdapter ();
    }
}

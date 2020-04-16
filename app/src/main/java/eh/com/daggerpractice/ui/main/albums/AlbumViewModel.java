package eh.com.daggerpractice.ui.main.albums;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import eh.com.daggerpractice.SessionManager;
import eh.com.daggerpractice.model.Album;
import eh.com.daggerpractice.network.main.MainApi;
import eh.com.daggerpractice.ui.main.posts.Resource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AlbumViewModel extends ViewModel {

    MediatorLiveData< Resource<List< Album >> > albums = new MediatorLiveData<>();
    SessionManager sessionManager;
    MainApi mainApi;
    @Inject
    public AlbumViewModel(SessionManager sessionManager,MainApi mainApi)
    {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
    }

    public LiveData<Resource<List<Album>>> getAlbumsbyUserId()
    {
        albums.setValue(Resource.loading((List< Album>) null));
        final LiveData< Resource< List< Album > > > sources = LiveDataReactiveStreams.fromPublisher(
                mainApi.getAlbums(sessionManager.getAuthUser().getValue().data.getId())
                .onErrorReturn(new Function< Throwable, List< Album > >() {
                    @Override
                    public List< Album > apply(Throwable throwable) throws Exception {
                         Album errorAlbum = new Album();
                         List<Album> errAlbumList = new ArrayList<>();
                         errAlbumList.add(errorAlbum);
                        return errAlbumList;
                    }
                })
                .map(new Function< List< Album >, Resource< List<Album> > >() {
                    @Override
                    public Resource< List<Album> > apply(List< Album > albumsList) throws Exception {

                        if(albumsList.size()>0)
                        {
                            if(albumsList.get(0).getId()==-1)
                            {
                                return Resource.error(null,"Something went wrong!");
                            }
                        }
                        return Resource.success(albumsList);
                    }
                })
                .subscribeOn(Schedulers.io())

        );

        albums.addSource(sources, new Observer< Resource< List< Album > > >() {
            @Override
            public void onChanged(Resource< List< Album > > listResource) {

                albums.setValue(listResource);
                albums.removeSource(sources);
            }
        });
       return albums;
    }
}

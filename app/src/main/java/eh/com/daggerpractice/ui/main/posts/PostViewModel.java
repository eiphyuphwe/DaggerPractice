package eh.com.daggerpractice.ui.main.posts;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import eh.com.daggerpractice.SessionManager;
import eh.com.daggerpractice.database.FavouritePostReposistory;
import eh.com.daggerpractice.model.Post;
import eh.com.daggerpractice.network.main.MainApi;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostViewModel  extends ViewModel {

    private final SessionManager sessionManager;
    private final MainApi mainApi;

    private final FavouritePostReposistory favouritePostReposistory;
    MediatorLiveData< Resource< List<Post> > > posts = new MediatorLiveData<>();


    @Inject
    public PostViewModel(SessionManager sessionManager, MainApi mainApi, FavouritePostReposistory favouritePostReposistory)
    {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
        this.favouritePostReposistory = favouritePostReposistory;



    }

    public LiveData<Resource<List<Post>>> getObserveLiveDataPost()
    {
        return posts;
    }

    public LiveData< Resource<List<Post>> > observerPosts(int id)
    {
        posts.setValue(Resource.loading((List< Post>) null));
       final LiveData< Resource<List<Post>> >  source =

               //LiveDataReactiveStreams.fromPublisher(getPosts(sessionManager.getAuthUser().getValue().data.getId())
                       LiveDataReactiveStreams.fromPublisher(getPosts(id)
               );


       posts.addSource(source, new Observer< Resource< List< Post > > >() {
           @Override
           public void onChanged(Resource< List< Post > > listResource) {

               posts.setValue(listResource);
               posts.removeSource(source);

           }
       });

       return posts;

    }


    public Flowable< Resource< List< Post > > > getPosts(int id)
    {
        //return mainApi.getPosts(sessionManager.getAuthUser().getValue().data.getId())
        return mainApi.getPosts(id)
                        .onErrorReturn(new Function< Throwable, List< Post > >() {
                            @Override
                            public List< Post > apply(Throwable throwable) throws Exception {
                                Post post = new Post();
                                post.setId(-1);
                                ArrayList<Post> posts = new ArrayList<>();
                                posts.add(post);
                                return posts;
                            }
                        })
                        .map(new Function< List< Post >, Resource< List< Post > > >() {
                            @Override
                            public Resource< List< Post > > apply(List< Post > posts) throws Exception {
                                if(posts.size()>0) {
                                    if (posts.get(0).getId() == -1) {
                                        return Resource.error(null,"Some went wrong");
                                    }
                                }
                                return Resource.success(posts);
                            }
                        })
                        .subscribeOn(Schedulers.io());
    }


    public void saveFavouritePost(Post post)
    {
        favouritePostReposistory.insertFavPost ( post );
    }



}

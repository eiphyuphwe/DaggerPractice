package eh.com.daggerpractice.ui.main.favourite;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import eh.com.daggerpractice.database.FavouritePostReposistory;
import eh.com.daggerpractice.model.Post;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavouriteViewModel extends ViewModel {

    FavouritePostReposistory favouritePostReposistory;
    MutableLiveData< List< Post > > favouritePostLiveData = new MutableLiveData<> (  );

    @Inject
    public FavouriteViewModel (FavouritePostReposistory favouritePostReposistory)
    {
        this.favouritePostReposistory = favouritePostReposistory;
    }

    public void loadFavouritePosts()
    {
      favouritePostReposistory.getAllFavPosts ()
                .subscribeOn ( Schedulers.io () )
                .observeOn ( AndroidSchedulers.mainThread ())
                .subscribe ( new FlowableSubscriber< List< Post > > () {
                    @Override
                    public void onSubscribe (Subscription s) {

                    }

                    @Override
                    public void onNext (List< Post > posts) {

                        favouritePostLiveData.setValue ( posts );
                    }

                    @Override
                    public void onError (Throwable t) {

                    }

                    @Override
                    public void onComplete ( ) {

                    }
                } );

    }

    public LiveData<List<Post>> observeFavouritePosts()
    {
        return favouritePostLiveData;
    }


}

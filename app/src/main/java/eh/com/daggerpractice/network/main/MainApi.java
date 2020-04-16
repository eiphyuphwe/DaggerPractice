package eh.com.daggerpractice.network.main;

import java.util.List;

import eh.com.daggerpractice.model.Album;
import eh.com.daggerpractice.model.Post;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    @GET("posts")
    Flowable< List< Post > > getPosts(
            @Query("userId") int id
    );

    @GET("albums")
    Flowable<List< Album >> getAlbums(
            @Query("userId") int id
    );


}

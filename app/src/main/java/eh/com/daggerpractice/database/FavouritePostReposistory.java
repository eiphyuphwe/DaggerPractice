package eh.com.daggerpractice.database;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import eh.com.daggerpractice.model.Post;
import io.reactivex.Flowable;

@Singleton
public class FavouritePostReposistory {

    private final FavouriteDao favouriteDao;

    @Inject
   public FavouritePostReposistory (FavouriteDao favouriteDao)
   {
       this.favouriteDao = favouriteDao;
   }

   public Flowable< List< Post > > getAllFavPosts()
   {
       return favouriteDao.getAllFavouritePost ();
   }

   public void insertFavPost(Post post)
   {
       favouriteDao.insert ( post );
   }

   public void deleteFavPost(Post delPost)
   {
       favouriteDao.remove ( delPost );
   }

}

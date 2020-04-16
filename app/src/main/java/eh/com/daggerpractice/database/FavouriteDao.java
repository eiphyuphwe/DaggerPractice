package eh.com.daggerpractice.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import eh.com.daggerpractice.model.Post;
import io.reactivex.Flowable;

@Dao
public interface FavouriteDao {

    @Query ( "SELECT * FROM fav_posts")
    Flowable< List< Post > > getAllFavouritePost();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Delete
    void remove(Post post);


}

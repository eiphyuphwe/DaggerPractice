package eh.com.daggerpractice.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import eh.com.daggerpractice.model.Post;

@Database ( entities = {Post.class},version = DaggerPracticeDatabase.VERSION,exportSchema = false)
public abstract class DaggerPracticeDatabase extends RoomDatabase {

    static final int VERSION = 1;
    public static final String DATABASE_NAME = "daggerprac.db" ;

    public abstract FavouriteDao getShowDAO ( );
}

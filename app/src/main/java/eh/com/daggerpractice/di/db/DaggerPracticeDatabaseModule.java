package eh.com.daggerpractice.di.db;

import android.app.Application;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import eh.com.daggerpractice.database.DaggerPracticeDatabase;
import eh.com.daggerpractice.database.FavouriteDao;
import kotlin.jvm.JvmStatic;

@Module
public class DaggerPracticeDatabaseModule {

    @JvmStatic
    @Singleton
    @Provides
    public DaggerPracticeDatabase provideDaggerPracticeDatabase(Application context)
    {
        return Room.databaseBuilder (context,DaggerPracticeDatabase.class,DaggerPracticeDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration ()
                .allowMainThreadQueries ()
                .build ();
    }

    @JvmStatic
    @Singleton
    @Provides
    public FavouriteDao provideShowDAO(DaggerPracticeDatabase daggerPracticeDatabase)
    {
       return daggerPracticeDatabase.getShowDAO();
    }

    /*@Singleton
    @Provides
    PostReposistory providePostReposistory(ShowDao showDao)
    {
        return new PostReposistory ( showDao );
    }*/

}

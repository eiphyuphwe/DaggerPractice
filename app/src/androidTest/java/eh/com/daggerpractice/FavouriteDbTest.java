package eh.com.daggerpractice;

import android.content.Context;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import eh.com.daggerpractice.database.DaggerPracticeDatabase;
import eh.com.daggerpractice.database.FavouriteDao;
import eh.com.daggerpractice.database.FavouritePostReposistory;
import eh.com.daggerpractice.model.Post;
import eh.com.daggerpractice.ui.main.favourite.FavouriteViewModel;
import io.reactivex.subscribers.TestSubscriber;

@RunWith (MockitoJUnitRunner.class)
public class FavouriteDbTest {

    private FavouriteDao favouriteDao;
    private DaggerPracticeDatabase db;
    FavouriteViewModel viewModel;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    FavouritePostReposistory reposistory;
    //for live data to run thread
    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();



    private androidx.lifecycle.Observer< List<Post> > mockObserver;

    @Before
    public void createDb ( ) {
        Context context = ApplicationProvider.getApplicationContext ();
        db = Room.inMemoryDatabaseBuilder ( context, DaggerPracticeDatabase.class ).build ();
        favouriteDao = db.getShowDAO ();
        MockitoAnnotations.initMocks(this);
        viewModel = new FavouriteViewModel (reposistory );
        mockObserver =  Mockito.mock(androidx.lifecycle.Observer.class);
        viewModel.observeFavouritePosts ().observeForever ( mockObserver );

    }



    @After
    public void closeDb ( ) throws IOException
    {
        db.close ();
    }


    @Test
    public void readAllFavouritePostFromDbTest() throws Exception
    {
        Post fav_post1 = new Post ();
        fav_post1.setId ( 1 );
        fav_post1.setTitle ( "Post 1" );

        Post fav_post2 = new Post ();
        fav_post2.setId ( 2 );
        fav_post2.setTitle ( "Post 2" );

        favouriteDao.insert ( fav_post1 );
        favouriteDao.insert ( fav_post2 );

        TestSubscriber<  List< Post >  > testSubscriber = new TestSubscriber<>();

        favouriteDao.getAllFavouritePost ().subscribe ( testSubscriber );
        if(testSubscriber.values ().size ()>0)
        {
            List<Post> favPosts = testSubscriber.values ().get ( 0 );
            assert (!favPosts.isEmpty ());
            Assert.assertEquals ( 2,favPosts.size () );
            Assert.assertEquals ( fav_post1.getTitle (),favPosts.get ( 0 ).getTitle () );
           // testSubscriber.assertComplete ();
            testSubscriber.assertNoErrors ();
        }

    }

 /*   @Test
    public void observeFavourirtPostLiveData_ObserveStateChangedTest()
    {
        Post fav_post1 = new Post ();
        fav_post1.setId ( 1 );
        fav_post1.setTitle ( "Post 1" );

        Post fav_post2 = new Post ();
        fav_post2.setId ( 2 );
        fav_post2.setTitle ( "Post 2" );

        favouriteDao.insert ( fav_post1 );
        favouriteDao.insert ( fav_post2 );

        viewModel.loadFavouritePosts ();
        LiveData<List<Post>> favPostsLiveData = viewModel.observeFavouritePosts ();
        Assert.assertTrue ( viewModel.observeFavouritePosts ().hasObservers () );
        Mockito.verify(mockObserver).onChanged(viewModel.observeFavouritePosts ().getValue());
        Assert.assertEquals ( fav_post1.getTitle (),viewModel.observeFavouritePosts ().getValue ().get ( 0 ).getTitle ());
    }*/




}

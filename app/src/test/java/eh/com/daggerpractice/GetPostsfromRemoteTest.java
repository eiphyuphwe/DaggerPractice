package eh.com.daggerpractice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import eh.com.daggerpractice.database.FavouritePostReposistory;
import eh.com.daggerpractice.model.Post;
import eh.com.daggerpractice.network.main.MainApi;
import eh.com.daggerpractice.ui.main.posts.PostViewModel;
import eh.com.daggerpractice.ui.main.posts.Resource;
import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GetPostsfromRemoteTest {

    @Mock
    MainApi mainApi;

    SessionManager sessionManager;


    @Mock
    FavouritePostReposistory reposistory;


    PostViewModel postViewModel;


    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private androidx.lifecycle.Observer<Resource<List<Post>>> mockObserver;


   @Before
    public void setup()
   {
       MockitoAnnotations.initMocks(this);
       sessionManager = new SessionManager();
       postViewModel = new PostViewModel(sessionManager,mainApi,reposistory);
       //mock the live data Observer
       mockObserver =Mockito.mock(androidx.lifecycle.Observer.class);
       postViewModel.getObserveLiveDataPost().observeForever(mockObserver);

   }

   @Test
    public void testPreConditions()
   {
       assertNotNull(postViewModel);
   }

   @Test
    public void testGetPostApiResponse_success() throws Exception
   {
       TestSubscriber< Resource<List< Post > >> testSubscriber = new TestSubscriber<>();
       List<Post> posts = new ArrayList<>();
       Post post = new Post();
       post.setId(1);
       post.setTitle("Sample");
       post.setBody("Desc");
       post.setUserId(1);
       posts.add(post);



       //prepare fake response
       //when(mainApi.getPosts(1))
         //     .thenReturn();

       Mockito.doReturn(Flowable.just(posts)).when(mainApi).getPosts(1);


       //trigger respone

        postViewModel.getPosts(1).subscribe(testSubscriber);



        if(testSubscriber.values().size()>0) {
            Resource< List< Post > > result = testSubscriber.values().get(0);
            testSubscriber.assertComplete ();
            testSubscriber.assertNoErrors ();

            testSubscriber.assertValue(result);
        }


   }

   @Test
   public void testPostLiveDataObserverStateChanged() throws Exception
   {
       TestSubscriber< Resource<List< Post > >> testSubscriber = new TestSubscriber<>();
       List<Post> posts = new ArrayList<>();
       Post post = new Post();
       post.setId(1);
       post.setTitle("Sample");
       post.setBody("Desc");
       post.setUserId(1);
       posts.add(post);


       Mockito.doReturn(Flowable.just(posts)).when(mainApi).getPosts(1);
       LiveData<Resource<List<Post>>> resultLivePosts=  postViewModel.observerPosts(1);
       Assert.assertTrue(postViewModel.observerPosts(1).hasObservers());
       Assert.assertEquals (Resource.Status.LOADING,postViewModel.getObserveLiveDataPost ().getValue ().status); //Loading state

        Mockito.verify(mockObserver).onChanged(postViewModel.getObserveLiveDataPost().getValue());
       Assert.assertEquals(postViewModel.getObserveLiveDataPost().getValue().data.get(0).getTitle(),"Sample");
       Assert.assertEquals ( Resource.Status.SUCCESS,postViewModel.getObserveLiveDataPost ().getValue ().status );// get data and status is success

   }

   @Test
    public void testGetPostApiResponse_Failure() throws Exception
   {
       TestSubscriber<Resource<List<Post>>> testSubscriber = new TestSubscriber<>();

       //prepare fake exception
       Throwable exception = new IOException();

       //preapre fake response
       Mockito.doReturn(Flowable.error(exception)).when(mainApi).getPosts(1);
       //trigger respone

       postViewModel.getPosts(1).toObservable().subscribe(new Observer< Resource< List< Post > > >() {
           @Override
           public void onSubscribe(Disposable d) {

           }

           @Override
           public void onNext(Resource< List< Post > > listResource) {

               //assertNull(listResource);
           }

           @Override
           public void onError(Throwable e) {

               assertTrue(e.getMessage()!=null);
           }

           @Override
           public void onComplete() {

           }
       });

   }


}

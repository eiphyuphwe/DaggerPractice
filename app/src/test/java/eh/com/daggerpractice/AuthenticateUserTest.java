package eh.com.daggerpractice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import eh.com.daggerpractice.model.User;
import eh.com.daggerpractice.network.auth.AuthApi;
import eh.com.daggerpractice.ui.auth.AuthResource;
import eh.com.daggerpractice.ui.auth.AuthViewModel;
import eh.com.daggerpractice.ui.main.posts.Resource;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;


public class AuthenticateUserTest {

    @Mock
    AuthApi authApi;

    SessionManager sessionManager;


    AuthViewModel viewModel;
    //For LiveData Observer
    private Observer< AuthResource <User>> mockUserObserver;

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        sessionManager = new SessionManager();
        viewModel = new AuthViewModel(authApi,sessionManager);
        //mock the live data
        mockUserObserver = Mockito.mock(Observer.class);
        viewModel.observeAuthState().observeForever(mockUserObserver);
    }

    @Test
    public void observeAuthenticateUserTest()
    {
        TestSubscriber< AuthResource<User >> testSubscriber = new TestSubscriber<>();
        User user = new User();
        user.setId(1);
        user.setEmail("aa@gmail.com");
        user.setUsername("Aster");
        Mockito.doReturn(Flowable.just(user)).when(authApi).getUser(1);

        viewModel.authenticateUserbyId(1);

        //here i want to check each status change . how can i able to check?

        Mockito.verify(mockUserObserver).onChanged(viewModel.observeAuthState().getValue());

        Assert.assertEquals(viewModel.observeAuthState().getValue().data.getEmail(),user.getEmail());


    }

    @Test
    public void testUserAuthenticate_Failure() throws Exception {
        TestSubscriber< Resource< User > > testSubscriber = new TestSubscriber<>();

        //prepare fake exception
        Throwable exception = new IOException();

        //preapre fake response
        Mockito.doReturn(Flowable.error(exception)).when(authApi).getUser(1);

        viewModel.authenticateUserbyId(1);
        Mockito.verify(mockUserObserver).onChanged(viewModel.observeAuthState().getValue());
        Assert.assertEquals(viewModel.observeAuthState().getValue().status, AuthResource.AuthStatus.NOT_AUTHENTICATED);

    }


}

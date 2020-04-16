package eh.com.daggerpractice.ui.auth;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import eh.com.daggerpractice.SessionManager;
import eh.com.daggerpractice.model.User;
import eh.com.daggerpractice.network.auth.AuthApi;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private final AuthApi authApi; // @AuthScope
    private final SessionManager sessionManager;
    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();
    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager){
        this.sessionManager = sessionManager;
        this.authApi = authApi;

    }

    public void authenticateUserbyId(int userid)
    {
        sessionManager.authenticateWithId(queryUserById(userid));


    }

    public LiveData< AuthResource< User>> queryUserById(int userid)
    {
        return LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(userid)
                    //instead of calling error, if error happen
                    .onErrorReturn(new Function< Throwable, User >() {
                        @Override
                        public User apply(Throwable throwable) throws Exception {
                            User errorUser = new User();
                            errorUser.setId(-1);
                            return errorUser;
                        }

                    })
                    .map(new Function< User, AuthResource< User > >() {
                        @Override
                        public AuthResource< User > apply(User user) throws Exception {

                            if(user.getId()==-1)
                            {
                                return AuthResource.error("Could not authenticate",(User) null);
                            }

                            return AuthResource.authenticated(user);
                        }
                    })
                    .subscribeOn(Schedulers.io())
    );
    }

    public LiveData<AuthResource<User>> observeAuthState()
    {
        return sessionManager.getAuthUser();
    }
}

package eh.com.daggerpractice;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import eh.com.daggerpractice.model.User;
import eh.com.daggerpractice.ui.auth.AuthResource;

@Singleton
public class SessionManager {

    public MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager()
    {

    }

    public void authenticateWithId(final LiveData<AuthResource<User>> source)
    {
        if(cachedUser!=null)
        {
            cachedUser.setValue(AuthResource.loading((User) null));
            cachedUser.addSource(source, new Observer< AuthResource< User > >() {
                @Override
                public void onChanged(AuthResource< User > userAuthResource) {

                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);

                    if(userAuthResource.status.equals(AuthResource.AuthStatus.ERROR))
                    {
                        cachedUser.setValue(AuthResource.< User>logout());
                    }
                }
            });

        }
    }

    public void logout()
    {
        Log.d("Logging out","LogOut");
        cachedUser.setValue(AuthResource.< User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser()
    {
        return cachedUser;
    }


}

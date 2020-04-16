package eh.com.daggerpractice.ui.main.profile;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import eh.com.daggerpractice.SessionManager;
import eh.com.daggerpractice.model.User;
import eh.com.daggerpractice.ui.auth.AuthResource;

public class ProfileViewModel extends ViewModel {

    private final SessionManager sessionManager;

    @Inject
    ProfileViewModel (SessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }

    public LiveData< AuthResource< User > > getAuthenticatedUser()
    {
        return sessionManager.getAuthUser();
    }
}

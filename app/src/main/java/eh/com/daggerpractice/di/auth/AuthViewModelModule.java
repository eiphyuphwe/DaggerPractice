package eh.com.daggerpractice.di.auth;


import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import eh.com.daggerpractice.di.ViewModelKey;
import eh.com.daggerpractice.ui.auth.AuthViewModel;

@Module
public abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}

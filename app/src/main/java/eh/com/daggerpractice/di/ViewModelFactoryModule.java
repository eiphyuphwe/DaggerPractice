package eh.com.daggerpractice.di;


import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import eh.com.daggerpractice.viewmodel.ViewModelProviderFactory;

@Module
public abstract class ViewModelFactoryModule {

    //same with @provide , difference is @Bind used for abstrace method
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelProviderFactory(ViewModelProviderFactory providerFactory);

}

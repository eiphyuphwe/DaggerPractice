package eh.com.daggerpractice.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import eh.com.daggerpractice.di.auth.AuthModule;
import eh.com.daggerpractice.di.auth.AuthScope;
import eh.com.daggerpractice.di.auth.AuthViewModelModule;
import eh.com.daggerpractice.di.main.MainFragmentBuilderModule;
import eh.com.daggerpractice.di.main.MainModule;
import eh.com.daggerpractice.di.main.MainScope;
import eh.com.daggerpractice.di.main.MainViewModelModule;
import eh.com.daggerpractice.ui.auth.AuthActivity;
import eh.com.daggerpractice.ui.main.MainActivity;

@Module
public abstract class ActivityBuilderModule {

    @AuthScope
    @ContributesAndroidInjector (
            modules = {AuthViewModelModule.class, AuthModule.class}
    )//every activity, fragment can be potential client and @contributer have to be abstract
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules ={ MainFragmentBuilderModule.class, MainViewModelModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();

}

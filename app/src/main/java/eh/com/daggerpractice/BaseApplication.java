package eh.com.daggerpractice;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import eh.com.daggerpractice.di.DaggerAppComponent;

//Application Module
public class BaseApplication extends DaggerApplication
{
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}

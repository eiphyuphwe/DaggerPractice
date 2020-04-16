package eh.com.daggerpractice.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import eh.com.daggerpractice.BaseApplication;
import eh.com.daggerpractice.SessionManager;
import eh.com.daggerpractice.di.db.DaggerPracticeDatabaseModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuilderModule.class,
                AppModule.class,
                DaggerPracticeDatabaseModule.class,
                ViewModelFactoryModule.class
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication>  {
    //BaseApplication is the client and it mens AppCompaint will inject BaseApplication

    SessionManager sessionManager();




   @Component.Builder //override method
    interface Builder{

       @BindsInstance
       Builder application(Application app);

       AppComponent build(); // build method return AppCompnent //must include
   }




}

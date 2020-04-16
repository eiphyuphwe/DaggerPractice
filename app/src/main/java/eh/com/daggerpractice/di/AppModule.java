package eh.com.daggerpractice.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Singleton;

import androidx.core.content.ContextCompat;
import dagger.Module;
import dagger.Provides;
import eh.com.daggerpractice.R;
import eh.com.daggerpractice.util.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

 //Glide also need only one instance

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions()
    {
        return RequestOptions.placeholderOf(R.drawable.white_background)
                .error(R.drawable.white_background);
    }

    @Singleton
    @Provides
    RequestManager provideGlideInstance(Application application,RequestOptions requestOptions)
    {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton //limit the scope and @Singleton will persist all over the app
    @Provides
    static Drawable provideAppDrawable(Application application)
    {
        return ContextCompat.getDrawable(application, R.drawable.logo);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofitInstance()
    {
       return new Retrofit.Builder()
               .baseUrl(Constants.BASE_URL)
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               .build();

    }


}

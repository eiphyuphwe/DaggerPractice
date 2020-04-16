package eh.com.daggerpractice.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import eh.com.daggerpractice.ui.main.albums.AlbumFragment;
import eh.com.daggerpractice.ui.main.favourite.FavouriteFragment;
import eh.com.daggerpractice.ui.main.posts.PostFragment;
import eh.com.daggerpractice.ui.main.profile.ProfileFragment;

@Module
public abstract class MainFragmentBuilderModule {

   @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();


   @ContributesAndroidInjector
    abstract PostFragment contributePostFragment();

   @ContributesAndroidInjector
    abstract AlbumFragment contributeAlbumFragment();

   @ContributesAndroidInjector
    abstract FavouriteFragment contributeFavouriteFragment();




}

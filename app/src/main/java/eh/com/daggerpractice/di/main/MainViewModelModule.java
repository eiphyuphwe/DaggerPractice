package eh.com.daggerpractice.di.main;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import eh.com.daggerpractice.di.ViewModelKey;
import eh.com.daggerpractice.ui.main.albums.AlbumViewModel;
import eh.com.daggerpractice.ui.main.favourite.FavouriteViewModel;
import eh.com.daggerpractice.ui.main.posts.PostViewModel;
import eh.com.daggerpractice.ui.main.profile.ProfileViewModel;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel.class)
    public abstract ViewModel bindPostViewModel(PostViewModel postViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel.class)
    public abstract ViewModel bindAlbumViewModel(AlbumViewModel postViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel.class)
    public abstract ViewModel bindFavouriteViewModel(FavouriteViewModel postViewModel);



}

package eh.com.daggerpractice.network.auth;


import eh.com.daggerpractice.model.User;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;



    public interface AuthApi {

        @GET("users/{id}")
        Flowable<User> getUser(
                @Path("id") int id
        );
    }



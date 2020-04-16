package eh.com.daggerpractice.ui.main;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class RxjavaTest {

    List<String> strList = new ArrayList<> ( );

    public void createObservable()
    {
        strList.add ( "MgMg" );
        strList.add ( "Mama" );
        strList.add ( "KoKo" );

        Observable observable = Observable.create ( new ObservableOnSubscribe() {
            @Override
            public void subscribe (ObservableEmitter emitter) throws Exception {

                try{
                    for(String str:strList)
                    {
                        emitter.onNext ( str );
                    }
                }catch (Exception e)
                {
                   emitter.onError ( e );
                }
            }
        } );

        Observer observer = new Observer () {
            @Override
            public void onSubscribe (Disposable d) {

                System.out.println ("onSubscribe");
            }

            @Override
            public void onNext (Object o) {

                System.out.println ( o );
            }

            @Override
            public void onError (Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete ( ) {

                System.out.println("onComplete");
            }
        };

       observable.subscribe ( observer );
    }
}

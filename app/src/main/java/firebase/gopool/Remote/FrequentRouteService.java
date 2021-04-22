package firebase.gopool.Remote;

import java.util.List;

import firebase.gopool.models.FrequentRouteResults;
import firebase.gopool.models.Sender;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface FrequentRouteService {
    @Headers({
            "Content-Type:application/json",
    })
    @GET("route/account_id")
    Call<List<FrequentRouteResults>> getRouteByAccountId(@Query("account_id") String account_id);



}

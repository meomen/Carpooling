package firebase.gopool.Remote;

import java.util.List;

import firebase.gopool.Model.ExceptionResult;
import firebase.gopool.Model.Trip;
import firebase.gopool.Model.Waypoint;
import firebase.gopool.models.FrequentRouteResults;
import firebase.gopool.models.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;


public interface FrequentRouteService {
    @Headers({
            "Content-Type:application/json",
    })
    @GET("route/account_id")
    Call<List<FrequentRouteResults>> getRouteByAccountId(@Query("account_id") String account_id);

    @PUT("route/is_shared")
    Call<ExceptionResult> updateIsShared(@Query("is_shared") int is_shared, @Query("type_shared") String type_shared, @Query("id") int id, @Query("account_id") String account_id);

    @POST("trip")
    Call<Trip> addTrip(@Body Trip trip);

    @POST("waypoint")
    Call<Waypoint> addWaypoint (@Body Waypoint waypoint);
}

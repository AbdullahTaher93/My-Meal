
package com.apps.my_meal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApi {

    @GET("lookup.php?i=52772")
    Call<Meals> getMeal();

    // TODO 12 also make the Call like getMeals() method for category

    /*
     * @GET (" url ") -->
     *     this is the request annotation with REQUEST METHOD [GET]
     *
     * Call <Object> methodName(); -->
     *     we will make the getCategoris () method with the Call <Category> || method meaning
     *     that the result of the request [GET] will be accommodated into Object (Category)
     *
     */
    @GET("categories.php")
    Call<Categories> getCategories();

}

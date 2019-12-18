
package com.apps.my_meal;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class HomePresenter {

    private HomeView view;

    public HomePresenter(HomeView view) {
        this.view = view;
    }

    void getMeals() {
        view.showLoding();
        Call<Meals> mealsCall = Utils.getApi().getMeal();
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call,@NonNull Response<Meals> response) {
                view.hideLoding();
                if (response.isSuccessful() && response.body() != null) {
                    view.setMeal(response.body().getMeals());


                }
                else {
                    // TODO 23 Show an error message if the conditions are not met
                    view.onErrorLoding(response.message());

                }
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                /*
                 * Failure will be thrown here
                 * for this you must do
                 * 1. closes loading
                 * 2. displays an error message
                 */
                view.hideLoding();
                view.onErrorLoding(t.getLocalizedMessage());
            }
        });
    }


    void getCategories() {
        view.showLoding();

        Call<Categories> categoriesCall = Utils.getApi().getCategories();
        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call,@NonNull Response<Categories> response) {
                view.hideLoding();
                if (response.isSuccessful() && response.body() != null) {
                    view.setCategory(response.body().getCategories());
                }
                else {
                   view.onErrorLoding(response.message());

                }
            }

            @Override
            public void onFailure(@NonNull  Call<Categories> call, Throwable t) {
                view.hideLoding();
                view.onErrorLoding(t.getLocalizedMessage());
            }
        });
    }
}

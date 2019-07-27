package com.hfad.weatherappmaddevs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.weatherappmaddevs.Adapter.WeatherForecastAdapter;
import com.hfad.weatherappmaddevs.Common.Common;
import com.hfad.weatherappmaddevs.Model.WeatherForecastResult;
import com.hfad.weatherappmaddevs.Retrofit.IOpenWeatherMap;
import com.hfad.weatherappmaddevs.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView txt_city_name,txt_geo_coords;
    RecyclerView recycler_forecast;

    static ForecastFragment instance;

    public static ForecastFragment getInstance() {
        if (instance==null) instance = new ForecastFragment();
        return instance;
    }

    public ForecastFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_forecast, container, false);

        txt_city_name = (TextView)itemView.findViewById(R.id.txt_city_name);
        txt_geo_coords = (TextView)itemView.findViewById(R.id.txt_geo_coords);

        recycler_forecast = (RecyclerView)itemView.findViewById(R.id.recycler_forecast);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        getForecastWeatherInformation();

        return itemView;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void getForecastWeatherInformation() {

            if (!Common.isCitySelected){
                compositeDisposable.add(mService.getForecastWeatherByLatLng(
                        String.valueOf(Common.current_location.getLatitude()),
                        String.valueOf(Common.current_location.getLongitude()),
                        Common.API_ID,
                        "metric","ru")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<WeatherForecastResult>() {
                            @Override
                            public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                                displayForecastWeather(weatherForecastResult);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("ERROR",""+throwable.getMessage());
                            }
                        })

                );
            }else{
                compositeDisposable.add(mService.getForecastWeatherByCityName(Common.cityName,
                        Common.API_ID,
                        "metric","ru")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<WeatherForecastResult>() {
                            @Override
                            public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                                displayForecastWeather(weatherForecastResult);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("ERROR",""+throwable.getMessage());
                            }
                        })

                );
                Common.cityName = null;
                Common.isCitySelected=false;
            }
    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
        txt_city_name.setText(new StringBuilder(weatherForecastResult.city.name));
        txt_geo_coords.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(),weatherForecastResult);
        recycler_forecast.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getForecastWeatherInformation();
    }
}

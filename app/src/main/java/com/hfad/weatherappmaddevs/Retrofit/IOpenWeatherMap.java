package com.hfad.weatherappmaddevs.Retrofit;

import com.hfad.weatherappmaddevs.Model.WeatherForecastResult;
import com.hfad.weatherappmaddevs.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lng,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit,
                                                 @Query("lang") String lang);

    @GET("weather")
    Observable<WeatherResult> getWeatherByCityName(@Query("q") String cityName,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit,
                                                   @Query("lang") String lang);

    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLng(@Query("lat") String lat,
                                                                 @Query("lon") String lng,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String unit,
                                                                 @Query("lang") String lang);

    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByCityName(@Query("q") String cityName,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String unit,
                                                                 @Query("lang") String lang);
}

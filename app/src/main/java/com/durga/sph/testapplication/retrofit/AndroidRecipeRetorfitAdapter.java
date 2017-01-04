package com.durga.sph.testapplication.retrofit;

import android.content.Context;

import com.durga.sph.testapplication.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 1/3/17.
 */

public class AndroidRecipeRetorfitAdapter
{
    public static AndroidRecipeService getRestService(Context context)
    {
        Retrofit r = new Retrofit.Builder().baseUrl(context.getResources().getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build();
        AndroidRecipeService service = r.create(AndroidRecipeService.class);
        return service;
    }
}

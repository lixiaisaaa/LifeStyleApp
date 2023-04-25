package com.example.lifestyleapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WeatherFragment extends Fragment implements View.OnClickListener{


    private EditText mEtLocation;
    private TextView mTvTemp;
    private TextView mTvPress;
    private TextView mTvHum;
    private WeatherData mWeatherData;
    private Button mBtSubmit;

    private WeatherViewModel mWeatherViewModel;



    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String strCity = getArguments().getString("city");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        //Get the edit text and all the text views
        mEtLocation = (EditText) view.findViewById(R.id.et_location);
        mTvTemp = (TextView) view.findViewById(R.id.tv_temp);
        mTvPress = (TextView) view.findViewById(R.id.tv_pressure);
        mTvHum = (TextView) view.findViewById(R.id.tv_humidity);



        if(savedInstanceState!=null) {
            String Lo = savedInstanceState.getString("loc");
            String temp = savedInstanceState.getString("tvTemp");
            String hum = savedInstanceState.getString("tvHum");
            String press = savedInstanceState.getString("tvPress");
            if( Lo != null)
                mEtLocation.setText(Lo);
            if (temp != null)
                mTvTemp.setText(""+temp);
            if (hum != null)
                mTvHum.setText(""+hum);
            if (press != null)
                mTvPress.setText(""+press);
        }

        mEtLocation.setText(strCity);



        mBtSubmit = (Button) view.findViewById(R.id.button_submit);
        mBtSubmit.setOnClickListener(this);


        //Create the view model
        mWeatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        //Set the observer
        (mWeatherViewModel.getData()).observe(getViewLifecycleOwner(),nameObserver);


        return view;
    }

    //create an observer that watches the LiveData<WeatherData> object
    final Observer<WeatherData> nameObserver  = new Observer<WeatherData>() {
        @Override
        public void onChanged(@Nullable final WeatherData weatherData) {
            // Update the UI if this data variable changes
            if(weatherData!=null) {
                mTvTemp.setText("" + Math.round(weatherData.getTemperature().getTemp() - 273.15) + " C");
                mTvHum.setText("" + weatherData.getCurrentCondition().getHumidity() + "%");
                mTvPress.setText("" + weatherData.getCurrentCondition().getPressure() + " hPa");
            }
        }
    };
    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.button_submit:{
                //Get the string from the edit text and sanitize the input
                String inputFromEt = mEtLocation.getText().toString().replace(' ','&');
                loadWeatherData(inputFromEt);
            }

            break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("loc",mEtLocation.getText().toString());
        outState.putString("tvTemp",mTvTemp.getText().toString());
        outState.putString("tvHum",mTvHum.getText().toString());
        outState.putString("tvPress",mTvPress.getText().toString());
    }




    private void loadWeatherData(String location){
        mWeatherViewModel.setLocation(location);
    }




}
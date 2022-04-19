package com.example.traintable_java;

import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private Route[] routes;
    private RecyclerView recyclerView;
    private Button mbutton;
    private Switch mswitch;
    private EditText from;
    private EditText to;
    private TextView fromTv;
    private TextView toTv;
    private EditText editCode;
    private TextView codeTv;
    private  Animation fadeout;
    private  Animation fadein;
    private boolean switcState=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mswitch=findViewById(R.id.mswitch);
        recyclerView = findViewById(R.id.RV);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //LinearLayoutManager l =
        mbutton = findViewById(R.id.search);
        from=findViewById(R.id.editTextTextFrom);
        to=findViewById(R.id.editTextTextTo);
        fromTv=findViewById(R.id.textViewFrom);
        toTv=findViewById(R.id.textViewTo);
        toTv=findViewById(R.id.textViewTo);
        editCode=findViewById(R.id.editTextCode);
        codeTv=findViewById(R.id.textViewCode);
        fadeout = AnimationUtils.loadAnimation(this,R.anim.fadeout);
        fadein = AnimationUtils.loadAnimation(this,R.anim.fadein);
        RecyclerView v = new RecyclerView(this);
       hideView(editCode,codeTv);
       recyclerView.setVisibility(View.INVISIBLE);
        mbutton.setOnClickListener(
                view -> {
                    if(!switcState) {
                        String fromStation = from.getText().toString().trim();
                        String toStation = to.getText().toString().trim();
                        showResult(fromStation, toStation);
                    }
                    else {
                        String code = editCode.getText().toString().trim();
                        showResult(code);

            }
        });
        mswitch.setOnCheckedChangeListener(
                    (buttonView, isChecked) ->{
                        toggleSwitch(isChecked);
                });


    }
    void toggleSwitch(boolean isChecked){
        if(isChecked){
            mswitch.setText(R.string.switch_on);
            hideView(to,toTv,from,fromTv, recyclerView);
            recyclerView.setAdapter(new StopAdapterR(new ArrayList<>()));
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            showView(editCode,codeTv);
            switcState=true;
        }else  {
            mswitch.setText(R.string.switch_off);
            showView(to,toTv,from,fromTv);
            recyclerView.setAdapter(new StopAdapterR(new ArrayList<>()));
            recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            hideView(editCode,codeTv, recyclerView);
            switcState=false;
        }
    }

    void showResult(String from , String to){
        loadRoutes();
        ArrayList<Match> arrayOfMatches=new ArrayList<>();
        try {
            arrayOfMatches = searchByName(from,to);
        }
        catch (IllegalArgumentException e){
            Toast.makeText(this,R.string.toast_same_names,Toast.LENGTH_LONG).show();
            return;
        }
        catch (NullPointerException e){
            Toast.makeText(this,R.string.toast_empty_input,Toast.LENGTH_LONG).show();
            return;
        }
        ArrayList<Match> headerMatch = new ArrayList<>();
        headerMatch.add( new Match(
                getString(R.string.header_code),
                getString(R.string.header_city_from),
                getString(R.string.header_city_to)  ,
                getString(R.string.header_time_from),
                getString(R.string.header_time_to)
        ));
        ConcatAdapter adapter = new ConcatAdapter(
                new HeaderMatchAdapterR(headerMatch),
                new MatchAdapterR(arrayOfMatches)
        );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.startAnimation(fadein);
    }
    void showResult(String code){
        loadRoutes();
        Match match=null;
        try {
            match = searchByCode(code);
        }
        catch (IllegalArgumentException e){
            Toast.makeText(this,R.string.toast_bad_code,Toast.LENGTH_LONG).show();
            return;
        }
        catch (NullPointerException e){
            Toast.makeText(this,R.string.toast_empty_input_code,Toast.LENGTH_LONG).show();
            return;
        }


        ArrayList<Stop> stopsList =getRouteByMatch(match).getStopsList();
        ArrayList<Stop> headerStop = new ArrayList<>();
        headerStop.add(new Stop(
                getString(R.string.stop_from_text),
                getString( R.string.stop_fromTime_text)
        ));
        ConcatAdapter adapter = new ConcatAdapter(
                new HeaderStopAdapterR(headerStop),
                new StopAdapterR(stopsList)
        );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.startAnimation(fadein);
    }






    void loadRoutes()  {
        if(routes!=null) return;
        Gson gson = new Gson();
        try {
            AssetManager manager = getApplicationContext().getAssets();
            InputStream is = manager.open("routes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json= new String(buffer, StandardCharsets.UTF_8);
            JsonArray jsonarr = gson.fromJson(json, JsonArray.class);
            routes=new Route[jsonarr.size()];
            routes=gson.fromJson(jsonarr,routes.getClass());

        } catch (IOException e) {
            Path currentRelativePath = Paths.get("");
            String internalPath = currentRelativePath.toAbsolutePath().toString();
            Log.e("Exception",internalPath);
        }

    }
    ArrayList<Match> searchByName(String startPoint, String endPoint){
        ArrayList<Match> mat = new ArrayList<>();
        if(startPoint.equals("")||endPoint.equals("")) throw new NullPointerException("Empty input");
        else if(startPoint.equals(endPoint)) throw new IllegalArgumentException("Stops names are the same");
        for (Route i:routes) {
            String[] stopsNames = i.getStopsNames();
            for (int j = 0; j < stopsNames.length; j++) {
                if(startPoint.equals(stopsNames[j])){
                    for (int k = 0; k < stopsNames.length; k++) {
                        if(endPoint.equals(stopsNames[k])&&k>j){
                            Map<String,String> stops=i.getMapOfStops();
                            String number = i.getCode();
                            String start = stopsNames[j];
                            String startTime =  stops.get(stopsNames[j]);
                            String end =stopsNames[k];
                            String endTime =stops.get(stopsNames[k]);
                            mat.add( new Match(number,start,startTime,end,endTime));
                        }
                    }
                }
            }
        }
        return mat;
    }
    Match searchByCode(String code){
        Match match=null;
        if(code.equals("")) throw new NullPointerException("Empty input");
        for(Route i:routes){
            String codeI;
            if( code.length()<3){
                codeI=i.getCode().substring(0,3);
               int code1= Integer.parseInt(code);
               int code2 = Integer.parseInt(codeI);
               Log.d("code",""+code1);
               Log.d("code",""+code2);
               if(code1==code2){
                   String number = i.getCode();
                   String start = i.getStops().get(0).get(0);
                   String startTime = i.getStops().get(0).get(1);
                   String end = i.getStops().get(i.getStops().size() - 1).get(0);
                   String endTime = i.getStops().get(i.getStops().size() - 1).get(1);
                   match = new Match(number, start, startTime, end, endTime);
                   break;
               } else continue;
            }
             codeI = i.getCode().length()<4?i.getCode():i.getCode().substring(0,3)+i.getCode().substring(4);
                if (code.equals(codeI)) {
                    String number = i.getCode();
                    String start = i.getStops().get(0).get(0);
                    String startTime = i.getStops().get(0).get(1);
                    String end = i.getStops().get(i.getStops().size() - 1).get(0);
                    String endTime = i.getStops().get(i.getStops().size() - 1).get(1);
                    match = new Match(number, start, startTime, end, endTime);
                    break;
                } else {
                    code = code.substring(0, 3);
                    codeI = i.getCode().substring(0, 3);
                    if (code.equals(codeI)) {
                        String number = i.getCode();
                        String start = i.getStops().get(0).get(0);
                        String startTime = i.getStops().get(0).get(1);
                        String end = i.getStops().get(i.getStops().size() - 1).get(0);
                        String endTime = i.getStops().get(i.getStops().size() - 1).get(1);
                        match = new Match(number, start, startTime, end, endTime);
                        break;
                    }
                }


        }
        if(match==null)throw new IllegalArgumentException("Unexpected code value: "+code);
        return match;
    }
    Route getRouteByMatch(Match match){
        for (Route i :routes) {
            if(i.getCode().equals(match.getCode())) return i;
        }
        return null;
    }
    void hideView(View v ){

        v.startAnimation(fadeout);
        v.setVisibility(View.INVISIBLE);
        v.setTranslationZ(0);
        v.setEnabled(false);

    }
    void hideView(View... v ){
        for (View view : v) {
            hideView(view);
        }
    }
    void showView(View v){
        v.startAnimation(fadein);
        v.setVisibility(View.VISIBLE);
        v.bringToFront();
        v.setTranslationZ(1);
        v.setEnabled(true);
    }
    void showView(View... v ){
        for (View view : v) {
            showView(view);
        }
    }
}
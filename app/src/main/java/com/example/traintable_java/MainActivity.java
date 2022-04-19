package com.example.traintable_java;

import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private RecyclerView listView ;
    private View listHeader;
    private View listHeaderStop;
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
        listView = findViewById(R.id.RV);
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //LinearLayoutManager l =
        mbutton = findViewById(R.id.search);
        from=findViewById(R.id.editTextTextFrom);
        to=findViewById(R.id.editTextTextTo);
        fromTv=findViewById(R.id.textViewFrom);
        toTv=findViewById(R.id.textViewTo);
        toTv=findViewById(R.id.textViewTo);
        editCode=findViewById(R.id.editTextCode);
        codeTv=findViewById(R.id.textViewCode);
        //code_button=findViewById(R.id.button_code_details);
        listHeader=getLayoutInflater().inflate(R.layout.list_header,listView,false);
        listHeaderStop=getLayoutInflater().inflate(R.layout.list_header_stop,listView,false);
        fadeout = AnimationUtils.loadAnimation(this,R.anim.fadeout);
        fadein = AnimationUtils.loadAnimation(this,R.anim.fadein);
        RecyclerView v = new RecyclerView(this);
       hideView(editCode,codeTv);
       listView.setVisibility(View.INVISIBLE);
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
            hideView(to,toTv,from,fromTv,listView);
            showView(editCode,codeTv);
            switcState=true;
        }else  {
            mswitch.setText(R.string.switch_off);
            showView(to,toTv,from,fromTv);
            hideView(editCode,codeTv,listView);
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
        //listView.removeViewAt(0);
        //MatchAdapter adapter = new MatchAdapter(this, arrayOfMatches);
        listView.setAdapter(new MatchAdapterR(arrayOfMatches));
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //listView.addView(listHeader,0);
        listView.setVisibility(View.VISIBLE);
        listView.startAnimation(fadein);
    }
    void showResult(String code){
        loadRoutes();
        Match match=null;
        ArrayList<Match> matches = new ArrayList<>();
        try {
            match = searchByCode(code);
            matches.add(match);
        }
        catch (IllegalArgumentException e){
            Toast.makeText(this,R.string.toast_bad_code,Toast.LENGTH_LONG).show();
            return;
        }
        catch (NullPointerException e){
            Toast.makeText(this,R.string.toast_empty_input_code,Toast.LENGTH_LONG).show();
            return;
        }
       // listView.removeViewAt(0);
       // listView.removeHeaderView(listHeaderStop);
        /*MatchAdapter adapter1 = new MatchAdapter(this,matches);
        listView.setAdapter(adapter1);
        listView.addHeaderView(listHeader);*/

        ArrayList<Stop> n = getStopsList(getRouteByMatch(match));
        listView.setAdapter(new StopAdapterR(n));
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //listView.addView(listHeaderStop,0);
        listView.setVisibility(View.VISIBLE);
        listView.startAnimation(fadein);
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
    ArrayList<Stop> getStopsList(Route obj){
        ArrayList<Stop> s = new ArrayList<>();
            /*obj.getMapOfStops().entrySet().forEach(j->{
                s.add(new Stop(j.getKey(),j.getValue()));
            });*/
            obj.getStops().forEach(i->{
             s.add(new Stop(i.get(0),i.get(1)));
            });
            //s.sort(Comparator.comparing(Stop::getTime));
        s.forEach(i->{
            Log.d("stop",""+i);
        });
        return s;
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
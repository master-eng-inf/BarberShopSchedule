package com.udl.bss.barbershopschedule;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.udl.bss.barbershopschedule.database.Users.BarbersSQLiteHelper;
import com.udl.bss.barbershopschedule.database.Users.UsersSQLiteHelper;
import com.udl.bss.barbershopschedule.utils.BitmapUtils;

public class RegisterActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final int PLACE_PICKER_REQUEST = 2;

    private static final int PERMISSION_EXTERNAL_STORAGE = 1;
    private static final int PICK_IMAGE = 1;

    private GoogleApiClient mGoogleApiClient;

    private boolean isBarber;
    private ImageView imageView;
    private EditText et_name, et_pass, et_mail, et_phone, et_desc, et_age;
    private Bitmap bitmap;
    private Button btn_img;
    private Button btn_placesID;
    private String placesID;
    private byte[] image;
    private Spinner spinner_gender;
    private CardView cv_desc, cv_age, cv_place;

    private long id;
    private SharedPreferences sharedPreferences;
    private BarbersSQLiteHelper bsh;
    private UsersSQLiteHelper ush;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buildGoogleApiClient();

        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        bsh = new BarbersSQLiteHelper(getApplicationContext(), "DBBarbers", null, 1);
        ush = new UsersSQLiteHelper(getApplicationContext(), "DBUsers", null, 1);

        et_name = findViewById(R.id.editText_register_name);
        et_mail = findViewById(R.id.editText_register_mail);
        et_pass = findViewById(R.id.editText_register_password);
        et_phone = findViewById(R.id.editText_register_phone);
        et_desc = findViewById(R.id.editText_register_desc);
        et_age = findViewById(R.id.editText_register_age);
        btn_img = findViewById(R.id.button_form_image);
        Button btn_ok = findViewById(R.id.button_register_ok);
        btn_placesID = findViewById(R.id.button_selectPlacesID);
        imageView = findViewById(R.id.image_form);
        cv_desc = findViewById(R.id.cardview_desc);
        cv_age = findViewById(R.id.cardview_age);
        cv_place = findViewById(R.id.cardview_place);

        Spinner spinner = findViewById(R.id.mode_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner_items, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner_gender = findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter_gender = ArrayAdapter.createFromResource(
                this, R.array.gender_spinner_items, android.R.layout.simple_spinner_item
        );
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(adapter_gender);


        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                PERMISSION_EXTERNAL_STORAGE);
                    }
                } else {
                    Intent pickIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/*");
                    startActivityForResult(pickIntent, PICK_IMAGE);
                }
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });

        btn_placesID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPlacePicker();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        String item = parent.getItemAtPosition(position).toString();
        if (item.equals(getString(R.string.user))) {
            isBarber = false;
            btn_placesID.setVisibility(View.INVISIBLE);
            cv_age.setVisibility(View.VISIBLE);
            cv_desc.setVisibility(View.GONE);
            cv_place.setVisibility(View.GONE);
        } else if (item.equals(getString(R.string.barber))) {
            isBarber = true;
            btn_placesID.setVisibility(View.VISIBLE);
            cv_age.setVisibility(View.GONE);
            cv_desc.setVisibility(View.VISIBLE);
            cv_place.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean registerOk () {

        if (bitmap == null) {
            imageView.setImageResource(R.mipmap.ic_launcher);
            bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            image = BitmapUtils.bitmapToByteArray(bitmap);
        }

        if (isBarber){

            return et_name != null && et_mail != null && et_pass != null && image != null
                    && placesID != null && !et_name.getText().toString().equals("")
                    && !et_pass.getText().toString().equals("") && !et_mail.getText().toString().equals("")
                    && et_phone != null && !et_phone.getText().toString().equals("")
                    && et_desc != null && !et_desc.getText().toString().equals("");
        }

        return et_name != null && et_mail != null && et_pass != null && image != null
                && !et_name.getText().toString().equals("") && !et_pass.getText().toString().equals("")
                && !et_mail.getText().toString().equals("")
                && et_phone != null && !et_phone.getText().toString().equals("")
                && et_age != null && !et_age.getText().toString().equals("");

    }

    private void saveToDatabase () {

        if (registerOk()) {

            ContentValues data = new ContentValues();
            data.put("name", et_name.getText().toString());
            data.put("mail", et_mail.getText().toString());
            data.put("password", et_pass.getText().toString());
            byte[] image = BitmapUtils.bitmapToByteArray(bitmap);
            data.put("image", image);
            data.put("phone", et_phone.getText().toString());
            data.put("gender", ((TextView)spinner_gender.getSelectedView()).getText().toString());


            if (isBarber) {
                data.put("placesID", placesID);
                data.put("description", et_desc.getText().toString());
            } else {
                data.put("age", et_age.getText().toString());
            }

            save(data);

            Toast.makeText(getApplicationContext(), "Registered succesfully", Toast.LENGTH_SHORT).show();

            String mode = isBarber ? getString(R.string.barber) : getString(R.string.user);
            saveToSharedPreferencesAndStart((int)id, et_name.getText().toString(), mode);

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.field_error), Toast.LENGTH_SHORT).show();
        }

    }

    private void save (final ContentValues data) {
        SQLiteDatabase db;

        if (isBarber) {
            db = bsh.getWritableDatabase();
            id = db.insert("Barbers", null, data);
        } else {
            db = ush.getWritableDatabase();
            id = db.insert("Users", null, data);
        }

    }

    private void saveToSharedPreferencesAndStart (int id, String name, String mode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", id);
        editor.putString("user", name);
        editor.putString("mode", mode);
        editor.apply();

        //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch(requestCode) {
            case PICK_IMAGE:
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = intent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();


                        bitmap = BitmapFactory.decodeFile(filePath);
                        if (BitmapUtils.sizeOfBitmap(bitmap) > 9999999) bitmap = BitmapUtils.reduceSize(bitmap);

                        imageView.setImageBitmap(bitmap);
                        image = BitmapUtils.bitmapToByteArray(bitmap);
                    }
                }
                break;

            case PLACE_PICKER_REQUEST:
                if(resultCode == RESULT_OK && intent != null){
                    placesID = PlacePicker.getPlace(this, intent).getId();

                    setMap(PlacePicker.getPlace(this, intent).getName().toString(),
                            PlacePicker.getPlace(this, intent).getLatLng());
                    TextView tv_no_place = findViewById(R.id.textView_no_place);
                    tv_no_place.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void setMap(String name, LatLng location) {
        ViewHolder holder = new ViewHolder();
        holder.mapView = findViewById(R.id.lite_listrow_map);
        holder.title = findViewById(R.id.textView_register_place);

        holder.mapView.setVisibility(View.VISIBLE);

        holder.initializeMapView();
        NamedLocation item = new NamedLocation(name, location);
        holder.mapView.setTag(item);

        if (holder.map != null) {
            setMapLocation(holder.map, item);
        }
        holder.title.setText(item.name);
    }


    private static void setMapLocation(GoogleMap map, NamedLocation data) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.location, 13f));
        map.addMarker(new MarkerOptions().position(data.location));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    private void displayPlacePicker() {
        if( mGoogleApiClient == null || !mGoogleApiClient.isConnected() )
            return;

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult( builder.build( this ), PLACE_PICKER_REQUEST );
        } catch ( GooglePlayServicesRepairableException e ) {
            Log.d( "PlacesAPI Demo", "GooglePlayServicesRepairableException thrown" );
        } catch ( GooglePlayServicesNotAvailableException e ) {
            Log.d( "PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown" );
        }
    }

    private void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient
                .Builder( this )
                .enableAutoManage( this, 0, this )
                .addApi( Places.GEO_DATA_API )
                .addApi( Places.PLACE_DETECTION_API )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if( mGoogleApiClient != null )
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("ON CONNECTION FAILED","Google Places API connection failed with error code:");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    private static class NamedLocation {

        public final String name;
        final LatLng location;

        NamedLocation(String name, LatLng location) {
            this.name = name;
            this.location = location;
        }
    }


    class ViewHolder implements OnMapReadyCallback {

        MapView mapView;
        TextView title;
        GoogleMap map;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(getApplicationContext());
            map = googleMap;
            NamedLocation data = (NamedLocation) mapView.getTag();
            if (data != null) {
                setMapLocation(map, data);
            }
        }

        void initializeMapView() {
            if (mapView != null) {
                mapView.onCreate(null);
                mapView.getMapAsync(this);
            }
        }

    }

}

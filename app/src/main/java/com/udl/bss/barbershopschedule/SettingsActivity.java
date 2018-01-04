package com.udl.bss.barbershopschedule;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActionBar();

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment
            implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


        private static final int PLACE_PICKER_REQUEST = 2;

        private GoogleApiClient mGoogleApiClient;

        private static String BARBER_MODE = "Barber";

        PreferenceScreen pScreen;
        EditTextPreference pName;
        ListPreference pGender;
        EditTextPreference pAge;
        EditTextPreference pEmail;
        EditTextPreference pPhone;
        EditTextPreference pPassword;
        Preference pPlace;
        EditTextPreference pDescription;

        Barber barber;
        Client client;
        SharedPreferences mPrefs;

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            buildGoogleApiClient();

            mPrefs = getContext().getSharedPreferences("USER", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString("user", "");
            final String mode = mPrefs.getString("mode", "");

            pScreen = getPreferenceScreen();
            pName = (EditTextPreference) findPreference("save_name");
            pEmail= (EditTextPreference) findPreference("save_mail");
            pPhone = (EditTextPreference) findPreference("save_phone");
            pPassword = (EditTextPreference) findPreference("save_password");
            pDescription= (EditTextPreference)findPreference("save_description");
            pPlace = findPreference("save_place");
            pGender = (ListPreference) findPreference("gender_list");
            pAge = (EditTextPreference) findPreference("save_age");


            if (mode.equals(BARBER_MODE)) {
                barber = gson.fromJson(json, Barber.class);
                pScreen.removePreference(pAge);

                setContent(barber.getName(), barber.getEmail(), barber.getPhone(),
                        barber.getPassword(), barber.getDescription(),
                        barber.getAddress() + ", " + barber.getCity(),
                        Integer.valueOf(barber.getGender()), null);


                pDescription.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        barber.setDescription(o.toString());
                        APIController.getInstance().updateBarber(barber.getToken(), barber);
                        saveToSharedPreferences((new Gson()).toJson(barber));

                        preference.setSummary(o.toString());
                        Toast.makeText(getContext(), "Description updated successfully", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });


            } else {
                client = gson.fromJson(json, Client.class);
                pScreen.removePreference(pDescription);
                pScreen.removePreference(pPlace);

                setContent(client.getName(), client.getEmail(), client.getPhone(),
                        client.getPassword(), null, null,
                        client.getGender(), String.valueOf(client.getAge()));


                pAge.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        client.setAge(Integer.valueOf(o.toString()));
                        APIController.getInstance().updateClient(client.getToken(), client);
                        saveToSharedPreferences((new Gson()).toJson(client));

                        preference.setSummary(o.toString());
                        Toast.makeText(getContext(), "Age updated successfully", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }

            pName.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(final Preference preference, final Object o) {

                    APIController.getInstance().isUserAvailable(o.toString()).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.getResult()) {

                                if (mode.equals(BARBER_MODE)) {
                                    barber.setName(o.toString());
                                    APIController.getInstance().updateBarber(barber.getToken(), barber);
                                    saveToSharedPreferences((new Gson()).toJson(barber));
                                } else {
                                    client.setName(o.toString());
                                    APIController.getInstance().updateClient(client.getToken(), client);
                                    saveToSharedPreferences((new Gson()).toJson(client));
                                }

                                preference.setSummary(o.toString());

                                Toast.makeText(getContext(), "Name updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Username is not available", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    return true;
                }
            });

            pEmail.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if (mode.equals(BARBER_MODE)) {
                        barber.setEmail(o.toString());
                        APIController.getInstance().updateBarber(barber.getToken(), barber);
                        saveToSharedPreferences((new Gson()).toJson(barber));
                    } else {
                        client.setEmail(o.toString());
                        APIController.getInstance().updateClient(client.getToken(), client);
                        saveToSharedPreferences((new Gson()).toJson(client));
                    }
                    preference.setSummary(o.toString());
                    Toast.makeText(getContext(), "Email updated successfully", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            pPhone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if (mode.equals(BARBER_MODE)) {
                        barber.setPhone(o.toString());
                        APIController.getInstance().updateBarber(barber.getToken(), barber);
                        saveToSharedPreferences((new Gson()).toJson(barber));
                    } else {
                        client.setPhone(o.toString());
                        APIController.getInstance().updateClient(client.getToken(), client);
                        saveToSharedPreferences((new Gson()).toJson(client));
                    }
                    preference.setSummary(o.toString());
                    Toast.makeText(getContext(), "Phone updated successfully", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            pPassword.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if (mode.equals(BARBER_MODE)) {
                        barber.setPassword(o.toString());
                        APIController.getInstance().updateBarber(barber.getToken(), barber);
                        saveToSharedPreferences((new Gson()).toJson(barber));
                    } else {
                        client.setPassword(o.toString());
                        APIController.getInstance().updateClient(client.getToken(), client);
                        saveToSharedPreferences((new Gson()).toJson(client));
                    }
                    Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            pGender.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    int index = ((ListPreference)preference).findIndexOfValue(o.toString());
                    if (mode.equals(BARBER_MODE)) {
                        barber.setGender(String.valueOf(index));
                        APIController.getInstance().updateBarber(barber.getToken(), barber);
                        saveToSharedPreferences((new Gson()).toJson(barber));
                    } else {
                        client.setGender(index);
                        APIController.getInstance().updateClient(client.getToken(), client);
                        saveToSharedPreferences((new Gson()).toJson(client));
                    }
                    preference.setSummary(o.toString());
                    Toast.makeText(getContext(), "Gender updated successfully", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            pPlace.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    displayPlacePicker();
                    return true;
                }
            });

        }

        private void setContent (String name, String mail, String phone, String password,
                                 String description, String place, int gender, String age) {
            pName.setText(name);
            bindPreferenceSummaryToValue(pName);

            pEmail.setText(mail);
            bindPreferenceSummaryToValue(pEmail);

            pPhone.setText(phone);
            bindPreferenceSummaryToValue(pPhone);

            pPassword.setText(password);

            if (description != null) {
                pDescription.setText(description);
                bindPreferenceSummaryToValue(pDescription);
            }

            if (place != null) pPlace.setSummary(place);

            pGender.setValueIndex(gender);
            bindPreferenceSummaryToValue(pGender);

            if (age != null) {
                pAge.setText(age);
                bindPreferenceSummaryToValue(pAge);
            }

        }

        private void saveToSharedPreferences (String user) {
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            prefsEditor.putString("user", user);
            prefsEditor.apply();
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            super.onActivityResult(requestCode, resultCode, intent);

            switch(requestCode) {
                case PLACE_PICKER_REQUEST:
                    if(resultCode == RESULT_OK && intent != null){
                        String placesID = PlacePicker.getPlace(getActivity(), intent).getId();
                        String address = (String) PlacePicker.getPlace(getActivity(), intent).getAddress();

                        String[] strArray = address.replaceAll("\\'", " ").split(",");

                        barber.setAddress(strArray[0]+","+strArray[1]);
                        barber.setCity(strArray[2].replaceFirst("\\s",""));
                        barber.setPlacesID(placesID);

                        APIController.getInstance().updateBarber(barber.getToken(), barber);
                        saveToSharedPreferences((new Gson()).toJson(barber));

                        pPlace.setSummary(barber.getAddress() + ", " + barber.getCity());

                        Toast.makeText(getActivity(), "Place updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }


        private void displayPlacePicker() {
            if( mGoogleApiClient == null || !mGoogleApiClient.isConnected() )
                return;

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult( builder.build( getActivity() ), PLACE_PICKER_REQUEST );
            } catch ( GooglePlayServicesRepairableException e ) {
                Log.d( "PlacesAPI Demo", "GooglePlayServicesRepairableException thrown" );
            } catch ( GooglePlayServicesNotAvailableException e ) {
                Log.d( "PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown" );
            }
        }

        private void buildGoogleApiClient(){
            mGoogleApiClient = new GoogleApiClient
                    .Builder( getActivity() )
                    /*.enableAutoManage(getContext(), 0, this )*/
                    .addApi( Places.GEO_DATA_API )
                    .addApi( Places.PLACE_DETECTION_API )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .build();
        }

        @Override
        public void onStart() {
            super.onStart();
            if( mGoogleApiClient != null )
                mGoogleApiClient.connect();
        }

        @Override
        public void onStop() {
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


    }

}

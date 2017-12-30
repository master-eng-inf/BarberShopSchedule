package com.udl.bss.barbershopschedule.serverCommunication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.domain.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gerard on 26/12/17.
 */

public class APIController {

    private static APIController instance;

    private APIController() {}

    public static synchronized APIController getInstance(){
        if(instance == null){
            instance = new APIController();
        }
        return instance;
    }




    /* Session Controller */

    public Task<String> logInUser(String username, String password){
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().logInUser(username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        Log.i("APISERVER", s);
                        tcs.setResult(s);
                    } else {
                        tcs.setResult(null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Log in user ERROR");
            }
        });
        return tcs.getTask();
    }





    /* User Controller */

    public Task<Boolean> isUserAvailable(String username){
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().isUserAvailable(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        Log.i("APISERVER", s);
                        tcs.setResult(Boolean.valueOf(s));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Is user available ERROR");
            }
        });
        return tcs.getTask();
    }




    /* Barber Shop Controller */

    public Task<List<Barber>> getAllBarbers(String token){
        final TaskCompletionSource<List<Barber>> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getAllBarbers(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        List<Barber> barberList = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            Barber barber = new Barber(
                                    json.getInt("id"),
                                    json.getString("name"),
                                    json.getString("email"),
                                    json.getString("places_id"),
                                    json.getString("password"),
                                    json.getString("telephone"),
                                    json.getString("gender"),
                                    json.getString("description"),
                                    json.getString("address"),
                                    json.getString("city"),
                                    null);
                            barberList.add(barber);
                        }

                        Log.i("APISERVER", s);
                        tcs.setResult(barberList);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get all barbers ERROR");
            }
        });

        return tcs.getTask();
    }


    public Task<Barber> getBarberById(String token, String id){
        final TaskCompletionSource<Barber> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getBarberById(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();

                        JSONObject json = new JSONObject(s);
                        Barber barber = new Barber(
                                json.getInt("id"),
                                json.getString("name"),
                                json.getString("email"),
                                json.getString("places_id"),
                                json.getString("password"),
                                json.getString("telephone"),
                                json.getString("gender"),
                                json.getString("description"),
                                json.getString("address"),
                                json.getString("city"),
                                null);

                        Log.i("APISERVER", s);
                        tcs.setResult(barber);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get barber by id ERROR");
            }
        });

        return tcs.getTask();
    }


    public void createBarber(Barber barber) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("password", barber.getPassword());
        requestBody.put("email", barber.getEmail());
        requestBody.put("telephone", barber.getPhone());
        requestBody.put("name", barber.getName());
        requestBody.put("address", barber.getAddress());
        requestBody.put("city", barber.getCity());
        requestBody.put("description", barber.getDescription());
        requestBody.put("places_id", barber.getPlacesID());

        ApiUtils.getService().createBarber(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.i("APISERVER", "Create barber OK");
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Create barber ERROR");
            }
        });

    }




    /* Client controller */

    public void createClient(Client client) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("password", client.getPassword());
        requestBody.put("email", client.getEmail());
        requestBody.put("telephone", client.getPhone());
        requestBody.put("name", client.getName());
        requestBody.put("gender", String.valueOf(client.getGender()));
        requestBody.put("age", String.valueOf(client.getAge()));


        ApiUtils.getService().createClient(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.i("APISERVER", "Create client OK");
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Create client ERROR");
            }
        });

    }


    public Task<Client> getClientById(String token, String id){
        final TaskCompletionSource<Client> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getClientById(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();

                        JSONObject json = new JSONObject(s);
                        Client client = new Client(
                                json.getInt("id"),
                                json.getString("name"),
                                json.getString("email"),
                                json.getString("password"),
                                json.getString("telephone"),
                                json.getInt("gender"),
                                json.getInt("age"),
                                null);

                        Log.i("APISERVER", s);
                        tcs.setResult(client);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get client by id ERROR");
            }
        });

        return tcs.getTask();
    }



    /* Appointment Controller */

    public Task<List<Appointment>> getAppointmentsByBarber(String token, String id){
        final TaskCompletionSource<List<Appointment>> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getAppointmentByBarber(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        List<Appointment> appointmentList = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            Appointment appointment = new Appointment(
                                    json.getInt("id"),
                                    json.getInt("client_id"),
                                    json.getInt("barber_shop_id"),
                                    json.getInt("service_id"),
                                    json.getInt("promotion_id"),
                                    json.getString("date"));
                            appointmentList.add(appointment);
                        }

                        Log.i("APISERVER", s);
                        tcs.setResult(appointmentList);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get appointments by barber ERROR");
            }
        });

        return tcs.getTask();
    }

    public Task<List<Appointment>> getAppointmentsByClient(String token, String id){
        final TaskCompletionSource<List<Appointment>> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getAppointmentByClient(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        List<Appointment> appointmentList = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            Appointment appointment = new Appointment(
                                    json.getInt("id"),
                                    json.getInt("client_id"),
                                    json.getInt("barber_shop_id"),
                                    json.getInt("service_id"),
                                    json.getInt("promotion_id"),
                                    json.getString("date"));
                            appointmentList.add(appointment);
                        }

                        Log.i("APISERVER", s);
                        tcs.setResult(appointmentList);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get appointments by client ERROR");
            }
        });

        return tcs.getTask();
    }


    /* Service Controller */

    public void createService(String token, BarberService service) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("barber_shop_id", String.valueOf(service.getBarberShopId()));
        requestBody.put("name", service.getName());
        requestBody.put("price", String.valueOf(service.getPrice()));
        requestBody.put("duration", String.valueOf((int)service.getDuration()));

        ApiUtils.getService().createService(token, requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.i("APISERVER", "Create service OK" + response.toString()+" "+response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Create service ERROR");
            }
        });

    }

    public Task<BarberService> getServiceById(String token, String id){
        final TaskCompletionSource<BarberService> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getServiceById(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        JSONObject json = new JSONObject(s);
                        BarberService service = new BarberService(
                                json.getInt("id"),
                                json.getInt("barber_shop_id"),
                                json.getString("name"),
                                json.getInt("price"),
                                json.getDouble("duration"));

                        Log.i("APISERVER", s);
                        tcs.setResult(service);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get service by id ERROR");
            }
        });

        return tcs.getTask();
    }


    public Task<List<BarberService>> getServicesByBarber(String token, String id){
        final TaskCompletionSource<List<BarberService>> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getServicesByBarber(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        List<BarberService> serviceList = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);

                            BarberService service = new BarberService(
                                    json.getInt("id"),
                                    json.getInt("barber_shop_id"),
                                    json.getString("name"),
                                    json.getInt("price"),
                                    json.getDouble("duration"));
                            serviceList.add(service);
                        }

                        Log.i("APISERVER", s);
                        tcs.setResult(serviceList);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get promotions by barber ERROR");
            }
        });

        return tcs.getTask();
    }





    /* Promotion Controller */

    public Task<Promotion> getPromotionById(String token, String id){
        final TaskCompletionSource<Promotion> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getPromotionById(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();

                        JSONObject json = new JSONObject(s);

                        int promotional = Boolean.valueOf(json.getString("is_promotional")) ? 1 : 0;

                        Promotion promotion = new Promotion(
                                json.getInt("id"),
                                json.getInt("barber_shop_id"),
                                json.getInt("service_id"),
                                json.getString("name"),
                                json.getString("description"),
                                promotional);

                        Log.i("APISERVER", s);
                        tcs.setResult(promotion);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get promotion by id ERROR");
            }
        });

        return tcs.getTask();
    }

    public Task<List<Promotion>> getPromotionsByBarber(String token, String id){
        final TaskCompletionSource<List<Promotion>> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getPromotionsByBarber(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        List<Promotion> promotionList = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);

                            int promotional = Boolean.valueOf(json.getString("is_promotional")) ? 1 : 0;

                            Promotion promotion = new Promotion(
                                    json.getInt("id"),
                                    json.getInt("barber_shop_id"),
                                    json.getInt("service_id"),
                                    json.getString("name"),
                                    json.getString("description"),
                                    promotional);
                            promotionList.add(promotion);
                        }

                        Log.i("APISERVER", s);
                        tcs.setResult(promotionList);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get promotions by barber ERROR");
            }
        });

        return tcs.getTask();
    }


    public Task<List<Promotion>> getPromotionalPromotions(String token){
        final TaskCompletionSource<List<Promotion>> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getAllPromotionalPromotions(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    ResponseBody body = response.body();
                    if (body != null){
                        String s = body.string();
                        List<Promotion> promotionList = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);

                            int promotional = Boolean.valueOf(json.getString("is_promotional")) ? 1 : 0;

                            Promotion promotion = new Promotion(
                                    json.getInt("id"),
                                    json.getInt("barber_shop_id"),
                                    json.getInt("service_id"),
                                    json.getString("name"),
                                    json.getString("description"),
                                    promotional);
                            promotionList.add(promotion);
                        }

                        Log.i("APISERVER", s);
                        tcs.setResult(promotionList);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get promotional promotions ERROR");
            }
        });

        return tcs.getTask();
    }


    /* Review Controller */

    public void createReview(String token, Review review) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("client_id", String.valueOf(review.getClientId()));
        requestBody.put("barber_shop_id", String.valueOf(review.getBarberShopId()));
        requestBody.put("mark", String.valueOf(review.getMark()));
        requestBody.put("description", review.getDescription());
        requestBody.put("date", review.getDate());

        ApiUtils.getService().createReview(token, requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.i("APISERVER", "Create review OK");
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Create review ERROR");
            }
        });

    }

    public void updateReview(String token, Review review) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("client_id", String.valueOf(review.getClientId()));
        requestBody.put("barber_shop_id", String.valueOf(review.getBarberShopId()));
        requestBody.put("mark", String.valueOf(review.getMark()));
        requestBody.put("description", review.getDescription());
        requestBody.put("date", review.getDate());

        ApiUtils.getService().updateReview(token, requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.i("APISERVER", "Update review OK");
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Update review ERROR");
            }
        });
    }

    public void removeReview(String token, String barber_id, String client_id) {
        ApiUtils.getService().removeReview(token,barber_id, client_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.i("APISERVER", "Review removed");
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Remove review ERROR");
            }
        });
    }

    public Task<List<Review>> getReviewsByBarber(String token, String id) {
        final TaskCompletionSource<List<Review>> tcs = new TaskCompletionSource<>();

        ApiUtils.getService().getReviewsByBarberId(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                ResponseBody body = response.body();
                if (body != null) {
                    try {
                        String s = body.string();
                        List<Review> reviewList = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(s);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);

                            Review review = new Review(
                                    json.getInt("client_id"),
                                    json.getInt("barber_shop_id"),
                                    json.getString("description"),
                                    json.getDouble("mark"),
                                    json.getString("date"));
                            reviewList.add(review);
                        }

                        Log.i("APISERVER", s);
                        tcs.setResult(reviewList);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("APISERVER", "Get reviews by barber ERROR");
            }
        });

        return tcs.getTask();
    }
}

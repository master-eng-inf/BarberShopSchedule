package com.udl.bss.barbershopschedule.serverCommunication;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gerard on 26/12/17.
 */

public interface APIService {

    /* Session Controller */

    @GET("sessions/login/{username}/{password}")
    Call<ResponseBody> logInUser(@Path(value = "username", encoded = true) String username,
                                 @Path(value = "password", encoded = true) String password);


    /* User Controller */

    @GET("users/isAvailable/{username}")
    Call<ResponseBody> isUserAvailable(@Path(value = "username", encoded = true) String username);



    /* Appointment Controller */

    @GET("appointments/list/{token}")
    Call<ResponseBody> getAllAppointments(@Path(value = "token", encoded = true) String token);

    @GET("appointments/list/barberShop/{barber_shop_id}/{token}")
    Call<ResponseBody> getAppointmentByBarber(@Path(value = "token", encoded = true) String token,
                                              @Path(value = "barber_shop_id", encoded = true) String id);

    @GET("appointments/list/client/{client_id}/{token}")
    Call<ResponseBody> getAppointmentByClient(@Path(value = "token", encoded = true) String token,
                                              @Path(value = "client_id", encoded = true) String id);

    @GET("appointments/appointment/{appointment_id}/{token}")
    Call<ResponseBody> getAppointmentById(@Path(value = "token", encoded = true) String token,
                                          @Path(value = "appointment_id", encoded = true) String id);


    @POST("appointments/insertAppointment/{token}")
    Call<ResponseBody> createAppointment(@Path(value = "token", encoded = true) String token,
                                         @Body Map<String, String> appointment);

    @POST("appointments/deleteAppointment/{appointment_id}/{token}")
    Call<ResponseBody> removeAppointment(@Path(value = "token", encoded = true) String token,
                                         @Path(value = "appointment_id", encoded = true) String id);


    /* Barber Shop Controller */

    @GET("barberShops/list/{token}")
    Call<ResponseBody> getAllBarbers(@Path(value = "token", encoded = true) String token);

    @GET("barberShops/barberShop/{barber_shop_id}/{token}")
    Call<ResponseBody> getBarberById(@Path(value = "token", encoded = true) String token,
                                     @Path(value = "barber_shop_id", encoded = true) String id);


    @Headers("Content-Type: application/json")
    @POST("barberShops/insertBarberShop")
    Call<ResponseBody> createBarber(@Body Map<String, String> barber);

    @Headers("Content-Type: application/json")
    @POST("barberShops/updateBarberShop/{token}")
    Call<ResponseBody> updateBarber(@Path(value = "token", encoded = true) String token,
                                    @Body Map<String, String> barber);

    @POST("barberShops/deleteBarberShop/{barber_shop_id}/{token}")
    Call<ResponseBody> removeBarber(@Path(value = "token", encoded = true) String token,
                                    @Path(value = "barber_shop_id", encoded = true) String id);




    /* Client controller */

    @GET("clients/list/{token}")
    Call<ResponseBody> getAllClients(@Path(value = "token", encoded = true) String token);

    @GET("clients/client/{client_id}/{token}")
    Call<ResponseBody> getClientById(@Path(value = "token", encoded = true) String token,
                                     @Path(value = "client_id", encoded = true) String id);


    @Headers("Content-Type: application/json")
    @POST("clients/insertClient")
    Call<ResponseBody> createClient(@Body Map<String, String> client);

    @Headers("Content-Type: application/json")
    @POST("clients/updateClient/{token}")
    Call<ResponseBody> updateClient(@Path(value = "token", encoded = true) String token,
                                    @Body Map<String, String> client);

    @POST("clients/deleteClient/{client_id}/{token}")
    Call<ResponseBody> removeClient(@Path(value = "token", encoded = true) String token,
                                    @Path(value = "client_id", encoded = true) String id);


    /* Promotion controller */

    @GET("promotions/list/{token}")
    Call<ResponseBody> getAllPromotions(@Path(value = "token", encoded = true) String token);

    @GET("promotions/promotional/list/{token}")
    Call<ResponseBody> getAllPromotionalPromotions(@Path(value = "token", encoded = true) String token);

    @GET("promotions/list/barberShop/{barber_shop_id}/{token}")
    Call<ResponseBody> getPromotionByBarber(@Path(value = "token", encoded = true) String token,
                                            @Path(value = "barber_shop_id", encoded = true) String id);

    @GET("promotions/promotion/{promotion_id}/{token}")
    Call<ResponseBody> getPromotionById(@Path(value = "token", encoded = true) String token,
                                        @Path(value = "promotion_id", encoded = true) String id);


    @Headers("Content-Type: application/json")
    @POST("promotions/insertPromotion/{token}")
    Call<ResponseBody> createPromotion(@Path(value = "token", encoded = true) String token,
                                       @Body Map<String, String> promotion);

    @Headers("Content-Type: application/json")
    @POST("promotions/updatePromotion/{token}")
    Call<ResponseBody> updatePromotion(@Path(value = "token", encoded = true) String token,
                                       @Body Map<String, String> promotion);

    @POST("promotions/deletePromotion/{promotion_id}/{token}")
    Call<ResponseBody> removePromotion(@Path(value = "token", encoded = true) String token,
                                       @Path(value = "promotion_id", encoded = true) String id);



    /* Review controller */

    @GET("reviews/barberShop/{barber_shop_id}/list/{token}")
    Call<ResponseBody> getReviewsByBarberId(@Path(value = "token", encoded = true) String token,
                                            @Path(value = "barber_shop_id", encoded = true) String id);


    @Headers("Content-Type: application/json")
    @POST("reviews/insertReview/{token}")
    Call<ResponseBody> createReview(@Path(value = "token", encoded = true) String token,
                                    @Body Map<String, String> review);

    @Headers("Content-Type: application/json")
    @POST("reviews/updateReview/{token}")
    Call<ResponseBody> updateReview(@Path(value = "token", encoded = true) String token,
                                    @Body Map<String, String> review);

    @POST("reviews/deleteReview/client_id/{client_id}/barber_shop_id/{barber_shop_id}/{token}")
    Call<ResponseBody> removeReview(@Path(value = "token", encoded = true) String token,
                                    @Path(value = "barber_shop_id", encoded = true) String barber_id,
                                    @Path(value = "client_id", encoded = true) String client_id);




    /* Schedule controller */

    @GET("schedules/barberShop/{barber_shop_id}/list/{token}")
    Call<ResponseBody> getScheduleByBarberId(@Path(value = "token", encoded = true) String token,
                                             @Path(value = "barber_shop_id", encoded = true) String id);


    @Headers("Content-Type: application/json")
    @POST("schedules/insertSchedule/{token}")
    Call<ResponseBody> createSchedule(@Path(value = "token", encoded = true) String token,
                                      @Body Map<String, String> schedule);

    @Headers("Content-Type: application/json")
    @POST("schedules/updateSchedule/{token}")
    Call<ResponseBody> updateSchedule(@Path(value = "token", encoded = true) String token,
                                      @Body Map<String, String> schedule);





    /* Service controller */

    @GET("services/list/{token}")
    Call<ResponseBody> getAllServices(@Path(value = "token", encoded = true) String token);

    @GET("services/list/barberShop/{barber_shop_id}/{token}")
    Call<ResponseBody> getServiceByBarber(@Path(value = "token", encoded = true) String token,
                                          @Path(value = "barber_shop_id", encoded = true) String id);

    @GET("services/service/{service_id}/{token}")
    Call<ResponseBody> getServiceById(@Path(value = "token", encoded = true) String token,
                                      @Path(value = "service_id", encoded = true) String id);


    @Headers("Content-Type: application/json")
    @POST("services/insertService/{token}")
    Call<ResponseBody> createService(@Path(value = "token", encoded = true) String token,
                                     @Body Map<String, String> service);

    @Headers("Content-Type: application/json")
    @POST("services/updateService/{token}")
    Call<ResponseBody> updateService(@Path(value = "token", encoded = true) String token,
                                     @Body Map<String, String> service);

    @POST("services/deleteService/{service_id}/{token}")
    Call<ResponseBody> removeService(@Path(value = "token", encoded = true) String token,
                                     @Path(value = "service_id", encoded = true) String barber_id);



    /* Special day controller */

    @GET("specialDays/barberShop/{barber_shop_id}/list/{token}")
    Call<ResponseBody> getSpecialDayByBarberId(@Path(value = "token", encoded = true) String token,
                                               @Path(value = "barber_shop_id", encoded = true) String id);


    @Headers("Content-Type: application/json")
    @POST("specialDays/insertSpecialDay/{token}")
    Call<ResponseBody> createSpecialDay(@Path(value = "token", encoded = true) String token,
                                        @Body Map<String, String> specialDay);

    @Headers("Content-Type: application/json")
    @POST("specialDays/updateSpecialDay/{token}")
    Call<ResponseBody> updateSpecialDay(@Path(value = "token", encoded = true) String token,
                                        @Body Map<String, String> specialDay);

    @POST("specialDays/deleteSpecialDay/{barber_shop_id}/date/{date}/{token}")
    Call<ResponseBody> removeSpecialDay(@Path(value = "token", encoded = true) String token,
                                        @Path(value = "barber_shop_id", encoded = true) String barber_id,
                                        @Path(value = "date", encoded = true) String date);



}

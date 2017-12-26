package com.udl.bss.barbershopschedule.serverCommunication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gerard on 26/12/17.
 */

public interface APIService {

    /* Session Controller */

    @GET("sessions/login/{user_id}")
    Call<ResponseBody> getSessionToken(@Path(value = "user_id", encoded = true) String user_id);


    /* Appointment Controller */

    @GET("appointments/list/{token}")
    Call<ResponseBody> getAllAppointments(@Path(value = "token", encoded = true) String token);

    @GET("appointments/appointment/{appointment_id}/{token}")
    Call<ResponseBody> getAppointmentById(@Path(value = "token", encoded = true) String token,
                                          @Path(value = "appointment_id", encoded = true) String id);


    /* Barber Shop Controller */

    @GET("barberShops/list/{token}")
    Call<ResponseBody> getAllBarbers(@Path(value = "token", encoded = true) String token);

    @GET("barberShops/barberShop/{barber_shop_id}/{token}")
    Call<ResponseBody> getBarberById(@Path(value = "token", encoded = true) String token,
                                     @Path(value = "barber_shop_id", encoded = true) String id);

    /* Client controller */

    @GET("clients/list/{token}")
    Call<ResponseBody> getAllClients(@Path(value = "token", encoded = true) String token);

    @GET("clients/client/{client_id}/{token}")
    Call<ResponseBody> getClientById(@Path(value = "token", encoded = true) String token,
                                     @Path(value = "client_id", encoded = true) String id);


    /* Promotion controller */

    @GET("promotions/list/{token}")
    Call<ResponseBody> getAllPromotions(@Path(value = "token", encoded = true) String token);

    @GET("promotions/promotion/{promotion_id}/{token}")
    Call<ResponseBody> getPromotionById(@Path(value = "token", encoded = true) String token,
                                     @Path(value = "promotion_id", encoded = true) String id);



    /* Review controller */

    @GET("reviews/barberShop/{barber_shop_id}/list/{token}")
    Call<ResponseBody> getReviewsByBarberId(@Path(value = "token", encoded = true) String token,
                                        @Path(value = "barber_shop_id", encoded = true) String id);



    /* Schedule controller */

    @GET("schedules/barberShop/{barber_shop_id}/list/{token}")
    Call<ResponseBody> getScheduleByBarberId(@Path(value = "token", encoded = true) String token,
                                            @Path(value = "barber_shop_id", encoded = true) String id);



    /* Service controller */

    @GET("services/list/{token}")
    Call<ResponseBody> getAllServices(@Path(value = "token", encoded = true) String token);

    @GET("services/service/{service_id}/{token}")
    Call<ResponseBody> getServiceById(@Path(value = "token", encoded = true) String token,
                                        @Path(value = "service_id", encoded = true) String id);


    /* Special day controller */

    @GET("specialDays/barberShop/{barber_shop_id}/list/{token}")
    Call<ResponseBody> getSpecialDayByBarberId(@Path(value = "token", encoded = true) String token,
                                             @Path(value = "barber_shop_id", encoded = true) String id);

}

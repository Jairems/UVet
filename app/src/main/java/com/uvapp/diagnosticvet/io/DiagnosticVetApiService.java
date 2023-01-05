package com.uvapp.diagnosticvet.io;

import com.uvapp.diagnosticvet.model.Disease;
import com.uvapp.diagnosticvet.model.Species;
import com.uvapp.diagnosticvet.model.Symptom;
import com.uvapp.diagnosticvet.model.System;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DiagnosticVetApiService {

    // Diseases

    @GET("diseases")
    Call<ArrayList<Disease>> getDiseases();

    @GET("species/{id}/diseases")
    Call<ArrayList<Disease>> getDiseasesBySpecies(@Path("id") int species_id);

    @GET("system/{id}/diseases")
    Call<ArrayList<Disease>> getDiseasesBySystem(@Path("id") int system_id);


    // Species and systems

    @GET("species")
    Call<ArrayList<Species>> getSpecies();

    @GET("systems")
    Call<ArrayList<System>> getSystemsBySpecies(@Query("species_id") int species_id);


    // Symptoms

    @GET("species/{id}/symptoms")
    Call<ArrayList<Symptom>> getSymptomsBySpecies(@Path("id") int species_id);

    @GET("system/{id}/symptoms")
    Call<ArrayList<Symptom>> getSymptomsBySystem(@Path("id") int system_id);


    // Diagnosis

    @GET("system/{id}/diagnosis")
    Call<ArrayList<Disease>> getDiagnosisBySystem(
            @Path("id") int system_id,
            @Query("symptoms[]") ArrayList<Integer> selected_symptoms
    );

    @GET("species/{id}/diagnosis")
    Call<ArrayList<Disease>> getDiagnosisBySpecies(
            @Path("id") int species_id,
            @Query("symptoms[]") ArrayList<Integer> selected_symptoms
    );
}

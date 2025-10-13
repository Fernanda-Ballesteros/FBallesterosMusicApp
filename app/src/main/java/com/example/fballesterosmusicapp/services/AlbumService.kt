package com.example.fballesterosmusicapp.services
import  com.example.fballesterosmusicapp.models.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {
    @GET("albums")
    suspend fun getAllAlbums(): List<Album>


    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: String): Album
}


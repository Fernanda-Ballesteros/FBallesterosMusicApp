package com.example.fballesterosmusicapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.fballesterosmusicapp.models.Album
import com.example.fballesterosmusicapp.services.AlbumService
import com.example.fballesterosmusicapp.ui.theme.AccentColor
import com.example.fballesterosmusicapp.ui.theme.Background
import com.example.fballesterosmusicapp.ui.theme.DarkPurple
import com.example.fballesterosmusicapp.ui.theme.PrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val BASE_URL = "https://music.juanfrausto.com/api/"
    var albums by remember { mutableStateOf(listOf<Album>()) }
    var loading by remember { mutableStateOf(true) }




    LaunchedEffect(true) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AlbumService::class.java)
            val result = withContext(Dispatchers.IO) { service.getAllAlbums() }
            albums = result
            Log.e("HomeScreen", result.toString())
            loading = false
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error al cargar albums: ${e.message}")
            loading = false
        }
    }


    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    } else {
        Scaffold(
            containerColor = PrimaryColor,
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(DarkPurple)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = albums.firstOrNull()?.image ?: "",
                            contentDescription = "Album actual",
                            modifier = Modifier
                                .size(55.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = albums.firstOrNull()?.title ?: "Reproduciendo ahora",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = albums.firstOrNull()?.artist ?: "",
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )
                        }

                        IconButton(
                            onClick = {  },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = DarkPurple,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PrimaryColor)
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {


                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .height(130.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.1f),
                                        AccentColor
                                    )
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.White
                                )
                            }

                            Text(
                                text = "Good Morning!",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Text(
                                text = "Hola Fer",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }


                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = "Albums", color = Color.White, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "See more", color = DarkPurple, fontWeight = FontWeight.Bold)
                    }
                }

                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        items(albums) { album ->
                            Box(
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(Color.White.copy(alpha = 0.2f))
                                    .clickable {
                                        navController.navigate(AlbumDetailScreenRoute(id = album.id))
                                    },
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                AsyncImage(
                                    model = album.image,
                                    contentDescription = album.title,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(DarkPurple.copy(alpha = 0.8f))
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = album.title,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            )
                                            Text(
                                                text = album.artist,
                                                color = Color.LightGray,
                                                fontSize = 8.sp
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .clip(CircleShape)
                                                .background(Color.White),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = "Play",
                                                tint = DarkPurple,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Recently Played",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "See more", color = DarkPurple, fontWeight = FontWeight.Bold)
                    }
                }


                items(albums) { album ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Background)
                            .padding(16.dp)
                            .clickable {
                                navController.navigate(AlbumDetailScreenRoute(id = album.id))
                            }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = album.image,
                                contentDescription = album.title,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = album.title,
                                    color = DarkPurple,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = album.artist,
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = DarkPurple
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

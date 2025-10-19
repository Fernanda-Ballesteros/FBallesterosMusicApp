package com.example.fballesterosmusicapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.fballesterosmusicapp.models.Album
import com.example.fballesterosmusicapp.services.AlbumService
import com.example.fballesterosmusicapp.ui.theme.DarkPurple
import com.example.fballesterosmusicapp.ui.theme.PrimaryColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AlbumDetailScreen(albumId: String, navController: NavController) {
    val BASE_URL = "https://music.juanfrausto.com/api/"
    var album by remember { mutableStateOf<Album?>(null) }
    var loading by remember { mutableStateOf(true) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(albumId) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AlbumService::class.java)
            val result = withContext(Dispatchers.IO) { service.getAlbumById(albumId) }
            album = result
            Log.d("AlbumDetailScreen", "Album loaded: $result")
            loading = false
        } catch (e: Exception) {
            Log.e("AlbumDetailScreen", "Error al cargar album: ${e.message}")
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
                            model = album?.image ?: "",
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
                                text = album?.title ?: "Album Title",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = album?.artist ?: "Artist",
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )
                        }

                        IconButton(
                            onClick = { },
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
            ) {

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) {

                        AsyncImage(
                            model = album?.image ?: "",
                            contentDescription = album?.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.3f),
                                            Color.Black.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }

                            IconButton(
                                onClick = { isFavorite = !isFavorite },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = Color.White
                                )
                            }
                        }


                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(24.dp)
                        ) {
                            Text(
                                text = album?.title ?: "Album Title",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = album?.artist ?: "Artist",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )


                            Row(
                                modifier = Modifier.padding(top = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                IconButton(
                                    onClick = {  },
                                    modifier = Modifier
                                        .size(56.dp)
                                        .background(Color(0xFF7B5FCC), CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }


                                IconButton(
                                    onClick = { },
                                    modifier = Modifier
                                        .size(56.dp)
                                        .background(Color.White, CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Shuffle",
                                        tint = DarkPurple,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }
                    }
                }


                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(20.dp)
                    ) {
                        Column {
                            Text(
                                text = "About this album",
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Un álbum sinfónico que mezcla elementos de música clásica con death metal melódico.",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Artist: ",
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = album?.artist ?: "Artist Name",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }


                items((1..10).toList()) { trackNumber ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = album?.image ?: "",
                                contentDescription = album?.title,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 12.dp)
                            ) {
                                Text(
                                    text = "${album?.title ?: "Album"} • Track $trackNumber",
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = album?.artist ?: "Artist",
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }

                            IconButton(
                                onClick = {  },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "More",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
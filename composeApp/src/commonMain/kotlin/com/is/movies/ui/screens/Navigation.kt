package com.`is`.movies.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.`is`.movies.data.MoviesRepository
import com.`is`.movies.data.MoviesService
import com.`is`.movies.ui.screens.detail.DetailScreen
import com.`is`.movies.ui.screens.detail.DetailViewModel
import com.`is`.movies.ui.screens.home.HomeScreen
import com.`is`.movies.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import moviesapp.composeapp.generated.resources.Res
import moviesapp.composeapp.generated.resources.api_key
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun Navigation() {
    val navController = rememberNavController()
    val repository = rememberMoviesRepository()
    val viewModel = viewModel { HomeViewModel(repository) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate("details/${movie.id}")
                },
                viewModel = viewModel { HomeViewModel(repository) }
            )
        }
        composable(
            route = "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                viewModel = viewModel { DetailViewModel(movieId, repository) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun rememberMoviesRepository(
    apiKey: String = stringResource(Res.string.api_key),
): MoviesRepository = remember {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                parameters.append("api_key", apiKey)
            }
        }
    }
    MoviesRepository(MoviesService(client))
}

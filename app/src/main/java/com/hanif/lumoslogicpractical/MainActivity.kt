package com.hanif.lumoslogicpractical

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hanif.lumoslogicpractical.ui.details.ProductDetailScreen
import com.hanif.lumoslogicpractical.ui.details.ProductDetailViewModel
import com.hanif.lumoslogicpractical.ui.products.ProductListScreen
import com.hanif.lumoslogicpractical.ui.products.ProductViewModel
import com.hanif.lumoslogicpractical.ui.theme.LumoslogicPracticalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LumoslogicPracticalTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "product_list") {
        composable("product_list") {
            val viewModel: ProductViewModel = hiltViewModel()
            ProductListScreen(
                viewModel = viewModel,
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                }
            )
        }
        composable(
            route = "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: return@composable
            val viewModel: ProductDetailViewModel = hiltViewModel()
            ProductDetailScreen(
                productId = productId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

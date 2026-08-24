package com.webdevavi.chemlabsimulator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.webdevavi.chemlabsimulator.audio.SoundEffectsManager
import com.webdevavi.chemlabsimulator.data.repository.SavedExperimentRepository
import com.webdevavi.chemlabsimulator.ui.screens.experiments.ExperimentLibraryScreen
import com.webdevavi.chemlabsimulator.ui.screens.experiments.GuidedExperimentScreen
import com.webdevavi.chemlabsimulator.ui.screens.home.HomeScreen
import com.webdevavi.chemlabsimulator.ui.screens.inventory.ChemicalInventoryScreen
import com.webdevavi.chemlabsimulator.ui.screens.saved.SavedExperimentsScreen
import com.webdevavi.chemlabsimulator.ui.screens.splash.SplashScreen
import com.webdevavi.chemlabsimulator.ui.screens.workspace.WorkspaceScreen
import com.webdevavi.chemlabsimulator.ui.screens.workspace.WorkspaceViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Workspace : Screen("workspace")
    object Experiments : Screen("experiments")
    object GuidedExperiment : Screen("guided_experiment/{experimentId}") {
        fun createRoute(experimentId: String) = "guided_experiment/$experimentId"
    }
    object Inventory : Screen("inventory")
    object Saved : Screen("saved")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val soundEffectsManager = remember { SoundEffectsManager(context) }
    val savedExperimentRepository = remember { SavedExperimentRepository(context) }
    val workspaceViewModel: WorkspaceViewModel = viewModel {
        WorkspaceViewModel(savedExperimentRepository, soundEffectsManager)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToWorkspace = { navController.navigate(Screen.Workspace.route) },
                onNavigateToExperiments = { navController.navigate(Screen.Experiments.route) },
                onNavigateToInventory = { navController.navigate(Screen.Inventory.route) },
                onNavigateToSaved = { navController.navigate(Screen.Saved.route) }
            )
        }

        composable(Screen.Workspace.route) {
            WorkspaceScreen(
                viewModel = workspaceViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Experiments.route) {
            ExperimentLibraryScreen(
                onNavigateBack = { navController.popBackStack() },
                onSelectExperiment = { experimentId ->
                    navController.navigate(Screen.GuidedExperiment.createRoute(experimentId))
                }
            )
        }

        composable(
            route = Screen.GuidedExperiment.route,
            arguments = listOf(navArgument("experimentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val expId = backStackEntry.arguments?.getString("experimentId") ?: "exp_neutralization"
            GuidedExperimentScreen(
                experimentId = expId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Inventory.route) {
            ChemicalInventoryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Saved.route) {
            SavedExperimentsScreen(
                repository = savedExperimentRepository,
                onNavigateBack = { navController.popBackStack() },
                onLoadState = { state ->
                    workspaceViewModel.loadState(state)
                    navController.navigate(Screen.Workspace.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }
    }
}


package io.github.mmolosay.datalayercommunication.ui.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

/**
 * [NavGraph] with all destinations, that may occur after startup phase of application had passed.
 */
@RootNavGraph
@NavGraph
annotation class StartedNavGraph(
    val start: Boolean = false,
)
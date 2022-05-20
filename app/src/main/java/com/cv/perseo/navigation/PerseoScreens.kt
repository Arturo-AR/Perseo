package com.cv.perseo.navigation

sealed class PerseoScreens(
    val route: String,
    val id: Int
) {
    /**
     * Screens that need permission
     */
    object Dashboard: PerseoScreens(
        route = "dashboard",
        id = 1
    )
    object Subscribers: PerseoScreens(
        route = "subscribers",
        id = 2
    )
    object ServiceOrders: PerseoScreens(
        route = "serviceOrders",
        id = 3
    )
    object Compliance: PerseoScreens(
        route = "compliance",
        id = 4
    )
    object MyServiceOrders: PerseoScreens(
        route = "myServiceOrders",
        id = 5
    )
    object Inventory: PerseoScreens(
        route = "inventory",
        id = 6
    )
    object ServicesCords: PerseoScreens(
        route = "servicesCords",
        id = 7
    )

    /**
     * Screens that don't need permissions
     */

    object SplashScreen: PerseoScreens(
        route = "splashScreen",
        id = 0
    )
    object LoginScreen: PerseoScreens(
        route = "loginScreen",
        id = 0
    )
    object EnterpriseSelector: PerseoScreens(
        route = "enterpriseSelector",
        id = 0
    )
    object OSDetails: PerseoScreens(
        route = "oSDetails",
        id = 0
    )
}
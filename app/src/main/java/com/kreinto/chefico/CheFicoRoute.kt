package com.kreinto.chefico

/**
 * Route for app navigation.
 *
 */

object CheFicoRoute {
  abstract class Route(val path: String)
  abstract class RouteArgs(base: String, vararg args: String) : Route("$base/${args.joinToString("/") { "{$it}" }}") {
    fun path(vararg args: String): String = path.replaceAfter(delimiter = "/", replacement = args.joinToString("/") { "$it" })
  }

  object Back : Route("back")

  object Dashboard : Route("dashboard")

  object Settings : Route("sesttings")

  object Maps : Route("maps")

  object Camera : Route("camera")

  object PoiList : Route("poiList")

  object PoiCreation : Route("poiCreation")

  object Login : Route("login")

  object Signin : Route("signIn")

  object Account : Route("account")

  object PlantDetail : RouteArgs("plantDetail", "imageName", "organ")

  object PoiDetail : RouteArgs("poiDetail", "poiId")
}
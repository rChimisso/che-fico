package com.kreinto.chefico

/**
 * Route for app navigation.
 *
 * @property path Navigation path.
 * @property arg Optional navigation argument.
 */
sealed class Route(val path: String, val arg: String = "") {
  object Back : Route("back")
  object Dashboard : Route("dashboard")
  object Settings : Route("settings")
  object Maps : Route("maps")
  object Camera : Route("camera")
  object PoiList : Route("poilist")
  object PoiDetail : Route("poidetail/{poiId}", "poiId")
  object PoiCreation : Route("poicreation")
  object PlantDetail : Route("plantdetail/{imageName}", "imageName")
  object Login : Route("login")
  object Signin : Route("signin")
  object Account : Route("account")

  fun route(arg: String): String {
    return path.replace("{${this.arg}}", arg)
  }
}
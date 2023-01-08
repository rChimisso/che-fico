package com.kreinto.chefico

sealed class Route(val route: String, val arg: String = "") {
  object Back : Route("back")
  object Dashboard : Route("dashboard")
  object Settings : Route("settings")
  object Maps : Route("maps")
  object Camera : Route("camera")
  object PoiList : Route("poilist")
  object PoiDetail : Route("poidetail/{poiId}", "poiId")
  object PoiCreation : Route("poicreation")
  object PlantDetail : Route("plantdetail/{imageName}", "imageName")

  fun route(arg: String): String {
    return route.replace("{${this.arg}}", arg)
  }
}
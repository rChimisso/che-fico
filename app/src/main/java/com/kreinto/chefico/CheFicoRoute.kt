package com.kreinto.chefico

/**
 * Route for views navigation.
 */
object CheFicoRoute {
  /**
   * Base route.
   *
   * @property path Navigation path.
   */
  abstract class Route(val path: String)

  /**
   * [Route] with arguments.
   *
   * @constructor
   * Encodes the arguments into the path.
   *
   * @param base Base navigation path.
   * @param args Navigation rguments.
   */
  abstract class RouteArgs(base: String, vararg args: String) : Route("$base/${args.joinToString("/") { "{$it}" }}") {
    fun path(vararg args: String): String = path.replaceAfter("/", args.joinToString("/") { it })
  }

  /**
   * Back [Route].
   */
  object Back : Route("back")

  /**
   * Dashboard [Route].
   */
  object Dashboard : Route("dashboard")

  /**
   * Settings [Route].
   */
  object Settings : Route("settings")

  /**
   * Maps [Route].
   */
  object Maps : Route("maps")

  /**
   * Camera [Route].
   */
  object Camera : Route("camera")

  /**
   * POI List [Route].
   */
  object PoiList : Route("poiList")

  /**
   * POI creating [Route].
   */
  object PoiCreation : Route("poiCreation")

  /**
   * Login [Route].
   */
  object Login : Route("login")

  /**
   * Signin [Route].
   */
  object Signin : Route("signIn")

  /**
   * Account [Route].
   */
  object Account : Route("account")

  /**
   * Account Edit [Route].
   */
  object AccountEdit : Route("edit")

  /**
   * Blacklist [Route].
   */
  object Blacklist : Route("blacklist")

  /**
   * Plant Detail [Route with arguments][RouteArgs].
   */
  object PlantDetail : RouteArgs("plantDetail", "imageName", "organ")

  /**
   * POI Detail [Route with arguments][RouteArgs].
   */
  object PoiDetail : RouteArgs("poiDetail", "poiId")
}
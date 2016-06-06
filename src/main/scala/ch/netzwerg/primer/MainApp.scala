package ch.netzwerg.primer

import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactDOM}
import org.scalajs.dom._

import scala.scalajs.js

object MainApp extends js.JSApp {

  // 01: A component which outputs a static message

  val Hello = ReactComponentB[Unit]("Hello")
    .render($ => <.div("Hello world"))
    .build

  // 02: A component which outputs a static message read from String properties

  val HelloWithProps = ReactComponentB[String]("HelloWithProps")
    .render($ => <.div($.props))
    .build

  // 03: A component which constructs and outputs a message from two values it reads from custom Person properties

  case class Person(name: String, age: Int)

  val HelloWithCaseClassProps = ReactComponentB[Person]("HelloWithCaseClassProps")
    .render($ => <.div(s"Hello ${$.props.name}, your age is ${$.props.age}"))
    .build

  // 04: A component which outputs a message read from a mutable State object

  case class State04(msg: String)

  class Backend04($: BackendScope[Unit, State04]) {
    def render(state: State04) = <.div(state.msg)
  }

  val HelloWithState = ReactComponentB[Unit]("HelloWithState")
    .initialState(State04("Hello state"))
    .renderBackend[Backend04]
    .build

  // 05: A component which toggles mutable State and renders a label accordingly

  case class State05(checked: Boolean)

  class Backend05($: BackendScope[Unit, State05]) {

    def toggle: Callback = $.modState(s => s.copy(!s.checked))

    def render(state: State05) = <.div(
      <.input.checkbox(^.checked := state.checked, ^.onChange --> toggle),
      "State: " + (if (state.checked) "Checked" else "Unchecked")
    )

  }

  val HelloWithMutableState = ReactComponentB[Unit]("HelloWithMutableState")
    .initialState(State05(false))
    .renderBackend[Backend05]
    .build

  // A component which serves as a container for all our demo components
  val All = ReactComponentB[Unit]("All")
    .render($ => <.div(
      Hello(),
      HelloWithProps("Hello again"),
      HelloWithCaseClassProps(Person("barack", 54)),
      HelloWithState(),
      HelloWithMutableState()
    ))
    .build

  def main() = {
    // Looks up the dom element into which we will render our react components
    val parent = document.getElementById("root")
    // Actually render the 'All' react component (which contains all other demo components)
    ReactDOM.render(All(), parent)
  }

}

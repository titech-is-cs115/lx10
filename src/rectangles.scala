package lx10

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Point2D
import scalafx.scene.Scene
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle

object RectangleDemo extends JFXApp {

  val rectangle = new Rectangle {
    fill = Blue
    // strokeWidth = 100
    // stroke = Red
  }

  val drawingPane = new Pane { children += rectangle }

  var p1 = new Point2D(0, 0); var p2 = new Point2D(0, 0)

  def update(_p1: Point2D, _p2: Point2D) {
    p1 = _p1
    p2 = _p2
    rectangle.x = math.min(p1.x, p2.x)
    rectangle.y = math.min(p1.y, p2.y)
    rectangle.width = math.abs(p1.x - p2.x)
    rectangle.height = math.abs(p1.y - p2.y)
  }

  drawingPane.onMousePressed = { (e: MouseEvent) =>
    println(f"Pressed: (${e.x}, ${e.y})")
    val p = new Point2D(e.x, e.y)
    update(p, p)
  }

  drawingPane.onMouseDragged = { (e: MouseEvent) =>
    println(f"Dragged: (${e.x}, ${e.y})")
    update(p1, new Point2D(e.x, e.y))
  }

  stage = new PrimaryStage {
    title = "Draw Rectangle Demo"
    scene = new Scene(500, 500) {
      root = drawingPane
    }
  }
}

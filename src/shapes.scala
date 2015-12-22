package lx10

import scala.math.{abs,min}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Point2D
import scalafx.scene.Scene
import scalafx.scene.control.{ToggleButton, ToolBar, ToggleGroup}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{Pane, BorderPane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Ellipse, Line, Rectangle}

object Shape3Demo extends JFXApp {

  type Updator = (Point2D, Point2D) => Unit

  val rectangle = new Rectangle { fill = Color.rgb(255, 0, 0, 0.5) }

  val updateRectangle: Updator = { (p1: Point2D, p2: Point2D) =>
    rectangle.x      = math.min(p1.x, p2.x)
    rectangle.y      = math.min(p1.y, p2.y)
    rectangle.width  = math.abs(p1.x - p2.x)
    rectangle.height = math.abs(p1.y - p2.y)
  }

  val ellipse = new Ellipse { fill = Color.rgb(0, 255, 0, 0.5) }

  def updateEllipse: Updator = { (p1: Point2D, p2: Point2D) =>
    ellipse.centerX = (p1.x + p2.x) / 2
    ellipse.centerY = (p1.y + p2.y) / 2
    ellipse.radiusX = abs(p1.x - ellipse.centerX())
    ellipse.radiusY = abs(p1.y - ellipse.centerY())
  }

  val line = new Line { stroke = Color.rgb(0, 0, 255, 0.5); strokeWidth = 3 }

  def updateLine: Updator = { (p1: Point2D, p2: Point2D) =>
    line.startX = p1.x; line.startY = p1.y
    line.endX   = p2.x; line.endY   = p2.y
  }

  var update: Updator = updateRectangle

  val drawingPane = new Pane { children ++= Seq(rectangle, ellipse, line) }

  val flagFrame = new Rectangle {
    width = 60; height = 40
    fill   = Color.White
  }

  val flagCircle = new Ellipse {
    centerX = 30; centerY = 20
    radiusX = 12; radiusY = 12
    fill    = Color.rgb(0xbd, 0x1e, 0x48)
  }

  val updateFlag: Updator = { (p1: Point2D, p2: Point2D) =>
  }

  drawingPane.children ++= Seq(flagFrame, flagCircle)

  var p1 = new Point2D(0, 0)

  drawingPane.onMousePressed = { e: MouseEvent =>
    val p = new Point2D(e.x, e.y)
    p1 = p
    update(p, p)
  }

  drawingPane.onMouseDragged = { e: MouseEvent =>
    update(p1, new Point2D(e.x, e.y))
  }

  val alignToggleGroup = new ToggleGroup()

  alignToggleGroup.selectedToggle.onChange {
    val id = alignToggleGroup.selectedToggle().asInstanceOf[javafx.scene.control.ToggleButton].id()
    update = id match {
      case "rectangle" => updateRectangle
      case "ellipse"   => updateEllipse
      case "line"      => updateLine
    }
  }

  val toolButtons: List[ToggleButton] = List(
    new ToggleButton {
      id = "rectangle"
      graphic = new Rectangle {
        fill = rectangle.fill()
        width = 32; height = 32
      }
      toggleGroup = alignToggleGroup
    },
  
    new ToggleButton {
      id = "ellipse"
      graphic = new Ellipse {
        fill = ellipse.fill()
        radiusX = 16; radiusY = 16
      }
      toggleGroup = alignToggleGroup
    },

    new ToggleButton {
      id = "line"
      graphic = new Line {
        stroke = line.stroke()
        startX = 0; startY = 0
        endX = 28;  endY = 28
        strokeWidth = 3
      }
      toggleGroup = alignToggleGroup
    },
    
    new ToggleButton {
      graphic = new Pane {
        children = Seq(new Rectangle { width = 32; height = 32; fill = Color.Transparent },
                      new Rectangle { x = 1; y = 6; width = 30; height = 20; fill = Color.White },
                      new Ellipse { centerX = 15; centerY = 16; radiusX = 6; radiusY = 6; fill = Color.Red })
      }
    })

  stage = new PrimaryStage {
    title = "Multiple Shape Drawing Demo"
    scene = new Scene(600, 400) {
      root = new BorderPane {
        top = new ToolBar { content = toolButtons }
        center = drawingPane
      }
    }
  }
}

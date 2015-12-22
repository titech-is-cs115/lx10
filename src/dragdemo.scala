package lx10

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group,Scene}
import scalafx.scene.canvas.{Canvas}
import scalafx.scene.input.MouseEvent

/**
 * キャンバス（描画領域）上でのマウスドラッグの扱い方の例
 **/

object DragDemo extends JFXApp {
  // size: キャンバスの大きさ
  val size = 500
  // canvas: キャンバス
  val canvas = new Canvas(size, size)

  /**
   * キャンバスに設定された onMousePressed メソッド
   * このメソッドは所定の場所でマウスのボタンを押し下げたときに自動的に呼び出される．
   * 引数としてはマウスの操作を表現するさまざまな情報を格納する MouseEvent オブジェクトをとる．MouseEvent から情報を得ることで，マウス操作がなされた位置，同時にキーボードのキーが押されたか否か，クリック回数などの情報が得られる．
   *
   * 詳しくは，ScalaFXのMouseEventクラスのマニュアルを参照．
   * http://docs.scalafx.googlecode.com/hg/scalafx-1.0/scaladoc/index.html#scalafx.scene.input.MouseEvent
   **/
  canvas.onMousePressed  = { (e: MouseEvent) =>
    println(f"Pressed:  (${e.x}, ${e.y})")
  }

  /**
   * キャンバスに設定された onMouseReleased メソッド
   * canvas.onMousePressed メソッドと同様に，押し下げたマウスボタンを手放したときに自動的に呼ばれる．
   **/
  canvas.onMouseReleased = { (e: MouseEvent) =>
    println(f"Released: (${e.x}, ${e.y})")
  }

  stage = new PrimaryStage {
    title = "Mouse Dragging Demo"
    width = size
    height = size
    scene = new Scene {
      root = new Group(canvas)
    }
  }
}

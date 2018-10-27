//		線分，点の描画方法（２次元）

public class ex1_1{	
	public static void main(String[] args){

		Win2D	W=new Win2D("線分，点の描画", 400, 350);	//	ウインドウ生成
		W.drawAxis();               						//	座標軸の表示

		Vector[]	p=new Vector[]{	new Vector( 50, 50),			//	点配列
								new Vector(100, 50),
								new Vector(100,100) };

		Line	l=new Line( p );							//	線分の記述
					
		W.setColor( COLor.BLUE );							//	描画色の設定
		W.draw ( l );										//	線分の描画

		W.setColor( COLor.RED );							//	描画色の設定
		W.point(new Vector(50,-50) );						//	点の描画
	}
}

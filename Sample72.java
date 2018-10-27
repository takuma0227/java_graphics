//回転スイープによる立体（ワイヤーフレーム）を描画する
//Surfaceクラスを使用する

public class Sample72 {
	public static void main(String[] args) {
		Win3D W1 = new Win3D("ワイヤーフレーム", 1000, 350, COLor.WHITE);
		W1.drawAxis();
		W1.setColor(COLor.BLACK);
		
		Line La = new Line(new Vector( 0, 0, 100), new Vector(50, 0, 0));	//円錐の素（直線）の生成
		Surface Sf = Surface.revolve_z(La, 20);							//円錐（ポリゴン集合）の生成
		W1.draw(Surface._mul(TMatrix.move(0, -150, 0), Sf));				//円錐の描画
		
		La = Line._mul(50, Line.circle_y(10, 0, 180));					//球体の素（半円）の生成
		Sf = Surface.revolve_z(La, 10);									//球体（ポリゴン集合）の生成
		W1.draw(Surface._mul(TMatrix.move(0,150,0), Sf));					//球体の描画
		
		La = Line._mul(20, Line.circle_y(8, 0, 360));						//トーラスの素（円）の生成
		Sf = Surface.revolve_z(Line._mul(TMatrix.move(50, 0, 0),La),20);	//トーラスの生成
		W1.draw(Sf);														//トーラスの描画
		
																		//線分図形（直線）の生成
		La = new Line(new Vector[] {
				new Vector(0, 0, 0), new Vector(0, 0, 20),
				new Vector(80, 0, 20), new Vector(80, 0, 100),
				new Vector(100, 0, 100), new Vector(100, 0, 0),
				new Vector(0, 0, 0)
		});
		Sf = Surface.revolve_z(La, 20);									//回転スイープによる回転体の生成
		W1.draw(Surface._mul(TMatrix.move(0, 350, 0), Sf));				//回転体の描画
	}
}

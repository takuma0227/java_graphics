//平行移動スイープによる立体（ワイヤーフレーム）を描画する
//Surfaceクラスを使用する

public class Sample71 {
	public static void main(String[] args){
		Win3D W1  = new Win3D("ワイヤーフレーム",650,550,COLor.WHITE);
		W1.drawAxis();
		W1.setColor(COLor.BLACK);
		
		//正方形（直線）の生成
		Line La = new Line(new Vector[] {
							new Vector( 0, 0, 0), new Vector( 0, 0,50),
							new Vector(50, 0,50), new Vector(50, 0, 0),
							new Vector( 0, 0, 0)
		});
		Line Lb = new Line(new Vector( 0, 0, 0), new Vector( 0,50, 0));	//スイープの線分の生成
		Surface Sf = Surface.sweep_xz(La, Lb);								//スイープによる立方体の生成
		W1.draw(Surface._mul(TMatrix.move(0,-100,0), Sf));					//立方体の描画
		
		//線分図形（直線）の生成
		La = new Line(new Vector[] {
					  new Vector( 0, 0, 0), new Vector( 0, 0,20),
					  new Vector(90, 0,20), new Vector(90, 0,100),
					  new Vector(100, 0,100), new Vector(100, 0, 0),
					  new Vector(0,0,0)
		});
		Lb = new Line(new Vector(0,0,0), new Vector(0,100,0));				//スイープの線分の生成
		Sf = Surface.sweep_xz(La, Lb);										//スイープによる立体の生成
		W1.draw(Surface._mul(TMatrix.move(0,100,0), Sf));					//立体の描画
	}
}

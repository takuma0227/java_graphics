//回転スイープによる立体（ワイヤーフレーム）を描画する
//Surfaceクラスを使用する

public class Kadai3d {
	public static void main(String[] args) {
		Win3D W1 = new Win3D("ワイヤーフレーム", 1000, 1000, COLor.WHITE);
		W1.drawAxis();
		W1.setColor(COLor.BLACK);
		Surface Sf;
		Line La, Lb;
		int hsum,t,h;
		Polygon p;
		

			for(int i=1; i>-2;i--) {
				for(int j=-1;j<2;j++) {
					hsum = 0; t = 0;	h = 60;
					for(int k = 0; k < 20; k++) {
						//線分図形（直線）の生成
						La = new Line(new Vector[] {
								new Vector(0, 0, hsum), new Vector(0, 0, hsum),
								new Vector(h, 0, hsum), new Vector(h, 0, hsum + h),
								new Vector(h, 0, hsum + h), new Vector(h, 0, hsum),
								new Vector(0, 0, hsum)
						});
						Sf = Surface.revolve_z(La, 20);									//回転スイープによる回転体の生成
						W1.draw(Surface._mul(TMatrix.move(j*300, i*300, 0), Sf));				//回転体の描画
						
						hsum += h;
						h -= 4;
					}
				}
			}


/*
			for(int i=10; i<=1000;i+=10) {
				//図形生成
				La = new Line(new Vector[]{
					new Vector(0, 0, 0), new Vector(0, 0, i),
					new Vector(680+i, 0, i), new Vector(680+i,0,0),
					new Vector(0, 0, 0)
				});
				Lb = new Line(new Vector(0,0,0),new Vector(0,640+i,0));	//スイープの線分の生成
				Sf = Surface.sweep_xz(La, Lb);								//スイープによる図形の生成
				W1.draw(Surface._mul(TMatrix.move(-340,-340, -i+10), Sf));			//図形の描画
			}
*/
	}
}
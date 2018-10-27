public class Samball {
	public static void main(String[] args) {	//球体を表示する（レイトレーシング法）

		Ball Ba = new Ball(new Vector(0,0,0), 100);	//球体の記述
		Vector L0 = new Vector(0, 500, 500);	//照明光の記述
		Vector V0 = new Vector(500, 500, 500);	//視点の記述
		Win3D W1 = new Win3D("球体の描画（レイトレーシング）", 400, 500, COLor.WHITE);
		W1.setView(V0);

		for(int x=0; x<W1.size_x; x++) {
			for(int y=0; y<W1.size_y; y++) {
				Vector V = (Vector._sub(W1.world(new Vector(x, y, -W1.dv)), V0)).unit();	//視線ベクトルの計算
				Ray VR = new Ray(V0, V);	//視線の記述
				Vector P = new Vector();	//交点座標
				Vector N = new Vector();
				if(Ba.hit(VR, P, N)<Ray.INFINITY) {	//視線が球体と交わるかの判定
					Vector L = (Vector._sub(P, L0)).unit();	//照明光の方向ベクトル
					W1.setColor(VR.shading(L, N, COLor.RED));	//描画色の設定
					W1.w.point(x, y);	//screen座標で描画
				}
			}
		}
	}
}

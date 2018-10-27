public class Samballs {
	public static void main(String[] args) {	//球体を表示する（レイトレーシング法）
		
		int num = 10;

		Ball Ba[] = new Ball[num];
		COLor C[] = new COLor[num];
		
		for(int i=0; i<num; i++) {
			Ba[i] = new Ball(new Vector(200-i*10,200- i*10, 0),200- i*10);	//球体の記述
			C[i] = new COLor(1-0.1*i, 0.1*i, 0.1*i);
		}
		Vector L0 = new Vector(0, 800, 500);	//照明光の記述
		Vector V0 = new Vector(1000,1000, 0);	//視点の記述
		Win3D W1 = new Win3D("球体の描画（レイトレーシング）", 800, 800);
		W1.setView(V0);

		for(int x=0; x<W1.size_x; x++) {
			for(int y=0; y<W1.size_y; y++) {
				Vector V = (Vector._sub(W1.world(new Vector(x, y, -W1.dv)), V0)).unit();	//視線ベクトルの計算
				Ray VR = new Ray(V0, V);	//視線の記述
				Vector P = new Vector();	//交点座標
				Vector N = new Vector();
				Vector L = (Vector._sub(P, L0)).unit();	//照明光の方向ベクトル
				COLor  Cs = COLor.WHITE;
				for(int i=0; i<num;i++) {
					if(Ba[i].hit(VR, P, N)<Ray.INFINITY) {	//視線が球体と交わるかの判定
						Cs = VR.shading(L, N, C[i]);	//球体iの色
						Ball t = Ba[i];
						for(int j=i+1;j<num;j++) {
							if(Ba[j].hit(VR, P, N)<t.hit(VR, P, N)) {	//他の球体と交わるかの判定
								Cs = VR.shading(L, N, C[j]);	//他の球体が手前にある場合その色
								t = Ba[j];
							}
						}
					}
				}
				W1.setColor(Cs);	//描画色の設定
				W1.w.point(x, y);	//screen座標で描画
			}
		}
	}
}

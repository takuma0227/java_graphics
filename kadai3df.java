public class kadai3df {
	static int X_SIZE = 900, Y_SIZE = 900;	//ウィンドウサイズ
	static double[][] z_buff = new double[X_SIZE][Y_SIZE];	//zバッファの定義


	public static void main(String[] args) {

		for(int x=0; x < X_SIZE; x++) 		//zバッファの初期化
			for(int y = 0; y<Y_SIZE;y++) z_buff[x][y] = -1e30;

		Win3D W1 = new Win3D("フォンシェーディング", X_SIZE, Y_SIZE, COLor.WHITE);

		Surface Sf;
		Line La, Lb;
		int hsum,t,h;
		Polygon p;

		Vector pv = new Vector(900,900,900);	//視点の記述
		Vector li = new Vector(0, 900, 1200);	//照明光の記述（座標）
		W1.setView(pv);
		W1.drawAxis();
		W1.setColor(COLor.BLACK);

		hsum = -100; t = 0;	h = 64;
		for(int k = 0; k < 20; k++) {
			//線分図形（直線）の生成
			La = new Line(new Vector[] {
				new Vector(0, 0, hsum), new Vector(0, 0, hsum),
				new Vector(h, 0, hsum), new Vector(h, 0, hsum + h),
				new Vector(h, 0, hsum + h), new Vector(h, 0, hsum),
				new Vector(0, 0, hsum)
			});
			Sf = Surface.revolve_z(La, 20);

			for(int i = 0; i < Sf.n; i++)
				paint(W1, Sf.getPolygon(i), Sf.getNormal(i), li, COLor.CYAN);	//回転体の描画

			hsum += h;
			h -= 4;
		}
	}

	//	ポリゴンを塗りつぶす
	static void paint(Win3D W1, Polygon Pf, Polygon Pn, Vector li, COLor ca) {

		int top = 0, bot = 0, lef = 0, rig = 0, n = Pf.p.length - 1;	//上下左右の添字、頂点数
		Vector[] pp = new Vector[n];
		for(int i =0; i<n;i++) {
			pp[i] = W1.screen(Pf.p[i]);	//screen座標の計算
			if(pp[top].y > pp[i].y) lef = rig = top = i;	//上部の添字
			if(pp[bot].y < pp[i].y) bot = i;				//下部の添字
		}

		for(int y=(int)pp[top].y; y <= (int)pp[bot].y; y++) {
			if(y<0 || Y_SIZE <= y) continue;
			if((int)pp[(lef+1)%n].y<=y) lef = (lef+1)%n;	//	左上の添え字
			if((int)pp[(rig-1+n)%n].y<=y) rig = (rig-1+n)%n;	//右上の添え字
			Vector p1 = pp[lef], p2 = pp[(lef+1)%n];		//左上、左下の座標
			Vector p0 = pp[rig], p3 = pp[(rig-1+n)%n];		//右上、右下の座標
			Vector n1 = Pn.p[lef], n2 = Pn.p[(lef+1)%n];	//左上、左下の法線ベクトル
			Vector n0 = Pn.p[rig], n3 = Pn.p[(rig-1+n)%n];	//右上、右下の法線ベクトル
			Vector p4 = sup(p1,p2,y-(int)p1.y, (int)p2.y-y);	//y方向の補間
			Vector n4 = sup(n1, n2, y-(int)p1.y, (int)p2.y-y);
			Vector p5 = sup(p3,p0,(int)p3.y-y, y-(int)p0.y);
			Vector n5 = sup(n3, n0, (int)p3.y-y, y-(int)p0.y);
			for(int x = (int)p4.x; x<=(int)p5.x;x++) {	//x方向の補間
				if(x<0 || X_SIZE <=x) continue;
				Vector P = sup(p4,p5,x-(int)p4.x,(int)p5.x-x);	//描画点座標の計算
				Vector N = sup(n4, n5, x-(int)p4.x, (int)p5.x-x).unit();	//法線ベクトルの計算	
				if(z_buff[x][y]<P.z) {
					Vector v = (Vector._sub(W1.world(P), W1.v0)).unit();	//視線ベクトルの計算
					Vector l = (Vector._sub(W1.world(P), li)).unit();		//照明ベクトルの計算
					W1.setColor(shading(v,l,N,ca));
					W1.point(x,y);	//screen座標で描画
					z_buff[x][y] = P.z;
				}

			}

		}
	}

	//反射光を計算する　v:視線ベクトル l:照明光の方向ベクトル n:表面の法線ベクトル
	static COLor shading(Vector v, Vector l, Vector n, COLor ca) {
		double kd = 0.7, ks = 0.7, ke =0.3;	//拡散反射係数、鏡面反射係数、環境光
			Vector r = Vector._sub(l, Vector._mul(2*(Vector._mul(l, n)), n));	//反射方向ベクトル
			return COLor._add(COLor._add(COLor._mul(kd*Math.max(Vector._mul(Vector._mul(-1, n), l), 0), ca),	//拡散反射光
			COLor._mul(ks*Math.pow(Math.max(Vector._mul(Vector._mul(-1, r), v), 0), 20), COLor.WHITE)),	//鏡面反射光
			COLor._mul(ke, ca));	//環境光

	}

	static Vector sup(Vector p1, Vector p2, double a1, double a2) {	//p1, p2を補間する
		if(a1+a2 ==0) return p1;
		return Vector._div((Vector._add(Vector._mul(a2, p1), Vector._mul(a1, p2))), (a1+a2));

	}
}
public class Kadai3d2 {
	public static void main(String[] args) {
		Win3D W1 = new Win3D("ワイヤーフレーム", 1000, 1000, COLor.WHITE);
		
		Surface Sf;
		Line La, Lb;
		int hsum,t,h;
		Polygon p;
		Vector v,n;
		
		Vector pv = new Vector(900,900,900);	//視点の記述
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
			
			for(int m=0;m<Sf.n;m++) {
				p = Sf.getPolygon(m);
				v = (Vector._sub(p.p[0], pv)).unit();
				
				n = (Vector._out(Vector._sub(p.p[0],p.p[2]), Vector._sub(p.p[1],p.p[3]))).unit();
				
				if(Vector._mul(n,v )<=0) W1.draw(p);	//内積が負の時ポリゴン枠を描画
			}
						
			hsum += h;
			h -= 4;
		}
	}
}
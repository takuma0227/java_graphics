//	３次元ウインドウ用のクラス

public	class	Win3D{		

	int		size_x=400,	size_y=350;							//	ウインドウサイズ
	Vector	org=new Vector(size_x/2,size_y/2);				//  原点の位置（スクリーン座標）
	TMatrix	t,it;											//	視点変換行列,tの逆行列
	Vector	v0;												//	視点の位置
	double	dv;												//	原点までの距離
	Screen	w;

	public	Win3D(String title, int ix, int iy)	{			//	ウインドウを開く
			init(title, ix, iy, COLor.WHITE);
	}
	public	Win3D(String title, int ix, int iy, COLor c){	//	ウインドウを開く
			init(title, ix, iy, c);
	}

	public void init(String title, int x, int y,COLor c){							
		size_x=x;			size_y=y;
		org.x=size_x/2+.5;	org.y=size_y/2-.5;
		w=new Screen(title, x, y, c);
		setView(30,30,10000);
	}

	public	void	setOrigin(int x, int y){				//	原点の位置設定
			org=new Vector(x+.5,y-.5,0);	
	}
	public	void	setView(double a, double e){			//視点方向の設定(d:原点からの距離)
			setView(a,e,100000);
	}
	public	void	setView(double a, double e, double d){	//視点方向の設定(d:原点からの距離)
			dv=d;					
			t=TMatrix._mul(TMatrix._mul(TMatrix.perspect(d),TMatrix.rot_x(e-90)),TMatrix.rot_z(-90-a));
			it=TMatrix._mul(TMatrix.rot_z(90+a),TMatrix.rot_x(90-e));
			v0=Vector._mul(TMatrix._mul(it,(new Vector(0,0,1))),d);
	}
	public	void	setView(Vector p){				   		//	視点位置を設定
			dv=p.len();				
			Vector	wa=p.unit();
			v0=p;
			if((wa.x==0)&&(wa.y==0)){
				t=TMatrix.perspect(p.len());
				return;	
			}
			double	wd=Math.sqrt(wa.x*wa.x+wa.y*wa.y);
			t=TMatrix._mul(TMatrix._mul(TMatrix.perspect(p.len()),TMatrix.rot_x(-wd,wa.z)),TMatrix.rot_z(-wa.x/wd,-wa.y/wd));
			it=TMatrix._mul(TMatrix.rot_z(wa.x/wd,-wa.y/wd),TMatrix.rot_x(wd,wa.z));
	}
	public	void	setColor(COLor c){						//	描画色を設定
			w.setColor(c);
	}

	//	座標の変換
	public	Vector	screen(Vector p){						//	スクリーン座標に変換する
			Vector	a=TMatrix._mul(t,p);
			double	s=t.t[3][0]*p.x+t.t[3][1]*p.y+t.t[3][2]*p.z+t.t[3][3];
			return	new Vector( org.x+a.x/s, org.y-a.y/s, a.z );
	}
	public	Vector	world(Vector p){ 						//	視野(view)座標をworld座標に変換する
			double	sz=-p.z/dv;
			return  TMatrix._mul(it,(new Vector((p.x-org.x)*sz,(-p.y+org.y)*sz,p.z+dv)));
	}
	public	Vector	world(Vector p, TMatrix m, double d){ 	//	world座標に変換する
			double	sz=-p.z/d;
			return  TMatrix._mul(m,(new Vector((p.x-org.x)*sz,(-p.y+org.y)*sz,p.z+d)));
	}
	
	//	２次元座標での操作
	public	void	point(double x, double y)	{			//	点を描画
			w.point(x,y);
	}
	public 	COLor	getColor(double x, double y){			//	色を返す	
			return w.getColor(x,y);
	}

	//	３次元座標での操作
	public	void	point(Vector p)	{						//	点を描画
			Vector	a=screen(p);
			w.point(a.x,a.y);
	}
	public	void	draw (Vector p0, Vector p1)	{			//	線分を描画
			Vector	a=screen(p0),	b=screen(p1);
			w.draw( a.x,a.y, b.x,b.y );
	}
	public	void	draw (Vector[] p){						//	線分を描画
			int		n=p.length;
			int[]	xp=new int[n],	yp=new int[n];
			for(int i=0; i<n; i++){
				Vector	pp=screen(p[i]);
				xp[i]=(int)pp.x;	yp[i]=(int)pp.y;
			}
			w.draw(xp,yp,n);
	}
	public	void	draw (Line l)	{						//	線分を描画
			draw(l.p);
	}
	public	void	draw (Polygon p)	{					//	線分を描画
			draw(p.p);
	}
	public	void	draw (Surface s)	{					//	線分を描画
			for(int i=0; i<s.n; i++)draw(s.getPolygon(i));
	}
	public	void	paint (Polygon p)	{					//	塗りつぶす
			int		n=p.p.length;
			int[]	xp=new int[n],	yp=new int[n];
			for(int i=0; i<n; i++){
				Vector	pp=screen(p.p[i]);
				xp[i]=(int)pp.x;	yp[i]=(int)pp.y;
			}
			w.paint(xp,yp,n);
	}

	public	void	text(Vector p,String s){					//	文字列を描画
			Vector	a=screen(p);
			w.text(a.x,a.y,s);
	}

	public	void	drawAxis(){								//	座標軸を描画
			w.setColor(COLor.BLUE);
			draw( new Vector(-size_x/2+10,0,0),new Vector(size_x/2-10,0,0) );
			text( new Vector(size_x/2,0,0),"X");
			draw( new Vector(0,-size_x/2+10,0),new Vector(0,size_x/2-10,0) );
			text( new Vector(0,size_x/2,0),"Y");
			draw( new Vector(0,0,-size_y/2+10),new Vector(0,0,size_y/2-10));
			text( new Vector(0,0,size_y/2),"Z");
			setColor(COLor.BLACK);
	}
}

//		２次元描画用のクラス定義

public	class	Win2D{											//  Win2D用ウインドウ

		int		size_x=400,	size_y=350;							//	ウインドウサイズ
		Vector	org=new Vector(size_x/2,size_y/2);				//  原点の位置（スクリーン座標）
		COLor	bc=COLor.WHITE;									//	背景色(初期値)
		COLor	fc=COLor.BLACK;									//	描画色(初期値)
		TMatrix	t;												//	変換行列（標準座標=>screen座標）
		Screen	w;

		public	Win2D(String title, int ix, int iy)	{			//	ウインドウを開く
				init(title, ix, iy, bc);
		}
		public	Win2D(String title, int ix, int iy, COLor ic){	//	ウインドウを開く
				init(title, ix, iy, ic);
		}
		public	Win2D(String title, String fn){					//	画像ファイルを読み込む
				init(title, size_x, size_y, bc);
				read(fn);	
		}

		void	init(String title,int x, int y, COLor c){
			size_x=x;			size_y=y;
			setOrigin(x/2,y/2);
			bc=c;
			w=new Screen(title, x, y, c);
		}
		public	void	close(){								//	ウインドウを閉じる
				w.close();
		}
		
		public	void	setOrigin(int x, int y){				//	原点の位置設定(screen座標)
				org=new Vector(x+.5,y-.5,0);
				t=TMatrix._mul(TMatrix.move(org),TMatrix.scale(1,-1,1));
		}
		public	void	setColor(COLor c){						//	描画色を設定
				fc=c;
				w.setColor(c);
		}

		public 	void	point(double x, double y){				//	点を描画(screen座標)
				w.point((int)x,(int)y);
		}
		
		public 	void	point(Vector p){							//	点を描画
				Vector	a=TMatrix._mul(t,p);
				w.point(a.x,a.y);
		}
		public 	void	draw(Vector p0,Vector p1){				//	線分を描画
				Vector	a=TMatrix._mul(t,p0),	b=TMatrix._mul(t,p1);
				w.draw( a.x,a.y, b.x,b.y );
		}
		public	void	draw (Vector[] p){						//	線分を描画
				int		n=p.length;
				int[]	xp=new int[n],	yp=new int[n];
				for(int i=0; i<n; i++){
					Vector	pp=TMatrix._mul(t,p[i]);
					xp[i]=(int)pp.x;	yp[i]=(int)pp.y;
				}
				w.draw(xp,yp,n);
		}
		public	void	draw (Line l)	{						//	線分を描画
				draw(l.p);	
		}
		public	void	text(Vector p, String s){				//	文字列を描画
				Vector	a=TMatrix._mul(t,p);
				w.text(a.x,a.y,s);
		}
		
		public	void	drawAxis(){								//	座標軸を描画
				w.setColor(COLor.BLACK);
				draw(new Vector(-org.x+15,0),new Vector(size_x-org.x-15,0));
				text(new Vector(size_x-org.x-6,0),"X");
				draw(new Vector(0,-size_y+org.y+20),new Vector(0,org.y-20));
				text(new Vector(0,org.y-12),"Y");
				w.setColor(COLor.BLACK);
		}
		
		public 	COLor	getColor(double x, double y){			//	描画色を返す(screen座標)	
				return w.getColor((int)x,(int)y);
		}
		public 	COLor	getColor(Vector p){						//	描画色を返す	
				return w.getColor((int)(org.x+p.x),(int)(org.y-p.y));
		}
		public 	int		read(String	fn){						//	画像ファイルを読込む
				int	rc=w.read(fn);
				size_x=w.size_x;	size_y=w.size_y;
				org.x=size_x/2+.5;	org.y=size_y/2-.5;
				return	rc;
		}
}

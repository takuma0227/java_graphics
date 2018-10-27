//		サーフェイスモデルを記述するためのクラス

public	class	Surface{

	public	final static	int	FLAT  = 0x01;				//	平面の指定
	public	final static	int	SMOOTH= 0x00;				//	曲面の指定

	public	Vector[][] 	m;									//	メッシュ配列
	int		n=0;											//	メッシュ数
	int		n0=0,	n1=0;

	public	Surface	(Vector[][] ss){							//	メッシュ配列(拡張する)
			m=extend(ss);
			n0=m.length-3;	n1=m[0].length-3;
			n=n0*n1;
	}
	public	Surface	(Vector[][] ss, int a){					//	メッシュ配列(拡張しない)
			m=ss;
			n0=m.length-3;	n1=m[0].length-3;
			n=n0*n1;
	}
	Polygon	getPolygon(int i){								//	ポリゴンを返す
			int	i0=i/n1,	i1=i%n1;
			return	new Polygon(m[i0+1][i1+1],m[i0+1][i1+2],
								m[i0+2][i1+2],m[i0+2][i1+1]);
	}
	Polygon	getNormal(int i){								//	ポリゴンの法線を返す
			int	i0=i/n1,	i1=i%n1;
			Vector[][]	nv=new Vector[2][2];	
			for(int j0=i0, k0=0; j0<i0+2; j0++,k0++)
				for(int j1=i1, k1=0; j1<i1+2; j1++,k1++){
					nv[k0][k1]=Vector._out((Vector._sub(m[j0][j1+2],m[j0+2][j1])),(Vector._sub(m[j0+2][j1+2],m[j0][j1])));
					if( nv[k0][k1].len()<=0.5){
						nv[k0][k1]=Vector._out((Vector._sub(m[j0+2][j1],m[j0+2][j1+2])),(Vector._sub(m[j0][j1],m[j0][j1+2])));
						Vector	nn=Vector._out((Vector._sub(m[i0+1][i1+1],m[i0+2][i1+2])),(Vector._sub(m[i0+1][i1+2],m[i0+2][i1+1])));
						if(Vector._mul(nv[k0][k1],nn)<0)nv[k0][k1]=Vector._mul(-1,nv[k0][k1]);
					}
				}
			return new Polygon(	nv[0][0].unit(),nv[0][1].unit(),
								nv[1][1].unit(),nv[1][0].unit() );
	}
	
	Patch	getPatch(int i){	return getPatch(i/n1,i%n1);	}

	Patch	getPatch(int i0, int i1){
			return new Patch( new Vector[][] {
					{	m[i0][i1  ],m[i0+1][i1  ],m[i0+2][i1  ],m[i0+3][i1  ]	},
					{	m[i0][i1+1],m[i0+1][i1+1],m[i0+2][i1+1],m[i0+3][i1+1]	},
					{	m[i0][i1+2],m[i0+1][i1+2],m[i0+2][i1+2],m[i0+3][i1+2]	},
					{	m[i0][i1+3],m[i0+1][i1+3],m[i0+2][i1+3],m[i0+3][i1+3]	} } 
					,	Patch.SMOOTH);
	}

	static	Vector[][]	extend(Vector[][] pp){				//	メッシュ配列を拡張(広げる)する
			
			int		s0=pp.length-1,	s1=pp[0].length-1;
			Vector[][]	e=new Vector[s0+3][s1+3];
			for(int i0=0; i0<s0+1; i0++)
				for(int i1=0; i1<s1+1; i1++)
					e[i0+1][i1+1]=pp[i0][i1];
					
			for(int i0=1; i0<s0+2; i0++)					//	端辺の処理
				if( (Vector._sub(e[i0][1],e[i0][s1+1])).len()<0.1 ){		//	始点と終点が一致する場合
					e[i0][0]   =e[i0][s1];
					e[i0][s1+2]=e[i0][2];
				}
				else{
					if(s1==1){								//	線分が１本
						e[i0][0]=Vector._sub(Vector._mul(2,e[i0][1]),e[i0][2]);
						e[i0][3]=Vector._sub(Vector._mul(2,e[i0][2]),e[i0][1]);
						continue;
					}
					if((Vector._sub(e[i0][2],e[i0][3])).len()<0.1 || (Vector._sub(e[i0][1],e[i0][2])).len()<0.1)
							e[i0][0]=Vector._sub(Vector._mul(2,e[i0][1]),e[i0][2]);
					else	e[i0][0]   =Vector._add(e[i0][1],TMatrix._mul(TMatrix.rotate(Vector._sub(e[i0][2],e[i0][3]),Vector._sub(e[i0][1],e[i0][2])),(Vector._sub(e[i0][1],e[i0][2]))));
					if((Vector._sub(e[i0][s1],e[i0][s1-1])).len()<0.1 || (Vector._sub(e[i0][s1+1],e[i0][s1])).len()<0.1)
							e[i0][s1+2]=Vector._sub(Vector._mul(2,e[i0][s1+1]),e[i0][s1]);
					else	e[i0][s1+2]=Vector._add(e[i0][s1+1],TMatrix._mul(TMatrix.rotate(Vector._sub(e[i0][s1],e[i0][s1-1]),Vector._sub(e[i0][s1+1],e[i0][s1])),(Vector._sub(e[i0][s1+1],e[i0][s1]))));
				}

			for(int i1=0; i1<s1+3; i1++)					//	端辺の処理
				if( (Vector._sub(e[1][i1],e[s0+1][i1])).len()<0.1 ){		//	始点と終点が一致する場合
					e[0][i1]   =e[s0][i1];
					e[s0+2][i1]=e[2][i1];
				}
				else{
					if(s0==1){								//	線分が１本
						e[0][i1]=Vector._sub(Vector._mul(2,e[1][i1]),e[2][i1]);
						e[3][i1]=Vector._sub(Vector._mul(2,e[2][i1]),e[1][i1]);
						continue;
					}
					if((Vector._sub(e[2][i1],e[3][i1])).len()<0.1 || (Vector._sub(e[1][i1],e[2][i1])).len()<0.1)
							e[0][i1]=Vector._sub(Vector._mul(2,e[1][i1]),e[2][i1]);
					else	e[0][i1]   =Vector._add(e[1][i1],TMatrix._mul(TMatrix.rotate(Vector._sub(e[2][i1],e[3][i1]),Vector._sub(e[1][i1],e[2][i1])),(Vector._sub(e[1][i1],e[2][i1]))));
					if((Vector._sub(e[s0][i1],e[s0-1][i1])).len()<0.1 || (Vector._sub(e[s0+1][i1],e[s0][i1])).len()<0.1)
							e[s0+2][i1]=Vector._sub(Vector._mul(2,e[s0+1][i1]),e[s0][i1]);
					else	e[s0+2][i1]=Vector._add(e[s0+1][i1],TMatrix._mul(TMatrix.rotate(Vector._sub(e[s0][i1],e[s0-1][i1]),Vector._sub(e[s0+1][i1],e[s0][i1])),(Vector._sub(e[s0+1][i1],e[s0][i1]))));
				}
		return e;
	}

	//	線分ａをZ軸回転てポリゴンモデルを作る
	public	static	Surface	revolve_z(Line a, int n){	
			return	revolve_z(a,n,0,360);
	}

	public	static	Surface	revolve_z(Line a, int n, double b, double e)	{
			Vector[][]	mm=new Vector[n+1][a.p.length];
			for(int j=0; j<n+1; j++)	{
				TMatrix	m0=TMatrix.rot_z(b+j*(e-b)/n);
				for(int i=0; i<a.p.length; i++)		
					mm[j][i]=TMatrix._mul(m0,a.p[i]);
			}
			return new Surface(mm);
	}

	//	線分ａを線分ｂに沿ってポリゴンモデルを作る
	public	static	Surface	sweep_xz(Line a, Line b)	{		//	a,bを両側に増やす
			int		an=a.p.length,	bn=b.p.length;
			Vector[][]	mm=new Vector[bn][an];
			Line[]		f=new Line[bn];
			for(int i=1; i<bn-1; i++)
				f[i]=Line._mul(TMatrix._mul(TMatrix.move(b.p[i]),TMatrix.rot_xz(Vector._sub(b.p[i+1],b.p[i-1]))),a);
			f[0]	=Line._mul(TMatrix._mul(TMatrix.move(b.p[0]),TMatrix.rot_xz(Vector._sub(b.p[1],b.p[0]))),a);
			f[bn-1]	=Line._mul(TMatrix._mul(TMatrix.move(b.p[bn-1]),TMatrix.rot_xz(Vector._sub(b.p[bn-1],b.p[bn-2]))),a);
			for(int i=0; i<bn; i++)
				for(int j=0; j<an; j++)
					mm[i][j]=f[i].p[j];
			return new Surface(mm);		
	}

	public	static	Surface	 _mul(TMatrix a, Surface b)	{		
			Vector[][]	mm=TMatrix._mul(a,b.m);
			return new Surface(mm,0); 
	}
}

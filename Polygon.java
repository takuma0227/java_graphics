//	多角形のクラス

public	class	Polygon{

	public	Vector[]	p;										//	頂点座標(Point)配列のポインタ
	public	Vector	nv;										//	法線ベクトル

	public	Polygon(int n)	{
			if(n>0) p= new Vector[n];
	}
	public	Polygon(Vector a0,Vector a1,Vector a2,Vector a3){
			p= new Vector[5];
			p[0]=p[4]=a0;	p[1]=a1;
			p[2]=a2;		p[3]=a3;
			nv=(Vector._out((Vector._sub(p[0],p[2])),(Vector._sub(p[1],p[3])))).unit();
	}
	public	Polygon(Vector a[]){
			int		n=a.length;
			if(n>0) p= new Vector[n+1];
			for(int i=0; i<n; i++)p[i]=a[i]; p[n]=a[0];
			nv=(Vector._out((Vector._sub(p[0],p[2])),(Vector._sub(p[1],p[3%n])))).unit();
	}
	public	Polygon(Polygon a){
			int		n=a.p.length;
			p= new Vector[n];
			for(int i=0; i<n; i++)p[i]=a.p[i];
			nv=a.nv;
	}
	public	static	Polygon	 _mul(TMatrix m, Polygon b){
			Polygon c=new Polygon(b);
			for(int i=0; i<c.p.length; i++)c.p[i]=TMatrix._mul(m,b.p[i]);
			c.nv=(Vector._out((Vector._sub(c.p[0],c.p[2])),(Vector._sub(c.p[1],c.p[3%c.p.length])))).unit();
			return c;
	}
	public	static	Polygon	square_xy(double x, double y){
			return	Polygon._mul(TMatrix.scale(x,y,0),Polygon.square_xy());
	}
	public	static	Polygon	square_xy(){
			return	new Polygon( new Vector[]
					{	new Vector( 1, 1, 1),new Vector(-1, 1, 1),
	   					new Vector(-1,-1, 1),new Vector( 1,-1, 1)	}	);
	}
	public	double	hit(Ray VR, Vector P, Vector N){			//	交点座標を返す
			double	t=Vector._mul(nv,(Vector._sub(p[1],VR.o)))/(Vector._mul(nv,VR.d));				//	距離
			if(t<=0.)	return Ray.INFINITY; 
			P.copy(Vector._add(VR.o,Vector._mul(t,VR.d)));							//	交点,法線
			N.copy(nv);
			for(int i=0; i<p.length-1; i++)					//	交点座標Pがポリゴン内かの判定
	    		if(Vector._mul(N,(Vector._out((Vector._sub(p[i+1],p[i])),(Vector._sub(P,p[i])))))<0)return Ray.INFINITY;
			return t;
	}
	
	public	static	Polygon[]	box(){
			Polygon		p=square_xy();
			return	new Polygon[]{	
						p,					 Polygon._mul(TMatrix.rot_x(90),p),
						Polygon._mul(TMatrix.rot_x(180),p),Polygon._mul(TMatrix.rot_x(270),p),
						Polygon._mul(TMatrix.rot_y(90),p),Polygon._mul(TMatrix.rot_y(270),p)	};
	}
	public	static	Polygon[]	 _mul(TMatrix m, Polygon[] b){
		Polygon[]	p=new Polygon[b.length];
		for(int i=0; i<b.length; i++)	p[i]=Polygon._mul(m,b[i]);
		return p;
	}
}

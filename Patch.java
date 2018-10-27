//	四角形パッチ（レイトレーシング法用）

class	Patch{

	final static	int	FLAT  = 0x01;				//	平面の指定
	final static	int	SMOOTH= 0x00;				//	曲面の指定
	
	Polygon	p;										//	ポリゴン
	Polygon	n;										//	法線ベクトル
	int		mode;									
													//	パラメトリック曲面
	Vector[][]	PV;									//	制御点(4×4)
	Ball		BV;									//	バウンディングボリューム(球)
	Polygon[]	BB=new Polygon[6];					//	バウンディングボリューム(６面体)
	Vector[][]	NV=new Vector[2][2];				//	法線ベクトル
	Vector[][]	FP=new Vector[2][2];				//	局面頂点
	double	d_u,d_v;								//	ワーク用

	Patch(Vector[][] pp, Polygon p, Polygon n, int mi ){	//	制御点で指定(PATCH)
		mode=mi;
		init(pp);
		this.p=p;
		this.n=n;
	}
	Patch(Vector[][] pp, int mi ){							//	制御点で指定(PATCH)
		mode=mi;
		init(pp);
		p=new Polygon(pp[1][1],pp[2][1],pp[2][2],pp[1][2]);
		n=new Polygon(	(Vector._out((Vector._sub(pp[0][1],pp[2][1])),(Vector._sub(pp[1][0],pp[1][2])))).unit(),
						(Vector._out((Vector._sub(pp[1][1],pp[3][1])),(Vector._sub(pp[2][0],pp[2][2])))).unit(),
						(Vector._out((Vector._sub(pp[1][2],pp[3][2])),(Vector._sub(pp[2][1],pp[2][3])))).unit(),
						(Vector._out((Vector._sub(pp[0][2],pp[2][2])),(Vector._sub(pp[1][1],pp[1][3])))).unit()	);
	}
	Patch(Polygon pp ){										//	Polygonで指定
		mode=FLAT;
		Vector[][] vv=new Vector[4][4];
		vv[1][1]=pp.p[0];	vv[1][2]=pp.p[3];
		vv[2][1]=pp.p[1];	vv[2][2]=pp.p[2];
		for(int i0=1; i0<3; i0++){
			vv[i0][0]=Vector._sub(Vector._mul(2,vv[i0][1]),vv[i0][2]);
			vv[i0][3]=Vector._sub(Vector._mul(2,vv[i0][2]),vv[i0][1]);
		}
		for(int i1=0; i1<4; i1++){
			vv[0][i1]=Vector._sub(Vector._mul(2,vv[1][i1]),vv[2][i1]);
			vv[3][i1]=Vector._sub(Vector._mul(2,vv[2][i1]),vv[1][i1]);
		}
		mode=FLAT;
		init(vv);
		this.p=pp;
		this.n=new Polygon(pp.nv,pp.nv,pp.nv,pp.nv);
	}
		
	double	hit(Ray VR, Vector P, Vector N){					//	交点座標を返す

		if( BV.hit_bv(VR)>=Ray.INFINITY )return Ray.INFINITY;
		if(mode==FLAT)return p.hit(VR,P,N);
		return hitP( VR, P, N ,Ray.INFINITY);
	}

	//////////////////////////////
	//	パラメトリック曲面用	//
	//////////////////////////////

	double[]	funcN(double t){
			double	t2=t*t,	t3=t2*t;
			return new double[]{	-t3/6+t2/2-t/2+1/6., t3/2-t2+2/3.,
						-t3/2+t2/2+t/2+1/6., t3/6	};
	}
	double[]	funcNd(double t){
			double	t2=2*t,	t3=3*t*t;
			return new double[]{	-t3/6+t2/2-1/2., t3/2-t2,
						-t3/2+t2/2+1/2., t3/6	};
	}
	Vector	funcS (double u, double v, Vector[][] P){
			return	Patch._mul(Patch._mul(funcN(u),P),funcN(v));
	}
	Vector	funcSu(double u, double v, Vector[][] P){
			return	Patch._mul(Patch._mul(funcNd(u),P),funcN(v));
	}
	Vector	funcSv(double u, double v, Vector[][] P){
			return	Patch._mul(Patch._mul(funcN(u),P),funcNd(v));
	}
	Vector	func( double u, double v ){
			return	funcS(u,v,PV);
	}
	
	void	init(Vector[][] pp)	{ 	PV=pp;	bvolume();	}
	
	double	hitP(Ray VR, Vector P, Vector N, double l){		//	交差判定
	
			if( BV.hit_bv(VR)>=l )return Ray.INFINITY;
	
			int 	i=0;
			for( i=0; i<6; i++){
				//if(BB[i].nv*VR.d<-0.1)continue;
				if(BB[i].hit(VR,P,N)<l)break;
			}
			if(i==6)return Ray.INFINITY;
	
			double	u0=0,v0=0,u1=0,v1=0,d0;
			if	   ( (Vector._mul(NV[0][0],VR.d))*(Vector._mul(NV[0][1],VR.d))<=0 )v1=1; 
			else if( (Vector._mul(NV[0][0],VR.d))*(Vector._mul(NV[1][0],VR.d))<=0 )u1=1; 
			else if( (Vector._mul(NV[0][0],VR.d))*(Vector._mul(NV[1][1],VR.d))<=0 )u1=v1=1; 
			else v0=-1;
	
			if( v0<0 )	return hitP(.5, .5, VR, P, N, l);
			double[]	uu={	.5,	0,	u1,	1,	1	};
			double[]	vv={	.5,	0,	v1,	0,	1	};
			Vector	P0=new Vector(),N0=new Vector();
			for( i=0; i<3; i++){
				d0=hitP( uu[i], vv[i], VR, P0, N0, l);
				if(d0<l){
					l=d0;
					P.copy(P0);
					N.copy(N0);
				}
			}
			return l;
	}
	
	double	hitP(double u, double v, Ray VR, Vector P, Vector N, double l){
	
			Vector[][]	PP=TMatrix._mul(TMatrix._mul(TMatrix.rotate(VR.d,new Vector(0,0,1)),TMatrix.move(Vector._mul(-1,VR.o))),PV);
	
			double	du,	dv;
			int		LOOP_LM=10;
			Vector	S=new Vector(), Su, Sv;
			int		i;
			for( i=0; i<LOOP_LM; i++){
				S= funcS ( u, v, PP );
				if( (Math.abs(S.x)<=0.0001)&&(Math.abs(S.y)<=0.0001) ) break;
				Su=funcSu( u, v, PP );
				Sv=funcSv( u, v, PP );
				du=(-Sv.y*S.x+Sv.x*S.y)/(Su.x*Sv.y-Sv.x*Su.y);
				dv=( Su.y*S.x-Su.x*S.y)/(Su.x*Sv.y-Sv.x*Su.y);
	 			u+=du;	v+=dv;
				if( (Math.abs(du)>2) || (Math.abs(dv)>2) )return Ray.INFINITY;
			}
			if( i>=LOOP_LM )return Ray.INFINITY;
			if( u<0 || 1<u || v<0 || 1<v ) return Ray.INFINITY;
			P.copy(Vector._add(VR.o,Vector._mul(S.z,VR.d)));
			N.copy((Vector._out(funcSu(u,v,PV),funcSv(u,v,PV))).unit());	//	法線ベクトル
			return S.z;
	}
	
	//	外接三角形を求める
	Ball	inc_ball(Vector a0, Vector a1, Vector a2){
			Vector	b0=Vector._div((Vector._sub(a1,a0)),2), b1=Vector._div((Vector._sub(a2,a1)),2), b2=Vector._div((Vector._sub(a0,a2)),2);
			Vector	N=Vector._out(b0,b1);
			if(N.len()<0.1)return new Ball(new Vector(0,0,0),0);
			Vector	e0=Vector._out(N,b0);
			Vector	pp=Vector._add(Vector._add(a0,b0),Vector._mul(e0,(Vector._mul(b1,b2)/(Vector._mul(e0,b2)))));
			return	new Ball( pp, (Vector._sub(a0,pp)).len()+0.5 );
	}
	
	//	交点座標の計算    線:o,d   平面:s,n
	Vector	cross(Vector o, Vector d, Vector s, Vector n)
			{	return	Vector._add(o,Vector._mul((Vector._mul((Vector._sub(s,o)),n)/(Vector._mul(d,n))),d));	}
	void	swap(Vector a, Vector b){	Vector c=a; a=b; b=c;	}

	//	バウンディングボリュームBVの処理（Patch用）
	void	bvolume(){
			Vector[]	s=new Vector[4],t =new Vector[4],r =new Vector[4],
					q=new Vector[4],nv=new Vector[4],tt=new Vector[4];
	
			//	法線ベクトルの計算
			NV[0][0]=nv[0]=(Vector._out(funcSu(0,0,PV),funcSv(0,0,PV))).unit();
			NV[1][0]=nv[1]=(Vector._out(funcSu(1,0,PV),funcSv(1,0,PV))).unit();
			NV[1][1]=nv[2]=(Vector._out(funcSu(1,1,PV),funcSv(1,1,PV))).unit();
			NV[0][1]=nv[3]=(Vector._out(funcSu(0,1,PV),funcSv(0,1,PV))).unit();
	
			//	バウンディングボリューウムの原形（６面体）
			q[0]=PV[1][1];	q[1]=PV[2][1];					//	制御点(内側の四角形)
			q[2]=PV[2][2];	q[3]=PV[1][2];
			FP[0][0]=s[0]=funcS(0,0,PV);					//	曲面の四角形頂点
			FP[1][0]=s[1]=funcS(1,0,PV);
			FP[1][1]=s[2]=funcS(1,1,PV);
			FP[0][1]=s[3]=funcS(0,1,PV);
			d_u=0.5/Math.max((Vector._sub(s[0],s[1])).len(),(Vector._sub(s[2],s[3])).len());
			d_v=0.5/Math.max((Vector._sub(s[0],s[3])).len(),(Vector._sub(s[1],s[2])).len());
	
			//	四角形の法線ベクトルの作成
			Vector	nq=(Vector._out((Vector._sub(q[0],q[2])),(Vector._sub(q[1],q[3])))).unit();
			Vector	ns=(Vector._out((Vector._sub(s[0],s[2])),(Vector._sub(s[1],s[3])))).unit();
	
			//	t四角形の作成(曲面の四角形)
			for(int i=0,j; i<4; i++){
				if( (Vector._sub(s[i],s[(i+1)%4])).len()<0.1 )
						{	nv[i]=nv[(i+1)%4]=ns;	}
				t[i]=cross(s[i],ns,q[i],nq);
			}
			//	t四角形の拡大(横)
			for(int i=0; i<4; i++){
				Vector	a1=Vector._sub(t[i],t[(i+3)%4]),	a2=Vector._sub(t[i],t[(i+1)%4]);
				Vector	a=Vector._add(a1.unit(),a2.unit());
				if	   ((a1).len()<0.1)	tt[i]=Vector._sub(t[i],(Vector._out(a2,ns)).unit());
				else if((a2).len()<0.1)	tt[i]=Vector._add(t[i],(Vector._out(a1,ns)).unit());
				else tt[i]=Vector._add(t[i],Vector._div(a,(Vector._mul(a,(Vector._out(a1,ns)).unit()))));
			}
			for(int i=0; i<4; i++)	t[i].copy(tt[i]);
			//	t四角形の交差調整
			for(int i=0,j=1; i<4; i++,j=(i+1)%4){
				if( q[i].x==q[j].x && q[i].y==q[j].y && q[i].z==q[j].z )continue;
				if( Vector._mul((Vector._sub(t[(i+2)%4],t[(j+2)%4])),(Vector._sub(q[i],q[j]))) >0 )
							swap( t[(i+2)%4], t[(j+2)%4] );
			}
	
			//	ｒ四角形の拡大(横)
			for(int i=0; i<4; i++){
				Vector	a1=Vector._sub(s[i],s[(i+3)%4]),	a2=Vector._sub(s[i],s[(i+1)%4]);
				Vector	a=Vector._add(a1.unit(),a2.unit());
				if	   (a1.len()<0.1)	r[i]=Vector._sub(s[i],(Vector._out(a2,ns)).unit());
				else if(a2.len()<0.1)	r[i]=Vector._add(s[i],(Vector._out(a1,ns)).unit());
				else r[i]=Vector._add(Vector._add(s[i],Vector._div(a,(Vector._mul(a,(Vector._out(a1,ns)).unit())))),Vector._mul((Vector._sub(q[i],s[i])).len(),a));
			}
			//	ｒ四角形の交差調整
	 		for(int i=0,j=1; i<4; i++,j=(i+1)%4){
				if( q[i].x==q[j].x && q[i].y==q[j].y && q[i].z==q[j].z )continue;
				if( Vector._mul((Vector._sub(r[(i+2)%4],r[(j+2)%4])),(Vector._sub(q[i],q[j]))) >0 )
					swap( r[(i+2)%4], r[(j+2)%4] );
			}
	
			//	上下関係(t,r)の調整( r-t方向をns方向にする )
			//	６面体の拡大(上下)  r四角形を上げ、t四角形を下げる
			for(int i=0; i<4; i++){
				if( Vector._mul(ns,(Vector._sub(r[i],t[i])))>0 ){	r[i]=Vector._add(r[i],ns);	t[i]=Vector._sub(t[i],ns);	}
				else				{	r[i]=Vector._sub(r[i],ns);	t[i]=Vector._add(t[i],ns);	}
				if( Vector._mul(ns,(Vector._sub(t[i],r[i])))>0 ) swap( t[i], r[i] ); 
			}
				
			//	バウンディングボリューウム（６面体）
			BB[0]=new Polygon( r[0],r[1],r[2],r[3] );
			BB[1]=new Polygon( t[3],t[2],t[1],t[0] );
			BB[2]=new Polygon( t[1],r[1],r[0],t[0] );
			BB[3]=new Polygon( t[2],r[2],r[1],t[1] );
			BB[4]=new Polygon( t[3],r[3],r[2],t[2] );
			BB[5]=new Polygon( t[0],r[0],r[3],t[3] );
	
			//	バウンディングボリューウム（球体）
			BV=inc_ball(s[0],s[1],s[2]);	if( BV.r>(Vector._sub(BV.o,s[3])).len()) return;
			BV=inc_ball(s[0],s[1],s[3]);	if( BV.r>(Vector._sub(BV.o,s[2])).len()) return;
			BV=inc_ball(s[0],s[2],s[3]);	if( BV.r>(Vector._sub(BV.o,s[1])).len()) return;
			BV=inc_ball(s[1],s[2],s[3]);	if( BV.r>(Vector._sub(BV.o,s[0])).len()) return;
			System.out.println("BV error  ");
			BV=new Ball(new Vector(0,0,0), 0 );
	}

	//	バウンディングボリュームBV(球体)の処理BVの処理
	Ball	bvolume(Patch[] p, int n){
			if( n<=0 )return new Ball(new Vector(0,0,0),0);
			Vector	c;					//	BV(球体)の中心座標を求める
			double	d=0;
			int		i,j, i0=0,j0=0;
			for( i=0; i<n; i++)
				for( j=0; j<i; j++){
					if(  Math.abs(p[i].BV.o.x-p[j].BV.o.x)
						+Math.abs(p[i].BV.o.y-p[j].BV.o.y)
						+Math.abs(p[i].BV.o.z-p[j].BV.o.z)
						+p[i].BV.r+p[j].BV.r<d )continue;
					double	dd=(Vector._sub(p[i].BV.o,p[j].BV.o)).len()+p[i].BV.r+p[j].BV.r;
					if(d<dd){	d=dd;	i0=i;	j0=j;	}	
				}
			Vector	e=(Vector._sub(p[i0].BV.o,p[j0].BV.o)).unit();
	 		c=Vector._div((Vector._add(Vector._mul(2,p[i0].BV.o),Vector._mul(e,(p[i0].BV.r-p[j0].BV.r)))),2);
	
			double	r=0;				//	BV(球体)の半径を求める
			for( i=0; i<n; i++)
				for( j=0; j<2; j++)
					for(int k=0; k<2; k++){
						r=Math.max( r, (Vector._sub(c,p[i].FP[j][k])).len() );
						r=Math.max( r, (Vector._sub(c,p[i].PV[j+1][k+1])).len() );
			}
			return new Ball( c, r+1 );
	}

	//	行列間の演算子の定義
	static	Vector	 _mul(double[] a, Vector[] b){
			Vector	c=new Vector(0,0,0);
			for(int i=0; i<4; i++)	c=Vector._add(c,Vector._mul(a[i],b[i]));
			return c;
	}
	static	Vector	 _mul(Vector[] a, double[] b){
			return Patch._mul(b,a);
	}

	static	Vector[]  _mul(double[] a, Vector[][] b){
			Vector[] c=new Vector[4];
			for(int j=0; j<4; j++){
				c[j]=new Vector();
				for(int k=0; k<4; k++)
						c[j]=Vector._add(c[j],Vector._mul(a[k],b[k][j]));
			}
			return c;
	}
	static	Vector[]  _mul(Vector[][] a, double[] b){
			return Patch._mul(b,a);
	}
}

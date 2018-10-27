//	視線、光線のクラス

public	class	Ray{

	public	static	double	INFINITY=1e12;					//	無限遠方
	public	static	double	DEL		=1e-3;					//	計算誤差
	public	static	int		N_SHADE	=20;					//	鏡面反射の係数

	public	Vector	o;										//	始点座標
	public	Vector	d;										//	方向ベクトル

	public	Ray	(Vector og, Vector di){
			o=og;	
			d=di;	
	}
	public	Ray	(Ray a){
			o=new Vector(a.o);
			d=new Vector(a.d);
	}

	//	反射光を計算する	L:照明方向ベクトル	N:法線ベクトル
	public	COLor	shading( Vector L, Vector N, COLor c){
			if(0<Vector._mul(N,L))return	shading(L,N,c,0);
			else	return	shading(L,N,c,1);
	}

	//	反射光を計算する	L:照明方向ベクトル	N:法線ベクトル s:照明光強度
	public	COLor	shading( Vector L, Vector N, COLor c, double s){
			double	kd=0.7,	ks=0.7,	ke=0.3;				               	//	拡散反射,鏡面反射,環境光
			double	NL=Vector._mul(N,L);
			Vector	R=Vector._sub(L,Vector._mul(2*NL,N));              	//	反射方向ベクトル
			COLor	Cd=COLor._mul(kd*Math.max(-NL,0)*s,c);	            //	拡散反射光
			COLor	Cs=COLor._mul(ks*Math.pow(Math.max(Vector._mul(Vector._mul(-1,R),d),0),N_SHADE)*s,COLor.WHITE);  //鏡面反射光
			COLor	Ce=COLor._mul(ke,c);				            	//	環境光
			return	COLor._add(COLor._add(Cd,Cs),Ce);
	}

	//	反射光を計算する	L:照明方向ベクトル	N:法線ベクトル s:照明光強度
	public	COLor	shading( Vector L, Vector N, Optics A, double s){
			Vector	R=Vector._sub(L,Vector._mul(2*(Vector._mul(L,N)),N));							//	反射方向ベクトル
			return	COLor._add(COLor._mul((A.kd*Math.max(Vector._mul(Vector._mul(-1,N),L),0)*s+A.ke),A.c),
			COLor._mul(A.ks*Math.pow(Math.max(Vector._mul(Vector._mul(-1,R),d),0),N_SHADE)*s,COLor.WHITE))
                                                           ;
	}
}

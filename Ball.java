//	球体のクラス

public	class	Ball{

	public	Vector	o;										//	中心座標
	public	double	r;										//	半径
	
	public	Ball(){
			o=new Vector(0,0,0);
			r=0;	
	}
	public	Ball(Vector og, double ra){
			o=og;
			r=ra;	
	}
	public	Ball(Ball a){
			o=new Vector(a.o);
			r=a.r;	
	}
	public	double	hit(Ray VR, Vector P, Vector N){			//	交点座標を返す
			Vector	Vo=Vector._sub(VR.o,o);								//		の交差判定			
			double	ds=Vector._mul(VR.d,Vo);	
			double	d2=ds*ds-Vector._mul(Vo,Vo)+r*r;						//	判別式
			if( d2<0 )	return Ray.INFINITY;
			double	t=-ds-Math.sqrt(d2);					//	距離
			if(t<Ray.DEL) t=-ds+Math.sqrt(d2);
			P.copy(Vector._add(VR.o,Vector._mul(t,VR.d)));
			N.copy((Vector._sub(P,o)).unit());							//	交点座標,法線ベクトル
			return t;
	}
	public	double	hit_bv(Ray VR){							//	BV(バウンディングボリューム)
			Vector	Vo=Vector._sub(VR.o,o);								//		の交差判定			
			double	ds=Vector._mul(VR.d,Vo);	
			double	d2=ds*ds-Vector._mul(Vo,Vo)+r*r;						//	判別式
			if( d2 < 0 )	return Ray.INFINITY;
			return	-ds-Math.sqrt(d2);						//	距離
	}
	public	double	hit_bv2(Ray VR){						//	BV(バウンディングボリューム)
			Vector	Vo=Vector._sub(VR.o,o);								//		の交差判定			
			double	ds=Vector._mul(VR.d,Vo);	
			double	d2=ds*ds-Vector._mul(Vo,Vo)+r*r;						//	判別式
			if( d2 < 0 )	return Ray.INFINITY;
			return	-ds+Math.sqrt(d2);						//	距離
	}
}

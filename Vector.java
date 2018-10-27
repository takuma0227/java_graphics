//		Vector,Pointクラス用の記述

public	class	Vector{
	
	public	static	final	double	PIR=Math.PI/180.0;
	public	double	x,y,z;									//各座標の値
	
	public	Vector(){
		x=y=z=0.0;
	}
	public	Vector(double ix, double iy){
		x=ix;
		y=iy;
		z=0.0;
	}
	public	Vector(double ix, double iy, double iz){
		x=ix;
		y=iy;
		z=iz;
	}
	public	Vector(Vector p){
		x=p.x;
		y=p.y;
		z=p.z;
	}
	public	Vector(Vector p1,Vector p2){
		x=p1.x-p2.x;
		y=p1.y-p2.y;
		z=p1.z-p2.z;
	}
	public	Vector(String s){
		String[] tx=s.split(" ");
		x=y=z=0;
		if(0<tx.length)x=Double.parseDouble(tx[0]);
		if(1<tx.length)y=Double.parseDouble(tx[1]);
		if(2<tx.length)z=Double.parseDouble(tx[2]);
	}
	
	//	Vector,Point関連の演算子の記述
	public	static	Vector	 _add(Vector v1,Vector v2){
		return new Vector(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z);
	}
	public	static	Vector	 _sub(Vector v1,Vector v2){
		return new Vector(v1.x-v2.x,v1.y-v2.y,v1.z-v2.z);
	}
	public	static	Vector	 _mul(double d1,Vector v2){
		return new Vector(d1*v2.x,d1*v2.y,d1*v2.z);
	}
	public	static	Vector	 _mul(Vector v2,double d1){
		return new Vector(d1*v2.x,d1*v2.y,d1*v2.z);
	}
	public	static	double	 _mul(Vector v1,Vector v2){		//	内積
		return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
	}
	public	static	Vector	 _out(Vector v1,Vector v2){		//	外積
		return new Vector(v1.y*v2.z-v1.z*v2.y,v1.z*v2.x-v1.x*v2.z, v1.x*v2.y-v1.y*v2.x);
	}
	public	static	Vector	 _div(Vector v1,double d2){
		return new Vector(v1.x/d2,v1.y/d2,v1.z/d2);
	}
	
	//	
	public	static	Line	 _orr(Vector v1,Vector v2){		//	線分の作成
		return new Line(v1, Vector._add(v1,v2));
	}
	public	static	Line	 _uni(Vector v1,Vector v2){	//	線分の作成
		return new Line(v1, v2);
	}
	
	public	double	len(){			//大きさ（長さ）
		return Math.sqrt(x*x+y*y+z*z);
	}
	public	Vector	unit(){			//大きさを１にする
		double	l=len();
		if(l==0.0)return	new Vector(1,0,0);
		return new Vector(x/l,y/l,z/l);
	}
	public	static	Vector	unit(Vector p1,Vector p2){
		return 	(Vector._sub(p1,p2)).unit();
	}
	public	static	Vector	unit(double a, double e)	{
		return new Vector( 	Math.cos(a*PIR)*Math.cos(e*PIR),
							Math.sin(a*PIR)*Math.cos(e*PIR),
							Math.sin(e*PIR) );
	}
	public	static	Vector	norm(Vector a,Vector b){			//	外積
		return (Vector._out(a,b)).unit();
	}

	public	void	copy(Vector s){							//	参照型の引数とする場合に使用
		x=s.x;
		y=s.y;
		z=s.z;
	}
	public	void	print(String s){
		System.out.println("Point:"+s+".x,y,z="+x+","+y+","+z);
	}
}

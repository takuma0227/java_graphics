//		À•W•ÏŠ·—ps—ñ

public	class	TMatrix{
	
	public	static	final	double	PIR=Math.PI/180.0;
	public	double[][]	t=new double[4][4];					//	•ÏŠ·s—ñ

	public	TMatrix()	{	init();	}
	public	TMatrix(TMatrix it)	{	
			t=new double[4][4];
			for(int i=0; i<4; i++)
				for(int j=0; j<4; j++)t[i][j]=it.t[i][j];
	}
	public	TMatrix(double m00,	double m01,	double m03,
					double m10,	double m11,	double m13,
					double m30,	double m31,	double m33		){
			init();
			t[0][0]=m00;	t[0][1]=m01;	t[0][3]=m03;
			t[1][0]=m10;	t[1][1]=m11;	t[1][3]=m13;
			t[2][0]=m30;	t[2][1]=m31;	t[3][3]=m33;
	}
	public	TMatrix(double m00,	double m01,	double m02,	double m03,
					double m10,	double m11,	double m12,	double m13,
					double m20,	double m21,	double m22,	double m23,
					double m30,	double m31,	double m32,	double m33	){
			t[0][0]=m00;	t[0][1]=m01;	t[0][2]=m02;	t[0][3]=m03;
			t[1][0]=m10;	t[1][1]=m11;	t[1][2]=m12;	t[1][3]=m13;
			t[2][0]=m20;	t[2][1]=m21;	t[2][2]=m22;	t[2][3]=m23;
			t[3][0]=m30;	t[3][1]=m31;	t[3][2]=m32;	t[3][3]=m33;
	}
	void	init(){
			for(int i=0; i<4; i++)
				for(int j=0; j<4; j++)t[i][j]=0.0;
			for(int i=0; i<4; i++) t[i][i]=1.0;
	}

	//	‰‰Zq‚Ì‹Lq
	public	static	Vector	 _mul(TMatrix m, Vector b){
		return	new Vector(	m.t[0][0]*b.x+m.t[0][1]*b.y+m.t[0][2]*b.z+m.t[0][3],
							m.t[1][0]*b.x+m.t[1][1]*b.y+m.t[1][2]*b.z+m.t[1][3],
							m.t[2][0]*b.x+m.t[2][1]*b.y+m.t[2][2]*b.z+m.t[2][3]	);
	}
	public	static	Vector[]	 _mul(TMatrix m, Vector[] b){
		Vector[] c=new Vector[b.length];
		for(int i=0; i<c.length; i++)c[i]=TMatrix._mul(m,b[i]);
		return c;
	}
	public	static	Vector[][]	 _mul(TMatrix m, Vector[][] b){
		int		n0=b.length,	n1=b[0].length;
		Vector[][] c=new Vector[n0][n1];
		for(int i0=0; i0<n0; i0++)
			for(int i1=0; i1<n1; i1++)c[i0][i1]=TMatrix._mul(m,b[i0][i1]);
		return c;
	}
	public	static	TMatrix	 _mul(TMatrix m1, TMatrix m2){
		TMatrix	m=new TMatrix();
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++){
				m.t[i][j]=0;
				for(int k=0; k<4; k++) m.t[i][j]+=m1.t[i][k]*m2.t[k][j];
			}
		return m;
	}

	//	”{—¦•ÏŠ·
	public	static	TMatrix	 _mul(double a, TMatrix m){
		return	TMatrix._mul(TMatrix.scale(a,a,a),m);
	}
	public	static	TMatrix	scale(double x, double y, double z){
			TMatrix	m=new TMatrix();
			m.t[0][0]=x; m.t[1][1]=y; m.t[2][2]=z;
			return	m;
	}
	public	static	TMatrix	scale(Vector a)	{	return scale(a.x,a.y,a.z);	}
	public	static	TMatrix	scale(double a)	{	return scale(a,  a,  a);	}

	//	‰ñ“]•ÏŠ·
	static	TMatrix	rot_x(double sn, double cs){				//	‚w²‚ğ’†S‚É‰ñ“]
			TMatrix	m=new TMatrix();
			m.t[1][1]=cs;	m.t[1][2]=-sn;
			m.t[2][1]=sn;   m.t[2][2]=cs;
			return	m;
	}
	static	TMatrix	rot_y(double sn, double cs){				//	‚x²‚ğ’†S‚É‰ñ“]
			TMatrix	m=new TMatrix();
			m.t[0][0]=cs;	m.t[0][2]=sn;
			m.t[2][0]=-sn;  m.t[2][2]=cs;
			return	m;
	}
	static	TMatrix	rot_z(double sn, double cs){				//	‚y²‚ğ’†S‚É‰ñ“]
			TMatrix	m=new TMatrix();
			m.t[0][0]=cs;	m.t[0][1]=-sn;
			m.t[1][0]=sn;   m.t[1][1]=cs;
			return	m;
	}
	public	static	TMatrix	rot_x(double r)	{	return rot_x( Math.sin(r*PIR), Math.cos(r*PIR) );	}
	public	static	TMatrix	rot_y(double r)	{	return rot_y( Math.sin(r*PIR), Math.cos(r*PIR) );	}
	public	static	TMatrix	rot_z(double r)	{	return rot_z( Math.sin(r*PIR), Math.cos(r*PIR) );	}

	static	TMatrix	rot_xz(Vector a){							//	Y²‰ñ“]
			Vector	ua=a.unit(),	aa=TMatrix._mul(TMatrix.scale(1,1,0),ua);
			if(aa.len()==0)return TMatrix.rot_x(90);
			TMatrix	mx=TMatrix.rot_x(Vector._mul(new Vector(0,0,1),ua),aa.len());
			TMatrix	mz=TMatrix.rot_z(-aa.x/aa.len(),aa.y/aa.len());
			return TMatrix._mul(mz,mx);
	}

	public	static	TMatrix	rotate(Vector a, double sn, double cs){//	a‚ğ²‚Æ‚µ‚ÄC‰ñ“]‚·‚é
			Vector	axy=(new Vector(a.x,a.y,0)).unit();
			Vector	axz=TMatrix._mul(rot_z(-axy.y,axy.x),a.unit());
			return	TMatrix._mul(TMatrix._mul(TMatrix._mul(TMatrix._mul(rot_z(axy.y,axy.x),rot_y(-axz.z,axz.x)),rot_x(sn,cs)),rot_y(axz.z,axz.x)),rot_z(-axy.y,axy.x))
                                             ;
	}
	public	static	TMatrix	rotate(Vector a, Vector b){
			double	sn=(Vector._out(a.unit(),b.unit())).len();
			if(sn!=0)	return	rotate((Vector._out(a,b)).unit(),sn,Vector._mul(a.unit(),b.unit()));
			if((Vector._sub(a,b)).len()==0)	return  new TMatrix();
			return	rot_z(180);
	}
	public	static	TMatrix	rotate(Vector a, double r){			//	a‚ğ²‚Æ‚µ‚ÄCr“x‰ñ“]‚·‚é
			return rotate(a, Math.sin(r*PIR), Math.cos(r*PIR) );
	}
	public	static	TMatrix	rotate(double r){					//	2ŸŒ³—p
			return rot_z(r);
	}
	
	//	•½sˆÚ“®
	public	static	TMatrix	move(double x, double y, double z){
			TMatrix	m=new TMatrix();
			m.t[0][3]=x;	m.t[1][3]=y;	m.t[2][3]=z;
			return	m;
	}
	public	static	TMatrix	move(Vector a){
			return move(a.x,a.y,a.z);
	}

	public	static	TMatrix	perspect(double d){				//	“§‹•ÏŠ·i‰“‹ßŠ´j
			TMatrix	m=new TMatrix();
			m.t[2][3]=-d;	m.t[3][2]=-1/d;
			return	m;
	}
	
	public	static	TMatrix	trans(TMatrix a){				//	“]’us—ñ
			TMatrix	c=new TMatrix();
			for(int i=0; i<4; i++)
				for(int j=0; j<4; j++)	c.t[i][j]=a.t[j][i];
			return c;
	}
	public	void print(String s){
			System.out.println("TMatrix:"+s);
			for(int i=0; i<4; i++){
				for(int j=0; j<4; j++)
					System.out.print("\t"+t[i][j]);
				System.out.println();
			}
	}

	public	static	TMatrix	inverse(TMatrix a){				//	‹t•ÏŠ·s—ñ
			double	d=0;
			for(int i=0; i<3; i++)
					d=d+a.t[0][i]*a.t[1][(i+1)%3]*a.t[2][(i+2)%3]	
					-a.t[0][i]*a.t[1][(i+2)%3]*a.t[2][(i+1)%3];
			TMatrix	b=new TMatrix();
			if(d!=0)
			for(int i=0; i<3; i++)							//	‹ts—ñ
				for(int j=0; j<4; j++){
					b.t[i][j]=(  a.t[(j+1)%4][(i+1)%4]*a.t[(j+2)%4][(i+2)%4]*a.t[(j+3)%4][(i+3)%4]
								+a.t[(j+2)%4][(i+1)%4]*a.t[(j+3)%4][(i+2)%4]*a.t[(j+1)%4][(i+3)%4]
								+a.t[(j+3)%4][(i+1)%4]*a.t[(j+1)%4][(i+2)%4]*a.t[(j+2)%4][(i+3)%4]
								-a.t[(j+1)%4][(i+3)%4]*a.t[(j+2)%4][(i+2)%4]*a.t[(j+3)%4][(i+1)%4]
								-a.t[(j+2)%4][(i+3)%4]*a.t[(j+3)%4][(i+2)%4]*a.t[(j+1)%4][(i+1)%4]
								-a.t[(j+3)%4][(i+3)%4]*a.t[(j+1)%4][(i+2)%4]*a.t[(j+2)%4][(i+1)%4] )/d;
					if( (i+j)%2==1 )b.t[i][j]*=-1;
			}
			return b;
	}
}

//		光学的性質を記述

class	Optics{

		COLor	c;								//	色
		double	kd,ks,ke;						//	拡散反射率,鏡面反射率,環境光係数
		double	kr,kn,kt;						//	反射率,屈折率,透過率
												//	反射量の調整BEA，密度の調整GAM(Volume)
		Optics	(){
				c=COLor.BLACK;
				kd=0.7;		ks=0.8;		ke=0.2;
				kr=kn=kt=0;
		}
		Optics	(COLor ic){
				c=ic;
				kd=0.7;		ks=0.8;		ke=0.2;
				kr=0;		kn=0;		kt=0;
				if( kn!=0.0 && kt==0.0 )kt=1.0;
		}
		Optics	(COLor ic, double id, double is, double ie){
				c=ic;
				kd=id;		ks=is;		ke=ie;
				kr=0;		kn=0;		kt=0;
				if( kn!=0.0 && kt==0.0 )kt=1.0;
		}
		Optics	(COLor ic, double id, double is, double ie,
									double ir, double in ,double it){
				if( in!=0 && it==0 )it=1;
				c=ic;		kd=id;		ks=is;		ke=ie;
				kr=ir;		kn=in;		kt=it;
		}
		Optics	(Optics op){
				c=op.c;
				kd=op.kd;		ks=op.ks;		ke=op.ke;
				kr=op.kr;		kn=op.kn;		kt=op.kt;
		}

		static	Optics	 _add(Optics a, Optics b){
				Optics	c=new Optics();
				c.c=COLor._add(a.c,b.c);
				c.kd=a.kd+b.kd;	c.ks=a.ks+b.ks;	c.ke=a.ke+b.ke;
				c.kr=a.kr+b.kr;	c.kn=a.kn+b.kn;	c.kt=a.kt+b.kt;
				return c;
		}

		static	Optics	 _sub(Optics a, Optics b){
				Optics	c=new Optics();
				c.c=COLor._sub(a.c,b.c);
				c.kd=a.kd-b.kd;	c.ks=a.ks-b.ks;	c.ke=a.ke-b.ke;
				c.kr=a.kr-b.kr;	c.kn=a.kn-b.kn;	c.kt=a.kt-b.kt;
				return c;
		}
		
		static	Optics	 _mul(double a, Optics b){
				Optics	c=new Optics();
				c.c=COLor._mul(a,b.c);
				c.kd=a*b.kd;	c.ks=a*b.ks;	c.ke=a*b.ke;
				c.kr=a*b.kr;	c.kn=a*b.kn;	c.kt=a*b.kt;
				return c;
		}
		
		static	Optics	 _div(Optics a, double b){
				Optics	c=new Optics();
				c.c=COLor._div(a.c,b);
				c.kd=a.kd/b;	c.ks=a.ks/b;	c.ke=a.ke/b;
				c.kr=a.kr/b;	c.kn=a.kn/b;	c.kt=a.kt/b;
				return c;
		}
		void	copy(Optics op){
				c=op.c;
				kd=op.kd;		ks=op.ks;		ke=op.ke;
				kr=op.kr;		kn=op.kn;		kt=op.kt;
		}
}

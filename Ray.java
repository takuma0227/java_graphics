//	�����A�����̃N���X

public	class	Ray{

	public	static	double	INFINITY=1e12;					//	��������
	public	static	double	DEL		=1e-3;					//	�v�Z�덷
	public	static	int		N_SHADE	=20;					//	���ʔ��˂̌W��

	public	Vector	o;										//	�n�_���W
	public	Vector	d;										//	�����x�N�g��

	public	Ray	(Vector og, Vector di){
			o=og;	
			d=di;	
	}
	public	Ray	(Ray a){
			o=new Vector(a.o);
			d=new Vector(a.d);
	}

	//	���ˌ����v�Z����	L:�Ɩ������x�N�g��	N:�@���x�N�g��
	public	COLor	shading( Vector L, Vector N, COLor c){
			if(0<Vector._mul(N,L))return	shading(L,N,c,0);
			else	return	shading(L,N,c,1);
	}

	//	���ˌ����v�Z����	L:�Ɩ������x�N�g��	N:�@���x�N�g�� s:�Ɩ������x
	public	COLor	shading( Vector L, Vector N, COLor c, double s){
			double	kd=0.7,	ks=0.7,	ke=0.3;				               	//	�g�U����,���ʔ���,����
			double	NL=Vector._mul(N,L);
			Vector	R=Vector._sub(L,Vector._mul(2*NL,N));              	//	���˕����x�N�g��
			COLor	Cd=COLor._mul(kd*Math.max(-NL,0)*s,c);	            //	�g�U���ˌ�
			COLor	Cs=COLor._mul(ks*Math.pow(Math.max(Vector._mul(Vector._mul(-1,R),d),0),N_SHADE)*s,COLor.WHITE);  //���ʔ��ˌ�
			COLor	Ce=COLor._mul(ke,c);				            	//	����
			return	COLor._add(COLor._add(Cd,Cs),Ce);
	}

	//	���ˌ����v�Z����	L:�Ɩ������x�N�g��	N:�@���x�N�g�� s:�Ɩ������x
	public	COLor	shading( Vector L, Vector N, Optics A, double s){
			Vector	R=Vector._sub(L,Vector._mul(2*(Vector._mul(L,N)),N));							//	���˕����x�N�g��
			return	COLor._add(COLor._mul((A.kd*Math.max(Vector._mul(Vector._mul(-1,N),L),0)*s+A.ke),A.c),
			COLor._mul(A.ks*Math.pow(Math.max(Vector._mul(Vector._mul(-1,R),d),0),N_SHADE)*s,COLor.WHITE))
                                                           ;
	}
}

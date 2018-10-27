//	���̂̃N���X

public	class	Ball{

	public	Vector	o;										//	���S���W
	public	double	r;										//	���a
	
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
	public	double	hit(Ray VR, Vector P, Vector N){			//	��_���W��Ԃ�
			Vector	Vo=Vector._sub(VR.o,o);								//		�̌�������			
			double	ds=Vector._mul(VR.d,Vo);	
			double	d2=ds*ds-Vector._mul(Vo,Vo)+r*r;						//	���ʎ�
			if( d2<0 )	return Ray.INFINITY;
			double	t=-ds-Math.sqrt(d2);					//	����
			if(t<Ray.DEL) t=-ds+Math.sqrt(d2);
			P.copy(Vector._add(VR.o,Vector._mul(t,VR.d)));
			N.copy((Vector._sub(P,o)).unit());							//	��_���W,�@���x�N�g��
			return t;
	}
	public	double	hit_bv(Ray VR){							//	BV(�o�E���f�B���O�{�����[��)
			Vector	Vo=Vector._sub(VR.o,o);								//		�̌�������			
			double	ds=Vector._mul(VR.d,Vo);	
			double	d2=ds*ds-Vector._mul(Vo,Vo)+r*r;						//	���ʎ�
			if( d2 < 0 )	return Ray.INFINITY;
			return	-ds-Math.sqrt(d2);						//	����
	}
	public	double	hit_bv2(Ray VR){						//	BV(�o�E���f�B���O�{�����[��)
			Vector	Vo=Vector._sub(VR.o,o);								//		�̌�������			
			double	ds=Vector._mul(VR.d,Vo);	
			double	d2=ds*ds-Vector._mul(Vo,Vo)+r*r;						//	���ʎ�
			if( d2 < 0 )	return Ray.INFINITY;
			return	-ds+Math.sqrt(d2);						//	����
	}
}

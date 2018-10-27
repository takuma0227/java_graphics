//	���������i�܂���j�p�̃N���X

public	class	Line{
	
	public	static	final	double	PIR=Math.PI/180.0;
	public	Vector[]	p;										//	�����̘A���_

	public	Line(Vector a1, Vector a2){
		p=new Vector[]{	a1, a2	};
	}
	public	Line(Vector[] a){
		p=a;
	}
	public	Line(Line a){
		p=new Vector[a.p.length];
		for(int i=0; i<a.p.length; i++)
			p[i]=new Vector(a.p[i]);
	}
	Line(int n){
		p=new Vector[n];
	}
	public	static	Line	 _mul(TMatrix m, Line b){
		Line c=new Line(b.p.length);
		for(int i=0; i<c.p.length; i++)c.p[i]=TMatrix._mul(m,b.p[i]);
		return c;
	}
	public	static	Line	 _mul(double d1,Line v2){
		Line	v=new Line(v2.p.length);
		for(int i=0; i<v2.p.length; i++)v.p[i]=Vector._mul(d1,v2.p[i]);
		return v;
	}
	
	//	v1��v2��A������iv1�̏I�_��v2�̎n�_���d�˂�j
	public	static	Line	 _orr(Line v1, Line v2){
		Line	 v=new Line(v1.p.length+v2.p.length-1);
		for(int i=0; i<v1.p.length; i++)v.p[i]=v1.p[i];
		for(int i=1; i<v2.p.length; i++)v.p[v1.p.length+i-1]=Vector._add(Vector._sub(v1.p[v1.p.length-1],v2.p[0]),v2.p[i]);
		return v;
	}
	public	static	Line	 _orr(Line v1,Vector v2){	//	
		Line	v=new Line(v1.p.length+1);
		for(int i=0; i<v1.p.length; i++)v.p[i]=v1.p[i];
		v.p[v1.p.length]=Vector._add(v1.p[v1.p.length-1],v2);
		return v;
	}
	public	static	Line	 _uni(Line v1,Line v2){	//	v1�̏I�_��v2�̎n�_���d�˂Ȃ�
		Line	 v=new Line(v1.p.length+v2.p.length-1);
		for(int i=0; i<v1.p.length; i++)v.p[i]=v1.p[i];
		for(int i=0; i<v2.p.length; i++)v.p[v1.p.length+i]=v2.p[i];
		return v;
	}

	//		�܂���i�Ȑ��ɋߎ������������̐����j��������֐�
	static	Line	circle_x(int n, double b, double e){	//	x���𒆐S��
			Line	l=new Line(n+1);
			double	t=Vector.PIR*b,	d=Vector.PIR*(e-b)/n;
			for(int i=0; i<n+1; i++,t+=d)l.p[i]=new Vector(0,Math.cos(t),Math.sin(t));
			return	l;
	}
	static	Line	circle_y(int n, double b, double e){	//	y���𒆐S��
			Line	l=new Line(n+1);
			double	t=Vector.PIR*b,	d=Vector.PIR*(e-b)/n;
			for(int i=0; i<n+1; i++,t+=d)l.p[i]=new Vector(Math.sin(t),0,Math.cos(t));
			return	l;
	}
	static	Line	circle_z(int n, double b, double e){	//	z���𒆐S��
			Line	l=new Line(n+1);
			double	t=Vector.PIR*b,	d=Vector.PIR*(e-b)/n;
			for(int i=0; i<n+1; i++,t+=d)l.p[i]=new Vector(Math.cos(t),Math.sin(t),0);
			return	l;
	}

	Line	extend(){										//	���[�ɐ����𑝂₷			
			int		n=p.length;
			Line	l=new Line(n+2);
			if(n==2)return new Line(new Vector[]				//	������{�̏ꍇ
				{	Vector._sub(Vector._mul(2,p[0]),p[1]),p[0],p[1],Vector._sub(Vector._mul(2,p[1]),p[0])	});
			for(int i=0; i<n; i++)l.p[i+1]=p[i];
			if((Vector._sub(p[0],p[n-1])).len()<0.5){					//	�n�_�ƏI�_����v����ꍇ
				l.p[0]	=p[n-2];
				l.p[n+1]=p[1];
			}												//	�n�_�ƏI�_����v���Ȃ��ꍇ
			l.p[0]	=Vector._add(p[0],TMatrix._mul(TMatrix.rotate(Vector._sub(p[1],p[2]),Vector._sub(p[0],p[1])),(Vector._sub(p[0],p[1]))));
			l.p[n+1]=Vector._add(p[n-1],TMatrix._mul(TMatrix.rotate(Vector._sub(p[n-2],p[n-3]),Vector._sub(p[n-1],p[n-2])),(Vector._sub(p[n-1],p[n-2]))));
			return	l;
	}
}

//		Color(COLor)�N���X�p�̋L�q

public	class	COLor{
	
	public	static	final	COLor	BLACK	= new COLor(0,0,0);	//	��
	public	static	final	COLor	RED		= new COLor(1,0,0);	//	��
	public	static	final	COLor	GREEN	= new COLor(0,1,0);	//	��
	public	static	final	COLor	BLUE	= new COLor(0,0,1);	//	��
	public	static	final	COLor	CYAN	= new COLor(0,1,1);	//	�V�A��
	public	static	final	COLor	MAGENTA	= new COLor(1,0,1);	//	��
	public	static	final	COLor	YELLOW	= new COLor(1,1,0);	//	��
	public	static	final	COLor	WHITE	= new COLor(1,1,1);	//	��

	public	float	r,g,b;										//	��,��,�̒l
	
	COLor(double r, double g, double b){
		this.r=(float)r;
		this.g=(float)g;
		this.b=(float)b;
	}
	
	//	Color�֘A�̉��Z�q�̋L�q
	public	static	COLor	 _add(COLor c1, COLor c2){
		return new COLor(	c1.r+c2.r, c1.g+c2.g, c1.b+c2.b );
	}
	public	static	COLor	 _sub(COLor c1, COLor c2){
		return new COLor(	c1.r-c2.r, c1.g-c2.g, c1.b-c2.b );
	}
	public	static	COLor	 _mul(double a, COLor c){
		return new COLor(	a*c.r, a*c.g, a*c.b );
	}
	public	static	COLor	 _mul(COLor c, double a){
		return new COLor(	a*c.r, a*c.g, a*c.b );
	}
	public	static	COLor	 _div(COLor c, double a){
		return new COLor(	c.r/a, c.g/a, c.b/a );
	}
	public	static	COLor	 _mul(COLor c1, COLor c2){
		return new COLor(	c1.r*c2.r, c1.g*c2.g, c1.b*c2.b );
	}
}

//		�Q�����`��p�̃N���X��`

public	class	Win2D{											//  Win2D�p�E�C���h�E

		int		size_x=400,	size_y=350;							//	�E�C���h�E�T�C�Y
		Vector	org=new Vector(size_x/2,size_y/2);				//  ���_�̈ʒu�i�X�N���[�����W�j
		COLor	bc=COLor.WHITE;									//	�w�i�F(�����l)
		COLor	fc=COLor.BLACK;									//	�`��F(�����l)
		TMatrix	t;												//	�ϊ��s��i�W�����W=>screen���W�j
		Screen	w;

		public	Win2D(String title, int ix, int iy)	{			//	�E�C���h�E���J��
				init(title, ix, iy, bc);
		}
		public	Win2D(String title, int ix, int iy, COLor ic){	//	�E�C���h�E���J��
				init(title, ix, iy, ic);
		}
		public	Win2D(String title, String fn){					//	�摜�t�@�C����ǂݍ���
				init(title, size_x, size_y, bc);
				read(fn);	
		}

		void	init(String title,int x, int y, COLor c){
			size_x=x;			size_y=y;
			setOrigin(x/2,y/2);
			bc=c;
			w=new Screen(title, x, y, c);
		}
		public	void	close(){								//	�E�C���h�E�����
				w.close();
		}
		
		public	void	setOrigin(int x, int y){				//	���_�̈ʒu�ݒ�(screen���W)
				org=new Vector(x+.5,y-.5,0);
				t=TMatrix._mul(TMatrix.move(org),TMatrix.scale(1,-1,1));
		}
		public	void	setColor(COLor c){						//	�`��F��ݒ�
				fc=c;
				w.setColor(c);
		}

		public 	void	point(double x, double y){				//	�_��`��(screen���W)
				w.point((int)x,(int)y);
		}
		
		public 	void	point(Vector p){							//	�_��`��
				Vector	a=TMatrix._mul(t,p);
				w.point(a.x,a.y);
		}
		public 	void	draw(Vector p0,Vector p1){				//	������`��
				Vector	a=TMatrix._mul(t,p0),	b=TMatrix._mul(t,p1);
				w.draw( a.x,a.y, b.x,b.y );
		}
		public	void	draw (Vector[] p){						//	������`��
				int		n=p.length;
				int[]	xp=new int[n],	yp=new int[n];
				for(int i=0; i<n; i++){
					Vector	pp=TMatrix._mul(t,p[i]);
					xp[i]=(int)pp.x;	yp[i]=(int)pp.y;
				}
				w.draw(xp,yp,n);
		}
		public	void	draw (Line l)	{						//	������`��
				draw(l.p);	
		}
		public	void	text(Vector p, String s){				//	�������`��
				Vector	a=TMatrix._mul(t,p);
				w.text(a.x,a.y,s);
		}
		
		public	void	drawAxis(){								//	���W����`��
				w.setColor(COLor.BLACK);
				draw(new Vector(-org.x+15,0),new Vector(size_x-org.x-15,0));
				text(new Vector(size_x-org.x-6,0),"X");
				draw(new Vector(0,-size_y+org.y+20),new Vector(0,org.y-20));
				text(new Vector(0,org.y-12),"Y");
				w.setColor(COLor.BLACK);
		}
		
		public 	COLor	getColor(double x, double y){			//	�`��F��Ԃ�(screen���W)	
				return w.getColor((int)x,(int)y);
		}
		public 	COLor	getColor(Vector p){						//	�`��F��Ԃ�	
				return w.getColor((int)(org.x+p.x),(int)(org.y-p.y));
		}
		public 	int		read(String	fn){						//	�摜�t�@�C����Ǎ���
				int	rc=w.read(fn);
				size_x=w.size_x;	size_y=w.size_y;
				org.x=size_x/2+.5;	org.y=size_y/2-.5;
				return	rc;
		}
}

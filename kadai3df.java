public class kadai3df {
	static int X_SIZE = 900, Y_SIZE = 900;	//�E�B���h�E�T�C�Y
	static double[][] z_buff = new double[X_SIZE][Y_SIZE];	//z�o�b�t�@�̒�`


	public static void main(String[] args) {

		for(int x=0; x < X_SIZE; x++) 		//z�o�b�t�@�̏�����
			for(int y = 0; y<Y_SIZE;y++) z_buff[x][y] = -1e30;

		Win3D W1 = new Win3D("�t�H���V�F�[�f�B���O", X_SIZE, Y_SIZE, COLor.WHITE);

		Surface Sf;
		Line La, Lb;
		int hsum,t,h;
		Polygon p;

		Vector pv = new Vector(900,900,900);	//���_�̋L�q
		Vector li = new Vector(0, 900, 1200);	//�Ɩ����̋L�q�i���W�j
		W1.setView(pv);
		W1.drawAxis();
		W1.setColor(COLor.BLACK);

		hsum = -100; t = 0;	h = 64;
		for(int k = 0; k < 20; k++) {
			//�����}�`�i�����j�̐���
			La = new Line(new Vector[] {
				new Vector(0, 0, hsum), new Vector(0, 0, hsum),
				new Vector(h, 0, hsum), new Vector(h, 0, hsum + h),
				new Vector(h, 0, hsum + h), new Vector(h, 0, hsum),
				new Vector(0, 0, hsum)
			});
			Sf = Surface.revolve_z(La, 20);

			for(int i = 0; i < Sf.n; i++)
				paint(W1, Sf.getPolygon(i), Sf.getNormal(i), li, COLor.CYAN);	//��]�̂̕`��

			hsum += h;
			h -= 4;
		}
	}

	//	�|���S����h��Ԃ�
	static void paint(Win3D W1, Polygon Pf, Polygon Pn, Vector li, COLor ca) {

		int top = 0, bot = 0, lef = 0, rig = 0, n = Pf.p.length - 1;	//�㉺���E�̓Y���A���_��
		Vector[] pp = new Vector[n];
		for(int i =0; i<n;i++) {
			pp[i] = W1.screen(Pf.p[i]);	//screen���W�̌v�Z
			if(pp[top].y > pp[i].y) lef = rig = top = i;	//�㕔�̓Y��
			if(pp[bot].y < pp[i].y) bot = i;				//�����̓Y��
		}

		for(int y=(int)pp[top].y; y <= (int)pp[bot].y; y++) {
			if(y<0 || Y_SIZE <= y) continue;
			if((int)pp[(lef+1)%n].y<=y) lef = (lef+1)%n;	//	����̓Y����
			if((int)pp[(rig-1+n)%n].y<=y) rig = (rig-1+n)%n;	//�E��̓Y����
			Vector p1 = pp[lef], p2 = pp[(lef+1)%n];		//����A�����̍��W
			Vector p0 = pp[rig], p3 = pp[(rig-1+n)%n];		//�E��A�E���̍��W
			Vector n1 = Pn.p[lef], n2 = Pn.p[(lef+1)%n];	//����A�����̖@���x�N�g��
			Vector n0 = Pn.p[rig], n3 = Pn.p[(rig-1+n)%n];	//�E��A�E���̖@���x�N�g��
			Vector p4 = sup(p1,p2,y-(int)p1.y, (int)p2.y-y);	//y�����̕��
			Vector n4 = sup(n1, n2, y-(int)p1.y, (int)p2.y-y);
			Vector p5 = sup(p3,p0,(int)p3.y-y, y-(int)p0.y);
			Vector n5 = sup(n3, n0, (int)p3.y-y, y-(int)p0.y);
			for(int x = (int)p4.x; x<=(int)p5.x;x++) {	//x�����̕��
				if(x<0 || X_SIZE <=x) continue;
				Vector P = sup(p4,p5,x-(int)p4.x,(int)p5.x-x);	//�`��_���W�̌v�Z
				Vector N = sup(n4, n5, x-(int)p4.x, (int)p5.x-x).unit();	//�@���x�N�g���̌v�Z	
				if(z_buff[x][y]<P.z) {
					Vector v = (Vector._sub(W1.world(P), W1.v0)).unit();	//�����x�N�g���̌v�Z
					Vector l = (Vector._sub(W1.world(P), li)).unit();		//�Ɩ��x�N�g���̌v�Z
					W1.setColor(shading(v,l,N,ca));
					W1.point(x,y);	//screen���W�ŕ`��
					z_buff[x][y] = P.z;
				}

			}

		}
	}

	//���ˌ����v�Z����@v:�����x�N�g�� l:�Ɩ����̕����x�N�g�� n:�\�ʂ̖@���x�N�g��
	static COLor shading(Vector v, Vector l, Vector n, COLor ca) {
		double kd = 0.7, ks = 0.7, ke =0.3;	//�g�U���ˌW���A���ʔ��ˌW���A����
			Vector r = Vector._sub(l, Vector._mul(2*(Vector._mul(l, n)), n));	//���˕����x�N�g��
			return COLor._add(COLor._add(COLor._mul(kd*Math.max(Vector._mul(Vector._mul(-1, n), l), 0), ca),	//�g�U���ˌ�
			COLor._mul(ks*Math.pow(Math.max(Vector._mul(Vector._mul(-1, r), v), 0), 20), COLor.WHITE)),	//���ʔ��ˌ�
			COLor._mul(ke, ca));	//����

	}

	static Vector sup(Vector p1, Vector p2, double a1, double a2) {	//p1, p2���Ԃ���
		if(a1+a2 ==0) return p1;
		return Vector._div((Vector._add(Vector._mul(a2, p1), Vector._mul(a1, p2))), (a1+a2));

	}
}
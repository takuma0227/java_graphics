public class Samballs {
	public static void main(String[] args) {	//���̂�\������i���C�g���[�V���O�@�j
		
		int num = 10;

		Ball Ba[] = new Ball[num];
		COLor C[] = new COLor[num];
		
		for(int i=0; i<num; i++) {
			Ba[i] = new Ball(new Vector(200-i*10,200- i*10, 0),200- i*10);	//���̂̋L�q
			C[i] = new COLor(1-0.1*i, 0.1*i, 0.1*i);
		}
		Vector L0 = new Vector(0, 800, 500);	//�Ɩ����̋L�q
		Vector V0 = new Vector(1000,1000, 0);	//���_�̋L�q
		Win3D W1 = new Win3D("���̂̕`��i���C�g���[�V���O�j", 800, 800);
		W1.setView(V0);

		for(int x=0; x<W1.size_x; x++) {
			for(int y=0; y<W1.size_y; y++) {
				Vector V = (Vector._sub(W1.world(new Vector(x, y, -W1.dv)), V0)).unit();	//�����x�N�g���̌v�Z
				Ray VR = new Ray(V0, V);	//�����̋L�q
				Vector P = new Vector();	//��_���W
				Vector N = new Vector();
				Vector L = (Vector._sub(P, L0)).unit();	//�Ɩ����̕����x�N�g��
				COLor  Cs = COLor.WHITE;
				for(int i=0; i<num;i++) {
					if(Ba[i].hit(VR, P, N)<Ray.INFINITY) {	//���������̂ƌ���邩�̔���
						Cs = VR.shading(L, N, C[i]);	//����i�̐F
						Ball t = Ba[i];
						for(int j=i+1;j<num;j++) {
							if(Ba[j].hit(VR, P, N)<t.hit(VR, P, N)) {	//���̋��̂ƌ���邩�̔���
								Cs = VR.shading(L, N, C[j]);	//���̋��̂���O�ɂ���ꍇ���̐F
								t = Ba[j];
							}
						}
					}
				}
				W1.setColor(Cs);	//�`��F�̐ݒ�
				W1.w.point(x, y);	//screen���W�ŕ`��
			}
		}
	}
}

public class Samball {
	public static void main(String[] args) {	//���̂�\������i���C�g���[�V���O�@�j

		Ball Ba = new Ball(new Vector(0,0,0), 100);	//���̂̋L�q
		Vector L0 = new Vector(0, 500, 500);	//�Ɩ����̋L�q
		Vector V0 = new Vector(500, 500, 500);	//���_�̋L�q
		Win3D W1 = new Win3D("���̂̕`��i���C�g���[�V���O�j", 400, 500, COLor.WHITE);
		W1.setView(V0);

		for(int x=0; x<W1.size_x; x++) {
			for(int y=0; y<W1.size_y; y++) {
				Vector V = (Vector._sub(W1.world(new Vector(x, y, -W1.dv)), V0)).unit();	//�����x�N�g���̌v�Z
				Ray VR = new Ray(V0, V);	//�����̋L�q
				Vector P = new Vector();	//��_���W
				Vector N = new Vector();
				if(Ba.hit(VR, P, N)<Ray.INFINITY) {	//���������̂ƌ���邩�̔���
					Vector L = (Vector._sub(P, L0)).unit();	//�Ɩ����̕����x�N�g��
					W1.setColor(VR.shading(L, N, COLor.RED));	//�`��F�̐ݒ�
					W1.w.point(x, y);	//screen���W�ŕ`��
				}
			}
		}
	}
}

//��]�X�C�[�v�ɂ�闧�́i���C���[�t���[���j��`�悷��
//Surface�N���X���g�p����

public class Sample72 {
	public static void main(String[] args) {
		Win3D W1 = new Win3D("���C���[�t���[��", 1000, 350, COLor.WHITE);
		W1.drawAxis();
		W1.setColor(COLor.BLACK);
		
		Line La = new Line(new Vector( 0, 0, 100), new Vector(50, 0, 0));	//�~���̑f�i�����j�̐���
		Surface Sf = Surface.revolve_z(La, 20);							//�~���i�|���S���W���j�̐���
		W1.draw(Surface._mul(TMatrix.move(0, -150, 0), Sf));				//�~���̕`��
		
		La = Line._mul(50, Line.circle_y(10, 0, 180));					//���̂̑f�i���~�j�̐���
		Sf = Surface.revolve_z(La, 10);									//���́i�|���S���W���j�̐���
		W1.draw(Surface._mul(TMatrix.move(0,150,0), Sf));					//���̂̕`��
		
		La = Line._mul(20, Line.circle_y(8, 0, 360));						//�g�[���X�̑f�i�~�j�̐���
		Sf = Surface.revolve_z(Line._mul(TMatrix.move(50, 0, 0),La),20);	//�g�[���X�̐���
		W1.draw(Sf);														//�g�[���X�̕`��
		
																		//�����}�`�i�����j�̐���
		La = new Line(new Vector[] {
				new Vector(0, 0, 0), new Vector(0, 0, 20),
				new Vector(80, 0, 20), new Vector(80, 0, 100),
				new Vector(100, 0, 100), new Vector(100, 0, 0),
				new Vector(0, 0, 0)
		});
		Sf = Surface.revolve_z(La, 20);									//��]�X�C�[�v�ɂ���]�̂̐���
		W1.draw(Surface._mul(TMatrix.move(0, 350, 0), Sf));				//��]�̂̕`��
	}
}

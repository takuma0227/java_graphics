//���s�ړ��X�C�[�v�ɂ�闧�́i���C���[�t���[���j��`�悷��
//Surface�N���X���g�p����

public class Sample71 {
	public static void main(String[] args){
		Win3D W1  = new Win3D("���C���[�t���[��",650,550,COLor.WHITE);
		W1.drawAxis();
		W1.setColor(COLor.BLACK);
		
		//�����`�i�����j�̐���
		Line La = new Line(new Vector[] {
							new Vector( 0, 0, 0), new Vector( 0, 0,50),
							new Vector(50, 0,50), new Vector(50, 0, 0),
							new Vector( 0, 0, 0)
		});
		Line Lb = new Line(new Vector( 0, 0, 0), new Vector( 0,50, 0));	//�X�C�[�v�̐����̐���
		Surface Sf = Surface.sweep_xz(La, Lb);								//�X�C�[�v�ɂ�闧���̂̐���
		W1.draw(Surface._mul(TMatrix.move(0,-100,0), Sf));					//�����̂̕`��
		
		//�����}�`�i�����j�̐���
		La = new Line(new Vector[] {
					  new Vector( 0, 0, 0), new Vector( 0, 0,20),
					  new Vector(90, 0,20), new Vector(90, 0,100),
					  new Vector(100, 0,100), new Vector(100, 0, 0),
					  new Vector(0,0,0)
		});
		Lb = new Line(new Vector(0,0,0), new Vector(0,100,0));				//�X�C�[�v�̐����̐���
		Sf = Surface.sweep_xz(La, Lb);										//�X�C�[�v�ɂ�闧�̂̐���
		W1.draw(Surface._mul(TMatrix.move(0,100,0), Sf));					//���̂̕`��
	}
}

//		�����C�_�̕`����@�i�Q�����j

public class ex1_1{	
	public static void main(String[] args){

		Win2D	W=new Win2D("�����C�_�̕`��", 400, 350);	//	�E�C���h�E����
		W.drawAxis();               						//	���W���̕\��

		Vector[]	p=new Vector[]{	new Vector( 50, 50),			//	�_�z��
								new Vector(100, 50),
								new Vector(100,100) };

		Line	l=new Line( p );							//	�����̋L�q
					
		W.setColor( COLor.BLUE );							//	�`��F�̐ݒ�
		W.draw ( l );										//	�����̕`��

		W.setColor( COLor.RED );							//	�`��F�̐ݒ�
		W.point(new Vector(50,-50) );						//	�_�̕`��
	}
}

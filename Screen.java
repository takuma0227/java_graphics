//	�X�N���[���p�̃N���X
import java.awt.*; 
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*; 
import javax.imageio.*; 
import java.io.*;

public	class	Screen extends JPanel implements Runnable{	//  �X�N���[���p�E�C���h�E

	int		size_x=400,	size_y=350;							//	�E�C���h�E�T�C�Y(�����l)
	Color	bc=Color.WHITE;									//	�w�i�F(�����l)
	Color	fc=Color.BLACK;									//	�`��F(�����l)
	BufferedImage ibf;										//	�摜�o�b�t�@
	Graphics gb;											//	�摜�o�b�t�@��Graphics�I�u�W�F�N�g
	Thread	tthr;											//	�^�C�}�p�X���b�h
	JFrame	frm	;

	public	Screen(String title){
			init(title, size_x, size_y, bc);
	}
	public	Screen(String title, int ix, int iy, COLor c)	{
			init(title, ix, iy, new Color(c.r, c.g, c.b) );
	}
	public	Screen(String title, String fn){
			init(title, size_x, size_y, bc);
			read(fn);
	}

	void	init(String title,int x, int y, Color c){
		frm=new JFrame(title);
		frm.getContentPane().add(this);
		frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		size_x=x;			size_y=y;
		frm.setSize(size_x+8,size_y+34);
		frm.setVisible(true);

		bc=c;
		ibf=new BufferedImage(size_x, size_y,BufferedImage.TYPE_INT_RGB);//	�摜�o�b�t�@�̏����ݒ�
		gb=ibf.getGraphics();								//	�摜�o�b�t�@��Graphics�I�u�W�F�N�g
		gb.setColor(bc);
		gb.fillRect(0,0,size_x,size_y);
		gb.setColor(fc);
        addMouseListener(new MouseAdapter(){				//	�}�E�X�N���b�N�i�ۑ��j
			public	void	mouseClicked(MouseEvent me){
					if(me.getButton()==MouseEvent.BUTTON3)	//	�E�{�^��
						write(null,frm.getTitle()+".bmp");
			}
        });
		
		start();											//	�X���b�h�̃X�^�[�g
	}

	public	void	close(){								//	�E�C���h�E�����
		removeAll();
		tthr=null;
		frm.setVisible(false);
		frm=null;
		ibf=null;
		gb =null;
	}
	
	public	void	clear(){
	}
	public 	void	point(double x, double y){				//	�_�̕`��
			gb.drawLine( (int)x,(int)y, (int)x,(int)y );
	}
	public 	void	draw(double x, double y,double x1, double y1){//	�����̕`��
			gb.drawLine( (int)x,(int)y, (int)x1,(int)y1 );
	}
	public	void	draw(int[] xp, int[] yp, int n)	{		//	���������̕`��
			gb.drawPolyline(xp,yp,n);
	}
	public	void	paint(int[] xp, int[] yp, int n){		//	�h��ׂ�
			gb.fillPolygon(xp,yp,n);
	}

	public	void	text(double x, double y,String s){		//	������̕`��
		gb.drawString(s,(int)x-4,(int)y+4);
	}

	public	void	setColor(COLor c){						//	�`��F�̐ݒ�
			fc=new Color(	(float)Math.max(Math.min(c.r,1f),0f),
							(float)Math.max(Math.min(c.g,1f),0f),
							(float)Math.max(Math.min(c.b,1f),0f)	);
			gb.setColor( fc );
	}
	
	public 	COLor	getColor(double x, double y){			//	�F��Ԃ�	
			Color	c=new Color(ibf.getRGB(((int)x+size_x)%size_x,
										((int)y+size_y)%size_y));
			return	new COLor(	(c.getRed()/255.),
								(c.getGreen()/255.),
								(c.getBlue()/255.)	);
	}
	
	public 	int		read(String	fn){						//	�摜�t�@�C���Ǎ���
			File file = new File(fn);
			if (!file.exists()){							//	�t�@�C���̑��݊m�F
				final JFileChooser	f=new JFileChooser();
				if(f.showOpenDialog(Screen.this)
						==JFileChooser.APPROVE_OPTION){		//	�I��
					file=f.getSelectedFile();
				}
				else return -1;
			}
			try{
				ibf = ImageIO.read(file);
				size_x=ibf.getWidth();
				size_y=ibf.getHeight();
				frm.setSize(size_x+8,size_y+34);
				frm.setVisible(true);
			}catch(FileNotFoundException e){
				System.out.println("�t�@�C����������܂���!! "+fn );
				return -1;
			}catch(IOException e){
				System.out.println(e);
			}
			return 1;
	}

	public 	int		write(String fn){						//	�摜�t�@�C��������
			return	write(fn,null);
	}
	public 	int		write(String fn,String ff){				//	�摜�t�@�C��������
			File file;
			if (fn==null){									//	�t�@�C���̑��݊m�F
				final JFileChooser f=new JFileChooser("./");
				if(f.showSaveDialog(Screen.this)
						==JFileChooser.APPROVE_OPTION){		//	�t�@�C��������
						file=f.getSelectedFile();
				}
				else	return -1;
			}
			else	file= new File(fn);
			try {
			    ImageIO.write(ibf, "bmp", file);
			}catch(IOException e){
				System.out.println(e);
			}
			return 1;
	}
	
	public	void paintComponent(Graphics g){				//	�ĕ`��		
		super.paintComponent(g);
		g.drawImage(ibf, 0, 0, this);						//	�摜�o�b�t�@��\��
	}

	void start(){											//	�^�C�}�����p
		tthr = new Thread(this);							//	�X���b�h�̐���
		tthr.start();										//	�X���b�h�̃X�^�[�g
	}

	public	void run(){
		Thread nowThread = Thread.currentThread();
		while (tthr == nowThread){
			try{
				Thread.sleep(100);							//	�^�C�}�[�i100m�b�j
			}
			catch (InterruptedException e){
				break;
			}
			repaint();										//	�ĕ`��
		}
	}

	void stop(){
		if (tthr != null)tthr = null;
	}
}

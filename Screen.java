//	スクリーン用のクラス
import java.awt.*; 
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*; 
import javax.imageio.*; 
import java.io.*;

public	class	Screen extends JPanel implements Runnable{	//  スクリーン用ウインドウ

	int		size_x=400,	size_y=350;							//	ウインドウサイズ(初期値)
	Color	bc=Color.WHITE;									//	背景色(初期値)
	Color	fc=Color.BLACK;									//	描画色(初期値)
	BufferedImage ibf;										//	画像バッファ
	Graphics gb;											//	画像バッファのGraphicsオブジェクト
	Thread	tthr;											//	タイマ用スレッド
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
		ibf=new BufferedImage(size_x, size_y,BufferedImage.TYPE_INT_RGB);//	画像バッファの初期設定
		gb=ibf.getGraphics();								//	画像バッファのGraphicsオブジェクト
		gb.setColor(bc);
		gb.fillRect(0,0,size_x,size_y);
		gb.setColor(fc);
        addMouseListener(new MouseAdapter(){				//	マウスクリック（保存）
			public	void	mouseClicked(MouseEvent me){
					if(me.getButton()==MouseEvent.BUTTON3)	//	右ボタン
						write(null,frm.getTitle()+".bmp");
			}
        });
		
		start();											//	スレッドのスタート
	}

	public	void	close(){								//	ウインドウを閉じる
		removeAll();
		tthr=null;
		frm.setVisible(false);
		frm=null;
		ibf=null;
		gb =null;
	}
	
	public	void	clear(){
	}
	public 	void	point(double x, double y){				//	点の描画
			gb.drawLine( (int)x,(int)y, (int)x,(int)y );
	}
	public 	void	draw(double x, double y,double x1, double y1){//	線分の描画
			gb.drawLine( (int)x,(int)y, (int)x1,(int)y1 );
	}
	public	void	draw(int[] xp, int[] yp, int n)	{		//	複数線分の描画
			gb.drawPolyline(xp,yp,n);
	}
	public	void	paint(int[] xp, int[] yp, int n){		//	塗り潰し
			gb.fillPolygon(xp,yp,n);
	}

	public	void	text(double x, double y,String s){		//	文字列の描画
		gb.drawString(s,(int)x-4,(int)y+4);
	}

	public	void	setColor(COLor c){						//	描画色の設定
			fc=new Color(	(float)Math.max(Math.min(c.r,1f),0f),
							(float)Math.max(Math.min(c.g,1f),0f),
							(float)Math.max(Math.min(c.b,1f),0f)	);
			gb.setColor( fc );
	}
	
	public 	COLor	getColor(double x, double y){			//	色を返す	
			Color	c=new Color(ibf.getRGB(((int)x+size_x)%size_x,
										((int)y+size_y)%size_y));
			return	new COLor(	(c.getRed()/255.),
								(c.getGreen()/255.),
								(c.getBlue()/255.)	);
	}
	
	public 	int		read(String	fn){						//	画像ファイル読込み
			File file = new File(fn);
			if (!file.exists()){							//	ファイルの存在確認
				final JFileChooser	f=new JFileChooser();
				if(f.showOpenDialog(Screen.this)
						==JFileChooser.APPROVE_OPTION){		//	選択
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
				System.out.println("ファイルが見つかりません!! "+fn );
				return -1;
			}catch(IOException e){
				System.out.println(e);
			}
			return 1;
	}

	public 	int		write(String fn){						//	画像ファイル書込み
			return	write(fn,null);
	}
	public 	int		write(String fn,String ff){				//	画像ファイル書込み
			File file;
			if (fn==null){									//	ファイルの存在確認
				final JFileChooser f=new JFileChooser("./");
				if(f.showSaveDialog(Screen.this)
						==JFileChooser.APPROVE_OPTION){		//	ファイル名入力
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
	
	public	void paintComponent(Graphics g){				//	再描画		
		super.paintComponent(g);
		g.drawImage(ibf, 0, 0, this);						//	画像バッファを表示
	}

	void start(){											//	タイマ処理用
		tthr = new Thread(this);							//	スレッドの生成
		tthr.start();										//	スレッドのスタート
	}

	public	void run(){
		Thread nowThread = Thread.currentThread();
		while (tthr == nowThread){
			try{
				Thread.sleep(100);							//	タイマー（100m秒）
			}
			catch (InterruptedException e){
				break;
			}
			repaint();										//	再描画
		}
	}

	void stop(){
		if (tthr != null)tthr = null;
	}
}

package com.itheima.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.itheima.mario.Mario;
import com.itheima.role.*;
import com.itheima.util.Map;
import com.itheima.util.MusicUtil;
/**
   ���崰�ڽ��棺չʾ��ɫ��

 */
public class GameFrame extends JFrame{
	// ��������:������Ҫһ�����������ġ�
	public Mario mario;
	// �ֱ���:ˮ�ܣ���Һ�ש��
	public Enemy pipe ,cion , brick,staticbrick,mushroom;
	badFlower badflower;
	Trap trap;
	//����ͼƬ
	public BackgroundImage bg;
	//����һ����������װ���˶��� ���ˣ��ϰ������ȵ�
	public ArrayList<Enemy> eneryList = new ArrayList<Enemy>();
	//����һ����������װ�ӵ�
	public ArrayList<Boom> boomList = new ArrayList<Boom>();
	//�ӵ����ٶ�
	public int bspeed=0;

	//��ͼ���ݣ��ƶ�������1��שͷ����2����ң���3��ˮ��
	public int[][] map = null;
	{   //�������飺ÿ��ʵ��������ʱ��������һ��
		// ʵ��������г�ʼ����ͼ��Դ������
		Map mp = new Map();
		map = mp.readMap();
	}

	//���캯�������ʼ������ͼƬ������¶���
	public GameFrame() throws Exception {
		//��ʼ���������������Ϣ����
		// this�����˵�ǰ���������
		this.setSize(800,450);
		this.setTitle("��������");
		this.setResizable(false);
		// ����չʾ����
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		// ������������
		mario = new Mario(this);

		// ��������ͼƬ
		bg = new BackgroundImage();

		// ��ȡ��ͼ�������õ�ͼ
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				//��ȡ������1����שͷ
				if(map[i][j]==1){
					// x
					brick = new Brick(j*30,i*30,30,30,new ImageIcon("image/brick.png").getImage());
					eneryList.add(brick);
				}
				//����2�����
				if(map[i][j]==2){
					cion = new Coin(j*30,i*30,30,30,new ImageIcon("image/coin_brick.png").getImage());
					eneryList.add(cion);
				}
				//����3��ˮ��
				if(map[i][j]==3){
					pipe = new Pipe(j*30,i*30,60,120,new ImageIcon("image/pipe.png").getImage());
					eneryList.add(pipe);
				}
				//����4��ʳ�˻�
                if(map[i][j]==4){
                    badflower=new badFlower(j*30+10,i*30+5,40,40,new ImageIcon("images/badflower/0.png").getImage());
                    pipe = new Pipe(j*30,i*30+40,60,80,new ImageIcon("image/pipe.png").getImage());
                    //����ʳ�˻���ʱ��
                    TimerTask task=new TimerTask() {
                        @Override
                        public void run() {
                            while(true){
                                badflower.move();
                            }
                        }
                    };
                    long delay=0;
                    long intevalPeriod=1000;
                    Timer timer=new Timer();
                    timer.scheduleAtFixedRate(task,delay,intevalPeriod);
                    eneryList.add(badflower);
                    eneryList.add(pipe);
                }
                //����5 ������
                if(map[i][j]==5){
                    trap=new Trap(j*30,358+32,30,30,new ImageIcon("image/blue.png").getImage());
                    eneryList.add(trap);
                }

                // ����6����Ģ��ש��
                if (map[i][j] == 6) {
                    mushroom = new Mushroom(j * 30, i * 30, 30, 30, new ImageIcon("image/coin_brick.png").getImage());
                    eneryList.add(mushroom);
                }

                //����7����Ӳש���޷��ƻ���
                if (map[i][j] == 7)
                {
                    staticbrick = new staticBrick(j * 30, i * 30, 30, 30,null);
                    eneryList.add(staticbrick);
                }
			}
		}

        //�����߳�����£��Ϳ��Զ���
		mario.start();

		//����һ���̸߳������Ĵ����ػ��߳�
		new Thread(){
			public void run(){
				while(true){
					//�ػ洰��
					repaint(); // �Զ�������ǰ�����е�paint����
					//����ӵ��Ƿ����
					//checkBoom();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();


		//���ñ�������
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				MusicUtil.playBackground();
//			}
//		}).start();
	}

	@Override
	public void paint(Graphics g) {
		//����˫���廭����ͼƬ�������
		BufferedImage bi =(BufferedImage)this.createImage(this.getSize().width,this.getSize().height);
		Graphics big = bi.getGraphics();
		big.drawImage(bg.img, bg.x, bg.y, null);

		// ��ʼ���ƽ����ϵ��ϰ��
		for (int i = 0; i < eneryList.size(); i++) {
			Enemy e = eneryList.get(i);
			big.drawImage(e.img, e.x, e.y, e.width, e.height,null);
		}

		//���ӵ�
		for (int i = 0; i < boomList.size(); i++) {
			Boom b =boomList.get(i);
			Color c =big.getColor();
			big.setColor(Color.red);
			big.fillOval(b.x+=b.speed, b.y, b.width, b.width);
			big.setColor(c);
		}

		//������ �����Լ�
		big.drawImage(mario.img, mario.x, mario.y, mario.width, mario.height,null);
		g.drawImage(bi,0,0,null);

	}

	//����ӵ��Ƿ���磬��������������Ƴ������Ƴ��Ļ����ڴ��й©
	public void checkBoom(){
		for (int i = 0; i < boomList.size(); i++) {
			Boom b = boomList.get(i);
			if(b.x<0 || b.x>800){
				boomList.remove(i);
			}
		}
	}

    public static void main(String[] args) throws Exception{
        GameFrame frame=new GameFrame();
    }
}

package com.itheima.role;

import java.awt.Image;

//�ϰ���ĳ�����
public abstract class Enemy extends Thread{
	public int x,y;
	public int width,height;
	public Image img;
	public Enemy(int x, int y, int width, int height, Image img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img=img;
	}
	public void setImg(Image img){
	    this.img=img;
    }
    public void setX(int x){
        this.x+=x;
    }
    public void setY(int y){
	    this.y+=y;
    }
    public void die(){
        this.y=450;
    }
}

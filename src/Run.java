import com.itheima.ui.GameFrame;
import com.itheima.util.KeyListener;
/**
  超级玛丽启动类。
 */
public class Run {
	//主函数，程序入口
	public static void main(String[] args) throws Exception {
	    //通过调用GameFrame空参构造器 绘制一个马里奥的界面
		GameFrame gf = new GameFrame();
		// 创建监听器对象
		KeyListener kl = new KeyListener(gf);
		// 给窗体添加键盘监听器
		gf.addKeyListener(kl);
	}
}

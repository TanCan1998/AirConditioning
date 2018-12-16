import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;

public class LoadingFrame extends JFrame {
	protected JLabel label;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9047408894334679159L;
	private JPanel contentPane;
	static int xOld, yOld;
	private LoadingFrame lf = this;
	
	/**
	 * Create the frame.
	 */
	public LoadingFrame() {
		setAlwaysOnTop(true);
		try {
			this.setUndecorated(true); // 禁用或启用此窗体的修饰
		    this.setBackground(new Color(0,0,0,0));
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); // 设置成nimbus风格
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		setResizable(false);
		// 设置窗体图标
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/img/1.png"));
		setIconImage(icon.getImage());
		// 设置窗体按钮
		contentPane = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 26727233793219801L;

			public void paintComponent(Graphics g) {
				//圆角Panel
				int fieldX = 0;
				int fieldY = 0;
				int fieldWeight = getSize().width;
				int fieldHeight = getSize().height;
				RoundRectangle2D rect = new RoundRectangle2D.Double(fieldX, fieldY,
					fieldWeight, fieldHeight, 260, 260);
				g.setClip(rect);
				//载入背景
				ImageIcon img = new ImageIcon(this.getClass().getResource("/img/2.gif"));
				// 下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
				g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("载入中......",SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD | Font.ITALIC, 16));
		label.setForeground(new Color(60, 199, 90));
		label.setBounds(52, 148, 121, 15);
		contentPane.add(label);
		setBounds(100, 100, 227, 227);
		// 界面显示居中
		Dimension screen = this.getToolkit().getScreenSize();
		this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
		
		// 为移动窗口添加鼠标事件
				this.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						xOld = e.getX();// 记录鼠标按下时的坐标
						yOld = e.getY();
					}
				});
				this.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						int xOnScreen = e.getXOnScreen();
						int yOnScreen = e.getYOnScreen();
						int xx = xOnScreen - xOld;
						int yy = yOnScreen - yOld;
						lf.setLocation(xx, yy);// 设置拖拽后，窗口的位置
					}
				});
	}
}

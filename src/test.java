import java.awt.* ;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class test extends JFrame{

	public test() {
		showFrame();
	}
	
	public void showFrame() {
		setSize(490, 630) ;		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//컨테이너의 레이아웃 변경
		//방법1. 해당 컨테이너 객체 생성 시 생성자에 레이아웃 변경 코드 사용
		//   =>new XXXLayout([옵션]);
//		JPanel p=new JPanel(new BorderLayout());	//JPanel을 BorderLayout으로 변경
		
		//방법2. 해당 컨테이너 객체 생성 후 setLayout()메서드로 변경
		JPanel p=new JPanel();
		p.setLayout(new BorderLayout());
		
		//확인하깅
		add(p, BorderLayout.CENTER);

		setVisible(true);
	}
	public static void main(String[] args) {
		new test();
	}
}
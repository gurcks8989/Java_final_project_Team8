import java.awt.* ;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class test extends JFrame{

	public test() {
		showFrame();
	}
	
	public void showFrame() {
		setSize(500, 670) ;		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//컨테이너의 레이아웃 변경
		//방법1. 해당 컨테이너 객체 생성 시 생성자에 레이아웃 변경 코드 사용
		//   =>new XXXLayout([옵션]);
//		JPanel p=new JPanel(new BorderLayout());	//JPanel을 BorderLayout으로 변경
		
		//방법2. 해당 컨테이너 객체 생성 후 setLayout()메서드로 변경
		//JPanel p=new JPanel();

		int wall = 48 ;
		
		setLayout(new GridLayout(8, 1));
		
		JPanel P1 = new JPanel() ;
		JPanel P2 = new JPanel() ;
		add(P1) ;
		add(P2) ;
		
		JButton btn[] = new JButton[4] ;
		String btnText[] = {"Undo", "Clear", "", "Menu"} ;
		Font font = new Font("DialogIntput",Font.BOLD,10) ;
		
		P2.setLayout(new BorderLayout()) ;
		
		JPanel LEFT = new JPanel() ;
		JPanel RIGHT = new JPanel() ;
		JPanel UP = new JPanel() ;
		JPanel DOWN = new JPanel() ;
		
		LEFT.setPreferredSize(new Dimension(wall, 100));
		RIGHT.setPreferredSize(new Dimension(wall, 100));
		UP.setPreferredSize(new Dimension(500, 10));
		DOWN.setPreferredSize(new Dimension(500, 10));
		
		P2.add(LEFT, BorderLayout.WEST) ;
		P2.add(RIGHT, BorderLayout.EAST) ;
		P2.add(UP, BorderLayout.NORTH) ;
		P2.add(DOWN, BorderLayout.SOUTH) ;
		
		JPanel P3 = new JPanel() ;
		P3.setLayout(new GridLayout(1, 4, wall, 5)) ;
		P2.add(P3, BorderLayout.CENTER) ;
		
		
		for(int i = 0; i < 4; i++) {
			btn[i] = new JButton(btnText[i]) ;	
			btn[i].setFont(font);
			btn[i].setBackground(Color.cyan) ;
			//btn[i].addActionListener();
			P3.add(btn[i]) ;
		}
		
		//확인하깅
		//add(p);

		setVisible(true);
	}
	public static void main(String[] args) {
		new test();
	}
}
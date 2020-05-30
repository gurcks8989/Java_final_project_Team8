package armada.project.game;

import java.awt.*;
import java.awt.event.* ;
import javax.swing.* ;

public class Item extends JFrame implements ActionListener{
	
	
	JButton btn[] = new JButton[2] ;
	String btnText[] = {"Undo", "Clear"} ;
	Font font = new Font("DialogIntput",Font.BOLD,30) ;
	
	this.setLayout(new GridLayout(1, 4, 5, 5)) ;
	
    for(int i = 0; i < 2; i++) {
		btn[i] = new JButton(btnText[i]) ;	
		btn[i].setFont(font);
		btn[i].setBackground(Color.cyan) ;
		//btn[i].addActionListener();
		this.add(btn[i]) ;
	}

	this.setVisible(true);
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

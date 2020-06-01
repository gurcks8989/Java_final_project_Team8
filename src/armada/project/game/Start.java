package armada.project.game;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
public class Start {

	public static void main(String[] args){
		
		//시작할때 사진--------------------------------------------------------------
		JFrame f = new JFrame("메인윈도우") ;
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null) ;

		JLabel l = new JLabel(); //프레임에 레이블 추가         
		f.add(l);

		File file= new File("seed/madeby.png");  //이미지 파일 경로 
		BufferedImage m;         

		try {   
		     m = ImageIO.read(file); //이미지 파일을 읽어와서 BufferedImage 에 넣음
		     l.setIcon(new ImageIcon(m)); //레이블에 이미지 표시
		}
		catch(Exception e) {}
		
		
		f.setVisible(true);
		f.pack(); //프레임의 크기를 자동으로 설정해줍니다.
		//--------------------------------------------------------------------------
		Game game = new Game();
		JFrame window = new JFrame("2048");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.add(game);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		game.start();
		
		Music music = new Music("music.mp3",true);
		music.start();
	}
}

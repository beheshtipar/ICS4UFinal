import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class testGUI extends JFrame{

	private static final long serialVersionUID = 1L;

	private static JFrame frame;
	
	public testGUI() throws IOException {
		init();
	}

	protected static void init() throws IOException{
		frame = new JFrame("Guess That Champion!");
		frame.setBounds(0,0,800,600);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JLayeredPane mainPane = new JLayeredPane();
		frame.getContentPane().add(mainPane, "name_155929863069294");
		
		BufferedImage BG = ImageIO.read(new File("src/akali_vs_baron_3.jpg"));
		GridBagLayout gbl_mainPane = new GridBagLayout();
		gbl_mainPane.columnWidths = new int[]{784, 0};
		gbl_mainPane.rowHeights = new int[]{561, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_mainPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_mainPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		mainPane.setLayout(gbl_mainPane);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		mainPane.setLayer(lblNewLabel_1, 1);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		mainPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblBG = new JLabel(new ImageIcon(BG));
		lblBG.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBG.setForeground(Color.BLACK);
		mainPane.setLayer(lblBG, 0);
		GridBagConstraints gbc_lblBG = new GridBagConstraints();
		gbc_lblBG.fill = GridBagConstraints.VERTICAL;
		gbc_lblBG.insets = new Insets(0, 0, 5, 0);
		gbc_lblBG.gridx = 0;
		gbc_lblBG.gridy = 3;
		mainPane.add(lblBG, gbc_lblBG);
	}
	public static void main(String[] args){
		// TODO Auto-generated method stub
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					testGUI window = new testGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JCheckBox;

import java.awt.Panel;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;


public class mainFrame extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public mainFrame() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 300);
		
		
		
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{793, 1, 0};
		gbl_contentPane.rowHeights = new int[]{100, 468, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel title = new JPanel();
		title.setPreferredSize(new Dimension(800, 50));
		GridBagConstraints gbc_title = new GridBagConstraints();
		gbc_title.fill = GridBagConstraints.HORIZONTAL;
		gbc_title.gridwidth = 2;
		gbc_title.insets = new Insets(0, 0, 5, 0);
		gbc_title.gridx = 0;
		gbc_title.gridy = 0;
		contentPane.add(title, gbc_title);
		
		Font titleFont = new Font("Calibri", Font.BOLD, 25);
		JLabel lblTitle = new JLabel("Guess That Champion!");
		lblTitle.setFont(new Font("Avenir Next Condensed", Font.BOLD, 25));
		lblTitle.setPreferredSize(new Dimension(230, 25));
		title.add(lblTitle);
		
		JPanel content = new JPanel();
		GridBagConstraints gbc_content = new GridBagConstraints();
		gbc_content.fill = GridBagConstraints.BOTH;
		gbc_content.insets = new Insets(0, 0, 0, 5);
		gbc_content.gridx = 0;
		gbc_content.gridy = 1;
		contentPane.add(content, gbc_content);
		GridBagLayout gbl_content = new GridBagLayout();
		gbl_content.columnWidths = new int[]{0, 0};
		gbl_content.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_content.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_content.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		content.setLayout(gbl_content);
		
		JLabel chooseCategories = new JLabel("Please choose the categories you would like to be quizzed on: ");
		chooseCategories.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		GridBagConstraints gbc_chooseCategories = new GridBagConstraints();
		gbc_chooseCategories.insets = new Insets(0, 0, 5, 0);
		gbc_chooseCategories.anchor = GridBagConstraints.NORTH;
		gbc_chooseCategories.gridx = 0;
		gbc_chooseCategories.gridy = 0;
		content.add(chooseCategories, gbc_chooseCategories);
		
		JPanel choices = new JPanel();
		GridBagConstraints gbc_choices = new GridBagConstraints();
		gbc_choices.anchor = GridBagConstraints.NORTH;
		gbc_choices.insets = new Insets(0, 0, 5, 0);
		gbc_choices.gridx = 0;
		gbc_choices.gridy = 1;
		content.add(choices, gbc_choices);
		
		JCheckBox choicePassive = new JCheckBox("Champion Passives");
		choices.add(choicePassive);
		
		JCheckBox choiceReg = new JCheckBox("Regular Champion Abilities");
		choiceReg.setToolTipText("Abilities in Q, W, and E slots");
		choices.add(choiceReg);
		
		JCheckBox choiceUltimate = new JCheckBox("Champion Ultimates");
		choices.add(choiceUltimate);
		
		JButton btnStart = new JButton("Start!\n");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.ipady = 10;
		gbc_btnStart.ipadx = 5;
		gbc_btnStart.insets = new Insets(5, 5, 5, 5);
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 2;
		content.add(btnStart, gbc_btnStart);
		
		
		BufferedImage BG = ImageIO.read(new File("src/morgana_vs_ahri_3.jpg"));
		JLabel lblBG = new JLabel(new ImageIcon(BG));
		GridBagConstraints gbc_lblBG = new GridBagConstraints();
		gbc_lblBG.gridx = 1;
		gbc_lblBG.gridy = 1;
		contentPane.add(lblBG, gbc_lblBG);
	}

}

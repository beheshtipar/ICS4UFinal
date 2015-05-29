import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;


public class frame extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame frame = new frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocation(null);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{793, 1, 0};
		gbl_contentPane.rowHeights = new int[]{100, 468, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel title = new JPanel();
		title.setPreferredSize(new Dimension(800, 50));
		GridBagConstraints gbc_title = new GridBagConstraints();
		gbc_title.gridwidth = 2;
		gbc_title.insets = new Insets(0, 0, 5, 5);
		gbc_title.gridx = 0;
		gbc_title.gridy = 0;
		contentPane.add(title, gbc_title);
		
		Font titleFont = new Font("Calibri", Font.BOLD, 25);
		JLabel lblTitle = new JLabel("Guess That Champion!");
		lblTitle.setFont(titleFont);
		lblTitle.setPreferredSize(new Dimension(300, 25));
		title.add(lblTitle);
		
		JPanel conent = new JPanel();
		GridBagConstraints gbc_conent = new GridBagConstraints();
		gbc_conent.insets = new Insets(0, 0, 0, 5);
		gbc_conent.anchor = GridBagConstraints.WEST;
		gbc_conent.fill = GridBagConstraints.HORIZONTAL;
		gbc_conent.gridx = 0;
		gbc_conent.gridy = 1;
		contentPane.add(conent, gbc_conent);
		GridBagLayout gbl_conent = new GridBagLayout();
		gbl_conent.columnWidths = new int[]{0};
		gbl_conent.rowHeights = new int[]{0};
		gbl_conent.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_conent.rowWeights = new double[]{Double.MIN_VALUE};
		conent.setLayout(gbl_conent);
	}

}

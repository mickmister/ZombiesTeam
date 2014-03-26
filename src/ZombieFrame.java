import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;


/**
 * The frame that 
 *
 * @author watersdr.
 *         Created Mar 20, 2014.
 */
public class ZombieFrame extends JFrame {
	
	public static void main(String[] args) {
		new ZombieFrame();
	}
	
	/**
	 * Constructs the main menu that first pops up when the game is ran.
	 *
	 */
	public ZombieFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException exception) {
			
			exception.printStackTrace();
		}
		setLayout(new FlowLayout());
		setTitle("Zombies!!!");
		JButton button1 = new JButton("Click Here");
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, "Waddup Buffalo -Koch");
			}
		});
		add(button1);
		
		JButton button2 = new JButton("Second Button");
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});
		add(button2);
		
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	/**
	 * pointless method to do unit tests on
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static int multiply(int x, int y) {
		return x * y;
	}
	
	
}

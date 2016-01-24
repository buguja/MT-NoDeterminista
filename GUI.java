import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.Window.Type;
import java.awt.Color;

public class GUI extends JFrame{
	private JTextField textFieldPathFile;
	private JButton btnCalcular;
	private JButton btnExaminar;
	private JTextArea textAreaRecorrido;
	private JTextField textFieldCosto;
	private JLabel lblTipoArchivo;
	
	public GUI(){
		setType(Type.UTILITY);
		setTitle("MT no determinista");
		setResizable(false);
		setSize(new Dimension(480, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel lblJavierBurnGutirrez = new JLabel("Javier Bur\u00F3n Guti\u00E9rrez");
		panel_1.add(lblJavierBurnGutirrez);
		lblJavierBurnGutirrez.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblTipoArchivo = new JLabel("archivo");
		lblTipoArchivo.setBackground(Color.WHITE);
		panel_1.add(lblTipoArchivo);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.NORTH);
		
		JLabel lblArchivo = new JLabel("Archivo:");
		panel_3.add(lblArchivo);
		
		textFieldPathFile = new JTextField();
		textFieldPathFile.setBackground(Color.WHITE);
		textFieldPathFile.setEditable(false);
		panel_3.add(textFieldPathFile);
		textFieldPathFile.setColumns(20);
		
		btnExaminar = new JButton("Examinar");
		panel_3.add(btnExaminar);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		textAreaRecorrido = new JTextArea();
		textAreaRecorrido.setRows(10);
		textAreaRecorrido.setColumns(40);
		panel_4.add(textAreaRecorrido);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5, BorderLayout.CENTER);
		
		JLabel lblCosto = new JLabel("Costo:");
		panel_5.add(lblCosto);
		
		textFieldCosto = new JTextField();
		textFieldCosto.setBackground(Color.WHITE);
		textFieldCosto.setEditable(false);
		panel_5.add(textFieldCosto);
		textFieldCosto.setColumns(20);
		
		JPanel panel_6 = new JPanel();
		panel_2.add(panel_6, BorderLayout.EAST);
		
		btnCalcular = new JButton("Calcular");
		btnCalcular.setEnabled(false);
		panel_6.add(btnCalcular);
		setVisible(true);
	}
}

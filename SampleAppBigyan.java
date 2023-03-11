package individualProject;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.UIManager;

public class SampleAppBigyan {

	private JFrame frame;
	private JTextField nameText;
	private JTextField addressText;
	DefaultTableModel model;
	JTable table_1;
	PreparedStatement pst;
	String id = "";
	JCheckBox covidCheck;
	JRadioButton maleRadio;
	JRadioButton femaleRadio;
	public String name, gender, covid, address;

	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/covid", "root", "Baby@6318!");

	public static void main(String[] args) throws SQLException {
		SampleAppBigyan window = new SampleAppBigyan();
		window.frame.setVisible(true);
	}

	public SampleAppBigyan() throws SQLException {
		initialize();
	}

	public void getID() {
		table_1.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				name = nameText.getText();
				address = addressText.getText();
				gender = "";
				covid = "";
				if (maleRadio.isSelected()) {
					gender = "Male";
				} else {
					gender = "Female";
				}

				if (covidCheck.isSelected()) {
					covid = "Positive";
				} else {
					covid = "Negative";
				}

				try {

					pst = conn.prepareStatement(
							"select id from covid where Name=? AND Address=? AND Gender=? AND COVID_19=?");

					pst.setString(1, name);
					pst.setString(2, address);
					pst.setString(3, gender);
					pst.setString(4, covid);

					ResultSet rs = pst.executeQuery();
					if (rs.next()) {
						id = rs.getString("id");

					}

				} catch (Exception exception) {
					exception.printStackTrace();

				}

			}
		});
	}

	public void textBoxFiller() {

		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table_1.getModel();
				int selectedRowIndex = table_1.getSelectedRow();

				nameText.setText(model.getValueAt(selectedRowIndex, 0).toString());
				addressText.setText(model.getValueAt(selectedRowIndex, 1).toString());

				String gender = model.getValueAt(selectedRowIndex, 2).toString();
				if (gender.equals("Male")) {
					maleRadio.setSelected(true);
				} else {
					femaleRadio.setSelected(true);
				}
				String covid = model.getValueAt(selectedRowIndex, 3).toString();
				if (covid.equals("Positive")) {
					covidCheck.setSelected(true);
				} else {
					covidCheck.setSelected(false);
				}

			}
		});
	}

	public void jTableFiller() {

		model.setRowCount(0);
		try {
			PreparedStatement st = conn.prepareStatement("SELECT * FROM covid");

			ResultSet res = st.executeQuery();

			while (res.next()) {
				model.addRow(new Object[] { res.getString("Name"), res.getString("Address"), res.getString("Gender"),
						res.getString("COVID_19"),

				});
			}

		} catch (SQLException ed) {
			ed.printStackTrace();
		}
	}

	public void initialize() throws SQLException {

		frame = new JFrame("Sample App by Bigyan");

		frame.setBounds(100, 100, 1586, 864);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

// Menu Bar

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(10, 0, 116, 22);
		frame.getContentPane().add(menuBar);

		JMenu file = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New");
		JMenuItem exitItem = new JMenuItem("Exit");
		menuBar.add(file);
		file.add(newItem);
		file.addSeparator();
		file.add(exitItem);

		JMenu menu = new JMenu("Edit");
		menuBar.add(menu);

		JMenu view = new JMenu("View");
		menuBar.add(view);

// Left Panel

		JPanel panelLeft = new JPanel();
		panelLeft.setBorder(new TitledBorder(null, "Data Entry", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLeft.setBounds(0, 20, 760, 410);
		frame.getContentPane().add(panelLeft);
		panelLeft.setLayout(null);

		JLabel name = new JLabel("Name");
		name.setFont(new Font("Times New Roman", Font.BOLD, 14));
		name.setBounds(221, 126, 44, 27);
		panelLeft.add(name);

		JLabel address = new JLabel("Address");
		address.setFont(new Font("Times New Roman", Font.BOLD, 14));
		address.setBounds(221, 166, 68, 13);
		panelLeft.add(address);

		JLabel gender = new JLabel("Gender");
		gender.setFont(new Font("Times New Roman", Font.BOLD, 14));
		gender.setBounds(221, 204, 68, 13);
		panelLeft.add(gender);

		JLabel covid = new JLabel("Positive");
		covid.setFont(new Font("Times New Roman", Font.BOLD, 14));
		covid.setBounds(221, 237, 68, 13);
		panelLeft.add(covid);

		nameText = new JTextField();
		nameText.setBounds(282, 132, 227, 19);
		panelLeft.add(nameText);
		nameText.setColumns(10);

		addressText = new JTextField();
		addressText.setBounds(282, 165, 227, 19);
		panelLeft.add(addressText);
		addressText.setColumns(10);

		maleRadio = new JRadioButton("Male");
		maleRadio.setFont(new Font("Times New Roman", Font.BOLD, 14));
		maleRadio.setBounds(285, 199, 59, 21);
		panelLeft.add(maleRadio);

		femaleRadio = new JRadioButton("Female");
		femaleRadio.setFont(new Font("Times New Roman", Font.BOLD, 14));
		femaleRadio.setBounds(356, 199, 103, 21);
		panelLeft.add(femaleRadio);

		ButtonGroup bg = new ButtonGroup();
		bg.add(maleRadio);
		bg.add(femaleRadio);

		covidCheck = new JCheckBox("");
		covidCheck.setBounds(290, 231, 93, 21);
		panelLeft.add(covidCheck);

// Bottom Panel

		JPanel panelBottom = new JPanel();
		panelBottom
				.setBorder(new TitledBorder(null, "List of data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBottom.setBounds(0, 432, 1532, 432);
		frame.getContentPane().add(panelBottom);
		panelBottom.setLayout(null);

		model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] { "Name", "Address", "Gender", "COVID_19" });

		table_1 = new JTable(model);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 23, 1510, 432);
		panelBottom.add(scrollPane);
		scrollPane.setViewportView(table_1);

		jTableFiller();
		textBoxFiller();
		getID();

// Right Panel

		JPanel panelRight = new JPanel();
		panelRight.setBorder(
				new TitledBorder(null, "Button Functionality", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRight.setBounds(768, 20, 760, 410);
		frame.getContentPane().add(panelRight);

		JButton save = new JButton("Save");
		save.setFont(new Font("Times New Roman", Font.BOLD, 14));
		save.setBounds(12, 17, 365, 190);
		save.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String name = nameText.getText();
				String address = addressText.getText();

				String gender = "";
				String covid = "";
				if (maleRadio.isSelected()) {
					gender = "Male";
				} else {
					gender = "Female";
				}

				if (covidCheck.isSelected()) {
					covid = "Positive";
				} else {
					covid = "Negative";
				}

				try {

					pst = conn.prepareStatement("insert into covid(Name,Address,Gender,COVID_19)values(?,?,?,?)");
					pst.setString(1, name);
					pst.setString(2, address);
					pst.setString(3, gender);
					pst.setString(4, covid);

					pst.executeUpdate();
					jTableFiller();
					JOptionPane.showMessageDialog(save, "Your data is sucessfully added");

				} catch (Exception exception) {
					exception.printStackTrace();
				}

				textBoxFiller();

			}
		});
		
				JButton update = new JButton("Update");
				update.setFont(new Font("Times New Roman", Font.BOLD, 14));
				update.setBounds(380, 17, 370, 190);
				update.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
				
						update.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
				
								String name = nameText.getText();
								String address = addressText.getText();
				
								String gender = "";
								if (maleRadio.isSelected()) {
									gender = "Male";
								} else {
									gender = "Female";
								}
				
								String covid = "";
								if (covidCheck.isSelected()) {
									covid = "Positive";
								} else {
									covid = "Negative";
								}
				
								try {
				
									pst = conn.prepareStatement("update covid set Name=?,Address=?,Gender=?,COVID_19=? where id = ?");
									pst.setString(1, name);
									pst.setString(2, address);
									pst.setString(3, gender);
									pst.setString(4, covid);
									pst.setString(5, id);
				
									pst.executeUpdate();
									jTableFiller();
									JOptionPane.showMessageDialog(update, "Your data is sucessfully updated");
								}
				
								catch (Exception e3) {
									e3.printStackTrace();
								}
							}
						});

		JButton delete = new JButton("Delete");
		delete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		delete.setBounds(12, 210, 365, 190);
		delete.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {

					pst = conn.prepareStatement("delete from covid where id = ?");
					pst.setString(1, id);

					pst.executeUpdate();
					jTableFiller();
					JOptionPane.showMessageDialog(delete, "The data has been sucessfully deleted");
				}

				catch (Exception e3) {
					e3.printStackTrace();
				}

			}
		});

		JButton clear = new JButton("Clear");
		clear.setFont(new Font("Times New Roman", Font.BOLD, 14));
		clear.setBounds(380, 210, 370, 190);
		clear.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameText.setText("");
				addressText.setText("");
				bg.clearSelection();
				covidCheck.setSelected(false);
				JOptionPane.showMessageDialog(clear, "The data has been cleared");
			}
		});
		panelRight.setLayout(null);
		panelRight.add(save);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 10, 10);
		panelRight.add(panel);
		panelRight.add(update);
		panelRight.add(delete);
		panelRight.add(clear);

	}
}

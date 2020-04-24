import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MainForm {
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private File file;
	private JTextField textField_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(100, 100, 867, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new Manager();
		panel.setBorder(new LineBorder(new Color(0, 0, 255)));
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(12, 13, 484, 288);
		frame.getContentPane().add(panel);

		final DefaultListModel<String> list2 = new DefaultListModel<String>();
		JList<String> list = new JList<String>(list2);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setBorder(new LineBorder(new Color(0, 0, 255)));
		list.setBackground(SystemColor.inactiveCaption);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				String s = (String) list.getSelectedValue();
				if (file != null) {
					Place[] ps = file.getNode().getpositions();
					for (int i = 0; i < ps.length; i++) {
						Manager.setMemoryPoint(ps[i].I, ps[i].J, 2);
					}
				}
				File f = Changes.getfile(s);
				if (f != null) {
					Place[] ps = f.getNode().getpositions();
					if (ps != null) {
						for (int i = 0; i < ps.length; i++) {
							Manager.setMemoryPoint(ps[i].I, ps[i].J, 3);
						}
					}
					panel.repaint();
					file = f;
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					JOptionPane.showMessageDialog(null, file.getNode().toStr());

				}
			}
		});
		list.setBounds(712, 13, 125, 288);
		frame.getContentPane().add(list);

		JButton btnCreate = new JButton("Задать размер");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				list.removeAll();
				list2.clear();
				Manager.setFree();
				String str = textField.getText();
				if (Integer.parseInt(str) % 4 != 0 && str != "") {
					JOptionPane.showMessageDialog(null,
							"Не делится на 4. Попробуйте снова");
				} else {
					Manager.startup(Integer.parseInt(str));				
				}
				textField.setText("");
				panel.repaint();
			}
		});
		btnCreate.setBounds(508, 101, 148, 23);
		frame.getContentPane().add(btnCreate);

		textField = new JTextField();
		textField.setBounds(508, 68, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnAdd = new JButton("Создать файл");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int size = Integer.parseInt(textField_1.getText());
				if (textField_2.getText().trim().length() > 0) {
					try {
						if (size % 4 != 0) {
							while (size % 4 != 0) {
								size++;
							}
						}
						if (size % 4 == 0) {
							String name = textField_2.getText();
							if (name != null) {
							int i = 0;	
								while (size > 0) {
									int sizeLocal = 0;
									if(size / 4 > 3) {
										sizeLocal = 3*4;
										size -=sizeLocal;
									} else {
										sizeLocal = size;
										size = 0;
									}
									if(i!=0) {
										name+=i;
									}
									Boolean add = Manager.addFile(name, sizeLocal);
									if (add) {
										list2.addElement(name);
										list.setModel(list2);
									}
									i++;
								}
								panel.repaint();
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Не делится на 4. Попробуйте снова");
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Попробуйте снова");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Введите название файла");
				}
				textField_2.setText("");
				textField_1.setText("");
			}
		});
		btnAdd.setBounds(518, 205, 148, 23);
		frame.getContentPane().add(btnAdd);

		JButton btnCopy = new JButton("Скопировать");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField_2.getText().trim().length() > 0) {
					String name = textField_2.getText();
					Boolean add = Manager.addFile(name + "-копия", Changes
							.getfile(name).getNode().fileSize());
					if (add) {
						list2.addElement(name + "-копия");
						list.setModel(list2);
					}
					panel.repaint();
					textField_2.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"Введите название файла");
				}
			}
		});
		btnCopy.setBounds(516, 241, 150, 23);
		frame.getContentPane().add(btnCopy);

		JButton btnDelete = new JButton("Удалить файл");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField_2.getText().trim().length() > 0) {
					String name = textField_2.getText();
					Manager.Delete(name);
					file = null;
					list2.removeElement(name);
					list.setModel(list2);
					panel.repaint();
					textField_2.setText("");
				} else {
					JOptionPane.showMessageDialog(null,
							"Введите название файла");
				}
			}
		});
		btnDelete.setBounds(518, 277, 148, 23);
		frame.getContentPane().add(btnDelete);

		textField_1 = new JTextField();
		textField_1.setBounds(621, 137, 89, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel = new JLabel(
				"\u0420\u0430\u0437\u043C\u0435\u0440 \u0444\u0430\u0439\u043B\u0430:");
		lblNewLabel.setBounds(507, 139, 102, 16);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(
				"\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0444\u0430\u0439\u043B\u0430:");
		lblNewLabel_1.setBounds(508, 176, 116, 16);
		frame.getContentPane().add(lblNewLabel_1);

		textField_2 = new JTextField();
		textField_2.setBounds(621, 170, 89, 22);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Все файлы:");
		lblNewLabel_2.setBounds(608, 27, 100, 16);
		frame.getContentPane().add(lblNewLabel_2);
	}
}

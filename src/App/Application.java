package App;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Factory.DatabaseFactory;

import classes.Book;
import intefrace.BookInterface;
import javax.swing.JRadioButton;

public class Application {

	private JFrame frame;
	public BookInterface database;
	JLayeredPane layeredPane;
	JPanel panelAdd;
	JPanel panelList;
	private JTable table;
	private JButton btnSubmit;
	private JTextField textFieldId;
	
	
	String[] columnNames = {"ID",
            "Title",
            "Author",
            "Status"};
	
	Object[][] data;
	private JLabel lblTitle;
	private JTextField textFieldTitle;
	private JTextField textFieldAuthor;
	private JLabel lblAuthor;
	private JLabel lblStatus;
	private JPanel panelStatus;
	JRadioButton rdbtnInStock;
	JRadioButton rdbtnLoan;
	private JLabel lblBookId;
	private JLabel label;
	private JLabel label_1;
	private JRadioButton radioButtonStock;
	private JRadioButton radioButtonLoan;
	private JButton button;
	private JPanel panelEdit;
	private JLabel label_2;
	private JLabel labelEditId;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JButton buttonEdit;
	private JTextField textFieldEditId;
	private JTextField textFieldEditTitle;
	private JTextField textFieldEditAuthor;
	private JRadioButton radioEditStock;
	private JRadioButton radioEditLoan;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
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
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		CreateDatabase();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 827, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnLista = new JMenu("list");
		mnLista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loadTable();
				switchPanels(panelList);
			}
		});
		menuBar.add(mnLista);
		
		JMenu mnDodaj = new JMenu("add");
		mnDodaj.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearPanel2();
				switchPanels(panelAdd);
			}
		});
		menuBar.add(mnDodaj);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, "name_426893979037740");
		layeredPane.setLayout(new CardLayout(0, 0));
		
		panelList = new JPanel();
		layeredPane.add(panelList, "name_427368866468207");
		
		loadTable();
		
		
		panelAdd = new JPanel();
		layeredPane.add(panelAdd, "name_427368875073555");
		panelAdd.setLayout(null);
		
		JLabel lblId = new JLabel("id");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblId.setBounds(137, 54, 179, 45);
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		panelAdd.add(lblId);
		
		btnSubmit = new JButton("submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					int id = Integer.parseInt(textFieldId.getText());
					if(textFieldTitle.getText().equals("") || textFieldAuthor.getText().equals(""))
					{
						JOptionPane.showMessageDialog(frame,  "fields can not be empty");
					}
					else if(!database.existBookId(id))
					{
						String status;
						if(rdbtnInStock.isSelected())
							status = "in stock";
						else 
							status = "loan";
						Book book = new Book(Integer.parseInt(textFieldId.getText()),
								textFieldTitle.getText(),
								textFieldAuthor.getText(),
								status);
						database.addNewBook(book);
						loadTable();
						switchPanels(panelList);
					}
					else 
					{
						JOptionPane.showMessageDialog(frame,  "these id already exists in the database");
					}
				}
				catch(NumberFormatException exception)
				{
					JOptionPane.showMessageDialog(frame,  "id must be a number");
				}
				
			}
		});
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switchPanels(panelList);
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnSubmit.setBounds(298, 300, 159, 55);
		panelAdd.add(btnSubmit);
		
		textFieldId = new JTextField();
		textFieldId.setBounds(367, 65, 195, 30);
		panelAdd.add(textFieldId);
		textFieldId.setColumns(10);
		
		lblTitle = new JLabel("title");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTitle.setBounds(137, 111, 179, 45);
		panelAdd.add(lblTitle);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setColumns(10);
		textFieldTitle.setBounds(367, 122, 195, 30);
		panelAdd.add(textFieldTitle);
		
		textFieldAuthor = new JTextField();
		textFieldAuthor.setColumns(10);
		textFieldAuthor.setBounds(367, 177, 195, 30);
		panelAdd.add(textFieldAuthor);
		
		lblAuthor = new JLabel("author");
		lblAuthor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblAuthor.setBounds(137, 166, 179, 45);
		panelAdd.add(lblAuthor);
		
		lblStatus = new JLabel("status");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblStatus.setBounds(137, 215, 179, 45);
		panelAdd.add(lblStatus);
		
		rdbtnInStock = new JRadioButton("in stock", true);
		rdbtnInStock.setFont(new Font("Tahoma", Font.PLAIN, 17));
		rdbtnInStock.setBounds(367, 227, 105, 21);
		panelAdd.add(rdbtnInStock);
		
		rdbtnLoan = new JRadioButton("loan", false);
		rdbtnLoan.setFont(new Font("Tahoma", Font.PLAIN, 17));
		rdbtnLoan.setBounds(475, 227, 105, 21);
		panelAdd.add(rdbtnLoan);
		
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(rdbtnInStock);
		bGroup.add(rdbtnLoan);
		panelStatus = new JPanel();
		layeredPane.add(panelStatus, "name_503743329334363");
		panelStatus.setLayout(null);
		
		lblBookId = new JLabel("Book id: ");
		lblBookId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBookId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblBookId.setBounds(187, 90, 92, 62);
		panelStatus.add(lblBookId);
		
		label = new JLabel("");
		label.setBounds(284, 90, 179, 62);
		panelStatus.add(label);
		
		label_1 = new JLabel("status");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_1.setBounds(157, 159, 179, 45);
		panelStatus.add(label_1);
		
		radioButtonStock = new JRadioButton("in stock", true);
		radioButtonStock.setFont(new Font("Tahoma", Font.PLAIN, 17));
		radioButtonStock.setBounds(342, 174, 105, 21);
		panelStatus.add(radioButtonStock);
		
		radioButtonLoan = new JRadioButton("loan", false);
		radioButtonLoan.setFont(new Font("Tahoma", Font.PLAIN, 17));
		radioButtonLoan.setBounds(444, 174, 105, 21);
		panelStatus.add(radioButtonLoan);
		ButtonGroup bGroup2 = new ButtonGroup();
		bGroup2.add(radioButtonStock);
		bGroup2.add(radioButtonLoan);
		
		button = new JButton("submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(label.getText());
				Book book = database.getBookById(id);
				String status;
				if(radioButtonStock.isSelected())
					status = "in stock";
				else 
					status = "loan";
				book.status = status;
				database.updateBook(id, book);
				loadTable();
				switchPanels(panelList);
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 17));
		button.setBounds(320, 280, 159, 55);
		panelStatus.add(button);
		
		panelEdit = new JPanel();
		layeredPane.add(panelEdit, "name_507305350389956");
		panelEdit.setLayout(null);
		
		label_2 = new JLabel("Book id: ");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_2.setBounds(108, 42, 92, 62);
		panelEdit.add(label_2);
		
		labelEditId = new JLabel("");
		labelEditId.setBounds(202, 42, 179, 62);
		panelEdit.add(labelEditId);
		
		label_4 = new JLabel("id");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_4.setBounds(77, 100, 179, 45);
		panelEdit.add(label_4);
		
		label_5 = new JLabel("title");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_5.setBounds(77, 149, 179, 45);
		panelEdit.add(label_5);
		
		label_6 = new JLabel("author");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_6.setBounds(77, 197, 179, 45);
		panelEdit.add(label_6);
		
		label_7 = new JLabel("status");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_7.setBounds(77, 244, 179, 45);
		panelEdit.add(label_7);
		
		buttonEdit = new JButton("submit");
		buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					int id = Integer.parseInt(textFieldEditId.getText());
					int oldId = Integer.parseInt(labelEditId.getText());
					if(textFieldEditTitle.getText().equals("") || textFieldEditAuthor.getText().equals(""))
					{
						JOptionPane.showMessageDialog(frame,  "fields can not be empty");
					}
					else if( id == oldId || !database.existBookId(id)  )
					{
						String status;
						if(radioEditStock.isSelected())
							status = "in stock";
						else 
							status = "loan";
						Book book = new Book(
								id,
								textFieldEditTitle.getText(),
								textFieldEditAuthor.getText(),
								status);
						
						database.updateBook(oldId, book);
						loadTable();
						switchPanels(panelList);
					}
					else 
					{
						JOptionPane.showMessageDialog(frame,  "these id already exists in the database");
					}
				}
				catch(NumberFormatException exception)
				{
					JOptionPane.showMessageDialog(frame,  "id must be a number");
				}
			}
		});
		buttonEdit.setFont(new Font("Tahoma", Font.PLAIN, 17));
		buttonEdit.setBounds(202, 322, 159, 55);
		panelEdit.add(buttonEdit);
		
		textFieldEditId = new JTextField();
		textFieldEditId.setColumns(10);
		textFieldEditId.setBounds(307, 116, 195, 30);
		panelEdit.add(textFieldEditId);
		
		textFieldEditTitle = new JTextField();
		textFieldEditTitle.setColumns(10);
		textFieldEditTitle.setBounds(307, 165, 195, 30);
		panelEdit.add(textFieldEditTitle);
		
		textFieldEditAuthor = new JTextField();
		textFieldEditAuthor.setColumns(10);
		textFieldEditAuthor.setBounds(307, 213, 195, 30);
		panelEdit.add(textFieldEditAuthor);
		
		radioEditStock = new JRadioButton("in stock", true);
		radioEditStock.setFont(new Font("Tahoma", Font.PLAIN, 17));
		radioEditStock.setBounds(309, 259, 105, 21);
		panelEdit.add(radioEditStock);
		
		radioEditLoan = new JRadioButton("loan", false);
		radioEditLoan.setFont(new Font("Tahoma", Font.PLAIN, 17));
		radioEditLoan.setBounds(417, 259, 105, 21);
		panelEdit.add(radioEditLoan);
		ButtonGroup bGroup3 = new ButtonGroup();
		bGroup3.add(radioEditStock);
		bGroup3.add(radioEditLoan);
		
	}
	
	public void CreateDatabase()
	{
		Properties prop = new Properties();
		InputStream input = Application.class.getClassLoader().getResourceAsStream("config/config.properties");
		try
		{
			prop.load(input);
		}
		catch (IOException e) {
			System.out.println("Plik konfiguracyjny nie istnieje");
		}
		
		DatabaseFactory factory = new DatabaseFactory();
		database = factory.getDatabase(prop.getProperty("implementation"));
	}
	
	public void switchPanels( JPanel panel)
	{
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	public void loadData()
	{
		List<Book> books = database.getBooks();
		data = new Object[books.size()][4];
		for(int i = 0; i<books.size(); i++)
		{
				data[i][0] = books.get(i).id;
				data[i][1] = books.get(i).title;
				data[i][2] = books.get(i).author;
				data[i][3] = books.get(i).status;
		}
	}
	
	public void loadTable()
	{
		loadData();
		panelList.removeAll();			
		table = new JTable(data, columnNames);
		
		final JPopupMenu pop= RowPopup(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e))
				{
					int r = table.rowAtPoint(e.getPoint());
					table.setRowSelectionInterval(r, r);
					pop.show(e.getComponent(), e.getX(),e.getY());
				}
			}
		});
		
		panelList.add(new JScrollPane(table));   
	}
	
	public void clearPanel2()
	{
		textFieldId.setText("");
		textFieldTitle.setText("");
		textFieldAuthor.setText("");
		rdbtnInStock.setSelected(true);
		rdbtnLoan.setSelected(false);
	}

	public JPopupMenu RowPopup(JTable table)
	{
		JPopupMenu po = new JPopupMenu();
		
		final JMenuItem edit = new JMenuItem("Edit");
		final JMenuItem status = new JMenuItem("Status");
		final JMenuItem delete = new JMenuItem("Delete");
		
		
		edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = Integer.parseInt(data[table.getSelectedRow()][0].toString());
				Book book = database.getBookById(i);
				labelEditId.setText(String.valueOf(book.id));
				if(book.status.equals("in stock"))
				{
					radioButtonStock.setSelected(true);
					radioButtonLoan.setSelected(false);
				}
				else 
				{
					radioButtonStock.setSelected(false);
					radioButtonLoan.setSelected(true);
				}
				textFieldEditId.setText(String.valueOf(i));
				textFieldEditTitle.setText(book.title);
				textFieldEditAuthor.setText(book.author);
			
				switchPanels(panelEdit);
			}
		});
		
		status.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int i = Integer.parseInt(data[table.getSelectedRow()][0].toString());
				label.setText(String.valueOf(i));
				if(database.checkBookStatus(i).equals("in stock"))
				{
					radioButtonStock.setSelected(true);
					radioButtonLoan.setSelected(false);
				}
				else 
				{
					radioButtonStock.setSelected(false);
					radioButtonLoan.setSelected(true);
				}
				switchPanels(panelStatus);
			}
		});

		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				database.deleteBook(Integer.parseInt(data[table.getSelectedRow()][0].toString()));
				loadTable();
				switchPanels(panelList);
			}
		});
		
		po.add(edit);
		po.add(status);
		po.add(delete);
		
		return po;
	}
}

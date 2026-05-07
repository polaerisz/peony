import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ticketBooking extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// CONTAINER
	private JPanel pnlEvent1;
	private JPanel pnlEvent2;
	private JPanel pnlEvent3;

	// BUTTONS
	private JButton[] buttonsEvent1 = new JButton[16];
	private JButton[] buttonsEvent2 = new JButton[16];
	private JButton[] buttonsEvent3 = new JButton[16];

	// EVENT HANDLERS
	private boolean[] event1 = new boolean[16];
	private boolean[] event2 = new boolean[16];
	private boolean[] event3 = new boolean[16];

	// CARD LAYOUT
	private CardLayout cl;

	// FOR LOADING PANEL
	private JPanel pnlLoading = new JPanel();

	// GET NAME
	private String name;

	// NUM OF TICKET
	private JSpinner spNumOfTicket;
	private int totalSelectedSeats = 0;
	private DefaultTableModel model = new DefaultTableModel();
	private DefaultTableModel model1 = new DefaultTableModel();
	private JTable tblResult;
	private JTextField txtName;
	private JPasswordField passwordField;
	private JTable tblTransaction;

	// Theme for the system
	private final Color peonyBg = new Color(248, 232, 224);
	private final Color peonyPrimary = new Color(196, 112, 128);
	private final Color peonySoft = new Color(244, 182, 194);
	private final Color peonyPink = new Color(244, 182, 194);
	private final Color peonyText = new Color(70, 50, 50);
	private final Color peonyButton = new Color(255, 248, 241);
	private final Color selectedSeat = new Color(198, 192, 156);
	private final Color menuButton = new Color(142, 92, 98);
	private final Color bookedSeat = new Color(150, 150, 150);

	// Buttons
	private final Color btnHover = new Color(214, 120, 140);
	private final Color btnPressed = new Color(150, 70, 90);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ticketBooking frame = new ticketBooking();
					frame.setLocationRelativeTo(null);
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
	public ticketBooking() {
		ImageIcon appIcon = loadImageIcon("/resources/icon.png");
		if (appIcon != null) {
			setIconImage(appIcon.getImage());
		}
		setTitle("Peony - Ticket Booking System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 571);
		setMinimumSize(new Dimension(827, 571));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout());
		cl = (CardLayout) contentPane.getLayout(); // APPLY THE CARD
		contentPane.add(pnlLoading, "Loading");

		// PANEL FOR LOG IN PAGE
		JPanel pnlLogIn = new JPanel();
		contentPane.add(pnlLogIn, "Log");

		txtName = new JTextField("Enter your username.");
		txtName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtName.getText().isEmpty()) {
					txtName.setText("Enter your username.");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (txtName.getText().equals("Enter your username.")) {
					txtName.setText("");
				}
			}
		});
		txtName.setColumns(10);
		txtName.setBackground(peonyButton);
		txtName.setForeground(peonyText);

		passwordField = new JPasswordField("Enter your password.");
		passwordField.setEchoChar((char) 0);

		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String passwordString = new String(passwordField.getPassword());

				if (passwordString.isEmpty()) {
					passwordField.setText("Enter your password.");
					passwordField.setEchoChar((char) 0);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				String passwordString = new String(passwordField.getPassword());

				if (passwordString.equals("Enter your password.")) {
					passwordField.setText("");
					passwordField.setEchoChar('\u2022');
				}
			}
		});

		passwordField.setBackground(peonyButton);
		passwordField.setForeground(peonyText);

		// SET LOG IN PANEL INITIALLY BY DEFAULT
		cl.show(contentPane, "Log");

		// PANEL FOR SEAT
		JPanel pnlSeatSelection = new JPanel();
		contentPane.add(pnlSeatSelection, "Seats");
		pnlSeatSelection.setLayout(null);

		spNumOfTicket = new JSpinner(new SpinnerNumberModel(0, 0, 48, 1)); // (INITIAL VALUE, MININMUM VALUE, MAXIMUM
																			// VALUE, STEPS) 16 x 3 = 48
		spNumOfTicket.setBounds(6, 62, 64, 20);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP); // SELECTION TAB ON TOP
		tabbedPane.setBounds(6, 136, 371, 247);

		pnlEvent1 = new JPanel(); // CONTAINER OF SEAT 1 (Concert)
		pnlEvent1.setLayout(null);
		pnlEvent1.add(seat1());

		pnlEvent2 = new JPanel(); // CONTAINER OF SEAT 2 (ALFY BARA)
		pnlEvent2.setLayout(null);
		pnlEvent2.add(seat2());

		pnlEvent3 = new JPanel(); // CONTAINER OF SEAT 3 (TAYLOR SWIFT)
		pnlEvent3.setLayout(null);
		pnlEvent3.add(seat3());

		JScrollPane scrollPane = new JScrollPane(); // TABLE
		{

			scrollPane.setBounds(387, 84, 416, 368);
			{
				model = new DefaultTableModel(new Object[][] {}, new String[] { "Event", "Mode", "Seats", "Price" }) {
					public boolean isCellEditable(int row, int column) {
						JTextArea txtA = new JTextArea();
						txtA.setLineWrap(true);
						return false;
					}
				};
				tblResult = new JTable(model);
				tblResult.setBackground(peonyButton);
				tblResult.setForeground(peonyText);
				tblResult.getTableHeader().setEnabled(false); // TO MAKE THE HEADER PART UNCHANGEABLE
				scrollPane.setViewportView(tblResult);
			}
		}

		// TRANSACTION PANEL
		JPanel pnlTransaction = new JPanel();
		contentPane.add(pnlTransaction, "Transaction");
		pnlTransaction.setLayout(null);

		JScrollPane spTransaction = new JScrollPane();
		spTransaction.setBounds(10, 97, 783, 417);

		model1 = new DefaultTableModel(new Object[][] {

		}, new String[] { "Name", "Event", "Mode", "Seat", "Price" }) {
			public boolean isCellEditable(int row, int column) {
				JTextArea txtA = new JTextArea();
				txtA.setLineWrap(true);
				return false;
			}
		};

		// DUMMY DATA
		model1.addRow(new Object[] { "Jherwel", "Convert", "Semi-VIP", "Seat 6", "₱ " + String.valueOf(200) });
		model1.addRow(new Object[] { "Jirah", "Concert", "Semi-VIP", "Seat 9", "₱ " + String.valueOf(200) });
		model1.addRow(new Object[] { "Faith", "Concert", "Regular", "Seat 13", "₱ " + String.valueOf(100) });

		tblTransaction = new JTable(model1);
		tblTransaction.setBackground(peonyButton);
		tblTransaction.setForeground(peonyText);
		tblTransaction.getTableHeader().setEnabled(false); // TO MAKE THE HEADER PART UNCHANGEABLE
		spTransaction.setViewportView(tblTransaction);

		JLabel lblTransac = new JLabel("Transaction details");
		lblTransac.setFont(new Font("Arial", Font.PLAIN, 13));
		lblTransac.setForeground(peonyText);
		lblTransac.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransac.setBounds(0, 48, 803, 12);

		JButton btnBack2 = new JButton(" < Back");
		btnBack2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Menu");
			}
		});
		btnBack2.setOpaque(false);
		btnBack2.setForeground(peonyText);
		btnBack2.setHorizontalAlignment(SwingConstants.LEFT);
		btnBack2.setFont(new Font("Arial", Font.PLAIN, 11));
		btnBack2.setContentAreaFilled(false);
		btnBack2.setBorderPainted(false);
		btnBack2.setBounds(-11, 10, 84, 20);

		JCheckBox chckbxConcert = new JCheckBox("Concert");
		chckbxConcert.setOpaque(false);
		chckbxConcert.setForeground(peonyText);
		chckbxConcert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxConcert.isSelected()) {
				    if (tabbedPane.indexOfComponent(pnlEvent1) == -1) {
				        tabbedPane.addTab("Convert", pnlEvent1);
				    }
				} else {
				    tabbedPane.remove(pnlEvent1);
				}
			}
		});
		chckbxConcert.setBounds(6, 92, 92, 20);

		JCheckBox chckbxSports = new JCheckBox("Sports");
		chckbxSports.setOpaque(false);
		chckbxSports.setForeground(peonyText);
		chckbxSports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxSports.isSelected()) {
				    if (tabbedPane.indexOfComponent(pnlEvent2) == -1) {
				        tabbedPane.addTab("Sports", pnlEvent2);
				    }
				} else {
				    tabbedPane.remove(pnlEvent2);
				}
			}
		});
		chckbxSports.setBounds(152, 92, 92, 20);

		JCheckBox chckbxMovies = new JCheckBox("Movies");
		chckbxMovies.setOpaque(false);
		chckbxMovies.setForeground(peonyText);
		chckbxMovies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxMovies.isSelected()) {
				    if (tabbedPane.indexOfComponent(pnlEvent3) == -1) {
				        tabbedPane.addTab("Movies", pnlEvent3);
				    }
				} else {
				    tabbedPane.remove(pnlEvent3);
				}
			}
		});
		chckbxMovies.setBounds(285, 92, 92, 20);

		JLabel lblNumOfTicket = new JLabel("Select a number of  ticket to reserve");
		lblNumOfTicket.setForeground(peonyText);
		lblNumOfTicket.setBounds(97, 65, 276, 12);

		JButton btnBack1 = new JButton(" < Back");
		btnBack1.setHorizontalAlignment(SwingConstants.LEFT);
		btnBack1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Menu");
			}
		});
		btnBack1.setFont(new Font("Arial", Font.PLAIN, 11));
		btnBack1.setForeground(peonyText);
		btnBack1.setBounds(-16, 10, 84, 20);
		btnBack1.setOpaque(false);
		btnBack1.setBorderPainted(false);
		btnBack1.setContentAreaFilled(false);

		JButton btnTransac = new JButton("View in transaction >");
		btnTransac.setOpaque(false);
		btnTransac.setForeground(peonyText);
		btnTransac.setHorizontalAlignment(SwingConstants.LEFT);
		btnTransac.setFont(new Font("Arial", Font.PLAIN, 11));
		btnTransac.setContentAreaFilled(false);
		btnTransac.setBorderPainted(false);
		styleSmallButton(btnTransac);
		btnTransac.setBounds(657, 466, 146, 20);
		btnTransac.setEnabled(false);
		btnTransac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadingPanel();
				new Thread(() -> {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					javax.swing.SwingUtilities.invokeLater(() -> {
						cl.show(contentPane, "Transaction");
					});

				}).start();

			}
		});

		JPanel pnlMenu = new JPanel();
		contentPane.add(pnlMenu, "Menu");
		pnlMenu.setLayout(null);

		JButton btnSelectASeat = new JButton("Book A Ticket");
		btnSelectASeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Seats");
			}
		});
		btnSelectASeat.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnSelectASeat.setBackground(btnHover);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnSelectASeat.setBackground(menuButton);
			}

			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnSelectASeat.setBackground(btnPressed);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				btnSelectASeat.setBackground(btnHover);
			}
		});
		btnSelectASeat.setFont(new Font("Tw Cen MT", Font.BOLD, 29));
		styleMenuButton(btnSelectASeat);
		btnSelectASeat.setBounds(10, 171, 783, 58);

		JButton btnViewTransac = new JButton("View Transaction");
		btnViewTransac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Transaction");
			}
		});
		btnViewTransac.setFont(new Font("Tw Cen MT", Font.BOLD, 29));
		styleMenuButton(btnViewTransac);
		btnViewTransac.setBounds(10, 252, 783, 58);

		btnViewTransac.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btnViewTransac.setBackground(btnHover);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				btnViewTransac.setBackground(menuButton);
			}

			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnViewTransac.setBackground(btnPressed);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				btnViewTransac.setBackground(btnHover);
			}
		});
		JCheckBox showPass = new JCheckBox("Show password");
		showPass.setOpaque(false);
		showPass.setForeground(peonyText);
		showPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showPass.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('\u2022');
				}
			}
		});
		showPass.setFont(new Font("Arial", Font.PLAIN, 11));

		// MENU BAR
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 100, 21);
		menuBar.setOpaque(true);
		menuBar.setBackground(peonyPrimary);
		menuBar.setBorderPainted(false);
		JMenu mnNewMenu = new JMenu("Options");
		mnNewMenu.setHorizontalAlignment(SwingConstants.CENTER);
		mnNewMenu.setForeground(Color.white);
		mnNewMenu.setOpaque(true);
		mnNewMenu.setBackground(peonyPrimary);
		mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("<- LogOut");
		mntmNewMenuItem.setFont(new Font("Arial", Font.PLAIN, 10));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadingPanel();
				new Thread(() -> {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					javax.swing.SwingUtilities.invokeLater(() -> {
						passwordField.setText("");
						txtName.setText("");
						showPass.setSelected(false);
						cl.show(contentPane, "Log");
					});

				}).start();

			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		// FOR BUTTON LOG IN
		JButton btnLogIn = new JButton("Log in");
		btnLogIn.setFont(new Font("Arial", Font.BOLD, 12));
		btnLogIn.setForeground(peonyText);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = txtName.getText();
				String password = new String(passwordField.getPassword());

				if (name.equals("Enter your username.") || password.equals("Enter your password.")) {
					JOptionPane.showMessageDialog(null, "Please enter your credentials.");
					return;
				}

				if (name.equals("admin") && password.equals("123")) {

					loadingPanel();
					new Thread(() -> {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

						javax.swing.SwingUtilities.invokeLater(() -> {
							cl.show(contentPane, "Menu");
						});

					}).start();

				} else {
					JOptionPane.showMessageDialog(null, "Incorrect username or password", "ERROR MESSAGE",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		styleSmallButton(btnLogIn);

		ImageIcon bannerIcon = loadImageIcon("/resources/banner.png");
		JLabel banner;
		int bannerWidth = 271;
		int bannerHeight = 60;

		if (bannerIcon != null) {
			bannerHeight = bannerIcon.getIconHeight() * bannerWidth / bannerIcon.getIconWidth();
			Image scaledBanner = bannerIcon.getImage().getScaledInstance(bannerWidth, bannerHeight, Image.SCALE_SMOOTH);
			banner = new JLabel(new ImageIcon(scaledBanner));
		} else {
			banner = new JLabel("Peony", SwingConstants.CENTER);
			banner.setFont(new Font("Tw Cen MT", Font.BOLD, 32));
		}

		banner.setBackground(peonyBg);

		JLabel lblTagline = new JLabel("Bloom into every show with Peony tickets.", SwingConstants.CENTER);
		lblTagline.setFont(new Font("Arial", Font.ITALIC, 12));
		lblTagline.setForeground(peonyText);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < 16; i++) {
					if (!buttonsEvent1[i].getBackground().equals(bookedSeat)) {
						buttonsEvent1[i].setBackground(peonyPink);
						event1[i] = false;
					}

					if (!buttonsEvent2[i].getBackground().equals(bookedSeat)) {
						buttonsEvent2[i].setBackground(peonyPink);
						event2[i] = false;

					}

					if (!buttonsEvent3[i].getBackground().equals(bookedSeat)) {
						buttonsEvent3[i].setBackground(peonyPink);
						event3[i] = false;

					}

				}

				if (tabbedPane.getTabCount() > 0) {
					tabbedPane.setSelectedIndex(0);
				}

				spNumOfTicket.setValue(0);
				totalSelectedSeats = 0;
			}
		});
		btnClear.setBounds(0, 1, 139, 20);
		styleSmallButton(btnClear);

		JButton btnBook = new JButton("Book Ticket");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTransac.setEnabled(true);

				int currentSpin = (Integer) spNumOfTicket.getValue(); // GET THE VALUE OF SPINNER

				if (totalSelectedSeats != currentSpin) {
					JOptionPane.showMessageDialog(null,
							"Please select exactly " + currentSpin + " seats before booking.", "INFORMATION MESSAGE",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				// CHECK IF THE VALUE IS EQUAL TO ZERO
				if (currentSpin == 0) {
					JOptionPane.showMessageDialog(null, "Number of seats cannot be zero.", "INFORMATION MESSAGE",
							JOptionPane.INFORMATION_MESSAGE);

					return;
				}

				// GET THE VALUE OF THE SPINNER - PASSED IN SO THAT HANDLING IT CAN BE EASY
				int spinnerValue = currentSpin;

				for (int i = 0; i < 16; i++) {

					// FHUKERAT
					if (event1[i] == true) {
						if (spinnerValue > 0) {
							spinnerValue--;
						} // UPDATES THE VALUE OF THE SPINNER EVERY SUCCESSFUL TRANSACTION
						String mode;
						String seat = "";
						double price = 0;
						buttonsEvent1[i].setBackground(bookedSeat);

						if (i < 4) {
							price = 300;
							seat = seat + "Seat " + (i + 1);
							mode = "VIP";
						} else if (i > 3 && i < 10) {
							price = 200;
							seat = "Seat " + (i + 1);
							mode = "Semi-VIP";
						} else {
							price += 100;
							seat = "Seat " + (i + 1);
							mode = "Regular";
						}

						buttonsEvent1[i].setEnabled(false);
						model.addRow(new Object[] { "Concert", mode, seat, "₱ " + String.valueOf(price) });
						model1.addRow(new Object[] { name, "Concert", mode, seat, "₱ " + String.valueOf(price) });

						event1[i] = false;
					}

					// ALFY
					if (event2[i] == true) {
						if (spinnerValue > 0) {
							spinnerValue--;
						} // UPDATES THE VALUE OF THE SPINNER EVERY SUCCESSFUL TRANSACTION
						String mode;
						String seat = "";
						double price = 0;
						buttonsEvent2[i].setBackground(bookedSeat);

						if (i < 4) {
							price = 300;
							seat = seat + "Seat " + (i + 1);
							mode = "VIP";
						} else if (i > 3 && i < 10) {
							price = 200;
							seat = "Seat " + (i + 1);
							mode = "Semi-VIP";
						} else {
							price += 100;
							seat = "Seat " + (i + 1);
							mode = "Regular";
						}
						buttonsEvent2[i].setEnabled(false);
						model.addRow(new Object[] { "Sports", mode, seat, "₱ " + String.valueOf(price) });

						model1.addRow(new Object[] { name, "Sports", mode, seat, "₱ " + String.valueOf(price) });
						event2[i] = false;
					}

					if (event3[i] == true) {
						if (spinnerValue > 0) {
							spinnerValue--;
						} // UPDATES THE VALUE OF THE SPINNER EVERY SUCCESSFUL TRANSACTION
						String mode;
						String seat = "";
						double price = 0;
						buttonsEvent3[i].setBackground(bookedSeat);

						if (i < 4) {
							price = 300;
							seat = seat + "Seat " + (i + 1); // THESE ARE DATA FOR TABLE
							mode = "VIP";
						} else if (i > 3 && i < 10) {
							price = 200;
							seat = "Seat " + (i + 1);
							mode = "Semi-VIP";
						} else {
							price += 100;
							seat = "Seat " + (i + 1);
							mode = "Regular";
						}
						buttonsEvent3[i].setEnabled(false);
						model.addRow(new Object[] { "Movies", mode, seat, "₱ " + String.valueOf(price) });

						model1.addRow(new Object[] { name, "Movies", mode, seat, "₱ " + String.valueOf(price) });
						event3[i] = false;
					}
				}

				spNumOfTicket.setValue(0); // THIS REWRITE THE VALUE OF THE SPINNER
				totalSelectedSeats = 0; // THIS WILL RESET THE NUMBER OF SEAT SELECTED FOR THE CONDITION
			}
		});
		btnBook.setBounds(232, 1, 139, 20);
		styleSmallButton(btnBook);

		JPanel pnlButtons = new JPanel();
		pnlButtons.setBounds(6, 404, 371, 26);
		pnlButtons.setLayout(null);

		centerLogin(pnlLogIn, banner, lblTagline, txtName, passwordField, showPass, btnLogIn);
		centerMenu(pnlMenu, menuBar, btnSelectASeat, btnViewTransac);
		centerSeats(pnlSeatSelection, btnBack1, spNumOfTicket, lblNumOfTicket, chckbxConcert, chckbxSports,
				chckbxMovies, tabbedPane, scrollPane, pnlButtons, btnClear, btnBook, btnTransac);
		centerTransaction(pnlTransaction, btnBack2, lblTransac, spTransaction);

	}

	private void centerLogin(JPanel pnl, JLabel banner, JLabel lblTagline, JTextField txtUser, JPasswordField txtPass,
			JCheckBox showPass, JButton btnLogin) {
		pnl.removeAll();
		pnl.setBackground(peonyBg);
		pnl.setLayout(new GridBagLayout());

		JPanel pnlForm = new JPanel(new GridBagLayout());
		pnlForm.setOpaque(false);
		pnlForm.setPreferredSize(new Dimension(430, 300));

		GridBagConstraints gbc0 = makeGbc(0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER,
				new Insets(8, 12, 8, 12));
		pnlForm.add(banner, gbc0);

		GridBagConstraints gbc1 = makeGbc(0, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER,
				new Insets(8, 12, 8, 12));
		pnlForm.add(lblTagline, gbc1);

		txtUser.setPreferredSize(new Dimension(405, 31));
		GridBagConstraints gbc2 = makeGbc(0, 2, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER,
				new Insets(8, 12, 8, 12));
		pnlForm.add(txtUser, gbc2);

		txtPass.setPreferredSize(new Dimension(405, 31));
		GridBagConstraints gbc3 = makeGbc(0, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER,
				new Insets(8, 12, 8, 12));
		pnlForm.add(txtPass, gbc3);

		GridBagConstraints gbc4 = makeGbc(0, 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST,
				new Insets(8, 12, 8, 12));
		pnlForm.add(showPass, gbc4);

		GridBagConstraints gbc5 = makeGbc(0, 5, GridBagConstraints.NONE, GridBagConstraints.CENTER,
				new Insets(8, 12, 8, 12));
		pnlForm.add(btnLogin, gbc5);

		pnl.add(pnlForm, centerPlace());
	}

	private void centerMenu(JPanel pnl, JMenuBar menuBar, JButton btnBookSeat, JButton btnTransac) {
		pnl.removeAll();
		pnl.setBackground(peonyBg);
		pnl.setLayout(new BorderLayout());
		pnl.add(menuBar, BorderLayout.NORTH);

		JPanel pnlChoices = new JPanel(new GridBagLayout());
		pnlChoices.setOpaque(false);
		pnlChoices.setBorder(new EmptyBorder(30, 40, 30, 40));

		JLabel lblTitle = new JLabel("Peony Ticket Desk", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		lblTitle.setForeground(peonyText);

		JLabel lblStatus = new JLabel("System Online  |  Counter 01  |  Reservations open", SwingConstants.CENTER);
		lblStatus.setFont(new Font("Arial", Font.BOLD, 13));
		lblStatus.setForeground(peonyText);

		btnBookSeat.setPreferredSize(new Dimension(640, 64));
		btnTransac.setPreferredSize(new Dimension(640, 64));

		GridBagConstraints gbc0 = makeGbc(0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER,
				new Insets(12, 32, 12, 32));
		pnlChoices.add(lblTitle, gbc0);

		GridBagConstraints gbc1 = makeGbc(0, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER,
				new Insets(12, 32, 12, 32));
		pnlChoices.add(lblStatus, gbc1);

		GridBagConstraints gbc2 = makeGbc(0, 2, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER,
				new Insets(12, 32, 12, 32));
		pnlChoices.add(btnBookSeat, gbc2);

		GridBagConstraints gbc3 = makeGbc(0, 3, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER,
				new Insets(12, 32, 12, 32));
		pnlChoices.add(btnTransac, gbc3);

		pnl.add(pnlChoices, BorderLayout.CENTER);
	}

	private void centerSeats(JPanel pnl, JButton btnBack, JSpinner spinner, JLabel lblTicket, JCheckBox chkFhukerat,
			JCheckBox chkAlfy, JCheckBox chkTaylor, JTabbedPane tabs, JScrollPane tableScroll, JPanel pnlButtons,
			JButton btnClear, JButton btnBook, JButton btnTransac) {
		pnl.removeAll();
		pnl.setBackground(peonyBg);
		pnl.setLayout(new BorderLayout(12, 12));
		pnl.setBorder(new EmptyBorder(10, 12, 10, 12));

		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.setOpaque(false);
		pnlTop.add(btnBack, BorderLayout.WEST);

		JPanel pnlSelect = new JPanel(new GridBagLayout());
		pnlSelect.setOpaque(false);

		pnlSelect.add(spinner,
				makeGbc(0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, new Insets(4, 8, 4, 8)));
		pnlSelect.add(lblTicket,
				makeGbc(1, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, new Insets(4, 8, 4, 8)));
		pnlSelect.add(chkFhukerat,
				makeGbc(0, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, new Insets(4, 8, 4, 8)));
		pnlSelect.add(chkAlfy,
				makeGbc(1, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, new Insets(4, 8, 4, 8)));
		pnlSelect.add(chkTaylor,
				makeGbc(2, 1, GridBagConstraints.NONE, GridBagConstraints.CENTER, new Insets(4, 8, 4, 8)));

		pnlTop.add(pnlSelect, BorderLayout.CENTER);
		pnl.add(pnlTop, BorderLayout.NORTH);

		fixSeatPanel(pnlEvent1);
		fixSeatPanel(pnlEvent2);
		fixSeatPanel(pnlEvent3);

		tabs.setPreferredSize(new Dimension(420, 320));
		tableScroll.setPreferredSize(new Dimension(420, 320));

		JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 12, 0));
		pnlCenter.setOpaque(false);
		JPanel pnlSeatBox = new JPanel(new BorderLayout(0, 6));
		pnlSeatBox.setOpaque(false);

		JLabel lblStage = new JLabel("STAGE / FRONT VIEW", SwingConstants.CENTER);
		lblStage.setOpaque(true);
		lblStage.setBackground(menuButton);
		lblStage.setForeground(Color.white);
		lblStage.setFont(new Font("Arial", Font.BOLD, 12));

		pnlSeatBox.add(lblStage, BorderLayout.NORTH);
		pnlSeatBox.add(tabs, BorderLayout.CENTER);
		pnlCenter.add(pnlSeatBox);
		pnlCenter.add(tableScroll);
		pnl.add(pnlCenter, BorderLayout.CENTER);

		pnlButtons.removeAll();
		pnlButtons.setOpaque(false);
		pnlButtons.setLayout(new GridLayout(1, 2, 12, 0));
		pnlButtons.add(btnClear);
		pnlButtons.add(btnBook);

		JPanel pnlBottom = new JPanel(new BorderLayout());
		pnlBottom.setOpaque(false);
		pnlBottom.add(pnlButtons, BorderLayout.WEST);
		pnlBottom.add(makeLegend(), BorderLayout.CENTER);
		pnlBottom.add(btnTransac, BorderLayout.EAST);
		pnl.add(pnlBottom, BorderLayout.SOUTH);
	}

	private void centerTransaction(JPanel pnl, JButton btnBack, JLabel lblTitle, JScrollPane tableScroll) {
		pnl.removeAll();
		pnl.setBackground(peonyBg);
		pnl.setLayout(new BorderLayout(12, 12));
		pnl.setBorder(new EmptyBorder(10, 12, 10, 12));

		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.setOpaque(false);
		pnlTop.add(btnBack, BorderLayout.WEST);
		pnlTop.add(lblTitle, BorderLayout.CENTER);

		pnl.add(pnlTop, BorderLayout.NORTH);
		pnl.add(tableScroll, BorderLayout.CENTER);
	}

	private void fixSeatPanel(JPanel pnlEvent) {
		if (pnlEvent.getComponentCount() == 0) {
			return;
		}

		Component seats = pnlEvent.getComponent(0);
		pnlEvent.removeAll();
		pnlEvent.setLayout(new GridBagLayout());
		pnlEvent.add(seats, centerPlace());
	}

	private JPanel makeLegend() {
		JPanel pnlLegend = new JPanel(new GridBagLayout());
		pnlLegend.setOpaque(false);

		pnlLegend.add(makeLegendText("Available", peonyPink),
				makeGbc(0, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, new Insets(0, 8, 0, 8)));
		pnlLegend.add(makeLegendText("Selected", selectedSeat),
				makeGbc(1, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, new Insets(0, 8, 0, 8)));
		pnlLegend.add(makeLegendText("Reserved", bookedSeat),
				makeGbc(2, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, new Insets(0, 8, 0, 8)));

		return pnlLegend;
	}

	private JLabel makeLegendText(String text, Color color) {
		JLabel lbl = new JLabel(text);
		lbl.setOpaque(true);
		lbl.setBackground(color);
		lbl.setForeground(peonyText);
		lbl.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
		return lbl;
	}

	private void styleMenuButton(JButton btn) {
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setBorderPainted(false);
		btn.setBackground(menuButton);
		btn.setForeground(Color.white);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	}

	private void styleSmallButton(JButton btn) {
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setBorderPainted(false);
		btn.setBackground(peonySoft);
		btn.setForeground(peonyText);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
	}

	private void styleSeatButton(JButton btn) {
		btn.setPreferredSize(new Dimension(54, 54));
		btn.setBackground(peonyPink);
		btn.setForeground(peonyText);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setFocusPainted(false);
	}

	private GridBagConstraints makeGbc(int x, int y, int fill, int anchor, Insets space) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.fill = fill;
		gbc.anchor = anchor;
		gbc.insets = space;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		return gbc;
	}

	private GridBagConstraints centerPlace() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		return gbc;
	}

	private JPanel seat1() {
		JPanel pnlSeats = new JPanel();
		pnlSeats.setBorder(null);
		pnlSeats.setOpaque(false);
		pnlSeats.setBounds(0, 10, 365, 208);
		pnlSeats.setPreferredSize(new Dimension(354, 174));
		pnlSeats.setLayout(new GridLayout(3, 6, 6, 6));

		int index = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {

				if (i < 1 && (j > 1 && j < 4)) {
					pnlSeats.add(new JLabel(""));
				} else {

					int currentIndex = index;

					// EVENTS TRUE: SELECTED | FALSE: NOT SELECTED

					buttonsEvent1[currentIndex] = new JButton("S " + (currentIndex + 1));
					styleSeatButton(buttonsEvent1[currentIndex]);

					// THIS IS TO SET DUMMY DATA IN LINE 187
					if (currentIndex == 8 || currentIndex == 5 || currentIndex == 12) {
						buttonsEvent1[currentIndex].setEnabled(false);
						buttonsEvent1[currentIndex].setBackground(bookedSeat);
					}

					buttonsEvent1[currentIndex].addActionListener(e -> {

						int maxTickets = (Integer) spNumOfTicket.getValue();

						// SELECT SEAT
						if (!event1[currentIndex]) {

							if (totalSelectedSeats < maxTickets) {
								buttonsEvent1[currentIndex].setBackground(selectedSeat);
								event1[currentIndex] = true;
								totalSelectedSeats++;
							} else {
								JOptionPane.showMessageDialog(null, "You can only select " + maxTickets + " seats.",
										"INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
							}

						}
						// DESELECT seat
						else {
							buttonsEvent1[currentIndex].setBackground(peonyPink);
							event1[currentIndex] = false;
							totalSelectedSeats--;
						}
					});

					pnlSeats.add(buttonsEvent1[currentIndex]);
					index++;
				}
			}
		}

		return pnlSeats;
	}

	private JPanel seat2() {
		JPanel pnlSeats = new JPanel();
		pnlSeats.setBorder(null);
		pnlSeats.setOpaque(false);
		pnlSeats.setBounds(0, 10, 365, 208);
		pnlSeats.setPreferredSize(new Dimension(354, 174));
		pnlSeats.setLayout(new GridLayout(3, 6, 6, 6));

		int index = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {

				if (i < 1 && (j > 1 && j < 4)) {
					pnlSeats.add(new JLabel(""));
				} else {
					int currentIndex = index;

					buttonsEvent2[currentIndex] = new JButton("S " + (currentIndex + 1));
					styleSeatButton(buttonsEvent2[currentIndex]);

					buttonsEvent2[currentIndex].addActionListener(e -> {

						int maxTickets = (Integer) spNumOfTicket.getValue();

						// SELECT seat
						if (!event2[currentIndex]) {

							if (totalSelectedSeats < maxTickets) {
								buttonsEvent2[currentIndex].setBackground(selectedSeat);
								event2[currentIndex] = true;
								totalSelectedSeats++;
							} else {
								JOptionPane.showMessageDialog(null, "You can only select " + maxTickets + " seats.",
										"INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
							}

						}
						// DESELECT seat
						else {
							buttonsEvent2[currentIndex].setBackground(peonyPink);
							event2[currentIndex] = false;
							totalSelectedSeats--;
						}
					});

					pnlSeats.add(buttonsEvent2[currentIndex]);
					index++;
				}
			}
		}

		return pnlSeats;
	}

	private JPanel seat3() {
		JPanel pnlSeats = new JPanel();
		pnlSeats.setBorder(null);
		pnlSeats.setOpaque(false);
		pnlSeats.setBounds(0, 10, 365, 208);
		pnlSeats.setPreferredSize(new Dimension(354, 174));
		pnlSeats.setLayout(new GridLayout(3, 6, 6, 6));

		int index = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {

				if (i < 1 && (j > 1 && j < 4)) {
					pnlSeats.add(new JLabel(""));
				} else {
					int currentIndex = index;

					buttonsEvent3[currentIndex] = new JButton("S " + (currentIndex + 1));
					styleSeatButton(buttonsEvent3[currentIndex]);

					buttonsEvent3[currentIndex].addActionListener(e -> {

						int maxTickets = (Integer) spNumOfTicket.getValue();

						// SELECT seat
						if (!event3[currentIndex]) {

							if (totalSelectedSeats < maxTickets) {
								buttonsEvent3[currentIndex].setBackground(selectedSeat);
								event3[currentIndex] = true;
								totalSelectedSeats++;
							} else {
								JOptionPane.showMessageDialog(null, "You can only select " + maxTickets + " seats.",
										"INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
							}

						}
						// DESELECT seat
						else {
							buttonsEvent3[currentIndex].setBackground(peonyPink);
							event3[currentIndex] = false;
							totalSelectedSeats--;
						}
					});

					pnlSeats.add(buttonsEvent3[currentIndex]);
					index++;
				}
			}
		}

		return pnlSeats;
	}

	private void loadingPanel() {
		pnlLoading.removeAll();
		pnlLoading.setBackground(peonyBg);
		pnlLoading.setLayout(new GridBagLayout());

		URL resource = getResourceUrl("/resources/loading.gif");
		if (resource != null) {
			ImageIcon appIcon = loadImageIcon("/resources/icon.png");
			if (appIcon != null) {
				setIconImage(appIcon.getImage());
			}
			ImageIcon loadingIcon = new ImageIcon(resource);

			JLabel loadingLabel = new JLabel(loadingIcon);
			loadingLabel.setHorizontalAlignment(JLabel.CENTER);
			pnlLoading.add(loadingLabel, centerPlace());

		} else {
			JProgressBar progressBar = new JProgressBar(0, 100);
			progressBar.setPreferredSize(new Dimension(500, 30));
			progressBar.setValue(0);
			progressBar.setStringPainted(true);

			pnlLoading.add(progressBar, centerPlace());

			// LOADING PROGRESS
			new Thread(() -> {
				for (int i = 0; i <= 100; i++) {
					final int value = i;
					try {
						Thread.sleep(18); // SPEED
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}

					SwingUtilities.invokeLater(() -> {
						progressBar.setValue(value);
					});
				}
			}).start();
		}

		pnlLoading.revalidate();
		pnlLoading.repaint();
		cl.show(contentPane, "Loading");
	}

	private ImageIcon loadImageIcon(String resourcePath) {
		URL resource = getResourceUrl(resourcePath);
		return resource == null ? null : new ImageIcon(resource);
	}

	private URL getResourceUrl(String resourcePath) {
		URL resource = getClass().getResource(resourcePath);
		if (resource == null) {
			System.err.println("Missing resource: " + resourcePath
					+ ". Make sure src/resources is copied to the runtime classpath.");
		}
		return resource;
	}
}



import java.awt.Color; 
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.github.sarxos.webcam.Webcam;

public class caroServer {
	
	public static JFrame f,f2;
	JPanel jpnBanCo;
	JPanel panel_1;
	JTextArea jtaChat;
	JTextField jtfSend;
	JButton jbtSend;
	JLabel jlbTimer;
	
	JLabel jlbName;
	JLabel jlbSetName;
	JLabel jlbImg1;
	JLabel jlbImg2;
	JLabel jlbSetOCo;
	JLabel jlbOCo;
	ImageIcon imic;
	
	int soOCo = 0;
	int soOCoClient = 0;
	int tong = 0;
	
	Integer second;
	Integer minute;
	Timer thoiGian;
	
	JLabel jlbIP;
	JTextField jtfIP;
	JButton jbtIP;
	JLabel jlbNameConnect;
	JTextField jtfNameConnect;
	MenuBar mnBar;
	boolean KeyPressShift;
	boolean dauhang;
	boolean timeOut;
	
	final int x = 16;
	final int y = 16;
	int xx, yy;
	
	String mess = "";
	String setName1 = "";
	
	String sendnamechat = "";
	String nmchat = "";
	
	String player1 = "";
	String player2 = "";
	
	JButton[][] jbtOCo;
	int[][] maTran;
	int[][] maTranDanh;
	
	bdImgServer bdimgserver;
	
	ServerSocket svrSocket;
	Socket socket;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	OutputStream os;
	InputStream is;
	BufferedImage bm;

	static caroServer caroserver;
	public static void main(String[] args) {
		
		caroserver = new caroServer();
	}
	
	public caroServer() {
		f = new JFrame();
		f.setTitle("Game Cờ Caro Trực Tuyến");
		f.setSize(1000, 620);
		f.getContentPane().setLayout(null);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setVisible(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f2 = new JFrame();
		f2.setSize(350, 110);
		f2.getContentPane().setLayout(null);
		f2.setLocationRelativeTo(null);
		f2.setResizable(false);
		f2.setVisible(true);
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jbtIP = new JButton("Conect");
		jbtIP.setFont(new Font("Cambria", Font.BOLD, 16));
		jbtIP.setBounds(225, 23, 90, 27);
		f2.add(jbtIP);
		jbtIP.setVisible(false);
		jbtIP.setVisible(true);
		
		jlbNameConnect = new JLabel("Name: ");
		jlbNameConnect.setFont(new Font("Cambria", Font.BOLD, 16));
		jlbNameConnect.setBounds(29, 20, 60, 30);
		f2.add(jlbNameConnect);
		jlbNameConnect.setVisible(false);
		jlbNameConnect.setVisible(true);
		
		jtfNameConnect = new JTextField();
		jtfNameConnect.setText("");
		jtfNameConnect.setFont(new Font("Cambria", Font.BOLD, 16));
		jtfNameConnect.setBounds(80, 23, 135, 27);
		f2.add(jtfNameConnect);
		jtfNameConnect.setVisible(false);
		jtfNameConnect.setVisible(true);
		
		jbtIP.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				player1 = jtfNameConnect.getText();
				jlbSetName.setText(jtfNameConnect.getText());
				
				if (jtfNameConnect.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Bạn hãy nhập tên !!!");				
				} else {
					f2.setVisible(false);
					f2.dispose();
					f.setVisible(true);
				}
				
			}
		});
		
		mnBar = new MenuBar();
		f.setMenuBar(mnBar);
		Menu game = new Menu("Game");
		game.setFont(new Font("Cambria", Font.BOLD, 14));
		mnBar.add(game);
		Menu help = new Menu("Help");
		help.setFont(new Font("Cambria", Font.BOLD, 14));
		mnBar.add(help);
		
		MenuItem mnhelp = new MenuItem("Help");
		mnhelp.setFont(new Font("Cambria", Font.BOLD, 14));
		MenuItem mnabout = new MenuItem("About");
		mnabout.setFont(new Font("Cambria", Font.BOLD, 14));
		help.add(mnhelp);
		help.add(mnabout);
		help.addSeparator();
		
		mnhelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showConfirmDialog(f, "Nhiệm vụ của các bạn là đánh các nước cờ là X hoặc O sao cho \n"
						+ " 5 nước cờ của mình thành một đường thẳng, chéo hoặc ngang","Luật chơi",JOptionPane.CLOSED_OPTION);
			}
		});
		
		MenuItem mnnewgame = new MenuItem("New Game");
		mnnewgame.setFont(new Font("Cambria", Font.BOLD, 14));
		MenuItem mnhistory = new MenuItem("History");
		mnhistory.setFont(new Font("Cambria", Font.BOLD, 14));
		MenuItem menuthoat = new MenuItem("Out");
		menuthoat.setFont(new Font("Cambria", Font.BOLD, 14));
		game.add(mnnewgame);
		game.add(mnhistory);
		game.add(menuthoat);
		game.addSeparator();
		
		mnnewgame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				newGame();
				
				try {
					oos.writeObject("newgame,choilai");
				}catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		
		menuthoat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int n = JOptionPane.showConfirmDialog(f, "Bạn có thực sự muốn thoát !!!", "Thông báo", JOptionPane.YES_NO_OPTION);
		    	 
		    	 if (n == JOptionPane.YES_OPTION) {
		    		 try {
		    			 System.exit(0);
		    		 }catch (Exception e1) {
		    			 e1.printStackTrace();
		    		 }
		    	 } else if (n == JOptionPane.NO_OPTION) {
		    		 
		    	 }
				
			}
		});
		
		mnhistory.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xemLichSu xls = new xemLichSu();
				
			}
		});
			
		jpnBanCo = new JPanel();
		jpnBanCo.setBounds(430, 10, 540, 540);
		jpnBanCo.setLayout(new GridLayout(x, y));
		f.add(jpnBanCo);
		
		panel_1 = new JPanel();
		panel_1.setBounds(10, 15, 410, 190);
		panel_1.setBorder(new LineBorder(Color.pink));
		panel_1.setLayout(null);
		f.add(panel_1);
		
		jlbImg1 = new JLabel();
		jlbImg1.setBorder(new LineBorder(Color.white));
		jlbImg1.setBounds(15, 10, 214, 170);	
		panel_1.add(jlbImg1);
		
		jtaChat = new JTextArea();
		jtaChat.setFont(new Font("Cambria", Font.BOLD, 17));
		JScrollPane jspJtaChat = new JScrollPane(jtaChat);
		jspJtaChat.setBounds(10, 220, 410, 270);
		jtaChat.setEditable(false);
		f.add(jspJtaChat);
		jspJtaChat.setVisible(false);
		jspJtaChat.setVisible(true);
		
		jtfSend = new JTextField();
		jtfSend.setOpaque(false);
		jtfSend.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(30, 144, 255)));
		jtfSend.setFont(new Font("Cambria", Font.BOLD, 17));
		jtfSend.setBounds(20, 508, 348, 36);
		f.add(jtfSend);
		jtfSend.setColumns(10);
		jtfSend.setVisible(false);
		jtfSend.setVisible(true);
		
		KeyPressShift = false;
		
		jtfSend.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER && KeyPressShift==false) {
					sendMess();
				}
				if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
					KeyPressShift = false;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER && KeyPressShift==true) {
					String a="";
					a += '\n';
					jtfSend.setText(a);
				}
				if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
					KeyPressShift = true;
				}
			}
		});
		
		jbtSend = new JButton();
		jbtSend.setFont(new Font("Cambria", Font.BOLD, 16));
		jbtSend.setBounds(370, 508, 50, 40);
		f.add(jbtSend);
		jbtSend.setVisible(false);
		jbtSend.setVisible(true);
		
		jbtSend.setIcon(new ImageIcon("D:\\BT_Eclipse\\icon\\icon_send_9.png"));
		//xóa background JButton
		jbtSend.setBorderPainted(false); 
		jbtSend.setContentAreaFilled(false); 
		jbtSend.setFocusPainted(false); 
		jbtSend.setOpaque(false);
		
		jbtSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				sendMess();
				
			}
		});
		
		jlbName = new JLabel("Name: ");
		jlbName.setFont(new Font("Cambria", Font.BOLD, 16));
		jlbName.setBounds(250, 20, 60, 30);
		panel_1.add(jlbName);
		jlbName.setVisible(false);
		jlbName.setVisible(true);
		
		jlbSetName = new JLabel();
		jlbSetName.setFont(new Font("Cambria", Font.BOLD, 16));
		jlbSetName.setBounds(320, 20, 120, 30);
		panel_1.add(jlbSetName);
		jlbSetName.setVisible(false);
		jlbSetName.setVisible(true);
		
		jlbOCo = new JLabel("YOU: ");
		jlbOCo.setFont(new Font("Cambria", Font.BOLD, 16));
		jlbOCo.setBounds(250, 55, 60, 30);
		panel_1.add(jlbOCo);
		
		jlbSetOCo = new JLabel();
		jlbSetOCo.setIcon(new ImageIcon("D:\\BT_Eclipse\\icon\\o1.png"));
		jlbSetOCo.setBounds(300, 52, 32, 32);
		panel_1.add(jlbSetOCo);
		
		jlbImg2 = new JLabel();		
		jlbImg2.setIcon(new ImageIcon("D:\\BT_Eclipse\\icon\\playo3.png"));
		jlbImg2.setBorder(new LineBorder(Color.white));
		jlbImg2.setBounds(250, 120, 55, 60);
		bdimgserver = new bdImgServer(jlbImg2);
		bdimgserver.start();	
		panel_1.add(jlbImg2);
		
		jlbTimer = new JLabel("Time out:   05:00");
		jlbTimer.setFont(new Font("cambria", Font.BOLD, 16));
		jlbTimer.setBounds(250, 87, 150, 25);
		panel_1.add(jlbTimer);
		
		JButton jbtDauHang = new JButton("Give in");
		jbtDauHang.setFont(new Font("Cambria", Font.BOLD, 16));
		jbtDauHang.setBounds(315, 135, 85, 32);
//		panel_1.add(jbtDauHang);
		
		minute = 4;
		second = 59;
		
		thoiGian = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String phut = minute.toString();
				String giay = second.toString();
				
				if (phut.length() == 1) {
					phut = "0" + phut;
				}
				if (giay.length() == 1) {
					giay = "0" + giay;
				}
				
				if (minute == 0 && second == 0) {
					
					try {
				   		 oos.writeObject("youwin,thangroi");
				   	 }catch (Exception e1) {
				   		 e1.printStackTrace();
				   	 }
					
					int n = JOptionPane.showConfirmDialog(f, "YOU LOST !!! PLAY AGAIN !!!", "Thong bao", JOptionPane.YES_NO_OPTION);
				   	 
				   	 if (n == JOptionPane.YES_OPTION) {
				   		
				   		 newGame();

				   		 try {
				   			 oos.writeObject("newgame,choilai");
				   		 }catch (Exception e1) {
				   			 e1.printStackTrace();
				   		 }
				   		
				   	 } else if (n == JOptionPane.NO_OPTION) {
				   		thoiGian.stop();
				   		jlbTimer.setText("Time out:   05:00");
				   		
				   	 }											
			
				}
				
				second --;
				
				if (second == -1) {
					second = 59;
					minute --;
				}
				jlbTimer.setText("Time out:   " + phut + ":" + giay);				
			}
		});
		
		jbtOCo = new JButton[x][y];
		maTran = new int[x][y];
		maTranDanh = new int[x][y];
		
		for (int i = 0;i < x;i ++) {
			for (int j = 0;j < y;j ++) {
				int a = i, b = j;
				jbtOCo[a][b] = new JButton();
				jbtOCo[a][b].setBackground(new Color(151, 255, 255));
				jbtOCo[a][b].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						thoiGian.stop();
						
						 maTranDanh[a][b] = 1; 
						 soOCo ++;
						 
						 jbtOCo[a][b].removeActionListener(this);
						 jbtOCo[a][b].setIcon(new ImageIcon("D:\\BT_Eclipse\\icon\\o1.png"));
						
						try {
							oos.writeObject("kt," + a + "," +b);
							
							setEnableButton(false);
							bdimgserver.suspend();
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					}
				});
				
				jpnBanCo.add(jbtOCo[a][b]);
				jpnBanCo.setVisible(false);
				jpnBanCo.setVisible(true);
			}
		}
		
		webcamUDPServer wb = new webcamUDPServer();
		wb.start();
		
		try {
			
			svrSocket = new ServerSocket(1701);
			System.out.println("Server is running");
			socket = svrSocket.accept();
			System.out.println("Client da ket noi");
        	os = socket.getOutputStream();
        	is = socket.getInputStream();
        	oos = new ObjectOutputStream(os);
        	ois = new ObjectInputStream(is);
      	
			while (true) {
				
				String luong = ois.readObject().toString();
				String[] dulieu = luong.split(",");
				
				if (dulieu[0].equals("namechat")) {
					nmchat = dulieu[1] + ": ";
				} else if (dulieu[0].equals("chat")) {
					
					Integer minuteMess = Calendar.getInstance().getTime().getMinutes();
					String getminuteMess = minuteMess.toString();
					if (getminuteMess.length() == 1) {
						getminuteMess = "0" + getminuteMess;
					}
					
					Integer hourMess = Calendar.getInstance().getTime().getHours();
					String gethourMess = hourMess.toString();
					if (gethourMess.length() == 1) {
						gethourMess = "0" + gethourMess;
					}
					
                    mess += "["+gethourMess+":"+ getminuteMess + "] " + nmchat + dulieu[1] + '\n';
                    jtaChat.setText(mess);
				} else if (dulieu[0].equals("kt")) {
					thoiGian.start();
					hienThiODanh(dulieu[1], dulieu[2]);
					setEnableButton(true);
					bdimgserver.resume();
				} else if (dulieu[0].equals("newgame")) {
					newGame();
				} else if (dulieu[0].equals("player2")) {
					player2 = dulieu[1];
				} else if (dulieu[0].equals("sooco")) {
					soOCoClient = Integer.parseInt(dulieu[1]);
				} else if (dulieu[0].equals("youwin")) {
					JOptionPane.showMessageDialog(null, "YOU WIN");
					tong = soOCo + soOCoClient;
					ConnectSql.insert(player1, player2, player1, tong);
					soOCo = 0;
					soOCoClient = 0;
					tong = 0;
				}			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void sendMess() {
		if (jtfSend.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Hãy nhập gì đó !!!");
		} else {
			try {
				
				oos.writeObject("namechat," + jtfNameConnect.getText());
				
				Integer minuteMess = Calendar.getInstance().getTime().getMinutes();
				String getminuteMess = minuteMess.toString();
				if (getminuteMess.length() == 1) {
					getminuteMess = "0" + getminuteMess;
				}
				
				Integer hourMess = Calendar.getInstance().getTime().getHours();
				String gethourMess = hourMess.toString();
				if (gethourMess.length() == 1) {
					gethourMess = "0" + gethourMess;
				}
				
				mess+= "["+gethourMess+":"+getminuteMess + "] " + "You: " + jtfSend.getText() + "\n";
                jtaChat.setText(mess);
                
				oos.writeObject("chat," + jtfSend.getText());

                jtfSend.setText("");
                jtfSend.requestFocus();      
                jtaChat.setVisible(false);
                jtaChat.setVisible(true);
				
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void hienThiODanh(String x, String y) {
		
		 xx = Integer.parseInt(x);
	     yy = Integer.parseInt(y);
	     maTran[xx][yy] = 1;
	     maTranDanh[xx][yy] = 1;
	     
	     jbtOCo[xx][yy].setIcon(new ImageIcon("D:\\BT_Eclipse\\icon\\x1.png"));
			
 
	     tong = soOCo + soOCoClient;
	     
	     if (checkWinHang(x,y) == 1 || checkWinCot(x,y) == 1 || checkWinCheoChinh(x,y) == 1 || checkWinCheoPhu(x,y) == 1 || timeOut == true) {
	    	 
	    	 xuThua();
	    	 
	     }   
	}
	
	public void setEnableButton(boolean b) {
        for (int i = 0; i < x; i++) {
        	for (int j = 0; j < y; j++) {
        		if (maTranDanh[i][j] == 0)
        			jbtOCo[i][j].setEnabled(b);
            }
        }
	}
	
	public void newGame() {
		
		
		
		for (int i = 0;i < x;i ++) {
			for (int j = 0;j < y;j ++) {
				jbtOCo[i][j].setIcon(null);
				jbtOCo[i][j].setBackground(new Color(151, 255, 255));
				maTran[i][j] = 0;
				maTranDanh[i][j] = 0;
			}
		}
		setEnableButton(true);
		
		jlbTimer.setText("Time out:   05:00");
		thoiGian.stop();
		minute = 4;
  		second = 59;
	}
	
	public int checkWinHang(String a, String b) {
		int hangtrai = 0, hangphai = 0;
		int hangtraiplus = 0, hangphaiplus = 0;
		xx = Integer.parseInt(a);
	    yy = Integer.parseInt(b);
		
//		xx truc dung
//		yy truc ngang
		
		for (int i = yy;i < x;i ++) {
			if (maTran[xx][i] == 1) {
				hangphai ++;
				hangphaiplus = i + 1;
			} else {
				break;
			}
		}
		
		for (int j = yy - 1;j >= 0;j --) {
			if (maTran[xx][j] == 1) {
				hangtrai ++;
				hangtraiplus = j - 1;
			}
			else {
				break;
			}
		}
		
		if (hangtrai + hangphai == 5 && (hangphaiplus == 16 || hangtraiplus == -1)) {
			return 1;
		} else if (hangtrai + hangphai == 5 && maTranDanh[xx][hangphaiplus] == 1 && maTranDanh[xx][hangtraiplus] == 1) {
			return 0;
		} else if (hangtrai + hangphai == 5 ) {
			System.out.println("phai "+hangphaiplus);
			System.out.println("trai "+hangtraiplus);
			return 1;
		} else return 0;
		
	}
	
	public int checkWinCot(String a, String b) {
		int cottren = 0, cotduoi = 0;
		int cottrenplus = 0, cotduoiplus = 0;
		xx = Integer.parseInt(a);
	    yy = Integer.parseInt(b);
		
		for (int i = xx;i < y;i ++) {
			if (maTran[i][yy] == 1) {
				cotduoi ++;
				cotduoiplus = i + 1;
			} else {
				break;
			}
		}
		
		for (int j = xx - 1;j >= 0;j --) {
			if (maTran[j][yy] == 1) {
				cottren ++;
				cottrenplus = j - 1;
			}
			else {
				break;
			}
		}
		
		if (cotduoi + cottren == 5 && (cotduoiplus == 16 || cottrenplus == -1)) {
			return 1;
		} else if (cotduoi + cottren == 5 && maTranDanh[cotduoiplus][yy] == 1 && maTranDanh[cottrenplus][yy] == 1) {
			return 0;
		} else if (cotduoi + cottren == 5 ) {
			return 1;
		} else return 0;
	}
	
	public int checkWinCheoChinh(String a, String b) {
		int cheotren = 0, cheoduoi = 0;
		xx = Integer.parseInt(a);
	    yy = Integer.parseInt(b);

		for (int i = 0;i <= yy;i ++) {
			if (yy-i < 0 || xx - i < 0) {
				break;
			}
			if (maTran[xx-i][yy-i] == 1) {
				cheotren ++;
			} else {
				break;
			}
		}
		
		for (int j = 1;j <= x - yy;j ++) {
			if (xx+j >= y || yy+j >= x) {
				break;
			}
			if (maTran[xx+j][yy+j] == 1) {
				cheoduoi ++;
			}
			else {
				break;
			}
		}
		
		if (cheotren + cheoduoi == 5) {
			return 1;
		} else return 0;
		
	}
	
//	public int checkWinCheoPhu(String a, String b) {
//		int cheotren = 0, cheoduoi = 0;
//		xx = Integer.parseInt(a);
//	    yy = Integer.parseInt(b);
//		
//	    for (int i = 0;i <= yy;i ++) {
//			if (yy+i > x || xx - i < 0) {
//				break;
//			}
//			if (maTran[xx-i][yy+i] == 1) {
//				cheotren ++;
//			} else {
//				break;
//			}
//		}
//		
//		for (int j = 1;j <= x - yy;j ++) {
//			if (xx+j >= y || yy-j < 0) {
//				break;
//			}
//			if (maTran[xx+j][yy-j] == 1) {
//				cheoduoi ++;
//			}
//			else {
//				break;
//			}
//		}
//		
//		if (cheotren + cheoduoi == 5) {
//			return 1;
//		} else return 0;
//		
//	}
	
	public int checkWinCheoPhu(String a, String b)
    {
		xx = Integer.parseInt(a);
	    yy = Integer.parseInt(b);
        int count = 1;
        int c = xx + 1;
        int d = yy - 1;
        while (c < y && d >= 0 && maTran[xx][yy] == maTran[c][d])
        {
            count++;
            c++;
            d--;
        }
        c = xx - 1;
        d = yy + 1;
        while (c >= 0 && d < x && maTran[xx][yy] == maTran[c][d])
        {
            count++;
            c--;
            d++;
        }
        if (count == 5) {
        	return 1;
        } else {
        	return 0;
        }
    }
	
	public class xemLichSu extends JFrame {
		xemLichSu() {
			super("History");
			JFrame f3 = new JFrame();
			f3.setSize(635, 385);
			f3.getContentPane().setLayout(null);
			f3.setLocationRelativeTo(null);
			f3.setResizable(false);
			f3.setVisible(true);
			
			DefaultTableModel tbhs = new DefaultTableModel();
			JTable tablehs = new JTable(tbhs);
			tablehs.setFont(new Font("Cambria", Font.BOLD, 14));
			tablehs.getTableHeader().setFont(new Font("Cambria", Font.BOLD, 14));

			tbhs.addColumn("Stt");
			tbhs.addColumn("Người chơi 1");
			tbhs.addColumn("Người chơi 2");
			tbhs.addColumn("Thắng");
			tbhs.addColumn("Tổng số Ô đã đánh");

			TableColumn cot = null;

			for (int i = 0; i < 4; i++) {
				cot = tablehs.getColumnModel().getColumn(i);
				if (i == 0) {
					cot.setPreferredWidth(10);
				} else if (i == 1) {
					cot.setPreferredWidth(80);
				} else if (i == 2) {
					cot.setPreferredWidth(80);
				} else if (i == 3) {
					cot.setPreferredWidth(80);
				} else if (i == 4) {
					cot.setPreferredWidth(60);
				}
			}
			
			JScrollPane jshs = new JScrollPane(tablehs);
			Border bor = BorderFactory.createEtchedBorder(Color.BLUE, Color.CYAN);
			TitledBorder ttbdtable = BorderFactory.createTitledBorder(bor);
			jshs.setBorder(ttbdtable);
			jshs.setBounds(10, 10, 600, 300);
			f3.add(jshs);
			
			JButton jbtDeleteAll = new JButton("Xóa Lịch Sử");
			jbtDeleteAll.setFont(new Font("Cambria", Font.BOLD, 16));
			jbtDeleteAll.setBounds(490, 315, 120, 28);
			f3.add(jbtDeleteAll);
			jbtDeleteAll.setVisible(false);
			jbtDeleteAll.setVisible(true);
			
			jbtDeleteAll.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ConnectSql.deleteAllData();
					tbhs.setRowCount(0);
					
				}
			});
					
			try {
				
				String url = "jdbc:sqlite:./lichSuThanhThua.db";
				Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs1 = stmt.executeQuery("select * from thangThua");

				while (rs1.next()) {

					int stt = Integer.valueOf(rs1.getString(1));
					String player1 = rs1.getString(2);
					String player2 = rs1.getString(3);
					String playerwin = rs1.getString(4);
					int tongso = Integer.valueOf(rs1.getString(5));

					tbhs.addRow(new String[] { String.valueOf(stt), player1, player2, playerwin,String.valueOf(tongso)});

				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			

		}
	}
	
	public void xuThua() {
		
   	 setEnableButton(false);
	 
   	 try {
   		 oos.writeObject("youwin,thangroi");
   	 }catch (Exception e) {
   		 e.printStackTrace();
   	 }
   	 
   	 int n = JOptionPane.showConfirmDialog(f, "YOU LOST !!! PLAY AGAIN !!!", "Thong bao", JOptionPane.YES_NO_OPTION);
   	 
   	 if (n == JOptionPane.YES_OPTION) {
   		 newGame();
   		 try {
   			 oos.writeObject("newgame,choilai");
   		 }catch (Exception e) {
   			 e.printStackTrace();
   		 }
   	 } else if (n == JOptionPane.NO_OPTION) {
   		
   	 }
   	 
   	 luuKetQua();
		
	}
	
	public void luuKetQua() {
		try {
			String luong1 = ois.readObject().toString();
			String[] dulieu1 = luong1.split(",");
			if (dulieu1[0].equals("sooco")) {
				soOCoClient = Integer.parseInt(dulieu1[1]);
			}
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
			
			
		System.out.println(" Tong So o co 1: "+tong);
	    tong = soOCo + soOCoClient;
	    ConnectSql.insert(player1, player2, player2, tong);
		soOCo = 0;
		soOCoClient = 0;
		tong = 0;
	}
	
	class webcamUDPServer extends Thread {
		@Override
		public void run() {
			try {
				//khởi động udp server với port 8000

		    	DatagramSocket socket = new DatagramSocket(1717);

		        System.out.println("server is running");

		        //tạo chuỗi byte

		        byte[] inServer = new byte[60000];

		        byte[] outServer = new byte[60000];

		        //tạo packet nhận dữ liệu

		        DatagramPacket rcvPkt = new DatagramPacket(inServer, inServer.length);
		        
		        // lay webcam gui cho client
//		        Webcam webcam = Webcam.getDefault();
//				webcam.setViewSize(new Dimension(320,240));
//				webcam.open();
//				bm = webcam.getImage();
//				ImageIcon im = new ImageIcon(bm);

		        while (true) {

		            // chờ nhận dữ liệu từ client

		            socket.receive(rcvPkt);

		            InetAddress IP = rcvPkt.getAddress();

		            int port = rcvPkt.getPort();

		            //lấy dữ liệu nhận và gửi dữ liệu lại cho client

		            String temp = new String(rcvPkt.getData());
		            String list[];
		            list = temp.split("\n");
		            temp = list[0];
		     
		            imic = Base64Image.decoder(temp);

		            jlbImg1.setIcon(imic);

		            //gửi dữ liệu lại cho client
		            
//		            bm = webcam.getImage();
//		            im = new ImageIcon(bm);
//		            
//		            String data = Base64Image.ConvertImageIconToBase64String(im);
//					data = data +"\n";
//
//		            outServer = data.getBytes();
//
//		            DatagramPacket sndPkt = new DatagramPacket(outServer, outServer.length, IP, port);
//	
//		            socket.send(sndPkt);
		        }
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static ImageIcon Resize(ImageIcon a,int Height) {
		ImageIcon Result;
		double scale= Double.valueOf(a.getIconHeight())/Height;
		int Width=(int) Math.round(a.getIconWidth()/scale);
		Result= new ImageIcon(a.getImage().getScaledInstance(Width, Height, Image.SCALE_DEFAULT));
		return Result;
	}
	
	
	
	

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class LoginView extends JFrame{
    private JButton btnLogin;
    private JButton btnInit;
    private JPasswordField passText;
    private JTextField userText;
    private boolean bLoginCheck;
    BufferedReader bReader;

    LoginView(){
        setTitle("login");
        setSize(280, 150);
        setResizable(false);
        setLocation(600, 280);
        setBackground( Color.LIGHT_GRAY );
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        placeLoginPanel(panel);

        add(panel);

        setVisible(true);
    }

    public void placeLoginPanel(JPanel panel){
        panel.setLayout(null);
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        JLabel passLabel = new JLabel("Passwd");
        passLabel.setBounds(10, 40, 80, 25);
        panel.add(passLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        passText = new JPasswordField(20);
        passText.setBounds(100, 40, 160, 25);
        panel.add(passText);
        passText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isLoginCheck();
            }
        });

        btnInit = new JButton("Reset");
        btnInit.setBounds(10, 75, 75, 30);
        btnInit.setBackground( Color.darkGray );
        btnInit.setForeground( Color.white );
        panel.add(btnInit);
        btnInit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.Play( Main.sPath+"/decision31.wav" );
                userText.setText("");
                passText.setText("");
            }
        });

        btnLogin = new JButton("Login");
        btnLogin.setBounds(180, 75, 75, 30);
        btnLogin.setBackground( Color.darkGray );
        btnLogin.setForeground( Color.white );
        panel.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.Play( Main.sPath+"/decision31.wav" );
                isLoginCheck();
            }
        });
    }
    public void isLoginCheck(){
        String user = "mir";
        String passwd = "";

        try {
            File text = new File( Main.path + "/Passwd/passwd.txt" );
            bReader = new BufferedReader( new FileReader( text ) );
            passwd = bReader.readLine();

        }
            catch(FileNotFoundException e){
                e.printStackTrace();
                System.out.println(passwd);
            } catch(IOException e){
                e.printStackTrace();
            }

            if (userText.getText().equals( user ) && new String( passText.getPassword() ).equals( passwd )) {
                JOptionPane.showMessageDialog( null, "Success" );
                bLoginCheck = true;

                // 로그인 성공이라면 매니져창 뛰우기
                if (isLogin()) {
                    Main.Play( Main.sPath+"/papa1.wav" );
                    Main main = new Main();
                    setVisible( false );
                }
            } else {
                Main.Play( Main.sPath+"/beep1.wav" );
                JOptionPane.showMessageDialog( null, "Failed" );
            }
        }
    public boolean isLogin() {
        return bLoginCheck;
    }
    public static void loginView (String args[]){

        LoginView loginView = new LoginView();

    }
}

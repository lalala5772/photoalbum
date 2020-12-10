import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class setting_Window extends JFrame {
    private JPasswordField ori_f;
    private JPasswordField new_f;
    private JButton setting_btn;
    private boolean flag;
    BufferedReader bReader;
    setting_Window() {
        setTitle( "비밀번호 설정" );
        setSize(170, 220);
        setResizable(false);
        setLocation(575, 250);
        setBackground( Color.LIGHT_GRAY );

        JPanel set_panel = new JPanel();
        ChangePasswd( set_panel );
        add(set_panel);

    }

    public void ChangePasswd(JPanel set_panel){
        set_panel.setLayout(null);

        JLabel ori_passwd = new JLabel("현재 비밀번호");
        ori_passwd.setBounds(10, 10, 80, 25);
        set_panel.add(ori_passwd);

        JLabel new_passwd = new JLabel("바꿀 비밀번호");
        new_passwd.setBounds(10, 78, 80, 25);
        set_panel.add(new_passwd);

        ori_f = new JPasswordField(20);
        ori_f.setBounds(10, 35, 110, 20);
        set_panel.add(ori_f);

        new_f = new JPasswordField(20);
        new_f.setBounds(10, 110, 110, 20);
        set_panel.add(new_f);

        setting_btn = new JButton("save");
        setting_btn.setLocation(92, 150);
        setting_btn.setSize(62, 28);
        setting_btn.setBackground( Color.darkGray );
        setting_btn.setForeground( Color.white );
        set_panel.add(setting_btn);
        setting_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isLoginCheck();
            }
        });

        setResizable( false );
        setVisible( true );
    }

    public void isLoginCheck(){
        try {
            File text = new File( Main.path+ "/Passwd/passwd.txt" );
            bReader = new BufferedReader( new FileReader( text ) );
            String ori = bReader.readLine();
            if (ori_f.getText().equals( ori )) {
                JOptionPane.showMessageDialog( null, "비밀번호가 변경되었습니다." );
                flag = true;

                if (check()) {
                    Main.Play( Main.sPath+"/decision31.wav" );
                    setVisible( false );
                    flag = false;
                    FileWriter fw; // FileWriter 선언

                    try {
                        fw = new FileWriter(Main.path +"/Passwd/passwd.txt", false); // 파일이 있을경우 덮어쓰기
                        fw.write( new_f.getText() );
                        fw.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                Main.Play( Main.sPath+"/beep1.wav" );
                JOptionPane.showMessageDialog( null, "비밀번호가 일치하지않습니다." );
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    public boolean check() {
        return flag;
    }

}
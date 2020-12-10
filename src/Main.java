import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Math.min;

class Main extends JFrame {
    static int cnt = 0;
    static ArrayList<String> list = new ArrayList<String>(5);


    int calYear;
    int calMonth;
    int calDayOfMon;
    Calendar today = Calendar.getInstance();

    JTextArea memoArea;
    JScrollPane memoAreaSP;

    static Path relativePath = Paths.get("");
    static String path = relativePath.toAbsolutePath().toString();
    String dirPath = path + "/photo";
    static String sPath = path + "/res/sound";

    private BufferedImage image;
    Main() {
        this.setLayout( null );
        this.setLocation( 0, 0 );
        this.setSize( 1920, 1080 );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setVisible( true );
        this.setTitle( "Photo Story...☆" );

        JPanel memoSubPanel;
        JButton saveBut;
        JButton delBut;
        JButton clearBut;

        JLabel selectedDate = new JLabel("<Html><font size=3>"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR)+"&nbsp;(Today)</html>", SwingConstants.LEFT);
        selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        JPanel photo_panel = new JPanel();
        photo_panel.setLayout( null );
        photo_panel.setBounds( 15, 15, 1200, 650 );
        photo_panel.setBackground( Color.pink );
        photo_panel.setVisible( true );

        JPanel image_panel = new ChangeImagePanel();
        image_panel.setLayout( null );
        image_panel.setBounds( 65, 15, 1100, 650 );
        image_panel.setVisible( true );

        JPanel menu_panel = new JPanel();
        menu_panel.setLayout( null );
        menu_panel.setBounds( 1230, 15, 291, 650 );
        menu_panel.setBackground( Color.WHITE );
        menu_panel.setVisible( true );

        JPanel bottom_panel = new JPanel();
        bottom_panel.setLayout( null );
        bottom_panel.setBounds( 15, 680, 1200, 80 );
        bottom_panel.setBackground( Color.WHITE );
        bottom_panel.setVisible( true );

        JPanel memo_panel = new JPanel();
        memo_panel.setLayout( null );
        memo_panel.setBounds( 1247, 330, 255, 330 );
        memo_panel.setBackground( Color.LIGHT_GRAY );
        memo_panel.setVisible( true );
        memoArea = new JTextArea();
        memoArea.setLineWrap(true);
        memoArea.setWrapStyleWord(true);
        memoAreaSP = new JScrollPane(memoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        selectedDate = new JLabel("<Html><font size=3>"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR)+"&nbsp;(Today)</html>", SwingConstants.LEFT);
        selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        readMemo();//저장된 txt데이터를 불러옴-> 사진 명과 같은 memo를 불러오게 해야함


        memoSubPanel=new JPanel();
        saveBut = new JButton("Save");
        saveBut.setBackground( Color.pink );
        saveBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                try {
                    Play( sPath+"/decision31.wav" );
                    File f= new File("MemoData");
                    if(!f.isDirectory()) f.mkdir();

                    String memo = memoArea.getText();
                    Path file = Paths.get(list.get(cnt));
                    if(memo.length()>0){
                        BufferedWriter out = new BufferedWriter(new FileWriter("MemoData/"+file.getFileName()+".txt"));
                        String str = memoArea.getText();
                        out.write(str);
                        out.close();
                    }

                } catch (IOException e) {
                }
            }
        });
        delBut = new JButton("Delete");
        delBut.setBackground( Color.pink );
        delBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Play( sPath+"/decision31.wav" );
                memoArea.setText("");
                Path file = Paths.get(list.get(cnt));
                File f =new File("MemoData/"+file.getFileName()+".txt");
                if(f.exists()){
                    f.delete();
                }

            }
        });
        clearBut = new JButton("Clear");
        clearBut.setBackground( Color.pink );
        clearBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                Play( sPath+"/decision31.wav" );
                memoArea.setText(null);
            }
        });
        memoSubPanel.add(saveBut);
        memoSubPanel.add(delBut);
        memoSubPanel.add(clearBut);
        memo_panel.setLayout(new BorderLayout());
        memo_panel.add(selectedDate, BorderLayout.NORTH);
        memo_panel.add(memoAreaSP,BorderLayout.CENTER);
        memo_panel.add(memoSubPanel,BorderLayout.SOUTH);

        ImageIcon left = new ImageIcon( "res/icon/back.png" );
        ImageIcon right = new ImageIcon( "res/icon/next.png" );
        ImageIcon delete = new ImageIcon( "res/icon/cancel.png" );
        ImageIcon add = new ImageIcon( "res/icon/add.png" );
        ImageIcon settings = new ImageIcon( "res/icon/settings.png" );
        ImageIcon information = new ImageIcon( "res/icon/information.png" );

        JButton left_button = new JButton( left );
        left_button.setLayout( null );
        left_button.setBounds( 200, 20, 200, 50 );
        left_button.setVisible( true );
        left_button.setBorderPainted( false );
        left_button.setContentAreaFilled( false );
        left_button.setFocusPainted( false );

        JButton D_button = new JButton( delete );
        D_button.setLayout( null );
        D_button.setBounds( 550, 20, 100, 50 );
        D_button.setVisible( true );
        D_button.setBorderPainted( false );
        D_button.setContentAreaFilled( false );
        D_button.setFocusPainted( false );

        JButton right_button = new JButton( right );
        right_button.setLayout( null );
        right_button.setBounds( 800, 20, 200, 50 );
        right_button.setVisible( true );
        right_button.setBorderPainted( false );
        right_button.setContentAreaFilled( false );
        right_button.setFocusPainted( false );

        JButton helping_button = new JButton(information);
        helping_button.setLayout( null );
        helping_button.setBounds( 70, 90, 150, 50 );
        helping_button.setVisible( true );
        helping_button.setBorderPainted( false );
        helping_button.setContentAreaFilled( false );
        helping_button.setFocusPainted( false );

        JButton setting_button = new JButton( settings );
        setting_button.setLayout( null );
        setting_button.setBounds( 66, 167, 158, 50 );
        setting_button.setVisible( true );
        setting_button.setBorderPainted( false );
        setting_button.setContentAreaFilled( false );
        setting_button.setFocusPainted( false );

        JButton add_button = new JButton( add );
        add_button.setLayout( null );
        add_button.setBounds( 70, 245, 150, 50 );
        add_button.setVisible( true );
        add_button.setBorderPainted( false );
        add_button.setContentAreaFilled( false );
        add_button.setFocusPainted( false );

        addFilesInDIr(dirPath);

        readMemo();
        JLabel pic_label;
        try {
            image = ImageIO.read(new File(list.get(cnt)));
        } catch (IOException ex) { }

        pic_label = new JLabel( new ImageIcon(image));
        pic_label.setLayout( null );
        pic_label.setBounds( 0, 0, 1100, 650 );
        pic_label.setVisible( true );

        left_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Play( sPath+"/decision31.wav" );
                image_panel.setVisible( false );
                cnt--;
                cnt += list.size();
                cnt %= list.size();
                try {
                    image = ImageIO.read(new File(list.get(cnt)));
                    readMemo();
                    image_panel.setVisible( true );
                } catch (IOException ex) { }
                image_panel.repaint();
            }
        });

        right_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Play( sPath+"/decision31.wav" );
                image_panel.setVisible( false );
                cnt ++;
                cnt %= list.size();
                try {
                    image = ImageIO.read(new File(list.get(cnt)));
                    readMemo();
                    image_panel.setVisible( true );
                } catch (IOException ex) { }
                image_panel.repaint();
            }
        });

        D_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Play( sPath+"/decision31.wav" );
                new D_Window();
            }
        });

        helping_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Play( sPath+"/decision31.wav" );
                new helping_Window();
            }
        });

        setting_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Play( sPath+"/decision31.wav" );
                new setting_Window();
            }
        });

        add_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Play( sPath+"/decision31.wav" );

                //파일 탐색기
                JFileChooser chooser = new JFileChooser( FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setCurrentDirectory(new File("/")); // 현재 사용 디렉토리를 지정
                chooser.setAcceptAllFileFilterUsed(true);   // Fileter 모든 파일 적용
                chooser.setDialogTitle("사진 추가하기"); // 창의 제목
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // 파일 선택 모드

                int returnVal = chooser.showOpenDialog(null); // 열기용 창 오픈

                if(returnVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭
                    String ori = chooser.getSelectedFile().toString();
                    Path file = Paths.get(ori);
                    list.clear();
                    FileInputStream fis = null;
                    FileOutputStream fos = null;

                    try {
                        fis = new FileInputStream( ori );                             // 원본파일
                        fos = new FileOutputStream(dirPath + "/" + file.getFileName());   // 복사위치

                        byte[] buffer = new byte[1024];
                        int readcount = 0;

                        while((readcount=fis.read(buffer)) != -1) {
                            fos.write(buffer, 0, readcount);    // 파일 복사
                        }
                        addFilesInDIr(dirPath);
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    } finally {

                        try {
                            fis.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        try {
                            fos.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }

                }
            }
        });

        JLabel menu_label = new JLabel();
            menu_label.setLayout( null );
            menu_label.setBounds( 75, 20, 231, 50 );
            menu_label.setText( "MENU" );
            menu_label.setVisible( true );
            menu_label.setFont( menu_label.getFont().deriveFont( 48.0f ) );

            bottom_panel.add( left_button );
            bottom_panel.add( right_button );
            bottom_panel.add( D_button );

            menu_panel.add( setting_button );
            menu_panel.add( add_button );
            menu_panel.add( helping_button );

            menu_panel.add( menu_label );

            image_panel.add(pic_label);

            this.add( image_panel );
            this.add( memo_panel );
            this.add( photo_panel );
            this.add( menu_panel );
            this.add( bottom_panel );


        }

    OutputStream output;
    {
        try {
            output = new FileOutputStream(path + "/Text/photo.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void addFilesInDIr(String dirPath) {
        File dir = new File(dirPath);
        File files[] = dir.listFiles();


        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                addFilesInDIr(file.getPath());
            } else {
                list.add( String.valueOf( file ));
            }
            String str = String.valueOf( file );
            byte[] by=str.getBytes();
            try {
                output.write(by);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ChangeImagePanel extends JPanel {
        public ChangeImagePanel() {
        }
        @Override
        public void paint(Graphics g) {
            double tmpp = min(getWidth()/1100, getHeight()/650);
            int x = (int) (getWidth() * tmpp);
            int y = (int) (getHeight() * tmpp);
            x = (getWidth() - image.getWidth(null)) / 2;
            y = (getHeight() - image.getHeight(null)) / 2;
            int a=1100, b=650;
            if(image.getWidth()<1100) a = image.getWidth();
            else x=0;
            if(image.getHeight()<650) b = image.getHeight();
            else y=0;
            g.drawImage(image, x, y, a, b,null);
        }


    }



    class D_Window extends JFrame {
        D_Window() {
            setTitle( "Delete" );
            setLayout( null );

            JPanel NewWindowContainer = new JPanel();
            setContentPane( NewWindowContainer );

            JLabel NewLabel = new JLabel( "사진을 삭제하시겠습니까?" );

            NewWindowContainer.add( NewLabel );

            JButton yes = new JButton("네");
            yes.setForeground( Color.white );
            yes.setBackground( Color.DARK_GRAY );
            yes.setVisible( true );

            JButton no = new JButton("아니오");
            no.setForeground( Color.white );
            no.setBackground( Color.DARK_GRAY );
            no.setVisible( true );

            yes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Play( sPath+"/decision31.wav" );
                    File d_file = new File( list.get( cnt ) );
                    if(d_file.delete()){
                        setVisible(false);
                        dispose();
                    }
                    Path file = Paths.get(list.get(cnt));
                    File f =new File("MemoData/"+file.getFileName()+".txt");
                    if(f.exists()){
                        f.delete();
                    }
                    list.clear();
                    addFilesInDIr(dirPath);
                }
            });

            no.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Play( sPath+"/decision31.wav" );
                    setVisible(false);
                    dispose();
                }
            });

            this.add(yes);
            this.add(no);

            setBounds( 555, 285, 340, 130 );
            setResizable( false );
            setVisible( true );
        }

    }

    class helping_Window extends JFrame {
        helping_Window() {
            setTitle( "HOW TO USE?" );

            JPanel NewWindowContainer = new JPanel();
            setContentPane( NewWindowContainer );

            String a = "1. 하단의 '&lt' 버튼을 누르면 이전의 사진으로 넘어갑니다.";
            String b = "2. 하단의 '>'버튼을 누르면 다음 사진으로 넘어갑니다.";
            String c = "3. '&lt;'버튼과 '>'버튼 사이의 'X'버튼을 누르면 사진을 삭제할 수 있습니다 .";
            String d = "4. 설정 버튼을 통해 비밀번호를 바꿀 수 있습니다.(20자 이내)";
            String e = "5. '+'버튼을 통해 내 컴퓨터에 있는 사진을 앨범에 추가할 수 있습니다.";
            String f = "6. 메모장을 통해 사진마다 글을 적어놓을 수 있습니다.";
            String g = "7. save 버튼-내용 저장, delete 버튼-내용 삭제, clear 버튼-내용 비우기.";
            String h = "8. 메모장 윗 부분을 통해 오늘의 날짜를 알 수 있습니다.";

            JLabel NewLabel = new JLabel( "<html>" +a+ "<br>"+ "<br>" +b+"<br>"+ "<br>" +c+"<br>"+ "<br>" +d+"<br>"+ "<br>" +e+"<br>"+ "<br>" +f+"<br>"+ "<br>"+g+"<br>"+ "<br>"+h+ "</html>" );

            NewWindowContainer.add( NewLabel );

            setBounds( 455, 235, 500, 300 );
            setResizable( false );
            setVisible( true );
        }

    }

    private void readMemo(){
        File dir = new File(path+ "/photo");
        String[] filenames = dir.list();
        try{

            File f = new File("MemoData/"+filenames[cnt]+".txt");
            if(f.exists()){
                BufferedReader in = new BufferedReader( new FileReader("MemoData/"+filenames[cnt]+".txt"));
                String memoAreaText= new String();
                while(true){
                    String tempStr = in.readLine();
                    if(tempStr == null) break;
                    memoAreaText = memoAreaText + tempStr + System.getProperty("line.separator");
                }
                memoArea.setText(memoAreaText);
                in.close();
            }
            else{
                memoArea.setText("");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Play(String fileName) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File( fileName ));
            AudioFormat format = audioIn.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open( audioIn );
            clip.start();
        } catch (Exception ex) {
        }
    }

    public static void main (String args[]){

            LoginView loginView = new LoginView();
    }


}
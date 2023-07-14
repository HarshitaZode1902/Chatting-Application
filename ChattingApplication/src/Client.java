import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client  implements ActionListener {
    JTextField t1;
    static JPanel a1;
    static Box Vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    Client()
    {
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(23, 110, 69));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        // ARROW IMG AND BTN
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                f.setVisible(false);
                //OR System.exit(0);
            }
        });

        //PROFILE IMG
        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image ii2 = ii1.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel profile = new JLabel(ii3);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        //VIDEO IMG
        ImageIcon ii4 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image ii5 = ii4.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon ii6 = new ImageIcon(ii5);
        JLabel video = new JLabel(ii6);
        video.setBounds(300,20,30,30);
        p1.add(video);

        //AUDIO IMG
        ImageIcon ii7 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image ii8 = ii7.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon ii9 = new ImageIcon(ii8);
        JLabel audio = new JLabel(ii9);
        audio.setBounds(360,20,35,30);
        p1.add(audio);

        //THREE DOTS IMG
        ImageIcon ii10 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image ii11 = ii10.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon ii12 = new ImageIcon(ii11);
        JLabel threeDots = new JLabel(ii12);
        threeDots.setBounds(420,20,10,25);
        p1.add(threeDots);

        //NAME
        JLabel name = new JLabel("Tanmay");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.white);
        name.setFont(new Font("TIMES NEW ROMAN",Font.BOLD,18));
        p1.add(name);

        //STATUS
        JLabel status = new JLabel("Active Now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.white);
        status.setFont(new Font("TIMES NEW ROMAN",Font.BOLD,14));
        p1.add(status);

        //MSG DISPLAY
        a1 = new JPanel();
        a1.setBounds(5,75,440,590);
        f.add(a1);

        //TEXT MSG
        t1 = new JTextField();
        t1.setBounds(5,675,310,40);
        t1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(t1);

        //SEND BUTTON
        JButton send = new JButton("SEND");
        send.setBounds(320,675,123,40);
        send.setBackground(new Color(23, 110, 69));
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(send);


        //FRAME
        f.setSize(450,750);
        f.getContentPane().setBackground(Color.WHITE);
        f.setLocation(800,50);
        f.setUndecorated(true);
        f.setVisible(true);
    }
    public static void main(String[] args) {
        Client c = new Client();
        try{
            Socket s = new Socket("127.0.0.1",3000);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true)
            {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);
                Vertical.add(left);
                Vertical.add((Box.createVerticalStrut(10)));
                a1.add(Vertical,BorderLayout.PAGE_START);
                f.validate();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //SEND BTN FUNCTIONALITY
    @Override
    //From ActionListener Interface
    public void actionPerformed(ActionEvent e) {
        try {
            String output = t1.getText();
            JLabel op = new JLabel(output);
            JPanel p2 = formatLabel(output);
            a1.setLayout(new BorderLayout());

            //RIGHT SIDE ALIGNMENT OF MSG
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);

            //If MULTIPLE MSG THEN IT WILL ALIGN IT VERTICALLY
            Vertical.add(right);

            Vertical.add(Box.createVerticalStrut(10));

            a1.add(Vertical, BorderLayout.PAGE_START);

            dout.writeUTF(output);

            t1.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception ae)
        {
            ae.printStackTrace();
        }
    }

    public  static JPanel formatLabel(String output)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));
        JLabel op = new JLabel("<html><p style=\"width : 150px\">"+output+"</p></html>");
        op.setFont(new Font("TAHOMA",Font.PLAIN,16));
        op.setBackground(new Color(37,211,102));
        op.setOpaque(true);
        op.setBorder(new EmptyBorder(15,15,15,15));
        panel.add(op);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(date.format(cal.getTime()));
        panel.add(time);
        return  panel;
    }
}



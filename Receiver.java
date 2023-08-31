package Chatting_application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

public class Receiver  implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame jf = new JFrame();
    static DataOutputStream dout;
    Receiver() //constructor
    {
        jf.setLayout(null);
        JPanel pl = new JPanel();
        pl.setBackground(new Color(7,94,84));
        pl.setBounds(0 ,0,450,70);
        pl.setLayout(null);
        jf.add(pl);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(40,35,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(20,20,25,25);
        pl.add(back);

        back.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent ae)
            {
                System.exit(0);
            }
        });

        //profile picture icon
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/sid.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(60,10,50,50);
        pl.add(profile);

        //vedio call icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel vedio = new JLabel(i9);
        vedio.setBounds(290,10,40,50);
        pl.add(vedio);

        //Phone icons
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(350,10,40,50);
        pl.add(phone);

        //options Pane
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel option = new JLabel(i15);
        option.setBounds(400,10,20,50);
        pl.add(option);

        //User name label
        JLabel name = new JLabel("Sidharth Nigam");
        name.setBounds(120,10,200,30);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,22));
        pl.add(name);

        //user current status
        JLabel status = new JLabel("Active Now");
        status.setBounds(122,30,150,30);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,12));
        pl.add(status);

        a1= new JPanel();
        a1.setBounds(0,70,450,600);
        jf.add(a1);

        //whatappp main background
//        ImageIcon BG = new ImageIcon(ClassLoader.getSystemResource("icons/backgr.png"));
//        Image BG1 = BG.getImage().getScaledInstance(450,550,Image.SCALE_DEFAULT);
//        ImageIcon BG2 = new ImageIcon(BG1);
//        JLabel background = new JLabel(BG2);
//        background.setBounds(0,68,450,550);

//        a1.add(background,Integer.valueOf(0));

        a1.add(vertical,Integer.valueOf(1));

        //messege text field
        text = new JTextField();

        text.setBounds(3,625,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        jf.add(text);

        //send messege button
        JButton send = new JButton("SEND");
        send.setBounds(315,625,140,40);
        //send.setBackground(Color.cyan);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.white);
        send.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        send.addActionListener(this);
        jf.add(send);


        jf.setSize(450,700);
        jf.setLocation(800,50);
        jf.setUndecorated(true);
        jf.getContentPane().setBackground(Color.white);
        jf.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae)
    {
        try {
            String messege = text.getText();
            JLabel output = new JLabel(messege);

            JPanel p2 = formatLabel(messege);
//        p2.add(output);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(10));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(messege);

            jf.repaint();
            jf.invalidate();
            jf.validate();

            text.setText("");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public static JPanel formatLabel(String messege)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style= \"width: 150px\">" +messege+"</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,24));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }

    public static void main(String[] args) {
        new Receiver();   //class object
        try
        {
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true)
            {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel=formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);
                jf.validate();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

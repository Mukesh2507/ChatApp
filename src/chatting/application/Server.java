package chatting.application;

import javafx.scene.layout.Background;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;


// we are extending JFrame here because  setSize function is available in JFrame and implement for clicked event
public class Server implements ActionListener {


    static JFrame f1 = new JFrame();
    JPanel p1; //declaring pannel
    JTextField t1; //message writing field
    JButton b1;
    //static  JTextArea a1;
    static JPanel a1;  //we replacing JText are to JPanel so we can add speech bubble feature
    static Box vertical = Box.createVerticalBox();
    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    boolean typing;


    Server() {
//second task setting green color for pannel
        f1.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        f1.add(p1);


        //first task
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icon/3.png"));
        //for providing img and  you cant directly  send img to Jframe so we ned to create on label
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        //creating new object to set image size and shape
        //now here i2 cant pass directly to jframe so we are going to create new object refference
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);

        l1.setBounds(5, 17, 30, 30);//postion set
        p1.add(l1); //to add icon top on pannel we call with p1
        //mouse event
        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

        });


        //second img
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icon/1.png"));
        //for providing img and  you cant directly  send img to Jframe so we ned to create on label
        Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        //creating new object to set image size and shape
        //now here i2 cant pass directly to jframe so we are going to create new object refference
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(40, 5, 60, 60);
        p1.add(l2); //to add icon top on pannel we call with p1


        //third img
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icon/video.png"));
        //for providing img and  you cant directly  send img to Jframe so we ned to create on label
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        //creating new object to set image size and shape
        //now here i2 cant pass directly to jframe so we are going to create new object refference
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(330, 18, 35, 30);
        p1.add(l5); //to add icon top on pannel we call with p1

        //fourth img
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icon/phone.png"));
        //for providing img and  you cant directly  send img to Jframe so we ned to create on label
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        //creating new object to set image size and shape
        //now here i2 cant pass directly to jframe so we are going to create new object refference
        ImageIcon i12 = new ImageIcon(i11);
        JLabel l6 = new JLabel(i12);
        l6.setBounds(370, 18, 35, 30);
        p1.add(l6); //to add icon top on pannel we call with p1


//fifth img
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/Icon/3icon.png"));
        //for providing img and  you cant directly  send img to Jframe so we ned to create on label
        Image i14 = i13.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
        //creating new object to set image size and shape
        //now here i2 cant pass directly to jframe so we are going to create new object refference
        ImageIcon i15 = new ImageIcon(i14);
        JLabel l7 = new JLabel(i15);
        l7.setBounds(410, 20, 13, 25);
        p1.add(l7); //to add icon top on pannel we call with p1

        JLabel l3 = new JLabel("Gaitonde"); //for adding name
        l3.setFont(new Font("SAN_SERIF", Font.BOLD, 15));//for font
        l3.setForeground(Color.white);//for color change
        l3.setBounds(110, 15, 100, 18);
        p1.add(l3);


        JLabel l4 = new JLabel("Active Now"); //for adding name
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));//for font
        l4.setForeground(Color.white);//for color change
        l4.setBounds(110, 35, 100, 20);
        p1.add(l4);


        //timer class for typing

        Timer t = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!typing) {
                    l4.setText("Active now");
                }

            }
        });
        t.setInitialDelay(2000);
        //TextArea
        //Jpanel


        a1 = new JPanel();                                                     //a1=new JTextArea();
        // a1.setBounds(5,76,440,570);                         //a1.setBounds(5,75,440,570);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        //f1.add(a1);                                                                        //a1.setEditable(false);
        //a1.setBackground(Color.white);
        //a1.setLineWrap(true);//to set sending msg right side without cutting and getting into new line
        // a1.setWrapStyleWord(true);//same used for above
        JScrollPane sp = new JScrollPane(a1);                                  //adding main jpannel for scroll bar and commenting setbounds and add
        sp.setBounds(5, 75, 440, 570);
        sp.setBorder(BorderFactory.createEmptyBorder());
//abstract class      //child class
        ScrollBarUI ui = new BasicScrollBarUI() {


            protected JButton createDecreaseButtton(int orientation) {


                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(7, 94, 84));
                button.setForeground(Color.white);
                this.thumbColor = new Color(7, 94, 84);
                return button;
            }

            protected JButton createInecreaseButtton(int orientation) {


                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(7, 94, 84));
                button.setForeground(Color.white);
                this.thumbColor = new Color(7, 94, 84);
                return button;
            }

        };
        sp.getVerticalScrollBar().setUI(ui);
        f1.add(sp);


        //TextFiled


        t1 = new JTextField();
        t1.setBounds(5, 655, 310, 40);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(t1);
//for typing status
        t1.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent ke) {
                l4.setText("typing...");


                t.stop();


                typing = true;
            }

            public void keyReleased(KeyEvent ke) {

                typing = false;
                if (!t.isRunning()) {
                    t.start();
                }
            }
        });

        b1 = new JButton("Send");
        b1.setBounds(320, 655, 123, 40);
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.white);
        b1.setFont(new Font("SEN_SERIF", Font.PLAIN, 16));
        b1.addActionListener(this);//adding event for send buttton
        f1.add(b1);


        f1.setLayout(null); //to place icon into frame we have many layouts and bydefault we have border layout so
        //not to take bydefault layout we setlayout to null;

        f1.setSize(450, 700);//set variables are by default false so we need to make it true
        f1.setLocation(400, 200);
        f1.setUndecorated(true);//this will going to remove above all maximize and minimize and java symbols
        f1.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        try {
            String out = t1.getText();
            sendTextToFile(out);

            JPanel p2 = formatLabel(out);


            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            t1.setText("");//this will going to empty msg box after passing one message
            dout.writeUTF(out);


            //   a1.setText(a1.getText() + "\n\t\t\t" + out);//for message send here tab for message move to right


        } catch (Exception e1) {

            System.out.println(e1);
        }
    }

    //saving msg


    public void sendTextToFile(String message) throws FileNotFoundException {

        try (FileWriter f = new FileWriter("chat.txt");

             PrintWriter p = new PrintWriter(new BufferedWriter(f));) {

            p.println("Gaitonde: " + message);//appending new msg into new line

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    //bubble speech
    public static JPanel formatLabel(String out) {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style=\"width :150px\">" + out + "</p> </html>");

        l1.setBackground(new Color(37, 212, 102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(5, 15, 5, 50));
        l1.setFont(new Font("Tahoma", Font.PLAIN, 14));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sd.format(cal.getTime()));


        p3.add(l1);
        p3.add(l2);
        return p3;


    }

    public static void main(String[] args) {
        //we are going to code for JFrame into Constructer because as we run main then first class constructer call happen
        new Server().f1.setVisible(true);

        String msginput = " ";//

        try {
            skt = new ServerSocket(6001);

            s = skt.accept();
            din = new DataInputStream(s.getInputStream());//data trackin for input
            dout = new DataOutputStream(s.getOutputStream());//data track for output

            msginput = din.readUTF();//data out
            // a1.setText(a1.getText()+"\n" +msginput);//data set  //code before bubble speech

            JPanel p2 = formatLabel(msginput);
            JPanel left = new JPanel(new BorderLayout());
            left.add(p2, BorderLayout.LINE_START);
            vertical.add(left);
            f1.validate();

        } catch (Exception e) {
            System.out.println(e);
        }


    }

}



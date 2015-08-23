package jack;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Acep / D.Tsogtbayar
 */
public class JillBack {
    
    public final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public final int WIDTH = DIMENSION.width;
    public final int HEIGHT = DIMENSION.height;
    
    private Robot robot;
    
    private int out_width = 450;
    private int out_height = 600;
    
    private JFrame frame;
    private JPanel panel, panel2;
    private JList<String> list;
    
    private JButton[] buttons = new JButton[13];
    private String[] butTitles = {
        "Овог", 
        "Нэр", 
        "Тушаал", 
        "Хот", 
        "Дүүрэг", 
        "П Код",
        "Хот, Дүүрэг, П Код",
        "Талбайн Хэмжээ", 
        "Талбайн Нэр", 
        "<TAB>", 
        "Солбиц",
        "<RELOAD>", 
        "<MN>"};
    
    public JillBack() {
        try {
        robot = new Robot();
        
        frame = new JFrame("Tool");
        panel = new JPanel(new GridLayout(12, 3));
        panel.setBackground(Color.white);
        Color backg = new Color(255,112,112);
        Color fore = new Color(255, 255, 255);
        for (int a = 0; a<buttons.length; a++) {
            buttons[a] = new JButton(butTitles[a]);
            buttons[a].setHorizontalAlignment(JButton.CENTER);
            buttons[a].setVerticalAlignment(JButton.CENTER);
            buttons[a].setFont(new Font("Arial", Font.BOLD, 18));
            buttons[a].setBorder(BorderFactory.createLineBorder(Color.white));
            if (a==3) {
                backg = new Color(34, 158, 21);
                panel.add(new JLabel(""));
                panel.add(new JLabel(""));
                panel.add(new JLabel(""));
            }
            if (a==7) {
                panel.add(new JLabel(""));
                panel.add(new JLabel(""));
            }
            if (a==9) {
                backg = new Color(0, 0, 0);
                panel.add(new JLabel(""));
                panel.add(new JLabel(""));
            }
            if (a==10) {
            }
            if (a==11) {
                backg = new Color(34, 158, 21);
                panel.add(new JLabel(""));
                panel.add(new JLabel(""));
            }
            if (a==12) {
                backg = new Color(0, 0, 0);
            }
            buttons[a].setBackground(backg);
            buttons[a].setForeground(fore);
            panel.add(buttons[a]);
        }
        
        buttons[0].addActionListener((ActionEvent e) -> {
            // Овог
            robot.delay(15);
            switchingTab("Бямбаа");
        });
        buttons[1].addActionListener((ActionEvent e) -> {
            // Нэр
            robot.delay(5);
            switchingTab("Мөнхбаатар");
            robot.delay(5);
        });
        buttons[2].addActionListener((ActionEvent e) -> {
            // Тушаал
            robot.delay(5);
            switchingTab("Захирал");
            robot.delay(5);
        });
        buttons[3].addActionListener((ActionEvent e) -> {
            // Хот
            robot.delay(5);
            switchingTab("Өмнөговь");
            robot.delay(5);
        });
        buttons[4].addActionListener((ActionEvent e) -> {
            // Дүүрэг
            robot.delay(5);
            switchingTab("Баян-Овоо");
            robot.delay(5);
        });
        buttons[5].addActionListener((ActionEvent e) -> {
            // Планшетийн код
            robot.delay(5);
            switchingTab("К-48-46");
            robot.delay(5);
        });
        buttons[7].addActionListener((ActionEvent e) -> {
            // Талбайн хэмжээ
            robot.delay(5);
            switchingTab("5270");
            robot.delay(5);
        });
        buttons[8].addActionListener((ActionEvent e) -> {
            // Талбайн нэр
            robot.delay(5);
            switchingTab("Улаан уул");
            robot.delay(5);
        });
        buttons[10].addActionListener((ActionEvent e) -> {
            // Солбиц
            robot.delay(5);
            doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
            robot.delay(20);
            String str = "";
            str = "106";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "30";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "5";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            
            str = "42";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "55";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "18";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            
            str = "106";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "30";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "5";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            
            str = "42";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "59";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "42.5";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            
            str = "106";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "34";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "50";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            
            str = "42";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "59";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "42.5";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            
            str = "106";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "34";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "50";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            
            str = "42";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "55";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            str = "18";
            Solbiz(str);
            doType(KeyEvent.VK_TAB);
            
            robot.delay(10);
//            doType(KeyEvent.VK_TAB);
//            doType(KeyEvent.VK_TAB);
//            doType(KeyEvent.VK_TAB);
//            doType(KeyEvent.VK_TAB);
//            doType(KeyEvent.VK_TAB);
//            doType(KeyEvent.VK_TAB);
            doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
            robot.delay(15);
        });
        buttons[9].addActionListener((ActionEvent e) -> {
            // Солбиц
            doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
            robot.delay(20);
            doType(KeyEvent.VK_TAB);
            robot.delay(5);
            doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
            robot.delay(20);
        });
        buttons[11].addActionListener((ActionEvent e) -> {
            try {
                Runtime.getRuntime().exec("java -jar MonkhooAkh.jar");
            } catch (IOException ex) {
            }
            System.exit(0);
        });
        buttons[6].addActionListener((ActionEvent e) -> {
            robot.delay(5);
            doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
            robot.delay(20);
            String str = "Өмнөговь";
            String str2 = "Баян-Овоо";
            String str3 = "К-48-46";
            char[] chs = str.toCharArray();
            for (char c : chs) {
                type(c);
            }
            robot.delay(20);
            doType(KeyEvent.VK_TAB);
            
            chs = str2.toCharArray();
            for (char c : chs) {
                type(c);
            }
            robot.delay(20);
            doType(KeyEvent.VK_TAB);
            
            chs = str3.toCharArray();
            for (char c : chs) {
                type(c);
            }
            robot.delay(20);
            doType(KeyEvent.VK_TAB);
        });
        buttons[12].addActionListener((ActionEvent e) -> {
            robot.delay(5);
            doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
            robot.delay(20);
            doType(KeyEvent.VK_ALT, KeyEvent.VK_SHIFT);
            robot.delay(20);
            doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
            robot.delay(20);
        });
        
        
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(out_width, out_height);
        frame.setLocation(WIDTH-out_width, 50);
        } catch (Exception ex) {
        }
    }
    public void setVisible(Boolean visible) {
        frame.setVisible(visible);
    }
    private void Solbiz(String str) {
        char[] chs = str.toCharArray();
        for (char c : chs) {
            type(c);
        }
        robot.delay(20);
    }
    private boolean executeTabSwitch() {
        try {
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_ALT);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    private void switchingTab(String text) {
//        doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
        if (executeTabSwitch()) {
            robot.delay(15);
            char[] ch = text.toCharArray();
            robot.setAutoDelay(0);
            robot.delay(20);
            for (char c : ch) {
                type(c);
            }
            doType(KeyEvent.VK_TAB);
            robot.delay(15);
            doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
            robot.delay(20);
        } else {
            switchingTab(text);
        }
    }
    private void type(char character) {
        try {
        switch (character) {
            // Lowercase Mongolian Cyrillic
        case 'а': doType(java.awt.event.KeyEvent.VK_G); break;
        case 'б': doType(java.awt.event.KeyEvent.VK_D); break;
        case 'в': doType(java.awt.event.KeyEvent.VK_PERIOD); break;
        case 'г': doType(java.awt.event.KeyEvent.VK_U); break;
        case 'д': doType(java.awt.event.KeyEvent.VK_SEMICOLON); break;
        case 'е': doType(java.awt.event.KeyEvent.VK_MINUS); break;
        case 'ё': doType(java.awt.event.KeyEvent.VK_C); break;
        case 'ж': doType(java.awt.event.KeyEvent.VK_R); break;
        case 'з': doType(java.awt.event.KeyEvent.VK_P); break;
        case 'и': doType(java.awt.event.KeyEvent.VK_N); break;
        case 'й': doType(java.awt.event.KeyEvent.VK_A); break;
        case 'к': doType(java.awt.event.KeyEvent.VK_OPEN_BRACKET); break;
        case 'л': doType(java.awt.event.KeyEvent.VK_L); break;
        case 'м': doType(java.awt.event.KeyEvent.VK_B); break;
        case 'н': doType(java.awt.event.KeyEvent.VK_Y); break;
        case 'о': doType(java.awt.event.KeyEvent.VK_K); break;
        case 'ө': doType(java.awt.event.KeyEvent.VK_F); break;
        case 'п': doType(java.awt.event.KeyEvent.VK_QUOTE); break;
        case 'р': doType(java.awt.event.KeyEvent.VK_J); break;
        case 'с': doType(java.awt.event.KeyEvent.VK_V); break;
        case 'т': doType(java.awt.event.KeyEvent.VK_M); break;
        case 'у': doType(java.awt.event.KeyEvent.VK_E); break;
        case 'ү': doType(java.awt.event.KeyEvent.VK_O); break;
        case 'ф': doType(java.awt.event.KeyEvent.VK_Q); break;
        case 'х': doType(java.awt.event.KeyEvent.VK_H); break;
        case 'ц': doType(java.awt.event.KeyEvent.VK_W); break;
        case 'ч': doType(java.awt.event.KeyEvent.VK_X); break;
        case 'ш': doType(java.awt.event.KeyEvent.VK_I); break;
        case 'щ': doType(java.awt.event.KeyEvent.VK_EQUALS); break;
        case 'ъ': doType(java.awt.event.KeyEvent.VK_CLOSE_BRACKET); break;
        case 'ы': doType(java.awt.event.KeyEvent.VK_S); break;
        case 'ь': doType(java.awt.event.KeyEvent.VK_COMMA); break;
        case 'э': doType(java.awt.event.KeyEvent.VK_T); break;
        case 'ю': doType(java.awt.event.KeyEvent.VK_SLASH); break;
        case 'я': doType(java.awt.event.KeyEvent.VK_Z); break;
            // Uppercase Mongolia Cyrillic
        case 'А': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_G); break;
        case 'Б': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_D); break;
        case 'В': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_PERIOD); break;
        case 'Г': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_U); break;
        case 'Д': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_SEMICOLON); break;
        case 'Е': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_MINUS); break;
        case 'Ё': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_C); break;
        case 'Ж': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_R); break;
        case 'З': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_P); break;
        case 'И': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_N); break;
        case 'Й': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_A); break;
        case 'К': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_OPEN_BRACKET); break;
        case 'Л': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_L); break;
        case 'М': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_B); break;
        case 'Н': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_Y); break;
        case 'О': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_K); break;
        case 'Ө': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_F); break;
        case 'П': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_QUOTE); break;
        case 'Р': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_J); break;
        case 'С': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_V); break;
        case 'Т': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_M); break;
        case 'У': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_E); break;
        case 'Ү': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_O); break;
        case 'Ф': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_Q); break;
        case 'Х': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_H); break;
        case 'Ц': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_W); break;
        case 'Ч': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_X); break;
        case 'Ш': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_I); break;
        case 'Щ': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_EQUALS); break;
        case 'Ъ': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_CLOSE_BRACKET); break;
        case 'Ы': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_S); break;
        case 'Ь': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_COMMA); break;
        case 'Э': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_T); break;
        case 'Ю': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_SLASH); break;
        case 'Я': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_Z); break;
            // Numbers
        case '0': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_0); break;
        case '1': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_1); break;
        case '2': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_2); break;
        case '3': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_3); break;
        case '4': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_4); break;
        case '5': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_5); break;
        case '6': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_6); break;
        case '7': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_7); break;
        case '8': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_8); break;
        case '9': doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_9); break;
        case '-':
            doType(KeyEvent.VK_ALT, KeyEvent.VK_SHIFT);
            robot.delay(20);
            doType(KeyEvent.VK_MINUS);
            robot.delay(10);
            doType(KeyEvent.VK_ALT, KeyEvent.VK_SHIFT);
            robot.delay(20);
            break;
        case '\t': doType(java.awt.event.KeyEvent.VK_TAB); break;
        case '\n': doType(java.awt.event.KeyEvent.VK_ENTER); break;
        case '"': doType(java.awt.event.KeyEvent.VK_3); break;
        case '.': doType(java.awt.event.KeyEvent.VK_6); break;
        case ' ': doType(java.awt.event.KeyEvent.VK_SPACE); break;
        }
        } catch (Exception e) {
            
        }
    }
    private void doType(int... keyCodes) {
        doType(keyCodes, 0, keyCodes.length);
    }

    private void doType(int[] keyCodes, int offset, int length) {
        if (length == 0) {
            return;
        }
        robot.keyPress(keyCodes[offset]);
        doType(keyCodes, offset + 1, length - 1);
        robot.keyRelease(keyCodes[offset]);
    }

    
    public static void main(String[] args) {
        new JillBack().setVisible(true);
    }
}

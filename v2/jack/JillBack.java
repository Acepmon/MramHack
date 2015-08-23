package jack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.im.InputContext;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.ArrayList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 *
 * @author Acep / D.Tsogtbayar
 */
public class JillBack {

    private class CustomButton {

        private String injection;
        private String value;
        private boolean pressed;
        private JButton button;
        private ActionListener action;

        public CustomButton(String value, boolean pressed, ActionListener action) {
            this.injection = "";
            this.value = value;
            this.pressed = pressed;
            this.action = action;
            this.button = new JButton();
            this.button.addActionListener(this.action);
        }

        public CustomButton(String value, ActionListener action) {
            this.injection = "";
            this.value = value;
            this.action = action;
            this.pressed = false;
            this.button = new JButton();
            this.button.addActionListener(this.action);
        }

        public CustomButton(String value) {
            this.injection = "";
            this.value = value;
            this.pressed = false;
            this.button = new JButton();
            this.button.addActionListener(this.action);
        }

        public CustomButton() {
            this.injection = "";
            this.value = "";
            this.pressed = false;
            this.button = new JButton();
            this.button.addActionListener(this.action);
        }

        public String getInjection() {
            return injection;
        }

        public void setInjection(String injection) {
            this.injection = injection;
        }

        public JButton getButton() {
            return button;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
            this.button.setText(value);
        }

        public boolean isPressed() {
            return pressed;
        }

        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }

        public ActionListener getAction() {
            return action;
        }

        public void setAction(ActionListener action) {
            this.action = action;
            this.button.addActionListener(action);
        }

    }

    public final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public final int WIDTH = DIMENSION.width;
    public final int HEIGHT = DIMENSION.height;

    private Robot robot;

    private int out_width = 600;
    private int out_height = 700;

    private JFrame frame;
    private JPanel panel, panel2;
    private JList<String> list;

    private JButton[] buttons = new JButton[13];
    private ArrayList<CustomButton> btns = new ArrayList<>();
    private String[] butTitles = {"Овог", "Нэр", "Тушаал", "Хот", "Дүүрэг", "П Код", "Талбайн Хэмжээ", "Талбайн Нэр", "Солбиц", "<TAB>", "<RELOAD>", "Хот, Дүүрэг, П Код", "<MN>"};

    private File res = new File("resource.xml");

    public JillBack() {
        HashMap<String, Object> map = readResource(res);
        
        try {
            robot = new Robot();

            frame = new JFrame("Tool");
            GridLayout gridLayout = new GridLayout(12, 3);
            gridLayout.setHgap(5);
            gridLayout.setVgap(0);
            panel = new JPanel(gridLayout);
            panel.setBackground(Color.white);
            Color backg = new Color(255, 112, 112);
            Color fore = new Color(255, 255, 255);
            for (int a = 0; a < butTitles.length; a++) {
                CustomButton btn = new CustomButton();
                btn.setValue(butTitles[a]);

                btn.setAction(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchingTab(btn.getInjection());
                    }
                });

                buttons[a] = new JButton(butTitles[a]);
                buttons[a].setHorizontalAlignment(JButton.CENTER);
                buttons[a].setVerticalAlignment(JButton.CENTER);
                buttons[a].setFont(new Font("Arial", Font.BOLD, 22));
                buttons[a].setBorder(BorderFactory.createLineBorder(Color.white));
                if (a == 3) {
                    backg = new Color(34, 158, 21);
                    panel.add(new JLabel(""));
                    panel.add(new JLabel(""));
                    panel.add(new JLabel(""));
                }
                if (a == 9) {
                    backg = new Color(0, 0, 0);
                    panel.add(new JLabel(""));
                    panel.add(new JLabel(""));
                }
                if (a == 11) {
                    backg = new Color(34, 158, 21);
                    panel.add(new JLabel(""));
                    panel.add(new JLabel(""));
                }
                if (a == 12) {
                    backg = new Color(0, 0, 0);
                }
                buttons[a].setBackground(backg);
                buttons[a].setForeground(fore);
                panel.add(buttons[a]);
            }

            buttons[0].addActionListener((ActionEvent e) -> {
                // Овог
                changeToMn();
                switchingTab("И");
            });
            buttons[1].addActionListener((ActionEvent e) -> {
                // Нэр
                changeToMn();
                switchingTab("Бон Сун");
            });
            buttons[2].addActionListener((ActionEvent e) -> {
                // Тушаал
                changeToMn();
                switchingTab("Захирал");
            });
            buttons[3].addActionListener((ActionEvent e) -> {
                // Хот
                changeToMn();
                switchingTab("Өмнөговь");
            });
            buttons[4].addActionListener((ActionEvent e) -> {
                // Дүүрэг
                changeToMn();
                switchingTab("Хүрмэн");
            });
            buttons[5].addActionListener((ActionEvent e) -> {
                // Планшетийн код
                changeToMn();
                switchingTab("К-48-40");
            });
            buttons[6].addActionListener((ActionEvent e) -> {
                // Талбайн хэмжээ
                changeToMn();
                switchingTab("611.5");
            });
            buttons[7].addActionListener((ActionEvent e) -> {
                // Талбайн нэр
                changeToMn();
                switchingTab("Хүрмэн");
            });
            buttons[8].addActionListener((ActionEvent e) -> {
                // Солбиц
                executeTabSwitch();
                changeToEn();
                robot.delay(20);
                String str;
                str = "103";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "51";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "50.39";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);

                str = "42";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "54";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "31.25";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);

                str = "103";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "56";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "1.11";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);

                str = "42";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "55";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "40.81";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);

                str = "103";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "56";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "1.11";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);

                str = "42";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "54";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);
                str = "31.25";
                Solbiz(str);
                doType(KeyEvent.VK_TAB);

                robot.delay(10);
                doType(KeyEvent.VK_TAB);
                doType(KeyEvent.VK_TAB);
                doType(KeyEvent.VK_TAB);
                doType(KeyEvent.VK_TAB);
                doType(KeyEvent.VK_TAB);
                doType(KeyEvent.VK_TAB);
                doType(KeyEvent.VK_ALT, KeyEvent.VK_TAB);
                robot.delay(15);
            });
            buttons[9].addActionListener((ActionEvent e) -> {
                // Солбиц
                executeTabSwitch();
                robot.delay(50);
                doType(KeyEvent.VK_TAB);
                robot.delay(50);
                executeTabSwitch();
            });
            buttons[10].addActionListener((ActionEvent e) -> {
                try {
                    Runtime.getRuntime().exec("java -jar Mram.jar");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            });
            buttons[11].setVisible(false);
            buttons[11].addActionListener((ActionEvent e) -> {
                executeTabSwitch();
                changeToMn();
                String str = "Өмнөговь";
                String str2 = "Хүрмэн";
                String str3 = "К-48-40";
                changeToMn();
                switchingTab(str, false);
                robot.delay(20);
                doType(KeyEvent.VK_TAB);

                changeToMn();
                switchingTab(str2, false);
                robot.delay(20);
                doType(KeyEvent.VK_TAB);

                changeToEn();
                switchingTab(str3, false);
                robot.delay(20);
                doType(KeyEvent.VK_TAB);
            });
            buttons[12].addActionListener((ActionEvent e) -> {
                executeTabSwitch();
                robot.delay(20);
                changeToMn();
                executeTabSwitch();
                robot.delay(20);
            });

            frame.setContentPane(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(out_width, out_height);
            frame.setLocation(WIDTH - out_width, 50);
        } catch (Exception ex) {
            ex.printStackTrace();
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

    private void switchingTab(String text, boolean switchTab) {
        if (!switchTab) {
            robot.delay(15);
            char[] ch = text.toCharArray();
            robot.setAutoDelay(0);
            robot.delay(20);
            for (char c : ch) {
                type(c);
            }
            doType(KeyEvent.VK_TAB);
            robot.delay(20);
        }
    }

    private void switchingTab(String text) {
        changeToMn();
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
                case 'а':
                    doType(java.awt.event.KeyEvent.VK_G);
                    break;
                case 'б':
                    doType(java.awt.event.KeyEvent.VK_D);
                    break;
                case 'в':
                    doType(java.awt.event.KeyEvent.VK_PERIOD);
                    break;
                case 'г':
                    doType(java.awt.event.KeyEvent.VK_U);
                    break;
                case 'д':
                    doType(java.awt.event.KeyEvent.VK_SEMICOLON);
                    break;
                case 'е':
                    doType(java.awt.event.KeyEvent.VK_MINUS);
                    break;
                case 'ё':
                    doType(java.awt.event.KeyEvent.VK_C);
                    break;
                case 'ж':
                    doType(java.awt.event.KeyEvent.VK_R);
                    break;
                case 'з':
                    doType(java.awt.event.KeyEvent.VK_P);
                    break;
                case 'и':
                    doType(java.awt.event.KeyEvent.VK_N);
                    break;
                case 'й':
                    doType(java.awt.event.KeyEvent.VK_A);
                    break;
                case 'к':
                    doType(java.awt.event.KeyEvent.VK_OPEN_BRACKET);
                    break;
                case 'л':
                    doType(java.awt.event.KeyEvent.VK_L);
                    break;
                case 'м':
                    doType(java.awt.event.KeyEvent.VK_B);
                    break;
                case 'н':
                    doType(java.awt.event.KeyEvent.VK_Y);
                    break;
                case 'о':
                    doType(java.awt.event.KeyEvent.VK_K);
                    break;
                case 'ө':
                    doType(java.awt.event.KeyEvent.VK_F);
                    break;
                case 'п':
                    doType(java.awt.event.KeyEvent.VK_QUOTE);
                    break;
                case 'р':
                    doType(java.awt.event.KeyEvent.VK_J);
                    break;
                case 'с':
                    doType(java.awt.event.KeyEvent.VK_V);
                    break;
                case 'т':
                    doType(java.awt.event.KeyEvent.VK_M);
                    break;
                case 'у':
                    doType(java.awt.event.KeyEvent.VK_E);
                    break;
                case 'ү':
                    doType(java.awt.event.KeyEvent.VK_O);
                    break;
                case 'ф':
                    doType(java.awt.event.KeyEvent.VK_Q);
                    break;
                case 'х':
                    doType(java.awt.event.KeyEvent.VK_H);
                    break;
                case 'ц':
                    doType(java.awt.event.KeyEvent.VK_W);
                    break;
                case 'ч':
                    doType(java.awt.event.KeyEvent.VK_X);
                    break;
                case 'ш':
                    doType(java.awt.event.KeyEvent.VK_I);
                    break;
                case 'щ':
                    doType(java.awt.event.KeyEvent.VK_EQUALS);
                    break;
                case 'ъ':
                    doType(java.awt.event.KeyEvent.VK_CLOSE_BRACKET);
                    break;
                case 'ы':
                    doType(java.awt.event.KeyEvent.VK_S);
                    break;
                case 'ь':
                    doType(java.awt.event.KeyEvent.VK_COMMA);
                    break;
                case 'э':
                    doType(java.awt.event.KeyEvent.VK_T);
                    break;
                case 'ю':
                    doType(java.awt.event.KeyEvent.VK_SLASH);
                    break;
                case 'я':
                    doType(java.awt.event.KeyEvent.VK_Z);
                    break;
                // Uppercase Mongolia Cyrillic
                case 'А':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_G);
                    break;
                case 'Б':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_D);
                    break;
                case 'В':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_PERIOD);
                    break;
                case 'Г':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_U);
                    break;
                case 'Д':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_SEMICOLON);
                    break;
                case 'Е':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_MINUS);
                    break;
                case 'Ё':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_C);
                    break;
                case 'Ж':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_R);
                    break;
                case 'З':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_P);
                    break;
                case 'И':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_N);
                    break;
                case 'Й':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_A);
                    break;
                case 'К':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_OPEN_BRACKET);
                    break;
                case 'Л':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_L);
                    break;
                case 'М':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_B);
                    break;
                case 'Н':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_Y);
                    break;
                case 'О':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_K);
                    break;
                case 'Ө':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_F);
                    break;
                case 'П':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_QUOTE);
                    break;
                case 'Р':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_J);
                    break;
                case 'С':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_V);
                    break;
                case 'Т':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_M);
                    break;
                case 'У':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_E);
                    break;
                case 'Ү':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_O);
                    break;
                case 'Ф':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_Q);
                    break;
                case 'Х':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_H);
                    break;
                case 'Ц':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_W);
                    break;
                case 'Ч':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_X);
                    break;
                case 'Ш':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_I);
                    break;
                case 'Щ':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_EQUALS);
                    break;
                case 'Ъ':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_CLOSE_BRACKET);
                    break;
                case 'Ы':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_S);
                    break;
                case 'Ь':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_COMMA);
                    break;
                case 'Э':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_T);
                    break;
                case 'Ю':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_SLASH);
                    break;
                case 'Я':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_Z);
                    break;
                // Numbers
                case '0':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_0);
                    break;
                case '1':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_1);
                    break;
                case '2':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_2);
                    break;
                case '3':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_3);
                    break;
                case '4':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_4);
                    break;
                case '5':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_5);
                    break;
                case '6':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_6);
                    break;
                case '7':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_7);
                    break;
                case '8':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_8);
                    break;
                case '9':
                    doType(java.awt.event.KeyEvent.VK_SHIFT, java.awt.event.KeyEvent.VK_9);
                    break;
                case '-':
                    doType(KeyEvent.VK_ALT, KeyEvent.VK_SHIFT);
                    robot.delay(20);
                    doType(KeyEvent.VK_MINUS);
                    robot.delay(10);
                    doType(KeyEvent.VK_ALT, KeyEvent.VK_SHIFT);
                    robot.delay(20);
                    break;
                case '\t':
                    doType(java.awt.event.KeyEvent.VK_TAB);
                    break;
                case '\n':
                    doType(java.awt.event.KeyEvent.VK_ENTER);
                    break;
                case '"':
                    doType(java.awt.event.KeyEvent.VK_3);
                    break;
                case '.':
                    doType(java.awt.event.KeyEvent.VK_6);
                    break;
                case ' ':
                    doType(java.awt.event.KeyEvent.VK_SPACE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, Object> readResource(File resourceFile) {
        if (resourceFile.exists()) {
            File f = resourceFile;
            HashMap<String, Object> mapping = new HashMap<>();
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(f);
                Element root_e = (Element) doc.getChildNodes().item(0).getChildNodes();
                NodeList profile, locate, solbiz;
                profile = root_e.getElementsByTagName("profile").item(0).getChildNodes();
                locate = root_e.getElementsByTagName("locate").item(0).getChildNodes();
                solbiz = root_e.getElementsByTagName("solbiz").item(0).getChildNodes();
                
                XPathFactory xpf = XPathFactory.newInstance();
                XPath xpath = xpf.newXPath();
                XPathExpression exp;
                
                mapping.put("profile", getMaps(profile));
                mapping.put("locate", getMaps(locate));
                mapping.put("solbiz", getSolbizMaps(solbiz));
                
                return mapping;
            } catch (SAXException | IOException | ParserConfigurationException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private HashMap<String, Object> getMaps(NodeList nodeList) {
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                String text = node.getTextContent();
                map.put(nodeName, text);
            }
        }
        return map;
    }
    
    private HashMap<String, Object> getSolbizMaps(NodeList nodeList) {
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                HashMap<String, String> rows = new HashMap<>();
                NodeList rowsList = node.getChildNodes();
                for (int j = 0; j < rowsList.getLength(); j++) {
                    Node n = rowsList.item(j);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        String nName = n.getNodeName();
                        String nText = n.getTextContent();
                        rows.put(nName, nText);
                    }
                }
                map.put(nodeName, rows);
            }
        }
        return map;
    }

    private boolean isItMn() {
        InputContext context = InputContext.getInstance();
        String lang = context.getLocale().toString();
        return lang.contains("mn_");
    }

    private boolean isItEn() {
        InputContext context = InputContext.getInstance();
        String lang = context.getLocale().toString();
        return lang.contains("en_");
    }

    private void changeToMn() {
        if (isItEn()) {
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_ALT);
        }
    }

    private void changeToEn() {
        if (isItMn()) {
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_ALT);
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

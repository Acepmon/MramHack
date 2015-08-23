package jennifer;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.im.InputContext;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Acep / D.Tsogtbayar
 */
public class Jonathan extends Application {

    // Configurations
    private interface conf {

        public final double WIDTH = 530;
        public final double HEIGHT = 700;
        public final boolean RESIZABLE = true;
        public final boolean MAXIMIZED = false;
        public final double FIELD_WIDTH = 220;
        public final double FIELD_HEIGHT = 40;
        public final boolean FIELD_EDITABLE = false;
        public final double BUTTON_WIDTH = 200;
        public final double BUTTON_HEIGHT = 40;
        public final double GRID_SPACING_H = 10;
        public final double GRID_SPACING_V = 5;
        public final double GRID_PADDING = 10;
        public final double PANE_PADDING = 0;
        public final int FONT_SIZE = 13;
        public final String FONT_FAMILY = "Arial";
    }

    private interface InjectAction {
        public void run(Object fieldValue, ActionEvent event);
    }

    // Grid row containers
    private class GridRow {
        
        private String fieldValue;
        private String buttonText;
        private InjectAction action;
        private Object specialValue;
        private boolean includeSpecial = false;
        private String hotkey;

        public GridRow(String fieldValue, String buttonText) {
            this.fieldValue = fieldValue;
            this.buttonText = buttonText;
            this.action = null;
        }

        public GridRow(String fieldValue, String buttonText, InjectAction action) {
            this.fieldValue = fieldValue;
            this.buttonText = buttonText;
            this.action = action;
        }
        
        public GridRow(String fieldValue, String buttonText, String hotkey, InjectAction action) {
            this.fieldValue = fieldValue;
            this.buttonText = buttonText;
            this.hotkey = hotkey;
            this.action = action;
        }

        public GridRow() {
            this.fieldValue = "";
        }
        
        public GridRow(boolean includeSpecial) {
            this.fieldValue = "";
            this.includeSpecial = includeSpecial;
        }

        public String getHotkey() {
            return hotkey;
        }

        public void setHotkey(String hotkey) {
            this.hotkey = hotkey;
        }

        public boolean isIncludeSpecial() {
            return includeSpecial;
        }

        public void setIncludeSpecial(boolean includeSpecial) {
            this.includeSpecial = includeSpecial;
        }

        public Object getSpecialValue() {
            return specialValue;
        }

        public void setSpecialValue(Object specialValue) {
            this.specialValue = specialValue;
        }

        public String getButtonText() {
            return buttonText;
        }

        public void setButtonText(String buttonText) {
            this.buttonText = buttonText;
        }

        public InjectAction getAction() {
            return action;
        }

        public void setAction(InjectAction action) {
            this.action = action;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }

    }

    // Container
    private class Grids {

        private final ObservableList<GridRow> rows;

        public Grids() {
            rows = FXCollections.observableArrayList();
        }

        public void addRow(GridRow row) {
            if (row != null) {
                rows.add(row);
            }
        }

        public ObservableList<GridRow> getRows() {
            return rows;
        }
    }

    // Core essentials
    private Stage stage;
    private Scene scene;
    private ScrollPane root;

    // Container data
    private Grids grids;

    // Container pane
    private GridPane gridPane;

    // Robot
    private Robot robot;
    
    // Current language
    private String lang;
    
    // Button Font
    private Font btnFont;
    
    // Resources
    private HashMap<String, Object> resourceMap;
    
    // Hotkeys
    private ObservableList<HashMap<String, String>> hotkeys;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        double scr_width = screen.getWidth();
        double scr_height = screen.getHeight();

        double stage_x = scr_width - conf.WIDTH;
        double stage_y = scr_height - conf.HEIGHT;
        
        hotkeys = FXCollections.observableArrayList();
        grids = new Grids();
        robot = new Robot();
        btnFont = new Font(conf.FONT_FAMILY, conf.FONT_SIZE);
        
        stage = primaryStage;
        stage.setScene(getScene());
        stage.setMinWidth(conf.WIDTH);
        stage.setMinHeight(conf.HEIGHT);
        stage.setMaximized(conf.MAXIMIZED);
        stage.setResizable(conf.RESIZABLE);
        stage.setX(stage_x);
        stage.setY(stage_y);
        
        initializeComponents();
        loadGrid();
        addAdditionalButtons();
        hotkeyConfiguration();
        
        stage.show();
        
        InputContext context = InputContext.getInstance();
        lang = context.getLocale().toString();
//        if (lang.contains("en")) { 
//            changeLang(); 
//        }
    }

    private Scene getScene() {
        scene = new Scene(getRoot(), Color.web("#F0F4F5", 0.5));
        return scene;
    }

    private ScrollPane getRoot() {
        root = new ScrollPane();
        root.setPadding(new Insets(conf.PANE_PADDING));
        root.setContent(getGridPane());
        return root;
    }

    private GridPane getGridPane() {
        gridPane = new GridPane();
        gridPane.setHgap(conf.GRID_SPACING_H);
        gridPane.setVgap(conf.GRID_SPACING_V);
        gridPane.setPadding(new Insets(conf.GRID_PADDING));
        return gridPane;
    }
    
    private void addAdditionalButtons() {
        Button tab = new Button("(F1) <TAB>");
        tab.setPrefSize(conf.BUTTON_WIDTH, conf.BUTTON_HEIGHT);
        tab.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
        tab.setFont(btnFont);
        tab.setAlignment(Pos.CENTER_LEFT);
        tab.setOnAction((event) -> {
            executeAltTab();
            executeTab();
            executeAltTab();
        });
        tab.setOnMouseEntered((event) -> {
            tab.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
        });
        tab.setOnMouseExited((event) -> {
            tab.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
        });
        Button langBtn = new Button("(F2) <LANG>");
        langBtn.setPrefSize(conf.FIELD_WIDTH, conf.BUTTON_HEIGHT);
        langBtn.setFont(btnFont);
        langBtn.setAlignment(Pos.CENTER_LEFT);
        langBtn.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
        langBtn.setOnAction((event) -> {
            executeAltTab();
            changeLang();
            executeAltTab();
        });
        langBtn.setOnMouseEntered((event) -> {
            langBtn.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
        });
        langBtn.setOnMouseExited((event) -> {
            langBtn.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
        });
        Button cdc = new Button("(F3) Хот, Дүүрэг, Код");
        cdc.setPrefSize(conf.BUTTON_WIDTH, conf.BUTTON_HEIGHT);
        cdc.setFont(btnFont);
        cdc.setAlignment(Pos.CENTER_LEFT);
        cdc.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
        cdc.setOnAction((event) -> {
            readResource().forEach((String cdc_key, Object cdc_value) -> {
                if (cdc_key.equals("locate")) {
                    HashMap<String, Object> locate = (HashMap<String, Object>) cdc_value;
                    executeAltTab();
                    typeText(locate.get("pos_city").toString());
                    executeTab();
                    typeText(locate.get("pos_district").toString());
                    executeTab();
                    typeText(locate.get("pos_code").toString());
                    executeTab();
                    executeAltTab();
                }
            });
        });
        cdc.setOnMouseEntered((event) -> {
            cdc.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
        });
        cdc.setOnMouseExited((event) -> {
            cdc.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
        });
        Button solbizBtn = new Button("(F5) Солбицол Бүхэлдээ");
        solbizBtn.setPrefSize(conf.BUTTON_WIDTH, conf.BUTTON_HEIGHT);
        solbizBtn.setFont(btnFont);
        solbizBtn.setAlignment(Pos.CENTER_LEFT);
        solbizBtn.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
        solbizBtn.setOnMouseEntered((event) -> {
            cdc.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
        });
        solbizBtn.setOnMouseExited((event) -> {
            cdc.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
        });
        solbizBtn.setOnAction((event) -> {
            readResource().forEach((String solbiz_key, Object solbiz_value) -> {
                if (solbiz_key.equals("solbiz")) {
                    HashMap<String, Object> solbiz_map = (HashMap<String, Object>) solbiz_value;
                    executeAltTab();
                    solbiz_map.forEach((String s_key, Object s_val) -> {
                        if (s_key.contains("hotkey")) {
                            
                        } else {
                            HashMap<String, Object> solbiz = (HashMap<String, Object>) s_val;
                    
                            HashMap<String, String> col_urtrag = (HashMap<String, String>) solbiz.get("urtrag");
                            HashMap<String, String> col_orgorog = (HashMap<String, String>) solbiz.get("orgorog");
                            
                            // Urtrag
                            typeText(col_urtrag.get("grad"));
                            executeTab();
                            typeText(col_urtrag.get("min"));
                            executeTab();
                            typeText(col_urtrag.get("sec"));
                            executeTab();

                            // Orgorog
                            typeText(col_orgorog.get("grad"));
                            executeTab();
                            typeText(col_orgorog.get("min"));
                            executeTab();
                            typeText(col_orgorog.get("sec"));
                            executeTab();
                            
                        }
                    });
                    executeAltTab();
                }
            });
        });
        
        
        gridPane.add(tab, 0, 1);
        gridPane.add(langBtn, 1, 1);
        gridPane.add(cdc, 0, 2);
        gridPane.add(solbizBtn, 1, 2);
    }

    private void initializeComponents() {
        File resource = new File("resource.xml");
        if (resource.exists()) {
            this.resourceMap = loadResource(resource);
            HashMap<String, Object> map = readResource();
            HashMap<String, Object> profileMap = (HashMap<String, Object>) map.get("profile");
            HashMap<String, Object> locateMap = (HashMap<String, Object>) map.get("locate");
            HashMap<String, Object> solbizMap = (HashMap<String, Object>) map.get("solbiz");
            GridRow gridrow = new GridRow(profileMap.get("pos").toString(), "Албан тушаал", profileMap.get("pos_hotkey").toString(), (Object fieldValue, ActionEvent event) -> {
                executeAltTab();
                typeText(fieldValue.toString());
                executeTab();
                executeAltTab();
            });
            HashMap<String, String> hotkey = new HashMap<>();
            hotkey.put("hotkey", gridrow.getHotkey());
            hotkey.put("hashCode", ""+gridrow.hashCode());
            hotkeys.add(hotkey);
            grids.addRow(gridrow);
            
            gridrow = new GridRow(profileMap.get("lname").toString(), "Овог", profileMap.get("lname_hotkey").toString(), (Object fieldValue, ActionEvent event) -> {
                executeAltTab();
                typeText(fieldValue.toString());
                executeTab();
                executeAltTab();
            });
            hotkey = new HashMap<>();
            hotkey.put("hotkey", gridrow.getHotkey());
            hotkey.put("hashCode", ""+gridrow.hashCode());
            hotkeys.add(hotkey);
            grids.addRow(gridrow);
            
            gridrow = new GridRow(profileMap.get("fname").toString(), "Нэр", profileMap.get("fname_hotkey").toString(), (Object fieldValue, ActionEvent event) -> {
                executeAltTab();
                typeText(fieldValue.toString());
                executeTab();
                executeAltTab();
            });
            hotkey = new HashMap<>();
            hotkey.put("hotkey", gridrow.getHotkey());
            hotkey.put("hashCode", ""+gridrow.hashCode());
            hotkeys.add(hotkey);
            grids.addRow(gridrow);
            
            gridrow = new GridRow(locateMap.get("pos_city").toString(), "Хот", locateMap.get("pos_city_hotkey").toString(), (Object fieldValue, ActionEvent event) -> {
                executeAltTab();
                typeText(fieldValue.toString());
                executeTab();
                executeAltTab();
            });
            hotkey = new HashMap<>();
            hotkey.put("hotkey", gridrow.getHotkey());
            hotkey.put("hashCode", ""+gridrow.hashCode());
            hotkeys.add(hotkey);
            grids.addRow(gridrow);
            
            gridrow = new GridRow(locateMap.get("pos_district").toString(), "Дүүрэг", locateMap.get("pos_district_hotkey").toString(), (Object fieldValue, ActionEvent event) -> {
                executeAltTab();
                typeText(fieldValue.toString());
                executeTab();
                executeAltTab();
            });
            hotkey = new HashMap<>();
            hotkey.put("hotkey", gridrow.getHotkey());
            hotkey.put("hashCode", ""+gridrow.hashCode());
            hotkeys.add(hotkey);
            grids.addRow(gridrow);
            
            gridrow = new GridRow(locateMap.get("pos_code").toString(), "Планшетийн код", locateMap.get("pos_code_hotkey").toString(), (Object fieldValue, ActionEvent event) -> {
                executeAltTab();
                typeText(fieldValue.toString());
                executeTab();
                executeAltTab();
            });
            hotkey = new HashMap<>();
            hotkey.put("hotkey", gridrow.getHotkey());
            hotkey.put("hashCode", ""+gridrow.hashCode());
            hotkeys.add(hotkey);
            grids.addRow(gridrow);
            
            gridrow = new GridRow(locateMap.get("name").toString(), "Талбайн нэр", locateMap.get("name_hotkey").toString(), (Object fieldValue, ActionEvent event) -> {
                executeAltTab();
                typeText(fieldValue.toString());
                executeTab();
                executeAltTab();
            });
            hotkey = new HashMap<>();
            hotkey.put("hotkey", gridrow.getHotkey());
            hotkey.put("hashCode", ""+gridrow.hashCode());
            hotkeys.add(hotkey);
            grids.addRow(gridrow);
            
            gridrow = new GridRow(locateMap.get("size").toString(), "Талбайн хэмжээ", locateMap.get("size_hotkey").toString(), (Object fieldValue, ActionEvent event) -> {
                executeAltTab();
                typeText(fieldValue.toString());
                executeTab();
                executeAltTab();
            });
            hotkey = new HashMap<>();
            hotkey.put("hotkey", gridrow.getHotkey());
            hotkey.put("hashCode", ""+gridrow.hashCode());
            hotkeys.add(hotkey);
            grids.addRow(gridrow);
            
            Set<String> solbiz_keys = solbizMap.keySet();
            ObservableList<String> ob_sol_keys = FXCollections.observableArrayList();
            solbiz_keys.stream().forEach((String key) -> {
                ob_sol_keys.add(key);
            });
            ob_sol_keys.sort((String o1, String o2) -> {
                return o1.compareTo(o2);
//                if (o1.contains("hotkey") & o2.contains("hotkey")) {
//                    
//                }
//                
//                if (!o1.contains("hotkey") && !o2.contains("hotkey")) {
//                    int k1 = Integer.parseInt(o1.substring(o1.lastIndexOf("w")+1, o1.indexOf("_hotkey")));
//                    int k2 = Integer.parseInt(o2.substring(o2.lastIndexOf("w")+1, o2.indexOf("_hotkey")));
//                    if (k1 > k2) {
//                        return 1;
//                    } else if (k1 < k2) {
//                        return -1;
//                    }
//                } else {
//                    int k1 = Integer.parseInt(o1.substring(o1.lastIndexOf("w")+1, o1.length()));
//                    int k2 = Integer.parseInt(o2.substring(o2.lastIndexOf("w")+1, o2.length()));
//                    if (k1 > k2) {
//                        return 1;
//                    } else if (k1 < k2) {
//                        return -1;
//                    }
//                }
//                return 0;
            });
            for (String key : ob_sol_keys) {
                if (key.contains("_")) {
                    
                } else {
                HashMap<String, Object> row = (HashMap<String, Object>) solbizMap.get(key);
                int key_num = Integer.parseInt(key.substring(key.lastIndexOf("w")+1, key.length()));
                
                HashMap<String, String> urtrag = (HashMap<String, String>) row.get("urtrag");
                HashMap<String, String> orgorog = (HashMap<String, String>) row.get("orgorog");
                
                String urtragField = urtrag.get("grad") + ", " + urtrag.get("min") + ", " + urtrag.get("sec");
                String orgorogField = orgorog.get("grad") + ", " + orgorog.get("min") + ", " + orgorog.get("sec");
                
                gridrow = new GridRow(true);
                gridrow.setFieldValue("Urtrag: "+urtragField+"  Orgorog: "+orgorogField);
                gridrow.setSpecialValue(row);
                gridrow.setButtonText("Солбиз Мөр "+key_num);
                gridrow.setHotkey(solbizMap.get(key+"_hotkey").toString());
                gridrow.setAction((Object specialValue, ActionEvent event) -> {
                    HashMap<String, Object> solbiz = (HashMap<String, Object>) specialValue;
                    
                    HashMap<String, String> col_urtrag = (HashMap<String, String>) solbiz.get("urtrag");
                    HashMap<String, String> col_orgorog = (HashMap<String, String>) solbiz.get("orgorog");
                    
                    executeAltTab();
                    // Urtrag
                    typeText(col_urtrag.get("grad"));
                    executeTab();
                    typeText(col_urtrag.get("min"));
                    executeTab();
                    typeText(col_urtrag.get("sec"));
                    executeTab();
                    
                    // Orgorog
                    typeText(col_orgorog.get("grad"));
                    executeTab();
                    typeText(col_orgorog.get("min"));
                    executeTab();
                    typeText(col_orgorog.get("sec"));
                    executeTab();
                    executeAltTab();
                });
                hotkey = new HashMap<>();
                hotkey.put("hotkey", gridrow.getHotkey());
                hotkey.put("hashCode", ""+gridrow.hashCode());
                hotkeys.add(hotkey);
                grids.addRow(gridrow);
                }
            }
        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "'resource.xml' file iig program iin folder dotor huulj ogno uu!", "Resource oldohgui bn", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                initializeComponents();
            } else {
                System.exit(0);
            }
        }
    }

    private void loadGrid() {
        int current_row = 4;
        for (GridRow row : this.grids.getRows()) {
            TextField field = new TextField(row.fieldValue);
            field.setPrefSize(conf.FIELD_WIDTH, conf.FIELD_HEIGHT);
            field.setEditable(conf.FIELD_EDITABLE);
            field.setFont(btnFont);
            field.setOnMousePressed((event) -> {
                String myString = field.getText();
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            });
            Button button = new Button(row.buttonText);
            button.setFont(btnFont);
            button.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
            field.setStyle("-fx-font-weight: bold; -fx-text-fill: blue");
            button.setOnMouseEntered((event) -> {
                button.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
                field.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
            });
            button.setOnMouseExited((event) -> {
                button.setStyle("-fx-text-fill: blue; -fx-font-weight: bold");
                field.setStyle("-fx-text-fill: blue; -fx-font-weight: bold");
            });
            field.setOnMouseEntered((event) -> {
                button.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
                field.setStyle("-fx-text-fill: red; -fx-font-weight: bold"); 
            });
            field.setOnMouseExited((event) -> {
                button.setStyle("-fx-text-fill: blue; -fx-font-weight: bold");
                field.setStyle("-fx-text-fill: blue; -fx-font-weight: bold");
            });
            button.setAlignment(Pos.CENTER_LEFT);
            button.setPrefSize(conf.BUTTON_WIDTH, conf.BUTTON_HEIGHT);
            button.setOnAction((event) -> {
                if (row.includeSpecial) {
                    row.action.run(row.specialValue, event);
                } else {
                    row.action.run(row.fieldValue, event);
                }
            });
            Circle hotkey_circle = new Circle(13, Color.DARKGRAY);
            Text hotkey_text = new Text(row.hotkey);
            hotkey_text.setFill(Color.WHITE);
            hotkey_text.setStyle("-fx-font-weight: bold");
            hotkey_text.setFont(new Font("Consolas", btnFont.getSize()));
            hotkey_text.setBoundsType(TextBoundsType.VISUAL);
            StackPane hotkey_stack = new StackPane(hotkey_circle, hotkey_text);
            gridPane.addRow(current_row, new HBox(5, hotkey_stack, button), field);
            current_row++;
        }
    }
    
    private void hotkeyConfiguration() {
        this.readResource().forEach((String key, Object value) -> {
            if (key.equals("solbiz")) {
                HashMap<String, Object> profile = (HashMap<String, Object>) value;
                System.out.println(profile);
                Set<String> profile_keys = profile.keySet();
                for (Object k_obj : profile_keys.toArray()) {
                    if (k_obj.toString().contains("hotkey")) {
                        System.out.println(k_obj.toString());
                    }
                }
            } else if (key.equals("locate")) {
                
            } else if (key.equals("solbiz")) {
                
            }
        });
        scene.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.F1)) {
                executeAltTab();
                executeTab();
                executeAltTab();
            } else if (event.getCode().equals(KeyCode.F2)) {
                executeAltTab();
                changeLang();
                executeAltTab();
            } else if (event.getCode().equals(KeyCode.F3)) {
                readResource().forEach((String cdc_key, Object cdc_value) -> {
                    if (cdc_key.equals("locate")) {
                        HashMap<String, Object> locate = (HashMap<String, Object>) cdc_value;
                        executeAltTab();
                        typeText(locate.get("pos_city").toString());
                        executeTab();
                        typeText(locate.get("pos_district").toString());
                        executeTab();
                        typeText(locate.get("pos_code").toString());
                        executeTab();
                        executeAltTab();
                    }
                });
            } else if (event.getCode().equals(KeyCode.F5)) {
                readResource().forEach((String solbiz_key, Object solbiz_value) -> {
                if (solbiz_key.equals("solbiz")) {
                    HashMap<String, Object> solbiz_map = (HashMap<String, Object>) solbiz_value;
                    executeAltTab();
                    solbiz_map.forEach((String s_key, Object s_val) -> {
                        if (s_key.contains("hotkey")) {
                            
                        } else {
                            HashMap<String, Object> solbiz = (HashMap<String, Object>) s_val;
                    
                            HashMap<String, String> col_urtrag = (HashMap<String, String>) solbiz.get("urtrag");
                            HashMap<String, String> col_orgorog = (HashMap<String, String>) solbiz.get("orgorog");
                            
                            // Urtrag
                            typeText(col_urtrag.get("grad"));
                            executeTab();
                            typeText(col_urtrag.get("min"));
                            executeTab();
                            typeText(col_urtrag.get("sec"));
                            executeTab();

                            // Orgorog
                            typeText(col_orgorog.get("grad"));
                            executeTab();
                            typeText(col_orgorog.get("min"));
                            executeTab();
                            typeText(col_orgorog.get("sec"));
                            executeTab();
                            
                        }
                    });
                    executeAltTab();
                }
            });
            } else {
                hotkeys.stream().forEach((hash) -> {
                    if (event.getCode().getName().equalsIgnoreCase(hash.get("hotkey"))) {
                        grids.getRows().stream().forEach((gridrow) -> {
                            if (gridrow.hashCode() == Integer.parseInt(hash.get("hashCode"))) {
                                if (gridrow.includeSpecial) {
                                    gridrow.action.run(gridrow.specialValue, null);
                                } else {
                                    gridrow.action.run(gridrow.fieldValue, null);
                                }
                            }
                        });
                    }
                });
            }
        });
    }
    
    private boolean executeAltTab() {
        try {
            robot.delay(5);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(30);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.delay(5);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void typeText(String text) {
        removeExistingText();
        robot.delay(2);
        char[] ch = text.toCharArray();
        for (char c : ch) {
            type(c);
        }
        robot.delay(2);
    }
    
    private void removeExistingText() {
        robot.delay(5);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.delay(20);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(20);
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        robot.delay(5);
    }
    
    private void executeTab() {
        robot.delay(5);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        robot.delay(5);
    }
    
    private void changeToMn() {
        if (lang.contains("en")) {
            changeLang();
            lang = "mn_MN";
            System.out.println(lang);
        }
    }

    private void changeToEn() {
        if (!lang.contains("mn")) {
            changeLang();
            lang = "en_US";
            System.out.println(lang);
        }
    }
    
    private void changeLang() {
        robot.delay(5);
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.delay(200);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.delay(5);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private HashMap<String, Object> readResource() {
        return this.resourceMap;
    }
    
    private HashMap<String, Object> loadResource(File resourceFile) {
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
                Element e = (Element) node;
                String nodeName = node.getNodeName();
                String text = node.getTextContent();
                map.put(nodeName, text);
                map.put(nodeName+"_hotkey", e.getAttribute("hotkey"));
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
                HashMap<String, Object> rows = new HashMap<>();
                NodeList rowsList = node.getChildNodes();
                for (int j = 0; j < rowsList.getLength(); j++) {
                    Node n = rowsList.item(j);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        String nName = n.getNodeName();
                        HashMap<String, String> col = new HashMap<>();
                        NodeList colList = n.getChildNodes();
                        for (int k = 0; k < colList.getLength(); k++) {
                            Node c = colList.item(k);
                            if (c.getNodeType() == Node.ELEMENT_NODE) {
                                String cName = c.getNodeName();
                                String cText = c.getTextContent();
                                col.put(cName, cText);
                            }
                        }
                        rows.put(nName, col);
                    }
                }
                map.put(nodeName, rows);
                map.put(nodeName+"_hotkey", ((Element) node).getAttribute("hotkey"));
            }
        }
        return map;
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
                    doType(KeyEvent.VK_COMMA);
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
//                    doType(KeyEvent.VK_ALT, KeyEvent.VK_SHIFT);
                    robot.delay(10);
                    doType(KeyEvent.VK_2);
                    robot.delay(10);
//                    doType(KeyEvent.VK_ALT, KeyEvent.VK_SHIFT);
//                    robot.delay(20);
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

}

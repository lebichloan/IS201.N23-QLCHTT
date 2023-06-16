package controller;

import java.lang.reflect.Array;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.NhanVien;
import db.database;

public class QLNhanVienController implements Initializable {
	Connection conn = null;
	Statement stm = null;
	PreparedStatement prepareStm = null;
	ResultSet rs = null;
	String sql = null;
	String email_pattern = "^[a-zA-Z][\\w\\.]{5,29}@[\\w]{2,253}(\\.com)$";
	String email_error_msg = "- Tên email bắt đầu bằng chữ cái, có thể bao gồm chữ cái, con số, dấu chấm nhưng không được có 2 dấu chấm liên tiếp nhau và chiều dài từ 6 đến 30 kí tự\r\n"
			+ "- Kí tự @ đặt ngay cuối tên email và liền trước tên miền\r\n"
			+ "- Tên miền có thể bao gồm chữ cái, con số và chiều dài từ 2 đến 253 kí tự\r\n"
			+ "- Email kết thúc bằng tên miền .com";
	String sdt_pattern = "^0\\d{9}";
	String sdt_error_msg = "SĐT gồm 10 số và bắt đầu bằng số 0";
	IntegerProperty totalEmployee = new SimpleIntegerProperty(0);
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	class dateStringConverter extends StringConverter<LocalDate> {
		private DatePicker dp;
		DateTimeFormatter df;
		
		public dateStringConverter(DatePicker dp, DateTimeFormatter df) {
			this.dp = dp;
			this.df = df;
		}
		public dateStringConverter(DatePicker dp) {
			this.dp = dp;
			this.df = dtf;
		}
		@Override
		public LocalDate fromString(String arg0) {
			if(arg0 == null) return null;
			LocalDate date;
			try {
				date = LocalDate.parse(arg0, dtf);
			}catch(DateTimeParseException e) {
				showAlert(AlertType.ERROR, "Loi dinh dang", "Ngay khong phu hop (dd/MM/yyyy)");
				this.dp.getEditor().clear();
				return null;
			}
			return date;
		}

		@Override
		public String toString(LocalDate arg0) {
			if(arg0 == null) return null;
			return arg0.format(dtf);
		}
		
	}
	@FXML
    private BorderPane root;
	
    @FXML private ImageView dateImg;
    Image dateIcon = new Image(getClass().getResource("/asset/dateIcon.png").toExternalForm());
    Image dateIconGIF = new Image(getClass().getResource("/asset/dateIconGIF.gif").toExternalForm());
    @FXML private VBox datePane;
    DateTimeFormatter timeFormater = DateTimeFormatter.ofPattern("hh:mm:ss a");
    DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("dd/MM");
    LocalDateTime date;
    @FXML private Label dateLabel;
    @FXML private Label weekDayLabel;
    @FXML private Label timeLabel;
    @FXML private Label employeeLabel;
    @FXML private AnchorPane employeePane;
    @FXML private Label employeeTotalLabel;

    @FXML private AnchorPane infoBackgroundPane;
    String infoBackgroundPaneCSSTab1 = "-fx-background-color: #FF6969;"
    		+ "-fx-background-radius: 0 0 10 10;"
    		+ "-fx-border-width: 0;";
    String infoBackgroundPaneCSSTab2 = "-fx-background-color: #EBB02D;"
    		+ "-fx-background-radius: 0 0 10 10;"
    		+ "-fx-border-width: 0;";
    @FXML private AnchorPane infoContentPane;
    String infoContentPaneCSSTab1 = "-fx-background-color: #FF6969;"
    		+ "-fx-border-width: 0;";
    String infoContentPaneCSSTab2 = "-fx-background-color: #EBB02D;"
    		+ "-fx-border-width: 0;";
    @FXML private Label tab1;
    @FXML private AnchorPane tab1Sub1;
    @FXML private AnchorPane tab1Sub2;
    @FXML private AnchorPane tab1Sub3;
    @FXML private Label tab2;
    @FXML private AnchorPane tab2Sub1;
    @FXML private AnchorPane tab2Sub2;
    @FXML private AnchorPane tab2Sub3;
    @FXML private ImageView photoImageView;
    @FXML private AnchorPane photoFrame;
    @FXML private AnchorPane noPhotoPane;
    @FXML private TextField diaChiTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField hoTenTextField;
    @FXML private TextField maNVTextField;
    @FXML private DatePicker ngaySinhDatePicker;
    @FXML private DatePicker ngayVLDatePicker;
    @FXML private TextField sdtTextField;
    @FXML private JFXComboBox<String> gioiTinhComboBox;
    @FXML private ScrollPane infoScrollPane;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextArea ghiChuTextArea;
    @FXML private HBox topRightPaneHBox;
    @FXML private AnchorPane maximizeBtnAnchorPane;
    EventHandler<KeyEvent> enterHandlerTextField = (event)->{
    	if(event.getCode() == KeyCode.ENTER)
    		root.requestFocus();
    };
    
    @FXML private TableView<NhanVien> employeeTable;
    TableViewSelectionModel<NhanVien> tsm;
    ObservableList<NhanVien> nvList = FXCollections.observableArrayList();
    private TableColumn<NhanVien, CheckBox> tickCol = new TableColumn<>(null);
    @FXML private TableColumn<NhanVien, String> maNVCol;
    @FXML private TableColumn<NhanVien, String> hoTenCol;
    @FXML private TableColumn<NhanVien, String> gioiTinhCol;
    @FXML private TableColumn<NhanVien, LocalDate> ngaySinhCol;
    @FXML private TableColumn<NhanVien, String> sdtCol;
    @FXML private TableColumn<NhanVien, String> emailCol;
    @FXML private TableColumn<NhanVien, String> diaChiCol;
    @FXML private TableColumn<NhanVien, LocalDate> ngayVLCol;
    @FXML private TableColumn<NhanVien, Void> actionCol;
        
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		employeeTable.setItems(nvList);
		//init connection
		try {
			conn = database.getConnection();
			stm = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//top left pane
		totalEmployee.addListener((observableVal,oldVal,newVal)->{
			employeeTotalLabel.setText(newVal.toString());
		});
		dateImg.setImage(dateIcon);
		datePane.hoverProperty().addListener((observableVal, oldVal, newVal)->{
			if(newVal) {
				dateImg.setImage(dateIconGIF);
				dateLabel.getStyleClass().add("date-textHover");
				timeLabel.getStyleClass().add("time-textHover");
				weekDayLabel.getStyleClass().add("date-textHover");
				datePane.setPrefWidth(143);
				datePane.setPrefHeight(123);
			}else {
				dateImg.setImage(dateIcon);
				dateLabel.getStyleClass().remove("date-textHover");
				timeLabel.getStyleClass().remove("time-textHover");
				weekDayLabel.getStyleClass().remove("date-textHover");
				datePane.setPrefWidth(130);
				datePane.setPrefHeight(100);
			}
		});
		Timeline updateTime = new Timeline(new KeyFrame(Duration.seconds(0.5), (event)->{
			date = LocalDateTime.now();
			timeLabel.setText(date.format(timeFormater));
			DayOfWeek dow = date.getDayOfWeek();
			if(dow == DayOfWeek.SUNDAY)
				weekDayLabel.setText("CN,");
			else if(dow == DayOfWeek.MONDAY)
				weekDayLabel.setText("T2,");
			else if(dow == DayOfWeek.TUESDAY)
				weekDayLabel.setText("T3");
			else if(dow == DayOfWeek.WEDNESDAY)
				weekDayLabel.setText("T4");
			else if(dow == DayOfWeek.THURSDAY) 
				weekDayLabel.setText("T5");
			else if(dow == DayOfWeek.FRIDAY)
				weekDayLabel.setText("T6");
			else
				weekDayLabel.setText("T7");
			dateLabel.setText(date.format(dateFormater));
		}));
		updateTime.setCycleCount(Animation.INDEFINITE);
		updateTime.play();
		
		employeePane.hoverProperty().addListener((observableVal, oldVal, newVal)->{
			if(newVal) {
				employeeLabel.getStyleClass().add("employee-textHover");
				employeeTotalLabel.getStyleClass().add("employee-textHover");
				employeeLabel.setPrefWidth(125);
				employeeLabel.setPrefHeight(75);
				employeePane.setPrefSize(155, 113);
			}else {
				employeeLabel.getStyleClass().remove("employee-textHover");
				employeeTotalLabel.getStyleClass().remove("employee-textHover");
				employeeLabel.setPrefWidth(107);
				employeeLabel.setPrefHeight(59);
				employeePane.setPrefSize(142, 100);
			}
		});
		
		//top right pane
		topRightPaneHBox.widthProperty().addListener((observableVal, oldVal, newVal)->{
			topRightPaneHBox.setMargin(maximizeBtnAnchorPane, new Insets(0, 0, 0, 415 + ((newVal.doubleValue()>710)?(newVal.doubleValue()-710):0)));
		});
		infoScrollPane.widthProperty().addListener((observableVal, oldVal, newVal)->{
			infoContentPane.setPrefWidth((double)newVal-15);
		});
		tab1Sub1.setVisible(true); tab1Sub2.setVisible(true); tab1Sub3.setVisible(true);
		tab2Sub1.setVisible(false); tab2Sub2.setVisible(false); tab2Sub3.setVisible(false);
		gioiTinhComboBox.setItems(FXCollections.observableArrayList("Nam","Nữ"));
		ngaySinhDatePicker.setConverter(new dateStringConverter(ngaySinhDatePicker));
		ngaySinhDatePicker.setPromptText("dd/MM/yyyy");
		ngayVLDatePicker.setConverter(new dateStringConverter(ngayVLDatePicker));
		ngayVLDatePicker.setPromptText("dd/MM/yyyy");
		maNVTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		hoTenTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		diaChiTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		emailTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		sdtTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		ngaySinhDatePicker.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		ngayVLDatePicker.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		usernameTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		passwordTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		ghiChuTextArea.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		
		//Nhan Vien Table
		tsm = employeeTable.getSelectionModel();
		tsm.getSelectedIndices().addListener((ListChangeListener.Change<? extends Integer> change)->{
			change.next();
			System.out.println(change);
			System.out.println("selected indices: " + tsm.getSelectedIndices());
			System.out.println("add: " + change.getAddedSubList());
			System.out.println("remove: " + change.getRemoved());
			
		});
		tsm.getSelectedItems().addListener((ListChangeListener.Change<? extends NhanVien> change)->{
			change.next();
			for(NhanVien nv : change.getAddedSubList()) {
				nv.getCheckBox().setSelected(true);
			}
			for(NhanVien nv : change.getRemoved()) {
				nv.getCheckBox().setSelected(false);
			}
			if(maNVTextField.isEditable()) { //tab1
				if(change.getAddedSubList().size() > 0) {
					fillEmployeeInfo(change.getAddedSubList().get(0));
				}else {
					resetField();
				}
			}
		});
		tsm.setCellSelectionEnabled(false);
		tsm.setSelectionMode(SelectionMode.MULTIPLE);
		
//		employeeTable.setOnMousePressed(event->{
//			if(event.isDragDetect()) {
//				tsm.clearAndSelect(((TableRow)event.getSource()).getIndex());
//			}
//		});
//		employeeTable.setOnMouseDragEntered(event->{
//			tsm.selectRang
//		});
		
		employeeTable.setEditable(true);
		tickCol.setGraphic(new CheckBox());
		((CheckBox)tickCol.getGraphic()).selectedProperty().addListener((observableVal,oldVal,newVal)->{
			if(newVal) {
				for(NhanVien nv : nvList) {
					if(!(tsm.getSelectedItems().contains(nv))) {
						tsm.select(nv);
					}
				}
			}else {
				tsm.clearSelection();
			}
		});
		tickCol.setResizable(false);
		tickCol.setMinWidth(42);
		tickCol.setMaxWidth(42);
		tickCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
		tickCol.setCellFactory(col -> {
			return new TableCell<NhanVien,CheckBox>() {
				
				@Override
				protected void updateItem(CheckBox item, boolean empty) {
					this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					this.setAlignment(Pos.BASELINE_CENTER);
					super.updateItem(item, empty);
					if(!empty) {
						this.setGraphic(item);
					}
					
				}
			};
		});
		employeeTable.getColumns().add(0, tickCol);
//		tickCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
//		tickCol.setCellFactory(col->{
//			return new TableCell<NhanVien,Boolean>() {
//				CheckBox cb = new CheckBox();
//				{
//					cb.setPrefSize(27, 27);	
//					cb.setSelected(false);
//					cb.selectedProperty().addListener((observableVal,oldVal,newVal)->{
//						if(newVal) {
//							tsm.select(this.getIndex());
//							this.getIt
//						}else {
//							tsm.clearSelection(this.getIndex());
//						}
//					});
//				}
//				
//			};
//		});
		maNVCol.setMinWidth(50);
		maNVCol.setMaxWidth(50);
		maNVCol.setResizable(false);
		maNVCol.setCellValueFactory(new PropertyValueFactory<>("maNV"));
		maNVCol.setEditable(false);
		hoTenCol.setMinWidth(160);
		hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoten"));
		hoTenCol.setCellFactory(TextFieldTableCell.forTableColumn());
		hoTenCol.setOnEditCommit(editEvent -> {
			NhanVien nv = editEvent.getRowValue();
			nv.setHoten(editEvent.getNewValue().trim());
			sql = "update nhanvien set ho_ten = '" + editEvent.getNewValue().trim() + 
					"' where ma_nv = '" + nv.getMaNV() + "'";
			hoTenCol.setCellFactory(TextFieldTableCell.forTableColumn());
			try {
				stm.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		gioiTinhCol.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
		gioiTinhCol.setCellFactory(ComboBoxTableCell.<NhanVien,String>forTableColumn("Nam","Nữ"));
		gioiTinhCol.setOnEditCommit(editEvent->{
			NhanVien nv = editEvent.getRowValue();
			nv.setGioiTinh(editEvent.getNewValue());
			sql = "update nhanvien set gioi_tinh = ? where ma_nv = ?";
			try {
				prepareStm = conn.prepareStatement(sql);
				prepareStm.setString(1, editEvent.getNewValue());
				prepareStm.setString(2, nv.getMaNV());
				prepareStm.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		gioiTinhCol.setMinWidth(75);
		ngaySinhCol.setMinWidth(85);
		ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
		ngaySinhCol.setCellFactory(col -> {
			TableCell<NhanVien, LocalDate> cell =
				new TableCell<NhanVien, LocalDate>() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					this.setText(null);
					this.setGraphic(null);
					if (!empty) {
						String formattedDob = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(item);
						this.setText(formattedDob);
					}
				}
			};
			return cell;
		});
		diaChiCol.setMinWidth(175);
		diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		diaChiCol.setCellFactory(TextFieldTableCell.forTableColumn());
		diaChiCol.setOnEditCommit(editEvent -> {
			NhanVien nv = editEvent.getRowValue();
			nv.setDiaChi(editEvent.getNewValue().trim());
			sql = "update nhanvien set dia_chi = '" + editEvent.getNewValue().trim() + 
					"' where ma_nv = '" + nv.getMaNV() + "'";
			diaChiCol.setCellFactory(TextFieldTableCell.forTableColumn());
			try {
				stm.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		sdtCol.setMinWidth(75);
		sdtCol.setCellValueFactory(new PropertyValueFactory<>("sdt"));
		sdtCol.setCellFactory(TextFieldTableCell.forTableColumn());
		sdtCol.setOnEditCommit(editEvent -> {
			//Kiem tra dinh dang sdt
			if(!(editEvent.getNewValue().trim()).matches(sdt_pattern)) {
				showAlert(AlertType.ERROR, "Sai định dạng SĐT", sdt_error_msg);
				sdtCol.setCellFactory(TextFieldTableCell.forTableColumn());
				return;
			}
			NhanVien nv = editEvent.getRowValue();
			sql = "update nhanvien set sdt = '" + editEvent.getNewValue().trim() + 
					"' where ma_nv = '" + nv.getMaNV() + "'";
			try {
				stm.executeUpdate(sql);
				nv.setSdt(editEvent.getNewValue().trim());
				sdtCol.setCellFactory(TextFieldTableCell.forTableColumn());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		emailCol.setMinWidth(160);
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
		emailCol.setOnEditCommit(editEvent -> {
			//Kiem tra dinh dang email
			if(!(editEvent.getNewValue().trim()).matches(email_pattern) || editEvent.getNewValue().contains("..")) {
				showAlert(AlertType.ERROR, "Sai định dạng email",email_error_msg);
				emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
				return;
			}
			NhanVien nv = editEvent.getRowValue();
			sql = "update nhanvien set email = '" + editEvent.getNewValue().trim() + 
					"' where ma_nv = '" + nv.getMaNV() + "'";
			try {
				stm.executeUpdate(sql);
				nv.setEmail(editEvent.getNewValue().trim());
				emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		ngayVLCol.setMinWidth(85);
		ngayVLCol.setCellValueFactory(new PropertyValueFactory<>("ngayVL"));
		ngayVLCol.setCellFactory(col -> {
			TableCell<NhanVien, LocalDate> cell =
				new TableCell<NhanVien, LocalDate>() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					this.setText(null);
					this.setGraphic(null);
					if (!empty) {
						String formattedDob = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(item);
						this.setText(formattedDob);
					}
				}
			};
			return cell;
		});
//		actionCol.setCellValueFactory(celldata->{
//			NhanVien nv = celldata.getValue();
//			return new ReadOnlyBooleanWrapper(tsm.getSelectedItems().contains(nv));
//		});
		actionCol.setMaxWidth(70);
		actionCol.setMinWidth(70);
		actionCol.setResizable(false);
		actionCol.setCellFactory(col->{
			return new TableCell<NhanVien,Void>() {
				HBox hbox = new HBox();
				{
					hbox.setAlignment(Pos.CENTER_LEFT);
					hbox.setSpacing(15);
					hbox.setPadding(new Insets(3));
				}
				//button
				{
					JFXButton removeBtn = new JFXButton(null);
					removeBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					ImageView imgV = new ImageView(new Image(
							getClass().getResource("/asset/removeUserIcon.png").toExternalForm()));
					removeBtn.setGraphic(imgV);
					imgV.setPreserveRatio(true); imgV.setFitHeight(30);
					removeBtn.getStyleClass().add("removeUserButton");
					removeBtn.hoverProperty().addListener((observableVal,oldVal,newVal)->{
						if(newVal) {
							imgV.setFitHeight(35);
						}else {
							imgV.setFitHeight(30);
						}
					});
					removeBtn.setOnAction(event->{
						String selectMaNV = this.getTableRow().getItem().getMaNV();
						ArrayList<NhanVien> removedNV = new ArrayList<>();
						removedNV.add(this.getTableRow().getItem());
						for(NhanVien nv : tsm.getSelectedItems()) {
							if(this.getTableRow().getItem().getMaNV() != nv.getMaNV()) {
								selectMaNV += ", " + nv.getMaNV();
								removedNV.add(nv);
							}
						}
						Optional<ButtonType> option = showAlert(AlertType.CONFIRMATION,
								"Xác nhận xoá nhân viên", "Bạn chắc chắn muốn xoá nhân viên " + selectMaNV + "?");
						if(option.get() == ButtonType.CANCEL)
							return;
						tsm.clearSelection();
						sql = "delete from nhanvien where ma_nv = ?";
						for(NhanVien nv : removedNV) {
							try {
								prepareStm = conn.prepareStatement(sql);
								prepareStm.setString(1, nv.getMaNV());
								prepareStm.executeUpdate();
								nvList.remove(nv);
								totalEmployee.set(totalEmployee.get() - 1);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					hbox.getChildren().add(removeBtn);
				}
				
				@Override
				protected void updateItem(Void item, boolean empty) {
					super.updateItem(item, empty);
					this.setText(null);
					this.setGraphic(null);
					if(!empty) {
						this.setGraphic(hbox);
						this.setText(null);
					}
				}
			};
		});
		refreshTable();
		
	}
	//end init
	
	@FXML
	public void switchTab(MouseEvent event) {
		if(event.getButton() != MouseButton.PRIMARY)
			return;
		Node tab = (Node)event.getSource();
		if(tab == tab1) {
			if(tab1.getStyleClass().contains("top-right-pane-tab1-active")) 
				return;
			tab1.getStyleClass().clear();
			tab1.getStyleClass().add("top-right-pane-tab1-active");
			tab2.getStyleClass().clear();
			tab2.getStyleClass().add("top-right-pane-tab-inactive");
			tab2Sub1.setVisible(false); tab2Sub2.setVisible(false); tab2Sub3.setVisible(false); 
			tab1Sub1.setVisible(true); tab1Sub2.setVisible(true); tab1Sub3.setVisible(true); 
			infoBackgroundPane.setStyle(infoBackgroundPaneCSSTab1);
			infoContentPane.setStyle(infoContentPaneCSSTab1);
			
//			fetch data from tableview into textfield
			NhanVien nv = tsm.getSelectedItem();
			if(nv == null) {
				resetField();
			}else {
				fillEmployeeInfo(nv);
			}
			
			maNVTextField.setEditable(true);
		}else if(tab == tab2) {
			if(tab2.getStyleClass().contains("top-right-pane-tab2-active")) 
				return;
			tab2.getStyleClass().clear();
			tab2.getStyleClass().add("top-right-pane-tab2-active");
			tab1.getStyleClass().clear();
			tab1.getStyleClass().add("top-right-pane-tab-inactive");
			tab1Sub1.setVisible(false); tab1Sub2.setVisible(false); tab1Sub3.setVisible(false); 
			tab2Sub1.setVisible(true); tab2Sub2.setVisible(true); tab2Sub3.setVisible(true); 
			infoBackgroundPane.setStyle(infoBackgroundPaneCSSTab2);
			infoContentPane.setStyle(infoContentPaneCSSTab2);
			
			resetField();
			/*set maNV automatically ...*/
			sql = "{? = call func_nhanvien_get_id";
			String newMaNV = null;
			try {
				CallableStatement call = conn.prepareCall(sql);
				call.registerOutParameter(1, Types.NVARCHAR);
				call.execute();
				newMaNV = call.getNString(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			maNVTextField.setText(newMaNV);
//			
			maNVTextField.setEditable(false);
		}
	}
	
	void refreshTable() {
		nvList.clear();
		tsm.clearSelection();
		sql = "select * from nhanvien order by ma_nv asc";
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()) {
				String maNV = rs.getString("ma_nv");
				String hoten = rs.getString("ho_ten");
				String gioiTinh = rs.getString("gioi_tinh");
				LocalDate ngaySinh = rs.getDate("ngay_sinh").toLocalDate();
				String diaChi = rs.getString("dia_chi");
				String sdt = rs.getString("sdt");
				String email = rs.getString("email");
				String ghiChu = rs.getString("ghi_chu");
				LocalDate ngayVL = rs.getDate("ngay_vl").toLocalDate();
				String username = rs.getString("username");
				String password = rs.getString("password");
				NhanVien nv = new NhanVien(maNV, hoten, gioiTinh, ngaySinh, diaChi, sdt, email, ghiChu, ngayVL, username, password, null);
				nv.getCheckBox().selectedProperty().addListener((observableVal,oldVal,newVal)->{
					System.out.println("checkBox of " + nv.getMaNV() + ": " + nv.getCheckBox().isSelected());
					if(newVal) 
						tsm.select(nv);
					else 
						tsm.clearSelection(nvList.indexOf(nv));
				});

				nvList.add(nv);
			}
			totalEmployee.set(nvList.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	Optional<ButtonType> showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initStyle(StageStyle.DECORATED);
		return alert.showAndWait();
	}
	
	public void resetField() {
		maNVTextField.clear();
		hoTenTextField.clear();
		gioiTinhComboBox.getSelectionModel().clearSelection();
		ngaySinhDatePicker.setValue(null);
		diaChiTextField.clear();
		emailTextField.clear();
		sdtTextField.clear();
		ghiChuTextArea.clear();
		ngayVLDatePicker.setValue(null);
		usernameTextField.clear();
		passwordTextField.clear();
	}
	
	public void fillEmployeeInfo(NhanVien nv) {
		if(nv == null)
			resetField();
		maNVTextField.setText(nv.getMaNV());
		hoTenTextField.setText(nv.getHoten());
		gioiTinhComboBox.getSelectionModel().select(nv.getGioiTinh());
		ngaySinhDatePicker.setValue(nv.getNgaySinh());
		diaChiTextField.setText(nv.getDiaChi());
		sdtTextField.setText(nv.getSdt());
		emailTextField.setText(nv.getEmail());
		ghiChuTextArea.setText(nv.getGhiChu());
		ngayVLDatePicker.setValue(nv.getNgayVL());
		usernameTextField.setText(nv.getUsername());
		passwordTextField.setText(nv.getPassword());
	}
	
    public void exitFocused(MouseEvent event) {
    	if(event.getButton() == MouseButton.PRIMARY)
    		root.requestFocus();
    }
	
}
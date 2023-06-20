package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
import javafx.scene.control.Tooltip;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
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
	IntegerProperty maleNumber = new SimpleIntegerProperty(0);
	IntegerProperty femaleNumber = new SimpleIntegerProperty(0);

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	StringConverter<LocalDate> StringDateConverter = new StringConverter<LocalDate>() {
		
		@Override
		public String toString(LocalDate arg0) {
			System.out.println("hello from toString: " + ((arg0 == null)?"null":arg0.format(dtf)));
			if(arg0 == null) return "";
			return arg0.format(dtf);
		}
		
		@Override
		public LocalDate fromString(String arg0) {
			System.out.println("hello from fromString: " + ((arg0 == null)?"null":arg0));
			try {
				LocalDate date = LocalDate.parse(arg0, dtf);
				return date;
			}catch(DateTimeParseException e) {
				return null;
			}
		}
	};

	private NhanVien NHANVIEN_SHOWING = null;

	@FXML
	private BorderPane root;

	@FXML
	private ImageView dateImg;
	Image dateIcon = new Image(getClass().getResource("/asset/dateIcon.png").toExternalForm());
	Image dateIconGIF = new Image(getClass().getResource("/asset/dateIconGIF.gif").toExternalForm());
	@FXML
	private VBox datePane;
	DateTimeFormatter timeFormater = DateTimeFormatter.ofPattern("hh:mm:ss a");
	DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("dd/MM");
	LocalDateTime date;
	@FXML
	private Label dateLabel;
	@FXML
	private Label weekDayLabel;
	@FXML
	private Label timeLabel;
	@FXML
	private Label employeeLabel;
	@FXML
	private AnchorPane employeePane;
	@FXML
	private Label employeeTotalLabel;
	@FXML
	private Label maleNumberLabel;
	@FXML
	private Label femaleNumberLabel;
	@FXML
	private Label displayedRowCntLabel;
	@FXML
	private Label selectedRowCntLabel;

	@FXML
	private AnchorPane infoBackgroundPane;
	String infoBackgroundPaneCSSTab1 = "-fx-background-color: #FF6969;" + "-fx-background-radius: 0 0 10 10;"
			+ "-fx-border-width: 0;";
	String infoBackgroundPaneCSSTab2 = "-fx-background-color: #EBB02D;" + "-fx-background-radius: 0 0 10 10;"
			+ "-fx-border-width: 0;";
	@FXML
	private AnchorPane infoContentPane;
	String infoContentPaneCSSTab1 = "-fx-background-color: #FF6969;" + "-fx-border-width: 0;";
	String infoContentPaneCSSTab2 = "-fx-background-color: #EBB02D;" + "-fx-border-width: 0;";
	@FXML
	private Label tab1;
	@FXML
	private AnchorPane tab1Sub1;
	@FXML
	private AnchorPane tab1Sub2;
	@FXML
	private AnchorPane tab1Sub3;
	@FXML
	private Label tab2;
	@FXML
	private AnchorPane tab2Sub1;
	@FXML
	private AnchorPane tab2Sub2;
	@FXML
	private AnchorPane tab2Sub3;
	@FXML
	private ImageView photoImageView;
	@FXML
	private AnchorPane photoPane;
	@FXML
	private TextField diaChiTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField hoTenTextField;
	@FXML
	private TextField maNVTextField;
	@FXML
	private DatePicker ngaySinhDatePicker;
	@FXML
	private DatePicker ngayVLDatePicker;
	@FXML
	private TextField sdtTextField;
	@FXML
	private JFXComboBox<String> gioiTinhComboBox;
	@FXML
	private ScrollPane infoScrollPane;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private TextArea ghiChuTextArea;
	@FXML
	private HBox topRightPaneHBox;

	@FXML
	private AnchorPane noPhotoPane;
	@FXML
	private ImageView addPhotoImgV;
	Image addImgIcon = new Image(getClass().getResource("/asset/addImageIcon.png").toExternalForm());
	@FXML
	private Label themHinhLabel;
	double avatarWidth = 113;
	double avatarHeight = 115;
	FileChooser avatarFileChooser = new FileChooser();
	File avatarFile;
	@FXML
	private AnchorPane changePhotoPane;

	EventHandler<KeyEvent> enterHandlerTextField = (event) -> {
		if (event.getCode() == KeyCode.ENTER)
			root.requestFocus();
	};
	@FXML private AnchorPane disableAddPhotoPane;
	@FXML JFXButton addEmployeeBtn;

	@FXML
	private TableView<NhanVien> employeeTable;
	TableViewSelectionModel<NhanVien> tsm;
	ObservableList<NhanVien> nvList = FXCollections.observableArrayList();
	FilteredList<NhanVien> filteredList = new FilteredList<>(nvList);
	private TableColumn<NhanVien, CheckBox> tickCol = new TableColumn<>(null);
	@FXML
	private TableColumn<NhanVien, String> maNVCol;
	@FXML
	private TableColumn<NhanVien, String> hoTenCol;
	@FXML
	private TableColumn<NhanVien, String> gioiTinhCol;
	@FXML
	private TableColumn<NhanVien, LocalDate> ngaySinhCol;
	@FXML
	private TableColumn<NhanVien, String> sdtCol;
	@FXML
	private TableColumn<NhanVien, String> emailCol;
	@FXML
	private TableColumn<NhanVien, String> diaChiCol;
	@FXML
	private TableColumn<NhanVien, LocalDate> ngayVLCol;
	@FXML
	private TableColumn<NhanVien, Void> actionCol;
	@FXML
	private TextField searchTextField;
	@FXML
	private ImageView searchIconImgV;
	Image searchIcon = new Image(getClass().getResource("/asset/inActiveSearchIcon.png").toExternalForm());
	Image activeSearchIcon = new Image(getClass().getResource("/asset/activeSearchIcon.gif").toExternalForm());
	@FXML
	private ImageView clearSearchBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// init
		// connection-------------------------------------------------------------------
		try {
			conn = database.getConnection();
			stm = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// top left
		// pane---------------------------------------------------------------------
		maleNumberLabel.setText("0");
		femaleNumberLabel.setText("0");
		employeeTotalLabel.setText("0");
		displayedRowCntLabel.setText("0");
		selectedRowCntLabel.setText("0");

		maleNumber.addListener((observableVal, oldVal, newVal) -> {
			maleNumberLabel.setText(newVal.toString());
			int total = femaleNumber.add(newVal.intValue()).intValue();
			employeeTotalLabel.setText(Integer.toString(total));
		});
		femaleNumber.addListener((observableVal, oldVal, newVal) -> {
			femaleNumberLabel.setText(newVal.toString());
			int total = maleNumber.add(newVal.intValue()).intValue();
			employeeTotalLabel.setText(Integer.toString(total));

		});

		dateImg.setImage(dateIcon);
		datePane.hoverProperty().addListener((observableVal, oldVal, newVal) -> {
			if (newVal) {
				dateImg.setImage(dateIconGIF);
				dateLabel.getStyleClass().add("date-textHover");
				timeLabel.getStyleClass().add("time-textHover");
				weekDayLabel.getStyleClass().add("date-textHover");
				datePane.setPrefWidth(143);
				datePane.setPrefHeight(123);
			} else {
				dateImg.setImage(dateIcon);
				dateLabel.getStyleClass().remove("date-textHover");
				timeLabel.getStyleClass().remove("time-textHover");
				weekDayLabel.getStyleClass().remove("date-textHover");
				datePane.setPrefWidth(130);
				datePane.setPrefHeight(100);
			}
		});
		Timeline updateTime = new Timeline(new KeyFrame(Duration.seconds(0.5), (event) -> {
			date = LocalDateTime.now();
			timeLabel.setText(date.format(timeFormater));
			DayOfWeek dow = date.getDayOfWeek();
			if (dow == DayOfWeek.SUNDAY)
				weekDayLabel.setText("CN,");
			else if (dow == DayOfWeek.MONDAY)
				weekDayLabel.setText("T2,");
			else if (dow == DayOfWeek.TUESDAY)
				weekDayLabel.setText("T3");
			else if (dow == DayOfWeek.WEDNESDAY)
				weekDayLabel.setText("T4");
			else if (dow == DayOfWeek.THURSDAY)
				weekDayLabel.setText("T5");
			else if (dow == DayOfWeek.FRIDAY)
				weekDayLabel.setText("T6");
			else
				weekDayLabel.setText("T7");
			dateLabel.setText(date.format(dateFormater));
		}));
		updateTime.setCycleCount(Animation.INDEFINITE);
		updateTime.play();

		employeePane.hoverProperty().addListener((observableVal, oldVal, newVal) -> {
			if (newVal) {
				employeeLabel.getStyleClass().add("employee-textHover");
				employeeTotalLabel.getStyleClass().add("employee-textHover");
				employeeLabel.setPrefWidth(125);
				employeeLabel.setPrefHeight(75);
				employeePane.setPrefSize(155, 113);
			} else {
				employeeLabel.getStyleClass().remove("employee-textHover");
				employeeTotalLabel.getStyleClass().remove("employee-textHover");
				employeeLabel.setPrefWidth(107);
				employeeLabel.setPrefHeight(59);
				employeePane.setPrefSize(142, 100);
			}
		});

		// top right
		// pane-----------------------------------------------------------------------
		topRightPaneHBox.widthProperty().addListener((observableVal, oldVal, newVal) -> {
			topRightPaneHBox.setMargin(addEmployeeBtn,
					new Insets(0, 0, 0, 385 + ((newVal.doubleValue() > 710) ? (newVal.doubleValue() - 710) : 0)));
		});
		infoScrollPane.widthProperty().addListener((observableVal, oldVal, newVal) -> {
			infoContentPane.setPrefWidth((double) newVal - 15);
		});
		tab1Sub1.setVisible(true);
		tab1Sub2.setVisible(true);
		tab1Sub3.setVisible(true);
		tab2Sub1.setVisible(false);
		tab2Sub2.setVisible(false);
		tab2Sub3.setVisible(false);
		gioiTinhComboBox.setItems(FXCollections.observableArrayList("Nam", "Nữ"));
		ngaySinhDatePicker.setConverter(StringDateConverter);
		ngaySinhDatePicker.setPromptText("dd/MM/yyyy");
		ngaySinhDatePicker.getEditor().focusedProperty().addListener((observableVal,oldVal,newVal)->{
			if(!newVal) {
				String input = ngaySinhDatePicker.getEditor().getText();
				try {
					LocalDate.parse(input, dtf);
				}catch(Exception e) {
					showAlert(AlertType.ERROR, "Lỗi nhập xuất", "Giá trị nhập không hợp lệ (dd/MM/yyyy)");
					if(maNVTextField.isEditable())
						ngaySinhDatePicker.setValue(NHANVIEN_SHOWING.getNgaySinh());
					else {
						ngaySinhDatePicker.setValue(null);
						ngaySinhDatePicker.getEditor().setText(null);
					}
				}
			}
		});
		ngayVLDatePicker.setConverter(StringDateConverter);
		ngayVLDatePicker.setPromptText("dd/MM/yyyy");
		ngayVLDatePicker.getEditor().focusedProperty().addListener((observableVal,oldVal,newVal)->{
			if(!newVal) {
				String input = ngayVLDatePicker.getEditor().getText();
				try {
					LocalDate.parse(input, dtf);
				}catch(Exception e) {
					showAlert(AlertType.ERROR, "Lỗi nhập xuất", "Giá trị nhập không hợp lệ (dd/MM/yyyy)");
					if(maNVTextField.isEditable())
						ngayVLDatePicker.setValue(NHANVIEN_SHOWING.getNgayVL());
					else {
						ngayVLDatePicker.setValue(null);
						ngayVLDatePicker.getEditor().setText(null);
					}
				}
			}
		});
		ngayVLDatePicker.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		ngaySinhDatePicker.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		maNVTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		hoTenTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		diaChiTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		emailTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		sdtTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		usernameTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		passwordTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		ghiChuTextArea.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);

		noPhotoPane.setVisible(true);
		themHinhLabel.setVisible(false);
		addPhotoImgV.setImage(addImgIcon);
		noPhotoPane.setCursor(Cursor.HAND);
		noPhotoPane.hoverProperty().addListener((observableVal, oldVal, newVal) -> {
			if (newVal) {
				themHinhLabel.setVisible(true);
				addPhotoImgV.setFitWidth(75);
				addPhotoImgV.setFitHeight(75);
				addPhotoImgV.setX(-5);
				addPhotoImgV.setY(-10);
			} else {
				themHinhLabel.setVisible(false);
				addPhotoImgV.setFitWidth(64);
				addPhotoImgV.setFitHeight(64);
				addPhotoImgV.setX(0);
				addPhotoImgV.setY(0);
			}
		});
		avatarFileChooser.getExtensionFilters().clear();
		avatarFileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		noPhotoPane.addEventHandler(MouseEvent.MOUSE_CLICKED, editAvatarAction);

		photoPane.setVisible(false);
		System.out.println(System.getProperty("user.dir"));

		changePhotoPane.setVisible(false);
		photoPane.hoverProperty().addListener((observableVal, oldVal, newVal) -> {
			if (newVal) {
				changePhotoPane.setVisible(true);
			}
		});
		changePhotoPane.setCursor(Cursor.HAND);
		changePhotoPane.hoverProperty().addListener((observableVal, oldVal, newVal) -> {
			if (!newVal) {
				changePhotoPane.setVisible(false);
			}
		});
		changePhotoPane.addEventHandler(MouseEvent.MOUSE_CLICKED, editAvatarAction);

		maNVTextField.focusedProperty().addListener((observableVal, oldVal, newVal) -> {
			if (!newVal && maNVTextField.isEditable()) {
				NHANVIEN_SHOWING = null;
				String val = maNVTextField.getText();
				resetField();
				if (val.isBlank() || val.isEmpty() || val == null) {
					setAllFieldOff();
					maNVTextField.setText("");
				} else {
					sql = "select * from nhanvien where ma_nv = ?";
					try {
						prepareStm = conn.prepareStatement(sql);
						prepareStm.setString(1, val.trim());
						rs = prepareStm.executeQuery();
						if (rs.next()) {
							NhanVien nv = getNhanVien(rs);
							for (int i = 0; i < nvList.size(); i++) {
								if (nvList.get(i).getMaNV().equals(nv.getMaNV())) {
									NhanVien nv_ = nvList.get(i);
									nv_.clone(nv);
									nv = nv_;
									hoTenCol.setCellFactory(col -> {
										return new myTextFieldTableCell();
									});
									gioiTinhCol.setCellFactory(col -> {
										return new myComboBoxTableCell();
									});
									ngaySinhCol.setCellFactory(col -> {
										return new DatePickerTableCell();
									});
									diaChiCol.setCellFactory(col -> {
										return new myTextFieldTableCell();
									});
									sdtCol.setCellFactory(col -> {
										return new myTextFieldTableCell();
									});
									emailCol.setCellFactory(col -> {
										return new myTextFieldTableCell();
									});
									ngayVLCol.setCellFactory(col -> {
										return new DatePickerTableCell();
									});
								}
							}
							NHANVIEN_SHOWING = nv;
							fillEmployeeInfo(nv);
							maNVTextField.setText(nv.getMaNV());
						} else {
							showAlert(AlertType.ERROR, "", "Nhân viên không tồn tại");
							setAllFieldOff();
							maNVTextField.setText("");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		sdtTextField.focusedProperty().addListener((obser,oldVal,newVal)->{
			if(!newVal && maNVTextField.isEditable()) {
				if(!(sdtTextField.getText().trim()).matches(sdt_pattern)) {
					showAlert(AlertType.ERROR, "Sai định dạng SĐT", sdt_error_msg);
					sdtTextField.setText(NHANVIEN_SHOWING.getSdt());
					return;
				}
				NhanVien nv = NHANVIEN_SHOWING;
				sql = "update nhanvien set sdt = '" + sdtTextField.getText().trim() + "' where ma_nv = '" + nv.getMaNV()
						+ "'";
				try {
					stm.executeUpdate(sql);
					nv.setSdt(sdtTextField.getText().trim());
					sdtCol.setCellFactory(col -> {
						return new myTextFieldTableCell();
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(!newVal) {
				enableAddEmployeeBtn();
			}
		});
		gioiTinhComboBox.setOnAction(event->{
			if(gioiTinhComboBox.getValue() == null) 
				return;
			if(maNVTextField.isEditable() && NHANVIEN_SHOWING != null) {
				NhanVien nv = NHANVIEN_SHOWING;
				if(nvList.contains(nv)) {
					if(nv.getGioiTinh().equals("Nam") && gioiTinhComboBox.getValue().equals("Nữ")) {
						maleNumber.set(maleNumber.get()-1);
						femaleNumber.set(femaleNumber.get()+1);
					}
					if(nv.getGioiTinh().equals("Nữ") && gioiTinhComboBox.getValue().equals("Nam")) {
						maleNumber.set(maleNumber.get()+1);
						femaleNumber.set(femaleNumber.get()-1);
					}
				}
				String sql = "update nhanvien set gioi_tinh = ? where ma_nv = ?";
				try {
					prepareStm = conn.prepareStatement(sql);
					prepareStm.setString(1, gioiTinhComboBox.getValue());
					prepareStm.setString(2, nv.getMaNV());
					prepareStm.executeUpdate();
					nv.setGioiTinh(gioiTinhComboBox.getValue());
					gioiTinhCol.setCellFactory(col->{
						return new myComboBoxTableCell();
					});
				} catch (SQLException e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
			enableAddEmployeeBtn();
		});
		hoTenTextField.focusedProperty().addListener((obser,oldVal,newVal)->{
			if(!newVal && maNVTextField.isEditable()) {
				NhanVien nv = NHANVIEN_SHOWING;
				String input = hoTenTextField.getText();
				if (input.isBlank()) {
					showAlert(AlertType.ERROR, "", "Không được để trống giá trị này");
					hoTenTextField.setText(nv.getHoten());
					return;
				}
				sql = "update nhanvien set ho_ten = '" + input.trim() + "' where ma_nv = '" + nv.getMaNV()
						+ "'";
				try {
					stm.executeUpdate(sql);
					nv.setHoten(input);
					hoTenCol.setCellFactory(col -> {
						return new myTextFieldTableCell();
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(!newVal) {
				enableAddEmployeeBtn();
			}
		});
		emailTextField.focusedProperty().addListener((obser,oldVal,newVal)->{
			if(!newVal && maNVTextField.isEditable()) {
				NhanVien nv = NHANVIEN_SHOWING;
				String input = emailTextField.getText();
				if (!(input.trim()).matches(email_pattern) || input.contains("..")) {
					showAlert(AlertType.ERROR, "Sai định dạng email", email_error_msg);
					emailTextField.setText(nv.getEmail());
					return;
				}
				sql = "update nhanvien set email = '" + input.trim() + "' where ma_nv = '" + nv.getMaNV()
						+ "'";
				try {
					stm.executeUpdate(sql);
					nv.setEmail(input.trim());
					emailCol.setCellFactory(col -> {
						return new myTextFieldTableCell();
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(!newVal) {
				enableAddEmployeeBtn();
			}
		});
		diaChiTextField.focusedProperty().addListener((obser,oldVal,newVal)->{
			if(!newVal && maNVTextField.isEditable()) {
				NhanVien nv = NHANVIEN_SHOWING;
				String input = diaChiTextField.getText();
				if (input.isBlank()) {
					showAlert(AlertType.ERROR, "", "Không được để trống giá trị này");
					diaChiTextField.setText(nv.getDiaChi());
					return;
				}
				sql = "update nhanvien set dia_chi = '" + input.trim() + "' where ma_nv = '"
						+ nv.getMaNV() + "'";
				try {
					stm.executeUpdate(sql);
					nv.setDiaChi(input.trim());
					diaChiCol.setCellFactory(col -> {
						return new myTextFieldTableCell();
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(!newVal) {
				enableAddEmployeeBtn();
			}
		});
		ngaySinhDatePicker.setOnAction(event->{
			if(ngaySinhDatePicker.getValue() == null) 
				return;
			if(maNVTextField.isEditable() && NHANVIEN_SHOWING != null) {
				NhanVien nv = NHANVIEN_SHOWING;
				LocalDate date = ngaySinhDatePicker.getValue();
				if(date.isEqual(nv.getNgaySinh())) {
					ngaySinhDatePicker.setValue(nv.getNgaySinh());
					ngaySinhDatePicker.getEditor().setText(nv.getNgaySinh().format(dtf));
					return;
				}
				if(date.isAfter(nv.getNgayVL())) {
					showAlert(AlertType.ERROR, "Ngày sinh không hợp lệ", "Ngày sinh không được sau ngày vào làm");
					ngaySinhDatePicker.setValue(nv.getNgaySinh());
					ngaySinhDatePicker.getEditor().setText(nv.getNgaySinh().format(dtf));
					return;
				}
				sql = "update nhanvien set ngay_sinh = ? where ma_nv = ?";
				try {
					prepareStm = conn.prepareStatement(sql);
					prepareStm.setDate(1, Date.valueOf(date));
					prepareStm.setString(2, nv.getMaNV());
					prepareStm.executeUpdate();
					nv.setNgaySinh(date);
					ngaySinhCol.setCellFactory(col -> {
						return new DatePickerTableCell();
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			enableAddEmployeeBtn();
		});
		ngayVLDatePicker.setOnAction(event->{
			if(ngayVLDatePicker.getValue() == null)
				return;
			if(maNVTextField.isEditable() && NHANVIEN_SHOWING != null) {
				NhanVien nv = NHANVIEN_SHOWING;
				LocalDate date = ngayVLDatePicker.getValue();
				if(date.isEqual(nv.getNgayVL())) {
					ngayVLDatePicker.setValue(nv.getNgayVL());
					ngayVLDatePicker.getEditor().setText(nv.getNgayVL().format(dtf));
					return;
				}
				if(date.isBefore(nv.getNgaySinh())) {
					showAlert(AlertType.ERROR, "Ngày vào làm không hợp lệ", "Ngày vào làm không được sớm hơn ngày sinh");
					ngayVLDatePicker.setValue(nv.getNgayVL());
					ngayVLDatePicker.getEditor().setText(nv.getNgayVL().format(dtf));
					return;
				}
				sql = "update nhanvien set ngay_vl = ? where ma_nv = ?";
				try {
					prepareStm = conn.prepareStatement(sql);
					prepareStm.setDate(1, Date.valueOf(date));
					prepareStm.setString(2, nv.getMaNV());
					prepareStm.executeUpdate();
					nv.setNgayVL(date);
					ngayVLCol.setCellFactory(col -> {
						return new DatePickerTableCell();
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			enableAddEmployeeBtn();
		});
		ghiChuTextArea.focusedProperty().addListener((obser,oldVal,newVal)->{
			if(maNVTextField.isEditable() && !newVal) {
				NhanVien nv = NHANVIEN_SHOWING;
				sql = "update nhanvien set ghi_chu = ? where ma_nv = ?";
				try {
					prepareStm = conn.prepareStatement(sql);
					prepareStm.setString(1, ghiChuTextArea.getText());
					prepareStm.setString(2, nv.getMaNV());
					prepareStm.executeUpdate();
					nv.setGhiChu(ghiChuTextArea.getText());
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		usernameTextField.focusedProperty().addListener((obser,oldVal,newVal)->{
			if(!newVal && maNVTextField.isEditable()) {
				NhanVien nv = NHANVIEN_SHOWING;
				String in = usernameTextField.getText();
				if(in.isBlank()) {
					showAlert(AlertType.ERROR, "", "Không được để trống giá trị này");
					usernameTextField.setText(nv.getUsername());
					return;
				}
				sql = "select * from nhanvien where username = ? and ma_nv != ?";
				try {
					prepareStm = conn.prepareStatement(sql);
					prepareStm.setString(1, in);
					prepareStm.setString(2, nv.getMaNV());
					rs = prepareStm.executeQuery();
					if(rs.next()) {
						showAlert(AlertType.ERROR, "", "Tên đăng nhập đã tồn tại");
						usernameTextField.setText(nv.getUsername());
					}else {
						sql = "update nhanvien set username = ? where ma_nv = ?";
						prepareStm = conn.prepareStatement(sql);
						prepareStm.setString(1, in);
						prepareStm.setString(2, nv.getMaNV());
						prepareStm.executeUpdate();
						nv.setUsername(in);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(!newVal) {
				enableAddEmployeeBtn();
			}
		});
		passwordTextField.focusedProperty().addListener((obser,oldVal,newVal)->{
			if(!newVal && maNVTextField.isEditable()) {
				NhanVien nv = NHANVIEN_SHOWING;
				String in = passwordTextField.getText();
				if(in.isBlank()) {
					showAlert(AlertType.ERROR, "", "Không được để trống giá trị này");
					passwordTextField.setText(nv.getPassword());
					return;
				}
				sql = "update nhanvien set password = ? where ma_nv = ?";
				try {
					prepareStm = conn.prepareStatement(sql);
					prepareStm.setString(1, in);
					prepareStm.setString(2, nv.getMaNV());
					prepareStm.executeUpdate();
					nv.setPassword(in);
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}else if(!newVal) {
				enableAddEmployeeBtn();
			}
		});
		
		setAllFieldOff();
		maNVTextField.setEditable(true);
		
		addEmployeeBtn.setVisible(false);
		addEmployeeBtn.setDisable(true);
		addEmployeeBtn.setOnAction(event->{
			String maNV = maNVTextField.getText();
			String hoten = hoTenTextField.getText();
			String gioiTinh = gioiTinhComboBox.getValue();
			LocalDate ngaySinh = ngaySinhDatePicker.getValue();
			String diaChi = diaChiTextField.getText();
			String sdt = sdtTextField.getText();
			String email = emailTextField.getText();
			String ghiChu = ghiChuTextArea.getText();
			LocalDate ngayVL = ngayVLDatePicker.getValue();
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			String maLND = "LND002";
			if (!sdt.matches(sdt_pattern)) {
				showAlert(AlertType.ERROR, "Sai định dạng SĐT", sdt_error_msg);
				return;
			}
			if (!email.matches(email_pattern) || email.contains("..")) {
				showAlert(AlertType.ERROR, "Sai định dạng email", email_error_msg);
				return;
			}
			sql = "select * from nhanvien where username = ?";
			try {
				prepareStm = conn.prepareStatement(sql);
				prepareStm.setString(1, username);
				rs = prepareStm.executeQuery();
				if(rs.next()) {
					showAlert(AlertType.ERROR, "", "Tên đăng nhập đã tồn tại");
					return;
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			if(ngaySinh.isAfter(ngayVL)) {
				showAlert(AlertType.ERROR, "","Ngày vào làm phải sau ngày sinh");
				return;
			}
			File file = avatarFile; avatarFile = null;
			NhanVien nv = new NhanVien(maNV, hoten, gioiTinh, ngaySinh, diaChi, sdt, email, ghiChu, ngayVL, username, password, maLND, file);
			sql = "insert into nhanvien values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			FileInputStream fis = null;
			try {
				if(file != null)
					fis = new FileInputStream(file);
				prepareStm = conn.prepareStatement(sql);
				prepareStm.setString(1, maNV);
				prepareStm.setString(2, hoten);
				prepareStm.setString(3, gioiTinh);
				prepareStm.setDate(4, Date.valueOf(ngaySinh));
				prepareStm.setString(5, diaChi);
				prepareStm.setString(6, sdt);
				prepareStm.setString(7, email);
				prepareStm.setString(8, ghiChu);
				prepareStm.setDate(9, Date.valueOf(ngayVL));
				prepareStm.setString(10,username);
				prepareStm.setString(11, password);
				prepareStm.setString(12, maLND);
				if(file != null)
					prepareStm.setBinaryStream(13, (InputStream) fis, fis.available());
				else 
					prepareStm.setBinaryStream(13, null);
				prepareStm.executeUpdate();
				showAlert(AlertType.INFORMATION, "", "Thêm mới thành công");
				nvList.add(nv);
				if(nv.getGioiTinh().equals("Nam")) 
					maleNumber.set(maleNumber.get()+1);
				else
					femaleNumber.set(femaleNumber.get()+1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			resetField();
			sql = "{ ? = call func_nhanvien_get_id}";
			String newMaNV = null;
			try {
				CallableStatement call = conn.prepareCall(sql);
				call.registerOutParameter(1, Types.NVARCHAR);
				call.execute();
				newMaNV = call.getNString(1);
				maNVTextField.setText(newMaNV);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		});

		// Nhan Vien Table------------------------------------------------------------------
//		search table
		filteredList.addListener((ListChangeListener.Change<? extends NhanVien> change) -> {
			displayedRowCntLabel.setText(Integer.toString(filteredList.size()));
		});
		searchTextField.textProperty().addListener((observableVal, oldVal, newVal) -> {
			if (newVal != null && !newVal.isEmpty()) {
				clearSearchBtn.setVisible(true);
			} else {
				clearSearchBtn.setVisible(false);
			}

			tsm.clearSelection();
			((CheckBox) tickCol.getGraphic()).setSelected(false);
			filteredList.setPredicate(nhanvien -> {
				if (newVal.isEmpty() || newVal == null || newVal.isBlank()) {
					return true;
				}

				String searchKeyword = newVal.toLowerCase();
				if (nhanvien.getMaNV() != null && nhanvien.getMaNV().toLowerCase().indexOf(searchKeyword) > -1)
					return true;
				if (nhanvien.getHoten() != null && nhanvien.getHoten().toLowerCase().indexOf(searchKeyword) > -1)
					return true;
				if (nhanvien.getGioiTinh() != null && nhanvien.getGioiTinh().toLowerCase().indexOf(searchKeyword) > -1)
					return true;
				if (nhanvien.getNgaySinh() != null
						&& nhanvien.getNgaySinh().format(dtf).toLowerCase().indexOf(searchKeyword) > -1)
					return true;
				if (nhanvien.getDiaChi() != null && nhanvien.getDiaChi().toLowerCase().indexOf(searchKeyword) > -1)
					return true;
				if (nhanvien.getSdt() != null && nhanvien.getSdt().toLowerCase().indexOf(searchKeyword) > -1)
					return true;
				if (nhanvien.getEmail() != null && nhanvien.getEmail().toLowerCase().indexOf(searchKeyword) > -1)
					return true;
//				if(nhanvien.getGhiChu() != null && nhanvien.getGhiChu().toLowerCase().indexOf(searchKeyword) > -1) 
//					return true;
				if (nhanvien.getNgayVL() != null
						&& nhanvien.getNgayVL().format(dtf).toLowerCase().indexOf(searchKeyword) > -1)
					return true;

				return false;
			});
		});
		employeeTable.setItems(filteredList);

		searchTextField
				.setFont(Font.loadFont(getClass().getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm(), 13));
		searchTextField.addEventHandler(KeyEvent.KEY_PRESSED, enterHandlerTextField);
		searchIconImgV.setImage(searchIcon);
		searchTextField.focusedProperty().addListener((observableVal, oldVal, newVal) -> {
			if (newVal) {
				searchIconImgV.setImage(activeSearchIcon);
			} else {
				searchIconImgV.setImage(searchIcon);
			}
		});

		clearSearchBtn.setVisible(false);
		clearSearchBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			if (event.getButton().equals(MouseButton.PRIMARY)) {
				searchTextField.clear();
			}
		});

//		table selection model
		tsm = employeeTable.getSelectionModel();
		tsm.getSelectedIndices().addListener((ListChangeListener.Change<? extends Integer> change) -> {
			change.next();
			System.out.println(change);
			System.out.println("selected indices: " + tsm.getSelectedIndices());
			System.out.println("add: " + change.getAddedSubList());
			System.out.println("remove: " + change.getRemoved());

		});
		tsm.getSelectedItems().addListener((ListChangeListener.Change<? extends NhanVien> change) -> {
			change.next();
			for (NhanVien nv : change.getAddedSubList()) {
				nv.getCheckBox().setSelected(true);
			}
			for (NhanVien nv : change.getRemoved()) {
				nv.getCheckBox().setSelected(false);
			}
			if(change.getAddedSubList().size() > 0) {
				NHANVIEN_SHOWING = change.getAddedSubList().get(0);
				if(maNVTextField.isEditable())
					fillEmployeeInfo(NHANVIEN_SHOWING);
			}
			if(change.getRemovedSize() > 0 && NHANVIEN_SHOWING != null && change.getRemoved().contains(NHANVIEN_SHOWING)) {
				NHANVIEN_SHOWING = null;
				if(maNVTextField.isEditable())
					resetField();
			}

			if (tsm.getSelectedItems().size() == filteredList.size() && filteredList.size() != 0)
				((CheckBox) tickCol.getGraphic()).setSelected(true);
			else
				((CheckBox) tickCol.getGraphic()).setSelected(false);

			selectedRowCntLabel.setText(Integer.toString(tsm.getSelectedItems().size()));
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
		VBox placeHolderVBox = new VBox();
		ImageView placeHolderImgV = new ImageView(
				new Image(getClass().getResource("/asset/userNotFoundIcon.png").toExternalForm()));
		Label placeHolderLabel = new Label("Không tìm thấy dữ liệu nhân viên");
		placeHolderLabel.setContentDisplay(ContentDisplay.TEXT_ONLY);
		placeHolderLabel.setTextAlignment(TextAlignment.CENTER);
		placeHolderLabel.setFont(
				Font.loadFont(this.getClass().getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm(), 16));
//		System.out.println(getClass().getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm());
		placeHolderVBox.getChildren().addAll(placeHolderImgV, placeHolderLabel);
		placeHolderVBox.setAlignment(Pos.CENTER);
		placeHolderVBox.setMargin(placeHolderLabel, new Insets(20));
		employeeTable.setPlaceholder(placeHolderVBox);
		employeeTable.setEditable(true);
		tickCol.setReorderable(false);
		tickCol.setSortable(false);
		tickCol.setGraphic(new CheckBox());
		CheckBox tickColCheckBox = (CheckBox) tickCol.getGraphic();
		tickColCheckBox.setOnAction((event) -> {
			if (tickColCheckBox.isSelected()) {
				for (NhanVien nv : filteredList) {
					if (!(tsm.getSelectedItems().contains(nv))) {
						tsm.select(nv);
					}
				}
			} else {
				tsm.clearSelection();
			}
		});
		tickCol.setResizable(false);
		tickCol.setMinWidth(42);
		tickCol.setMaxWidth(42);
		tickCol.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
		tickCol.setCellFactory(col -> {
			return new TableCell<NhanVien, CheckBox>() {

				@Override
				protected void updateItem(CheckBox item, boolean empty) {
					super.updateItem(item, empty);
					this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					this.setAlignment(Pos.BASELINE_CENTER);
					if (!empty) {
						this.setGraphic(item);
					} else {
						this.setGraphic(null);
					}

				}
			};
		});
		employeeTable.getColumns().add(0, tickCol);
		maNVCol.setMinWidth(70);
		maNVCol.setMaxWidth(70);
		maNVCol.setResizable(false);
		maNVCol.getStyleClass().add("my-column-header-style");
		maNVCol.setCellValueFactory(new PropertyValueFactory<>("maNV"));
		maNVCol.setCellFactory(col -> {
			TableCell<NhanVien, String> cell = new TableCell<>() {

				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					this.setFont(Font
							.loadFont(getClass().getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm(), 13));
					TableRow<NhanVien> row = this.getTableRow();
					row.hoverProperty().addListener((observableVal, oldVal, newVal) -> {
						if (newVal) {
							this.setFont(Font.loadFont(
									getClass().getResource("/asset/BeVietnamPro-Bold.ttf").toExternalForm(), 14));
						} else {
							this.setFont(Font.loadFont(
									getClass().getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm(), 13));
						}
					});
					this.setContentDisplay(ContentDisplay.TEXT_ONLY);
					if (empty) {
						this.setText(null);
					} else {
						this.setTextAlignment(TextAlignment.LEFT);
						this.setPadding(new Insets(15, 0, 0, 0));
						this.setText(item);
					}
				}

			};
			return cell;
		});
		maNVCol.setEditable(false);
		hoTenCol.setMinWidth(160);
		hoTenCol.getStyleClass().add("my-column-header-style");
		hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoten"));
		hoTenCol.setCellFactory(col -> {
			return new myTextFieldTableCell();
		});
		hoTenCol.setOnEditCommit(editEvent -> {
			root.requestFocus();
			if (editEvent.getNewValue().isBlank()) {
				showAlert(AlertType.ERROR, "", "Không được để trống giá trị này");
				hoTenCol.setCellFactory(col -> {
					return new myTextFieldTableCell();
				});
				return;
			}
			NhanVien nv = editEvent.getRowValue();
			sql = "update nhanvien set ho_ten = '" + editEvent.getNewValue().trim() + "' where ma_nv = '" + nv.getMaNV()
					+ "'";
			try {
				stm.executeUpdate(sql);
				nv.setHoten(editEvent.getNewValue().trim());
				hoTenCol.setCellFactory(col -> {
					return new myTextFieldTableCell();
				});
				if (maNVTextField.isEditable() && (maNVTextField.getText()).equals(nv.getMaNV())) {
					hoTenTextField.setText(editEvent.getNewValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		gioiTinhCol.setMinWidth(75);
		gioiTinhCol.getStyleClass().add("my-column-header-style");
		gioiTinhCol.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
		gioiTinhCol.setCellFactory(col -> {
			return new myComboBoxTableCell();
		});
		gioiTinhCol.setOnEditCommit(editEvent -> {
			if(editEvent.getNewValue().equals("Nam")) {
				maleNumber.set(maleNumber.get()+1);
			}else {
				femaleNumber.set(femaleNumber.get()+1);
			}
			if(editEvent.getOldValue().equals("Nam")) {
				maleNumber.set(maleNumber.get()-1);
			}else {
				femaleNumber.set(femaleNumber.get()-1);
			}
			root.requestFocus();
			NhanVien nv = editEvent.getRowValue();
			sql = "update nhanvien set gioi_tinh = ? where ma_nv = ?";
			try {
				prepareStm = conn.prepareStatement(sql);
				prepareStm.setString(1, editEvent.getNewValue());
				prepareStm.setString(2, nv.getMaNV());
				prepareStm.executeUpdate();
				nv.setGioiTinh(editEvent.getNewValue());
				if (maNVTextField.isEditable() && (maNVTextField.getText()).equals(nv.getMaNV())) {
					gioiTinhComboBox.setValue(editEvent.getNewValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		ngaySinhCol.setMinWidth(110);
		ngaySinhCol.getStyleClass().add("my-column-header-style");
		ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
		ngaySinhCol.setCellFactory(col -> {
			return new DatePickerTableCell();
		});
		ngaySinhCol.setOnEditCommit(editEvent -> {
			root.requestFocus();
			NhanVien nv = editEvent.getRowValue();
			sql = "update nhanvien set ngay_sinh = ? where ma_nv = ?";
			try {
				prepareStm = conn.prepareStatement(sql);
				prepareStm.setDate(1, Date.valueOf(editEvent.getNewValue()));
				prepareStm.setString(2, nv.getMaNV());
				prepareStm.executeUpdate();
				nv.setNgaySinh(editEvent.getNewValue());
				if (maNVTextField.isEditable() && (maNVTextField.getText()).equals(nv.getMaNV())) {
					ngaySinhDatePicker.setValue(editEvent.getNewValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		diaChiCol.setMinWidth(175);
		diaChiCol.getStyleClass().add("my-column-header-style");
		diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		diaChiCol.setCellFactory(col -> {
			return new myTextFieldTableCell();
		});
		diaChiCol.setOnEditCommit(editEvent -> {
			root.requestFocus();
			if (editEvent.getNewValue().isBlank()) {
				showAlert(AlertType.ERROR, "", "Không được để trống giá trị này");
				diaChiCol.setCellFactory(col -> {
					return new myTextFieldTableCell();
				});
				return;
			}
			NhanVien nv = editEvent.getRowValue();
			sql = "update nhanvien set dia_chi = '" + editEvent.getNewValue().trim() + "' where ma_nv = '"
					+ nv.getMaNV() + "'";
			try {
				stm.executeUpdate(sql);
				nv.setDiaChi(editEvent.getNewValue().trim());
				diaChiCol.setCellFactory(col -> {
					return new myTextFieldTableCell();
				});
				if (maNVTextField.isEditable() && (maNVTextField.getText()).equals(nv.getMaNV())) {
					diaChiTextField.setText(editEvent.getNewValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		sdtCol.setMinWidth(75);
		sdtCol.getStyleClass().add("my-column-header-style");
		sdtCol.setCellValueFactory(new PropertyValueFactory<>("sdt"));
		sdtCol.setCellFactory(col -> {
			return new myTextFieldTableCell();
		});
		sdtCol.setOnEditCommit(editEvent -> {
			root.requestFocus();
			// Kiem tra dinh dang sdt
			if (!(editEvent.getNewValue().trim()).matches(sdt_pattern)) {
				showAlert(AlertType.ERROR, "Sai định dạng SĐT", sdt_error_msg);
				sdtCol.setCellFactory(col -> {
					return new myTextFieldTableCell();
				});
				return;
			}
			NhanVien nv = editEvent.getRowValue();
			sql = "update nhanvien set sdt = '" + editEvent.getNewValue().trim() + "' where ma_nv = '" + nv.getMaNV()
					+ "'";
			try {
				stm.executeUpdate(sql);
				nv.setSdt(editEvent.getNewValue().trim());
				sdtCol.setCellFactory(col -> {
					return new myTextFieldTableCell();
				});
				if (maNVTextField.isEditable() && (maNVTextField.getText()).equals(nv.getMaNV())) {
					sdtTextField.setText(editEvent.getNewValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		emailCol.setMinWidth(160);
		emailCol.getStyleClass().add("my-column-header-style");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		emailCol.setCellFactory(col -> {
			return new myTextFieldTableCell();
		});
		emailCol.setOnEditCommit(editEvent -> {
			root.requestFocus();
			// Kiem tra dinh dang email
			if (!(editEvent.getNewValue().trim()).matches(email_pattern) || editEvent.getNewValue().contains("..")) {
				showAlert(AlertType.ERROR, "Sai định dạng email", email_error_msg);
				emailCol.setCellFactory(col -> {
					return new myTextFieldTableCell();
				});
				return;
			}
			NhanVien nv = editEvent.getRowValue();
			sql = "update nhanvien set email = '" + editEvent.getNewValue().trim() + "' where ma_nv = '" + nv.getMaNV()
					+ "'";
			try {
				stm.executeUpdate(sql);
				nv.setEmail(editEvent.getNewValue().trim());
				emailCol.setCellFactory(col -> {
					return new myTextFieldTableCell();
				});
				if (maNVTextField.isEditable() && (maNVTextField.getText()).equals(nv.getMaNV())) {
					emailTextField.setText(editEvent.getNewValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		ngayVLCol.setMinWidth(110);
		ngayVLCol.getStyleClass().add("my-column-header-style");
		ngayVLCol.setCellValueFactory(new PropertyValueFactory<>("ngayVL"));
		ngayVLCol.setCellFactory(col -> {
			return new DatePickerTableCell();
		});
		ngayVLCol.setOnEditCommit(editEvent -> {
			root.requestFocus();
			NhanVien nv = editEvent.getRowValue();
			sql = "update nhanvien set ngay_vl = ? where ma_nv = ?";
			try {
				prepareStm = conn.prepareStatement(sql);
				prepareStm.setDate(1, Date.valueOf(editEvent.getNewValue()));
				prepareStm.setString(2, nv.getMaNV());
				prepareStm.executeUpdate();
				nv.setNgayVL(editEvent.getNewValue());
				if (maNVTextField.isEditable() && (maNVTextField.getText()).equals(nv.getMaNV())) {
					ngayVLDatePicker.setValue(editEvent.getNewValue());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		actionCol.setReorderable(false);
		actionCol.setSortable(false);
		actionCol.getStyleClass().add("my-column-header-style");
		actionCol.setMaxWidth(70);
		actionCol.setMinWidth(70);
		actionCol.setResizable(false);
		actionCol.setCellFactory(col -> {
			return new TableCell<NhanVien, Void>() {
				HBox hbox = new HBox();
				{
					hbox.setAlignment(Pos.CENTER_LEFT);
					hbox.setSpacing(15);
					hbox.setPadding(new Insets(3));
				}
				// button
				{
					JFXButton removeBtn = new JFXButton(null);
					removeBtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					ImageView imgV = new ImageView(
							new Image(getClass().getResource("/asset/removeUserIcon.png").toExternalForm()));
					removeBtn.setGraphic(imgV);
					imgV.setPreserveRatio(true);
					imgV.setFitHeight(30);
					removeBtn.getStyleClass().add("removeUserButton");
					removeBtn.setOnAction(event -> {
						String selectMaNV = this.getTableRow().getItem().getMaNV();
						ArrayList<NhanVien> removedNV = new ArrayList<>();
						removedNV.add(this.getTableRow().getItem());
						for (NhanVien nv : tsm.getSelectedItems()) {
							if (this.getTableRow().getItem().getMaNV() != nv.getMaNV()) {
								selectMaNV += ", " + nv.getMaNV();
								removedNV.add(nv);
							}
						}
						Optional<ButtonType> option = showAlert(AlertType.CONFIRMATION, "Xác nhận xoá nhân viên",
								"Bạn chắc chắn muốn xoá nhân viên " + selectMaNV + "?");
						if (option.get() == ButtonType.CANCEL)
							return;
						tsm.clearSelection();
						sql = "delete from nhanvien where ma_nv = ?";
						for (NhanVien nv : removedNV) {
							try {
								prepareStm = conn.prepareStatement(sql);
								prepareStm.setString(1, nv.getMaNV());
								prepareStm.executeUpdate();
								nvList.remove(nv);
								if (nv.getGioiTinh().equals("Nam")) {
									maleNumber.set(maleNumber.get() - 1);
								} else {
									femaleNumber.set(femaleNumber.get() - 1);
								}
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
					if (!empty) {
						this.setGraphic(hbox);
						this.setText(null);
					}
				}
			};
		});

		refreshTable(); // fetch data into table for first time
		root.requestFocus();
	}
	// end init

	@FXML
	public void switchTab(MouseEvent event) {
		if (event.getButton() != MouseButton.PRIMARY)
			return;
		Node tab = (Node) event.getSource();
		if (tab == tab1) {
			if (tab1.getStyleClass().contains("top-right-pane-tab1-active"))
				return;
			root.requestFocus();
			maNVTextField.setEditable(true);
			addEmployeeBtn.setVisible(false);
			addEmployeeBtn.setDisable(true);
			tab1.getStyleClass().clear();
			tab1.getStyleClass().add("top-right-pane-tab1-active");
			tab2.getStyleClass().clear();
			tab2.getStyleClass().add("top-right-pane-tab-inactive");
			tab2Sub1.setVisible(false);
			tab2Sub2.setVisible(false);
			tab2Sub3.setVisible(false);
			tab1Sub1.setVisible(true);
			tab1Sub2.setVisible(true);
			tab1Sub3.setVisible(true);
			infoBackgroundPane.setStyle(infoBackgroundPaneCSSTab1);
			infoContentPane.setStyle(infoContentPaneCSSTab1);

//			fetch data from tableview into textfield
			if (NHANVIEN_SHOWING == null) {
				resetField();
			} else {
				fillEmployeeInfo(NHANVIEN_SHOWING);
			}
		} else if (tab == tab2) {
			if (tab2.getStyleClass().contains("top-right-pane-tab2-active"))
				return;
			root.requestFocus();
			maNVTextField.setEditable(false);
			addEmployeeBtn.setVisible(true);
			addEmployeeBtn.setDisable(true);
			tab2.getStyleClass().clear();
			tab2.getStyleClass().add("top-right-pane-tab2-active");
			tab1.getStyleClass().clear();
			tab1.getStyleClass().add("top-right-pane-tab-inactive");
			tab1Sub1.setVisible(false);
			tab1Sub2.setVisible(false);
			tab1Sub3.setVisible(false);
			tab2Sub1.setVisible(true);
			tab2Sub2.setVisible(true);
			tab2Sub3.setVisible(true);
			infoBackgroundPane.setStyle(infoBackgroundPaneCSSTab2);
			infoContentPane.setStyle(infoContentPaneCSSTab2);

			resetField();
			/* set maNV automatically ... */
			sql = "{? = call func_nhanvien_get_id}";
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
		}
	}

	void refreshTable() {
		maleNumber.set(0); femaleNumber.set(0);
		nvList.clear();
		tsm.clearSelection();
		sql = "select * from nhanvien order by ma_nv asc";
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				NhanVien nv = getNhanVien(rs);
				nv.getCheckBox().setOnAction(event -> {
					System.out.println("checkBox of " + nv.getMaNV() + ": " + nv.getCheckBox().isSelected());
					if (nv.getCheckBox().isSelected())
						tsm.select(nv);
					else
						tsm.clearSelection(filteredList.indexOf(nv));
				});
				nvList.add(nv);
				if (nv.getGioiTinh().equals("Nam")) {
					maleNumber.set(maleNumber.get() + 1);
				} else {
					femaleNumber.set(femaleNumber.get() + 1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void refreshBtnAction(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			refreshTable();
			showAlert(AlertType.INFORMATION, "Refresh table", "Dữ liệu đã được làm mới!");
		}
	}

	Optional<ButtonType> showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initStyle(StageStyle.DECORATED);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.initOwner(root.getScene().getWindow());
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
		avatarFile = null;
		photoImageView.setImage(null);
		photoPane.setVisible(false);
		noPhotoPane.setVisible(true);
		if (maNVTextField.isEditable()) // tab1
			setAllFieldOff();
		else
			setAllFieldOn();
	}

	public void fillEmployeeInfo(NhanVien nv) {
		if (nv == null)
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
		File avatar = nv.getAvatarFile();
		if (avatar != null) {
			noPhotoPane.setVisible(false);
			photoPane.setVisible(true);
			Image img = new Image(avatar.toURI().toString(), avatarWidth, avatarHeight, false, false);
			photoImageView.setImage(img);
		} else {
			avatarFile = null;
			photoPane.setVisible(false);
			noPhotoPane.setVisible(true);
			photoImageView.setImage(null);
		}
		setAllFieldOn();
	}

	public void exitFocused(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY)
			root.requestFocus();
	}

	class myTextFieldTableCell extends TextFieldTableCell<NhanVien, String> {
		public myTextFieldTableCell() {
			super(new StringConverter<String>() {

				@Override
				public String fromString(String arg0) {
					if (arg0 == null)
						return null;
					return arg0;
				}

				@Override
				public String toString(String arg0) {
					return arg0;
				}

			});
			this.setFont(Font.loadFont(getClass().getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm(), 13));
		}

		@Override
		public void updateItem(String arg0, boolean arg1) {
			super.updateItem(arg0, arg1);
			TableRow<NhanVien> row = this.getTableRow();
			row.hoverProperty().addListener((observableVal, oldVal, newVal) -> {
				if (newVal) {
					this.setFont(
							Font.loadFont(getClass().getResource("/asset/BeVietnamPro-Bold.ttf").toExternalForm(), 14));
				} else {
					this.setFont(Font
							.loadFont(getClass().getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm(), 13));
				}
			});
			this.setTextAlignment(TextAlignment.LEFT);
			this.setPadding(new Insets(15, 0, 0, 0));
		}
	}

	class myComboBoxTableCell extends ComboBoxTableCell<NhanVien, String> {
		public myComboBoxTableCell() {
			super("Nam", "Nữ");
			this.setFont(Font.loadFont(getClass().getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm(), 13));
		}

		@Override
		public void cancelEdit() {
			// TODO Auto-generated method stub
			super.cancelEdit();
		}

		@Override
		public ObservableList<String> getItems() {
			// TODO Auto-generated method stub
			return super.getItems();
		}

		@Override
		public void startEdit() {
			// TODO Auto-generated method stub
			super.startEdit();
		}

		@Override
		public void updateItem(String arg0, boolean arg1) {
			TableRow<NhanVien> row = this.getTableRow();
			row.hoverProperty().addListener((observableVal, oldVal, newVal) -> {
				if (newVal) {
					this.setFont(
							Font.loadFont(getClass().getResource("/asset/BeVietnamPro-Bold.ttf").toExternalForm(), 14));
				} else {
					this.setFont(Font
							.loadFont(getClass().getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm(), 13));
				}
			});
			super.updateItem(arg0, arg1);
			this.setTextAlignment(TextAlignment.LEFT);
			this.setPadding(new Insets(15, 0, 0, 0));
		}
	}

	EventHandler<MouseEvent> editAvatarAction = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (!event.getButton().equals(MouseButton.PRIMARY))
				return;
			Stage stg = (Stage) (root.getScene().getWindow());
			avatarFile = avatarFileChooser.showOpenDialog(stg);
			if (avatarFile != null) {
				noPhotoPane.setVisible(false);
				photoPane.setVisible(true);
				Image avatar = new Image(avatarFile.toURI().toString(), avatarWidth, avatarHeight, false, false);
				photoImageView.setImage(avatar);

				if (maNVTextField.isEditable()) { // tab1
					sql = "update nhanvien set avatar = ? where ma_nv = ?";
					try {
						prepareStm = conn.prepareStatement(sql);
						FileInputStream fis = new FileInputStream(avatarFile);
						try {
							prepareStm.setBinaryStream(1, (InputStream) fis, fis.available());
							prepareStm.setString(2, maNVTextField.getText());
							prepareStm.executeUpdate();
							if (NHANVIEN_SHOWING != null) {
								NhanVien nv = NHANVIEN_SHOWING;
								nv.setAvatarFile(avatarFile);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					avatarFile = null;
				}
			} else {
				showAlert(AlertType.ERROR, "", "Mở file thất bại");
			}
		}
	};

	public void setAllFieldOn() {
		disableAddPhotoPane.setVisible(false);
		noPhotoPane.setDisable(false);
		hoTenTextField.setDisable(false);
		gioiTinhComboBox.setDisable(false);
		diaChiTextField.setDisable(false);
		emailTextField.setDisable(false);
		sdtTextField.setDisable(false);
		ngaySinhDatePicker.setDisable(false);
		ngayVLDatePicker.setDisable(false);
		usernameTextField.setDisable(false);
		passwordTextField.setDisable(false);
		ghiChuTextArea.setDisable(false);
	}

	public void setAllFieldOff() {
		disableAddPhotoPane.setVisible(true);
		noPhotoPane.setDisable(true);
		hoTenTextField.setDisable(true);
		gioiTinhComboBox.setDisable(true);
		diaChiTextField.setDisable(true);
		emailTextField.setDisable(true);
		sdtTextField.setDisable(true);
		ngaySinhDatePicker.setDisable(true);
		ngayVLDatePicker.setDisable(true);
		usernameTextField.setDisable(true);
		passwordTextField.setDisable(true);
		ghiChuTextArea.setDisable(true);
	}

	NhanVien getNhanVien(ResultSet rs) {
		NhanVien nv = null;
		try {
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
			String maLND = rs.getString("ma_lnd");
			Blob avatar = rs.getBlob("avatar");
			File file = null;
			if (avatar != null) {
				byte[] byteArr = avatar.getBytes(1, (int) avatar.length());
				FileOutputStream fos = null;
				file = new File(System.getProperty("user.dir") + "/src/asset/" + maNV + "Avatar.png");
				try {
					fos = new FileOutputStream(file);
					fos.write(byteArr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			nv = new NhanVien(maNV, hoten, gioiTinh, ngaySinh, diaChi, sdt, email, ghiChu, ngayVL, username, password,
					maLND, file);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return nv;
	}
	
	void enableAddEmployeeBtn() {
		if(maNVTextField.isEditable())
			return;
		String hoten = hoTenTextField.getText();
		String gioiTinh = gioiTinhComboBox.getValue();
		LocalDate ngaySinh = ngaySinhDatePicker.getValue();
		String diaChi = diaChiTextField.getText();
		String sdt = sdtTextField.getText();
		String email = emailTextField.getText();
		LocalDate ngayVL = ngayVLDatePicker.getValue();
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		if(hoten.isBlank() || gioiTinh == null || gioiTinh.isEmpty() || ngaySinh == null
				|| diaChi.isBlank() || sdt.isEmpty() || email.isEmpty() || ngayVL == null
				|| username.isBlank() || password.isBlank()) {
			addEmployeeBtn.setDisable(true);
		}else {
			addEmployeeBtn.setDisable(false);
		}
	}
	
}
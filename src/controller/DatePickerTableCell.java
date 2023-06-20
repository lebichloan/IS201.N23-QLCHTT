package controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import model.NhanVien;

public class DatePickerTableCell extends TableCell<NhanVien,LocalDate> {
	
	class dateStringConverter extends StringConverter<LocalDate> {
		DateTimeFormatter df;
		
		public dateStringConverter(DateTimeFormatter df) {
			this.df = df;
		}
		
		@Override
		public LocalDate fromString(String arg0) {
			System.out.println("fromString" + arg0);
			if(arg0 == null) return null;
			LocalDate date;
			try {
				date = LocalDate.parse(arg0, df);
			}catch(DateTimeParseException e) {
				return null;
			}
			return date;
		}

		@Override
		public String toString(LocalDate arg0) {
			System.out.println("toString " + arg0);
			System.out.println("dp value: " + DatePickerTableCell.this.dp.getValue());
			if(arg0 == null) return null;
			if(DatePickerTableCell.this.isEditing()) {
				if (checkValidDate())
					DatePickerTableCell.this.commitEdit(arg0);
				else {
					DatePickerTableCell.this.cancelEdit();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Ngày sinh không hợp lệ");
					alert.setHeaderText(null);
					alert.setContentText("Ngày sinh không được sau ngày vào làm");
					alert.initStyle(StageStyle.DECORATED);
					alert.showAndWait();
				}
			}
			return arg0.format(this.df);
		}
	}
	DatePicker dp = null;
	boolean isNgaySinh;
	StringConverter<LocalDate> strConverter = null;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public DatePickerTableCell() {
		createDatePicker();
		strConverter = new dateStringConverter(formatter);
		dp.setConverter(strConverter);
	}
	
	public DatePickerTableCell(StringConverter<LocalDate> strConv) {
		createDatePicker();
		this.strConverter = strConv;
		dp.setConverter(strConv);
	}

	void createDatePicker() {
		this.dp = new DatePicker();
		dp.setPromptText("dd/MM/yyyy");
		dp.valueProperty().addListener((observableVal,oldVal,newVal)->{
			System.out.println("newVal = " + newVal);
			System.out.println("oldVal = " + oldVal);
			if(this.isEditing() && newVal != null && oldVal != null) {
				System.out.println("commit from value");
				if(checkValidDate())
					this.commitEdit(newVal);
				else {
					this.cancelEdit();
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Ngày sinh không hợp lệ");
					alert.setHeaderText(null);
					alert.setContentText("Ngày sinh không được sau ngày vào làm");
					alert.initStyle(StageStyle.DECORATED);
					alert.showAndWait();
				}
					
			}else if(this.isEditing() && oldVal != null) {
				System.out.println("cancel from value"); 
				this.cancelEdit();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Loi dinh dang");
				alert.setHeaderText(null);
				alert.setContentText("Ngay khong phu hop (dd/MM/yyyy)");
				alert.initStyle(StageStyle.DECORATED);
				alert.showAndWait();
			}
		});
	}
	
	@Override
	public void cancelEdit() {
		// TODO Auto-generated method stub
		super.cancelEdit();
		this.setContentDisplay(ContentDisplay.TEXT_ONLY);
		this.setText(this.getItem().format(formatter));
		this.setGraphic(null);
	}

	@Override
	public void startEdit() {
		// TODO Auto-generated method stub
		super.startEdit();
		this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		this.setGraphic(dp);
		this.setText(null);
		dp.setValue(this.getItem());
		
		NhanVien nv = this.getTableRow().getItem();
		if(nv.getNgaySinh().equals(this.getItem())) 
			isNgaySinh = true;
		else 
			isNgaySinh = false;
	}

	@Override
	protected void updateItem(LocalDate item, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(item, empty);
		if(empty) {
			this.setText(null);
			this.setGraphic(null);
		}else {
			if(this.isEditing()) {
				dp.setValue(item);
				this.setText(null);
				this.setGraphic(dp);
				this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				this.setPadding(new Insets(15, 0, 0, 0));
			}else {
				this.setContentDisplay(ContentDisplay.TEXT_ONLY);
				this.setText(item.format(formatter));
				this.setGraphic(null);
				this.setTextAlignment(TextAlignment.LEFT);
				this.setPadding(new Insets(15, 0, 0, 0));
			}
		}
		TableRow<NhanVien> row = this.getTableRow();
		row.hoverProperty().addListener((observableVal,oldVal,newVal) -> {
			if(newVal) {
				this.setFont(Font.loadFont(getClass().
						getResource("/asset/BeVietnamPro-SemiBold.ttf").toExternalForm(), 14));
			}else {
				this.setFont(Font.loadFont(getClass().
						getResource("/asset/BeVietnamPro-Regular.ttf").toExternalForm(), 13));
			}
		});
	}
	
	boolean checkValidDate() {
		if(isNgaySinh) {
			LocalDate date = this.getTableRow().getItem().getNgayVL();
			if(this.dp.getValue().isAfter(date)) 
				return false;
		}else { 
			LocalDate date = this.getTableRow().getItem().getNgaySinh();
			if(this.dp.getValue().isBefore(date))
				return false;
		}
		return true;
	}
}

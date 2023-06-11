package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ThongKeController implements Initializable {

    @FXML
    private AnchorPane giaoDienAnchorPane;
    
    @FXML
    private Text namDoanhThuText;
    
    @FXML 
    private MenuButton chonNamMenuButton;

    private int selectedYear = 2023; // Variable to store the selected year
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	DoanhThuCuaHangCacThang(2023);
    	
    	chonNamMenuButton.getItems().forEach(item -> item.setOnAction(event -> {
            MenuItem menuItem = (MenuItem) event.getSource();
            selectedYear = Integer.parseInt(menuItem.getText());
            DoanhThuCuaHangCacThang(selectedYear);
        }));
    	 
        Top3KhachHangCoDoanhThuCaoNhat();
        Top3SanPhamCoDoanhThuCaoNhat();
        SoLuongSanPhamCacLoai();
    }
    
    public void DoanhThuCuaHangCacThang(int year) {
    	NumberAxis xAxis = new NumberAxis(1, 12, 1); // Set the lower and upper bounds of the X-axis
        NumberAxis yAxis = new NumberAxis();

        namDoanhThuText.setText("Doanh thu cửa hàng các tháng trong năm " + selectedYear);
        xAxis.setLabel("Tháng");
        yAxis.setLabel("Doanh thu (triệu đồng)");

        // Create the line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(null);
        lineChart.setLegendVisible(false);

        // Create a series of data points
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data<>(1, 191.6));
        dataSeries.getData().add(new XYChart.Data<>(2, 111.5));
        dataSeries.getData().add(new XYChart.Data<>(3, 112.7));
        dataSeries.getData().add(new XYChart.Data<>(4, 111.9));
        dataSeries.getData().add(new XYChart.Data<>(5, 132.5));
        dataSeries.getData().add(new XYChart.Data<>(6, 161.7));
        dataSeries.getData().add(new XYChart.Data<>(7, 181.6));
        dataSeries.getData().add(new XYChart.Data<>(8, 172.7));
        dataSeries.getData().add(new XYChart.Data<>(9, 132.6));
        dataSeries.getData().add(new XYChart.Data<>(10, 111.7));
        dataSeries.getData().add(new XYChart.Data<>(11, 166.8));
        dataSeries.getData().add(new XYChart.Data<>(12, 200.8));
        
        for (XYChart.Data<Number, Number> data : dataSeries.getData()) {
            Node node = data.getNode();
            Tooltip tooltip = new Tooltip(data.getYValue().toString());
            Tooltip.install(node, tooltip);
        }


        // Add the series to the line chart
        lineChart.getData().add(dataSeries);

        lineChart.setLayoutX(120);
        lineChart.setLayoutY(40);
        lineChart.setPrefHeight(350);
        lineChart.setPrefWidth(1100);

        giaoDienAnchorPane.getChildren().add(lineChart);
    }
    
    public void Top3KhachHangCoDoanhThuCaoNhat() {
    	CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Khách hàng");
        yAxis.setLabel("Doanh thu (triệu đồng)");

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(null); // Remove the data series label

        // Create a series of data points
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName(null); // Remove the data series name
        dataSeries.getData().add(new XYChart.Data<>("Nguyễn Văn A", 22));
        dataSeries.getData().add(new XYChart.Data<>("Phạm Thị B", 19.6));
        dataSeries.getData().add(new XYChart.Data<>("Trịnh Quang C", 18.8));
        barChart.setCategoryGap(50); // Adjust the gap between bars
        barChart.setBarGap(10); // Adjust the gap between categories

        // Add the series to the bar chart
        barChart.getData().add(dataSeries);
        
        for (XYChart.Data<String, Number> data : dataSeries.getData()) {
            Node node = data.getNode();
            Tooltip tooltip = new Tooltip(data.getYValue().toString());
            Tooltip.install(node, tooltip);
        }

        barChart.setLayoutX(800);
        barChart.setLayoutY(460);
        barChart.setPrefHeight(300);
        barChart.setPrefWidth(400);

        giaoDienAnchorPane.getChildren().add(barChart);
    }
    
    public void Top3SanPhamCoDoanhThuCaoNhat() {
    	CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Sản Phẩm");
        yAxis.setLabel("Số lượng");

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(null); // Remove the data series label

        // Create a series of data points
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName(null); // Remove the data series name
        dataSeries.getData().add(new XYChart.Data<>("Áo thun Nam SP005", 22));
        dataSeries.getData().add(new XYChart.Data<>("Quan kaki SP007", 21));
        dataSeries.getData().add(new XYChart.Data<>("Quan Thi SP023", 17));
        barChart.setCategoryGap(50); // Adjust the gap between bars
        barChart.setBarGap(10); // Adjust the gap between categories

        // Add the series to the bar chart
        barChart.getData().add(dataSeries);
        for (XYChart.Data<String, Number> data : dataSeries.getData()) {
            Node node = data.getNode();
            Tooltip tooltip = new Tooltip(data.getYValue().toString());
            Tooltip.install(node, tooltip);
        }

        barChart.setLayoutX(30);
        barChart.setLayoutY(460);
        barChart.setPrefHeight(300);
        barChart.setPrefWidth(400);

        giaoDienAnchorPane.getChildren().add(barChart);
    }
    
    public void SoLuongSanPhamCacLoai() {
    	 // Create the pie chart
        PieChart pieChart = new PieChart();

        // Create the data slices
        PieChart.Data slice1 = new PieChart.Data("Ao LSP001", 40);
        PieChart.Data slice2 = new PieChart.Data("Quan LSP002", 40);
        PieChart.Data slice3 = new PieChart.Data("Khan LSP003", 15);
        PieChart.Data slice4 = new PieChart.Data("Mu LSP004", 5);
        PieChart.Data slice5 = new PieChart.Data("Kinh LSP005", 0);

        // Add the data slices to the pie chart
        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);
        pieChart.getData().add(slice4);
        pieChart.getData().add(slice5);
        
        pieChart.setLayoutX(420);
        pieChart.setLayoutY(420);
        pieChart.setPrefHeight(300);
        pieChart.setPrefWidth(400);

        giaoDienAnchorPane.getChildren().add(pieChart);
    }
    
    
    

}

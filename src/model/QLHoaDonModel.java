package model;

import java.util.ArrayList;
public class QLHoaDonModel {
	private ArrayList<HoaDon> dsHoaDon;

	public QLHoaDonModel(ArrayList<HoaDon> dsHoaDon) {
		this.dsHoaDon = dsHoaDon;
	}

	public QLHoaDonModel() {
	   this.dsHoaDon = new ArrayList<HoaDon>();
	}

	public ArrayList<HoaDon> getDsHoaDon() {
		return dsHoaDon;
	}

	public void setDsHoaDon(ArrayList<HoaDon> dsHoaDon) {
		this.dsHoaDon = dsHoaDon;
	}
}

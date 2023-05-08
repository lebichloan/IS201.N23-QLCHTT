package model;

import java.util.ArrayList;

public class QLSanPhamModel {
	private ArrayList<SanPham> dsSanPham;

	public ArrayList<SanPham> getDsSanPham() {
		return dsSanPham;
	}

	public void setDsSanPham(ArrayList<SanPham> dsSanPham) {
		this.dsSanPham = dsSanPham;
	}

	public QLSanPhamModel(ArrayList<SanPham> dsSanPham) {
		this.dsSanPham = dsSanPham;
	}

	public QLSanPhamModel() {
	}
}

package entity;

import java.util.Objects;

public class Ban {
    private String maBan;
    private int soCho;
    private String khuVuc;
    private String trangThai;
    
	public Ban() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Ban(String maBan, int soCho, String khuVuc, String trangThai) {
		super();
		this.maBan = maBan;
		this.soCho = soCho;
		this.khuVuc = khuVuc;
		this.trangThai = trangThai;
	}
	public String getMaBan() {
		return maBan;
	}
	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}
	public int getSoCho() {
		return soCho;
	}
	public void setSoCho(int soCho) {
		this.soCho = soCho;
	}
	public String getKhuVuc() {
		return khuVuc;
	}
	public void setKhuVuc(String khuVuc) {
		this.khuVuc = khuVuc;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maBan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ban other = (Ban) obj;
		return Objects.equals(maBan, other.maBan);
	}
    
    // Constructor, getter, setter
}


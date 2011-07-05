package com.silthus.rcregions.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "rcr_players")
public class Players {
	
	@Id
	private int id;
	@NotNull
	@Length(max = 32)
	private String playername;
	private int regioncount;
	private boolean locked;
	private String lastlogin;
	private double taxdebt;
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public String getPlayername() {
		return playername;
	}
	public void setRegioncount(int regioncount) {
		this.regioncount = regioncount;
	}
	public int getRegioncount() {
		return regioncount;
	}
	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}
	public String getLastlogin() {
		return lastlogin;
	}
	public void setTaxdebt(double taxdebt) {
		this.taxdebt = taxdebt;
	}
	public double getTaxdebt() {
		return taxdebt;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public boolean isLocked() {
		return locked;
	}

}

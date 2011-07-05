package com.silthus.rcregions.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "rcr_regions")
public class Regions {
	
	@Id
	private int id;
	@NotNull
	@Length(max = 32)
	private String name;
	private String owner;
	private double price;
	private int size;
	private boolean buyable;
	private boolean locked;
	private String lastchanged;
	private double taxcosts;
	private int priceclass;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setName(String regionname) {
		this.name = regionname;
	}
	public String getName() {
		return name;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwner() {
		return owner;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPrice() {
		return price;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
	public void setBuyable(boolean buyable) {
		this.buyable = buyable;
	}
	public boolean isBuyable() {
		return buyable;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public boolean isLocked() {
		return this.locked;
	}
	public void setLastchanged(String lastchanged) {
		this.lastchanged = lastchanged;
	}
	public String getLastchanged() {
		return lastchanged;
	}
	public void setTaxcosts(double taxcosts) {
		this.taxcosts = taxcosts;
	}
	public double getTaxcosts() {
		return taxcosts;
	}
	public void setPriceclass(int taxclass) {
		this.priceclass = taxclass;
	}
	public int getPriceclass() {
		return priceclass;
	}

}

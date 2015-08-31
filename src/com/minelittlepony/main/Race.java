package com.minelittlepony.main;

public enum Race {
	EARTH(false, false),
	PEGASUS(true, false),
	THESTRAL(true, false),
	BUTTERFLY(true, false),
	UNICORN(false, true),
	ALICORN(true, true);
	
	private final boolean flight;
	private final boolean magic;
	
	Race(boolean flight, boolean magic) {
		this.flight = flight;
		this.magic = magic;
	}
	
	public boolean canFly() {
		return flight;
	}
	
	public boolean canCast() {
		return magic;
	}
}
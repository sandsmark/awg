package com.googlecode.awg.gui;
public interface Moveable {
	public enum Direction {
		NONE, UP, DOWN, LEFT, RIGHT,
		UP_LEFT, UP_RIGHT, 
		DOWN_LEFT, DOWN_RIGHT
	}

	public void move(Direction dir);
}

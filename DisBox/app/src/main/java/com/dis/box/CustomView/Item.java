package com.dis.box.CustomView;

import android.graphics.drawable.*;

public class Item
{
	private int unSelectedColor, selectedColor;
	private String Text;
	private Drawable Icon;

	public Item(int unSelectedColor, int selectedColor, String text, Drawable icon)
	{
		this.unSelectedColor = unSelectedColor;
		this.selectedColor = selectedColor;
		Text = text;
		Icon = icon;
	}

	public void setUnSelectedColor(int unSelectedColor)
	{
		this.unSelectedColor = unSelectedColor;
	}

	public int getUnSelectedColor()
	{
		return unSelectedColor;
	}

	public void setSelectedColor(int selectedColor)
	{
		this.selectedColor = selectedColor;
	}

	public int getSelectedColor()
	{
		return selectedColor;
	}

	public void setIcon(Drawable icon)
	{
		Icon = icon;
	}

	public Drawable getIcon()
	{
		return Icon;
	}

	public void setText(String text)
	{
		Text = text;
	}

	public String getText()
	{
		return Text;
	}
}

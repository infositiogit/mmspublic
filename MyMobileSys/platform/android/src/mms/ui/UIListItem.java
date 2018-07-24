package mms.ui;
/*
 * Created on 21-04-2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class UIListItem {
	protected String text;
	protected String[] text2;
	protected String image;
	protected String id;
	protected Object data;
	/**
	 * @param text
	 */
	public UIListItem(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = new String[]{text2};
	}
	
	public void setText2(String[] t2) {
		text2 = t2;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	protected String getHTMLItem() {
		StringBuffer ret = new StringBuffer("<li id=\"" + id + "\"><a href=\"#\">");
		String text = this.getText().replace("'", "\\'");;
		String[] text2 = this.getText2();
				
		if(this.getImage() != null) {
			ret.append("<img src=\"").append(this.getImage()).append("\">");
			if(text2 != null) {
				ret.append("<h2>").append(text).append("</h2>");
				for(String t2: text2) {
					ret.append("<p style=\"white-space: normal !important;\">").append(t2).append("</p>");
				}
				
			} else {
				ret.append(this.getText());
			}

		} else {
			if(text2 != null) {
				ret.append("<h2>").append(text).append("</h2>");
				for(String t2: text2) {
					ret.append("<p style=\"white-space: normal !important;\">").append(t2).append("</p>");
				}
				
			} else {
				ret.append(this.getText());
			}
		}
		
		
		ret.append("</a></li>");
		return(ret.toString());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

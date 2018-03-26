package com.duncan.read.domain.data;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
@SuppressWarnings("serial")
public class GetCommentResponse implements Serializable {

	@SerializedName("parent")
	@JsonField(name ="parent")
	private int parent;

	@SerializedName("by")
	@JsonField(name ="by")
	private String by;

	@SerializedName("id")
	@JsonField(name ="id")
	private int id;

	@SerializedName("text")
	@JsonField(name ="text")
	private String text;

	@SerializedName("time")
	@JsonField(name ="time")
	private long time;

	@SerializedName("type")
	@JsonField(name ="type")
	private String type;

	@SerializedName("kids")
	@JsonField(name ="kids")
	private List<Integer> kids;

	public void setParent(int parent){
		this.parent = parent;
	}

	public int getParent(){
		return parent;
	}

	public void setBy(String by){
		this.by = by;
	}

	public String getBy(){
		return by;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setTime(long time){
		this.time = time;
	}

	public long getTime(){
		return time;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setKids(List<Integer> kids){
		this.kids = kids;
	}

	public List<Integer> getKids(){
		return kids;
	}

	@Override
 	public String toString(){
		return 
			"GetCommentResponse{" + 
			"parent = '" + parent + '\'' + 
			",by = '" + by + '\'' + 
			",id = '" + id + '\'' + 
			",text = '" + text + '\'' + 
			",time = '" + time + '\'' + 
			",type = '" + type + '\'' + 
			",kids = '" + kids + '\'' + 
			"}";
		}
}
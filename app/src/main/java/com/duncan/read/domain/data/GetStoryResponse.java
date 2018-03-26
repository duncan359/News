package com.duncan.read.domain.data;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
@SuppressWarnings("serial")
public class GetStoryResponse implements Serializable {

	@SerializedName("score")
	@JsonField(name ="score")
	private int score;

	@SerializedName("by")
	@JsonField(name ="by")
	private String by;

	@SerializedName("id")
	@JsonField(name ="id")
	private int id;

	@SerializedName("time")
	@JsonField(name ="time")
	private long time;

	@SerializedName("title")
	@JsonField(name ="title")
	private String title;

	@SerializedName("type")
	@JsonField(name ="type")
	private String type;

	@SerializedName("descendants")
	@JsonField(name ="descendants")
	private int descendants;

	@SerializedName("url")
	@JsonField(name ="url")
	private String url;

	@SerializedName("kids")
	@JsonField(name ="kids")
	private List<Integer> kids;

	public void setScore(int score){
		this.score = score;
	}

	public int getScore(){
		return score;
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

	public void setTime(long time){
		this.time = time;
	}

	public long getTime(){
		return time;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setDescendants(int descendants){
		this.descendants = descendants;
	}

	public int getDescendants(){
		return descendants;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
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
			"GetStoryResponse{" + 
			"score = '" + score + '\'' + 
			",by = '" + by + '\'' + 
			",id = '" + id + '\'' + 
			",time = '" + time + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",descendants = '" + descendants + '\'' + 
			",url = '" + url + '\'' + 
			",kids = '" + kids + '\'' + 
			"}";
		}
}
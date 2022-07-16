package fr.dotmazy.dotplugin.core;

public class Report {
	
	private int id;
	private String name;
	private String reason;
	private String auteur;
	private String date;
	private String uuid;
	
	public Report(int id, String name, String reason, String auteur, String date, String uuid) {
		super();
		this.name = name;
		this.reason = reason;
		this.auteur = auteur;
		this.date = date;
		this.uuid = uuid;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getReason() {
		return reason;
	}

	public String getAuteur() {
		return auteur;
	}

	public String getDate() {
		return date;
	}

	public String getUuid() {
		return uuid;
	}

	public int getId() {
		return id;
	}

}

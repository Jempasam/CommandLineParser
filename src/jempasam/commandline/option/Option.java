package jempasam.commandline.option;

import jempasam.samstream.stream.SamStream;

public class Option<T> {
	
	
	
	private String completeName;
	private String smallName;
	private String description;
	private OptionParser<T> parser;
	
	
	
	public Option(String completeName, String smallName, String description, OptionParser<T> parser) {
		super();
		this.completeName = "--"+completeName;
		this.smallName = smallName;
		this.description = description;
		this.parser = parser;
	}

	public boolean test(String option) {
		if(option.equals(completeName)) {
			return true;
		}
		else if(option.startsWith("-") && !option.startsWith("--")) {
			for(int i=1; i<option.length(); i++) {
				if(option.substring(i, i+1).equals(smallName))return true;
			}
		}
		return false;
	}
	
	public T getValue(SamStream<String> argline) throws OptionParserException {
		return parser.parse(argline);
	}
	
	public String getDoc() {
		return "< -"+smallName+" | "+completeName+" > <"+parser.getType().getSimpleName()+"> : "+description;
	}
}

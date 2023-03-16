package jempasam.commandline;

import jempasam.commandline.option.OptionParser;
import jempasam.commandline.option.OptionParserException;
import jempasam.samstream.stream.SamStream;

public class CommandLines {
	
	public static final OptionParser<String> STRING=new OptionParser<>() {
		@Override public Class<String> getType() {return String.class;}
		@Override public String parse(SamStream<String> str) throws OptionParserException {
			return str.next().orElseThrow(()->new OptionParserException("Miss value after argument"));
		}
	};
	
	public static final OptionParser<Integer> INTEGER=new OptionParser<>() {
		@Override public Class<Integer> getType() {return Integer.class;}
		@Override public Integer parse(SamStream<String> str) throws OptionParserException {
			try {
				return Integer.parseInt(str.next().orElseThrow(()->new OptionParserException("Miss value after argument")));
			}catch (NumberFormatException e) {
				throw new OptionParserException("Bad integer format");
			}
		}
	};
	
	public static final OptionParser<Boolean> BOOLEAN=new OptionParser<>() {
		@Override public Class<Boolean> getType() {return Boolean.class;}
		@Override public Boolean parse(SamStream<String> str) throws OptionParserException {
			return true;
		}
	};
	
}

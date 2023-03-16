package jempasam.commandline.option;

import jempasam.samstream.stream.SamStream;

public interface OptionParser<T> {
	T parse(SamStream<String> strs) throws OptionParserException;
	Class<T> getType();
}

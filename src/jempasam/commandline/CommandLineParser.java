package jempasam.commandline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jempasam.commandline.option.Option;
import jempasam.commandline.option.OptionParser;
import jempasam.commandline.option.OptionParserException;
import jempasam.logger.SLogger;
import jempasam.samstream.SamStreams;
import jempasam.samstream.stream.SamStream;

public class CommandLineParser {
	
	
	
	private SLogger logger;
	private HashMap<Option<?>, Object> values;
	private List<Option<?>> options;
	private List<String> arguments;
	
	
	
	public CommandLineParser(SLogger logger) {
		options=new ArrayList<>();
		values=new HashMap<>();
		arguments=new ArrayList<>();
		this.logger=logger;
	}
	
	
	
	public <T> Option<T> addOption(String small, String comple, String description, OptionParser<T> type) {
		Option<T> opt=new Option<>(comple, small, description, type);
		options.add(opt);
		return opt;
	}
	
	public void parse(String[] args) {
		SamStream.BufferedSStream<String> argstream=SamStreams.create(args).buffered(2);
		while(argstream.hasNext()) {
			String arg=argstream.tryNext();
				SamStream<Option<?>> optionstream=SamStreams.create(options).filter(op->op.test(arg));
				if(optionstream.first().isPresent()) {
					optionstream.forEach(option->{
						try {
							Object value=option.getValue(argstream);
							values.put(option, value);
						} catch (OptionParserException e) { logger.error(e.getMessage()); }
					});
				}
				else arguments.add(arg);
		}
	}
	
	public String getDoc() {
		StringBuilder sb=new StringBuilder();
		for(Option<?> option : options)sb.append(option.getDoc()).append("\n");
		if(sb.length()>0)sb.setLength(sb.length()-1);
		return sb.toString();
	}
	
	public <T> Optional<T> getValue(Option<T> option){
		@SuppressWarnings("unchecked")
		T ret=(T)values.get(option);
		return Optional.ofNullable(ret);
	}
	
	public List<String> arguments(){
		return arguments;
	}
}

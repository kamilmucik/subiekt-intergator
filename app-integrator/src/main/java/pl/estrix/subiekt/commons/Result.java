package pl.estrix.subiekt.commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;
import pl.estrix.subiekt.commons.annotation.ConfigJsonIgnore;

import java.io.Serializable;

import static java.util.Objects.nonNull;

/**
 * Created by Kamil on 30-07-2020.
 */
@Getter
@ToString
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class Result<T> implements Serializable {

	T data;

	@ConfigJsonIgnore
	@JsonIgnore
	String errorMessage;

	ErrorCode errorCode;

	Result(){
	}

	@JsonIgnore
	public boolean isError(){return nonNull(errorCode);}
}

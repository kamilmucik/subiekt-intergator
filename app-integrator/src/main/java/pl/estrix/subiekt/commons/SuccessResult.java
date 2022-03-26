package pl.estrix.subiekt.commons;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.ToString;

/**
 * Created by Kamil on 30-07-2020.
 */
@ToString(callSuper = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class SuccessResult<T> extends Result<T> {

	private SuccessResult(){}

	public SuccessResult(T data){
		this.data = data;
		this.errorCode = null;
		this.errorMessage = null;
	}

}

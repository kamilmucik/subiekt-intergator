package pl.estrix.subiekt.commons;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Kamil on 30-07-2020.
 */
public enum ErrorCode {

	NO_ERROR(0),
	SERVER_ERROR(500);

	private int code;

	ErrorCode(int code){this.code = code;}

	private static final ConcurrentMap<Integer, ErrorCode> ERROR_CODE_MAPPING = new ConcurrentHashMap<>();

	static {
		Arrays.stream(values()).forEach(errorCode -> ERROR_CODE_MAPPING.put(errorCode.getCode(),errorCode));
	}

	public static ErrorCode fromCode(int code) {return fromCode(code,ErrorCode.SERVER_ERROR);}

	public static ErrorCode fromCode(int code, ErrorCode defaultErrorCode) {
		return ERROR_CODE_MAPPING.getOrDefault(code, defaultErrorCode);
	}

	public int getCode(){return code;}

	@Override
	public String toString(){
		return String.format("%s (%s)",name(),code);
	}
}

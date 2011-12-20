package com.laiyifen.sap.exception;

/**
 * This exception class must be used when Service connection with 
 * external service fails
 * */
 public final class LaiYiFenCommunicationFailure extends Exception {

	private String methodName;
	private String nameSpace;
	
	public LaiYiFenCommunicationFailure() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LaiYiFenCommunicationFailure(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public LaiYiFenCommunicationFailure(String methodName, String nameSpace) {
		super("Communication Failure for "+methodName+" in ns " +nameSpace);
		// TODO Auto-generated constructor stub
	}

	public LaiYiFenCommunicationFailure(String methodName, String nameSpace, String message) {
		super("Communication Failure for "+methodName+" in ns " +nameSpace+"; Error : "+message);
		// TODO Auto-generated constructor stub
	}

	public LaiYiFenCommunicationFailure(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LaiYiFenCommunicationFailure(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public String _getMethod()
	{
		return methodName;
	}
	
	public String _getNamespace()
	{
		return nameSpace;
	}

}

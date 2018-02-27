package com.reminder.api.controllers.exception;

import java.io.Serializable;

//Erro Padrão --> Customizando erro
public class StandardError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer status; // status HTTP do erro
	private String msg; // mensagem de erro
	private Long timeStamp; // instante que ocorreu o erro (milisegundos)
	
	
	public StandardError(Integer status, String msg, Long timeStamp) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Long getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
		
}

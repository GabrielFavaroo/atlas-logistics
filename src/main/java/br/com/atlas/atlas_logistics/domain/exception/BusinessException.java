package br.com.atlas.atlas_logistics.domain.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(String message){
        super(message);
    }
}

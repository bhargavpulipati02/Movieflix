package org.project.movieflix.Exceptions;

public class FileEmptyException extends RuntimeException{
    public FileEmptyException(String message){
        super(message);
    }
}

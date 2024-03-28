package fr.did.exceptions.fr.did.object;

public class FormException extends Exception{

    public FormException() {
        super("The object form does not exists.");
    }
}

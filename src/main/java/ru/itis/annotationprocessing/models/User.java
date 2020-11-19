package ru.itis.annotationprocessing.models;

import ru.itis.annotationprocessing.annotations.HtmlForm;
import ru.itis.annotationprocessing.annotations.HtmlInput;

@HtmlForm(method = "POST", action = "/hello")
public class User {

    @HtmlInput(placeholder = "Enter email...", name = "email")
    private String email;
    @HtmlInput(placeholder = "Enter password", name = "password", type = "password")
    private String password;

}

package com.example.domain;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Profile")

public class Profile implements Serializable {
    private String avatar_path;
    private String introduction;

}

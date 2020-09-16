package com.leaderboard.Models;

/**
 * Created by Reagan Kieti on 16/09/2020.
 */
public class Submit {
    public String email;
    public String firstname;
    public String lastname;
    public String githubLink;

    public Submit(String email, String firstname, String lastname, String githubLink) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.githubLink = githubLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }
}

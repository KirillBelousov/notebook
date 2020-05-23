package ru.kirillbelousov.notebook;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Note {
    @Id
    @GeneratedValue
    private Long id;
    private final LocalDateTime creationMoment;
    private LocalDateTime editionMoment;
    private String subject;
    private String content;

    Note() {
        this.creationMoment = LocalDateTime.now();
        this.editionMoment = this.creationMoment;
    }

    Note(String subject, String content) {
        this.creationMoment = LocalDateTime.now();
        this.editionMoment = this.creationMoment;
        this.subject = subject;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEditionMoment() {
        return editionMoment;
    }

    public void setEditionMoment(LocalDateTime editionMoment) {
        this.editionMoment = editionMoment;
    }

    public LocalDateTime getCreationMoment() {
        return creationMoment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

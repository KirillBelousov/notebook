package ru.kirillbelousov.notebook.entity;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Entity
@Audited
public class Note {
    @Id
    @GeneratedValue
    private Long id;
    private final LocalDateTime creationMoment;
    private LocalDateTime editionMoment;
    private String subject;
    @Lob
    private String content;

    Note() {
        this.creationMoment = LocalDateTime.now();
        setEditionMoment(this.creationMoment);
        //this.editionMoment = this.creationMoment;
    }

    Note(String subject, String content) {
        this.creationMoment = LocalDateTime.now();
        setEditionMoment(this.creationMoment);
        setSubject(subject);
        setContent(content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationMoment() {
        return creationMoment;
    }

    public LocalDateTime getEditionMoment() {
        return editionMoment;
    }

    public void setEditionMoment(LocalDateTime editionMoment) {
        this.editionMoment = editionMoment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject != "" && content != null) {
            this.subject = subject;
        }
        else {
            this.subject = "no subject";
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content != "" && content != null) {
            this.content = content;
        }
        else {
            this.content = "no content";
        }
    }
}

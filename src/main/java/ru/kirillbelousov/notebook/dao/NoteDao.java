package ru.kirillbelousov.notebook.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kirillbelousov.notebook.entity.Note;

public interface NoteDao extends JpaRepository<Note, Long> {
}

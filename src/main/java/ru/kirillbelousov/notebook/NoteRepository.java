package ru.kirillbelousov.notebook;

import org.springframework.data.jpa.repository.JpaRepository;

interface NoteRepository extends JpaRepository<Note, Long> {
}

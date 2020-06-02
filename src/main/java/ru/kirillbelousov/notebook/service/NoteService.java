package ru.kirillbelousov.notebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kirillbelousov.notebook.dao.EnversNoteHistoryDao;
import ru.kirillbelousov.notebook.dao.NoteDao;
import ru.kirillbelousov.notebook.entity.Note;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NoteService {

    @Autowired
    private NoteDao noteDao;
    @Autowired
    private EnversNoteHistoryDao noteHistoryDao;

    public List<Note> getAll() {
        return noteDao.findAll();
    }

    public Note getById(long id) throws NoSuchElementException{
        return noteDao.findById(id).get();
    }

    public void save(Note note){
        noteDao.save(note);
    }

    public void update(Note note){
        Note oldNote = getById( note.getId() );
        oldNote.setSubject( note.getSubject() );
        oldNote.setContent( note.getContent() );
        oldNote.setEditionMoment( note.getCreationMoment() );
        save(oldNote);
    }

    public void deleteById(long id){
        noteDao.deleteById(id);
    }

    public List<Note> getHistoryOfNonDeletedById(long id) throws NoSuchElementException{
        return noteHistoryDao.getHistoryOfNonDeletedById(id);
    }
}

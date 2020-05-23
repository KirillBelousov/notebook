package ru.kirillbelousov.notebook;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {
    private final NoteRepository repository;

    NoteController(NoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/notes")
    public String getAll(Model model) {
        model.addAttribute("notes", repository.findAll() );
        return "notesList";
    }

    @GetMapping("/notes/{id}")
    public String getById(@PathVariable Long id, Model model) {
        Note targetNote;
        try {
            targetNote = repository.findById(id).get();
        }
        catch (NoSuchElementException e) {
            return "noteNotFound";
        };
        model.addAttribute("note", targetNote );
        return "oneNote";
    }

    @GetMapping("/new")
     public String showCreationForm() {
        return "createNewNote";
    }

    @PostMapping("/new")
    public String saveNewNote(Note newNote) {
        repository.save(newNote);
        return "redirect:notes/" + newNote.getId();
    }

    @PostMapping("/edit/{id}")
    public String getForEdit(@PathVariable Long id, Model model) {
        Note targetNote;
        try {
            targetNote = repository.findById(id).get();
        }
        catch (NoSuchElementException e) {
            return "noteNotFound";
        };
        model.addAttribute("note", targetNote );
        return "editNote";
    }

    @PostMapping("/edit")
    public String edit(Note editedNote) {
        Note oldNote = repository.findById( editedNote.getId() ).get();
        oldNote.setSubject( editedNote.getSubject() );
        oldNote.setContent( editedNote.getContent() );
        oldNote.setEditionMoment( editedNote.getCreationMoment() );
        repository.save(oldNote);
        return "redirect:notes/" + oldNote.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/notes";
    }
}
